package com.galaxyinternet.mongodb.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.galaxyinternet.framework.core.dao.utils.QueryField;
import com.galaxyinternet.framework.core.dao.utils.QueryType;
@Document(collection="galaxy.sop.InformationFixedTableMG")
public class InformationFixedTableMG {

	/*唯一编码*/
	@QueryField(attribute="uuid")
	private String uuid;
	@QueryField(attribute="projectId")
	private String projectId;
    @QueryField(attribute="titleId")
    private String titleId;
    private String parentId;
    private String rowNo;

    private String colNo;

    private String type;

    private String content;

    private String isValid;

    private String createTime;

    private String createId;

    private String updateTime;

    private String updateId;
    @QueryField(attribute="titleId",type=QueryType.IN)
    private List<String> titleIds;

    private String valueName;




	public List<String> getTitleIds() {
		return titleIds;
	}

	public void setTitleIds(List<String> titleIds) {
		this.titleIds = titleIds;
	}

	public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId == null ? null : titleId.trim();
    }

    public String getRowNo() {
        return rowNo;
    }

    public void setRowNo(String rowNo) {
        this.rowNo = rowNo == null ? null : rowNo.trim();
    }

    public String getColNo() {
        return colNo;
    }

    public void setColNo(String colNo) {
        this.colNo = colNo == null ? null : colNo.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName  = valueName == null ? null : valueName.trim();;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
    
    
}