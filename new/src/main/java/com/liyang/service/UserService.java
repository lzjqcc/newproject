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

@Service
public class UserService extends AbstractWorkflowService<User,UserWorkflow, UserAct,UserState,UserLog> {
	
	@Autowired
	UserActRepository userActRepository;
	
	@Autowired
	UserStateRepository userStateRepository;
	
	@Autowired
	UserLogRepository  userLogRepository;
	
	@Autowired
	UserRepository  userRepository;
	
	
	
	
	
	@Override
	@PostConstruct 
	public void sqlInit() {
		List<UserAct> findAll = userActRepository.findAll();
		if(findAll == null || findAll.isEmpty()){
			
			UserAct save1 = userActRepository.save(new UserAct("创建","create",10));
			UserAct save2 = userActRepository.save(new UserAct("读取","read",20));
			UserAct save3 = userActRepository.save(new UserAct("更新","update",30));
			UserAct save4 = userActRepository.save(new UserAct("删除","delete",40));
			UserAct save5 = userActRepository.save(new UserAct("自己的列表","listOwn",50));
			UserAct save6 = userActRepository.save(new UserAct("部门的列表","listOwnDepartment",60));
			UserAct save7 = userActRepository.save(new UserAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70));
			UserAct save8 = userActRepository.save(new UserAct("手动通知","handNotice",80));
			UserAct save9 = userActRepository.save(new UserAct("通知给操作者","noticeActUser",90));
			UserAct save10 = userActRepository.save(new UserAct("通知给展示人","noticeShowUser",100));
			
	
			userStateRepository.save(new UserState("新建",0,"NEW"));
			userStateRepository.save(new UserState("申请",10,"APPLY"));
			UserState userState = new UserState("有效",20,"ENABLE");
			userState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7));
			userStateRepository.save(userState);
			userStateRepository.save(new UserState("无效",30,"DISABLE"));
			userStateRepository.save(new UserState("删除",40,"DELETE"));
		}
		
	}

	@Override
	public LogRepository<UserLog> getLogRepository() {
		// TODO Auto-generated method stub
		return userLogRepository;
	}

	@Override
	public AuditorEntityRepository<User> getAuditorEntityRepository() {
		
		return userRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityActRepository() {
		new User().setActRepository(userActRepository);
	}

	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new User().setLogRepository(userLogRepository);
		
	}

	@Override
	public UserLog getLogInstance() {
		// TODO Auto-generated method stub
		return new UserLog();
	}


}
