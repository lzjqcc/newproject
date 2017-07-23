package com.liyang.domain.qcajs;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractWorkflow;

@Entity
@Table(name="qcajs_workflow")
@Cacheable
public class QcajsWorkflow extends AbstractWorkflow<Qcajs, QcajsState> {



}
