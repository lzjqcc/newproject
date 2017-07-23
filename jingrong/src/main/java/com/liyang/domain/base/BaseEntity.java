package com.liyang.domain.base;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.department.Department;
import com.liyang.domain.user.User;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.CommonUtil;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	
	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "createdBy")
	private User createdBy;

	@ManyToOne
	private Department createdByDepartment;

	@LastModifiedBy
	@ManyToOne
	@JoinColumn(name = "lastModifiedBy")
	private User lastModifiedBy;

	@CreatedDate
	private Date createdAt;

	@LastModifiedDate
	private Date lastModifiedAt;

	private String label;

	
	public Department getCreatedByDepartment() {
		return createdByDepartment;
	}

	public void setCreatedByDepartment(Department createdByDepartment) {
		this.createdByDepartment = createdByDepartment;
	}



	@PrePersist
	public void prePersist() {

		User createdByUser = CommonUtil.getPrincipal();
		if (createdByUser != null) {
			this.createdByDepartment = createdByUser.getDepartment();
		}
	}



	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public Integer getId() {
	
		return id;
	}

	public void setId(Integer id) {
	
		this.id = id;
	}


	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}


	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	@Override
	public String toString() {
		return "{\"id\"=\"" + id + "\", \"label\"=\"" + label + "\"}";
	}


}
