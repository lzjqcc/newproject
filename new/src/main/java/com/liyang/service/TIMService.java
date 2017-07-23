package com.liyang.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.el.lang.EvaluationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.config.TIMKey;
import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.base.AbstractAuditorAct.FirstNoticeType;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.message.Message;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.ReturnObjectImpl;

@Service
public class TIMService {
	
	@Autowired
	UserRepository userRepository;

	private String ver = "V4";
	private long sdkappid = TIMKey.sdkAppId;
	private String identifier = TIMKey.identifier;
	private String usersig = TIMKey.usersig;
	private int random = 9999;
	private String contenttype = "json";

	private String urlTemplate = "https://console.tim.qq.com/" + ver + "/%s/%s?sdkappid=" + sdkappid + "&identifier="
			+ identifier + "&usersig=" + usersig + "&random=%d&contenttype=json";

	public void doNotice(User fromUser, AbstractAuditorEntity entity ,  AbstractAuditorAct act) {
	
		if (FirstNoticeType.SELF.equals(act.getFirstNotice()) || FirstNoticeType.SELF_AND_SHOW_USER.equals(act.getFirstNotice())) {
			AbstractAuditorAct findByActCode = entity.getActRepository().findByActCode("noticeActUser");
			if(findByActCode==null){
				throw new FailReturnObject(1800, "noticeActUser动作不存在" );
			}
			String sendCustomElemMessage = sendCustomElemMessage(CommonUtil.getPrincipal(), entity, act);
			if (sendCustomElemMessage != null) {
				
				noticeLog(CommonUtil.getPrincipal(), entity, findByActCode,sendCustomElemMessage);
			}
		}
		if (FirstNoticeType.SHOW_USER.equals(act.getFirstNotice()) || FirstNoticeType.SELF_AND_SHOW_USER.equals(act.getFirstNotice())) {
			AbstractAuditorAct findByActCode = entity.getActRepository().findByActCode("noticeShowUser");
			if(findByActCode==null){
				throw new FailReturnObject(1810, "noticeShowUser动作不存在" );
			}
			String sendCustomElemMessage = sendCustomElemMessage(fromUser, entity, act);
			if (sendCustomElemMessage != null) {
				
				noticeLog(fromUser, entity, findByActCode,sendCustomElemMessage);
			}
		}
		if(entity.getNotice()!=null && !entity.getNotice().isEmpty()){
			AbstractAuditorAct findByActCode = entity.getActRepository().findByActCode("handNotice");
			if(findByActCode==null){
				throw new FailReturnObject(1820, "handNotice动作不存在" );
			}
			for (Object object : entity.getNotice()) {
				User findByUnionid = userRepository.findByUnionid(object.toString());
				String sendCustomElemMessage = sendCustomElemMessage(findByUnionid, entity, act);
				if (sendCustomElemMessage != null) {
					noticeLog(findByUnionid, entity, findByActCode,sendCustomElemMessage);
				}
			}
		}
	}
	
	private void noticeLog(User user , AbstractAuditorEntity entity, AbstractAuditorAct act,String message){
		AbstractAuditorLog logInstance = entity.getLogInstance();
		logInstance.setAct(act);
		logInstance.setEntity(entity);
		logInstance.setLabel(act.getLabel());
		logInstance.setNoticeTo(user);
		logInstance.setRequestBody(message);
		entity.getLogRepository().save(logInstance);
	}

	public String sendCustomElemMessage(User user, AbstractAuditorEntity entity, AbstractAuditorAct act) {
		if (user != null) {
			String url = String.format(urlTemplate, "openim", "sendmsg", new Random().nextInt(700000000));
			String dataFromTemplate = getDataFromTemplate(user, entity, act);
			if (dataFromTemplate == null) {
				throw new FailReturnObject(1900, "没有设置行为消息模板");
			}
			Message message = CommonUtil.customElemMessageWrapper(dataFromTemplate, "notice:", user.getUnionid(), 2);

			ReturnObject restTemplatePost = restTemplatePost(url, message);
			if (restTemplatePost != null && "OK".equals(restTemplatePost.getActionStatus())) {
				return dataFromTemplate;
			} else {
				return restTemplatePost.getErrorCode() + ":" + restTemplatePost.getErrorInfo();
			}

		} else {
			return null;
		}
	}

	private String getDataFromTemplate(User user, AbstractAuditorEntity entity, AbstractAuditorAct act) {
		SpelContext spelContext = new SpelContext(user, entity, act);
		ExpressionParser parser = new SpelExpressionParser();
		if (act.getNoticeTemplate() != null && !"".equals(act.getNoticeTemplate().trim())) {
			String result = parser.parseExpression(act.getNoticeTemplate(), new TemplateParserContext())
					.getValue(spelContext, String.class);
			return result;
		} else {
			return null;
		}
	}

	private ReturnObject restTemplatePost(String url, Object message) {
		RestTemplate restTemplate = new RestTemplate();
		String ret = restTemplate.postForObject(url, message, String.class);
		ObjectMapper mapper = new ObjectMapper();
		ReturnObjectImpl readValue;
		try {
			readValue = mapper.readValue(ret, ReturnObjectImpl.class);
		} catch (IOException e) {
			throw new FailReturnObject(1800, "解析tim信息错误");
		}
		return readValue;
	}

	public ReturnObject addUser(String identifier, String nick, String faceUrl) {
		String url = String.format(urlTemplate, "im_open_login_svc", "account_import", new Random().nextInt(100000000));
		AddUser user = new AddUser();
		user.setFaceUrl(faceUrl);
		user.setIdentifier(identifier);
		user.setNick(nick);
		ReturnObject restTemplatePost = restTemplatePost(url, user);
		if (restTemplatePost != null && "OK".equals(restTemplatePost.getActionStatus())) {
			return restTemplatePost;
		} else {
			throw new FailReturnObject(1750, restTemplatePost.getErrorCode() + ":" + restTemplatePost.getErrorInfo());
		}
	}

	private class AddUser {
		@JsonProperty("Identifier")
		private String identifier;
		@JsonProperty("Nick")
		private String nick;
		@JsonProperty("FaceUrl")
		private String faceUrl;

		public String getIdentifier() {
			return identifier;
		}

		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public String getFaceUrl() {
			return faceUrl;
		}

		public void setFaceUrl(String faceUrl) {
			this.faceUrl = faceUrl;
		}

	}

	private class SpelContext {
		private User user;
		private AbstractAuditorEntity entity;
		private AbstractAuditorAct act;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public AbstractAuditorEntity getEntity() {
			return entity;
		}

		public void setEntity(AbstractAuditorEntity entity) {
			this.entity = entity;
		}

		public AbstractAuditorAct getAct() {
			return act;
		}

		public void setAct(AbstractAuditorAct act) {
			this.act = act;
		}

		public SpelContext(User user, AbstractAuditorEntity entity, AbstractAuditorAct act) {
			super();
			this.user = user;
			this.entity = entity;
			this.act = act;
		}

	}
}
