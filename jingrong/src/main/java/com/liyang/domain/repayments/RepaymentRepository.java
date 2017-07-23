package com.liyang.domain.repayments;

import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface RepaymentRepository extends WorkflowEntityRepository<Repayments> {

}