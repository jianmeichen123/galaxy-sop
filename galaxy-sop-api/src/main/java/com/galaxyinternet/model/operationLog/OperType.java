package com.galaxyinternet.model.operationLog;

/**
 * @Description: 操作类型 <br/>
 * @author keifer
 * @date 2016年3月16日
 */
public enum OperType {

	ADD("添加"), SUBMIT("提交"), DOWNLOAD("下载"), UPLOAD("上传"), UPDATE("更新"), REMINDER("催办"),CLAIMT("领取"),FINISH("完成");

	private String operationType;

	private OperType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationType() {
		return operationType;
	}

}
