<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<!-- 校验样式 -->
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/reset.css" /> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
    <h2>我的项目</h2>
      <!-- 面包屑 -->
     <!--  <ul class="breadcrumb">
       <li><a href="javascript:;" class="bcfirst">我的项目</a></li>
       <li><span>&gt;</span><a href="javascript:;">项目基本信息</a></li>
       <li><span>&gt;</span><a href="javascript:;" class="active">项目详情信息</a></li>
     </ul> -->
      <div class="clearfix"></div>
        <!--项目详细信息内容-->
        <div class="projectmsg_d clearfix">
          <!-- 第1部分 -->
          <div class="block block1">
            <table width="100%" cellspacing="5" cellpadding="0" >
            <form action="" id="add_form" method="post">
             <tbody>
                  <tr>
                      <td>
                        <dl>
                          <dt>项目编码：</dt>
                          <dd id="pcode"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>创建时间：</dt>
                          <dd>
                          	<input type="text" class="datepicker-text time" name="createDate" id="createDate" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/>
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>项目名称：</dt>
                          <dd><input type="text" id="projectName" name="projectName" value="" placeholder="项目名称" valType="required" msg="<font color=red>*</font>项目名称不能为空"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>项目类型：</dt>
                          <dd>
                            <label><input name="projectType" type="radio" value="projectType:2"  checked/>内部创建</label>
                            <label><input name="projectType" type="radio" value="projectType:1" />外部投资</label>
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>计划额度：</dt>
                          <dd><input type="text" id="formatContribution" name="formatContribution" value="" placeholder="计划额度" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>只能为整数或两位小数点的数字"></dd>
                        </dl>
                      </td>                      
                      <td>
                        <dl>
                          <dt>估值：</dt>
                          <dd>
                          	<input type="text" id="formatValuations" name="formatValuations" value="" placeholder="估值" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>只能为整数或两位小数点的数字">
                          </dd>
                        </dl>
                      </td>

                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>出让股份：</dt>
                          <dd><input type="text" id="formatShareRatio" name="formatShareRatio" value="" class="transferSharesTxt" allowNULL="yes" valType="LIMIT_2_INTEGER" msg="<font color=red>*</font>0-100间整数"><span>&nbsp;%</span></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>单位（万）：</dt>
                          <dd>
                            <label><input name="formatUnit" type="radio" value="0" checked/>人民币</label>
                            <label><input name="formatUnit" type="radio" value="1" />美元</label>
                            <label><input name="formatUnit" type="radio" value="2" />英镑</label>
                            <label><input name="formatUnit" type="radio" value="3" />欧元</label>
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>公司名称：</dt>
                          <dd><input type="text" id="projectCompany" name="projectCompany" value="" placeholder="公司名称"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>组织机构代码：</dt>
                          <dd><input type="text" id="projectCompanyCode" name="projectCompanyCode" value="" placeholder="组织机构代码" class="zzjg_txt"  allowNULL="yes" valType="CODE" msg='<font color=red>*</font>由字母或数字或"-"组成'></dd>
                        </dl>
                      </td>
                  </tr>                   
                </tbody>
                </form>
              </table>
              <div class="btnbox">
                <a href="javascript:;" onclick="add('save');" class="bluebtn pubbtn">保存</a>
                <a href="javascript:;" onclick="add('saveandupdate');" class="pubbtn bluebtn">保存并编辑</a>
                 
              </div>
          </div>
          <!-- 第2部分 -->
     <!--      <div class="block block2 shadow">
            <dl>
              <dt>项目概述</dt>
            </dl>
          </div>
          第3部分
          <div class="block block2">
            <dl>
              <dt>商业模式</dt>
            </dl>
          </div>
          第4部分
          <div class="block block2">
            <dl>
              <dt>公司定位</dt>
            </dl>
          </div>
          第5部分
          <div class="block block2 shadow">
            <dl>
              <dt>用户分析</dt>
            </dl>
          </div>
          第6部分
          <div class="block block2 shadow">
            <dl>
              <dt>竞情分析</dt>
            </dl>
          </div>
          第7部分
          <div class="block block2 clearfix">
            <dl>
              <dt>团队成员</dt>
            </dl>
          </div> 
          第8部分       
          <div class="block block2">
            <dl>
              <dt>股权结构</dt>
            </dl>
          </div>  -->
          <!-- 第9部分     
          <div class="block block2">
            <dl>
              <dt>档案库</dt>
            </dl>
          </div> -->          
        </div>
    </div> 
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
   var message = "";
   var result=false;
   var TOKEN ;
	$(function(){
		$("#createDate").val(new Date().format("yyyy-MM-dd"));
		createMenus(5);
		//获取TOKEN 用于验证表单提交
		sendPostRequest(platformUrl.getToken,callback);
		sendGetRequest(platformUrl.getProjectCode, {}, function(data){
			var code = data.entity.pcode;
			$("#pcode").empty();
			$("#pcode").html(code);
		});
		$("#formatShareRatio").blur(function(){
			var valuations = calculationValuations();
			if(valuations != null){
				$("#formatValuations").val(valuations.toFixed(2));
			}
		});
		$("#formatContribution").blur(function(){
			var valuations = calculationValuations();
			if(valuations != null){
				$("#formatValuations").val(valuations.toFixed(2));
			}
		});
	});
	function callback(data){
		TOKEN=data.TOKEN;
		return TOKEN;
	}
	function calculationValuations(){
		var projectShareRatio = $("#formatShareRatio").val();
		var projectContribution = $("#formatContribution").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			return projectContribution * (100/projectShareRatio);
		}
		return null;
	}
	var b = new Base64();
	function add(obj){
		if(beforeSubmit()){
			var json = {};
			var projectName = $("#projectName").val();
			var projectCompanyCode = $("#projectCompanyCode").val();
			json = {"projectName":projectName,"projectCompanyCode":projectCompanyCode};
			sendPostRequestByJsonObj(platformUrl.checkProject,json,callbackcheckProject);
			if(result){
				layer.msg(message);
				$("#projectName").val("");
				result=false;
				return false;
			}
			$.ajax({
				url : platformUrl.addProject,
				data : b.encode(JSON.stringify(JSON.parse($("#add_form").serializeObject()))),
			//  data : JSON.stringify(JSON.parse($("#add_form").serializeObject())),
				async : false,
				type : 'POST',
				contentType : "application/json; charset=UTF-8",
				dataType : "text",
				cache : false,
				beforeSend : function(xhr) {
					if (TOKEN) {
						xhr.setRequestHeader("TOKEN", TOKEN);
					}
					if (sessionId) {
						xhr.setRequestHeader("sessionId", sessionId);
					}
					if(userId){
						xhr.setRequestHeader("guserId", userId);
					}
				},
				error : function() {
					layer.msg("操作失败");
				},
				success : function(data) {
					data = JSON.parse(b.decode(data));
					if(data.result.status=="ERROR"){
						layer.msg("项目名重复，请重新输入");
					}else{
						if(obj=="save"){
							forwardWithHeader(Constants.sopEndpointURL + "/galaxy/mpl");
						}
						if(obj=="saveandupdate"){
							forwardWithHeader(Constants.sopEndpointURL + "/galaxy/upp/"+data.id);
						}
					}
				}
			}); 
		}
	}
	function callbackcheckProject(data) {
		if (data.count!=0) {
		   message = "存在重复项目名，请重新输入";
			result=true;
		} 
	}
</script>

</html>

