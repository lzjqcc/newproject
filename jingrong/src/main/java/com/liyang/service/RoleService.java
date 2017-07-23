package com.liyang.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.DepartmentAct;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleAct;
import com.liyang.domain.role.RoleActRepository;
import com.liyang.domain.role.RoleLog;
import com.liyang.domain.role.RoleLogRepository;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.role.RoleState;
import com.liyang.domain.role.RoleStateRepository;

@Service
public class RoleService extends AbstractAuditorService<Role,RoleAct,RoleLog> {

	@Autowired
	RoleActRepository roleActRepository;
	
	@Autowired
	RoleStateRepository roleStateRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	RoleLogRepository roleLogRepository;
	
	@Override
	@PostConstruct 
	public void sqlInit() {
		List<RoleAct> findAll = roleActRepository.findAll();
		if(findAll == null || findAll.isEmpty()){
			
			
			for(Role.RoleCode roleCode : Role.RoleCode.values()){
				Role role = new Role();
				role.setRoleCode(roleCode);
				roleRepository.save(role);
			}
			
			
			RoleAct save1 = roleActRepository.save(new RoleAct("创建","create",10));
			RoleAct save2 = roleActRepository.save(new RoleAct("读取","read",20));
			RoleAct save3 = roleActRepository.save(new RoleAct("更新","update",30));
			RoleAct save4 = roleActRepository.save(new RoleAct("删除","delete",40));
			RoleAct save5 = roleActRepository.save(new RoleAct("自己的列表","listOwn",50));
			RoleAct save6 = roleActRepository.save(new RoleAct("部门的列表","listOwnDepartment",60));
			RoleAct save7 = roleActRepository.save(new RoleAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70));
			RoleAct save8 = roleActRepository.save(new RoleAct("手动通知","handNotice",80));
			RoleAct save9 = roleActRepository.save(new RoleAct("通知给操作者","noticeActUser",90));
			RoleAct save10 = roleActRepository.save(new RoleAct("通知给展示人","noticeShowUser",100));
			
	
			roleStateRepository.save(new RoleState("新建",0,"NEW"));
			roleStateRepository.save(new RoleState("申请",10,"APPLY"));
			RoleState roleState = new RoleState("有效",20,"ENABLE");
			roleState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7));
			roleStateRepository.save(roleState);
			roleStateRepository.save(new RoleState("无效",30,"DISABLE"));
			roleStateRepository.save(new RoleState("删除",40,"DELETE"));
		}
		
	}

	@Override
	public LogRepository<RoleLog> getLogRepository() {
		// TODO Auto-generated method stub
		return roleLogRepository;
	}



	@Override
	public AuditorEntityRepository<Role> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return roleRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityActRepository() {
		new Role().setActRepository(roleActRepository);
		
	}

	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new Role().setLogRepository(roleLogRepository);
		
	}

	@Override
	public RoleLog getLogInstance() {
		// TODO Auto-generated method stub
		return new RoleLog();
	}

}
