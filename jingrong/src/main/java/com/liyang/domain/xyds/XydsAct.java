package com.liyang.domain.xyds;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowAct;


@Entity
@Table(name="xyds_act")
@Cacheable
public class XydsAct extends AbstractWorkflowAct<XydsState,XydsWorkflow> {

	public XydsAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public XydsAct(){
		super();
	}
	
}
