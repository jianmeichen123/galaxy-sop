/*var Constants = {
	platformEndpointURL : endpointObj["galaxy.project.platform.endpoint"],
	reportEndpointURL : endpointObj["galaxy.project.report.endpoint"]
}*/
var sopContentUrl = "http://127.0.0.1:8080/galaxy-sop-server";

var platformUrl = {
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
	soptaskshouye: "/galaxy/soptask/taskListByRole",
	
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
	 * 访谈查询 page
	 */
	selectViewPage : sopContentUrl + "/galaxy/project/progress/queryInterview",
	
	/**
	 * 获取登录人的项目,阶段为：
	 */
	getUserPro : sopContentUrl + "/galaxy/project/progress/queryPerPro",
	
	/**
	 * 访谈添加：附件上传
	 */
	saveInteverView : sopContentUrl + "/galaxy/project/progress/addInterview",
	
	/**
	 * 会议添加：
	 */
	saveMeet : sopContentUrl + "/galaxy/project/progress/addmeet",
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
	 * 启动内部评审
	 */
	startReview : sopContentUrl + "/galaxy/project/progress/startReview/",

}

/**
 * how to use? location.href = platformUrl.login
 */
