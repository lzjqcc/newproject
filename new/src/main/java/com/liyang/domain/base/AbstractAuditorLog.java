package com.liyang.domain.base;

import javax.persistence.Cacheable;
import javax.persistence.ConstraintMode;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.domain.user.User;
@MappedSuperclass
@Cacheable
public abstract class AbstractAuditorLog<E extends AbstractAuditorEntity,A extends AbstractAuditorAct> extends BaseEntity {
	


	@ManyToOne
	@JoinColumn(name = "entity_id")
	private E entity;
	
	@Lob
	private String requestBody;
	
	@ManyToOne
	@JoinColumn(name = "act_id")
	private A act;
	
	
	@ManyToOne
	@JoinColumn(name="notice_to")
	private User noticeTo;
	

	public User getNoticeTo() {
		return noticeTo;
	}

	public void setNoticeTo(User user) {
		this.noticeTo = user;
	}


	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public A getAct() {
		return act;
	}

	public void setAct(A act) {
		this.act = act;
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractAuditorLogProjectiona#getEntity()
	 */
	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return "AbstractAuditorLog [getId()=" + getId() + "]";
	}
}
