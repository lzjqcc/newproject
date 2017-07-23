package com.liyang.service;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.user.User;

public interface IActable<T extends AbstractAuditorEntity, A extends AbstractAuditorAct> {

	A getAct(T entity, String code);

	Boolean canAct(T entity, String code);

	T doBeforeAct(T entity, A act, Object linked,User fromUser);
	
	T doAfterAct(T entity, Object linked, User fromUser);

	T doBeforeAct(T entity, A act);
	T doBeforeAct(T entity, A act,Object linked);
	T doAfterAct(T entity, Object linked);
	T doAfterAct(T entity);

}