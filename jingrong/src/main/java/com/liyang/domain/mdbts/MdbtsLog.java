package com.liyang.domain.mdbts;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="mdbts_log")
@Cacheable
public class MdbtsLog extends AbstractWorkflowLog<Mdbts,MdbtsWorkflow,MdbtsState,MdbtsAct>  {


	
}
