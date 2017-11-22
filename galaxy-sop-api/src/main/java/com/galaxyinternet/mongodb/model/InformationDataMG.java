package com.galaxyinternet.mongodb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.galaxyinternet.framework.core.dao.utils.QueryField;
import com.galaxyinternet.framework.core.utils.GSONUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class InformationDataMG {
	private static final long serialVersionUID = 1L;
	/*唯一编码*/
	@QueryField(attribute="uuid")
	private String uuid;
	@ApiModelProperty("项目")
	private String projectId;
	private int flag;//0:正常数据编辑;1:表格数据处理;2:固定表格处理
	@ApiModelProperty("普通类型")
	private List<InformationModelMG> infoModeList;
	@ApiModelProperty("固定表格")
	private List<FixedTableModelMG> infoFixedTableList;
	@ApiModelProperty("动态表格-删除的记录")
	private Set<String> deletedRowIds;  //适用table表
	@ApiModelProperty("动态表格")
	private List<TableModelMG>     infoTableModelMGList;
	@ApiModelProperty("分数")
	private List<InformationScoreMG> scoreList;
	@ApiModelProperty("文件/图片")
	private List<InformationFileMG> infoFileList;
	private Set<Long> deleteFileIds;
	
	private Set<String> deletedResultTids;  //适用result表
	
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<InformationModelMG> getInfoModeList() {
		return infoModeList == null? new ArrayList<InformationModelMG>():infoModeList;
	}

	public void setInfoModeList(List<InformationModelMG> infoModeList) {
		this.infoModeList = infoModeList;
	}

	public List<FixedTableModelMG> getInfoFixedTableList() {
		return infoFixedTableList;
	}

	public void setInfoFixedTableList(List<FixedTableModelMG> infoFixedTableList) {
		this.infoFixedTableList = infoFixedTableList;
	}

	public List<TableModelMG> getInfoTableModelMGList() {
		return infoTableModelMGList;
	}

	public void setInfoTableModelMGList(List<TableModelMG> infoTableModelMGList) {
		this.infoTableModelMGList = infoTableModelMGList;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Set<String> getDeletedRowIds() {
		return deletedRowIds;
	}

	public void setDeletedRowIds(Set<String> deletedRowIds) {
		this.deletedRowIds = deletedRowIds;
	}

	public Set<String> getDeletedResultTids() {
		return deletedResultTids;
	}

	public void setDeletedResultTids(Set<String> deletedResultTids) {
		this.deletedResultTids = deletedResultTids;
	}

	public List<InformationScoreMG> getScoreList()
	{
		return scoreList;
	}

	public void setScoreList(List<InformationScoreMG> scoreList)
	{
		this.scoreList = scoreList;
	}

	public List<InformationFileMG> getInfoFileList()
	{
		return infoFileList;
	}

	public void setInfoFileList(List<InformationFileMG> infoFileList)
	{
		this.infoFileList = infoFileList;
	}

	public Set<Long> getDeleteFileIds()
	{
		return deleteFileIds;
	}

	public void setDeleteFileIds(Set<Long> deleteFileIds)
	{
		this.deleteFileIds = deleteFileIds;
	}

	@Override
	public String toString() {
		return GSONUtil.toJson(this);
	}

	
	
	

}
