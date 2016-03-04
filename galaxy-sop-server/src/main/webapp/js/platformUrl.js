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
	 * 跳转到首页
	 */
	toIndex : Constants.platformEndpointURL + "/galaxy/redirect",
	/**
	 * 模板列表显示
	 */
	queryTemplate:"/galaxy/template/queryTemplate",
}

/**
 * how to use? location.href = platformUrl.login
 */
