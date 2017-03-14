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
	closeProject : Constants.sopEndpointURL + "/galaxy/project/breakpro",
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
	updateProject : Constants.sopEndpointURL + "/galaxy/project/editProject",
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
	 * 项目文档-模块入口
	 */
	toFileList : Constants.sopEndpointURL+"/galaxy/sopFile/toFileList",
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
	
	businessAdjustment :  Constants.sopEndpointURL + "/galaxy/project/businessAdjustment?sid="+sessionId + "&guid="+userId,
	
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
	 * 排期时间跳转
	 */
	popupMeetingList : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/popupMeetingList/",
	/**
	 * 排期时间-内容视图
	 */
	sh : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/sh",
	
	
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
	 * 日程列表
	 */
	shecudle_list : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/queryscheduleList",
	
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
	projectDetail : Constants.sopEndpointURL + "/galaxy/project/detail/",
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
	 * 项目创意 列表页
	 */
	ideaList : Constants.sopEndpointURL + "/galaxy/idea/idealist",
	
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
	getBusinessPlanFile : Constants.sopEndpointURL + "/galaxy/sopFile/getBusinessPlanFile",
	
	/**
	 * 
	 * 编辑公司法人信息
	 */
	saveCompanyInfo : Constants.sopEndpointURL + "/galaxy/project/saveCompanyInfo",
	/**
	 * 融资状态
	 */
	getFinanceStatusByParent : Constants.sopEndpointURL + "/galaxy/project/getFinanceStatusByParent",
	
	/**
	 * 保存公司法人信息
	 * 
	 */
	saveCompanyInfo : Constants.sopEndpointURL + "/galaxy/project/saveCompanyInfo",
	/**
	 * 商业计划页面
	 */	
	toBusinessPlanHistory : Constants.sopEndpointURL + "/galaxy/sopFile/toBusinessPlanHistory",
	/**
	 * 跳转至会议录音详情页面
	 */	
	toInterviewDetail : Constants.sopEndpointURL + "/galaxy/sopFile/toInterviewDetail",
	/**
	 * 跳转至会议录音详情页面
	 */	
	interViewByMeetingId : Constants.sopEndpointURL + "/galaxy/sopFile/interViewByMeetingId",

	
	/**
	 * 商业计划历史查询
	 */	
	searchBusinessPlanHistory : Constants.sopEndpointURL + "/galaxy/sopFile/searchBusinessPlanHistory",
	/**
	 * 查询数据字典子项集合
	 */
	searchDictionaryChildrenItems : Constants.sopEndpointURL + "/galaxy/common/getDictionaryList/",
	/**
	 * 查询事业线
	 */
	getCareerlineList : Constants.sopEndpointURL + "/galaxy/common/getCareerlineList",
	/**
	 * 查询事业线
	 */
	getCareerlineListByRole : Constants.sopEndpointURL + "/galaxy/common/getCareerlineListByRole",
	/**
	 * 根据事业线查询相应的投资经理
	 */
	getUserList : Constants.sopEndpointURL + "/galaxy/common/getUserList/",
	/**
	 * 项目列表页面
	 */
	projectList : Constants.sopEndpointURL + "galaxy/mpl",
	/**
	 * 商业计划上传到session中
	 */
	uploadBpToSession : Constants.sopEndpointURL + "/galaxy/sopFile/uploadBpToSession",
	/**
	 * session中获取商业计划书
	 */
	getBusinessPlanFileInSession : Constants.sopEndpointURL + "/galaxy/sopFile/getBusinessPlanFileInSession",
	/**
	 * 运营分析查询
	 */
	queryPostMeeting : Constants.sopEndpointURL + "/galaxy/project/postOperation/queryPostMeeting",
	/**
	 * 添加投后运营会议弹窗跳转
	 */
	toEditPostMeeting : Constants.sopEndpointURL + "/galaxy/project/postOperation/toEditPostMeeting",
	/**
	 * 保存投后运营会议
	 */
	saveMeeting : Constants.sopEndpointURL + "/galaxy/project/postOperation/saveMeeting",

	/**
	 * 删除投后运营会议
	 */
	deletePostMeeting: Constants.sopEndpointURL + "/galaxy/project/postOperation/deletePostMeeting",
	
	/**
	 * 立项报告/尽调启动会报告/尽调总结会报告
	 */
	showReportUpload: Constants.sopEndpointURL + "/galaxy/sopFile/showReportUpload",
	/**
	 * 立项报告/尽调启动会报告/尽调总结会报告
	 */
	uploadSimpleFile: Constants.sopEndpointURL + "/galaxy/sopFile/upload",
	
	delFile: Constants.sopEndpointURL + "/galaxy/sopFile/delFile",
	
	
	
	
	
	/*************以下为report url**************/
	/**
	 * 会议排期等待数
	 * 1.工作桌面－事项预览
	 */
	platformMeetingScheduling : Constants.sopEndpointURL + "/galaxy/report/platformMeetingScheduling",
	/**
	 * 会议排期列表通用
	 * 1.工作桌面－事项预览弹出层
	 */
	meetingSchedList : Constants.sopEndpointURL + "/galaxy/report/meetingSchedList",

	/**
	 * 1.数据简报－项目目标追踪、投资事业线目标完成对比
	 */
	databriefchart : Constants.sopEndpointURL + "/galaxy/report/databriefchart",
	/**
	 * 项目进度分布
	 * 1.工作桌面－项目进度
	 * 2.项目分析－项目总览－项目进度分布图
	 */
	projectprogress : Constants.sopEndpointURL + "/galaxy/report/projectprogress",
	/**
	 * 项目列表和项目维度的相关数据项
	 * 1. 项目分析－项目总览（数据表格）这里有10个切换表格
	 */
	projectlist : Constants.sopEndpointURL + "/galaxy/report/projectlist",
	
	/**
	 * 项目完成增长率
	 * 1. 项目分析－项目完成增长率统计（项目完成率分析－日报－图表）
	 */
	rateRiseDChart : Constants.sopEndpointURL + "/galaxy/report/rateRiseDChart",
	/**
	 * 项目完成增长率
	 * 1.项目分析－项目完成增长率统计（项目完成率分析－日报－表格）
	 */
	rateRiseD : Constants.sopEndpointURL + "/galaxy/report/rateRiseD",
	/**
	 * 项目完成增长率
	 * 1. 项目分析－项目完成增长率统计（项目完成率分析－月报－图表）
	 */
	rateRiseMChart : Constants.sopEndpointURL + "/galaxy/report/rateRiseMChart",
	/**
	 * 项目完成增长率
	 * 1.项目分析－项目完成增长率统计（项目完成率分析－月报－表格）
	 */
	rateRiseM : Constants.sopEndpointURL + "/galaxy/report/rateRiseM",
	/**
	 * 项目完成率分析
	 * 1. 数据简报－项目完成率分析
	 * 2. 工作平台－投资资金
	 */
	rateRiseMonthChart : Constants.sopEndpointURL + "/galaxy/report/rateRiseMonthChart",
	
	
	/**
	 * 部门列表
	 * 1. common.js 各查询条件中的 投资事业线 select
	 */
	departmentList : Constants.sopEndpointURL + "/galaxy/report/departmentList",
	
	/**
	 * 获取会议总数
	 */
	MeetingShedule : Constants.sopEndpointURL + "/galaxy/meetingShe/sheduling",
	
	/**
	 * 获取会议总数
	 */
	MeetingSheduleNewList : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/shedulingList",
	
	ceosh : Constants.sopEndpointURL + "/galaxy/meetingShe/sh",
	
    /**
     * pop弹出层
     */
	
	popList : Constants.sopEndpointURL + "/galaxy/sopUserSchedule/popupList",
	
	/**
	 * 排期日程入口
	 */
	popupMeetingList: Constants.sopEndpointURL + "/galaxy/sopUserSchedule/popupMeetingList/",
	
	/**
	 * 项目分析
	 */
	projectAnalysis: Constants.sopEndpointURL + "/galaxy/kpireport/toProOverView",
		
	/**
	 * 项目阶段高管
	 */
	projectStage4Manager: Constants.sopEndpointURL+"/galaxy/sop2/",	
	/**
	 * 项目目标追踪
	 */
	searchTargetTracking: Constants.sopEndpointURL+"/galaxy/charts/briefing/searchTargetTracking",
	/**
	 * 项目完成率分析
	 */
	searchProjectCompletion : Constants.sopEndpointURL+"/galaxy/charts/briefing/searchProjectCompletion",
	/**
	 * 项目总览
	 */
	searchOverView : Constants.sopEndpointURL+"/galaxy/charts/analysis/searchOverView",
	/**
	 * 项目查询（图表用）
	 */
	searchProjectByCharts : Constants.sopEndpointURL+"/galaxy/charts/analysis/searchProjectByCharts",
	/**
	 * 项目完成率查询（图表）
	 */
	searchRiseRate : Constants.sopEndpointURL+"/galaxy/charts/analysis/searchRiseRate",
	/**
	 * 项目完成率（表格）
	 */
	searchRiseRateGrid : Constants.sopEndpointURL+"/galaxy/charts/analysis/searchRiseRateGrid",
	/**
	 * 项目投资金额
	 */
	searchInvestmentGroupDate : Constants.sopEndpointURL+"/galaxy/charts/analysis/searchInvestmentGroupDate",	
	/**
	 * 投后项目分析（图表）
	 */
	searchPostAnalysis : Constants.sopEndpointURL + "/galaxy/charts/analysis/searchPostAnalysis",

	/**
	 * 1.数据简报－投资事业线目标完成对比
	 */
	deptProTarget : Constants.sopEndpointURL + "/galaxy/kpireport/deptProTarget",
	
	/**
	 * 1.数据简报－投资事业线目标完成对比 --合伙人专用
	 */
	tzjlProTarget : Constants.sopEndpointURL + "/galaxy/kpireport/tzjlProTarget",
	
	
	
	/**
	 * 过会率－事业线
	 * 1. 项目分析－过会率统计（图表＋表格）
	 * 2. 项目分析－投决率统计（图表＋表格）
	 */
	meetingrate : Constants.sopEndpointURL + "/galaxy/kpireport/deptMeetPassRate",
	/**
	 * 过会率-投资经理
	 * 1. 项目分析－过会率统计（图表＋表格）
	 * 2. 项目分析－投决率统计（图表＋表格）
	 */
	meetingRateUser : Constants.sopEndpointURL + "/galaxy/kpireport/tzjlMeetPassRate",
	/**
	 * 项目分析－过会率 - 项目统计弹窗
	 */
	meetRateProjectlist : Constants.sopEndpointURL + "/galaxy/kpireport/meetRateProjectlist",
	
	
	
	/**
	 * 项目分析－项目数统计弹窗
	 */
	topronumProjectlist : Constants.sopEndpointURL + "/galaxy/kpireport/toproNumProjectlist",
	/**
	 * 项目分析－项目数统计弹窗
	 */
	proNumProjectlist : Constants.sopEndpointURL + "/galaxy/kpireport/proNumProjectlist",
	/**
	 * 1.项目分析－项目数统计
	 */
	gglinechart : Constants.sopEndpointURL + "/galaxy/kpireport/gglinechart",
	
	/**
	 * 1.项目分析－项目数统计 --合伙人专用
	 */
	hhrlinechart : Constants.sopEndpointURL + "/galaxy/kpireport/hhrLineChart",
	
	/**
	 * to kpi
	 */
	touserkpi : Constants.sopEndpointURL + "/galaxy/kpireport/touserkpi",
	todeptkpi : Constants.sopEndpointURL + "/galaxy/kpireport/toteamkpi",
	
	/**
	 * 投资经理kpi
	 * 1. 绩效考核－投资经理绩效考核（图表＋表格）
	 */
	userkpi : Constants.sopEndpointURL + "/galaxy/kpireport/userkpi",
	/**
	 * 团队kpi
	 * 1. 绩效考核－团队绩效考核（图表＋表格）
	 * 2. 工作桌面－绩效考核
	 */
	deptkpi : Constants.sopEndpointURL + "/galaxy/kpireport/deptkpi",
	
	
	partnerkpi : Constants.sopEndpointURL + "/galaxy/kpireport/partnerkpi",
	/**
	 * 团队kpi 弹窗
	 * 团队绩效－项目列表页面
	 */
	deptkpiprojectlist : Constants.sopEndpointURL + "/galaxy/kpireport/deptkpiprojectlist",
	/**
	 * 项目历时
	 * 1. 工作桌面－项目历时
	 */
	progressDurationList : Constants.sopEndpointURL + "/galaxy/kpireport/proProgressTimeLine",
	/**
	 * 项目历时
	 * 1. 移交项目页面跳转
	 * 
	 */
	toProjectTransfer : Constants.sopEndpointURL + "/galaxy/projectTransfer/toProjectTransfer",
	/**
	 * 项目移交
	 * 1. 移交项目页面跳转
	 * 
	 */
	applyTransfer : Constants.sopEndpointURL + "/galaxy/projectTransfer/applyTransfer",
	/**
	 * 移交撤销
	 * 1. 撤销移交项目页面跳转
	 * 
	 */
	undoTransfer : Constants.sopEndpointURL + "/galaxy/projectTransfer/undoTransfer",
	/**
	 * 移交撤销页面
	 * 1. 撤销移交项目页面跳转
	 * 
	 */
	toRevokeProTransfer : Constants.sopEndpointURL + "/galaxy/projectTransfer/toRevokeProTransfer",
	/**
	 * 跳转至拒接页面
	 * 
	 * 
	 */
	toRefuseTransfer : Constants.sopEndpointURL + "/galaxy/projectTransfer/toRefuseTransfer",
	/**
	 * 拒绝接收项目
	 * 1. 
	 * 
	 */
	rejectTransfer : Constants.sopEndpointURL + "/galaxy/projectTransfer/rejectTransfer",
	/**
	 * 接收项目
	 * 
	 * 
	 */
	receiveTransfer : Constants.sopEndpointURL + "/galaxy/projectTransfer/receiveTransfer",
	/**
	 * 否决项目
	 */
	toRefuseProject : Constants.sopEndpointURL + "/galaxy/project/toRefuseProject",
	
	/**
	 * 注资信息开始
	 */
    //添加分期注资计划弹出页面
	toApprActualAging : Constants.sopEndpointURL + "/galaxy/grant/part/toApprActualAging",
    //添加总注资计划弹出页面
	toApprAllAdd : Constants.sopEndpointURL + "/galaxy/grant/total/toApprAllAdd",
    //添加总注资计划提交
	addGrantTotal : Constants.sopEndpointURL + "/galaxy/grant/total/addGrantTotal",
	//查询总注资计划列表
	queryGrantTotalList : Constants.sopEndpointURL + "/galaxy/grant/total/search",
	//查询单个总注资计划列表
	getGrantTotal : Constants.sopEndpointURL + "/galaxy/grant/total/getGrantTotal",
	//删除总注资计划列表
	deleteGrantTotal : Constants.sopEndpointURL + "/galaxy/grant/total/deleteGrantTotal",
	//编辑总注资计划列表
	resetGrantTotal : Constants.sopEndpointURL + "/galaxy/grant/total/resetGrantTotal",
	//添加分期注资计划
	toAddApprActualAging : Constants.sopEndpointURL + "/galaxy/grant/part/addGrantPart",
	//谈书实际注资列表
	toApprActualPage : Constants.sopEndpointURL + "/galaxy/grant/actual/toApprActualPage",
	//查看实际注资详情
	toApprActualLook : Constants.sopEndpointURL + "/galaxy/grant/actual/toApprActualLook",
	//查看实际注资详情
	toApprActualAdd : Constants.sopEndpointURL + "/galaxy/grant/actual/toApprActualLook",
	//右侧注资进度
	getApprProcess : Constants.sopEndpointURL + "/galaxy/grant/total/getApprProcess",
	//删除成功提示框
	deleteAlert : Constants.sopEndpointURL + "/galaxy/deleteAlert",
	//实际注资编辑弹窗
	toEditApprActual : Constants.sopEndpointURL + "/galaxy/grant/actual/toEditApprActual",
	//初始化注资编辑弹窗
	initEditApprActual : Constants.sopEndpointURL + "/galaxy/grant/actual/initEditApprActual",
	//保存实际注资
	saveApprActual : Constants.sopEndpointURL + "/galaxy/grant/actual/saveApprActual",
	//删除实际注资
	deleteApprActual : Constants.sopEndpointURL + "/galaxy/grant/actual/deleteApprActual",
	//获取分拨中所有实际金额
	getActualPartMoney : Constants.sopEndpointURL + "/galaxy/grant/actual/getActualPartMoney",
	
	
	/**
	 * 项目详情-基本信息
	 */
	toTabProjectInfo : Constants.sopEndpointURL + "/galaxy/project/detail/toTabProjectInfo",
	/**
	 * 项目详情-项目文档
	 */
	toTabFile : Constants.sopEndpointURL + "/galaxy/project/detail/toTabFile",
	/**
	 * 项目详情-股权结构
	 */
	showShareDetail: Constants.sopEndpointURL + "/galaxy/project/tabShares",
	/**
	 * 项目详情-团队成员
	 */
	showPersonDetail: Constants.sopEndpointURL + "/galaxy/personTab",
	
	/**
	 * 运营分析
	 */
	showOperationsAnalysis: Constants.sopEndpointURL+"/galaxy/project/postOperation/toPostMeeting",
	/**
	 * 项目详情-右边
	 */
	toRight : Constants.sopEndpointURL + "/galaxy/project/detail/toRight",
	/**
	 * 高管首页-健康状况报表展示
	 */
	getHealthyCharts : Constants.sopEndpointURL + "/galaxy/health/getHealthyCharts",
	/**
	 * 高管首页-健康状况报表展示详情
	 */
	toHealthChartDetail : Constants.sopEndpointURL + "/galaxy/health/toHealthChartDetail",
	/**
	 * kpi导出
	 */
	exportKpiGrade : Constants.sopEndpointURL + "/galaxy/kpireport/exportKpiGrade",
	/**
	 * 报表格式选择对话框
	 */
	toChooseReportSuffix : Constants.sopEndpointURL + "/galaxy/kpireport/toChooseReportSuffix",
	/**
	 * 项目详情-根据项目id查询融资历史信息
	 */
	searchFH : Constants.sopEndpointURL + "/galaxy/financeHistory/searchFH",
	/**
	 * 项目详情-新增一融资历史信息
	 */
	saveFH : Constants.sopEndpointURL + "/galaxy/financeHistory/saveFH",
	/**
	 * 项目详情-跳转到融资历史修改页面
	 */
	toUpateOrSaveFH : Constants.sopEndpointURL + "/galaxy/financeHistory/toUpateOrSaveFH",
	/**
	 * 项目详情-查询改融资历史的详细信息
	 */
	getFH : Constants.sopEndpointURL + "/galaxy/financeHistory/getFH",
	/**
	 * 项目详情-融资历史修改保存
	 */
	upateFHSave : Constants.sopEndpointURL + "/galaxy/financeHistory/upateFHSave",
	/**
	 * 项目详情-删除融资历史信息
	 */
	deleteFH : Constants.sopEndpointURL + "/galaxy/financeHistory/deleteFH",
	/**
	 * 项目详情-删除融资历史信息
	 */
	selectEntityShare : Constants.sopEndpointURL + "/galaxy/projectShares/selectEntityShare",
	/**
	 * 投后运营-头后项目跟踪-事业部创投项目列表
	 */
	deptProjectList : Constants.sopEndpointURL + "/galaxy/project/deptProjectList",
	/**
	 * 投后运营-头后项目跟踪-事业部创投项目列表-导出
	 */
	exportProjectGrade : Constants.sopEndpointURL + "/galaxy/project/exportProjectGrade",
	/**
	 * 创意咨询列表-导出
	 */
	exportZixunGrade : Constants.sopEndpointURL + "/galaxy/zixun/exportZixunGrade",
	/**
	 * 添加运营数据
	 */
	addOperationData : Constants.sopEndpointURL + "galaxy/operationalData/formAddOperationalData",
	/**
	 * 运营数据列表
	 */
	operationalDataList :   Constants.sopEndpointURL + "galaxy/operationalData/operationalDataList",
	
	
	
	/*************以下为全息图 url**************/
	/**
	 * 基本信息全息图-tab页跳转
	 */
	tabInfomation : Constants.sopEndpointURL + "/galaxy/infomation/tabInfomation",
	/**
	 * 全息图-基本信息模块
	 */
	toBaseInfo : Constants.sopEndpointURL + "/galaxy/infomation/toBaseInfo",
	/**
	 * 全息图-项目模块
	 */
	toProjectInfo : Constants.sopEndpointURL + "/galaxy/infomation/toProjectInfo",
	/**
	 * 全息图-团队成员
	 */
	toTeamInfo : Constants.sopEndpointURL + "/galaxy/infomation/toTeamInfo",
	/**
	 * 全息图-运营数据
	 */
	toOperateInfo : Constants.sopEndpointURL + "/galaxy/infomation/toOperateInfo",
	/**
	 * 全息图-竞争
	 */
	toCompeteInfo : Constants.sopEndpointURL + "/galaxy/infomation/toCompeteInfo",
	/**
	 * 全息图-战略以及策略
	 */
	toPlanInfo : Constants.sopEndpointURL + "/galaxy/infomation/toPlanInfo",
	/**
	 *  全息图-财务
	 */
	toFinanceInfo : Constants.sopEndpointURL + "/galaxy/infomation/toFinanceInfo",
	/**
	 * 全息图-法务
	 */
	toJusticeInfo : Constants.sopEndpointURL + "/galaxy/infomation/toJusticeInfo",
	/**
	 * 全息图-融资及估值
	 */
	toValuationInfo : Constants.sopEndpointURL + "/galaxy/infomation/toValuationInfo",
	
	
	
	
	
	
	
	//eg： platformUrl.queryTitleInfo+"code"
	/**
	 * 传入题 id 或 code， 返回 题 信息
	 */
	queryTitleInfo : Constants.sopEndpointURL + "/galaxy/tvalue/queryTitleInfo/",
	/**
	 * 传入题 id 或 code， 返回该题 的下一级所有 题 信息
	 */
	queryTsTitles : Constants.sopEndpointURL + "/galaxy/tvalue/queryTsTitles/",
	/**
	 * 传入题 id 或 code， 返回该题信息及其下的所有子级的 题 信息
	 */
	queryAllTitle : Constants.sopEndpointURL + "/galaxy/tvalue/queryAllTitle/",
	
	/**
	 * 传入题 id ， 返回 题对应的 value 信息
	 */
	queryValuesByTid : Constants.sopEndpointURL + "/galaxy/tvalue/queryValuesByTid/",
	/**
	 * 传入题 id 或 code， 返回 题信息及其对应的 values 信息
	 */
	queryTitleAndValues : Constants.sopEndpointURL + "/galaxy/tvalue/queryTitleAndValues/",
	/**
	 * 传入题 id 或 code， 返回该题的下一级的 题及value 信息
	 */
	queryTsTvalues : Constants.sopEndpointURL + "/galaxy/tvalue/queryTsTvalues/",
	/**
	 * 传入题 id 或 code， 返回该题信息及其下的所有级的 题和value信息
	 */
	queryAllTitleValues : Constants.sopEndpointURL + "/galaxy/tvalue/queryAllTitleValues/"
	
	
	
}

/**
 * how to use? location.href = platformUrl.login
 */
