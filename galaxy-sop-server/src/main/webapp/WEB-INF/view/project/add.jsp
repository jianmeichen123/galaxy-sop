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

</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin">
		<div class="new_tit_a"><a href="#">工作桌面</a>><a href="#">创投项目</a>>添加项目</div>
        <div class="new_tit_b">
            <span class="size18">添加项目</span>
        </div>
    	
        <div class="new_left">
        	<div class="tabtable_con_on">
                    <!--融资计划-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_basic ico_add_project"></span>
                        <span class="new_color size16">基本信息</span>
                    </div>  
                    <form action="" id="add_form" method="post">
                    <ul class="basic_ul">
                    	<li>
                        	<span class="basic_span"><em class="red">*</em>项目类型：</span>
                            <span class="m_r30"><input name="projectType" type="radio" value="projectType:1" id="radio_w"><label for="radio_w">投资</label></span>
                            <span class="m_r30"><input name="projectType" type="radio" value="projectType:2" id="radio_n"><label for="radio_n">创建</label></span>
                           <span id="projectTypeTip"  style="display:none;">
                            	<div class="tip-yellowsimple" style="visibility: inherit; left: 452px; top: 202px; opacity: 1; width: 101px;"><div class="tip-inner tip-bg-image"><font color="red">*</font>项目类型不能为空</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div></div>
                            </span>
                        </li>
                        <li>
                            <span class="basic_span"><em class="red">*</em>项目名称：</span>
                            <span class="m_r30"><input type="text" class='new_nputr' maxlength="24" id="projectName" name="projectName" valType="required" msg="<font color=red>*</font>项目名称不能为空"/></span>
                            <span class="basic_span" style="margin-left:23px;"><em class="red">*</em>创建时间：</span>
                            <span class="m_r30"><input type="text" class='datepicker-text new_nputr' name="createDate" id="createDate" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/></span>
                        </li>
                        <li>
                        	<span class="basic_span"><em class="red">*</em>行业归属：</span>
                            <span class="m_r30">
                            	<select name="industryOwn" class='new_nputr' valType="required" msg="<font color=red>*</font>行业归属不能为空">
			                    	<option value="">--请选择--</option>
			                    </select>
                            </span>
                        	<span class="basic_span" style="width:105px;"><em class="red">*</em>本轮融资轮次：</span>
                            <span class="m_r30">
								<select name="financeStatus" class='new_nputr'>
			                    </select>
							</span>
                        </li>
                        <li>
                        <span class="basic_span"><em class="red">*</em>来源于FA：</span>
                            <span class="m_r30" style="with:400px">
                            <span class="m_r30"><input type="radio" name="faFlag" checked=checked  value="0" onclick="setText('reset')">否</span>
                             <input type="radio" name="faFlag" onclick="setText('set')" value="1" id="faFlag2">是
                             <input type="text" class="new_nputr"  placeholder="请输入FA名称" style="display:none" maxlength="20" name="faName" allowNULL="yes" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>姓名只能是汉字或是字符,长度为20" id="faName"/>
                        </span>
                        </li>
                    </ul>  
                    
                    <!--融资计划-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_financing"></span>
                        <span class="new_color size16 m_r15">融资计划</span>
                    </div>  
                    <ul class="basic_ul">
                    	
                        <li>
                            <span class="basic_span">融资金额：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatContribution" name="formatContribution" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持四位小数"/>
                            </span>
                            <span class="m_r30">万元</span>
                            <span class="basic_span">项目估值：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatValuations" name="formatValuations" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持四位小数"/>
                            </span>
                            <span class="m_r30">万元</span>
                        </li>
                        <li>
                        	<span class="basic_span">出让股份：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatShareRatio" name="formatShareRatio" allowNULL="yes" valType="OTHER" regString="^(\d{1,2}(\.\d{1,4})?)$" msg="<font color=red>*</font>0到100之间的四位小数"/>
                            </span>
                            <span class="m_r30">% </span>
                        </li>
                    </ul>
                    </form>
                    <!--实际投资-->
                    <div class="new_r_compile new_bottom_color">
                        <span class="new_ico_book"></span>
                        <span class="new_color size16">商业计划书</span>
                    </div>  
                    <!-- 商业计划书表格-->
                    <table style="width:94%;" id="plan_business_table" cellspacing="0" cellpadding="0" class="basic_table">
                    </table>
                    <div class="compile_on_center">
                       <div class="compile_on_left">
                           <span class="pubbtn bluebtn" onclick="add();">保存</span>
                           <span class="pubbtn fffbtn" data-name='industry' data-on="close">取消</span>
                       </div>  
                   </div>
                </div>
        </div>
       <!--右边-->
        <div class="basic_right">
        	<div class="tabtable_con_on">
            	<div class="new_bottom_color">
                    <span class="new_ico_hint"></span>
                    <span class="new_color size16">温馨提示</span>
                </div>
                <p class="basic_p">标记 <em class="red">*</em> 的内容需要进行填写，填写后方能进入内部评审阶段。（包括基本信息中的商业计划书、融资计划、项目描述、公司定位、用户画像、产品服务、行业分析、竞争分析；访谈记录；团队成员中的基本信息）</p>
            </div>
        </div>
        <!--右边 end--> 
    </div>
     
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/teamSheetNew.js'></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/addPlanbusiness.js'></script>
<!-- 校验 -->
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=path %>/js/validate/lib/jq.validate.js'></script>
<script type="text/javascript">
	/**
	 * @version 2016-06-21
	 */
	$('[data-on="compile"]').on('click',function(){
		$('.bj_hui_on').show();
		$('.compile_on').show();
	});
	$('[data-on="close"]').on('click',function(){
		forwardWithHeader(Constants.sopEndpointURL + "/galaxy/mpl");
	});
	/**
	 * 查询事业线
	 * @version 2016-06-21
	 */
	 createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"industryOwn","industryOwn");
	/**
	 * 获取融资状态下拉项
	 * @version 2016-06-21
	 */
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus", 17);
	
	
   var TOKEN;
   var formData;
	$(function(){
		$("#createDate").val(new Date().format("yyyy-MM-dd"));
		createMenus(5);
		//获取TOKEN 用于验证表单提交
		sendPostRequest(platformUrl.getToken,function(data){
			TOKEN=data.TOKEN;
			return TOKEN;
		});
		$("#formatShareRatio").blur(function(){
			var valuations = calculationValuations();
			if(valuations != null){
				$("#formatValuations").val(valuations.toFixed(4));
			}
		});
		$("#formatContribution").blur(function(){
			var valuations = calculationValuations();
			if(valuations != null){
				$("#formatValuations").val(valuations.toFixed(4));
			}
		});
		$('input:radio[name="projectType"]').click(function(){
			$("#projectTypeTip").css("display","none");
		});
		
	});
	function calculationValuations(){
		var projectShareRatio = $("#formatShareRatio").val();
		var projectContribution = $("#formatContribution").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			return projectContribution * (100/projectShareRatio);
		}
		return null;
	}
	
	function add(){
		var val=$('input:radio[name="projectType"]:checked').val();
		if(val == null || typeof(val) == "undefined"){
			$("#projectTypeTip").css("display","block");
			return;
		}
		var nowFormData = $("#add_form").serializeObject();
		if(formData != nowFormData){
			//获取TOKEN 用于验证表单提交
			sendPostRequest(platformUrl.getToken,function(data){
				TOKEN=data.TOKEN;
				return TOKEN;
			});
		}
		if(beforeSubmit()){
			sendPostRequestBySignJsonStr(platformUrl.addProject, $("#add_form").serializeObject(), function(data){
				if(!data){
					layer.msg("提交表单过于频繁!");
				}else if(data.result.status=="ERROR"){
					if(data.result.errorCode == "csds"){
						layer.msg("必要的参数丢失!");
					}else if(data.result.errorCode == "myqx"){
						layer.msg("没有权限添加项目!");
					}else if(data.result.errorCode == "mccf"){
						layer.msg("项目名重复!");
					}
					formData = $("#add_form").serializeObject();
				}else{
					forwardWithHeader(Constants.sopEndpointURL + "/galaxy/mpl");
				}
				
			},TOKEN);
		}
	}
	
	function setText(obj){
		if(obj=="set"){
			$("#faName").attr("style","display:inline-block;")
			$("#faName").removeAttr("allowNULL");
			$("#faName").focus();
			if($("#faName").val()=="请输入FA名称"){
				$("#faName").attr("style","color:#999;");
			}
		}else{
			$('.tip-yellowsimple').remove();
			$("#faName").val('');
			$("#faName").attr("allowNULL","yes");
			$("#faName").attr("style","display:none;");
		}
		
	}

	$("#faName").keydown(function(){
  		if(this.value=="请输入FA名称"){
  			this.value = "";
  		}
  		if(this.value!="请输入FA名称"){
  			$("#faName").attr("style","color:#333;");
  		}
		
	})
	$("#faName").blur(function(){
  		if(this.value==""){
  			this.value = "请输入FA名称";
  		}
  		if(this.value=="请输入FA名称"){
  			$("#faName").attr("style","color:#999;");
  		}
		
	})
	

	
</script>

</html>

