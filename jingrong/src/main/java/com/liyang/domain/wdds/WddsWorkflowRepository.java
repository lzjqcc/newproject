package com.liyang.domain.wdds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.EntityRepository;
//@RepositoryRestResource(excerptProjection = AbstractWorkflowProjection.class)
public interface WddsWorkflowRepository extends EntityRepository<WddsWorkflow> {

	

	
	
}