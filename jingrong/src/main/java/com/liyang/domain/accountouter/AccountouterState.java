package com.liyang.domain.accountouter;

import com.liyang.domain.base.AbstractAuditorState;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accountouter_state")
public class AccountouterState extends AbstractAuditorState<Accountouter,AccountouterAct> {

	public AccountouterState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public AccountouterState(){
		super();
	}

}
