package com.liyang.domain.base;

import java.util.ArrayList;
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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
@MappedSuperclass
@Cacheable
public class AbstractAuditorState<E extends AbstractAuditorEntity,A extends AbstractAuditorAct> extends BaseEntity implements Comparable<AbstractAuditorState>{


	public AbstractAuditorState(String label, Integer sort, String stateCode) {
		this.setLabel(label);
		this.sort = sort;
		this.stateCode = stateCode;
	}
	public AbstractAuditorState(){
		super();
	}

	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "state_id") }, inverseJoinColumns = {
			@JoinColumn(name = "act_id") })
	private List<A> acts = new ArrayList<A>();
	
	@OneToMany(mappedBy="state")
	private Set<E> entities;
	
	private Integer sort=0;
	
	private String stateCode;
	

	
	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowStateProjection#getStateCode()
	 */
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String code) {
		this.stateCode = code;
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowStateProjection#getActs()
	 */
	public List<A> getActs() {
		return acts;
	}

	public void setActs(List<A> acts) {
		this.acts = acts;
	}



	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/* (non-Javadoc)
	 * @see com.liyang.domain.AbstractWorkflowStateProjection#getEntities()
	 */
	public Set<E> getEntities() {
		return entities;
	}

	public void setEntities(Set<E> entities) {
		this.entities = entities;
	}

	@Override
	public int compareTo(AbstractAuditorState o) {
		// TODO Auto-generated method stub
		if(getSort() > o.getSort()){
			return 1;
		}else if(getSort() == o.getSort()){
			return 0;
		}else{
			return -1;
		}
	}


	






}
