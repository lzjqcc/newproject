package com.liyang.util;

import java.util.List;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

public class TreeNodeImpl<T extends TreeNode> implements TreeNode<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer id;
	String label;
	String href;
	Integer sort;
	T parent;
	List<T> children;
	Integer parentId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public T getParent() {
		return parent;
	}
	public void setParent(T parent) {
		this.parent = parent;
	}
	public List<T> getChildren() {
		return children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}
	public Integer getParentId() {
		return parentId==null?0:parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}





}
