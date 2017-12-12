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
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<%-- <link href="<%=path %>/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet"/> --%>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<style>
	body{
		background-color:#E9EBF2;
	}
</style>
</head>

<body >

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
		<div class="new_tit_b add-project-title">
            <span>添加项目</span>
		</div>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
 	<div class="ritmin add-project-ritmin">
		<%-- <div class="new_tit_a"><a href="#">工作桌面</a><img alt="" src="<%=path %>/img/arrow-brumd.png" class="arrow"/><a href="#">创投项目</a><img alt="" src="<%=path %>/img/arrow-brumd.png" class="arrow"/>添加项目</div> --%>
        <div class="new_left add-poject-right">
        	<div class="tabtable_con_on add-project-tabtable">
                    <!--基本信息-->
                 <form action="" id="add_form" method="post" autocomplete="off">
                  <div class='addpro-basic-content'>
                    <div class="addpro-new-title ">
                        <span class="new_color  add-pro-basicmessage">基本信息</span>
                    </div>  
                    <ul class="basic_ul addpro-basi-ul clearfix">
                    	<li>
                        	<span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>项目类型：</span></span>
                            <span class="m_r30 inpu-self inpu-self-checked"><input class='inpu-radio' name="projectType" type="radio" value="projectType:1" id="radio_w" checked='checked'><label for="radio_w">投资</label></span>
                            <span class="m_r30 inpu-self"><input class='inpu-radio' name="projectType" type="radio" value="projectType:2" id="radio_n"><label for="radio_n">创建</label></span>
                            <span class="basic_span addpro-basic-span addpro-left"><em class="red">*</em><span class='letter-space'>创建时间：</span></span>
                             <span class="m_r30"><input type="text" class='datepicker-text addpro-input' name="createDate" id="createDate" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/></span>
                        </li>
                        <li>
                            <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>项目名称：</span></span>
                            <span class="m_r30"><input type="text" class='addpro-input' maxlength="24" id="projectName" name="projectName" <%-- data-msg-required="<font color=red>*</font>项目名称不能为空" --%>/><label class='project-name'>*项目名称重复</label></span>
                       		<span class="basic_span addpro-basic-span addpro-marin-lt"><em class="red">*</em><span class='letter-space rzlc_span'>本轮融资轮次：</span></span>
                            <span class="m_r30">
								<select name="financeStatus" class='new_nputr addpro-input addpro-input-arrow ' data-title-id="1108" data-type="14">
									<!-- <option value="">请选择</option> -->
			                    </select>
							</span>
                        </li>
                        <li>
                        	
                            <span class="basic_span addpro-basic-span "><em class="red">*</em><span class='letter-space'>项目来源：</span></span>
                            <span class="m_r30">
	                            <select name="proSource" class='new_nputr addpro-input addpro-input-arrow ' data-title-id="1120" data-type="14" >
				                    	<option value="">请选择</option>
				                </select>
								<!-- <input type="text" class="new_nputr addpro-input addpro-input-fh"  placeholder="请输入名称" id="" name=""/>
								<input type="text" class="new_nputr addpro-input addpro-input-ly"  placeholder="请输入名称" id="" name=""/>
								<input type="text" class="new_nputr addpro-input addpro-input-ct"  placeholder="请输入名称" id="" name=""/>
								<input type="text" class="new_nputr addpro-input addpro-input-mtbd"  placeholder="请输入名称" id="" name=""/> -->
                       		</span>                       		
                       		<span class="basic_span addpro-basic-span addpro-marin-lt"><em class="red">*</em><span class='letter-space'>行业归属：</span></span>
                            <span class="m_r30">
                            	<select name="industryOwn" class='new_nputr addpro-input addpro-input-arrow '>
			                    	<option value="">请选择</option>
			                    </select>
                            </span>
                        </li>
                        <li class="projectSourceli">
                        	<div class="projectSource projectSource6">
                        		 <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>FA名称 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1122" data-type="1"   maxlength="20"  placeholder="请输入FA名称（必填）" id="proS6" name="proS6"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource7">
                        		 <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>孵化器名称 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1123" data-type="1" maxlength="50" placeholder="请输入孵化器名称" id="proS7" name="proS7"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource8">
                        		 <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>路演活动名称 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1124" data-type="1" maxlength="50" placeholder="请输入机构及路演名称" id="proS8" name="proS8"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource9">
                        		 <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>各创投数据库 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1125" data-type="1" maxlength="50" placeholder="请输入创投机构名称" id="proS9" name="proS9"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource10">
                        		 <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>专业媒体报道 ：</span></span>
                        		 <span class="m_r30">
									<input type="text" class="addpro-input" data-title-id="1126" data-type="1" maxlength="50" placeholder="请输入媒体名称" id="proS10" name="proS10"/>
								 </span>
                        	</div>
                        	<div class="projectSource projectSource11">
                        		 <span class="basic_span addpro-basic-span"><em class="red">*</em><span class='letter-space'>项目承揽人 ：</span></span>
                        		 <span class="m_r30 selectcheck" >
									<select id="selectRadio" name="projectContractor" class="selectpicker" multiple data-live-search="true" data-type="13" data-title-id="1118">
									    
									  </select>
									  <input type="text" class="addpro-input"  />
								</span>
                        	</div>
                        </li>
                    </ul>  
                </div>
                    <!--融资计划-->
                <div class='addpro-finacing-plan'>
                    <div class="addpro-new-title ">
                        <span class="new_color  add-pro-basicmessage">融资计划</span>
                    </div> 
                    <ul class="basic_ul addpro-finacing-ul">
                        <li>
                            <span class="basic_span letter-space add-finace-lf">融资金额：</span>
                            <span class="m_r15">
                            	<input type="text" placeholder='融资金额（万元）' class='new_nputr_number addpro-input' id="formatContribution" data-title-id="1916" data-type="19" name="formatContribution procontribution" data-rule-procontribution="true"  data-msg-procontribution="<font color=red>*</font>支持9位长度的四位小数"/>
                            </span>
                            <!-- <span class="m_r30">万元</span> -->
                            
                        </li>
                        <li>
	                        <span class="basic_span letter-space add-finace-lf">出让股份：</span>
                            <span class="m_r15">
                            	<input type="text" placeholder='出让股份（%）' class='new_nputr_number addpro-input' id="formatShareRatio" data-title-id="1917" data-type="19" name="formatShareRatio proshare"  data-rule-proshare="true" data-msg-proshare="<font color=red>*</font>0到100之间的两位小数"/>
                            </span>
                            <!-- <span class="m_r30">% </span> -->
	                    </li>
                        <li>
                        	<span class="basic_span letter-space add-finace-lf">项目估值：</span>
                            <span class="m_r15">
                            	<input type="text" placeholder='项目估值（万元）' class='new_nputr_number addpro-input' id="formatValuations" data-title-id="1943" data-type="19" name="formatValuations provaluations"  data-rule-provaluations="true" data-msg-provaluations="<font color=red>*</font>支持13位长度的四位小数"/>
                            </span>
                            <!-- <span class="m_r30">万元</span> -->
                        </li>
                    </ul>
               </div>
               
                    <!--实际投资-->
                <div class='addpro-business-plan'>
	                <div class="addpro-new-title ">
                        <span class="new_color  add-pro-basicmessage">商业计划书<em>(文件上传大小不超过25MB)</em></span>
                    </div> 
	                <!-- 商业计划书表格-->
	                <table style="width:97%;" id="plan_business_table" cellspacing="0" cellpadding="0" class="business-plan-table">
	                </table>
	                <div class="compile_on_center">
                       <div class="compile_on_left addpro-compile">
                           <span class="pubbtn adddpro-save" onclick="add();">保存</span>
                           <span class="pubbtn addpro-cacel" data-name='industry' data-on="close">取消</span>
                       </div>  
                   </div>
                </div>
                 </form>
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
					
					    <a href="javascript:;" class="pubbtn bluebtn" id="win_uploadBtn" style="margin-left:80px;">上传保存</a>
					<%--     <input type="hidden" id="pathInput" value="<%=path%>"> --%>
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
<script type='text/javascript' src='<%=path%>/js/validate/jquery.validate.min.js'></script>
<script type='text/javascript' src='<%=path%>/js/projectDetail/tabInfoValidate.js'></script>
<script type="text/javascript">
//计算距离的左边距
// detailHeaderWidth();
// function detailHeaderWidth(){
// 	  var  w_lft=$(".lft").width();
// 	  	$('.add-project-title').css({'margin-left':w_lft});
// }
// $(window).resize(function(){
// 	detailHeaderWidth();
// })	
//radio样式切换
$('.inpu-self').click(function(){
	$(this).addClass('inpu-self-checked').siblings().removeClass('inpu-self-checked');
	$('.inpu-self-checked').find('input').attr('checked',true);
	$('.inpu-self-checked').siblings().find('input').attr('checked',false);
});


$('.addpro-basi-ul li select.addpro-input-arrow').click(function(){
	var _this = $(this);
	_this.toggleClass('addpro-input-arrow-up')
});
$('.addpro-basi-ul li select.addpro-input-arrow').blur(function(){
	var _this = $(this);
	_this.removeClass('addpro-input-arrow-up')
})



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
	 * 获取项目承揽人下拉项
	 * @version 2016-06-21
	 */
	 sendGetRequest(platformUrl.queryAllTitleValues+'NO1?reportType=4', null,CallBackE);
function CallBackE(data){
	var data_list = data.entity.childList[0].childList;
	var dataresu =data_list.filter(o=>o.id=="1118")[0].valueList;
	var res="";
	$.each(dataresu,function(){
		res+="<option value='"+this.id+"' data-title-id='"+this.titleId+"'>"+this.name+"</option>"
	})
	$("#selectRadio").html(res)
	}
	 
	 sendGetRequest(platformUrl.queryAllTitleValues+'FNO1?reportType=4', null,CallBackB);
	function CallBackB(data){
	    var _dom=$("select[name='financeStatus']");
	        _dom.html("");
	        _dom.append('<option value="">请选择</option>');
	    var childNum = _dom.find("option").length;
	    var entity=data.entity.childList[0];
	    if(!childNum || childNum !=0 ){
	    	$.each(entity.valueList,function(){
	    		_dom.append("<option value='"+this.id+"' data-title-id='"+this.titleId+"'>"+this.name+"</option>");
			});
	    }
	    CallBackD(data)
	}

	/**
	 * 项目来源
	 * @version 2017-12-07
	 */
	function CallBackD(data){
	    var _dom=$("select[name='proSource']");
	        _dom.html("");
	        _dom.append('<option value="">请选择</option>');
	    var childNum = _dom.find("option").length;
	    var entity=data.entity.childList[1];
	    if(!childNum || childNum !=0 ){
	    	$.each(entity.valueList,function(){
	    		_dom.append("<option value='"+this.id+"' code='"+this.code+"'  data-title-id='"+this.titleId+"'>"+this.name+"</option>");
			});
	    }
	}
	$("#selectRadio").change(function(){
		var otherValue = $(this).find("option").last().val();
		var value = $(this).val();
		if(value==null){
			$(".selectcheck .addpro-input").hide();
			return;
		}
		var filt = value.filter(o=>o==otherValue);
		if(filt.length>0){
			$(".selectcheck .addpro-input").show();
			$(".selectcheck .addpro-input").attr("ovalue",filt[0])
		}else{
			$(".selectcheck .addpro-input").hide();
		}
	})

	/**
	 * 获取项目来源下拉项
	 * @version 2016-06-21
	 */
	//createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"projectSource","faFlag");
	
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
		 $('.selectpicker').selectpicker({
			 	dropupAuto:false
            });
		projectSelect();
		
	});
	function projectSelect(){
		$("select[name='proSource']").change(function(){
			$(".projectSource").hide();
			var selCode=$(this).find("option:checked").attr("code");
			$("."+selCode).show();
			 
		})
	}
	function calculationValuations(){
		var projectShareRatio = $("#formatShareRatio").val();
		var projectContribution = $("#formatContribution").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			return projectContribution * (100/projectShareRatio);
		}
		return null;
	}
//项目名称重复checkProjectName
  $('#projectName').blur(function(){
	var projectName=$("#projectName").val().trim();
	var data2 = {
			'projectName' : projectName
	}
	sendPostRequestByJsonObj(platformUrl.checkProjectName,data2,function(data){
		console.log(data)
			if(data.result.status=="ERROR"){
				if(data.result.errorCode == "name-repeat"){
					$('.project-name').css('display','block');
				}
			}else if(data.result.status ==='OK'){
				$('.project-name').css('display','none');
			}
	})
	
	
})  
//添加项目页面保存按钮
	function add(){
		if(!$('#add_form').validate().form()){//验证不通过时候执行
			$(".adddpro-save").submit();
			return false;	
		}
		var val=$('input:radio[name="projectType"]:checked').val();
		if(val == null || typeof(val) == "undefined"){
			$("#projectTypeTip").css("display","block");
			return;
		} 
		var data1= JSON.stringify(getUpdateData());//转换成字符串
		console.log()
		if(formData != data1){
			
			//获取TOKEN 用于验证表单提交
			sendPostRequest(platformUrl.getToken,function(data){
				TOKEN=data.TOKEN;
				return TOKEN;
			});
		} 
		console.log(data1);
			sendPostRequestBySignJsonStr(platformUrl.addProject,data1, function(data){
				console.log(data);
				if(!data){
					layer.msg("提交表单过于频繁!");
				}else if(data.result.status=="ERROR"){
					if(data.result.errorCode == "csds"){
						layer.msg("必要的参数丢失!");
					}else if(data.result.errorCode == "myqx"){
						layer.msg("没有权限添加项目!");
					}
					// else if(data.result.errorCode == "mccf"){
					// 	layer.msg("项目名重复!");
					// }
					formData = JSON.stringify(getUpdateData());
				}else{
					saveBaseInfo("add_form",data.id,data.id);
					
				}
				
			},TOKEN);
		
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
			if(field.data('titleId')=="1118"&&type=="13"){
				//获取多选带备注数据 
				var values =[] ; 
				var doms = $(".selectcheck li.selected span");
				$.each(doms,function(){ 
					values.push($(this).attr('data-value'))
				})  
				var remark = $('.selectcheck .addpro-input').val();
				var other = $('.selectcheck .addpro-input').attr("ovalue");  
				for(i=0;i<values.length;i++){ 
					var infoMode = {
							titleId	: field.data('titleId'),
							tochange:'true',
							resultId:_resultId,
							type : type
						};
					var that = values[i]; 
					infoMode.value=that;  
					if(other==that&&remark!=''&&remark!=null){  
						infoMode.remark1=remark;
					}
					infoModeList.push(infoMode); 
				}  

			}else if(type==14 )
			{
				infoMode.value = field.val();
			}else if(type==19){
				infoMode.remark1 = field.val();
			}else if(type==1){ 				
				infoMode.value=field.val();
			}
			if (infoMode != null&&type!="13") {
		        infoModeList.push(infoMode);
		    }
			data.infoModeList = infoModeList;
		});
		console.log(data);
		
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
		var formatData={
					   "projectType":projectType,
				       "projectName":projectName,
				       "createDate":createDate,
				       "industryOwn":industryOwn
		};
		return formatData;
	}

</script>

</html>

