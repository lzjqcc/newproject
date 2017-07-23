package com.liyang.service;

import java.io.Serializable;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

@Component
public class MyPermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TIMService timService;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		// TODO Auto-generated method stub
		AbstractAuditorEntity e = null;
		if (targetDomainObject instanceof PageImpl) {
			if (((PageImpl) targetDomainObject).hasContent()) {
				Object o = ((PageImpl) targetDomainObject).getContent().get(0);
				e = (AbstractAuditorEntity) o;
			}
		} else if (targetDomainObject != null) {
			e = (AbstractAuditorEntity) targetDomainObject;
		}
		if (e == null) {
			return true;
		} else {
			String actStr = permission.toString();
			if ("read".equals(permission.toString()) && request.getParameter("act") != null) {
				actStr = request.getParameter("act").trim();
			}
			AbstractAuditorAct act = e.getActRepository().findByActCode(actStr);
			if (act == null) {
				throw new FailReturnObject(1200, "没有设置动作" + actStr);
			} else {

				if (request.getParameter("notice") != null && !request.getParameter("notice").trim().equals("")) {
					e.setNotice(request.getParameter("notice"));
				}

				Set<Role> roles = (Set<Role>) act.getRoles();
				String roleCode = authentication.getAuthorities().toArray()[0].toString();
				for (Role role : roles) {
					if (role.getRoleCode().toString().equals(roleCode)) {
						User fromUser = null;
						if (request.getParameter("fromUser") != null) {
							fromUser = userRepository.findByUnionid(request.getParameter("fromUser"));
						}
						timService.doNotice(fromUser, e, act);
						if ("read".equals(permission.toString())) {
							if (targetDomainObject instanceof AbstractAuditorEntity) {
								
								((AbstractAuditorEntity) targetDomainObject).setMap();
							}
							if (targetDomainObject instanceof AbstractWorkflowEntity) {

								((AbstractWorkflowEntity) targetDomainObject).injectStateActList();
							}
						}
						return true;
					}
				}
				throw new FailReturnObject(1240, "用户不允许" + act.getActCode() + ":" + ((AbstractAuditorEntity) targetDomainObject).getClass().getSimpleName());
			}
		}
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

}
