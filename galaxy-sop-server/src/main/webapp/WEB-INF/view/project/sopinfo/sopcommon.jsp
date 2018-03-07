<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<% 
	String path = request.getContextPath(); 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
	response.setHeader("Pragma","no-cache"); //HTTP 1.0    
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server 
%>
	<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
  <c:set var="aclViewProject"
	value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }"
	scope="request" />
	
<c:set var="isEditable"
	value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}"
	scope="request" />
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!-- 保存进度条 -->
<link href="<%=path %>/css/showLoading.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<style type="text/css">
body{
		background-color:#E9EBF2;
	}
div.tip-yellowsimple {
    visibility: hidden;
    position: absolute;
    top: 0;
    left: 0;
}
.tip-yellowsimple .tip-arrow-left{
position:absolute;
}
#selectRadio-error{
position: absolute;
    top: 45px;
    left: 0px;
    margin: 0;
}
</style>
</head>
<script src="<%=path %>/js/projectTransfer.js"></script>
<body>
<div class='version19_detail_header_box'>
	<div class='version19_detail_header'>
    	<div class="top clearfix">
	    	<div class='fl one'>
	    		<span class="project_name_t" id="project_name_t"></span>
	    	</div>
        	<div class='fl two'>
        		<label class="" id="industryOwnDs" ></label><label class='middot'>&middot;</label><label class="" id="financeStatusDs"  data-title-id="1108"></label>
        	</div>
        	<div class='fl three'>
        		<span class="projectType" id="projectType"></span>
        		<span class="faName" id="faName" data-toggle="tooltip" data-placement="top"></span>
        	</div>
        	<div class='fr four'>
        		<c:if test="${isEditable}">
					<span class="version19_edit_btn" data-name="basic"  data-on="data-open-basic" data-cont="message">编辑</span>
				</c:if>
				<span class="b_span version19_blue_btn" onclick="back();">返回</span>
        	</div>
        </div>
        <div class='middle'>
        	<span class='m_one'>
        		<span>创建时间：</span>
				<span id="create_date"></span>
        	</span>
        	<span class='m_one'>
        		<span>承作人：</span>
				<span id="createUname"></span><span>(</span><span id="projectCareerline"></span><span>)&nbsp;</span>
				<span data-toggle="tooltip" data-placement="bottom" title="" data-original-title="点击查看全部" id="undeLine_sop">等&nbsp;&nbsp;共<span id="czrNum"></span>人</span>
        	</span>
        </div>
        <!-- tab标签 -->
		<jsp:include page="tab_header.jsp" flush="true"></jsp:include> 
	</div>
		
</div>
<div class="basic_on basic_common_width sop_common_width">
		<form id="basicForm1" onsubmit="return false;">
		<!-- 编辑基本信息 -->
		<div class="basic_message message_current basic_current">
		<div class="title_bj_tzjl"><span class="edit_icon_img"></span>编辑基本信息<em class="agency_close" data-on="close" data-name="basic"></em></div>
		<div class="compile_on_center edit_com_color" id="updateProjectInfo">
			<div class="basic_message_cont">
				<table width='100%' cellspacing='0' cellpadding='0' class="edit_basic_table">
					<tr>
						<td>
							<span>投资经理：</span><span class="basic_mes_left" id="createUname_edit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<span>项目类型：</span><span class="basic_mes_left" id="projectType_edit">2017-10-23</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>创建时间：</span><span class="basic_mes_left" id="create_date_edit">2017-10-23</span>
						</td>
					</tr>
					<tr>
						<td>
							<span>项目名称：</span>
							<input type="text" value="" class="basic_mes_input" maxlength="24" name="projectName" id="project_name_edit" required data-rule-limit24="true" data-msg-required="<font color=red>*</font>项目名称不能为空"/>
							<label class='projectname-edit-label'>*项目名称重复</label>
						</td>
					</tr>
					<tr>
						<td>
							<span>行业归属：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" autocomplete="off" readonly="readonly" onclick="dropdown_select(this,event)"  placeholder='请选择' value="请选择" id="industry_own_sel" name="industryOwn" required data-msg-required="<font color=red>*</font>行业归属不能为空"/> 
									<ul class='base_select_ul'> 
										<li>企业服务</li> 
										<li>数字娱乐</li> 
										<li>互联网教育</li> 
										<li>互联网医疗</li> 
									</ul> 
							</div> 
						</td>
					</tr>
					<tr>
						<td>
							<span>本轮融资轮次：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" autocomplete="off" readonly="readonly" onclick="dropdown_select(this,event)" placeholder='请选择'  value="请选择" id="finance_status_sel" name="financeStatus" required data-msg-required="<font color=red>*</font>融资轮次不能为空" aria-required="true" data-title-id="1108" data-type="14"/> 
									<ul class='base_select_ul finance_status_ul'>  
									</ul> 
							</div> 
						</td>
					</tr>
					<tr>
						<td>
							<span>项目来源：</span>
							<div id="dropdown"> 
								<input class="input_select" type="text" data-title-id="1120"  data-type="14" autocomplete="off" readonly="readonly" onclick="dropdown_select(this,event)"  placeholder='请选择' value="请选择" name="projectSource" required data-msg-required="<font color=red>*</font>项目来源不能为空"/> 
									<ul class='base_select_ul' id="selectSource">  
									</ul> 
							</div> 
							<span class="historyData">项目来源选项已更新，请重新选择</span>
						</td>
					</tr>
					<tr class="trSouce projectSource5 ">
						<td>
							<span>合投机构名称：</span>
							<div> 
								<input type="text" value="" placeholder="请输入合投机构名称" data-title-id="1121" data-type="1" class="basic_mes_input inputSouce" name="proS5"   maxlength="50"/>
							</div>
							
						</td>
					</tr>
					<tr class="trSouce projectSource6 ">
						<td>
							<span>FA名称：</span>
							<div> 
								<input type="text" value="" placeholder="请输入FA名称（必填）" data-title-id="1122" data-type="1" class="basic_mes_input inputSouce" data-rule-limit20="true" required name="proS6"  data-msg-required="<font color=red>*</font>不能超过20字且不能全为空格" maxlength="20"/>
							</div>
							
						</td>
					</tr>
					<tr class="trSouce projectSource7">
						<td>
							<span>孵化器名称：</span>
							<div> 
								<input type="text" value=""  placeholder="请输入孵化器名称"  data-title-id="1123" data-type="1" class="basic_mes_input inputSouce" name="proS7"  maxlength="50"/>
							</div>
							
						</td>
					</tr>
					<tr class="trSouce projectSource8">
						<td>
							<span>机构及路演名称：</span>
							<div> 
								<input type="text" value="" placeholder="请输入机构及路演名称" data-title-id="1124" data-type="1" class="basic_mes_input inputSouce" name="proS8"  maxlength="50"/>
							</div>
							
						</td>
					</tr>
					<tr class="trSouce projectSource9">
						<td>
							<span>创投机构名称：</span>
							<div> 
								<input type="text" value="" data-title-id="1125" placeholder="请输入创投机构名称" data-type="1" class="basic_mes_input inputSouce" name="proS9"  maxlength="50"/>
							</div>
							
						</td>
					</tr>
					<tr class="trSouce projectSource10">
						<td>
							<span>媒体名称 ：</span>
							<div> 
								<input type="text" value="" placeholder="请输入媒体名称" data-title-id="1126" data-type="1" class="basic_mes_input inputSouce" name="proS10" maxlength="50" />
							</div>
							
						</td>
					</tr>
					<tr class="trSouce projectSource11">
						<td >
							<span>项目承揽人：</span>
							<div class="selectcheck">
								
								<select id="selectRadio"  style="width:0;height:0;opacity:0;margin:0;padding:0!important;display:block!important;" required   data-msg-required="<font color=red>*</font>项目承揽人不能为空"  class="selectpicker" multiple data-type="23" data-live-search="true" data-title-id="1118" >

								  </select>
								  
								  <input type="text" placeholder="请输入非投资线项目承揽人名称(必填)" class="trSouceOther addpro-input basic_mes_input" maxlength="12" name="pickeother" required data-rule-limit12="true" data-msg-required="<font color=red>*</font>不能超过12字且不能全为空格"/>
							</div>
							</div>
							</div>
							
						</td>
					</tr>
				</table>
			
			</div>
	    </div>
	   		<div class="btn btnbox basic_mes_button basicSpButton">
              <button  class="pubbtn bluebtn version19_save_btn" data-on="save_basic" save_type='save_basic'>保存</button>
              <button  class="pubbtn fffbtn version19_cancel_btn" data-name='basic' data-on="close" >取消</button>
            </div> 
	    </div>
	 </form>
</div>
<!--隐藏-->
<div class="bj_hui_on_common"></div>
</body>
<%-- <script src="<%=request.getContextPath() %>/js/cookie.js"></script> --%>
<!-- 高管/投资经理 -->
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isCreatedByUser" value="${fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<c:set var="isThyy" value="${fx:hasRole(20)}" scope="request"/>
<script src="<%=path %>/js/projectDetail/tabInfoCommon.js" type="text/javascript"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js" type="text/javascript"></script>
<script>
 
var isCreatedByUser = "${isCreatedByUser}";
var isEditable = "${isEditable}";
var pid='${pid}';
if(null==pid||typeof(pid)=="underfind"||pid==""){
	pid='${projectId}';
}

var projectInfo = '';
sendGetRequest(platformUrl.detailProject + pid, {}, function(data){	
	projectInfo = data.entity;
});
//项目基本信息
var reportResult='';
var projectInfoDetail = '';
var projectInfoReport=[];
var projectInfoSource='';
sendGetRequest(Constants.sopEndpointURL+"/galaxy/infoProject/getTitleRelationResults/4/"+projectInfo.id, null, function(data){
	if(data.result.status=='OK'){		
		reportResult=data.userData.report; 
		projectInfoDetail=data.userData.pro;
		projectInfoList=data.userData.report[0].childList; 
	}
})
if(projectInfoDetail.projectName.length>24){
			var str=projectInfoDetail.projectName.substring(0,24);
		}
if(projectInfoDetail.projectName.length>20){
	$('.project_name_t').css('font-size','24px')
}else{
	$('.project_name_t').css('font-size','28px')
}
		$("#project_name").text(str);
		$("#project_name").attr("title",projectInfoDetail.projectName);
		var projectPro = projectInfoDetail.projectProgress;
		if(projectPro=="projectProgress:10"){
			$("#end").hide();
			$("#s").hide();
		}
			$("#project_name_title").text(projectInfoDetail.projectName);
			$("#project_name_t").text(projectInfoDetail.projectName); 
			$("#project_name_t").attr("pid",projectInfoDetail.id);
			$("#create_date").text(projectInfoDetail.createDate);
			$("#createUname").text(projectInfoDetail.createUname);
			$("#projectCareerline").text(projectInfoDetail.projectCareerline);
			$("#projectType").text(projectInfoDetail.type==null?"-":projectInfoDetail.type);
			$("#projectProgress").text(projectInfoDetail.progress);
			$("#projectStatusDs").text(projectInfoDetail.projectProgress=="projectProgress:10"?"":projectInfoDetail.projectStatusDs);
			$("#financeStatusDs").text(projectInfoDetail.financeStatusDs==null?"-":projectInfoDetail.financeStatusDs);
			$("#industryOwnDs").text(projectInfoDetail.industryOwnDs);
			$("#faName").text(projectInfoDetail.faFlagStr==null?"-":projectInfoDetail.faFlagStr).attr("data",projectInfoDetail.faFlag);

			showFaname()



			$('.version19_detail_header_box').css('visibility','visible')
			//report信息
			if(roleId==4){   //投资经理a看投资经理B的项目，团队，法人，股权，融资隐藏
				var roleProject=$('#createUname').text();
				var roleLogin=$('.man_info .name').text();
				if(roleProject==roleLogin){
					$('.role_hide').show();
				}
			}else{
				$('.role_hide').show();
			}
function back(){
	var _url=window.location.href;
	if(_url.indexOf('?backurl=list')>-1){  //新建项目入口
		forwardWithHeader(Constants.sopEndpointURL + "/galaxy/mpl")
	}else{   //列表页入口
		var flag=getCookieValue("cooki_flag");//处理待办任务页面不需要保存参数
		console.log(flag);
		if(null==flag||""==flag||flag=="undefined"){
			setCookie("backProjectList", 'click',24,'/');
		}else{
			deleteCookie("cooki_flag","/");	
		}
		var href_url=getCookieValue("href_url");
		if(href_url && href_url.length>0){
			deleteCookie("href_url","/");
			window.location=href_url;
		}
		else
		{
			window.history.back();
		}
	}
}

$(function(){
	var str=projectInfo.projectName;
	if(projectInfo.projectName.length>24){
		str=projectInfo.projectName.substring(0,24);
	}
	$("#project_name_title").text(projectInfo.projectName);
	$("#project_name_t").text(str);
	$("#project_name_t").attr("title",projectInfo.projectName);
	$("#project_code_t").text(projectInfo.projectCode);
    $("#workDesk").click(function(){
	   var url=Constants.sopEndpointURL+"/galaxy/index";
	   forwardWithHeader(url);
    });
    
  //计算version19_detail_header的左边距
  detailHeaderWidth();
  function detailHeaderWidth(){
	  var  w_lft=$(".lft").width();
	  	$('.version19_detail_header').css({'padding-left':w_lft+40});
  }
  	$(window).resize(function(){
  		detailHeaderWidth();
  	})
});
/**
 * 面包屑
 * @version 2016-06-21
 */
function backIndex(){
	  var url=Constants.sopEndpointURL+"/galaxy/index";
	  window.location.href = url+"?sid="+sessionId+"&guid="+userId+"&_is_menu_=true";
	
}
function projectList(){
	var url=Constants.sopEndpointURL+"/galaxy/mpl";
	forwardWithHeader(url);
}

//项目名称重复限制
$('#project_name_edit').blur(function(){
	var projectName = $('#project_name_edit').val().trim();
	if(projectName==""||projectName=="undefined"){
		$('.projectname-edit-label').hide();
		return false;
	}else{
		var projectID = '${projectId}';
		var data1 = {
			'projectName':projectName,
			'id' : projectID
		}
		sendPostRequestByJsonObj(platformUrl.checkProjectName,data1,function(data){ 
				if(data.result.status=="ERROR"){
					if(data.result.errorCode == "name-repeat"){
						$('.projectname-edit-label').show();
						return false
					}
				}else if(data.result.status ==='OK'){
					$('.projectname-edit-label').hide();
				}
		})
	}
	
	
	
}) 
//查询 FAtitke 
function showFaname(){ 
sendGetRequest(platformUrl.editProjectAreaInfo + projectInfoDetail.id + "/NO1_1",null,showName);
	function showName(data){ 
		var code = $("#faName").attr("data"); 
		code="1120,"+code;
		var valList =data.entity.childList ;  
		if(valList.filter(function(val){return val.valRuleFormula==code})[0]==undefined||valList.filter(function(val){return val.valRuleFormula==code})[0].resultList==undefined){

		}else{
			var content = valList.filter(function(val){ return val.valRuleFormula==code})[0].resultList[0].contentDescribe1;
			if(content){		
				$("#faName").attr('data-original-title',content);
				$("#faName[data-toggle='tooltip']").tooltip();//提示
			}else{
				$("#faName").removeAttr('data-original-title');
			}
			var  List =valList.filter(function(val){return val.valRuleFormula==code})[0].resultList;
			if(valList.filter(function(val){return val.valRuleFormula==code})[0].id=="1118"&&List){
					var Str=""
				$.each(List,function(){ 
					Str+=$(this)[0].valueName=="非投资线员工"?$(this)[0].contentDescribe1:$(this)[0].valueName;		
					Str+='、'; 
				}) 
				Str=Str.substring(0,Str.length-1); 
				$("#faName").attr('data-original-title',Str);
				$("#faName[data-toggle='tooltip']").tooltip();//提示
			}
		} 
		//承作人
		var czrData = data.entity.childList.filter(function(val){return val.id==1103})[0];
		var czrList = czrData.dataList;
		if(czrList.length>1){ 
			$("#czrNum").text(czrList.length)
		}else{ 
			$("#undeLine_sop").hide()
		} 
		sendGetRequest(platformUrl.getUsersInfo, null, function(data){
			userInfo=data.entityList; 
		});
		$("#undeLine_sop").click(function(){ 
			//czrList
			$.getHtml({
				url:"/sop/html/czr_pop.html",//模版请求地址
				data:"",//传递参数
				okback:function(){
					$("#pop-title-czr").text("项目承作人");
					$("#learning").remove();
					$("#czrTableInfo").show();  
					buildTable(czrData);
				} 
			}) 
		})
	}
}

</script>

</html>

