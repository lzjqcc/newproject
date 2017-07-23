package com.liyang.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyang.domain.base.AbstractWorkflowEntity;

public class CustomContent implements IContent{
	
	private Object data;
	private String desc;
	private String ext;
	private String sound;
	
	@JsonProperty("Data")
	public Object getData() {
		return data;
	}

	@JsonProperty("Data")
	public void setData(Object data) {
		this.data = data;
	}
	
	@JsonProperty("Desc")
	public String getDesc() {
		return desc;
	}
	@JsonProperty("Desc")
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@JsonProperty("Ext")
	public String getExt() {
		return ext;
	}
	@JsonProperty("Ext")
	public void setExt(String ext) {
		this.ext = ext;
	}
	@JsonProperty("Sound")
	public String getSound() {
		return sound;
	}
	@JsonProperty("Sound")
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	
}
