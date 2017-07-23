package com.liyang.domain.bond;

import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="bond_log")
@Cacheable
public class BondLog extends AbstractWorkflowLog<Bond,BondWorkflow,BondState,BondAct>  {


	
}
