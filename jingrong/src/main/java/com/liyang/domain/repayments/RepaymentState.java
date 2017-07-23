package com.liyang.domain.repayments;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "repayment_state")
@Cacheable
public class RepaymentState extends AbstractWorkflowState<Repayments, RepaymentWorkflow, RepaymentAct> {

	public RepaymentState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public RepaymentState() {
		super();
	}

}
