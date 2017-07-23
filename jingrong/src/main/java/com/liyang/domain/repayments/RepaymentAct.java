package com.liyang.domain.repayments;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="repayment_act")
@Cacheable
public class RepaymentAct extends AbstractWorkflowAct<RepaymentState,RepaymentWorkflow> {

	public RepaymentAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public RepaymentAct(){
		super();
	}
	
}
