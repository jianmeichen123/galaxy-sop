package com.galaxyinternet.model.operationLog;

/**
 * @Description: sop流程中操作日志的规范类
 * @author keifer
 * @date 2016年3月16日
 */
public enum OperationLogType {

	/*项目操作日志的配置-------------------开始-----------------------------------------------------------*/
	//接触访谈阶段
	//添加访谈的弹出框中点击‘保存’
	ADD_INTERVIEW("/galaxy/project/stageChange/"+UrlNumber.one, OperType.ADD.getOperationType(), "访谈记录",SopStage.TOUCH_INTERVIEW.getStageName()), 
	ADD_INTERVIEW_FILE_RECORD("/galaxy/project/progress/addFileInterview", OperType.ADD.getOperationType(), "访谈记录",SopStage.TOUCH_INTERVIEW.getStageName()), 
	ADD_INTERVIEW_RECORD("/galaxy/project/progress/addInterview", OperType.ADD.getOperationType(), "访谈记录",SopStage.TOUCH_INTERVIEW.getStageName()), 
	
	//点击‘启动内部评审’
	SUBMIT_INNER_REVIEW_SCHEDULE("/galaxy/project/startReview", OperType.SUBMIT.getOperationType(), "内部评审",SopStage.TOUCH_INTERVIEW.getStageName()), 
	
	//内部评审阶段
	//添加会议记录的弹出框中点击‘保存’。
	INNER_REVIEW_SCHEDULE("/galaxy/project/stageChange/"+UrlNumber.two, OperType.ADD.getOperationType(), "会议记录",SopStage.INNER_REVIEW_SCHEDULE.getStageName()), 
	ADD_MEETING_FILE_ONE("/galaxy/project/progress/addfilemeet/"+UrlNumber.one, OperType.ADD.getOperationType(), "会议记录",SopStage.INNER_REVIEW_SCHEDULE.getStageName()),
	ADD_MEETING_ONE("/galaxy/project/progress/addmeet/"+UrlNumber.one, OperType.ADD.getOperationType(), "会议记录",SopStage.INNER_REVIEW_SCHEDULE.getStageName()), 

	
	//CEO评审阶段
	//添加会议记录的弹出框中点击‘保存’。
	CEO_REVIEW_SCHEDULE("/galaxy/project/stageChange/"+UrlNumber.three, OperType.ADD.getOperationType(), "会议记录",SopStage.CEO_REVIEW_SCHEDULE.getStageName()), 
	ADD_MEETING_FILE_TWO("/galaxy/project/progress/addfilemeet/"+UrlNumber.two, OperType.ADD.getOperationType(), "会议记录",SopStage.CEO_REVIEW_SCHEDULE.getStageName()),
	ADD_MEETING_TWO("/galaxy/project/progress/addmeet/"+UrlNumber.two, OperType.ADD.getOperationType(), "会议记录",SopStage.CEO_REVIEW_SCHEDULE.getStageName()), 

	//点击申请立项会排期
	ADD_PROJECT_SCHEDULE("/galaxy/project/ges", OperType.SUBMIT.getOperationType(), "立项会排期",SopStage.CEO_REVIEW_SCHEDULE.getStageName()), 
	
	//立项会阶段
	//添加会议记录的弹出框中点击‘保存’。
	ADD_MEETING_RECORD("/galaxy/project/stageChange/"+UrlNumber.four, OperType.ADD.getOperationType(), "会议记录",SopStage.PROJECT_SCHEDULE.getStageName()), 
	ADD_MEETING_FILE_THREE("/galaxy/project/progress/addfilemeet/"+UrlNumber.three, OperType.ADD.getOperationType(), "会议记录",SopStage.PROJECT_SCHEDULE.getStageName()),
	ADD_MEETING_THREE("/galaxy/project/progress/addmeet/"+UrlNumber.three, OperType.ADD.getOperationType(), "会议记录",SopStage.PROJECT_SCHEDULE.getStageName()), 

	//点击申请立项会排期
	//APPALY_PROJECT_SCHEDULE("", OperType.SUBMIT.getOperationType(), "立项会排期",SopStage.PROJECT_SCHEDULE.getStageName()), 

	//投资意向书阶段
	//下载投资意向书模版	
	DOWNLOAD_INVESTMENT_TEMPLATE("/galaxy/template/download/"+UrlNumber.five, OperType.DOWNLOAD.getOperationType(), "投资意向书模版",SopStage.INVESTMENT_INTENT.getStageName()),
	//上传投资意向书
	UPLOAD_INVESTMENT_TEMPLATE("/galaxy/project/stageChange/"+UrlNumber.five, OperType.UPLOAD.getOperationType(), "投资意向书",SopStage.INVESTMENT_INTENT.getStageName()),
	//更新投资意向书
	UPDATE_INVESTMENT_TEMPLATE("", OperType.UPDATE.getOperationType(), "投资意向书",SopStage.INVESTMENT_INTENT.getStageName()),
	//上传签署证明	
	SIGNED_CERTIFICATE("/galaxy/project/stageChange/"+UrlNumber.six, OperType.UPLOAD.getOperationType(), "签署证明",SopStage.INVESTMENT_INTENT.getStageName()),

	//尽职调查阶段
	//上传业务尽职调查报告	  
	UPLOAD_DUE_DILIGENCE_INVESTIGATION("/galaxy/project/stageChange/"+UrlNumber.seven, OperType.UPLOAD.getOperationType(), "业务尽职调查报告",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
	//点击申请投决会排期	
	APPLY_VOTE_SCHEDULE("/galaxy/project/smp", OperType.SUBMIT.getOperationType(), "投决会排期",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
	//尽职调查报告催办
	REMIND_VOTE_SCHEDULE("", OperType.REMINDER.getOperationType(), "法务尽职调查报告",SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName()),
	
	//投决会阶段
	//添加会议纪要的弹出框中点击“保存”
	ADD_VOTE_DECISION_MEETING_NOTE("/galaxy/project/stageChange/"+UrlNumber.eight, OperType.ADD.getOperationType(), "会议记录",SopStage.VOTE_DECISION_MEETING.getStageName()),
	ADD_MEETING_FILE_FOUR("/galaxy/project/progress/addfilemeet/"+UrlNumber.four, OperType.ADD.getOperationType(), "会议记录",SopStage.VOTE_DECISION_MEETING.getStageName()),
	ADD_MEETING_FOUR("/galaxy/project/progress/addmeet/"+UrlNumber.four, OperType.ADD.getOperationType(), "会议记录",SopStage.VOTE_DECISION_MEETING.getStageName()), 
	//APPLY_VOTE_DECISION_MEETING_SCHEDULE("", OperType.SUBMIT.getOperationType(), "投决会排期",SopStage.VOTE_DECISION_MEETING.getStageName()),
	
	//投资协议阶段
	//下载投资协议模版	
	DOWNLOAD_INVESTMENT_AGREEMENT_TEMPLATE("/galaxy/template/download/"+UrlNumber.eight, OperType.DOWNLOAD.getOperationType(), "投资协议模版",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//投资协议上传
	INVESTMENT_AGREEMENT_LOOK("/galaxy/project/stageChange/"+UrlNumber.nine, OperType.UPLOAD.getOperationType(), "投资协议",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//投资协议更新
	UPDATE_INVESTMENT_AGREEMENT("", OperType.UPDATE.getOperationType(), "投资协议",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//股权转让协议上传
	UPLOAD_EQUITY_TRANSFER_AGREEMENT("/galaxy/project/stageChange/"+UrlNumber.ten, OperType.UPLOAD.getOperationType(), "股权转让协议",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//股权转让协议更新
	UPDATE_EQUITY_TRANSFER_AGREEMENT("", OperType.UPDATE.getOperationType(), "股权转让协议",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//股权转让协议签署凭证上传
	UPLOAD_EQUITY_TRANSFER_AGREEMENT_SIGNED_CERTIFICATE("/galaxy/project/stageChange/"+UrlNumber.eleven, OperType.UPLOAD.getOperationType(), "股权转让协议签署凭证",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//股权转让协议签署凭证更新
	UPDATE_EQUITY_TRANSFER_AGREEMENT_SIGNED_CERTIFICATE("", OperType.UPDATE.getOperationType(), "股权转让协议签署凭证",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//投资协议签署凭证上传
	UPLOAD_EQUITY_INVESTMENT_SIGNED_CERTIFICATE("/galaxy/project/stageChange/"+UrlNumber.twelve, OperType.UPLOAD.getOperationType(), "投资协议签署凭证",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	//投资协议签署凭证更新
	UPDATE_EQUITY_INVESTMENT_SIGNED_CERTIFICATE("", OperType.UPDATE.getOperationType(), "投资协议签署凭证",SopStage.INVESTMENT_AGREEMENT.getStageName()),
	
	//股权交割阶段
	//点击资金拨付凭证催办列里的“催办”
	REMIND_FUNDS_SEND_CERTIFICATE("", OperType.REMINDER.getOperationType(), "资金拨付凭证",SopStage.EQUITY_DELIVERY_STAGE.getStageName()),
	//点击工商变更登记凭证催办列里的“催办”
	REMIND_COMPANY_CHANGE_CERTIFICATE("", OperType.REMINDER.getOperationType(), "工商变更登记凭证",SopStage.EQUITY_DELIVERY_STAGE.getStageName()),
	

	
	//更新投资意向书 1
	UPDATE_INVESTMENT_INTENT_FILE("/galaxy/sopFile/commonUploadFile/"+UrlNumber.one, OperType.UPDATE.getOperationType() , "投资意向书",SopStage.INVESTMENT_INTENT.getStageName()),
	/*项目操作日志的配置------------------------------------结束----------------------------------------------------------*/
	
	
	//------------------------------------------------忧愁的分割线-----------------------------------------------------//
	
	
	/*创意操作日志的配置-------------------开始---------------------------------------------------------------------------*/
	
	//添加可信性报告
	CY_ADD_REPORT("/galaxy/idea/ideaUpReport/"+UrlNumber.two, OperType.ADD.getOperationType() , "可行性报告",SopStage.CY_DY.getStageName()),
	//更新
	CY_UPDATE_REPORT("/galaxy/idea/ideaUpReport/"+UrlNumber.one, OperType.UPDATE.getOperationType() , "可行性报告",SopStage.CY_DY.getStageName()),
	//启动立项会
	CY_START_LXH("/galaxy/idea/ideaStartMeet", OperType.SUBMIT.getOperationType() , "启动立项会",SopStage.CY_DY.getStageName()),
	
	
	//添加会议记录
	CY_ADD_MEET("/galaxy/idea/saveCyMeetRecord", OperType.ADD.getOperationType() , "会议记录",SopStage.CY_LXH.getStageName()),
	//创建项目
	CY_ADD_PRO("/galaxy/idea/createProject", OperType.ADD.getOperationType() , "项目",SopStage.CY_PRO.getStageName()),
	//编辑项目名称
	CY_EDIT_PRO("/galaxy/idea/editProjectName", OperType.UPDATE.getOperationType() , "项目",SopStage.CY_PRO.getStageName()),

	//认领创意
	CY_CLAIMT_IDEA("/galaxy/idea/updateIdea", OperType.UPDATE.getOperationType() , "创意",SopStage.CY_DY.getStageName()),	
	//添加创意
	CY_ADD_IDEA("/galaxy/idea/addIdea/"+UrlNumber.one, OperType.ADD.getOperationType() , "创意",SopStage.CY_INFO.getStageName()),
	//待认领阶段编辑创意
	CY_EDIT_DYL_IDEA("/galaxy/idea/addIdea/"+UrlNumber.two, OperType.UPDATE.getOperationType(), "创意",SopStage.CY_DRL.getStageName()),
	//调研阶段编辑创意
	CY_EDIT_DY_IDEA("/galaxy/idea/addIdea/"+UrlNumber.three, OperType.UPDATE.getOperationType(), "创意",SopStage.CY_DY.getStageName()),
	//创建立项会阶段编辑创意
	CY_EDIT_LXH_IDEA("/galaxy/idea/addIdea/"+UrlNumber.four, OperType.UPDATE.getOperationType(), "创意",SopStage.CY_LXH.getStageName()),
	//放弃创意
	CY_EDIT_FQ_IDEA("/galaxy/idea/giveUp", OperType.UPDATE.getOperationType(), "创意",SopStage.CY_INFO.getStageName()),
	//搁置阶段编辑创意
	CY_EDIT_GZ_IDEA("/galaxy/idea/addIdea/"+UrlNumber.five, OperType.UPDATE.getOperationType(), "创意",SopStage.CY_GZ.getStageName());
	


	/*创意操作日志的配置-------------------结束---------------------------------------------------------------------------*/
		
	private OperationLogType(String uniqueKey, String type, String content, String sopstage) {
		this.uniqueKey = uniqueKey;
		this.type = type;
		this.content = content;
		this.sopstage = sopstage;
	}

	public static OperationLogType getObject(String uniqueKey) {
		OperationLogType[] types = OperationLogType.values();
		OperationLogType result = null;
		for (OperationLogType type : types) {
			if (type.getUniqueKey()!=null && type.getUniqueKey().trim().length()>0 && uniqueKey.contains(type.getUniqueKey())) {
				result = type;
				break;
			}
		}
		return result;
	}

	private String uniqueKey;
	private String type;//操作类型
	private String content;//内容
	private String sopstage;//sop阶段

	public String getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public String getSopstage() {
		return sopstage;
	}

}
