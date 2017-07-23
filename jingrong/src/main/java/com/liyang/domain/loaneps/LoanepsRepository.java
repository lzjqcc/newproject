package com.liyang.domain.loaneps;

import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface LoanepsRepository extends WorkflowEntityRepository<Loaneps> {

}