package com.liyang.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.group.Group;
import com.liyang.domain.role.Role;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNode;
import com.liyang.util.TreeNodeImpl;

@Entity
@Table(name = "users")
@Cacheable
public class User extends AbstractWorkflowEntity<UserWorkflow, UserState, UserAct, UserLog> {

	@Transient
	private static ActRepository actRepository;

	@Transient
	private static LogRepository logRepository;

	private String nickname;
	private Integer sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private String openid;

	@Column(length = 50, unique = true)
	private String unionid;

	@Column(length = 500)
	private String sig;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@Transient
	@JsonIgnore
	private List<Department> childrenDepartments;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="user_groups",joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "group_id") })
	private Set<Group> groups;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getNickname()
	 */

	public String getNickname() {
		return nickname;
	}

	public List<Department> getChildrenDepartments() {
		return childrenDepartments;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public void setChildrenDepartments(List<Department> childrenDepartments) {
		this.childrenDepartments = childrenDepartments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getRole()
	 */
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getSig()
	 */
	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getSex()
	 */
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getProvince()
	 */
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getCity()
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getCountry()
	 */
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getHeadimgurl()
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getOpenid()
	 */
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getUnionid()
	 */
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.UserProjection#getDepartment()
	 */
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@JsonIgnore
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	@Override
	public void setActRepository(ActRepository actRepository) {
		User.actRepository = actRepository;

	}

	@Override
	@JsonIgnore
	public AbstractWorkflowLog getLogInstance() {
		// TODO Auto-generated method stub
		return new UserLog();
	}

	@Override
	@JsonIgnore
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		User.logRepository = logRepository;
	}



}