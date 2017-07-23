package com.liyang.domain.menu;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.base.IVisibility;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.group.Group;
import com.liyang.domain.role.Role;
import com.liyang.util.CreateValidGroup;
import com.liyang.util.TreeNode;
import com.liyang.util.UpdateValidGroup;


@Entity
@Table(name = "menus")
public class Menu extends AbstractAuditorEntity<MenuLog,MenuAct,MenuState> implements IVisibility,TreeNode<Menu>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Transient
	private static ActRepository actRepository;
	
	@Transient
	private static LogRepository logRepository;
	
	@ManyToMany
	@JoinTable(name="menu_visible_roles",joinColumns = { @JoinColumn(name = "menu_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> visibleRoles;
	
	@NotNull(groups={UpdateValidGroup.class})
	private Integer sort;
	
	private Integer level;
	
	private String href;
	
	private String menuGroup;
	
	@ManyToOne
	private Menu parent;
	
	@OneToMany(mappedBy = "parent")  
	private List<Menu> children = new ArrayList<Menu>();

	@Transient
	private Integer parentId;



	public Integer getSort() {
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
	}



	public Integer getLevel() {
		return level;
	}



	public void setLevel(Integer level) {
		this.level = level;
	}



	public String getHref() {
		return href;
	}



	public void setHref(String href) {
		this.href = href;
	}



	public String getMenuGroup() {
		return menuGroup;
	}



	public void setMenuGroup(String menuGroup) {
		this.menuGroup = menuGroup;
	}



	public Menu getParent() {
		return parent;
	}



	public void setParent(Menu parent) {
		this.parent = parent;
	}



	public List<Menu> getChildren() {
		return children;
	}



	public void setChildren(List<Menu> children) {
		this.children = children;
	}



	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	@Transient
	public Integer getParentId() {
		return parent == null?0:parent.getId();
	}




	
	public Set<Role> getVisibleRoles() {
		// TODO Auto-generated method stub
		return visibleRoles;
	}



	
	public void setVisibleRoles(Set<Role> visibleRoles) {
		this.visibleRoles = visibleRoles;
		
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
		Menu.actRepository = actRepository;
		
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorLog getLogInstance() {
		// TODO Auto-generated method stub
		return new MenuLog();
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
		Menu.logRepository = logRepository;
		
	}
	


}