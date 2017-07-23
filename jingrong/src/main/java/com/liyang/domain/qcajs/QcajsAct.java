package com.liyang.domain.qcajs;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="qcajs_act")
@Cacheable
public class QcajsAct extends AbstractWorkflowAct<QcajsState,QcajsWorkflow> {

	public QcajsAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public QcajsAct(){
		super();
	}
	
}
