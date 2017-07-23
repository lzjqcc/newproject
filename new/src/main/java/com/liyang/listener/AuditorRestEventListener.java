package com.liyang.listener;

import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.StateRepository;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.menu.MenuAct;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.CommonUtil;
import com.liyang.util.CreateValidGroup;
import com.liyang.util.UpdateValidGroup;

public abstract class AuditorRestEventListener<T extends AbstractAuditorEntity, S extends AbstractAuditorState, A extends AbstractAuditorAct , L extends AbstractAuditorLog>
		extends AbstractRepositoryEventListener<T> {

	@Autowired
	AbstractAuditorService<T, A, L> service;

	@Autowired
	StateRepository<S> stateRepository;

	@Override
	protected void onBeforeCreate(T entity) {
		System.out.println("onBeforeCreate");
		CommonUtil.validate(entity, CreateValidGroup.class);
		
		if (entity.getState() == null) {
			S findByStateCode = stateRepository.findByStateCode("ENABLE");
			entity.setState(findByStateCode);
		}
		A act = service.getAct(entity, "create");
		entity = service.doBeforeAct(entity, act);
		super.onBeforeCreate(entity);

	}

	@Override
	protected void onAfterCreate(T entity) {
		System.out.println("onAfterCreate");
		service.doAfterAct(entity);
		super.onAfterCreate(entity);
	}

	@Override
	protected void onBeforeSave(T entity) {
		System.out.println("onBeforeSave");
//		System.out.println(entity.getMap());
//		System.out.println(CommonUtil.transBean2Map(entity));
		CommonUtil.validate(entity, UpdateValidGroup.class);
		A act = service.getAct(entity, "update");
		service.doBeforeAct(entity, act);
		super.onBeforeSave(entity);
	}

	@Override
	protected void onAfterSave(T entity) {
		System.out.println("onAfterSave");
		service.doAfterAct(entity);
		super.onAfterSave(entity);
	}

	@Override
	protected void onBeforeLinkSave(T parent, Object linked) {
		System.out.println("onBeforeLinkSave");
		A act = service.getAct(parent, "update");
		service.doBeforeAct(parent, act,linked);
		super.onBeforeLinkSave(parent, linked);
	}

	@Override
	protected void onAfterLinkSave(T parent, Object linked) {
		System.out.println("onAfterLinkSave");
		service.doAfterAct(parent,linked);
		super.onAfterLinkSave(parent, linked);
	}

	@Override
	protected void onBeforeLinkDelete(T parent, Object linked) {
		System.out.println("onBeforeLinkDelete");
		A act = service.getAct(parent, "update");
		service.doBeforeAct(parent, act , linked);
		super.onBeforeLinkDelete(parent, linked);
	}

	@Override
	protected void onAfterLinkDelete(T parent, Object linked) {
		System.out.println("onAfterLinkDelete");
		service.doAfterAct(parent);
		super.onAfterLinkDelete(parent, linked);
	}

	@Override
	protected void onBeforeDelete(T entity) {
		System.out.println("onBeforeDelete");
		A act = service.getAct(entity, "delete");
		service.doBeforeAct(entity, act);
		super.onBeforeDelete(entity);
	}

	@Override
	protected void onAfterDelete(T entity) {
		System.out.println("onAfterDelete");
		service.doAfterAct(entity);
		super.onAfterDelete(entity);
	}

}
