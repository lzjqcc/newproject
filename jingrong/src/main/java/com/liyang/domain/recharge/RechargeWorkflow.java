package com.liyang.domain.recharge;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="recharge_workflow")
@Cacheable
public class RechargeWorkflow extends AbstractWorkflow<Recharge, RechargeState> {



}
