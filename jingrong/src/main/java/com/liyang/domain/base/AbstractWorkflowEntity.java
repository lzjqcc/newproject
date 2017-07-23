package com.liyang.domain.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.Cacheable;
import javax.persistence.ConstraintMode;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.domain.group.Group;
import com.liyang.domain.role.Role;
import com.liyang.util.CommonUtil;

@MappedSuperclass
@Cacheable

public abstract class AbstractWorkflowEntity<W extends AbstractWorkflow, S extends AbstractWorkflowState, A extends AbstractWorkflowAct, L extends AbstractWorkflowLog>
		extends AbstractAuditorEntity<L, A, S> {

	public abstract AbstractWorkflowLog getLogInstance();

	@ManyToOne
	private W workflow;
	
	@Transient
	private List<State> stateActList= new ArrayList<State>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liyang.domain.AbstractEntityProjection#getWorkflow()
	 */
	public W getWorkflow() {
		return workflow;
	}

	public void setWorkflow(W abstractWorkflowTemplate) {
		this.workflow = abstractWorkflowTemplate;
	}

	public void setStateActList(List<State> stateActList) {
		this.stateActList = stateActList;
	}
	@Transient
	public List<State> getStateActList() {
		return stateActList;
	}

	public void injectStateActList() {
		ArrayList<State> stateList = new ArrayList<State>();
		
		if (getWorkflow() == null) {
			return ;
		}
		Set<S> states = getWorkflow().getStates();
		if (states == null) {
			return ;
		}

		if (getLogs().isEmpty()) {
			return ;
		}

		for (S abstractWorkflowState : states) {
			State state = new State();
			state.setId(abstractWorkflowState.getId());
			state.setLabel(abstractWorkflowState.getLabel());
			state.setSort(abstractWorkflowState.getSort());
			state.setStateCode(abstractWorkflowState.getStateCode());
			List<A> acts = (List<A>) abstractWorkflowState.getActs();
			ArrayList<Act> actList = new ArrayList<Act>();
			if (acts != null && !acts.isEmpty()) {
				for (A a : acts) {
					Act act = new Act();
					act.setId(a.getId());
					act.setLabel(a.getLabel());
					act.setSort(a.getSort());
					act.setActCode(a.getActCode());
					actList.add(act);
				}
			}
			Collections.sort(actList);
			state.setActs(actList);
			stateList.add(state);
		}
		Collections.sort(stateList);
		
		Map<AbstractWorkflowState, Map<AbstractWorkflowAct, List<L>>> collect = getLogs().stream()
				.collect(Collectors.groupingBy(L::getAfterState, Collectors.groupingBy(L::getAct)));

		for (Entry<AbstractWorkflowState, Map<AbstractWorkflowAct, List<L>>> s : collect.entrySet()) {
			AbstractWorkflowState logState = s.getKey();
			Map<AbstractWorkflowAct, List<L>> actMapLogs = s.getValue();

			State currentState = null;
			for (State state : stateList) {
				if (state.getId().equals(logState.getId())) {
					currentState = state;
				}
			}
			for (Entry<AbstractWorkflowAct, List<L>> m : actMapLogs.entrySet()) {
				// m.getKey().setDone(true);

				if (currentState != null) {
					for (Act act : currentState.getActs()) {
						if (act.getId().equals(m.getKey().getId())) {
							act.setDone(true);
						}
					}
					if (m.getKey().getTargetState() != null) {
						currentState.setDone(true);

					}

				}
			}

		}

		setStateActList(stateList);

	}

	private class State implements Comparable<State> {
		private Integer id;
		private String label;
		private Integer sort;
		private String stateCode;
		private Boolean done=false;
		private List<Act> acts;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}

		public String getStateCode() {
			return stateCode;
		}

		public void setStateCode(String stateCode) {
			this.stateCode = stateCode;
		}

		public Boolean getDone() {
			return done;
		}

		public void setDone(Boolean done) {
			this.done = done;
		}

		public List<Act> getActs() {
			return acts;
		}

		public void setActs(List<Act> acts) {
			this.acts = acts;
		}

		@Override
		public int compareTo(State o) {
			if (getSort() > o.getSort()) {
				return 1;
			} else if (getSort() == o.getSort()) {
				return 0;
			} else {
				return -1;
			}
		}

	}

	private class Act implements Comparable<Act> {
		private Integer id;
		private String label;
		private String actCode;
		private Integer sort;
		private Boolean done = false;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getActCode() {
			return actCode;
		}

		public void setActCode(String actCode) {
			this.actCode = actCode;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}

		public Boolean getDone() {
			return done;
		}

		public void setDone(Boolean done) {
			this.done = done;
		}

		@Override
		public int compareTo(Act o) {
			if (getSort() > o.getSort()) {
				return 1;
			} else if (getSort() == o.getSort()) {
				return 0;
			} else {
				return -1;
			}
		}

	}

}
