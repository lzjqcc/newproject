package com.liyang.domain.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.role.Role;
import com.liyang.message.EnumOperationMessageType;
@MappedSuperclass
@Cacheable
public class AbstractAuditorAct<S extends AbstractAuditorState> extends BaseEntity implements Comparable<AbstractAuditorAct>{
	
	public AbstractAuditorAct(String label, String actCode, Integer sort) {
		super();
		this.actCode = actCode;
		this.sort = sort;
		this.setLabel(label);
	}

	public AbstractAuditorAct(){
		super();
	}
	
	@Enumerated(EnumType.STRING)
	private FirstNoticeType firstNotice;
	
	private Boolean handNotice=false;
	
	@Lob
	private String noticeTemplate;
	

	@ManyToMany(mappedBy="acts")
	private Set<S> states;
	
	private String actCode;

	private Integer sort=0;
	
	@Enumerated(EnumType.STRING)
	private EnumOperationMessageType messageType;
	
	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "act_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>();

	

	public String getNoticeTemplate() {
		return noticeTemplate;
	}

	public void setNoticeTemplate(String noticeTemplate) {
		this.noticeTemplate = noticeTemplate;
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getSort()
	 */
	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}

	

	public String getActCode() {
		return actCode;
	}


	public void setActCode(String actCode) {
		this.actCode = actCode;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public Set<S> getStates() {
		return states;
	}


	public void setStates(Set<S> states) {
		this.states = states;
	}


	public EnumOperationMessageType getMessageType() {
		return messageType;
	}


	public void setMessageType(EnumOperationMessageType messageType) {
		this.messageType = messageType;
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowActProjection#getFirstNotice()
	 */
	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getFirstNotice()
	 */
	public FirstNoticeType getFirstNotice() {
		return firstNotice;
	}

	public void setFirstNotice(FirstNoticeType firstNotice) {
		this.firstNotice = firstNotice;
	}


	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowActProjection#getHandNotice()
	 */
	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getHandNotice()
	 */
	public Boolean getHandNotice() {
		return handNotice;
	}

	public void setHandNotice(Boolean handNotice) {
		this.handNotice = handNotice;
	}

	@Override
	public int compareTo(AbstractAuditorAct o) {
		// TODO Auto-generated method stub
		if(getSort() > o.getSort()){
			return 1;
		}else if(getSort() == o.getSort()){
			return 0;
		}else{
			return -1;
		}
	}


	public static enum FirstNoticeType{
		SELF,SHOW_USER,SELF_AND_SHOW_USER
	}


	@Override
	public String toString() {
		return "AbstractAuditorAct [getId()=" + getId() + "]";
	}
	



}
