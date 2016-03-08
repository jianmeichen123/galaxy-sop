var Constants = {
	platformEndpointURL : endpointObj["galaxy.project.platform.endpoint"],
	reportEndpointURL : endpointObj["galaxy.project.report.endpoint"]
}

var platformUrl = {
	/**
	 * 验证登录
	 */
	createMenus : "/galaxy/common/menu/",
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
	 * 模板邮件
	 */
	tempSendMail:"/galaxy/template/sendMail",
	
	/**
	 * 修改团队成员
	 */
	updatePerson : "/galaxy/project/upp",
	
	/**
	 * top5立项会排期
	 */
	top5ProjectMeeting : "/galaxy/home/top5ProjectMeeting",
	
	/**
	 * 所有立项会排期
	 */
	moreProjectMeeting : "/galaxy/home/moreProjectMeeting",
	
	/**
	 * 根据findByParentCode查询数据字典
	 */
	dictFindByParentCode : "/galaxy/sopFile/getDictByParent",
	

}

/**
 * how to use? location.href = platformUrl.login
 */
