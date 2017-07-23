package com.liyang.domain.bond;

import com.liyang.domain.base.AbstractWorkflowState;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bond_state")
@Cacheable
public class BondState extends AbstractWorkflowState<Bond, BondWorkflow, BondAct> {

	public BondState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public BondState() {
		super();
	}

}
