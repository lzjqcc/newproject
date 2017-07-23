package com.liyang.domain.person;

import com.liyang.domain.base.AuditorEntityRepository;

//@RepositoryRestResource(excerptProjection = RoleProjection.class)
public interface PersonRepository extends AuditorEntityRepository<Person> {
	
}
