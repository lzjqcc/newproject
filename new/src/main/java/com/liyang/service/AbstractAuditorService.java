package com.liyang.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import com.liyang.domain.base.BaseEntity;
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

    /**
     * @param acts 用户在某个状态拥有的动作
     * @param code
     * @param role
     * @return
     */
    protected A getActByRole(List<A> acts, String code, Role role) {
        for (A abstractAuditorAct : acts) {
            if (abstractAuditorAct.getActCode().equals(code)) {
                //当前的动作哪些角色拥有
                Set roles = abstractAuditorAct.getRoles();
                if (roles == null) {
                    throw new FailReturnObject(1130, "没有角色操作动作");
                }
                /*
                role_code
				DEVELOPER,
		        ANONYMOUS,
		        USER,
		        BROKER,
		        GROUP_OWNER,
		        GROUP_MANAGER,
		        GROUP_FINANCE,
		        ..........
				 */
                //
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
        //用户处于的这个状态哪些动作可以拥有
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

    public void doActLog(T entity, Object linked) {

        if (!"delete".equals(entity.getLastAct().getActCode())) {
            L log = getLogInstance();
            log.setAct(entity.getLastAct());
            log.setEntity(entity);
            log.setLabel(entity.getLastAct().getLabel());
            String linkedKey = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
            if ("update".equals(entity.getLastAct().getActCode())) {
                Map entityToDiffMap = CommonUtil.entityToDiffMap(entity, linkedKey, linked);
                log.setRequestBody(CommonUtil.prettyPrint(entityToDiffMap));
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

    public T doBeforeAct(T entity, A act, Object linked) {
        return doBeforeAct(entity, act, linked, null);
    }

    public T doBeforeAct(T entity, A act, Object linked, User fromUser) {
        entity.setLastAct(act);
        return entity;
    }

    public T doAfterAct(T entity) {
        User fromUser = null;
        if (request.getParameter("fromUser") != null) {
            fromUser = userRepository.findByUnionid(request.getParameter("fromUser"));
        }

        return doAfterAct(entity, null, fromUser);
    }

    public T doAfterAct(T entity, Object linked) {
        User fromUser = null;
        if (request.getParameter("fromUser") != null) {
            fromUser = userRepository.findByUnionid(request.getParameter("fromUser"));
        }
        return doAfterAct(entity, linked, fromUser);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.liyang.service.IActable#doAct(T, A, com.liyang.domain.user.User)
     */
    public T doAfterAct(T entity, Object linked, User fromUser) {

        doActLog(entity, linked);

        if (!"delete".equals(entity.getLastAct().getActCode())) {
            timService.doNotice(fromUser, entity, entity.getLastAct());
        }
        return entity;
    }
}
