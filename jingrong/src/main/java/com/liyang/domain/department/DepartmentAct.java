package com.liyang.domain.department;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;
@Entity
@Table(name = "department_act")
public class DepartmentAct extends AbstractAuditorAct<DepartmentState> {

	public DepartmentAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public DepartmentAct(){
		super();
	}

}
