package com.liyang.listener;

import org.hibernate.jdbc.AbstractWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

import com.liyang.domain.base.AbstractWorkflow;
import com.liyang.domain.base.AbstractWorkflowAct;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.base.StateRepository;
import com.liyang.service.AbstractWorkflowService;
import com.liyang.util.CommonUtil;
import com.liyang.util.CreateValidGroup;
import com.liyang.util.UpdateValidGroup;

public abstract class WorkflowRestEventListener<T extends AbstractWorkflowEntity, W extends AbstractWorkflow, A extends AbstractWorkflowAct, S extends AbstractWorkflowState, L extends AbstractWorkflowLog>
		extends AbstractRepositoryEventListener<T> {

	@Autowired
	AbstractWorkflowService<T, W, A, S, L> service;

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
		service.doBeforeAct(entity, act);
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
		CommonUtil.validate(entity, UpdateValidGroup.class);
		A act;
		if (entity.getAct() != null && !"".equals(entity.getAct().trim())) {
			act = service.getAct(entity, entity.getAct());
		} else {
			act = service.getAct(entity, "update");
		}
		if (act != null) {
			service.doBeforeAct(entity, act);
		}
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
		A act;
		if (parent.getAct() != null && !"".equals(parent.getAct().trim())) {
			act = service.getAct(parent, parent.getAct());
		} else {
			act = service.getAct(parent, "update");
		}
		if (act != null) {
			service.doBeforeAct(parent, act , linked);
		}
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
		A act;
		if (parent.getAct() != null && !"".equals(parent.getAct().trim())) {
			act = service.getAct(parent, parent.getAct());
		} else {
			act = service.getAct(parent, "update");
		}
		if (act != null) {
			service.doBeforeAct(parent, act , linked);
		}
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
