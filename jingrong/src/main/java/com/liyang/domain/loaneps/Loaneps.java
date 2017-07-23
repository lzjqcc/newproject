package com.liyang.domain.loaneps;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.ActRepository;
import com.liyang.domain.base.LogRepository;
import com.liyang.domain.department.Department;
import com.liyang.domain.group.Group;
import com.liyang.domain.role.Role;
import com.liyang.util.CommonUtil;
import com.liyang.util.TreeNode;
import com.liyang.util.TreeNodeImpl;
/**
 * 等额本金
 * @author win7
 *
 */
@Entity
@Table(name = "loaneps")
@Cacheable
public class Loaneps extends AbstractWorkflowEntity<LoanepsWorkflow, LoanepsState, LoanepsAct, LoanepsLog> {

	@Transient
	private static ActRepository actRepository;

	@Transient
	private static LogRepository logRepository;
	@Override
	@JsonIgnore
	public AbstractWorkflowLog getLogInstance() {
		// TODO Auto-generated method stub
		return new LoanepsLog();
	}

	@Override
	@JsonIgnore
	public LogRepository getLogRepository() {
		// TODO Auto-generated method stub
		return logRepository;
	}

	@Override
	public void setLogRepository(LogRepository logRepository) {
		Loaneps.logRepository = logRepository;
	}

	@Override
	public ActRepository getActRepository() {
		// TODO Auto-generated method stub
		return actRepository;
	}

	@Override
	public void setActRepository(ActRepository actRepository) {
		// TODO Auto-generated method stub
		this.actRepository=actRepository;
	}



}