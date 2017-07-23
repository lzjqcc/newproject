package com.liyang.domain.group;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;
@Entity
@Table(name = "group_state")
public class GroupState extends AbstractAuditorState<Group, GroupAct> {

	public GroupState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public GroupState(){
		super();
	}

}
