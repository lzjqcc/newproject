package com.liyang.domain.xyds;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="xyds_log")
@Cacheable
public class XydsLog extends AbstractWorkflowLog<Xyds,XydsWorkflow,XydsState,XydsAct>  {


	
}
