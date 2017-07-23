package com.liyang.domain.base;

import javax.persistence.Cacheable;
import javax.persistence.ConstraintMode;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@MappedSuperclass
@Cacheable
public  class AbstractWorkflowLog<E extends AbstractWorkflowEntity,
						W extends AbstractWorkflow,
						S extends AbstractWorkflowState , A extends AbstractWorkflowAct> extends AbstractAuditorLog<E,A>{




	
	@ManyToOne
	@JoinColumn(name="workflow_id" )
	private W workflow;
	

	@ManyToOne
	@JoinColumn(name="after_state_id" )
	private S afterState;
	

	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowLogProjection#getWorkflow()
	 */
	public W getWorkflow() {
		return workflow;
	}

	public void setWorkflow(W workflow) {
		this.workflow = workflow;
	}


	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowLogProjection#getAfterState()
	 */
	public S getAfterState() {
		return afterState;
	}

	public void setAfterState(S afterState) {
		this.afterState = afterState;
	}

	public A getAct() {
		return super.getAct();
	}

	public void setAct(A act) {
		super.setAct(act);
	}



}
