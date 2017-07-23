package com.liyang.domain.loanbiaps;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="loanbiaps_log")
@Cacheable
public class LoanbiapsLog extends AbstractWorkflowLog<Loanbiaps,LoanbiapsWorkflow,LoanbiapsState,LoanbiapsAct>  {


	
}
