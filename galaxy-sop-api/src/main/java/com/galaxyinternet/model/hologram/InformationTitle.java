package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;
import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class InformationTitle extends PagableEntity {
	private static final long serialVersionUID = 1L;

	private String parentId;

	private String code;

	private String name;

	private Double indexNo;

	private Integer type;

	private Integer sign;

	private String content;

	private String isShow;
	
	private int isValid;

	private String placeholder;
	
	private Long createId;

	private Long updateId;

	private Integer isMust;
	private String valRule; 
	private String valRuleMark;
	private String valRuleFormula;
	
	
	private List<InformationTitle> childList;
	private List<InformationDictionary> valueList;
	private List<InformationResult> resultList;
	private List<InformationFixedTable> fixedTableList;
	private List<InformationListdata> dataList;
	private InformationListdataRemark tableHeader;
	
	
	private Long resultId;
	private String resultIds;
	private String resultContent1;
	private String resultContent2;
	
	//TitleRelate 相关
	private Long titleId;
	private Long relateId;
	private String relateCode;
	private Integer reportType;
	private int relateIsValid;
	private BigDecimal weight;
	
	private InformationGrade informationGrade;
	private List<InformationGrade> informationGrades;
	//分数选项
	private List<ScoreAutoInfo> autoList;
	
	public List<InformationGrade> getInformationGrades() {
		return informationGrades;
	}

	public void setInformationGrades(List<InformationGrade> informationGrades) {
		this.informationGrades = informationGrades;
	}

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

	public Double getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(Double indexNo) {
		this.indexNo = indexNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	
	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
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

	public String getValRuleFormula() {
		return valRuleFormula;
	}

	public void setValRuleFormula(String valRuleFormula) {
		this.valRuleFormula = valRuleFormula;
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

	public List<InformationResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<InformationResult> resultList) {
		this.resultList = resultList;
	}

	public List<InformationFixedTable> getFixedTableList() {
		return fixedTableList;
	}

	public void setFixedTableList(List<InformationFixedTable> fixedTableList) {
		this.fixedTableList = fixedTableList;
	}

	public List<InformationListdata> getDataList() {
		return dataList;
	}

	public void setDataList(List<InformationListdata> dataList) {
		this.dataList = dataList;
	}

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public String getResultIds() {
		return resultIds;
	}

	public void setResultIds(String resultIds) {
		this.resultIds = resultIds;
	}

	public String getResultContent1() {
		return resultContent1;
	}

	public void setResultContent1(String resultContent1) {
		this.resultContent1 = resultContent1;
	}

	public String getResultContent2() {
		return resultContent2;
	}

	public void setResultContent2(String resultContent2) {
		this.resultContent2 = resultContent2;
	}

	public InformationListdataRemark getTableHeader() {
		return tableHeader;
	}

	public void setTableHeader(InformationListdataRemark tableHeader) {
		this.tableHeader = tableHeader;
	}

	public Integer getIsMust() {
		return isMust;
	}

	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}

	public String getValRule() {
		return valRule;
	}

	public void setValRule(String valRule) {
		this.valRule = valRule;
	}

	public String getValRuleMark() {
		return valRuleMark;
	}

	public void setValRuleMark(String valRuleMark) {
		this.valRuleMark = valRuleMark;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	public Long getRelateId() {
		return relateId;
	}

	public void setRelateId(Long relateId) {
		this.relateId = relateId;
	}

	public String getRelateCode() {
		return relateCode;
	}

	public void setRelateCode(String relateCode) {
		this.relateCode = relateCode;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public int getRelateIsValid() {
		return relateIsValid;
	}

	public void setRelateIsValid(int relateIsValid) {
		this.relateIsValid = relateIsValid;
	}

	public InformationGrade getInformationGrade() {
		return informationGrade;
	}

	public void setInformationGrade(InformationGrade informationGrade) {
		this.informationGrade = informationGrade;
	}

	public BigDecimal getWeight()
	{
		return weight;
	}

	public void setWeight(BigDecimal weight)
	{
		this.weight = weight;
	}

	public List<ScoreAutoInfo> getAutoList()
	{
		return autoList;
	}

	public void setAutoList(List<ScoreAutoInfo> autoList)
	{
		this.autoList = autoList;
	}

	
}
