package com.liyang.domain.group;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.BaseEntity;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.menu.MenuAct;
import com.liyang.domain.menu.MenuLog;
import com.liyang.domain.menu.MenuState;
@Entity
@Table(name = "groups")
public class Group extends AbstractAuditorEntity<GroupLog,GroupAct,GroupState>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Transient
	private static ActRepository actRepository;
	
	@Transient
	private static LogRepository logRepository;
	
	
	private String ownerAccount;
	private String name;
	@Enumerated(EnumType.STRING)
	private Type type;
	private String groupId;
	private String introduction;
	private String notification;
	private String faceUrl;
	private Integer maxMemberCount;
	@Enumerated(EnumType.STRING)
	private Option applyJoinOption;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getFaceUrl() {
		return faceUrl;
	}

	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}

	public Integer getMaxMemberCount() {
		return maxMemberCount;
	}

	public void setMaxMemberCount(Integer maxMemberCount) {
		this.maxMemberCount = maxMemberCount;
	}

	public Option getApplyJoinOption() {
		return applyJoinOption;
	}

	public void setApplyJoinOption(Option applyJoinOption) {
		this.applyJoinOption = applyJoinOption;
	}

	public String getOwnerAccount() {
		return ownerAccount;
	}

	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type{
		Private,Public,ChatRoom,AVChatRoom,BChatRoom
	}
	public enum Option{
		FreeAccess,NeedPermission,DisableApply
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
		Group.actRepository = actRepository;
		
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorLog getLogInstance() {
		// TODO Auto-generated method stub
		return new GroupLog();
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
		Group.logRepository = logRepository;
		
	}
	
}
