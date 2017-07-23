package com.liyang.domain.bond;

import com.liyang.domain.base.AbstractWorkflow;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bond_workflow")
@Cacheable
public class BondWorkflow extends AbstractWorkflow<Bond, BondState> {



}
