package com.liyang.domain.loaneps;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="loaneps_log")
@Cacheable
public class LoanepsLog extends AbstractWorkflowLog<Loaneps,LoanepsWorkflow,LoanepsState,LoanepsAct>  {


	
}
