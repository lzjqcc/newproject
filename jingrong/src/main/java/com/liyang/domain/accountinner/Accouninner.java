package com.liyang.domain.accountinner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.menu.Menu;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNodeImpl;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 内部账户
 */
@Entity
@Table(name = "accountinners")
public class Accouninner extends AbstractAuditorEntity<AccouninnerLog, AccouninnerAct, AccouninnerState>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private static ActRepository actRepository;
	
	@Transient
	private static LogRepository logRepository;
	



	@Override
	@JsonIgnore
	@Transient
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	@Override
	public void setActRepository(ActRepository actRepository) {
		Accouninner.actRepository = actRepository;
		
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorLog getLogInstance() {
		// TODO Auto-generated method stub
		return new AccouninnerLog();
	}

	@Override
	@JsonIgnore
	@Transient
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Accouninner.logRepository = logRepository;
		
	}



	

}