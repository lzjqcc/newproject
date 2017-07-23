package com.liyang.domain.billings;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="billing_log")
@Cacheable
public class BillingLog extends AbstractWorkflowLog<Billing,BillingWorkflow,BillingState,BillingAct>  {


	
}
