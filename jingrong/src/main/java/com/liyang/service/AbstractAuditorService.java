package com.liyang.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.MapDifference.ValueDifference;
import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorAct.FirstNoticeType;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.Role.RoleCode;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

public abstract class AbstractAuditorService<T extends AbstractAuditorEntity, A extends AbstractAuditorAct, L extends AbstractAuditorLog>
		implements ISqlInit, IActable<T, A> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TIMService timService;

	public abstract LogRepository<L> getLogRepository();

	public abstract L getLogInstance();

	public abstract AuditorEntityRepository<T> getAuditorEntityRepository();

	public abstract void injectEntityActRepository();

	public abstract void injectLogRepository();

	protected A getActByRole(List<A> acts, String code, Role role) {
		for (A abstractAuditorAct : acts) {
			if (abstractAuditorAct.getActCode().equals(code)) {
				Set roles = abstractAuditorAct.getRoles();
				if (roles == null) {
					throw new FailReturnObject(1130, "没有角色操作动作");
				}
				for (Object object : roles) {
					if (((Role) object).getRoleCode().equals(role.getRoleCode())) {
						return abstractAuditorAct;
					}
				}
			}
		}
		return null;
	}

	public A getAct(T entity, String code) {
		return getAct(entity, code, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.service.IActable#getAct(T, java.lang.String)
	 */
	public A getAct(T entity, String code, Role role) {

		if (role == null) {
			if (CommonUtil.getPrincipal() != null) {
				role = CommonUtil.getPrincipal().getRole();
			} else {
				role = new Role();
				role.setRoleCode(Role.RoleCode.valueOf("ANONYMOUS"));
			}
		}

		List<A> acts = entity.getState().getActs();

		if (acts.isEmpty()) {
			throw new FailReturnObject(1170, "当前状态下动作列表为空");
		}

		A act = getActByRole(acts, code, role);
		if (act == null) {
			throw new FailReturnObject(660, "没有为角色设置动作");
		}
		return act;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.service.IActable#canAct(T, java.lang.String)
	 */
	public Boolean canAct(T entity, String code) {
		getAct(entity, code);
		return true;
	}

	public void doActLog(T entity , Object linked) {
		
		
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(linked);
		Object propertyValue = beanWrapperImpl.getPropertyValue("value");
		Object storedSnapshot = beanWrapperImpl.getPropertyValue("storedSnapshot");

		
		if (!"delete".equals(entity.getLastAct().getActCode())) {
			L log = getLogInstance();
			log.setAct(entity.getLastAct());
			log.setEntity(entity);
			log.setLabel(entity.getLastAct().getLabel());
			log.setRequestBody("待添加");
			
			System.out.println(entity.getMap());
			System.out.println(CommonUtil.transBean2Map(entity));
			
			MapDifference<String,Integer> diffHadle=Maps.difference(entity.getMap(),CommonUtil.transBean2Map(entity));
			Map<String, ValueDifference<Integer>> entriesDiffering = diffHadle.entriesDiffering();
			Set<Entry<String, ValueDifference<Integer>>> entrySet = entriesDiffering.entrySet();
			for (Entry<String, ValueDifference<Integer>> entry : entrySet) {
				String key = entry.getKey();
				ValueDifference<Integer> value = entry.getValue();
				System.out.println(key);
				System.out.println(value.leftValue());
				System.out.println(value.rightValue());
			}
			
			getLogRepository().save(log);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.service.IActable#doAct(T, A)
	 */
	public T doBeforeAct(T entity, A act) {
		return doBeforeAct(entity, act, null, null);
	}
	public T doBeforeAct(T entity, A act , Object linked) {
		return doBeforeAct(entity, act, linked ,null);
	}

	public T doBeforeAct(T entity, A act,Object linked ,User fromUser) {
		entity.setLastAct(act);

		return entity;
	}



	public T doAfterAct(T entity) {
		User fromUser = null;
		if (request.getParameter("fromUser") != null) {
			fromUser = userRepository.findByUnionid(request.getParameter("fromUser"));
		}

		return doAfterAct(entity, null,fromUser);
	}
	
	
	public T doAfterAct(T entity,Object linked) {
		User fromUser = null;
		if (request.getParameter("fromUser") != null) {
			fromUser = userRepository.findByUnionid(request.getParameter("fromUser"));
		}

		return doAfterAct(entity, linked ,fromUser);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.service.IActable#doAct(T, A, com.liyang.domain.user.User)
	 */
	public T doAfterAct(T entity, Object linked, User fromUser) {
		
		doActLog(entity , linked);
		
		if (!"delete".equals(entity.getLastAct().getActCode())) {
			timService.doNotice(fromUser, entity, entity.getLastAct());
		}
		return entity;
	}
}
