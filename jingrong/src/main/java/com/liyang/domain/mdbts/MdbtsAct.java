package com.liyang.domain.mdbts;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="mdbts_act")
@Cacheable
public class MdbtsAct extends AbstractWorkflowAct<MdbtsState,MdbtsWorkflow> {

	public MdbtsAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public MdbtsAct(){
		super();
	}
	
}
