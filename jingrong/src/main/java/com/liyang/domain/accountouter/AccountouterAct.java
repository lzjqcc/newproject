package com.liyang.domain.accountouter;

import com.liyang.domain.base.AbstractAuditorAct;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "accountouter_act")
public class AccountouterAct extends AbstractAuditorAct<AccountouterState> {

	public AccountouterAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public AccountouterAct(){
		super();
	}

}
