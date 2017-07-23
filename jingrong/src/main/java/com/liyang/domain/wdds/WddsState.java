package com.liyang.domain.wdds;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "wdds_state")
@Cacheable
public class WddsState extends AbstractWorkflowState<Wdds, WddsWorkflow, WddsAct> {

	public WddsState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public WddsState() {
		super();
	}

}
