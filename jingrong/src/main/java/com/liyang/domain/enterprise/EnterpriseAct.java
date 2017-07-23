package com.liyang.domain.enterprise;

import com.liyang.domain.base.AbstractAuditorAct;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "enterprise_act")
public class EnterpriseAct extends AbstractAuditorAct<EnterpriseState> {

	public EnterpriseAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public EnterpriseAct(){
		super();
	}

}
