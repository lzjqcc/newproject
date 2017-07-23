package com.liyang.domain.loanepeis;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="loanepeis_act")
@Cacheable
public class LoanepeisAct extends AbstractWorkflowAct<LoanepeisState,LoanepeisWorkflow> {

	public LoanepeisAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public LoanepeisAct(){
		super();
	}
	
}
