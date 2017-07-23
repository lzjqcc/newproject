package com.liyang.domain.billings;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="billing_workflow")
@Cacheable
public class BillingWorkflow extends AbstractWorkflow<Billing, BillingState> {



}
