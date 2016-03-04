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
	 * 查询模板相关数据
	 */
	getTempRelatedData:"/galaxy/template/getRelatedData",
}

/**
 * how to use? location.href = platformUrl.login
 */
