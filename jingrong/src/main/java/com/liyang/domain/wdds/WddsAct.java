package com.liyang.domain.wdds;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="wdds_act")
@Cacheable
public class WddsAct extends AbstractWorkflowAct<WddsState,WddsWorkflow> {

	public WddsAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public WddsAct(){
		super();
	}
	
}
