package com.liyang.domain.qcajs;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowState;

@Entity
@Table(name = "qcajs_state")
@Cacheable
public class QcajsState extends AbstractWorkflowState<Qcajs, QcajsWorkflow, QcajsAct> {
	public QcajsState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public QcajsState() {
		super();
	}

}
