package com.liyang.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.menu.MenuAct;
import com.liyang.domain.menu.MenuActRepository;
import com.liyang.domain.menu.MenuLog;
import com.liyang.domain.menu.MenuLogRepository;
import com.liyang.domain.menu.MenuRepository;
import com.liyang.domain.menu.MenuState;
import com.liyang.domain.menu.MenuStateRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.Role.RoleCode;
import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNode;

@Service
public class MenuService extends AbstractAuditorService<Menu,MenuAct,MenuLog> {

	@Autowired
	MenuActRepository menuActRepository;
	
	@Autowired
	MenuStateRepository menuStateRepository;
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	MenuLogRepository menuLogRepository;

		
	@Override
	@PostConstruct 
	public void sqlInit() {
		List<MenuAct> findAll = menuActRepository.findAll();
		if(findAll == null || findAll.isEmpty()){
			
			MenuAct save1 = menuActRepository.save(new MenuAct("创建","create",10));
			MenuAct save2 = menuActRepository.save(new MenuAct("读取","read",20));
			MenuAct save3 = menuActRepository.save(new MenuAct("更新","update",30));
			MenuAct save4 = menuActRepository.save(new MenuAct("删除","delete",40));
			MenuAct save5 = menuActRepository.save(new MenuAct("自己的列表","listOwn",50));
			MenuAct save6 = menuActRepository.save(new MenuAct("部门的列表","listOwnDepartment",60));
			MenuAct save7 = menuActRepository.save(new MenuAct("部门和下属部门的列表","listOwnDepartmentAndChildren",70));
			MenuAct save8 = menuActRepository.save(new MenuAct("手动通知","handNotice",80));
			MenuAct save9 = menuActRepository.save(new MenuAct("通知给操作者","noticeActUser",90));
			MenuAct save10 = menuActRepository.save(new MenuAct("通知给展示人","noticeShowUser",100));
			
			
	
			menuStateRepository.save(new MenuState("新建",0,"NEW"));
			menuStateRepository.save(new MenuState("申请",10,"APPLY"));
			MenuState menuState = new MenuState("有效",20,"ENABLE");
			menuState.setActs(Arrays.asList(save1,save2,save3,save4,save5,save6,save7));
			menuStateRepository.save(menuState);
			menuStateRepository.save(new MenuState("无效",30,"DISABLE"));
			menuStateRepository.save(new MenuState("删除",40,"DELETE"));
		}
		
	}


	@Override
	public LogRepository<MenuLog> getLogRepository() {
		// TODO Auto-generated method stub
		return menuLogRepository ;
	}


	@Override
	public AuditorEntityRepository<Menu> getAuditorEntityRepository() {
		// TODO Auto-generated method stub
		return menuRepository;
	}


	@Override
	@PostConstruct 
	public void injectEntityActRepository() {
		new Menu().setActRepository(menuActRepository);
		
	}


	@Override
	@PostConstruct
	public void injectLogRepository() {
		new Menu().setLogRepository(menuLogRepository);
		
	}


	@Override
	public MenuLog getLogInstance() {
		// TODO Auto-generated method stub
		return new MenuLog();
	}

}
