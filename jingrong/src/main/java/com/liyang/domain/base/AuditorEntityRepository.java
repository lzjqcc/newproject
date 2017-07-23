package com.liyang.domain.base;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import com.liyang.domain.department.Department;
import com.liyang.domain.menu.Menu;
import com.liyang.domain.role.Role.RoleCode;
import com.liyang.domain.user.User;

@NoRepositoryBean
public interface AuditorEntityRepository<T extends AbstractAuditorEntity> extends EntityRepository<T> {
	
//	@Query("select m from #{#entityName} m JOIN m.state s JOIN s.acts a JOIN a.roles r  where s.stateCode = ? and a.actCode='list' and r.roleCode = ? ")
//	List<T> findByStateCodeAndRoleCode(String actCode , RoleCode roleCode);

	@Query("select m from #{#entityName} m JOIN m.state s where s.stateCode != 'DELETE' and m.createdBy = ?#{principal} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwn')")
	public Page<T> listOwn(Pageable pageable);

	@Query("select m from #{#entityName} m JOIN m.state s where s.stateCode != 'DELETE' and m.createdByDepartment = ?#{principal?.department} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwnDepartment')")
	public Page<T> listOwnDepartment(Pageable pageable);

	@Query("select m from #{#entityName} m JOIN m.state s where s.stateCode != 'DELETE' and m.createdByDepartment in ?#{principal?.childrenDepartments} ")
	@PostAuthorize("hasPermission(returnObject ,'listOwnDepartmentAndChildren')")
	public Page<T> listOwnDepartmentAndChildren(Pageable pageable);

	@PostAuthorize("hasPermission(returnObject ,'read')")
	public T findOne(Integer id);
	
	
	
}
