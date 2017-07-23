package com.liyang.domain.group;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;
@Entity
@Table(name = "group_act")
public class GroupAct extends AbstractAuditorAct<GroupState> {

	public GroupAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public GroupAct(){
		super();
	}

}
