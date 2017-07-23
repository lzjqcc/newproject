package com.liyang.domain.wdds;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="wdds_workflow")
@Cacheable
public class WddsWorkflow extends AbstractWorkflow<Wdds, WddsState> {



}
