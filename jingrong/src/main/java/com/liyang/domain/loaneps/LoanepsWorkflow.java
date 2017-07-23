package com.liyang.domain.loaneps;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="loaneps_workflow")
@Cacheable
public class LoanepsWorkflow extends AbstractWorkflow<Loaneps, LoanepsState> {



}
