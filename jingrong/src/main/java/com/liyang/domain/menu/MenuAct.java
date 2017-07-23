package com.liyang.domain.menu;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;
@Entity
@Table(name = "menu_act")
public class MenuAct extends AbstractAuditorAct<MenuState> {

	public MenuAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public MenuAct(){
		super();
	}

}
