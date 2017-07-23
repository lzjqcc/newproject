package com.liyang.domain.loanbiaps;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "loanbiaps_state")
@Cacheable
public class LoanbiapsState extends AbstractWorkflowState<Loanbiaps, LoanbiapsWorkflow, LoanbiapsAct> {

	public LoanbiapsState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public LoanbiapsState() {
		super();
	}

}
