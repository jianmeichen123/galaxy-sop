var Constants = {
	platformEndpointURL : endpointObj["galaxy.project.platform.endpoint"],
	reportEndpointURL : endpointObj["galaxy.project.report.endpoint"]
}

var platformUrl = {
	/**
	 * 验证登录
	 */
	createMenus : "./common/menu/",
	/**
	 * 获取项目编码接口地址
	 */
	getProjectCode : "./project/cpc",
	/**
	 * 添加项目
	 */
	addProject : "./project/ap",
	/**
	 * 获取项目详情
	 */
	detailProject : "./project/sp/",
	/**
	 * 修改项目信息
	 */
	updateProject : "./project/up",
	/**
	 * 添加团队成员
	 */
	addPerson : "./project/app",
	/**
	 * 跳转到首页
	 */
	toIndex : Constants.platformEndpointURL + "/galaxy/redirect",
	/**
	 * 查询模板列表
	 */
	queryTemplate : "/galaxy/template/queryTemplate",
	/**
	 * 查询模板相关数据
	 */
	getTempRelatedData:"/galaxy/template/getRelatedData",
	/**
	 * 模板上传
	 */
	tempUpload:"/galaxy/template/upload",
	/**
	 * 模板保存
	 */
	tempSave:"/galaxy/template/save",
	/**
	 * 模板下载
	 */
	tempDownload:"/galaxy/template/download",
	
	/**
	 * 修改团队成员
	 */
	updatePerson : "/galaxy/project/upp",
	
	/**
	 * 立项排期所有
	 */
    moreProjectMeeting : "/galaxy/home/moreProjectMeeting",
	/**
	 * top5立项会排期
	 */
	top5ProjectMeeting : "/galaxy/home/top5ProjectMeeting",
	ProjectVoteWill: "/galaxy/home/ProjectVoteWill",
	moreProjectVoteWill:"/galaxy/home/moreProjectVoteWill",
	
	/**
	 * 添加股权结构
	 */
	addStock : "/galaxy/projectShares/addProjectShares",
	
	/**
	 * 修改股权结构
	 */
	updateStock : "/galaxy/projectShares/updateProjectShares",

}

/**
 * how to use? location.href = platformUrl.login
 */
