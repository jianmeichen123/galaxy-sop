<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/> 
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<link rel="stylesheet" href="<%=path %>/bootstrap-table/bootstrap-table.css"  type="text/css">
<%-- <script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script>
<script src='<%=path %>/js/selectUi.js' type='text/javascript'></script>
<script src='<%=path %>/js/lq.datetimepick.js' type='text/javascript'></script> --%>

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    <!-- 面包屑 -->
    <ul class="breadcrumb">
      <li><a href="javascript:;" class="bcfirst">待办任务</a></li>
      <li class="bottom_align"><span>&gt;</span><a href="javascript:;" class="active">完善简历</a></li>
    </ul>
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
                          <dd></dd>
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
                  </tr>
                  <tr>
                    <td colspan="2">
                      <dl>
                        <dt>项目概述：</dt>
                        <dd class="tarea" id="projectDescribe"></dd>
                      </dl>
                    </td>
                  </tr>
                </tbody>
              </table>
            <a href="javascript:;"  class="pjt_more">项目详细信息&gt;</a>
          </div>

          <!-- 下半部分 -->
          <DIV class="btm">
            <TABLE width="100%" cellspacing="0" cellpadding="0" >
              <thead>
                  <tr>
                      <th>姓名 </th>
                      <th>性别</th>
                      <th>年龄 </th>
                      <th>当前职务</th>
                      <th>电话</th>
					  <th>操作</th>
                  </tr>
              </thead>                                                                                                                     
              <tbody id="wanshan">
              <tr>
                      <td>完善简历</td>
                      <td>男</td>
                      <td>21</td>
                      <td>2016-01-20</td>
                      <td>已上传</td>
                      <!-- <td><a href="javascript:;" onclick="test();" data-btn="resume" >完善简历</a></td -->
                       <td><a href="<%=path %>/galaxy/hrjl/resumetcc/"  DATA-btn="resume" >完善简历</a></td>
                  </tr> 
              </tbody>
          </TABLE> 

          </DIV>
        </div>

    </div>
 
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
$(function(){
	/* alert(111222) */
	createMenus(2);
	getProjectInfo();
	//ProjectRenyuan();
});

function ProjectRenyuan() {
	alert(111) 
	/* sendPostRequest("/galaxy/project/queryProjectPerson", SopTaskRenyuan); */
	var jsonData={"pageNum":0,"pageSize":500}; 
	sendPostRequestByJsonObj("/galaxy/project/queryProjectPerson",jsonData, SopTaskRenyuan);
}
function SopTaskRenyuan(data){
	//组装数据	
	var list =  data.pageList.content;
	if(list != "" || list != undefined || list != null){
		var tbodyList = $("#wanshan"); 
		var i=0;
		$(list).each(function(){
			 var temp = $(this)[0];
			 i=i+1;
			 var tr='<tr>'+
				 '<td>'+ temp.personName+'</td>'+
				 '<td>'+ temp.personSex+'</td>'+
				 '<td>'+ temp.personAge+'</td>'+
				 '<td>'+ temp.personDuties+'</td>'+
				 '<td>'+ temp.personTelephone+'</td>'+
				 '<td>'+'<a href="/galaxy/resumetcc" DATA-btn="resume" >完善简历</a>'+'</td>'+
				' </tr>'; 				
			 tbodyList.append(tr);
		  });	
	}
	
}
function tiaozhuan(){
	alert(222)
	window.location.href=forwardWithHeader("/galaxy/resumetcc/");
}

function getProjectInfo()
{
	var url = platformUrl.detailProject+"/${projectId}";
	sendGetRequest(
		url,
		null,
		function(data){
			if(data.result.status == "Error")
			{
				alert(data.result.message );
				return;
			}
			var project = data.entity;
			$("#project-summary dd")
			.each(function(){
				var self = $(this);
				if(self.attr('id') != 'undefined')
				{
					var id = self.attr('id');
					self.text(project[id]);
				}
			});
			$(".projectmsg h2").text(project.projectName);
		}
	);
}

</script>
</body>
</html>
	