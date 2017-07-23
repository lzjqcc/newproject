package com.liyang.domain.mdbts;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="mdbts_workflow")
@Cacheable
public class MdbtsWorkflow extends AbstractWorkflow<Mdbts, MdbtsState> {



}
