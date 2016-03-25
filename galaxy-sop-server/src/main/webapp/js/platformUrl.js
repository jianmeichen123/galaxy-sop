var Constants = {
	platformEndpointURL : endpointObj["galaxy.project.platform.endpoint"],
	reportEndpointURL : endpointObj["galaxy.project.report.endpoint"]
}
var sopContentUrl = "http://fx.dev.galaxyinternet.com/sop";
//var sopContentUrl = "http://127.0.0.1:8888/sop";
//var sopContentUrl = "http://127.0.0.1:8082/";

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
	createMenus : sopContentUrl + "/galaxy/common/menu/",
	/**
	 * 获取项目编码接口地址
	 */
	getProjectCode : sopContentUrl + "/galaxy/project/cpc",
	/**
	 * 添加项目
	 */
	addProject : sopContentUrl + "/galaxy/project/ap",
	/**
	 * 关闭项目
	 */
	closeProject : sopContentUrl + "/galaxy/project/breakpro/",
	/**
	 * 获取项目详情
	 */
	detailProject : sopContentUrl + "/galaxy/project/sp/",
	/**
	 * 修改项目信息
	 */
	updateProject : sopContentUrl + "/galaxy/project/up",
	/**
	 * 添加团队成员
	 */
	addPerson : sopContentUrl + "/galaxy/project/app",
	/**
	 * 跳转到首页
	 */
	toIndex : Constants.platformEndpointURL + "/galaxy/redirect",
	/**
	 * 查询模板列表
	 */
	queryTemplate : sopContentUrl + "/galaxy/template/queryTemplate",
	/**
	 * 查询模板相关数据
	 */
	getTempRelatedData : sopContentUrl + "/galaxy/template/getRelatedData",
	/**
	 * 模板上传
	 */
	tempUpload : sopContentUrl + "/galaxy/template/upload",
	/**
	 * 模板保存
	 */
	tempSave : sopContentUrl + "/galaxy/template/save",
	/**
	 * 模板下载
	 */
	tempDownload : sopContentUrl + "/galaxy/template/download",
	
	/**
	 * 修改团队成员
	 */
	updatePerson : sopContentUrl + "/galaxy/project/upp",
	
	/**
	 * 立项排期所有
	 */
    moreProjectMeeting : sopContentUrl + "/galaxy/home/moreProjectMeeting",
	/**
	 * top5立项会排期
	 */
	top5ProjectMeeting : sopContentUrl + "/galaxy/home/top5ProjectMeeting",
	/**
	 * 投诀会排期top5
	 */
	ProjectVoteWill: sopContentUrl + "/galaxy/home/ProjectVoteWill",
	/**
	 * 投诀会排期所有
	 */
	moreProjectVoteWill: sopContentUrl + "/galaxy/home/moreProjectVoteWill",
	/**
	 * 紧急任务总数
	 */
	totalUrgent: sopContentUrl + "/galaxy/soptask/totalUrgent",
	/**
	 * 待办任务总数
	 */
	totalMission: sopContentUrl + "/galaxy/soptask/totalMission",
	
	
	/**gxc
	 * 主页的待办任务:
	 */		
	soptaskshouye: sopContentUrl+"/galaxy/soptask/taskListByRole",
	
	/**-------首页相关 end  */
	
	/**
	 * 添加股权结构
	 */
	addStock : sopContentUrl + "/galaxy/projectShares/addProjectShares",
	
	/**
	 * 修改股权结构
	 */
	updateStock : sopContentUrl + "/galaxy/projectShares/updateProjectShares",
	
	
	/**
	 * tab -> 内评会： 
	 */
	tab_lphtc : sopContentUrl + "/galaxy/project/lphtc",
	
	/**
	 * tab -> ceo评审：  
	 */
	tab_ceotc : sopContentUrl + "/galaxy/project/ceopstc",
	
	/**
	 * tab -> 立项会 申请排期： 
	 */
	tab_lxhpq : sopContentUrl + "/galaxy/project/progress/proSchedule",
	/**
	 * tab -> 立项会： 
	 */
	tab_lxhtc : sopContentUrl + "/galaxy/project/lxhtc",
	/**
	 * tab -> 投决会： 
	 */
	tab_tjhtc : sopContentUrl + "/galaxy/project/tjhtc",
	
	/**
	 * 获取登录人的项目（会议类型、访谈阶段）：
	 */
	getUserPro : sopContentUrl + "/galaxy/project/progress/queryPerPro",
	
	/**
	 * 访谈添加：附件上传
	 */
	saveInteverView : sopContentUrl + "/galaxy/project/progress/addInterview",
	/**
	 * 访谈添加 弹出 page
	 */
	toViewAdd : sopContentUrl + "/galaxy/project/progress/interViewAdd",
	/**
	 * 访谈查询 page
	 */
	selectViewPage : sopContentUrl + "/galaxy/project/progress/queryInterview",
	/**
	 * 访谈添加：
	 */
	saveView : sopContentUrl + "/galaxy/project/progress/addInterview",
	/**
	 * 访谈添加,附加附件：
	 */
	saveViewFile : sopContentUrl + "/galaxy/project/progress/addFileInterview",
	
	/**
	 * 会议添加 弹出 page
	 */
	tomeetAdd : sopContentUrl + "/galaxy/project/progress/meetAddView",
	/**
	 * 会议添加：
	 */
	saveMeet : sopContentUrl + "/galaxy/project/progress/addmeet",
	/**
	 * 会议+附件添加：
	 */
	saveMeetFile : sopContentUrl + "/galaxy/project/progress/addfilemeet",
	/**
	 * 会议查询：
	 */
	selectMeetPage : sopContentUrl + "/galaxy/project/progress/queryMeet",
	/**
	 * 根据findByParentCode查询数据字典
	 */
	dictFindByParentCode : sopContentUrl + "/galaxy/sopFile/getDictByParent",
	/**
	 * 文档查询-无分页
	 */
	queryFile : sopContentUrl+"/galaxy/sopFile/query",
	/**
	 * 任务处理-上传文档
	 */
	uploadFile2Task : sopContentUrl+"/galaxy/taskprocess/uploadFile",
	/**
	 * 任务处理-上传签署凭证
	 */
	uploadVoucher2Task : sopContentUrl+"/galaxy/taskprocess/uploadVoucher",
	/**
	 * 档案列表查询(不分页)
	 */
	searchSopFileListWithoutPage : sopContentUrl + "/galaxy/sopFile/searchSopFileListWithoutPage",
	/**
	 * 档案上传
	 */
	simpleSopFileUpload : sopContentUrl + '/galaxy/sopFile/simpleUpload',
	/**
	 * 启动内部评审
	 */
	startReview : sopContentUrl + "/galaxy/project/startReview/",
	/**
	 * 我的项目-文档下载
	 */
	downLoadFile : sopContentUrl+"/galaxy/sopFile/downloadFile",
	/**
	 * 获取尽职调查文件列表
	 */
	getFileList : sopContentUrl + "/galaxy/project/progress/proFileInfo/",
	/**
	 * 更新任务状态
	 */
	updateTaskStatus: sopContentUrl + "/galaxy/soptask/updateTaskStatus",
	/**
	 * 项目阶段过程的记录保存
	 */
	stageChange : sopContentUrl + "/galaxy/project/stageChange?sid="+sessionId + "&guid="+userId,
	/**
	 * 申请立项会操作
	 */
	toEstablishStage : sopContentUrl + "/galaxy/project/ges/",
	inLxmeetingPool : sopContentUrl + "/galaxy/project/inlx/",
	/**
	 * 申请投决会
	 */
	inTjh : sopContentUrl + "/galaxy/project/smp/",
	inSureMeetingPool : sopContentUrl + "/galaxy/project/intj/",
	/**
	 * 我的日程
	 */
	toShedule : sopContentUrl + "/galaxy/sopUserSchedule/scheduleList",
	/**
	 * 档案列表
	 */
	searchSopFileList : sopContentUrl + "/galaxy/sopFile/searchSopFileList",
	/**
	 * 项目查询
	 */
	searchProject : sopContentUrl + "/galaxy/project/spl",
	/**
	 * 获取部门字典
	 */
	getDepartMentDict : sopContentUrl + "/galaxy/sopFile/getDepartmentDict",
	/**
	 * 档案上传通用
	 */
	commonUploadFile : sopContentUrl + "/galaxy/sopFile/commonUploadFile",
	
	showTask: sopContentUrl + "/galaxy/soptask",
	
	operationMessageQueryList : sopContentUrl + "/galaxy/operationMessage/queryList",
	
	operationMessageRemind : sopContentUrl + "/galaxy/operationMessage/remind",
	MessageIndex : sopContentUrl + "/galaxy/operationMessage/index",
	
	/***
	 * 日程显示三条记录
	 */
	sheduleMoreThree : sopContentUrl + "/galaxy/sopUserSchedule/selectSopUserSchedule/1",
	
	/***
	 * 保存日程
	 */
	saveShedule : sopContentUrl + "/galaxy/sopUserSchedule/addOrUpdateSopUserSchedule/",
	
	/**
	 * 获取日程
	 */
	sheduleInfo : sopContentUrl + "/galaxy/sopUserSchedule/getSchedule/",
	
	/**
	 * 删除日程
	 */
	deleteShedule : sopContentUrl + "/galaxy/sopUserSchedule/delete/",
	
	/**
	 * 添加团队成员跳转页面
	 */
	addPersonView : sopContentUrl + "/galaxy/addperson",
	/**
	 * 修改团队成员
	 */
	updatePerView : sopContentUrl + "/galaxy/project/updatePro/",
	
	/***
	 * 添加股权结构页面
	 */
	addSharesView : sopContentUrl + "/galaxy/projectShares/addShares",
	
	/**
	 * 修改股权结构面板
	 */
	editStockView : sopContentUrl + "/galaxy/projectShares/updateShare/",
	/***
	 * 获取股权结构列表
	 */
	projectSharesList : sopContentUrl + "/galaxy/projectShares/selectProjectShares",
	
	/**
	 * 获取团队成员列表
	 */
	projectPersonList : sopContentUrl + "/galaxy/project/queryProjectPerson",
	/**
	 * 删除股权结构
	 */
	deleteProjectShares : sopContentUrl + "/galaxy/projectShares/deleteProjectShares/",
	
	/***
	 * 删除团队成员
	 */
	deletePPerson : sopContentUrl + "/galaxy/project/dpp/" ,
	
	/**
	 * 完善简历
	 */
	addPersonHr :  sopContentUrl + "/galaxy/hrjl/addPersonHr" ,
	
	/**
	 * 完善简历回显
	 */
	toaddPersonHr:sopContentUrl + "/galaxy/hrjl/toaddPersonHr/" ,
	/**
	 * 跳到完善简历页面
	 */
	toWanshan: sopContentUrl + "/galaxy/project/queryPersonListToTask",
	/**
	 * 跳到完善简历页面(弹出)
	 */
	personHr: sopContentUrl+"/galaxy/hrjl/resumetcc",
	/**
	 * 催办
	 */
	taskUrged:sopContentUrl + "/galaxy/taskprocess/taskUrged",
	
	tempSendMail:sopContentUrl + "/galaxy/template/sendMail" ,
	/**
	 * Ajax判断项目名称，组织机构代码是否重复
	 */
	checkProject:sopContentUrl + "/galaxy/project/checkProject"
}

/**
 * how to use? location.href = platformUrl.login
 */
