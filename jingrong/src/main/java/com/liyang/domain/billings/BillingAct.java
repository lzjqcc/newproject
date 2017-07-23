package com.liyang.domain.billings;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="billing_act")
@Cacheable
public class BillingAct extends AbstractWorkflowAct<BillingState,BillingWorkflow> {

	public BillingAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public BillingAct(){
		super();
	}
	
}
