package com.galaxyinternet.model.user;

import java.util.ArrayList;
import java.util.List;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class Menus extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 0--总结点
	 * 1--子节点
	 */
	private Integer order;
	private String menuName;
	private String url;
	private List<Menus> nodes;
	
	public Menus(){}
	public Menus(Long id, String menuName, String url){
		this.id = id;
		this.menuName = menuName;
		this.url = url;
	}
	public Menus(Long id, Integer order, String menuName, String url){
		this.id = id;
		this.order = order;
		this.menuName = menuName;
		this.url = url;
	}
	
	public Menus addNode(Menus menus){
		if(nodes == null){
			nodes = new ArrayList<Menus>();
		}
		nodes.add(menus);
		return this;
	}
	
	public List<Menus> getNodes() {
		return nodes;
	}
	public void setNodes(List<Menus> nodes) {
		this.nodes = nodes;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}

}
