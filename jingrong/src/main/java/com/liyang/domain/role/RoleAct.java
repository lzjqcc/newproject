package com.liyang.domain.role;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;
@Entity
@Table(name = "role_act")
public class RoleAct extends AbstractAuditorAct<RoleState> {

	public RoleAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public RoleAct(){
		super();
	}

}
