package com.liyang.domain.loanbiaps;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="loanbiaps_act")
@Cacheable
public class LoanbiapsAct extends AbstractWorkflowAct<LoanbiapsState,LoanbiapsWorkflow> {

	public LoanbiapsAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public LoanbiapsAct(){
		super();
	}
	
}
