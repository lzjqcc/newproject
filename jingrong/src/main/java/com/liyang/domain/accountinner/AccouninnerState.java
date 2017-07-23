package com.liyang.domain.accountinner;

import com.liyang.domain.base.AbstractAuditorState;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accountinners_state")
public class AccouninnerState extends AbstractAuditorState<Accouninner,AccouninnerAct> {

	public AccouninnerState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public AccouninnerState(){
		super();
	}

}
