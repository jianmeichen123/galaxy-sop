var key = Date.parse(new Date());
var keyJSON={};
var deleteJSON={};
//通用编辑显示
function edit_display(_data){
	 key = Date.parse(new Date());
	 //窗口滚动高度
	 var sTop=$(window).scrollTop();
	// 例如NO3_3 请求数据接口的字段
	var id_code = _data.attr('attr-id');
	//整个保存区快
	var section = _data.closest('.section');
	//是否显示提示语
	var str ="";
	if(_data.parents(".h_btnbox").siblings(".h_title").find("span").is(":visible")){
		str =" <span style='color:#ff8181;display:inline'>（如果该项目涉及此项内容，请进行填写，反之可略过）</span>";
	}else{
		str ="";
	}
	keyJSON["b_"+id_code]=key;
	event.stopPropagation();
	 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				//请求数据成功
				
				var entity = data.entity;
				//模板渲染
				$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
				//填充数据
				section.showResults();
				
				$(".h#a_"+id_code).css("background","#fafafa");
				//背景遮罩
				$(".bj_hui_on").show();
				section.find(".h_title span").remove();
				section.find(".h_title").append(str);
				$("#"+id_code).hide();
				
				//通用验证方法
				validate();
				$("#b_"+id_code).validate();
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
				//通用方法
				edit_bsaicfun();
				
				
				
				
				//调整表格
				$("table").css({"width":"90%","table-layout":"fixed"});
				$(".h_edit .sign_title").css("margin-bottom","20px");
				//锚点
				btn_disable(1);
				
				
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
			}else{
				//请求数据失败
			}
	 })
	 $('body,html').scrollTop(sTop);  //定位
	//编辑表格显示隐藏
	 check_table();
}