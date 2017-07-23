package com.liyang.domain.repayments;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflowLog;


@Entity
@Table(name="repayment_log")
@Cacheable
public class RepaymentLog extends AbstractWorkflowLog<Repayments,RepaymentWorkflow,RepaymentState,RepaymentAct>  {


	
}
