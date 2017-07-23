package com.liyang.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflow;
import com.liyang.domain.base.AbstractWorkflowAct;
import com.liyang.domain.base.AbstractWorkflowAct.ActType;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.EntityRepository;
import com.liyang.domain.base.LogRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorAct.FirstNoticeType;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.Role.RoleCode;
import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

public abstract class AbstractWorkflowService<T extends AbstractWorkflowEntity, W extends AbstractWorkflow, A extends AbstractWorkflowAct, S extends AbstractWorkflowState, L extends AbstractWorkflowLog>
		extends AbstractAuditorService {

	@Autowired
	private TIMService timService;

	@Transient
	public abstract LogRepository<L> getLogRepository();
	
	@Transient
	public abstract L getLogInstance();

	public abstract AuditorEntityRepository<T> getAuditorEntityRepository();


	private Boolean isNotOperate(A act) {
		if ("create".equals(act.getActCode()) || "update".equals(act.getActCode()) || "read".equals(act.getActCode())
				|| "delete".equals(act.getActCode()) || "listOwn".equals(act.getActCode())
				|| "listOwnDepartment".equals(act.getActCode())
				|| "listOwnDepartmentAndChildren".equals(act.getActCode())) {
			return true;
		} else {
			return false;
		}
	}

	public A getAct(T entity, String code) {
		return getAct(entity, code, null);
	}
	
	public void doActLog(T entity , Object linked) {
		if (!"delete".equals(entity.getLastAct().getActCode())) {
			L log = getLogInstance();
			log.setAct((AbstractWorkflowAct) entity.getLastAct());
			log.setEntity(entity);
			log.setLabel(entity.getLastAct().getLabel());
			log.setRequestBody("待添加");
			log.setWorkflow(entity.getWorkflow());
			log.setAfterState((AbstractWorkflowState)entity.getState());
			
			
			MapDifference<String,Integer> diffHadle=Maps.difference(entity.getMap(),CommonUtil.transBean2Map(entity));
			Map<String, ValueDifference<Integer>> entriesDiffering = diffHadle.entriesDiffering();
			
			CommonUtil.prettyPrint(entriesDiffering);	
			
			
			getLogRepository().save(log);
		}
	}
	

	public A getAct(T entity, String code, Role role) {

		if (role == null) {
			if (CommonUtil.getPrincipal() != null) {
				role = CommonUtil.getPrincipal().getRole();
			}else{
				role = new Role();
				role.setRoleCode(Role.RoleCode.valueOf("ANONYMOUS"));
			}
		}

		List<A> acts = entity.getState().getActs();
		if (acts.isEmpty()) {
			throw new FailReturnObject(1170, "动作列表为空");
		}
		A act = (A) getActByRole(acts, code, role);
		if (act == null) {
			throw new FailReturnObject(660, "没有为角色设置动作");
		}

		if (isNotOperate(act)) {
			return act;
		}
		if (entity.getWorkflow() == null) {
			if (act.getActType().equals(ActType.START)) {
				return act;
			} else {
				throw new FailReturnObject(650, "没有任务以这个动作启动");
			}

		} else {
			if (act.getActType().equals(ActType.START)) {
				throw new FailReturnObject(1300, "工作流已经启动");
			} else {
				return act;
			}
		}
	}
	public T doBeforeAct(T entity, A act) {
		// TODO Auto-generated method stub
		return doBeforeAct(entity, act, null ,null);
	}
	
	public T doBeforeAct(T entity, A act , Object linked) {
		// TODO Auto-generated method stub
		return doBeforeAct(entity, act, linked ,null);
	}
	public T doBeforeAct(T entity, A act, Object linked ,User fromUser) {
		super.doBeforeAct(entity, act, linked,fromUser);
		if (act.getTargetState() != null) {
			entity.setState(act.getTargetState());
		}

		if (entity.getWorkflow() == null && AbstractWorkflowAct.ActType.START.equals(act.getActType())) {
			entity.setWorkflow(act.getStartWorkflow());
		}
		if (AbstractWorkflowAct.ActType.END.equals(act.getActType())) {
			if (act.getNextWorkflow() != null) {
				entity.setWorkflow(act.getNextWorkflow());
			} else {
				entity.setWorkflow(null);
			}
		}

		return entity;
	}



	public T doAfterAct(T entity) {
		// TODO Auto-generated method stub
		super.doAfterAct(entity);
		return entity;
	}
	public T doAfterAct(T entity, Object linked) {
		super.doAfterAct(entity, linked);
		return entity;
	}

	public T doAfterAct(T entity, Object linked ,User fromUser) {
		super.doAfterAct(entity, linked,fromUser);
		return entity;
	}


}
