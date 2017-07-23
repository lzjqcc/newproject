package com.liyang.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.group.Group;
import com.liyang.domain.group.GroupAct;
import com.liyang.domain.group.GroupActRepository;
import com.liyang.domain.group.GroupLog;
import com.liyang.domain.group.GroupLogRepository;
import com.liyang.domain.group.GroupRepository;
import com.liyang.domain.group.GroupState;
import com.liyang.domain.group.GroupStateRepository;
import com.liyang.domain.user.UserAct;


@Service
public class GroupService extends AbstractAuditorService<Group,GroupAct,GroupLog> {

	@Autowired
	GroupActRepository groupActRepository;
	
	@Autowired
	GroupStateRepository groupStateRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	GroupLogRepository groupLogRepository;

		
	@Override
	@PostConstruct 
	public void sqlInit() {
		List<GroupAct> findAll = groupActRepository.findAll();
		if(findAll == null || findAll.isEmpty()){
			
			GroupAct save1 = groupActRepository.save(new GroupAct("创建","create",10));
			GroupAct save2 = groupActRepository.save(new GroupAct("读取","read",20));
			GroupAct save3 = groupActRepository.save(new GroupAct("更新","update",30));
			GroupAct save4 = groupActRepository.save(new GroupAct("删除","delete",40));
			GroupAct save5 = groupActRepository.save(new GroupAct("自己的列表","listOwn",50));
			GroupAct save6 = groupActRepository.save(new GroupAct("部门的列表","listOwnDepartment",60));
			GroupAct save7 = groupActRepository.save(new GroupAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70));
			GroupAct save8 = groupActRepository.save(new GroupAct("手动通知","handNotice",80));
			GroupAct save9 = groupActRepository.save(new GroupAct("通知给操作者","noticeActUser",90));
			GroupAct save10 = groupActRepository.save(new GroupAct("通知给展示人","noticeShowUser",100));
			
	
			groupStateRepository.save(new GroupState("新建",0,"NEW"));
			groupStateRepository.save(new GroupState("申请",10,"APPLY"));
			GroupState groupState = new GroupState("有效",20,"ENABLE");
			groupState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7));
			groupStateRepository.save(groupState);
			groupStateRepository.save(new GroupState("无效",30,"DISABLE"));
			groupStateRepository.save(new GroupState("删除",40,"DELETE"));
		}
		
	}


	@Override
	public LogRepository<GroupLog> getLogRepository() {
		// TODO Auto-generated method stub
		return groupLogRepository ;
	}


	@Override
	public AuditorEntityRepository<Group> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return groupRepository;
	}


	@Override
	@PostConstruct 
	public void injectEntityActRepository() {
		new Group().setActRepository(groupActRepository);
		
	}


	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Group().setLogRepository(groupLogRepository);
		
	}


	@Override
	public GroupLog getLogInstance() {
		// TODO Auto-generated method stub
		return new GroupLog();
	}

}
