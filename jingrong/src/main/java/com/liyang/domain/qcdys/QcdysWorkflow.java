package com.liyang.domain.qcdys;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="qcdys_workflow")
@Cacheable
public class QcdysWorkflow extends AbstractWorkflow<Qcdys, QcdysState> {



}
