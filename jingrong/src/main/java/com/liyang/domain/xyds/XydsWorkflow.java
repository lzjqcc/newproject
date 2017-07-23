package com.liyang.domain.xyds;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="xyds_workflow")
@Cacheable
public class XydsWorkflow extends AbstractWorkflow<Xyds, XydsState> {



}
