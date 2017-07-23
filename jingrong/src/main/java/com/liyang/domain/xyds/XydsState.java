package com.liyang.domain.xyds;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "xyds_state")
@Cacheable
public class XydsState extends AbstractWorkflowState<Xyds, XydsWorkflow, XydsAct> {

	public XydsState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public XydsState() {
		super();
	}

}
