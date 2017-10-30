package com.galaxyinternet.model.common;

import java.util.List;

public class Node {
	
	 /**
     * 节点id
     */
    private Long id;
    /**
     * 结果集id
     */
    private Long resultId;
    /**
     * title_id
     */
    private Long titleId;
    /**
     * 节点名称
     */
    private String nodeName;
   
	/**
     * 节点值
     */
    private String value;
    /**
     * 父节点id
     */
    private Long parentId;
    /**
     * 孩子节点
     */
    private List<Node> childList;
    /**
     * type
     */
    private Integer type;
    
    public Node() {
    }
    Node(Long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }
    Node(Long id, String nodeName, Long parentId) {
        this.id = id;
        this.nodeName = nodeName;
        this.parentId = parentId;
    }
    Node(Long id, String nodeName,String value,Long parentId) {
        this.id = id;
        this.nodeName = nodeName;
        this.value = value;
        this.parentId = parentId;
    }
    public Node(Long id,Long resultId,Long titleId,String nodeName,String value,Long parentId) {
        this.id = id;
        this.resultId = resultId;
        this.titleId = titleId;
        this.nodeName = nodeName;
        this.value = value;
        this.parentId = parentId;
    }
    public Node(Long id,Long resultId,Long titleId,String nodeName,String value,Long parentId,Integer type) {
        this.id = id;
        this.resultId = resultId;
        this.titleId = titleId;
        this.nodeName = nodeName;
        this.value = value;
        this.parentId = parentId;
        this.type = type;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getResultId() {
		return resultId;
	}
	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}
    public Long getTitleId() {
		return titleId;
	}
	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}
	public List<Node> getChildList() {
		return childList;
	}
	public void setChildList(List<Node> childList) {
		this.childList = childList;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
