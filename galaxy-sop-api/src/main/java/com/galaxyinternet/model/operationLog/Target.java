package com.galaxyinternet.model.operationLog;

public enum Target {
	
	DUE_DILIGENCE_PERFECTED_PINFO("完善人才信息"),
	DUE_DILIGENCE_RS_JD("人事尽调"),
	DUE_DILIGENCE_CW_JD("财务尽调"),
	DUE_DILIGENCE_CW_FKPZ("打款凭证"),
	DUE_DILIGENCE_FW_JD("法务尽调"),
	DUE_DILIGENCE_YW("业务尽调"),
	DUE_DILIGENCE_FW_GSBG("工商变更");
	
	private Target(String targetName) {
		this.targetName = targetName;
	}

	private String targetName;

	public String getTargetName() {
		return targetName;
	}

}
