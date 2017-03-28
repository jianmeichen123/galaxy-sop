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
<jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include> 
                 <div class="tabtxt" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>

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
		$(".h#a_"+id_code).css("background","#fafafa");
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
					//文本域剩余字符数
					for(var i=0;i<$(".textarea_h").length;i++){
						var len=$(".textarea_h").eq(i).val().length;
						var initNum=$(".num_tj").eq(i).find("label").text();
						$(".num_tj").eq(i).find("label").text(initNum-len);
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
		$(".h#a_"+id_code).css("background","#fff");
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
			else if(type==1)
			{	
				infoMode.remark1 = field.val();
			}
			else if(type==8)
			{
				var str=field.val();
				var str=str.replace(/\n|\r\n/g,"<br>")
				var str=str.replace(/\s+/g,"&nbsp;&nbsp;&nbsp;&nbsp;");
				infoMode.remark1 = str;
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
							$(".h#a_"+id_code).css("background","#fff");
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
					var imglength = $('#'+fieInputId).children("li").length;
					if(imglength >= 5){
						layer.msg("不能超过5张照片!");
						return;
					}
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
