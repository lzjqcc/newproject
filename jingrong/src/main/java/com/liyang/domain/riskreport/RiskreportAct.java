package com.liyang.domain.riskreport;

import com.liyang.domain.base.AbstractAuditorAct;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "riskreport_act")
public class RiskreportAct extends AbstractAuditorAct<RiskreportState> {

	public RiskreportAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public RiskreportAct(){
		super();
	}

}
