package com.liyang.domain.withdraw;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="withdraw_act")
@Cacheable
public class WithdrawAct extends AbstractWorkflowAct<WithdrawState,WithdrawWorkflow> {

	public WithdrawAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public WithdrawAct(){
		super();
	}
	
}
