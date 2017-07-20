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
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path %>/js/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
</head>
<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

<body>
<!-- <ul class="h_navbar clearfix">
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基础<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
           
                </ul> -->
   <!--隐藏-->
<div class="bj_hui_on"></div>
<jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include> 
                 <div class="tabtxt" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>

<script type="text/javascript">
var key = Date.parse(new Date());
var keyJSON={};
var deleteJSON={};

getData();
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
	    key = Date.parse(new Date());
		var section = $(this).parents('.section');
		var id_code = $(this).attr('attr-id');
		//
		var str ="";
		if($(this).parents(".h_btnbox").siblings(".h_title").find("span").is(":visible")){
			str =" <span style='color:#ff8181;display:inline'>（如果该项目涉及此项内容，请进行填写，反之可略过）</span>";
		}else{
			str ="";
		}
		//
		console.log(key);
		
		keyJSON["b_"+id_code]=key;
		console.log(keyJSON);
		var sec = $(this).closest('.section');
		var sTop=$(window).scrollTop();
		event.stopPropagation();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					sec.showResults();
					bindChangeType13();
					$(".h#a_"+id_code).css("background","#fafafa");
					$("#"+id_code).hide();
					$(".bj_hui_on").show();
					validate();
					//编辑显示隐藏按钮不可用
					btn_disable(1);
					//isMust("#b_"+id_code);	
					$("#b_"+id_code).validate();
					section.find(".h_title span").remove();
					section.find(".h_title").append(str);
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
					edit_bsaicfun();
				}else{
					
				}
		}) 
		
	});
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var _this = $(this).parents(".radius");
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		$(".h#a_"+id_code).css("background","#fff");
		$(".bj_hui_on").hide();
		dtWidth();
		btn_disable(0);
		event.stopPropagation();
		//mustData(_this,1);
		toggle_btn($('.anchor_btn span'),0,_this);
	});
 	//通用保存
	$('div').delegate(".h_save_btn","click",function(event){
		var save_this = $(this).parents(".radius");
		var id_code = $(this).attr('attr-save');
		event.stopPropagation();
		var sec = $(this).closest('form');
		var fields = sec.find("input[type='text'],input:checked,textarea,li[class='check_label active'],li.active,option:selected");
		var dt_type_3 = $("#b_" + id_code).find("dt[data-type='3'],dt[data-type='13']");
		var data = {
			projectId : projectInfo.id
		};
		if(!$("#b_"+id_code).validate().form())
		{
			return;
		}
		//多选不选择的时候：
		var deletedResultTids = new Array();
		$.each(dt_type_3, function() {
			var _this = $(this);
			var active = _this.parent().find('dd .active');
			
			if(!(active && active.length > 0)){
				var tid = _this.data('titleId');
				deletedResultTids.push(tid);
			}
		});
		data.deletedResultTids = deletedResultTids;
		var infoModeList = new Array();
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var _tochange =field.parents("dd").prev().attr("tochange");
			var _resultId = field.attr("resultId");
			if(_resultId==undefined){
				_resultId=null;
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
			if(type==2 || type==3 || type==4 || type==14)
			{
				infoMode.value = field.val();
			}		
			else if(type==1)
			{	
				infoMode.remark1 = field.val();
			}
			else if(type==8)
			{
				var str=field.val();
				var str=str.replace(/\n|\r\n/g,"<br>")
				var str=str.replace(/\s/g,"&nbsp;");
				infoMode.remark1 = str;
			}
			 else if(type==13)
	            {
	                    infoMode.value = field.data('id');
	                    var field_v = field.data('id');
	                    var last_id = field.closest('ul').find('li.check_label:last').attr('data-id');
	                    var dt = field.closest('dt[data-type="13"]');
	                    if ( field_v == last_id)
	                    {
	                        infoMode.remark1 = field.closest('.h_edit_txt').find('input:last').val();
	                    }
	                    else
	                    {
	                        infoMode.remark1 = '' ;
	                    }
	               
	            }
			infoModeList.push(infoMode);
		});
		data.infoModeList = infoModeList;
		var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
		
		
		var key = keyJSON["b_"+id_code];
		var deleteids = deleteJSON["delete_"+id_code];
		
		var params = {};
		params.projectId =  projectInfo.id;
		params.fileReidsKey = key;
		params.deleteids = deleteids;
		$("body").showLoading();
		console.log("保存的 data");
		console.log(data);
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
								updateInforTime(projectInfo.id,"operationDataTime");
								layer.msg('保存成功');
								//tabInfoChange('3');
								$('#'+id_code).show();
								$('#b_'+id_code).remove();
								$(".bj_hui_on").hide();
								//右侧按钮显示隐藏
								btn_disable(0);
								$(".h#a_"+id_code).css("background","#fff");
								$(".loading-indicator-overlay").remove();
								$(".loading-indicator").remove();
								dtWidth();
								var pid=$('#a_'+id_code).attr("data-section-id");
								$('#a_'+id_code).find('dd[data-type="3"]').hide();
								setDate(pid,true);	
								picData(projectInfo.id);
								toggle_btn($('.anchor_btn span'),0,save_this);
								
							} else {
								layer.msg("操作失败!");
							}
					});
			}else{
				layer.msg("操作失败!");
			}
			
		}); 
	
		
	}); 
	
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
function getData(){
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO4", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				$("#page_list").tmpl(entity).appendTo('#page_all');
				picData(projectInfo.id,1);
				$(".section").each(function(){
					$(this).showResults(true);
				}); 
				mustData(projectInfo.id,0);
				fun_click();
				
			} else {

			}
		})
}
function bindChangeType13(){
    var dts = $("dt[data-type='13']");
    $.each(dts, function (i,n) {
        var dl = $(this).parent();
        var lis = dl.find('li.check_label');
        var last_id = dl.find('li.check_label:last').attr('data-id');
        var inputText = dl.find('input[type="text"]:last');
		if(dl.find('li.check_label:last').hasClass("active")){
			 inputText.attr('required' , true);
		}
        $.each(lis, function ( i ,n )
        {
        	$(this).click(function(){
        		if ( $(this).attr('data-id') == last_id ){
        			if(inputText.attr("disabled")=="disabled"){
        				 inputText.attr('disabled',false);
                         inputText.attr('required' , true);
        			}else{
        				inputText.attr('disabled',true);
                        inputText.attr('required' , false);
        			}
        		}   
        		
        	})
        });
    });
}
</script>

</body>
</html>
