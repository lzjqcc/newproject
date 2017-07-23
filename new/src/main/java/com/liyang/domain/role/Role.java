package com.liyang.domain.role;

import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.menu.Menu;
import com.liyang.service.MenuService;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNode;
import com.liyang.util.TreeNodeImpl;

@Entity
@Table(name = "roles")
public class Role extends AbstractAuditorEntity<RoleLog, RoleAct, RoleState>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private static ActRepository actRepository;
	
	@Transient
	private static LogRepository logRepository;
	

	@Enumerated(EnumType.STRING)
	private RoleCode roleCode;

	@ManyToMany(mappedBy="visibleRoles")
	private Set<Menu> visibleMenus;


	@ManyToMany(mappedBy = "departmentUseRoles")
	private Set<Department> usedByDepartments;
	
	

	public enum RoleCode {
		DEVELOPER,
		ANONYMOUS, 
		USER, 
		BROKER,
		GROUP_OWNER,
		GROUP_MANAGER,
		GROUP_FINANCE,
		GROUP_ANALYST,
		GROUP_ARCHIVIST,
		COMPANY_OWNER,
		COMPANY_MANAGER,
		COMPANY_SERVICE_DIRECTOR,
		COMPANY_SERVICE,
		COMPANY_FINANCE_DIRECTOR,
		COMPANY_FINANCE,
		COMPANY_RISK_DIRECTOR,
		COMPANY_RISK,
		STORE_OWNER,
		STORE_MANAGER,
		STORE_SALESMAN,
		STORE_FINANCE,
		STORE_SERVICE,
		STORE_STAFF,
		DISTRIBUTOR_OWNER,
		DISTRIBUTOR_STAFF,
		AGENT_OWNER,
		AGENT_STAFF,
		CREDITOR_OWNER,
		CREDITOR_STAFF,
		TRUST_OWNER,
		TRUST_STAFF,
		LAW_OWNER,
		LAW_STAFF,
		HANDLE_OWNER,
		HANDLE_STAFF,
		DEBTOR_OWNER,
		DEBTOR_STAFF
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.RoleProjection#getRoleCode()
	 */
	public RoleCode getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(RoleCode roleCode) {
		this.roleCode = roleCode;
	}

	
	public  List<TreeNodeImpl> getVisibleMenuTree() {
		return  CommonUtil.listToTree(getVisibleMenus());
	}

	public Set<Menu> getVisibleMenus() {
		return visibleMenus;
	}

	public void setVisibleMenus(Set<Menu> visibleMenus) {
		this.visibleMenus = visibleMenus;
	}



	public Set<Department> getUsedByDepartments() {
		return usedByDepartments;
	}

	public void setUsedByDepartments(Set<Department> usedByDepartments) {
		this.usedByDepartments = usedByDepartments;
	}

	@Override
	@JsonIgnore
	@Transient
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	@Override
	public void setActRepository(ActRepository actRepository) {
		Role.actRepository = actRepository;
		
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorLog getLogInstance() {
		// TODO Auto-generated method stub
		return new RoleLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Role.logRepository = logRepository;
		
	}



	

}