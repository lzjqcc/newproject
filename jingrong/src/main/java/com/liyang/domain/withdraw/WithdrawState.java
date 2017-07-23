package com.liyang.domain.withdraw;

import com.liyang.domain.base.AbstractWorkflowState;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "withdraw_state")
@Cacheable
public class WithdrawState extends AbstractWorkflowState<Withdraw, WithdrawWorkflow, WithdrawAct> {

	public WithdrawState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public WithdrawState() {
		super();
	}

}
