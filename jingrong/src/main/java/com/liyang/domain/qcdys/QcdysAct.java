package com.liyang.domain.qcdys;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="qcdys_act")
@Cacheable
public class QcdysAct extends AbstractWorkflowAct<QcdysState,QcdysWorkflow> {

	public QcdysAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public QcdysAct(){
		super();
	}
	
}
