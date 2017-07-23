package com.liyang.service;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.wdds.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RechargeService extends AbstractWorkflowService<Wdds,WddsWorkflow, WddsAct,WddsState,WddsLog> {
	
	@Autowired
	WddsActRepository wddsActRepository;
	
	@Autowired
	WddsStateRepository wddsStateRepository;
	
	@Autowired
	WddsLogRepository  wddsLogRepository;
	
	@Autowired
	WddsRepository  wddsRepository;

	@Override
	public LogRepository<WddsLog> getLogRepository() {
		// TODO Auto-generated method stub
		return wddsLogRepository;
	}

	@Override
	public AuditorEntityRepository<Wdds> getAuditorEntityRepository() {
		
		return wddsRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityActRepository() {
		new User().setActRepository(wddsActRepository);
	}

	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new User().setLogRepository(wddsLogRepository);
		
	}

	@Override
	public WddsLog getLogInstance() {
		// TODO Auto-generated method stub
		return new WddsLog();
	}

	@Override
	public void sqlInit() {
		// TODO Auto-generated method stub
		
	}


}
