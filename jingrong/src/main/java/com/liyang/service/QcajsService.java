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
import com.liyang.domain.qcajs.Qcajs;
import com.liyang.domain.qcajs.QcajsAct;
import com.liyang.domain.qcajs.QcajsActRepository;
import com.liyang.domain.qcajs.QcajsLog;
import com.liyang.domain.qcajs.QcajsLogRepository;
import com.liyang.domain.qcajs.QcajsRepository;
import com.liyang.domain.qcajs.QcajsState;
import com.liyang.domain.qcajs.QcajsStateRepository;
import com.liyang.domain.qcajs.QcajsWorkflow;
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
public class QcajsService extends AbstractWorkflowService<Qcajs,QcajsWorkflow, QcajsAct,QcajsState,QcajsLog> {
	
	@Autowired
	QcajsActRepository qcajsActRepository;
	
	@Autowired
	QcajsStateRepository qcajsStateRepository;
	@Autowired
	QcajsLogRepository qcajsLogRepository;
	@Autowired
	QcajsRepository  qcajsRepository;

	@Override
	public LogRepository<QcajsLog> getLogRepository() {
		// TODO Auto-generated method stub
		return qcajsLogRepository;
	}

	@Override
	public AuditorEntityRepository<Qcajs> getAuditorEntityRepository() {
		
		return qcajsRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityActRepository() {
		new User().setActRepository(qcajsActRepository);
	}

	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new User().setLogRepository(qcajsLogRepository);
		
	}

	@Override
	public QcajsLog getLogInstance() {
		// TODO Auto-generated method stub
		return new QcajsLog();
	}

	@Override
	public void sqlInit() {
		// TODO Auto-generated method stub
		
	}


}
