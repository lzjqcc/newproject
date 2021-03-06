package com.liyang.domain.base;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Ignore;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.domain.menu.MenuActRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@MappedSuperclass
public abstract class AbstractAuditorEntity<L extends AbstractAuditorLog, A extends AbstractAuditorAct, S extends AbstractAuditorState>
		extends BaseEntity {
	@Transient
	public abstract ActRepository getActRepository();

	public abstract void setActRepository(ActRepository actRepository);

	@Transient
	public abstract AbstractAuditorLog getLogInstance();

	@Transient
	public abstract LogRepository getLogRepository();

	public abstract void setLogRepository(LogRepository logRepository);

	@OneToMany(mappedBy = "entity", cascade = CascadeType.ALL)
	private Set<L> logs = new HashSet<L>();

	@ManyToOne
	@JoinColumn(name = "state_id")
	private S state;

	@Transient
	private String act;

	@Transient
	private Set<String> notice;

	@Transient
	@JsonIgnore
	private Map map;

	@Transient
	@JsonIgnore
	private Set linkedSet;

	@ManyToOne
	private A lastAct;

	public Set getLinkedSet() {
		return linkedSet;
	}

	public void setupLinkedSet(String property) {
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(this);
		if (beanWrapperImpl.isReadableProperty(property)) {
			Object propertyValue = beanWrapperImpl.getPropertyValue(property);
			if (propertyValue != null) {
				Set newSet = (Set) propertyValue;
				Object set1 = newSet.stream().map(e -> new B(((BaseEntity) e).getId(), ((BaseEntity) e).getLabel()))
						.collect(Collectors.toSet());

				this.linkedSet = (Set) set1;
			}
		}

	}

	@Transient
	public Map getMap() {
		return this.map;
	}

	public void setupMap() {
		if (this.map == null) {
			this.map = CommonUtil.transBean2Map(this);
		}
	}

	public Set<L> getLogs() {
		return logs;
	}

	public void setLogs(Set<L> logs) {
		this.logs = logs;
	}

	public S getState() {
		return state;
	}

	public void setState(S state) {
		this.state = state;
	}

	@Transient
	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.AbstractEntityProjection#getLastAct()
	 */
	public A getLastAct() {
		return lastAct;
	}

	public void setLastAct(A lastAct) {
		this.lastAct = lastAct;
	}

	@Transient
	public Set<String> getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		List<String> asList = Arrays.asList(notice.split(","));
		Set<String> set = new HashSet<String>();
		set.addAll(asList);
		this.notice = set;
	}

	@Transient
	public List<A> getCurrentUserCanActList() {
		System.out.println(getState());
		getState();
		// return (List<A>) CommonUtil.currentUserCanActList(this);
		//获取当前实体在当前状态拥有的动作
		List<A> acts = getState().getActs();
		Role.RoleCode roleCode = CommonUtil.getCurrentUserRoleCode();
		if (acts == null) {
			return new ArrayList<A>();
		}
		List<Integer> ids = acts.stream().map(a -> a.getId()).collect(Collectors.toList());

		List<A> rets = getActRepository().findByIdInAndRolesRoleCode(ids, roleCode);

		return rets;
	}

	private static class B {
		private Integer id;
		private String label;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public B(Integer id, String label) {
			super();
			this.id = id;
			this.label = label;
		}

	}

}
