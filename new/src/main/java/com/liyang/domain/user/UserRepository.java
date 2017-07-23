package com.liyang.domain.user;

import org.springframework.data.repository.query.Param;

import com.liyang.domain.base.WorkflowEntityRepository;

//@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface UserRepository extends WorkflowEntityRepository<User> {
	public User findByUnionid(@Param("unionid") String unionid);

}