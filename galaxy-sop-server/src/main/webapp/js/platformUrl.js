var Constants = {
	platformEndpointURL : endpointObj["galaxy.project.platform.endpoint"],
	reportEndpointURL : endpointObj["galaxy.project.report.endpoint"],
	sopEndpointURL : endpointObj["galaxy.project.sop.endpoint"],
}

var platformUrl = {
		
	/**
	 * 获取token
	*/
	getToken: Constants.platformEndpointURL+ "/galaxy/user/formtoken",
	/**
	 * 跳转到登录
	 */
	toLoginPage:Constants.platformEndpointURL +"/galaxy/userlogin/toLogin",
	/**
	 * 注销
	 */
	logout:Constants.platformEndpointURL +"/galaxy/userlogin/logout",
	/**
	 * 验证登录
	 */
	createMenus : Constants.sopEndpointURL + "/galaxy/common/menu/",
	/**
	 * 获取项目编码接口地址
	 */
	getProjectCode : Constants.sopEndpointURL + "/galaxy/project/cpc",
	/**
	 * 添加项目
	 */
	addProject : Constants.sopEndpointURL + "/galaxy/project/ap",
	/**
	 * 关闭项目
	 */
	closeProject : Constants.sopEndpointURL + "/galaxy/project/breakpro/",
	/**
	 * 获取事业部——高管查询select
	 */
	queryCheckLine : Constants.sopEndpointURL + "/galaxy/project/queryCheckLine/",
	/**
	 * 获取项目详情
	 */
	detailProject : Constants.sopEndpointURL + "/galaxy/project/sp/",
	/**
	 * 修改项目信息
	 */
	updateProject : Constants.sopEndpointURL + "/galaxy/project/up",
	/**
	 * 添加团队成员
	 */
	addPerson : Constants.sopEndpointURL + "/galaxy/project/app",
	/**
	 * 跳转到首页
	 */
	toIndex : Constants.platformEndpointURL + "/galaxy/redirect",
	/**
	 * 查询模板列表
	 */
	queryTemplate : Constants.sopEndpointURL + "/galaxy/template/queryTemplate",
	/**
	 * 查询模板相关数据
	 */
	getTempRelatedData : Constants.sopEndpointURL + "/galaxy/template/getRelatedData",
	/**
	 * 模板上传
	 */
	tempUpload : Constants.sopEndpointURL + "/galaxy/template/upload",
	/**
	 * 模板保存
	 */
	tempSave : Constants.sopEndpointURL + "/galaxy/template/save",
	/**
	 * 模板下载
	 */
	tempDownload : Constants.sopEndpointURL + "/galaxy/template/download",
	
	/**
	 * 修改团队成员
	 */
	updatePerson : Constants.sopEndpointURL + "/galaxy/project/upp",
	/**
	 * Ceo评审排期top5
	 */
	top5CeoPsMeeting: Constants.sopEndpointURL + "/galaxy/home/top5CeoPsMeeting",
	/**
	 * Ceo评审排期所有
	 */
	moreProjectCeoPsWill: Constants.sopEndpointURL + "/galaxy/home/moreCeoPsMeeting",
	
	/**
	 * 立项排期所有
	 */
    moreProjectMeeting : Constants.sopEndpointURL + "/galaxy/home/moreProjectMeeting",
	/**
	 * top5立项会排期
	 */
	top5ProjectMeeting : Constants.sopEndpointURL + "/galaxy/home/top5ProjectMeeting",
	/**
	 * 投诀会排期top5
	 */
	ProjectVoteWill: Constants.sopEndpointURL + "/galaxy/home/ProjectVoteWill",
	/**
	 * 投诀会排期所有
	 */
	moreProjectVoteWill: Constants.sopEndpointURL + "/galaxy/home/moreProjectVoteWill",
	/**
	 * 紧急任务总数
	 */
	totalUrgent: Constants.sopEndpointURL + "/galaxy/soptask/totalUrgent",
	/**
	 * 待办任务总数
	 */
	totalMission: Constants.sopEndpointURL + "/galaxy/soptask/totalMission",
	
	
	/**gxc
	 * 主页的待办任务:
	 */		
	soptaskshouye: Constants.sopEndpointURL+"/galaxy/soptask/taskListByRole",
	
	/**-------首页相关 end  */
	
	/**
	 * 添加股权结构
	 */
	addStock : Constants.sopEndpointURL + "/galaxy/projectShares/addProjectShares",
	
	/**
	 * 修改股权结构
	 */
	updateStock : Constants.sopEndpointURL + "/galaxy/projectShares/updateProjectShares",
	
	
	/**
	 * tab -> 内评会： 
	 */
	tab_lphtc : Constants.sopEndpointURL + "/galaxy/project/lphtc",
	
	
	/**
	 * tab -> 立项会 申请排期： 
	 */
	tab_lxhpq : Constants.sopEndpointURL + "/galaxy/project/progress/proSchedule",
	/**
	 * 修改:"是否涉及股权转让"状态
	 */
	storeUrl : Constants.sopEndpointURL + "/galaxy/project/store/",
	/**
	 * 获取登录人的项目（会议类型、访谈阶段）：
	 */
	getUserPro : Constants.sopEndpointURL + "/galaxy/project/progress/queryPerPro",
	
	/**
	 * 访谈添加：附件上传
	 */
	saveInteverView : Constants.sopEndpointURL + "/galaxy/project/progress/addInterview",
	/**
	 * 访谈添加 弹出 page
	 */
	toViewAdd : Constants.sopEndpointURL + "/galaxy/project/progress/interViewAdd",
	/**
	 * 访谈查询 page
	 */
	selectViewPage : Constants.sopEndpointURL + "/galaxy/project/progress/queryInterview",
	/**
	 * 访谈添加：
	 */
	saveView : Constants.sopEndpointURL + "/galaxy/project/progress/addInterview",
	/**
	 * 访谈添加,附加附件：
	 */
	saveViewFile : Constants.sopEndpointURL + "/galaxy/project/progress/addFileInterview",
	
	/**
	 * 会议添加 弹出 page
	 */
	tomeetAdd : Constants.sopEndpointURL + "/galaxy/project/progress/meetAddView",
	/**
	 * 会议添加：
	 */
	saveMeet : Constants.sopEndpointURL + "/galaxy/project/progress/addmeet",
	/**
	 * 会议+附件添加：
	 */
	saveMeetFile : Constants.sopEndpointURL + "/galaxy/project/progress/addfilemeet",
	/**
	 * 会议查询：
	 */
	selectMeetPage : Constants.sopEndpointURL + "/galaxy/project/progress/queryMeet",
	/**
	 * 根据findByParentCode查询数据字典
	 */
	dictFindByParentCode : Constants.sopEndpointURL + "/galaxy/sopFile/getDictByParent",
	/**
	 * 获取页面显示权限
	 */
	sopFileCheckShow : Constants.sopEndpointURL + "/galaxy/sopFile/checkShow",
	/**
	 * 文档查询-无分页
	 */
	queryFile : Constants.sopEndpointURL+"/galaxy/sopFile/query",
	/**
	 * 任务处理-上传文档
	 */
	uploadFile2Task : Constants.sopEndpointURL+"/galaxy/taskprocess/uploadFile",
	/**
	 * 任务处理-上传签署凭证
	 */
	uploadVoucher2Task : Constants.sopEndpointURL+"/galaxy/taskprocess/uploadVoucher",
	/**
	 * 档案列表查询(不分页)
	 */
	searchSopFileListWithoutPage : Constants.sopEndpointURL + "/galaxy/sopFile/searchSopFileListWithoutPage",
	/**
	 * 档案上传
	 */
	simpleSopFileUpload : Constants.sopEndpointURL + '/galaxy/sopFile/simpleUpload',
	/**
	 * 启动内部评审
	 */
	startReview : Constants.sopEndpointURL + "/galaxy/project/startReview/",
	/**
	 * 我的项目-文档下载
	 */
	downLoadFile : Constants.sopEndpointURL+"/galaxy/sopFile/downloadFile",
	/**
	 * 获取尽职调查文件列表
	 */
	getFileList : Constants.sopEndpointURL + "/galaxy/project/progress/proFileInfo/",
	/**
	 * 更新任务状态
	 */
	updateTaskStatus: Constants.sopEndpointURL + "/galaxy/soptask/updateTaskStatus",
	/**
	 * 人法财提交完成
	 */
	submitTask: Constants.sopEndpointURL + "/galaxy/soptask/submitTask",
	/**
	 * 项目阶段过程的记录保存
	 */
	stageChange : Constants.sopEndpointURL + "/galaxy/project/stageChange?sid="+sessionId + "&guid="+userId,
	/**
	 * 申请立项会操作
	 */
	toEstablishStage : Constants.sopEndpointURL + "/galaxy/project/ges/",
	inLxmeetingPool : Constants.sopEndpointURL + "/galaxy/project/inlx/",
	/**
	 * 申请投决会
	 */
	inTjh : Constants.sopEndpointURL + "/galaxy/project/smp/",
	inSureMeetingPool : Constants.sopEndpointURL + "/galaxy/project/intj/",
	/**
	 * 我的日程
	 */
	toShedule : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/scheduleList",
	/**
	 * 档案列表
	 */
	searchSopFileList : Constants.sopEndpointURL + "/galaxy/sopFile/searchSopFileList",
	/**
	 * 项目查询
	 */
	searchProject : Constants.sopEndpointURL + "/galaxy/project/spl",
	/**
	 * 获取部门字典
	 */
	getDepartMentDict : Constants.sopEndpointURL + "/galaxy/sopFile/getDepartmentDict",
	/**
	 * 档案上传通用
	 */
	commonUploadFile : Constants.sopEndpointURL + "/galaxy/sopFile/commonUploadFile",
	
	showTask: Constants.sopEndpointURL + "/galaxy/soptask",
	
	operationMessageQueryList : Constants.sopEndpointURL + "/galaxy/operationMessage/queryList",
	
	operationMessageRemind : Constants.sopEndpointURL + "/galaxy/operationMessage/remind",
	MessageIndex : Constants.sopEndpointURL + "/galaxy/operationMessage/index",
	
	/***
	 * 日程显示三条记录
	 */
	sheduleMoreThree : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/selectSopUserSchedule/1",
	
	/***
	 * 保存日程
	 */
	saveShedule : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/addOrUpdateSopUserSchedule/",
	
	/**
	 * 获取日程
	 */
	sheduleInfo : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/getSchedule/",
	
	/**
	 * 删除日程
	 */
	deleteShedule : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/delete/",
	
	/**
	 * 添加团队成员跳转页面
	 */
	addPersonView : Constants.sopEndpointURL + "/galaxy/addperson",
	/**
	 * 修改团队成员
	 */
	updatePerView : Constants.sopEndpointURL + "/galaxy/project/updatePro/",
	
	/***
	 * 添加股权结构页面
	 */
	addSharesView : Constants.sopEndpointURL + "/galaxy/projectShares/addShares",
	
	/**
	 * 修改股权结构面板
	 */
	editStockView : Constants.sopEndpointURL + "/galaxy/projectShares/updateShare/",
	/***
	 * 获取股权结构列表
	 */
	projectSharesList : Constants.sopEndpointURL + "/galaxy/projectShares/selectProjectShares",
	
	/**
	 * 获取团队成员列表
	 */
	projectPersonList : Constants.sopEndpointURL + "/galaxy/project/queryProjectPerson",
	/**
	 * 删除股权结构
	 */
	deleteProjectShares : Constants.sopEndpointURL + "/galaxy/projectShares/deleteProjectShares/",
	
	/***
	 * 删除团队成员
	 */
	deletePPerson : Constants.sopEndpointURL + "/galaxy/project/dpp/" ,
	
	/**
	 * 完善简历
	 */
	addPersonHr :  Constants.sopEndpointURL + "/galaxy/hrjl/addPersonHr" ,
	
	/**
	 * 完善简历回显
	 */
	toaddPersonHr:Constants.sopEndpointURL + "/galaxy/hrjl/toaddPersonHr/" ,
	/**
	 * 跳到完善简历页面
	 */
	toWanshan: Constants.sopEndpointURL + "/galaxy/project/queryPersonListToTask",
	/**
	 * 跳到完善简历页面(弹出)
	 */
	personHr: Constants.sopEndpointURL+"/galaxy/hrjl/resumetcc",
	/**
	 * 跳到完善简历之项目成员里的个人简历(弹出)
	 */
	
	personHHr: Constants.sopEndpointURL+"/galaxy/hrjl/genresumetcc",
	inCeoMeetingPool : Constants.sopEndpointURL+"/galaxy/project/incm/",
	/**
	 * 催办
	 */
	taskUrged:Constants.sopEndpointURL + "/galaxy/taskprocess/taskUrged",
	
	tempSendMail:Constants.sopEndpointURL + "/galaxy/template/sendMail" ,
	/**
	 * Ajax判断项目名称，组织机构代码是否重复
	 */
	checkProject:Constants.sopEndpointURL + "/galaxy/project/checkProject",
	/**
	 * 档案管理发送邮件窗口
	 */
	showFileMailDialog:Constants.sopEndpointURL + "/galaxy/sopFile/showMailDialog",
	/**
	 * 档案管理发送邮
	 */
	fileSendEmail:Constants.sopEndpointURL + "/galaxy/sopFile/sendMail",
	/**
	 * 排期池中是否存在
	 */
	checkHasPool:Constants.sopEndpointURL + "/galaxy/project/checkHasPool",
	/**
	 * 是否存在通过的会议
	 */
	checkPassMeet:Constants.sopEndpointURL + "/galaxy/project/checkPassMeet",
	/**
	 * 是否存在通过的会议
	 */

	checkCanUse:Constants.sopEndpointURL + "/galaxy/project/checkCanUse",
	/**
	 * 获取阿里云签名
	 */
	getPolicy : Constants.sopEndpointURL + "/galaxy/sopFile/getPolicy",
	/**
	 * 阿里云直连回掉
	 */
	fileCallBack : Constants.sopEndpointURL + "/galaxy/sopFile/fileCallBack",
	/**
	 * 项目详情
	 */
	projectDetail : Constants.sopEndpointURL + "/galaxy/detail/",
	/**
	 * 查询密码
	 */
	checkPwd : Constants.sopEndpointURL + "/galaxy/home/checkPwd",
	/**
	 * 修改密码
	 */
	updatePwd : Constants.sopEndpointURL + "/galaxy/home/updatePwd",
	/**
	 * 更新文件
	 */
	updateFile: Constants.sopEndpointURL + "/galaxy/project/updateCommonFile",
	/**
	 * 获取学历信息
	 */
	getDegreeByParent : Constants.sopEndpointURL + "/galaxy/project/getDegreeByParent",
	/**
	 * 获取创意的事业线信息
	 */
	getIdeaDepartment : Constants.sopEndpointURL + "/galaxy/idea/getDepartment",
	/**
	 * 获取创意-立项会、投决会
	 */
	ideaProjectList : Constants.sopEndpointURL + "/galaxy/idea/ideaProjectList",
	/**
	 * 创意详细
	 */
	ideaGoStage : Constants.sopEndpointURL + "/galaxy/idea/goIdeaStagePage",
	/**
	 * 创意详细
	 */
	ideaGoMeeting : Constants.sopEndpointURL + "/galaxy/idea/goIdeaMeeting",
	
	detailIdea:Constants.sopEndpointURL + "/galaxy/idea/getIdea",
	/**
	 * 获取创意添加编码等信息
	 */
	getAddIdeaInfo : Constants.sopEndpointURL + "/galaxy/idea/getAddIdeaInfo",
	/**
	 * 添加创意
	 */
	addIdea : Constants.sopEndpointURL + "/galaxy/idea/addIdea",
	
	/**
	 * 创意 报告上传更新
	*/
	ideaUpReport: Constants.sopEndpointURL+ "/galaxy/idea/ideaUpReport",
	
	/**
	 * 创意 启动创建立项会
	*/
	ideaStartMeet: Constants.sopEndpointURL+ "/galaxy/idea/ideaStartMeet",
	/**
	 * 创意 添加立项会
	*/
	saveCyMeetRecord: Constants.sopEndpointURL+ "/galaxy/idea/saveCyMeetRecord",
	/**
	 * 创意 报告存在检测
	*/
	ideaCheckHassReport: Constants.sopEndpointURL+ "/galaxy/idea/ideaCheckHassReport",
	/**
	 * 创意 立项会通过查询
	*/
	ideaCheckPassMeet: Constants.sopEndpointURL+ "/galaxy/idea/ideaCheckPassMeet",
	/**
	 * 创意 历史记录权限检测
	*/
	ideaCheckHistory: Constants.sopEndpointURL+ "/galaxy/idea/ideaCheckHistory",
	
	/**
	 * 创意 生成项目
	*/
	idea2Project: Constants.sopEndpointURL+ "/galaxy/idea/createProject",
	/**
	 * 创意 创意认领创意
	*/
	ideaUpdateIdea:Constants.sopEndpointURL+ "/galaxy/idea/giveUp",
	/**
	 * 创意 创意认领创意
	*/
	ideaClimat:Constants.sopEndpointURL+ "/galaxy/idea/updateIdea",
	
	/**
	 * 创意 编辑项目名称
	*/
	ideaEditProjectName: Constants.sopEndpointURL+ "/galaxy/idea/editProjectName",
	/**
	 * 创意 放弃创意
	*/
	GiveUpIdea:Constants.sopEndpointURL+ "/galaxy/idea/goGiveUpPage",
	/**
	 * 更新排期池时间
	 */
	reserveTime: Constants.sopEndpointURL+ "/galaxy/project/updateReserveTime",
	/**
	 * 更新排期池时间
	 */
	updateInterview: Constants.sopEndpointURL+ "/galaxy/project/progress/updateInterview",
	
	/**
	 * 创意 列表
	 */		
	sopcyshouye: Constants.sopEndpointURL+"/galaxy/idea/search",

	/**
	 * 创意 列表
	 */		
	authmenu: Constants.sopEndpointURL+"/galaxy/common/pullAuthority",
	
	updateMeet: Constants.sopEndpointURL+"/galaxy/project/progress/updatemeet",
	/**
	 * 判断角色
	 */		
	judgeRole : Constants.sopEndpointURL+"/galaxy/common/judgeRole",
	/**
	 * 获取商业计划
	 */	
	getBusinessPlanFile : Constants.sopEndpointURL + "/galaxy/sopFile/getBusinessPlanFile"
	
}

/**
 * how to use? location.href = platformUrl.login
 */
