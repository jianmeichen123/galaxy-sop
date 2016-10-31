<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script src="<%=path %>/js/my.js" type="text/javascript"></script>
<script src="<%=path %>/js/my_ext.js" type="text/javascript"></script>
<!-- 保存进度条 -->
<link href="<%=path %>/css/showLoading.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    <!-- 面包屑 -->
    <h2>待办任务</h2>
   <!--  <ul class="breadcrumb">
      <li><a href="javascript:;" class="bcfirst">待办任务</a></li>
      <li class="bottom_align"><a href="javascript:;" class="active">上传文档</a></li>
    </ul> -->
      <div class="clearfix"></div>
        <!--项目基本信息内容-->
        <div class="projectmsg clearfix">
          <h2></h2>
          <!-- 上半部分 -->
          <div class="top" id="project-summary">
            <table width="100%" cellspacing="5" cellpadding="0" >
             <tbody>
                  <tr>
                      <td>
                        <dl>
                          <dt>项目编码：</dt>
                          <dd id="projectCode"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>来源：</dt>
                          <dd id="type"></dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>项目名称：</dt>
                          <dd id="projectName"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>创建时间：</dt>
                          <dd id="createDate"></dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>投资事业线：</dt>
                          <dd id="projectCareerline"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>合伙人：</dt>
                          <dd id="hhrName"></dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>投资经理：</dt>
                          <dd id="createUname"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>公司名称：</dt>
                          <dd id="projectCompany"></dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                    <td colspan="2">
                      <dl>
                        <dt>项目描述：</dt>
                        <dd class="tarea" id="projectDescribe"></dd><div class="tips"></div>
                      </dl>
                    </td>
                  </tr>
                </tbody>
              </table>       <%-- <%=path %>/galaxy/detail/  upp --%>
            <a href='<%=path %>/galaxy/project/detail/${projectId}?mark=t'  class="pjt_more url_cookie">项目详细信息&gt;</a>
          </div>
          <!-- 下半部分 -->
          <!-- 人事尽职调查报告  hrjzdc;-->
          <jsp:include page="/galaxy/taskprocess/showFileList">
          <jsp:param value="<%=request.getAttribute(\"taskFlag\") %>" name="taskFlag"/>
          </jsp:include>
        </div>

    </div>
 
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">

var isPrivilege_6 = "${fx:isPrivilege_6(projectId) }";

$(function(){
	createMenus(2);
	getProjectInfo(projectLoaded);
});

function getProjectInfo(projectLoaded)
{
	var url = platformUrl.detailProject+"/${projectId}";
	sendGetRequest(
		url,
		null,
		function(data){
			if(data.result.status == "Error")
			{
				return;
			}
			var project = data.entity;
			stockTransfer = project.stockTransfer;
			if(project.projectType == 'projectType:1'){
				var checkboxHtml = '<input type="checkbox" value="1" class="input_checkbox" onclick="selected(this);" id="stock_transfer"><label for="stock_transfer" class="check-box"></label> <label for="stock_transfer" class="check-tit">涉及股权转让</label>';
				$("#stock_transfer_model").html(checkboxHtml);
			}
			$("#project-summary dd")
			.each(function(){
				var self = $(this);
				if(self.attr('id') != 'undefined')
				{
					var id = self.attr('id');
					if(id=='projectDescribe'){
						var desc = project[id];
						var text1=delHtmlTag(desc);
						if(desc && text1.length>100)
						{
							self.html(desc.substring(0,100)+'...');
						}
						else
						{
							self.html(desc);
						}
						self.next().html(desc);
					}					
					else{
						self.html(project[id]);
					}
					
				}
				if(self.attr('id') =='projectName'){
					var str=project[id];
					if(str.length>15){
						self.text(str.substring(0,15));
						self.attr("title",str);
					}else{
						self.text(str);
						self.attr("title",str);
					}
				}
				if(self.attr('id') =='projectCompany'){
					var str=$.trim(project[id]);
					//console.log(str)
					if(str == undefined || str == null || str ==''){
						self.text('');
					}else if(str.length>20){
						self.text(str.substring(0,20));
						self.attr("title",str);
					}else{
						self.text(str);
						self.attr("title",str);
					}
				}
			});
			$(".projectmsg h2").text(project.projectName);
			if($.isFunction(projectLoaded)){
				projectLoaded(project);
			}
		}
	);
}
function delHtmlTag(str)
{
	if(str){
		return str.replace(/<[^>]+>/g,"");//去掉所有的html标记
	}


}


</script>
</body>
</html>
	