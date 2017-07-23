package com.liyang.domain.repayments;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="repayment_workflow")
@Cacheable
public class RepaymentWorkflow extends AbstractWorkflow<Repayments, RepaymentState> {



}
