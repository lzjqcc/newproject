package com.liyang.domain.person;

import com.liyang.domain.base.AbstractAuditorState;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_state")
public class PersonState extends AbstractAuditorState<Person,PersonAct> {

	public PersonState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public PersonState(){
		super();
	}

}
