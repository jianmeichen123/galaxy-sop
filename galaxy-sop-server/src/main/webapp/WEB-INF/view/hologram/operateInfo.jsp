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
<title>项目详情</title>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
</head>
<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

<body>
<ul class="h_navbar clearfix">
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基本<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
           
                </ul>
                 <div class="tabtxt" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>
<!--点击编辑例子 -->
<script id="ifelse" type="text/x-jquery-tmpl">
<form id="b_\${code}">
<div class="h_edit section" >
	<div class="h_btnbox">
		<span class="h_save_btn" attr-save="\${code}">保存</span><span class="h_cancel_btn"
			data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}
		
	{{if sign=="3"}}
		<div>\${name}</div>
		{{each(i,childList) childList}}
		<div class="mb_16">
	   <dl class="h_edit_txt clearfix">
		<dt data-type="\${type}"  data-title-id="\${id}" data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
		{{if type=="1"}}
		<dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>

		{{else type=="2"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-title-id="\${titleId}" data-type="\${type}" name="\${titleId}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="3"}}
		<dd class="fl_none">
		<ul class="h_edit_checkbox clearfix" data-type="\${type}">
			{{each(i,valueList) valueList}}
			<li class="check_label" data-value="\${value}" data-title-id="\${titleId}" value="\${id}" data-id="\${id}" data-code="\${code}" data-type="\${type}">\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="4"}}
		{{each(i,valueList) valueList}}
		<dd>
		  <select name="" id="">
			<option value="">\${name}</option>
		  </select>
		</dd>
		{{/each}}

		{{else type=="5"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">500</label>/500
			</p>
		</dd>


		{{else type=="6"}}
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}

		{{else type=="7"}}
			<dd class="fl_none clearfix">
			 <ul class="h_imgs" id="edit-\${id}">

			 </ul>
			 <ul class="h_imgs">
				<li class="h_imgs_add"><input type="file" file-title-id="\${id}" id="selected_file_\${id}"></li>
			</ul>
			</dd>
			<dd class="fl_none red">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>
		{{else type=="8"}}
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">0</label>/2000
			</p>
		</dd>

		{{else type=="10"}}
		<dd class="fl_none">
			< data-title-id="\{id}" class="editable"></table>
			<span class="pubbtn bluebtn margin_btn" onclick="addRow(this)">新增</span>
		</dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>
		
		{{else type=="12"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>


		{{else type=="13"}}
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" placeholder="\${placeholder}"/></dd>
		
		{{else type=="14"}}
		<select data-id="\${id}">
		{{each(i,valueList) valueList}}
		<option data-value="\${value}" data-type="\${type}" data-id="\${id}" data-title-id="\${titleId}" value="\${id}" data-code="\${code}">\${name}</option>
		{{/each}}
		</select>


		{{/if}}
	  </dl>
	</div>

		{{/each}}
	{{else}}
	<div class="mb_16">
	   <dl class="h_edit_txt clearfix">		
		{{if type=="1"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>

		{{else type=="2"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" name="\${titleId}" value="\${id}" data-title-id="\${titleId}" data-type="\${type}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="3"}}
		<dt class="fl_none" data-type="\${type}"  data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
		<dd class="fl_none">
		<ul class="h_edit_checkbox clearfix" data-type="\${type}">
			{{each(i,valueList) valueList}}
			<li class="check_label" data-value="\${value}" data-title-id="\${titleId}" value="\${id}" data-id="\${id}" data-code="\${code}" data-type="\${type}">\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="4"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd>
		  <select name="" id="">
			<option value="">\${name}</option>
		  </select>
		</dd>
		{{/each}}

		{{else type=="5"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" data-value="\${value}" data-type="\${type}" placeholder="\${placeholder}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${titleId}" data-type="\${type}" data-parentId="\${parentId}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">500</label>/500
			</p>
		</dd>

		{{else type=="6"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}

		{{else type=="7"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
        <dd class="fl_none clearfix">
        <ul class="h_imgs mgedit"  id="edit-\${id}"></ul>
        <ul class="h_imgs" id="edit-\${id}">
        <li class="h_imgs_add"><input type="file" file-title-id="\${id}" id="selected_file_\${id}"></li>
        </ul>
        </dd>
        <dd class="fl_none red">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>

		{{else type=="8"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">0</label>/2000
			</p>
		</dd>

		{{else type=="10"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd class="fl_none">
			<table data-title-id="\${id}"  class="editable">

			</table>
			<span class="pubbtn bluebtn margin_btn" onclick="addRow(this)">新增</span>
		  </dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>


		{{else type=="13"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" placeholder="\${placeholder}"/></dd>
		
		{{else type=="14"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<select data-id="\${id}">
		{{each(i,valueList) valueList}}
		<option data-value="\${value}" data-type="\${type}" data-id="\${id}" data-title-id="\${titleId}" value="\${id}" data-code="\${code}">\${name}</option>
		{{/each}}
		</select>


		{{/if}}
	  </dl>
	</div>


	{{/if}}

	{{/each}}
	<div class="h_edit_btnbox clearfix">
	  <span class="pubbtn bluebtn h_save_btn fl" data-on="save" attr-save="\${code}">保存</span>
	  <span class="pubbtn fffbtn fl h_cancel_btn" data-name="basic" data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>

</div>
</form>
</script>



<!--页面例子 -->
<script id="page_list" type="text/x-jquery-tmpl">
{{each(i,childList) childList}}
<div class="h radius section" id="a_\${code}" data-section-id="\${id}">
  <div class="h_look h_team_look clearfix" id="\${code}">
	<c:if test="${isEditable}">
	<div class="h_btnbox"><span class="h_edit_btn" attr-id="\${code}">编辑</span></div>
	</c:if>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}                    
	{{if sign=="3"}}
	<div>\${name}</div>
		{{each(i,childList) childList}}
			<div class="mb_24 clearfix">
	  <dl class="clearfix">
		<dt data-type="\${type}" data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>

		{{if type=="1"}} 
         <dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="5"}}                             
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		<dd>备注</dd>

		{{else type=="2"}}
		<dd class="field" data-value="\${value}" data-title-id="\${id}" data-code="\${code}">未选择</dd>

		{{else type=="3"}}
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-type="3" value="\${id}" data-title-id="\${id}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="6"}}
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="7"}}
		<dd class="fl_none mglook" id="look-\${id}" ata-value="\${value}" data-id="\${id}" data-code="\${code}">
			</dd>

		{{else type=="8"}}
		<dd class="fl_none field" data-title-id="\${id}">未填写</dd>

		{{else type=="4"}}
		{{each(i,valueList) valueList}}
		<dd>未选择</dd>
		{{/each}}
		
		{{else type=="10"}}
		<dd><table data-title-id="\${id}"></table></dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="13"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="14"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{/if}}                      
		</dl>		
	</div>
		{{/each}}

	{{else}}
	<div class="mb_24 clearfix">
	  <dl class="clearfix">
		{{if type=="1"}}
		<dt  data-type="\${type}">\${name}</dt>  
         <dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="5"}}       
		<dt  data-type="\${type}">\${name}</dt>                 
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		<dd>备注</dd>

		{{else type=="2"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-value="\${value}" data-title-id="\${id}" data-code="\${code}">未选择</dd>

		{{else type=="3"}}
		<dt  data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		 <dd data-value="\${value}" data-type="3" value="\${id}" data-title-id="\${id}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="6"}}
		<dt  data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="7"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd class="fl_none mglook" id="look-\${id}" ata-value="\${value}" data-id="\${id}" data-code="\${code}">
			</dd>

		{{else type=="8"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd class="fl_none field" data-title-id="\${id}">未填写</dd>

		{{else type=="4"}}
		<dt data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd>未选择</dd>
		{{/each}}

		{{else type=="10"}}
		<dt class="fl_none" data-type="\${type}">\${name}</dt>
		<dd><table data-title-id="\${id}"></table></dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="13"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="14"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{/if}}                      
		</dl>		
	</div>

	{{/if}}
	{{/each}}
  </div>
</div>
{{/each}}
</script>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script type="text/javascript">
var key = Date.parse(new Date());
var deleteids = "";
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO4", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				console.log(entity);
				$("#page_list").tmpl(entity).appendTo('#page_all');
				$(".section").each(function(){
					$(this).showResults(true);
				});
			} else {

			}
		})
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');
		event.stopPropagation();
		$("#"+id_code).hide();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					console.log(entity);
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					sec.showResults();
					validate();
					$("#b_"+id_code).validate();
					var files = $("#"+id_code).nextAll().find("input[type='file']");
					var selectids = [];
					
					for(var i = 0;i < files.length; i++) {
						  var select_id = files.eq(i).attr("id");
						  var title_id = $("#"+select_id).attr("file-title-id");
							
							var params = {};
							params.fileReidsKey = key;
							params.projectId =  projectInfo.id;
							params.titleId = title_id;
							toBachUpload(Constants.sopEndpointURL+'galaxy/informationFile/sendInformationByRedis',Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile','edit-'+title_id,select_id,"h_save_btn","",null,params,null,null);
							
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
								              +'<a href="#" class="h_img_del" data-val=' + files[i].id +
								              '></a>' +'<img src="' + files[i].fileUrl + '" name="' + files[i].fileName + '" /></li>';
										}
									}
									$('#'+'edit-'+title_id).html(html);
								} else {

								}
				          }); 
							
							
					}
					 
				}else{
					
				}
					
				
				 
		}) 
	});
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		$(".tip-yellowsimple").hide();
		event.stopPropagation();
	});
	
 	//通用保存
	$('div').delegate(".h_save_btn","click",function(event){
		var id_code = $(this).attr('attr-save');
		event.stopPropagation();
		var sec = $(this).closest('form');
		var fields = sec.find("input[type='text'],input:checked,textarea,li.active,option:selected");
		var data = {
			projectId : projectInfo.id
		};
		
		var infoModeList = new Array();
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var infoMode = {
				titleId	: field.data('titleId'),
				type : type
			};
			if(type==2 || type==3 || type==4 || type==14)
			{
				console.log(field.val());
				infoMode.value = field.val()
			}		
			else if(type==1 || type==8)
			{	
				infoMode.remark1 = field.val().replace(/\n|\r\n/g,"<br>");
			}
			infoModeList.push(infoMode);
		});
		data.infoModeList = infoModeList;
		var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
		
		var params = {};
		params.projectId =  projectInfo.id;
		params.fileReidsKey = key;
		params.deleteids = deleteids;
		if(!$("#b_"+id_code).validate().form())
		{
			return;
		}
		
		if(beforeSubmit()){
			sendPostRequestByJsonObj(
					platformUrl.saveOrUpdateInfo , 
					data,
					function(data) {
						var result = data.result.status;
						if (result == 'OK') {
							layer.msg('保存成功');
							$('#'+id_code).show();
							$('#b_'+id_code).remove();
							sendPostRequestByJsonObj(sendFileUrl,params,function(data){
								//进行上传
								var result = data.result.status;
								if(result == "OK"){
									tabInfoChange('3');
								}else{
								}
								
							});
							
						} else {

						}
				});
		}
		
		
		
		
		
	}); 
	
	function toBachUpload(fileurl,sendFileUrl,fieInputId,selectBtnId,submitBtnId,containerId,fileListId,paramsFunction,deliver_form,callBackFun) {
		var params = {};
		var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4,jpg',
			browse_button : selectBtnId, // you can pass an id...
			//container: containerId, // ... or DOM Element itself
			multi_selection:false,
			url : fileurl,
			rename : true,
			unique_names:true,
			filters : {
				max_file_size : '25mb'
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
					
					console.log(uploader.browse_button);
					for(var i = 0, len = files.length; i<len; i++){
						var file_name = files[i].name; //文件名
						//构造html来更新UI
						!function(i){
							 previewImage(files[i], function (imgsrc) {
			                                $('#'+fieInputId).html($('#'+fieInputId).html() +
			                                    '<li class="pic_list fl" id="' + files[i].id + '">'
			                                    +'<a href="#" class="h_img_del" data-val=' + files[i].id +
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
          var toremove = '';
          var id = $(this).attr("data-val");
          
          deleteids += ","+id;
          
      	  var params = {};
		  params.projectId =  projectInfo.id;
		  params.fileReidsKey = key;
		  params.newFileName = id;
          //文件id
          sendPostRequestByJsonObj(Constants.sopEndpointURL+'galaxy/informationFile/deleteRedisFile',params,function(data){
				//进行上传
				var result = data.status;
				if(result == "OK"){
					
				}else{
					layer.msg("删除失败!");
				}
		  });
          
        /*   for (var i in uploader.files) {
              if (uploader.files[i].id === id) {
                  toremove = i;
              }
          }
          uploader.files.splice(toremove, 1); */
      });
	  
function previewImage(file,callback){//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
	if(!file || !/image\//.test(file.type)) return; //确保文件是图片
	if(file.type=='image/gif'){//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
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
var fileids = $(".mglook");
var infoFileids = "";
var data={};
for(var i = 0;i < fileids.length; i++) {
	  infoFileids += ","+fileids.eq(i).attr("id").replace("look-","");
}
data.projectId = projectInfo.id;
data.infoFileids = infoFileids;
sendPostRequestByJsonObj(
			Constants.sopEndpointURL+'galaxy/informationFile/getFileByProjectByType' , 
			data,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var files = data.entity.commonFileList;
					if(files != null && files != ""){
						$.each(files, function (key, value) { 
							var fl = value;
							var html="";
							for(var i = 0;i < fl.length; i++){
								html +='<img src="'+fl[i].fileUrl+'" alt="">';
							}
							$('#'+"look-"+key).html(html);
							
						});
					}
					
				} else {

				}
});
</script>
</body>
</html>
