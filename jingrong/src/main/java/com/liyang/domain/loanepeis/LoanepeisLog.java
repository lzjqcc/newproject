package com.liyang.domain.loanepeis;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="loanepeis_log")
@Cacheable
public class LoanepeisLog extends AbstractWorkflowLog<Loanepeis,LoanepeisWorkflow,LoanepeisState,LoanepeisAct>  {


	
}
