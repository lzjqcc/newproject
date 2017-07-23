package com.liyang.domain.withdraw;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="withdraw_log")
@Cacheable
public class WithdrawLog extends AbstractWorkflowLog<Withdraw,WithdrawWorkflow,WithdrawState,WithdrawAct>  {


	
}
