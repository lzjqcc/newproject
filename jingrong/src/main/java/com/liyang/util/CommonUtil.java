package com.liyang.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.persistence.Transient;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorAct.FirstNoticeType;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractWorkflow;
import com.liyang.domain.base.AbstractWorkflowAct;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.Role.RoleCode;
import com.liyang.domain.user.User;
import com.liyang.message.BaseMsgElement;
import com.liyang.message.CustomContent;
import com.liyang.message.EnumOperationMessageType;
import com.liyang.message.Message;

public class CommonUtil {

	public static Message customElemMessageWrapper(Object data, String extType, String toAccount,
			Integer syncOtherMachine) {
		Message message = new Message();
		BaseMsgElement customElement = new BaseMsgElement();
		CustomContent customContent = new CustomContent();
		customContent.setData(data);
		customContent.setExt(extType);
		customElement.setMsgType(EnumOperationMessageType.TIMCustomElem);
		customElement.setMsgContent(customContent);
		message.setMsgBody(new BaseMsgElement[] { customElement });
		message.setToAccount(toAccount);
		message.setSyncOtherMachine(syncOtherMachine);
		message.setMsgRandom(new Random().nextInt(30000000));
		return message;
	}

	public static String template(String entityName, String viewType) {
		String defaultStr = "default";
		String prefix = "template";
		String split = "/";
		String departmentType = getCurrentUserDepartment() != null
				? getCurrentUserDepartment().getType().toString().toLowerCase() : defaultStr;
		String departmentId = getCurrentUserDepartment() != null
				? getCurrentUserDepartment().getId().toString().toLowerCase() : defaultStr;
		String roleCode = CommonUtil.getCurrentUserRoleCode().toString().toLowerCase();
		String targetFile = entityName + split + departmentType + split + departmentId + split + roleCode + split
				+ viewType;
		ClassPathResource resource = new ClassPathResource(prefix + split + targetFile + ".html");
		if (!resource.exists()) {
			targetFile = entityName + split + departmentType + split + departmentId + split + viewType;
			resource = new ClassPathResource(prefix + split + targetFile + ".html");
			if (!resource.exists()) {
				targetFile = entityName + split + departmentType + split + viewType;
				resource = new ClassPathResource(prefix + split + targetFile + ".html");
				if (!resource.exists()) {
					targetFile = entityName + split + viewType;
					resource = new ClassPathResource(prefix + split + targetFile + ".html");
					if (!resource.exists()) {
						throw new FailReturnObject(800, "实体模板不存在");
					}
				}
			}
		}
		return targetFile;
	}

	public static RoleCode getCurrentUserRoleCode() {
		RoleCode authority;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication) {
			authority = RoleCode.valueOf("ANONYMOUS");
		} else {
			if (authentication.getAuthorities().isEmpty()) {
				throw new FailReturnObject(640, "没有角色授权");
			}
			authority = RoleCode.valueOf(authentication.getAuthorities().toArray()[0].toString());
		}
		return authority;
	}

	public static Department getCurrentUserDepartment() {
		Department department = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication && authentication.getPrincipal() instanceof User) {
			department = ((User) authentication.getPrincipal()).getDepartment();
		}
		return department;
	}

	public static String prettyPrint(Object o) {
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		String s = null;
		// 读取JSON数据
		try {
			s = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s);
		return s;
	}

	public static List<Department> ownAndChildrenDepartments(Department node) {
		if (node == null) {
			return new ArrayList<Department>();
		}
		ArrayList<Department> departments = new ArrayList<Department>();
		departments.add(node);

		if (node.getChildren() != null && !node.getChildren().isEmpty()) {
			for (Department department : node.getChildren()) {
				departments.addAll(ownAndChildrenDepartments(department));
			}
		}

		return departments;
	}

	public static <T extends TreeNode> List<TreeNodeImpl> listToTree(Collection<T> listItems) {
		List<TreeNodeImpl> treeNodes = new ArrayList<TreeNodeImpl>();
		for (T t : listItems) {
			TreeNodeImpl<TreeNodeImpl> treeNodeImpl = new TreeNodeImpl<TreeNodeImpl>();
			BeanUtils.copyProperties(t, treeNodeImpl, "children", "parent");
			treeNodes.add(treeNodeImpl);
		}
		// return null;
		return TreeBuilder.bulid(treeNodes);
	}

	public static User getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		if (authentication.getPrincipal() instanceof User) {
			return (User) authentication.getPrincipal();
		} else {
			return null;
		}
	}

	public static void validate(Object object, Class<?> group) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> validates = validator.validate(object, group);
		if (!validates.isEmpty()) {
			throw new FailReturnObject(400, validates, FailReturnObject.Level.DISPLAY);
		}

	}

	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());

			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class") && !key.contentEquals("enum")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					if (getter != null && !getter.getName().equals("getLastModifiedAt")) {
						Class<?> returnType = property.getReadMethod().getReturnType();
						Transient annotation = property.getReadMethod().getAnnotation(Transient.class);
						if(!returnType.equals(Map.class) &&!returnType.equals(List.class) && !returnType.equals(Set.class) && annotation == null){
							Object value = getter.invoke(obj);
							map.put(key, value);
						}
					}

				}

			}
		} catch (IntrospectionException |IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
			throw new FailReturnObject(1900, "bean2map失败");
		} 

		return map;

	}

}
