package com.liyang.domain.asset;

import com.liyang.domain.base.AbstractAuditorState;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "asset_state")
public class AssetState extends AbstractAuditorState<Asset,AssetAct> {

	public AssetState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}
	public AssetState(){
		super();
	}

}
