package com.liyang.domain.qcajs;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="qcajs_log")
@Cacheable
public class QcajsLog extends AbstractWorkflowLog<Qcajs,QcajsWorkflow,QcajsState,QcajsAct>  {


	
}
