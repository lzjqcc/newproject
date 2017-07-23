package com.liyang.domain.recharge;

import com.liyang.domain.base.AbstractWorkflowState;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "recharge_state")
@Cacheable
public class RechargeState extends AbstractWorkflowState<Recharge, RechargeWorkflow, RechargeAct> {

	public RechargeState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public RechargeState() {
		super();
	}

}
