package com.liyang.domain.qcdys;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="qcdys_log")
@Cacheable
public class QcdysLog extends AbstractWorkflowLog<Qcdys,QcdysWorkflow,QcdysState,QcdysAct>  {


	
}
