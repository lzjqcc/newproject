package com.liyang.domain.mdbts;

import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface MdbtsRepository extends WorkflowEntityRepository<Mdbts> {

}