package com.liyang.domain.loaneps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.EntityRepository;
//@RepositoryRestResource(excerptProjection = AbstractWorkflowProjection.class)
public interface LoanepsWorkflowRepository extends EntityRepository<LoanepsWorkflow> {

	

	
	
}