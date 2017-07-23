package com.liyang.domain.accountinner;

import com.liyang.domain.base.AbstractAuditorAct;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accountinners_act")
public class AccouninnerAct extends AbstractAuditorAct<AccouninnerState> {

	public AccouninnerAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public AccouninnerAct(){
		super();
	}

}
