package com.liyang.domain.withdraw;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="withdraw_workflow")
@Cacheable
public class WithdrawWorkflow extends AbstractWorkflow<Withdraw, WithdrawState> {



}
