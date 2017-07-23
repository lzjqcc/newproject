package com.liyang.domain.loaneps;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="loaneps_act")
@Cacheable
public class LoanepsAct extends AbstractWorkflowAct<LoanepsState,LoanepsWorkflow> {

	public LoanepsAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public LoanepsAct(){
		super();
	}
	
}
