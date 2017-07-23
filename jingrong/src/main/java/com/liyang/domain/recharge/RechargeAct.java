package com.liyang.domain.recharge;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="recharge_act")
@Cacheable
public class RechargeAct extends AbstractWorkflowAct<RechargeState,RechargeWorkflow> {

	public RechargeAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public RechargeAct(){
		super();
	}
	
}
