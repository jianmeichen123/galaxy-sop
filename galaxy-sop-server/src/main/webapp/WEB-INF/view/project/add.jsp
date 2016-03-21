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
                          	<input type="text" class="datepicker time" name="createDate" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/>
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
                          <dd><input type="text" id="projectContribution" name="projectContribution" value="" placeholder="计划额度" valType="NUMBER" msg="<font color=red>*</font>只能是数字"></dd>
                        </dl>
                      </td>                      
                      <td>
                        <dl>
                          <dt>初始估值：</dt>
                          <dd id="projectValuations"></dd>
                        </dl>
                      </td>

                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>出让股份：</dt>
                          <dd><input type="text" id="projectShareRatio" name="projectShareRatio" value="" class="transferSharesTxt" valType="NUMBER" msg="<font color=red>*</font>只能是数字"><span>&nbsp;%</span></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>单位（万）：</dt>
                          <dd>
                            <label><input name="currencyUnit" type="radio" value="0"  checked/>人民币</label>
                            <label><input name="currencyUnit" type="radio" value="1" />美元</label>
                            <label><input name="currencyUnit" type="radio" value="2" />英镑</label>
                            <label><input name="currencyUnit" type="radio" value="3" />欧元</label>
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>公司名称：</dt>
                          <dd><input type="text" id="projectCompany" name="projectCompany" value="" placeholder="公司名称" valType="required" msg="<font color=red>*</font>公司名称不能为空"></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>组织机构代码：</dt>
                          <dd><input type="text" id="projectCompanyCode" name="projectCompanyCode" value="" placeholder="组织机构代码" class="zzjg_txt"  valType="OTHER" regString="^[a-zA-Z\d]+$" msg="<font color=red>*</font>组织代码只能是字母或数字"></dd>
                        </dl>
                      </td>
                  </tr>                   
                </tbody>
                </form>
              </table>
              <div class="btnbox">
                <a href="javascript:add();" class="pubbtn bluebtn">保存</a>
              </div>
          </div>
          <!-- 第2部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>项目概述</dt>
            </dl>
          </div>
          <!-- 第3部分 -->
          <div class="block block2">
            <dl>
              <dt>商业模式</dt>
            </dl>
          </div>
          <!-- 第4部分 -->
          <div class="block block2">
            <dl>
              <dt>公司定位</dt>
            </dl>
          </div>
          <!-- 第5部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>用户分析</dt>
            </dl>
          </div>
          <!-- 第6部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>竞情分析</dt>
            </dl>
          </div>
          <!-- 第7部分 -->
          <div class="block block2 clearfix">
            <dl>
              <dt>团队成员</dt>
            </dl>
          </div> 
          <!-- 第8部分  -->      
          <div class="block block2">
            <dl>
              <dt>股权结构</dt>
            </dl>
          </div> 
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
	$(function(){
		createMenus(4);
		sendGetRequest(platformUrl.getProjectCode, {}, function(data){
			var code = data.entity.pcode;
			$("#pcode").empty();
			$("#pcode").html(code);
		});
		$("#projectShareRatio").blur(function(){
			var valuations = calculationValuations();
			$("#projectValuations").text("");
			if(valuations){
				$("#projectValuations").text(valuations);
			}
		});
		$("#projectContribution").blur(function(){
			var valuations = calculationValuations();
			$("#projectValuations").text("");
			if(valuations){
				$("#projectValuations").text(valuations);
			}
		});
	});
	function calculationValuations(){
		var projectShareRatio = $("#projectShareRatio").val();
		var projectContribution = $("#projectContribution").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			return projectContribution * (100/projectShareRatio);
		}
		return null;
	}
	function add(){
		if(beforeSubmit()){
			sendPostRequestByJsonObj(platformUrl.addProject, JSON.parse($("#add_form").serializeObject()), function(){
				forwardWithHeader(sopContentUrl + "/galaxy/mpl");
			});
		}
	}
</script>

</html>

