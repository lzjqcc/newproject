package com.liyang.message;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseMsgElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnumOperationMessageType msgType;
	private Object msgContent;
	private Boolean actElement = false;
	private Boolean noticeElement = false;
	private Boolean fromElement = false;
	
	
	@JsonIgnore
	public Boolean isNoticeElement(){
		return noticeElement;
	}
	@JsonIgnore
	public void setNoticeElement(Boolean noticeElement) {
		this.noticeElement = noticeElement;
	}
	@JsonIgnore
	public void setFromElement(Boolean fromElement) {
		this.fromElement = fromElement;
	}
	

	@JsonIgnore
	public Boolean isActElement(){
		return actElement;
	}
	@JsonIgnore
	public void setActElement(Boolean actElement) {
		this.actElement = actElement;
	}

	@JsonProperty("MsgType")
	public EnumOperationMessageType getMsgType() {
		return msgType;
	}
	@JsonProperty("MsgType")
	public void setMsgType(EnumOperationMessageType msgType) {
		this.msgType = msgType;
	}

	@JsonProperty("MsgContent")
	public Object getMsgContent() {
		return msgContent;
	}
	

	

	@JsonProperty("MsgContent")
	public void setMsgContent(Object content){
		
		if(!(content instanceof Map)){
			this.msgContent = content;
			if(((CustomContent)content).getExt().startsWith("act:")){
				setActElement(true);
			}else if(((CustomContent)content).getExt().startsWith("notice:")){
				setNoticeElement(true);
			}else if(((CustomContent)content).getExt().startsWith("from:")){
				setFromElement(true);
			}
		}else if(getMsgType() == EnumOperationMessageType.TIMTextElem){
			TextContent textContent = new TextContent();
			BeanWrapper beanWrapper = new BeanWrapperImpl(textContent);
			beanWrapper.setPropertyValues((Map<?, ?>) content);
			this.msgContent = textContent;
		}else if(getMsgType() == EnumOperationMessageType.TIMFaceElem){
			FaceContent textContent = new FaceContent();
			BeanWrapper beanWrapper = new BeanWrapperImpl(textContent);
			beanWrapper.setPropertyValues((Map<?, ?>) content);
			this.msgContent = textContent;
		}else if(getMsgType() == EnumOperationMessageType.TIMFileElem){
			FileContent textContent = new FileContent();
			BeanWrapper beanWrapper = new BeanWrapperImpl(textContent);
			beanWrapper.setPropertyValues((Map<?, ?>) content);
			this.msgContent = textContent;
		}else if(getMsgType() == EnumOperationMessageType.TIMImageElem){
			ImageContent textContent = new ImageContent();
			BeanWrapper beanWrapper = new BeanWrapperImpl(textContent);
			beanWrapper.setPropertyValues((Map<?, ?>) content);
			this.msgContent = textContent;
		}
		else if(getMsgType() == EnumOperationMessageType.TIMLocationElem){
			LocationContent textContent = new LocationContent();
			BeanWrapper beanWrapper = new BeanWrapperImpl(textContent);
			beanWrapper.setPropertyValues((Map<?, ?>) content);
			this.msgContent = textContent;
		}else if(getMsgType() == EnumOperationMessageType.TIMSoundElem){
			SoundContent textContent = new SoundContent();
			BeanWrapper beanWrapper = new BeanWrapperImpl(textContent);
			beanWrapper.setPropertyValues((Map<?, ?>) content);
			this.msgContent = textContent;
		}else if(getMsgType() == EnumOperationMessageType.TIMCustomElem){
			CustomContent textContent = new CustomContent();
			BeanWrapper beanWrapper = new BeanWrapperImpl(textContent);
			beanWrapper.setPropertyValues((Map<?, ?>) content);
			this.msgContent = textContent;
			
			if(((CustomContent)msgContent).getExt().startsWith("act:")){
				setActElement(true);
			}else if(((CustomContent)msgContent).getExt().startsWith("notice:")){
				setNoticeElement(true);
			}else if(((CustomContent)msgContent).getExt().startsWith("from:")){
				setFromElement(true);
			}
			
		}
		
		
	}

}