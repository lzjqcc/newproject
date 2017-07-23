package com.liyang.domain.asset;

import com.liyang.domain.base.AbstractAuditorAct;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "asset_act")
public class AssetAct extends AbstractAuditorAct<AssetState> {

	public AssetAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public AssetAct(){
		super();
	}

}
