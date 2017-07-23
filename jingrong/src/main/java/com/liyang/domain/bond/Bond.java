package com.liyang.domain.bond;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 保证金
 * @author win7
 *
 */
@Entity
@Table(name = "bonds")
@Cacheable
public class Bond extends AbstractWorkflowEntity<BondWorkflow, BondState, BondAct, BondLog> {

	@Transient
	private static ActRepository actRepository;

	@Transient
	private static LogRepository logRepository;
	@Override
	@JsonIgnore
	public AbstractWorkflowLog getLogInstance() {
		// TODO Auto-generated method stub
		return new BondLog();
	}

	@Override
	@JsonIgnore
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Bond.logRepository = logRepository;
	}

	@Override
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	@Override
	public void setActRepository(ActRepository actRepository) {
		// TODO Auto-generated method stub
		this.actRepository=actRepository;
	}



}