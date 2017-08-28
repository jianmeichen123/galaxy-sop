<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

<title>项目详情66666</title>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path %>/js/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<!-- 时间插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
</head>
<body>
<!-- <ul class="h_navbar clearfix">
     <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基础<br/>信息</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
    <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('8')">融资及<br/>估值</li>

  </ul> -->
   <!--隐藏-->
<div class="bj_hui_on"></div>
  <jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include>
  <div id="tab-content">
		<div class="tabtxt valuation" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>
	</div>


<script type="text/javascript">
var key = Date.parse(new Date());
var keyJSON={};
var deleteJSON={};
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO9", null,
		function(data) {
		console.log(data);
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				$("#page_list").tmpl(entity).appendTo('#page_all');
				picData(projectInfo.id,1);
				//customBuilder();  表格
				$(".section").each(function(){
					$(this).showResults(true);
					var table = $(this).find('.mb_24 table');
					table.each(function(){
					    resizetable($(this))
						if($(this).find('tr').length<=1){
							$(this).hide();
							if($(this).parents('dl').find('dd:gt(0)').length<=0){
								$(this).parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
							} 
						}
						else{
							$(this).show();
						}
					})
					
				});
				//调整表格
				$("table").css({"width":"80%","table-layout":"fixed"});
				mustData(projectInfo.id,0);
				toggle_btn($('.anchor_btn span'),1);
				fun_click();
				hideNav();
				
			} else {

			}
			
		})
	function customBuilder()
	{
		var div = $("div[data-code='NO9_3_12']");
		var titleId = div.data('titleId');
		var dd_box =$("<dd class='dd_field'></dd>")
		var table = $("<table data-title-id='"+titleId+"'></table>")
		var header = $("<tr></tr>");
		var row = $("<tr></tr>");
		
		var dls = $("dl[data-parent-id='"+titleId+"']");
		$.each(dls,function(){
			var dl = $(this);
			var name = dl.find('dt').text();
			var dd = dl.find('dd');
			header.append("<th>"+name.replace('：','')+"</th>");
			row.append("<td class='field' data-title-id='"+dd.data('titleId')+"'>未填写</td>")
		});
		dls.remove();
		table.append(header);
		table.append(row);
		dd_box.append(table);
		div.after(dd_box);
		
	}
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
		key = Date.parse(new Date());
		var section = $(this).parents('.section');
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');
		var sTop=$(window).scrollTop();
		var str ="";
		if($(this).parents(".h_btnbox").siblings(".h_title").find("span").is(":visible")){
			str =" <span style='color:#ff8181;display:inline'>（如果该项目涉及此项内容，请进行填写，反之可略过）</span>";
		}else{
			str ="";
		}
		keyJSON["b_"+id_code]=key;
		event.stopPropagation();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				console.log(data.entity)
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					sec.showResults();
					$(".h#a_"+id_code).css("background","#fafafa");
					$("#"+id_code).hide();
					validate();
					//调整表格
					$("table").css({"width":"80%","table-layout":"fixed"});
					$(".h_edit .sign_title").css("margin-bottom","20px");
					btn_disable(1);
					$("#b_"+id_code).validate();
					$(".bj_hui_on").show();
					section.find(".h_title span").remove();
					section.find(".h_title").append(str);
					//计算项目估值
					var valRuleFormula=$("input[data-type='19']").attr("data-valruleformula");
					//console.log(valRuleFormula);
					if(valRuleFormula!=null || valRuleFormula!=undefined){
						var valRule=valRuleFormula.split("=");
						var valRule1=valRule[1].split("/");
						var result=valRule[0];
						var parent=valRule1[0];
						var children=valRule1[1];
					}
					
					function calculationValuations(){
						var projectParent = $("input[data-title-id='"+parent+"']").val();
						var projectChildren = $("input[data-title-id='"+children+"']").val();
						var cell=$("input[data-title-id='"+children+"']").attr("data-content");
						if(projectParent > 0 && projectChildren > 0 && cell=="%"){
							return projectParent * (100/projectChildren);
						}
						return null;
					}
					$("div").delegate("input[data-title-id='"+parent+"']","blur",function(){
						var valuations = calculationValuations();
						if(valuations != null){
							$("input[data-title-id='"+result+"']").val(valuations.toFixed(4));
							$("input[data-title-id='"+result+"']").parents("dd").prev().attr("tochange",true);
						}
					});
					$("div").delegate("input[data-title-id='"+children+"']","blur",function(){
						var valuations = calculationValuations();
						if(valuations != null){
							$("input[data-title-id='"+result+"']").val(valuations.toFixed(4));
							$("input[data-title-id='"+result+"']").parents("dd").prev().attr("tochange",true);
						}
					})
					//文本域剩余字符数
					var textarea_h = section.find('.textarea_h');
					for(var i=0;i<textarea_h.length;i++){
						var len=textarea_h.eq(i).val().length;
						var initNum=textarea_h.parent('dd').find(".num_tj").eq(i).find("label").text();
						textarea_h.parent('dd').find(".num_tj").eq(i).find("label").text(initNum-len);
					}
					/* 文本域自适应高度 */
					for(var i=0;i<$("textarea").length;i++){
						var textareaId=$("textarea").eq(i).attr("id");
						autoTextarea(textareaId);
					}
					//检查表格tr是否10行
					check_table_tr_edit();
					edit_bsaicfun();
					//上传图片
					var files = $("#"+id_code).nextAll().find("input[type='file']");
					var selectids = [];
					
					for(var i = 0;i < files.length; i++) {
						  var select_id = files.eq(i).attr("id");
						  var title_id = $("#"+select_id).attr("file-title-id");
							
							var params = {};
							params.fileReidsKey = key;
							params.projectId =  projectInfo.id;
							params.titleId = title_id;
							toBachUpload(Constants.sopEndpointURL+'galaxy/informationFile/sendInformationByRedis',Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile','edit-'+title_id,select_id,"h_save_btn","",null,params,null,null,id_code);
							
							var data={};
							data.projectId = projectInfo.id;
							data.titleId = title_id;
							//打开显示历史图片记录
							sendPostRequestByJsonObj(
							Constants.sopEndpointURL+'galaxy/informationFile/getFileByProject' , 
							data,
							function(data) {
								var result = data.result.status;
								if (result == 'OK') {
									var files = data.entityList;
									var html = $('#'+'edit-'+title_id).html();
									if(files.length > 0){
										for(var i = 0;i < files.length; i++){
											html +=  '<li class="pic_list fl" id="' + files[i].id + '">'
								              +'<a href="javascript:;" class="h_img_del" code="'+"delete_"+id_code+'" data-val=' + files[i].id +
								              ' data-title-val=' + title_id +
								              '></a>' +'<img src="' + files[i].fileUrl + '" name="' + files[i].fileName + '" /></li>';
										       if(i == 4){
								            	  $("#h_imgs_add_"+title_id).hide();
								              }
										}
									}
									$('#'+'edit-'+title_id).html(html);
								} else {

								}
				          }); 
							
							
					}
				} else {

				}
		}) 
		$('body,html').scrollTop(sTop);  //定位
		$("table").each(function(){resizetable($(this))})
		//编辑表格显示隐藏
		 check_table();
	});
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var _this = $(this).parents(".radius");
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		$(".bj_hui_on").hide();
		btn_disable(0);
		$(".h#a_"+id_code).css("background","#fff");
		event.stopPropagation();
		deletedRowIds = new Array();
		toggle_btn($('.anchor_btn span'),0,_this);
		var code=_this.find("table").attr("data-code");
	    
	    resizetable($("table[data-code='"+code+"']"));
	});
	//通用保存
	$('div').delegate(".h_save_btn","click",function(event){
		var save_this = $(this).parents('.radius');
		var btn = this;
		var id_code = $(this).attr('attr-save');
		event.stopPropagation();
		var sec = $(this).closest('form');
		var fields = sec.find("input[type='text'],input:checked,textarea");
		var data = {
			projectId : projectInfo.id
		};

		//普通结果
		var infoModeList = new Array();
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var _tochange =field.parents("dd").prev().attr("tochange");
			var sele = field.parent().get(0).tagName;
			if(sele=="SELECT"){
				var _resultId = field.parent().attr("resultId");
			}else{
				var _resultId = field.attr("resultId");
			}
			if(_tochange==undefined){
				_tochange=false;
			}
			var infoMode = {
				titleId	: field.data('titleId'),
				tochange:_tochange,
				resultId:_resultId,
				type : type
			};
			if(type==2 || type==3 || type==4)
			{
				infoMode.value = field.val()
			}
			else if(type==1 || type==19 )
			{	
				infoMode.remark1 = field.val();
			}
			else if( type==20 )
			{	
				infoMode.remark1 = field.val();
				//infoMode.remark2 = field.parent().parent().children($('select option:selected')).text();
				var id  = infoMode.titleId
				var options=$("#"+id+"_select option:selected")
				var name = options.text();
				var value =options.val();
				infoMode.remark2 = name+"p"+value;
			}
			else if(type==8)
			{
				var str=field.val();
				var str=str.replace(/\n|\r\n/g,"<br>")
				var str=str.replace(/\s/g,"&nbsp;");
				infoMode.remark1 = str;
			}
			infoModeList.push(infoMode);
		});
		data.infoModeList = infoModeList;
		console.log(data);
		//表格
		var infoTableModelList = new Array();
		$.each(sec.find("table.editable"),function(){
			$.each($(this).find('tr:gt(0)'),function(){
				var row = $(this).data();
				if(row.id=="")
				{
					row.id=null;
				}
				infoTableModelList.push($(this).data());
			});
		});
	
		data.infoTableModelList = infoTableModelList;
		data.deletedRowIds = deletedRowIds;
//估值表格显示隐藏
		$.each($('table.editable'),function(){
			var table_id = $(this).attr('data-title-id');
			var noedi_table = $('table[data-title-id='+table_id+']')
			if($(this).find('tr').length<=1){
				if(noedi_table.parents('dl').find('dd').length<= 2){
					$('table[data-title-id='+table_id+']').parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
				}
				noedi_table.hide();
			}
			else{
				noedi_table.show();
				noedi_table.parents('dl').find('.no_enter').remove();
				
			}
		})

		//上传图片相关
		var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
		var key = keyJSON["b_"+id_code];
		var deleteids = deleteJSON["delete_"+id_code];
		var params = {};
		params.projectId =  projectInfo.id;
		params.fileReidsKey = key;
		params.deleteids = deleteids;
		
		if(!$("#b_"+id_code).validate().form())
		{
			return;
		}
		
		/* sendPostRequestByJsonObj(
			platformUrl.saveOrUpdateInfo , 
			data,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					updateInforTime(projectInfo.id,"financingTime");
					layer.msg('保存成功');
					$(".h#a_"+id_code).css("background","#fff");
					$(".bj_hui_on").hide();
					deletedRowIds = new Array();
					var parent = $(sec).parent();
					var id = parent.data('sectionId');
					$(btn).next().click();
					refreshSection(id);
					btn_disable(0);
				    toggle_btn($('.anchor_btn span'),0,save_this);

				} else {

				}
		}) */ 
		$("body").showLoading();
		sendPostRequestByJsonObjNoCache(sendFileUrl,params,true,function(dataParam){
			//进行上传
			var result = dataParam.result.status;
			if(result == "OK"){
				sendPostRequestByJsonObjNoCache(
						platformUrl.saveOrUpdateInfo , 
						data,
						true,
						function(data) {
							var result = data.result.status;
							if (result == 'OK') {
								updateInforTime(projectInfo.id,"NO9");
								layer.msg('保存成功');
								$(".h#a_"+id_code).css("background","#fff");
								$(".bj_hui_on").hide();
								deletedRowIds = new Array();
								var parent = $(sec).parent();
								var id = parent.data('sectionId');
								$(btn).next().click();
								$(".loading-indicator-overlay").remove();
								$(".loading-indicator").remove();
								refreshSection(id);
								picData(projectInfo.id);
								btn_disable(0);
							    toggle_btn($('.anchor_btn span'),0,save_this);
							     $('table').each(function(){
                                    resizetable($(this))
                                    if($(this).find('tr').length<=1){
                                        $(this).hide();
                                        if($(this).parents('dl').find('dd:gt(0)').length<=0){
                                            $(this).parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
                                        }
                                    }
                                    else{
                                        $(this).show();
                                    }
                                })
							} else {
								layer.msg("操作失败!");
							}
					});
			}else{
				layer.msg("操作失败!");
			}
			
		});

	});
/* function refreshSection(id)
{
	var sec = $(".section[data-section-id='"+id+"']");
	sec.showResults(true);
} */
function getDetailUrl(code)
{
	if(code == 'equity-structure')
	{
		return '<%=path%>/html/funcing_add_gd.html';
	}
	else if(code == 'investor-situation')
	{
		return '<%=path%>/html/funcing_add_tz.html';
	}
	else if(code =='operation-indices')
	{
		return '<%=path%>/html/fincing_add_yx.html';
	}
	else if(code == 'valuation-reference')
	{
		return '<%=path%>/html/fincing_add_tl.html';
	}
	else if(code == 'financing-milestone')
	{
		return '<%=path%>/html/fincing_add_jd.html';
	}else if(code == 'finance-history')
	{
		return '<%=path%>/html/finace_history.jsp';
	}
	
	return "";
}
/* function editRow(ele)	
{
	var code = $(ele).closest('table').data('code');
	var row = $(ele).closest('tr');
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			var title = $("#pop-title");
			title.text(title.text().replace('添加','编辑'));
			$.each($("#detail-form").find("input, select, textarea"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				ele.val(row.data(name));
			});
			//文本框剩余字数
			$.each($(".team_textarea"),function(){
				var len=$(this).val().length;
				var initNum=$(this).siblings('.num_tj').find("span").text();
				$(this).siblings('.num_tj').find("span").text(initNum-len);
			})
			$("#detail-form input[name='index']").val(row.index());
			$("#save-detail-btn").click(function(){
				saveForm($("#detail-form"));
			});
		}//模版反回成功执行	
	});
}
var deletedRowIds = new Array();
function delRow(ele)
{
	layer.confirm('是否删除?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero) {
		var tr = $(ele).closest('tr');
		var id = tr.data('id');
		
		if(typeof id != 'undefined' && id>0)
		{
			deletedRowIds.push(id);
		}
		tr.remove();
		check_table();
		check_table_tr_edit();
		$(".layui-layer-close1").click();
		//$(".layui-layer-btn1").click();
	}, function(index) {
	});
	
}
function addRow(ele)
{
	var code = $(ele).prev().data('code');
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			$("#detail-form input[name='projectId']").val(projectInfo.id);
			$("#detail-form input[name='titleId']").val($(ele).prev().data('titleId'));
			$("#save-detail-btn").click(function(){
				saveForm($("#detail-form"));
				check_table();
				check_table_tr_edit();
			});
		}//模版反回成功执行	
	});
}
function saveForm(form)
{
	if($(form).validate().form())
	{
		var data = $(form).serializeObject();
		saveRow(data);
	}
}

/**
 * 保存至到tr标签data属性
 */
 /*
function saveRow(data)
{
	data = JSON.parse(data);
	var titleId = data.titleId;
	var index = data.index;
	if(typeof index == 'undefined' || index == null || index == '')
	{
		var tr = buildRow(data,true,titleId);
		$('table[data-title-id="'+titleId+'"].editable').append(tr);
	}
	else
	{
		var tr = $('table[data-title-id="'+titleId+'"].editable').find('tr:eq('+index+')');
		for(var key in data)
		{
			if(key.indexOf('field')>-1)
			{
				tr.data(key,data[key]);
				tr.find('td[data-field-name="'+key+'"]').text(data[key]);
			}
		}
	}
	resizetable($('table[data-title-id="'+titleId+'"].editable'))
	$("a[data-close='close']").click();
} */

function getTableRowLimit(code)
{
	if(code == 'investor-situation' || code =='operation-indices')
	{
		return 20;
	}
	return 10;
}


function toBachUpload(fileurl,sendFileUrl,fieInputId,selectBtnId,submitBtnId,containerId,fileListId,paramsFunction,deliver_form,callBackFun,id_code) {
	var params = {};
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : selectBtnId, // you can pass an id...
		//container: containerId, // ... or DOM Element itself
		multi_selection:false,
		url : fileurl,
		rename : true,
		unique_names:true,
		filters : {
			max_file_size : '2mb',
			mime_types: [
					{title : "Image files", extensions : "jpg,png,gif,bmp"}
			]
		},
		init: {
			PostInit: function(up) {
				params = paramsFunction;
			},
			BeforeUpload:function(up,file){
				var name = file.name.replace(/\s+/g,"");
				params["fileName"] = name;
			},
			FileUploaded:function(up,file,rtn){
             }, 
			FilesAdded: function(up, files) {
				params = paramsFunction;
				var imglength = $('#'+fieInputId).children("li").length;
				
				if(imglength == 4){
					//layer.msg("不能超过5张照片!");
					var typeid = fieInputId.replace("edit-","");
					$("#h_imgs_add_"+typeid).hide();
					//return;
				}
				console.log(uploader.browse_button);
				for(var i = 0, len = files.length; i<len; i++){
					var file_name = files[i].name; //文件名
					//构造html来更新UI
					!function(i){
						 previewImage(files[i], function (imgsrc) {
		                                $('#'+fieInputId).html($('#'+fieInputId).html() +
		                                    '<li class="pic_list fl" id="' + files[i].id + '">'
		                                    +'<a href="javascript:;" class="h_img_del"  code="'+"delete_"+id_code+'" data-val=' + files[i].id +
							              ' data-title-val=' + fieInputId.replace("edit-","") +
							              '></a>' +'<img src="' + imgsrc + '" name="' + files[i].name + '" /></li>');
		                            })
				    }(i);
				    params.newFileName = files[i].id;
				    up.settings.multipart_params = params;
					uploader.start();
				}
				
			},
			UploadProgress: function(up, file) {
			},
			UploadComplete: function(up, files){//所有都上传完成
				
		    },
			Error: function(up, err) {
			}
			
		}
	});
	uploader.init();
}
  $(document).on('click', '.pic_list a.h_img_del', function () {
      $(this).parent().remove();
      var _this = $(this);
      var toremove = '';
      var id = $(this).attr("data-val");
      var deleteCode = $(this).attr("code");
      if(deleteJSON[deleteCode]){
          deleteJSON[deleteCode] = deleteJSON[deleteCode] +","+id;
      }else{
          deleteJSON[deleteCode] = id;
      }
  	  var params = {};
	  params.projectId =  projectInfo.id;
	  params.fileReidsKey = key;
	  params.newFileName = id;
      //文件id
      sendPostRequestByJsonObj(Constants.sopEndpointURL+'galaxy/informationFile/deleteRedisFile',params,function(data){
			//进行上传
			var result = data.status;
			if(result == "OK"){
			   //删除
			   var titleId = _this.attr("data-title-val");
	           var imglength = $('#edit-'+titleId).children("li").length;
	           if(imglength == 4){
	             $("#h_imgs_add_"+titleId).show();
	           }
			}else{
				layer.msg("删除失败!");
			}
	  });
      
   
  });
  
function previewImage(file,callback){//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
if(!file || !/image\//.test(file.type)) return; //确保文件是图片
if(file.type=='image/gif' || file.type=='image/bmp'){//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
	var fr = new mOxie.FileReader();
	fr.onload = function(){
		callback(fr.result);
		fr.destroy();
		fr = null;
	}
	fr.readAsDataURL(file.getSource());
}else{
	var preloader = new mOxie.Image();
	preloader.onload = function() {
		preloader.downsize( 300, 300 );//先压缩一下要预览的图片,宽300，高300
		var imgsrc = preloader.type=='image/jpeg' ? preloader.getAsDataURL('image/jpeg',80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
		callback && callback(imgsrc); //callback传入的参数为预览图片的url
		preloader.destroy();
		preloader = null;
	};
	preloader.load( file.getSource() );
}	

}

</script>
</body>


</html>
