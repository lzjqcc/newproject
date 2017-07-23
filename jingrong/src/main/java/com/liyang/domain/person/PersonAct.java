package com.liyang.domain.person;

import com.liyang.domain.base.AbstractAuditorAct;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_act")
public class PersonAct extends AbstractAuditorAct<PersonState> {

	public PersonAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public PersonAct(){
		super();
	}

}
