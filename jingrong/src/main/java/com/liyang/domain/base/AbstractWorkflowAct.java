package com.liyang.domain.base;

import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.domain.role.Role;
import com.liyang.message.EnumOperationMessageType;
@MappedSuperclass
@Cacheable
public class AbstractWorkflowAct<S extends AbstractWorkflowState,W extends AbstractWorkflow> extends AbstractAuditorAct<S>{
	

	public AbstractWorkflowAct(String label, String actCode, Integer sort) {
		super(label, actCode, sort);
		// TODO Auto-generated constructor stub
	}
	public AbstractWorkflowAct(){
		super();
	}

	@Enumerated(EnumType.STRING)
	private ActType actType = ActType.NORMAL;

	@ManyToOne
	private S targetState;
	
	@OneToOne
	private W startWorkflow;
	
	@OneToOne
	private W nextWorkflow;
	
	
	@Transient
	private Boolean done=false;
	


	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowActProjection#getActType()
	 */
	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getActType()
	 */
	public ActType getActType() {
		return actType;
	}

	public void setActType(ActType actType) {
		this.actType = actType;
	}


	public W getStartWorkflow() {
		return startWorkflow;
	}
	public void setStartWorkflow(W startWorkflow) {
		this.startWorkflow = startWorkflow;
	}
	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowActProjection#getDone()
	 */
	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getDone()
	 */
	public Boolean getDone(){
		return this.done;
	}
	
	public void setDone(Boolean done){
		this.done = done;
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getTargetState()
	 */
	public S getTargetState() {
		return targetState;
	}

	public void setTargetState(S targetState) {
		this.targetState = targetState;
	}


	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowActProjection#getNextWorkflow()
	 */
	/* (non-Javadoc)
	 * @see com.liyang.domain.a#getNextWorkflow()
	 */
	public W getNextWorkflow() {
		return nextWorkflow;
	}


	public void setNextWorkflow(W nextWorkflow) {
		this.nextWorkflow = nextWorkflow;
	}


	public static enum ActType{
		START,NORMAL,END
	}


	@Override
	public String toString() {
		return "AbstractWorkflowAct [done=" + done + ", getId()=" + getId() + "]";
	}



	



}
