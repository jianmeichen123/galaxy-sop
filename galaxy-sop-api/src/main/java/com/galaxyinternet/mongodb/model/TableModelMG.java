package com.galaxyinternet.mongodb.model;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class TableModelMG extends PagableEntity {
	private static final long serialVersionUID = 1L;
	
		private Long id;
	 private Long titleId;

	    private Long parentId;

	    private String code;

	    private String name;

	    private String field1;

	    private String field2;

	    private String field3;

	    private String field4;

	    private String field5;

	    private String field6;

	    private String field7;

	    private String field8;

	    private String field9;

	    private String field10;
	    
	    private String relateFileId;
	    
	    private String updateTimeStr;
	    
	    private String updateTimeSign;
	    

	    
		public String getUpdateTimeSign() {
			return updateTimeSign;
		}

		public void setUpdateTimeSign(String updateTimeSign) {
			this.updateTimeSign = updateTimeSign;
		}

		public String getUpdateTimeStr() {
			return updateTimeStr;
		}

		public void setUpdateTimeStr(String updateTimeStr) {
			this.updateTimeStr = updateTimeStr;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getTitleId() {
			return titleId;
		}

		public void setTitleId(Long titleId) {
			this.titleId = titleId;
		}

		public Long getParentId() {
			return parentId;
		}

		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getField1() {
			return field1;
		}

		public void setField1(String field1) {
			this.field1 = field1;
		}

		public String getField2() {
			return field2;
		}

		public void setField2(String field2) {
			this.field2 = field2;
		}

		public String getField3() {
			return field3;
		}

		public void setField3(String field3) {
			this.field3 = field3;
		}

		public String getField4() {
			return field4;
		}

		public void setField4(String field4) {
			this.field4 = field4;
		}

		public String getField5() {
			return field5;
		}

		public void setField5(String field5) {
			this.field5 = field5;
		}

		public String getField6() {
			return field6;
		}

		public void setField6(String field6) {
			this.field6 = field6;
		}

		public String getField7() {
			return field7;
		}

		public void setField7(String field7) {
			this.field7 = field7;
		}

		public String getField8() {
			return field8;
		}

		public void setField8(String field8) {
			this.field8 = field8;
		}

		public String getField9() {
			return field9;
		}

		public void setField9(String field9) {
			this.field9 = field9;
		}

		public String getField10() {
			return field10;
		}

		public void setField10(String field10) {
			this.field10 = field10;
		}
	    
	    public String getRelateFileId() {
			return relateFileId;
		}

		public void setRelateFileId(String relateFileId) {
			this.relateFileId = relateFileId;
		}

		private List<InformationListdataMG> dataList;
	    
		public List<InformationListdataMG> getDataList() {
			return dataList;
		}

		public void setDataList(List<InformationListdataMG> dataList) {
			this.dataList = dataList;
		}

	    



	
	
	
	
	

}
