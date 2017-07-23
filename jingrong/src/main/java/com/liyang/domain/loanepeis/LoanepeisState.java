package com.liyang.domain.loanepeis;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "loanepeis_state")
@Cacheable
public class LoanepeisState extends AbstractWorkflowState<Loanepeis, LoanepeisWorkflow, LoanepeisAct> {

	public LoanepeisState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public LoanepeisState() {
		super();
	}

}
