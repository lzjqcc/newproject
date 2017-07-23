package com.liyang.domain.enterprise;

import com.liyang.domain.base.AbstractAuditorState;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "enterprise_state")
public class EnterpriseState extends AbstractAuditorState<Enterprise,EnterpriseAct> {

	public EnterpriseState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public EnterpriseState(){
		super();
	}

}
