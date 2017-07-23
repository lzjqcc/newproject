package com.liyang.domain.wdds;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="wdds_log")
@Cacheable
public class WddsLog extends AbstractWorkflowLog<Wdds,WddsWorkflow,WddsState,WddsAct>  {


	
}
