package com.liyang.domain.department;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.IVisibility;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNode;


@Entity
@Table(name = "departments")
@Cacheable
public class Department extends AbstractAuditorEntity<DepartmentLog,DepartmentAct,DepartmentState> implements TreeNode<Department>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Transient
	private static ActRepository actRepository;
	
	@Transient
	private static LogRepository logRepository;


	@Enumerated(EnumType.STRING)
	private Type type;
	
	private Integer sort;
	
	@OneToMany(mappedBy = "department")
    private Set<User> employees;
	
	
	@ManyToMany
	@JoinTable(name="role_of_departments",joinColumns = { @JoinColumn(name = "department_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> departmentUseRoles;
	
	
//	@JsonBackReference(value="departmentTree")
	@ManyToOne
	private Department parent;
	
//	@JsonManagedReference(value="departmentTree")
	@OneToMany(mappedBy = "parent",fetch=FetchType.EAGER)  
	private List<Department> children = new ArrayList<Department>();
	
	@PostLoad
	public void postLoad() {
		System.out.print(this.getType());
		
	}
	
	
	
	public static enum Type{
		STORE,GROUP,COMPANY,CREDITOR,DEBTOR,LAW,DISTRIBUTOR;
	}



	public Type getType() {
		return type;
	}



	public void setType(Type type) {
		this.type = type;
	}



	public Set<User> getEmployees() {
		return employees;
	}



	public void setEmployees(Set<User> employees) {
		this.employees = employees;
	}




	public Set<Role> getDepartmentUseRoles() {
		return departmentUseRoles;
	}



	public void setDepartmentUseRoles(Set<Role> departmentUseRoles) {
		this.departmentUseRoles = departmentUseRoles;
	}



	public Department getParent() {
		return parent;
	}



	public void setParent(Department parent) {
		this.parent = parent;
	}



	public List<Department> getChildren() {
		return children;
	}



	public void setChildren(List<Department> children) {
		this.children = children;
	}



	@Override
	public Integer getSort() {
		// TODO Auto-generated method stub
		return sort;
	}



	@Override
	public void setSort(Integer sort) {
		this.sort = sort;
		
	}



	@Override
	public Integer getParentId() {
		// TODO Auto-generated method stub
		return parent == null?0:parent.getId();
	}



	@Override
	public void setParentId(Integer id) {
		// TODO Auto-generated method stub
		
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
		Department.actRepository = actRepository;
		
	}



	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorLog getLogInstance() {
		// TODO Auto-generated method stub
		return new DepartmentLog();
	}



	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		return logRepository;
	}



	@Override
	public void setLogRepository(LogRepository logRepository) {
		Department.logRepository = logRepository;
		
	}









}