package com.liyang.domain.loanepeis;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="loanepeis_workflow")
@Cacheable
public class LoanepeisWorkflow extends AbstractWorkflow<Loanepeis, LoanepeisState> {



}
