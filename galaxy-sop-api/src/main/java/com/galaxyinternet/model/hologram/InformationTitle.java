package com.galaxyinternet.model.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class InformationTitle extends PagableEntity {
	private static final long serialVersionUID = 1L;

	private String parentId;

	private String code;

	private String name;

	private Integer indexNo;

	private Integer type;

	private String sign;

	private String content;

	private int isValid;

	private Long createId;

	private Long updateId;

	
	private List<InformationTitle> childList;
	private List<InformationDictionary> valueList;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(Integer indexNo) {
		this.indexNo = indexNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign == null ? null : sign.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public List<InformationTitle> getChildList() {
		return childList;
	}

	public void setChildList(List<InformationTitle> childList) {
		this.childList = childList;
	}

	public List<InformationDictionary> getValueList() {
		return valueList;
	}

	public void setValueList(List<InformationDictionary> valueList) {
		this.valueList = valueList;
	}

	
	
}
