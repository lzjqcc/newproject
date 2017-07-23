package com.liyang.domain.qcdys;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "qcdys_state")
@Cacheable
public class QcdysState extends AbstractWorkflowState<Qcdys, QcdysWorkflow, QcdysAct> {
	public QcdysState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public QcdysState() {
		super();
	}

}
