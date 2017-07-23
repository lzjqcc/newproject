package com.liyang.domain.loanbiaps;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="loanbiaps_workflow")
@Cacheable
public class LoanbiapsWorkflow extends AbstractWorkflow<Loanbiaps, LoanbiapsState> {



}
