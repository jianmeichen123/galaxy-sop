<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
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
		<div class="new_tit_a"><a href="#">工作桌面</a><img alt="" src="<%=path %>/img/arrow-brumd.png" class="arrow"/><a href="#">创投项目</a><img alt="" src="<%=path %>/img/arrow-brumd.png" class="arrow"/>添加项目</div>
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
                    <form action="" id="add_form" method="post" autocomplete="off">
                    <ul class="basic_ul">
                    	<li>
                        	<span class="basic_span"><em class="red">*</em>项目类型：</span>
                            <span class="m_r30"><input name="projectType" type="radio" value="projectType:1" id="radio_w" checked="checked"><label for="radio_w">投资</label></span>
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
								<select name="financeStatus" class='new_nputr' valtype="required" msg="<font color=red>*</font>本轮融资轮次不能为空" data-title-id="1108" data-type="14">
								<option value="">--请选择--</option>
			                    </select>
							</span>
                        </li>
                        <li>
                        <span class="basic_span"><em class="red">*</em>项目来源：</span>
                            <span class="m_r30" style="with:400px">
	                            <select name="faFlag" class='new_nputr' valType="required" msg="<font color=red>*</font>项目来源不能为空">
				                    	<option value="">--请选择--</option>
				                   </select> 
	                            <!--  <input type="radio" name="faFlag"  value="0"  checked="checked">否</span>
	                             <input type="radio" name="faFlag"  value="1" id="faFlag2">是  -->
	                             <input type="text" class="new_nputr"  placeholder="请输入FA名称"  name="faName"  valType="OTHER" regString="^[^\s](.{0,19})$" id="faName" msg="<font color=red>*</font>不能以空格开头，字符最大长度为20"/>
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
                            	<input type="text" class='new_nputr_number' id="formatContribution" data-title-id="1916" data-type="19" name="formatContribution" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>支持9位长度的支持四位小数"/>
                            </span>
                            <span class="m_r30">万元</span>
                            <span class="basic_span">出让股份：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatShareRatio" data-title-id="1917" data-type="19" name="formatShareRatio" allowNULL="yes" valType="OTHER" regString="^([1-9]\d?(\.\d{1,2})?|0\.[1-9]0?|0\.\d[1-9]|100(\.[0]{1,2})?)$" msg="<font color=red>*</font>0到100之间的两位小数"/>
                            </span>
                            <span class="m_r30">% </span>
                        </li>
                        <li>
                        	<span class="basic_span">项目估值：</span>
                            <span class="m_r15">
                            	<input type="text" class='new_nputr_number' id="formatValuations" data-title-id="1943" data-type="19" name="formatValuations" allowNULL="yes" valType="LIMIT_13_NUMBER" msg="<font color=red>*</font>支持13位长度的四位小数"/>
                            </span>
                            <span class="m_r30">万元</span>
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
                    <!-- 商业计划书隐藏页面 -->
					<div id="uploadPanel"  style="display: none;">
						<div class="title_bj">上传更新</div>
						<div class="meetingtc margin_45">
						<dl class="fmdl clearfix">
					    	<dt>档案来源：</dt>
					        <dd class="clearfix">
					        	<label><input name="win_fileSource" type="radio" value = "1" checked="checked"/>内部</label>
					            <label><input name="win_fileSource" type="radio" value = "2"/>外部</label>
					        </dd>
					    </dl>
					    <dl class="fmdl clearfix">
					    	<dt>存储类型：</dt>
					        <dd>
					        	<select id="win_fileType">
					            	<option>sadasd</option>
					            </select>
					        </dd>
					    </dl>
					    <dl class="fmdl clearfix">
					    	<dt>业务分类：</dt>
					        <dd>
					<!--          	<input type="text" id="fileWorkType"  class="txt"/> -->
					<!--          	<input type="hidden" id="fileWorkTypeId"/> -->
					         	
					         	<select id="win_fileWorkType">
					            	<option>sadasd</option>
					            </select>
					         	
					        </dd>
					        <dd id="win_isProve_div">
					        	<label><input type="checkbox" value="1" id="win_isProve"/>签署凭证</label>
					        </dd>
					    </dl>
					    <dl class="fmdl clearfix">
					    	<dt>所属项目：</dt>
					        <dd>
					            <input type="hidden" id="win_sopFileId" data-tid=""  class="txt disabled"/>
					        	<input type="text" id="win_sopProjectId" data-tid=""  class="txt disabled"/>
					        </dd>
					       <dd><a class="searchbtn null" id="win_searchProjectBtn" href="javascript:;">搜索</a></dd>
					   
					    </dl>
					    
					     <dl class="fmdl clearfix">
					    	<dt>文档上传：</dt>
					        <dd>
					        	<input type="text" class="txt" id="win_fileTxt" readonly="readonly"/>
					        </dd>
					        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="win_selectBtn">选择档案</a></dd>
					    </dl>  
					    <TEXTAREA ID="win_FILELIST"></TEXTAREA>
					<!--     <div class="fmarea"> -->
					<!-- 		<div  id="filelist"></div> -->
					<!-- 		<div  id="console"></div> -->
					<!--     </div> -->
					    <a href="javascript:;" class="pubbtn bluebtn" id="win_uploadBtn" style="margin-left:80px;">上传保存</a>
					<%--     <input type="hidden" id="pathInput" value="<%=path%>"> --%>
						</div>
					</div>
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
        <!-- 	<div class="tabtable_con_on">
            	<div class="new_bottom_color">
                    <span class="new_ico_hint"></span>
                    <span class="new_color size16">温馨提示</span>
                </div>
                <p class="basic_p">标记 <em class="red">*</em> 的内容需要进行填写，填写后方能进入内部评审阶段。（包括基本信息中的商业计划书、融资计划、项目描述、公司定位、用户画像、产品服务、行业分析、竞争分析；访谈记录；团队成员中的基本信息）</p>
            </div> -->
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
	<script type='text/javascript' src='<%=request.getContextPath() %>/js/teamSheetNew2.js'></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/addPlanbusiness2.js'></script>
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
	//createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus");
	sendGetRequest(platformUrl.queryAllTitleValues+'FNO1?reportType=4', null,CallBackB);
	function CallBackB(data){
	    var _dom=$("select[name='financeStatus']");
	        _dom.html("");
	        _dom.append('<option value="">--请选择--</option>');
	    var childNum = _dom.find("option").length;
	    var entity=data.entity.childList[0];
	    if(!childNum || childNum !=0 ){
	    	$.each(entity.valueList,function(){
	    		_dom.append("<option value='"+this.id+"' data-title-id='"+this.titleId+"'>"+this.name+"</option>");
			});
	    }
	}
	/**
	 * 获取项目来源下拉项
	 * @version 2016-06-21
	 */
	createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"projectSource","faFlag");
	
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
		//项目来源切换
		$("select[name='faFlag']").change(function(){
			var text=$(this).find("option:checked").text();
			if(text=="FA"){
				$(this).siblings(".new_nputr").show();
			}else{
				$(this).siblings(".new_nputr").hide();
				$(".tip-yellowsimple").each(function(){  //隐藏提示  
		            if($(this).children(".tip-inner").text()=="*不能以空格开头，字符最大长度为20"){
		            	$(this).remove();
		            }
		        }); 
			}
		})
		
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
		var data1= JSON.stringify(getUpdateData());
		if(formData != data1){
			//获取TOKEN 用于验证表单提交
			sendPostRequest(platformUrl.getToken,function(data){
				TOKEN=data.TOKEN;
				return TOKEN;
			});
		} 
		
		if(beforeSubmit()){
			sendPostRequestBySignJsonStr(platformUrl.addProject,data1, function(data){
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
					formData = JSON.stringify(getUpdateData());
				}else{
					saveBaseInfo("add_form",data.id,data.id);
					
				}
				
			},TOKEN);
		}
	}
	
	function saveBaseInfo(dom,projectId,Id){
		
		var infoModeList = new Array();
		var fields = $("#"+dom).find("input[data-title-id],select[data-title-id]");
		var data = {
				projectId : projectId
			};
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var sele = field.get(0).tagName;
			var _resultId = field.attr("data-result-id");
			if(_resultId==undefined){
				_resultId=null;
			}
			var infoMode = {
				titleId	: field.data('titleId'),
				tochange:'true',
				resultId:_resultId,
				type : type
			};
			if(type==14 )
			{
				infoMode.value = field.val();
			}else if(type==19){
				infoMode.remark1 = field.val();
			}	
			if (infoMode != null) {
		        infoModeList.push(infoMode);
		    }
			data.infoModeList = infoModeList;
		});
		sendPostRequestByJsonObjNoCache(
				platformUrl.saveOrUpdateInfo , 
				data,
				true,
				function(data) {
					var result = data.result.status;
					if (result == 'OK') {
						forwardWithHeader(Constants.sopEndpointURL + "/galaxy/project/detail/"+Id+ "?backurl=list");
					} else {
						
					}
			});
	}
	
	function getUpdateData(){  //获取保存数据
		var projectType=$('input:radio[name="projectType"]:checked').val();
		var projectName=$("#projectName").val().trim();
		var createDate=$("#createDate").val().trim();
		var industryOwn=$('select[name="industryOwn"] option:selected').attr("value");
		var faFlag=$('select[name="faFlag"] option:selected').attr("value");
		var faName="";
		if(faFlag=='projectSource:1'){
			faName=$("input[name='faName']").val();
		}else{
			faName="";
		}
		
		var formatData={
					   "projectType":projectType,
				       "projectName":projectName,
				       "createDate":createDate,
				       "industryOwn":industryOwn,
	  	               "faFlag":faFlag,
	  	               "faName":faName
		};
		return formatData;
	}

</script>

</html>

