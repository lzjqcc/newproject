package com.liyang.domain.bond;

import com.liyang.domain.base.AbstractWorkflowAct;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="bond_act")
@Cacheable
public class BondAct extends AbstractWorkflowAct<BondState,BondWorkflow> {

	public BondAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public BondAct(){
		super();
	}
	
}
