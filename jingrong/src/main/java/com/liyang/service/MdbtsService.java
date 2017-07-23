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
import com.liyang.domain.mdbts.Mdbts;
import com.liyang.domain.mdbts.MdbtsAct;
import com.liyang.domain.mdbts.MdbtsActRepository;
import com.liyang.domain.mdbts.MdbtsLog;
import com.liyang.domain.mdbts.MdbtsLogRepository;
import com.liyang.domain.mdbts.MdbtsRepository;
import com.liyang.domain.mdbts.MdbtsState;
import com.liyang.domain.mdbts.MdbtsStateRepository;
import com.liyang.domain.mdbts.MdbtsWorkflow;
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
public class MdbtsService extends AbstractWorkflowService<Mdbts,MdbtsWorkflow, MdbtsAct,MdbtsState,MdbtsLog> {
	
	@Autowired
	MdbtsActRepository mdbtsActRepository;
	
	@Autowired
	MdbtsStateRepository mdbtsStateRepository;
	
	@Autowired
	MdbtsLogRepository  mdbtsLogRepository;
	
	@Autowired
	MdbtsRepository  mdbtsRepository;

	@Override
	public LogRepository<MdbtsLog> getLogRepository() {
		// TODO Auto-generated method stub
		return mdbtsLogRepository;
	}

	@Override
	public AuditorEntityRepository<Mdbts> getAuditorEntityRepository() {
		
		return mdbtsRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityActRepository() {
		new User().setActRepository(mdbtsActRepository);
	}

	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new User().setLogRepository(mdbtsLogRepository);
		
	}

	@Override
	public MdbtsLog getLogInstance() {
		// TODO Auto-generated method stub
		return new MdbtsLog();
	}

	@Override
	public void sqlInit() {
		// TODO Auto-generated method stub
		
	}


}
