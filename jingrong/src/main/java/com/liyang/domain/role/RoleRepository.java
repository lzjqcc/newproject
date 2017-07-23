package com.liyang.domain.role;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.EntityRepository;

//@RepositoryRestResource(excerptProjection = RoleProjection.class)
public interface RoleRepository extends AuditorEntityRepository<Role> {
	
}
