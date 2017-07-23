package com.liyang.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentAct;
import com.liyang.domain.department.DepartmentActRepository;
import com.liyang.domain.department.DepartmentLog;
import com.liyang.domain.department.DepartmentLogRepository;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.department.DepartmentState;
import com.liyang.domain.department.DepartmentStateRepository;
import com.liyang.domain.menu.MenuAct;
import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;

@Service
public class DepartmentService extends AbstractAuditorService<Department,DepartmentAct,DepartmentLog> {

	@Autowired
	DepartmentActRepository departmentActRepository;
	
	@Autowired
	DepartmentStateRepository departmentStateRepository;
	
	@Autowired
	DepartmentLogRepository  departmentLogRepository;
	
	@Autowired
	DepartmentRepository  departmentRepository;
	
	@Override
	@PostConstruct 
	public void sqlInit() {
		List<DepartmentAct> findAll = departmentActRepository.findAll();
		if(findAll == null || findAll.isEmpty()){
			
			DepartmentAct save1 = departmentActRepository.save(new DepartmentAct("创建","create",10));
			DepartmentAct save2 = departmentActRepository.save(new DepartmentAct("读取","read",20));
			DepartmentAct save3 = departmentActRepository.save(new DepartmentAct("更新","update",30));
			DepartmentAct save4 = departmentActRepository.save(new DepartmentAct("删除","delete",40));
			DepartmentAct save5 = departmentActRepository.save(new DepartmentAct("自己的列表","list",50));
			DepartmentAct save6 = departmentActRepository.save(new DepartmentAct("部门的列表","listOwnDepartment",60));
			DepartmentAct save7 = departmentActRepository.save(new DepartmentAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70));
			DepartmentAct save8 = departmentActRepository.save(new DepartmentAct("手动通知","handNotice",80));
			DepartmentAct save9 = departmentActRepository.save(new DepartmentAct("通知给操作者","noticeActUser",90));
			DepartmentAct save10 = departmentActRepository.save(new DepartmentAct("通知给展示人","noticeShowUser",100));
			
			
	
			departmentStateRepository.save(new DepartmentState("新建",0,"NEW"));
			departmentStateRepository.save(new DepartmentState("申请",10,"APPLY"));
			DepartmentState departmentState = new DepartmentState("有效",20,"ENABLE");
			departmentState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7));
			departmentStateRepository.save(departmentState);
			departmentStateRepository.save(new DepartmentState("无效",30,"DISABLE"));
			departmentStateRepository.save(new DepartmentState("删除",40,"DELETE"));
		}
		
	}

	@Override
	public LogRepository<DepartmentLog> getLogRepository() {
		// TODO Auto-generated method stub
		return departmentLogRepository;
	}

	@Override
	public AuditorEntityRepository<Department> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return departmentRepository;
	}

	@Override
	@PostConstruct 
	public void injectEntityActRepository() {
		new Department().setActRepository(departmentActRepository);
		
	}

	@Override
	@PostConstruct 
	public void injectLogRepository() {
		new Department().setLogRepository(departmentLogRepository);
		
	}

	@Override
	public DepartmentLog getLogInstance() {
		// TODO Auto-generated method stub
		return new DepartmentLog();
	}



}
