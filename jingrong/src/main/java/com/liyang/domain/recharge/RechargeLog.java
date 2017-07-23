package com.liyang.domain.recharge;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="recharge_log")
@Cacheable
public class RechargeLog extends AbstractWorkflowLog<Recharge,RechargeWorkflow,RechargeState,RechargeAct>  {


	
}
