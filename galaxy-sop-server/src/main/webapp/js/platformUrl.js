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
	 * 获取登录人的项目,阶段为：
	 */
	getUserPro : "/galaxy/project/progress/queryPerPro",
	
	/**
	 * 访谈添加：
	 */
	saveInteverView : "/galaxy/project/progress/addInterview",
	
	/**
	 * 访谈添加：
	 */
	saveMeet : "/galaxy/project/progress/addmeet",
}

/**
 * how to use? location.href = platformUrl.login
 */
