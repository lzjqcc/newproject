package com.liyang.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.liyang.domain.base.AuditorEntityRepository;
import com.liyang.domain.base.EntityRepository;
//@RepositoryRestResource(excerptProjection = DepartmentProjection.class)
public interface DepartmentRepository extends AuditorEntityRepository<Department> {

}