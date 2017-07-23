package com.liyang.domain.exception;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyang.domain.base.BaseEntity;
import com.liyang.util.ReturnObject;
@Entity
@Table(name = "exceptions")
public class Exception extends BaseEntity implements ReturnObject {

	private String actionStatus;
	@Lob
	private String errorInfo;
	private Integer errorCode;
	public String getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	@Transient
	public Integer getMsgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setMsgTime(Integer i) {
		// TODO Auto-generated method stub
		
	}
	@Override
	@JsonIgnore
	public Level getLevel() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLevel(Level level) {
		// TODO Auto-generated method stub
		
	}

}
