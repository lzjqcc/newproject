package com.liyang.domain.asset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 资产
 */
@Entity
@Table(name = "assets")
public class Asset extends AbstractAuditorEntity<AssetLog, AssetAct, AssetState>{


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
		Asset.actRepository = actRepository;
		
	}

	@Override
	@JsonIgnore
	@Transient
	public AbstractAuditorLog getLogInstance() {
		// TODO Auto-generated method stub
		return new AssetLog();
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
		Asset.logRepository = logRepository;
		
	}



	

}