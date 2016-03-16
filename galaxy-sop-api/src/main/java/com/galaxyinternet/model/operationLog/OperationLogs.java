package com.galaxyinternet.model.operationLog;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class OperationLogs extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
    private Long uid;

    private String uname;

    private Long userRoleid;

    private String userRole;

    private Long userDepartid;

    private String departName;

    private String operationType;
    private String operationTypeStr;

    private Long projectId;

    private String projectName;

    private String operationContent;
    private String operationContentStr;


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public Long getUserRoleid() {
        return userRoleid;
    }

    public void setUserRoleid(Long userRoleid) {
        this.userRoleid = userRoleid;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public Long getUserDepartid() {
        return userDepartid;
    }

    public void setUserDepartid(Long userDepartid) {
        this.userDepartid = userDepartid;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName == null ? null : departName.trim();
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
        //字典 操作类别  1 系统;2 项目;3 访谈;4 会议;5 任务;6 简历;7 日程;8 创意;9 档案;
        if(operationType!=null){
        	if(operationType.equals("1")){
        		operationTypeStr = "系统";
        	}else if(operationType.equals("2")){
        		operationTypeStr = "项目";
        	}else if(operationType.equals("3")){
        		operationTypeStr = "访谈";
        	}else if(operationType.equals("4")){
        		operationTypeStr = "会议";
        	}else if(operationType.equals("5")){
        		operationTypeStr = "任务";
        	}else if(operationType.equals("6")){
        		operationTypeStr = "简历";
        	}else if(operationType.equals("7")){
        		operationTypeStr = "日程";
        	}else if(operationType.equals("8")){
        		operationTypeStr = "创意";
        	}else if(operationType.equals("9")){
        		operationTypeStr = "档案";
        	}
    	}
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent == null ? null : operationContent.trim();
        //字典 操作类别  1 系统;2 项目;3 访谈;4 会议;5 任务;6 简历;7 日程;8 创意;9 档案;
        if(operationContent!=null){
        	if(operationContent.equals("101")){
        		operationContentStr = "登陆";
        	}else if(operationContent.equals("102")){
        		operationContentStr = "登出";
        		
        	}else if(operationContent.equals("201")){
        		operationContentStr = "创建项目";
        	}else if(operationContent.equals("202")){
        		operationContentStr = "修改项目信息";
        	}else if(operationContent.equals("203")){
        		operationContentStr = "修改项目成员";
        	}else if(operationContent.equals("204")){
        		operationContentStr = "申请立项会排期";
        	}else if(operationContent.equals("205")){
        		operationContentStr = "申请 投决会排期";
        	}else if(operationContent.equals("206")){
        		operationContentStr = "修改项目股权";
        	}else if(operationContent.equals("207")){
        		operationContentStr = "查询项目";
        		
        	}else if(operationContent.equals("301")){
        		operationContentStr = "添加访谈记录";
        	}else if(operationContent.equals("302")){
        		operationContentStr = "查询访谈记录";
        		
        	}else if(operationContent.equals("401")){
        		operationContentStr = "添加会议记录";
        	}else if(operationContent.equals("402")){
        		operationContentStr = "查询会议记录";
        		
        	}else if(operationContent.equals("501")){
        		operationContentStr = "认领任务";
        	}else if(operationContent.equals("502")){
        		operationContentStr = "完成任务";
        		
        	}else if(operationContent.equals("601")){
        		operationContentStr = "新建简历";
        	}else if(operationContent.equals("602")){
        		operationContentStr = "修改简历";
        		
        	}else if(operationContent.equals("701")){
        		operationContentStr = "添加日程";
        	}else if(operationContent.equals("702")){
        		operationContentStr = "修改日程";
        		
        	}else if(operationContent.equals("801")){
        		operationContentStr = "新建创意";
        	}else if(operationContent.equals("802")){
        		operationContentStr = "认领创意";
        		
        	}else if(operationContent.equals("901")){
        		operationContentStr = "更新档案";
        	}
    	}
    }

	public String getOperationTypeStr() {
		return operationTypeStr;
	}

	public void setOperationTypeStr(String operationTypeStr) {
		this.operationTypeStr = operationTypeStr;
	}

	public String getOperationContentStr() {
		return operationContentStr;
	}

	public void setOperationContentStr(String operationContentStr) {
		this.operationContentStr = operationContentStr;
	}

    
}