package com.liyang.domain.riskreport;

import com.liyang.domain.base.AbstractAuditorState;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "riskreport_state")
public class RiskreportState extends AbstractAuditorState<Riskreport,RiskreportAct> {

	public RiskreportState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public RiskreportState(){
		super();
	}

}
