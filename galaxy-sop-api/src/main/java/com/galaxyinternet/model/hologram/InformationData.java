package com.galaxyinternet.model.hologram;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.GSONUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class InformationData extends PagableEntity {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("项目")
	private String projectId;
	
	private int flag;//0:正常数据编辑;1:表格数据处理;2:固定表格处理
	@ApiModelProperty(value="普通类型",example="[{titleId: 1108, tochange: 'true', resultId: null, type: 14, value: '1123'},"+
	"{titleId: 1120, tochange: 'true', resultId: null, type: 14, value: '2254'},"+
	"{titleId: 1121, tochange: 'true', resultId: null, type: 1, remark1: ''},"+
	"{titleId: 1202, tochange: 'true', resultId: null, type: 8, remark1: '商业模式'},"+
	"{titleId: 1122, tochange: 'true', resultId: null, type: 1, remark1: ''},"+
	"{titleId: 1123, tochange: 'true', resultId: null, type: 1, remark1: ''},"+
	"{titleId: 1124, tochange: 'true', resultId: null, type: 1, remark1: ''},"+
	"{titleId: 1125, tochange: 'true', resultId: null, type: 1, remark1: ''},"+
	"{titleId: 1126, tochange: 'true', resultId: null, type: 1, remark1: ''},"+
	"{titleId: 1118, tochange: 'true', resultId: '',   type: 23, value: '104'},"+
	"{titleId: 1916, tochange: 'true', resultId: null, type: 19, remark1: '12'},"+
	"{titleId: 1917, tochange: 'true', resultId: null, type: 19, remark1: '12'},"+
	"{titleId: 1943, tochange: 'true', resultId: null, type: 19, remark1: '100'}]")
	private List<InformationModel> infoModeList;
	@ApiModelProperty(value="固定表格",hidden=true)
	private List<FixedTableModel> infoFixedTableList;
	@ApiModelProperty(value="动态表格-删除的记录",hidden=true)
	private Set<String> deletedRowIds;  //适用table表
	@ApiModelProperty(value="动态表格",example=""
			+ "[{ code: 'team-members',"
			+ "titleId: '1303', "
			+ "subCode: 'team-members',"
			+ "field1:'12',"
			+ "field2:'1353',"
			+ "field4:'1212',"
			+ "field5:'1374',"
			+ "field6:'12'}]")
	private List<TableModel>     infoTableModelList;
	@ApiModelProperty(value="分数",hidden=true)
	private List<InformationScore> scoreList;
	@ApiModelProperty(value="文件/图片",hidden=true)
	private List<InformationFile> infoFileList;
	@ApiModelProperty(value="删除文件集合",hidden=true)
	private Set<Long> deleteFileIds;
	@ApiModelProperty(value="删除结果集合",hidden=true)
	private Set<String> deletedResultTids;  //适用result表
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<InformationModel> getInfoModeList() {
		return infoModeList == null? new ArrayList<InformationModel>():infoModeList;
	}

	public void setInfoModeList(List<InformationModel> infoModeList) {
		this.infoModeList = infoModeList;
	}

	public List<FixedTableModel> getInfoFixedTableList() {
		return infoFixedTableList;
	}

	public void setInfoFixedTableList(List<FixedTableModel> infoFixedTableList) {
		this.infoFixedTableList = infoFixedTableList;
	}

	public List<TableModel> getInfoTableModelList() {
		return infoTableModelList;
	}

	public void setInfoTableModelList(List<TableModel> infoTableModelList) {
		this.infoTableModelList = infoTableModelList;
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

	public List<InformationScore> getScoreList()
	{
		return scoreList;
	}

	public void setScoreList(List<InformationScore> scoreList)
	{
		this.scoreList = scoreList;
	}

	public List<InformationFile> getInfoFileList()
	{
		return infoFileList;
	}

	public void setInfoFileList(List<InformationFile> infoFileList)
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
