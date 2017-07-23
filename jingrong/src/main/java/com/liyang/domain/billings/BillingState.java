package com.liyang.domain.billings;

import com.liyang.domain.base.AbstractWorkflowState;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "billing_state")
@Cacheable
public class BillingState extends AbstractWorkflowState<Billing, BillingWorkflow, BillingAct> {

	public BillingState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public BillingState() {
		super();
	}

}
