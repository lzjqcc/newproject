package com.liyang.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.DepartmentActRepository;
import com.liyang.domain.department.DepartmentLogRepository;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.department.DepartmentStateRepository;
import com.liyang.domain.role.RoleAct;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserActRepository;
import com.liyang.domain.user.UserLog;
import com.liyang.domain.user.UserLogRepository;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.domain.user.UserWorkflow;
import com.liyang.domain.wdds.Wdds;
import com.liyang.domain.wdds.WddsAct;
import com.liyang.domain.wdds.WddsActRepository;
import com.liyang.domain.wdds.WddsLog;
import com.liyang.domain.wdds.WddsLogRepository;
import com.liyang.domain.wdds.WddsRepository;
import com.liyang.domain.wdds.WddsState;
import com.liyang.domain.wdds.WddsStateRepository;
import com.liyang.domain.wdds.WddsWorkflow;

@Service
public class WddsService extends AbstractWorkflowService<Wdds,WddsWorkflow, WddsAct,WddsState,WddsLog> {
	
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
