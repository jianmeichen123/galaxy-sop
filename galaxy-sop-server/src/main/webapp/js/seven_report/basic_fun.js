var key = Date.parse(new Date());
var keyJSON={};
var deleteJSON={};
var totalMoney;
var remainMoney;
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
//通用取消编辑
$('div').delegate(".h_cancel_btn","click",function(event){
	event.stopPropagation();
	var _this = $(this).parents(".radius");
	var id_code = $(this).attr('attr-hide');
	var session_id = $(this).attr('attr-session')
	$('#'+id_code).show();
	$('#b_'+id_code).remove();
	$(".bj_hui_on").hide();
	btn_disable(0);
	$(".h#a_"+id_code).css("background","#fff");
	deletedRowIds = new Array();
	toggle_btn($('.anchor_btn span'),0,_this);
	//团队
	dtWidth();
	//判断团队-股权合理性
    if (session_id=='1324')
    {
        deletedRowIdsGq = new Array();
    }
});

//通用编辑显示
var reportType="";
$('div').delegate(".h_edit_btn","click",function(event){
	var key = Date.parse(new Date());
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
	var type=id_code.split("NO")[0];
	switch(type){
	   case "D":
		  reportType="2";
		  break;
	   case "O":
			  reportType="7";
			  break;
	   case "G":
			  reportType="5";
			  break;
	   case "P":
			  reportType="3";
			  break;
	   default:
		   reportType="";	  
	}
	keyJSON["b_"+id_code]=key;
	var sec = $(this).closest('.section');
	var sTop=$(window).scrollTop();
	event.stopPropagation();
	 sendGetRequest(platformUrl.queryAllTitleValues + id_code+"?reportType="+reportType, null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
				if(id_code=="DNO5_1" || id_code=="GNO5_1" ){   //竞争俩字
					$("#b_"+id_code).closest(".section").find(".h_title").text("竞争");
				}
				sec.showResults();
				bindChange();
				bindChangeType13();
				$(".h#a_"+id_code).css("background","#fafafa");
				$("#"+id_code).hide();
				$(".bj_hui_on").show();
				validate();
				//调整表格
				$("table").css({"width":"90%","table-layout":"fixed"});
				$(".h_edit .sign_title").css("margin-bottom","20px");
				//团队核心亮点与团队核心能力联动修改其他值
				$("input").on("input",function(){
					var titleId=$(this).parent("li").prev("li").attr("data-title-id");
					var val=$(this).val();
					if(titleId=="1364"){
						var inputText=$(this).closest(".mb_16").siblings().find("dt[data-title-id='"+titleId+"']").siblings("dd").find("input[type='text']");
						$(inputText).val(val);
					}
				})
				//编辑显示隐藏按钮不可用
				btn_disable(1);
				setReqiured();
				//isMust("#b_"+id_code);	
				$("#b_"+id_code).validate();
				$(".bj_hui_on").show();
				check_table_tr_edit();
				section.find(".h_title span").remove();
				section.find(".h_title").append(str);
				
				//运营报告中【融资估值中分期添加按钮隐藏】
				if(reportType=="7" && id_code=="ONO9_2"){
					$("#add_row").hide();
				}
				
				//计算项目估值
				if(reportType=="3"){
					$.each($("input[data-type='19']"),function(){
						var valRuleFormula=$(this).attr("data-valruleformula");
						if(valRuleFormula){
							var valRule=valRuleFormula.split("=");
							var valRule1="";
							if(null!=valRule[1]){
								valRule1=valRule[1].split("/");
							}
						
							var result=valRule[0];
							var parent=valRule1[0];
							var children=valRule1[1];
							if(reportType=="3"){
								var input='<input type="text" class="hidden" data-code="PNO1_1_2" data-title-id="'+result+'" data-type="19"/>';  //添加隐藏域
								$("input[data-valruleformula='"+valRuleFormula+"']").after(input);
							}
						}
						function calculationValuations(){  //编辑股权占比
							var projectParent = $("dd[data-title-id='"+parent+"']").text();
							var projectChildren = $("input[data-title-id='"+children+"']").val();
							if(projectParent > 0 && projectChildren > 0){
								console.log(projectParent * (100/projectChildren))
								return projectParent * (100/projectChildren);
							}else{
								return '';
							}
							
						}
						function calculationValuationsParent(){  //编辑投资金额
							var projectParent = $("input[data-title-id='"+parent+"']").val();
							var projectChildren = $("dd[data-title-id='"+children+"']").text();
							if(projectParent > 0 && projectChildren > 0){
								console.log(projectParent * (100/projectChildren))
								return projectParent * (100/projectChildren);
							}else{
								return '';
							}
							
						}
					  if(reportType=="3"){
							$("input[data-title-id='"+result+"']").val(calculationValuations());
						}
					   $("div").delegate("input[data-title-id='"+parent+"']","blur",function(){
							var valuations = calculationValuationsParent();
							if(valuations != null){
								$("input[data-title-id='"+result+"']").val(valuations);
								$("input[data-title-id='"+result+"']").parents("dd").prev().attr("tochange",true);
								$("input[type='hidden'].money").val(valuations);
								
								
							}
						});
						$("div").delegate("input[data-title-id='"+children+"']","blur",function(){
							var valuations = calculationValuations();
							if(valuations != null){
								$("input[data-title-id='"+result+"']").val(valuations);
								$("input[data-title-id='"+result+"']").parents("dd").prev().attr("tochange",true);
								$("input[type='hidden'].money").val(valuations);
							}
						})
					})
				}else{
					$.each($("input[data-type='19']"),function(){
						var valRuleFormula=$(this).attr("data-valruleformula");
						if(valRuleFormula){
							var valRule=valRuleFormula.split("=");
							var valRule1="";
							if(null!=valRule[1]){
								valRule1=valRule[1].split("/");
							}
						
							var result=valRule[0];
							var parent=valRule1[0];
							var children=valRule1[1];
						}
						function calculationValuations(){
							var projectParent = $("input[data-title-id='"+parent+"']").val();
							var projectChildren = $("input[data-title-id='"+children+"']").val();
							var cell=$("input[data-title-id='"+children+"']").attr("data-content");
							if(projectParent > 0 && projectChildren > 0){
								console.log(projectParent * (100/projectChildren))
								return projectParent * (100/projectChildren);
							}else{
								return '';
							}
							
						}
						$("div").delegate("input[data-title-id='"+parent+"']","blur",function(){
							var valuations = calculationValuations();
							if(valuations != null){
									$("input[data-title-id='"+result+"']").val(valuations);
									$("input[data-title-id='"+result+"']").parents("dd").prev().attr("tochange",true);
								
							}
						});
						$("div").delegate("input[data-title-id='"+children+"']","blur",function(){
							var valuations = calculationValuations();
							if(valuations != null){
									$("input[data-title-id='"+result+"']").val(valuations);
									$("input[data-title-id='"+result+"']").parents("dd").prev().attr("tochange",true);
							}
						})
					})
				}
				
				//文本域剩余字符数
				var textarea_h = section.find('.textarea_h');
				for(var i=0;i<textarea_h.length;i++){
					var len=textarea_h.eq(i).val().length;
					var initNum=textarea_h.parent('dd').find(".num_tj").eq(i).find("label").text();
					textarea_h.parent('dd').find(".num_tj").eq(i).find("label").text(initNum-len);
				}
				 //文本域自适应高度 
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
    $(".editable").each(function(){resizetable($(this))})
	//编辑表格显示隐藏
	 check_table();
});
//
function resizetable(table){
    var dict_map = {}
  /*  var fields_json = {
        "finance-history":["field6","field7","field8"],
        "equity-structure":["field3","field4"],
        "investor-situation":["field1","field6"]
    }*/
    var title_id = table.attr("data-title-id")
    var  code = table.attr("data-code")
    var fields_json=tableDictColumn(code);
    console.log(fields_json);
    if (fields_json && code in fields_json){
        var fields = fields_json[code]
        for(var i=0;i<fields.length;i++){
            var v = fields[i]
            var dict = dictCache(title_id,code,v)
            dict_map[title_id+"-"+code+"-"+v] = dict
            table.find('td[data-field-name="'+v+'"]').each(function(){
                var o = $(this)
                o.text(dict[o.text()])
            })
        }
    }
}
//隔轮融资的估值及时间表
function customBuilder()
{
	var div = $("div[data-title-id='1938']");
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
/**
 * 团队相关页面
 * 
 * 
 */

function editRow(ele)
{
	var code = $(ele).closest('table').data('code');
	if(code == 'grant-part' || code == 'grant-actual'){
		 if(!getTotalAppr(projectInfo.id)){
			  return;
		  }
	}
	var row = $(ele).closest('tr');
	var txt= $(ele).text();
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			var title = $("#pop-title");
			 if(txt=="查看"){
					$("#detail-form").hide();
					$(".button_affrim").hide();
					$("#delivery_popup_name").text("查看交割事项");
					 $('#grant_popup_name').html('查看分期注资计划');
					 $('#finace_popup_name').html('查看融资历史');
					 $("#complete_title").html('查看综合竞争比较');
					
				}else{
					$(".see_block").hide();
					$("#delivery_popup_name").text("编辑交割事项");
					 $('#grant_popup_name').html('编辑分期注资计划');
					 $('#finace_popup_name').html('编辑融资历史');
					 $("#complete_title").html('编辑综合竞争比较');
					 $("#pop-title-tz").html('编辑投资人');
					 $("#pop-title-share").html('编辑股东');
					 $("#pop-title-yy").html('编辑运营指标');
				}
			$("#detail-form input[name='subCode']").val(code);
			$("#detail-form input[name='titleId']").val(row.parent().parent().attr("data-title-id"));
			selectContext("detail-form");
			$.each($("#detail-form").find("input, select, textarea"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				var type=ele.attr('type');
				var idVal=ele.attr('id');
				if(type=="radio"){
					if(ele.val()==row.data(name)){
						ele.attr("checked","chedcked");
					}
				}else{
						ele.val(row.data(name));
				}
			});
			//查看显示
			$.each($(".see_block").find("dd[name]"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				ele.text(row.data(name));
				//历史融资特殊处理select,radio
				$.each($("#financeDetail select"),function(){
					var selectId=$(this).val();
					var selectVal=$("#financeDetail select").find("option[value='"+selectId+"']").text();
					if(row.data(name)==selectId && selectId!=""){
						ele.text(selectVal);
					}
					var val=$(".see_block").find("dd[name='field6']").text();
					if(row.data('field3')==""){
						$(".see_block").find("dd[name='field3']").text(row.data('field3'));
					}else{
						$(".see_block").find("dd[name='field3']").text(row.data('field3')+'万'+val);
					}
					if(row.data('field5')==""){
						$(".see_block").find("dd[name='field5']").text(row.data('field5'));
					}else{
						$(".see_block").find("dd[name='field5']").text(row.data('field5')+'万'+val);
					}
					
					
				});
				$.each($("#financeDetail input[type='radio']"),function(){
					var selectId=$(this).val();
					var selectVal=$("#financeDetail").find("input[type='radio'][value='"+selectId+"']").parent().text();
					if(row.data(name)==selectId){
						ele.text(selectVal);
					}
				});
				
				
			})
			//运营 报告嵌套表格处理
			if(reportType == 7){
				if(row.data("dataList"))
				{
					if(row.data("dataList").length > 0){
						$("#field3").attr("readonly","readonly");
					}
					$.each(row.data("dataList"),function(){
						 var row = this;
						 $.get("/sop/html/operation_appr_actual_table.html", row,function(data){
							   //新增数据
							   var o = $(data);
							   o.find("[name='id']").text(row.id);
							   o.find("[name='field1']").text(row.field1);
							   o.find("[name='field2']").text(row.field2);
							   o.find("[name='field3']").text(row.field3);
							   o.find("[name='code']").text(row.code);
							   $("#appr_part").append(o);
						   });
					});
					$("#save_appr_part").click(function(){
						var data = getData($("#detail-form"));
	        		    var dataList = getDataList($("#appr_part"));
	    	            var tr = $('table[data-title-id="'+data['titleId']+'"].editable').find('tr:eq('+data['index']+')');
	    	    		tr.data("dataList",dataList);
	        	        saveForm($("#detail-form"));
				    });
					
				}
			}
			//文本框剩余字数
			$.each($("textarea"),function(){
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

function delRow(ele)
{
	//判断是否可以删除分期注资计划
	var code = $(ele).closest('table').data('code');
	if(code == 'grant-part' || code == 'grant-actual'){
		var tr = $(ele).closest('tr');
		if(tr.data('dataList')){
			var data = tr.data('dataList').length;
			if(data > 0){
				layer.open({
					  type: 1,
					  skin: 'layui-layer layui-anim layui-layer-dialog', //加上边框
					  area: ['420px', '240px'], //宽高
					  content: '有实际注资计划,无法删除分期注资计划'
					});
			    return;
			}
		}
	
	}
	layer.confirm('是否删除?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero){
		var tr = $(ele).closest('tr');
		var id = tr.data('id');
       	var sectionId =$(ele).closest('.radius').attr("data-section-id");
        if(typeof id != 'undefined' && id>0)
        {
           //股权合理性
           if (sectionId ==1324){
              deletedRowIdsGq.push(id);
           }else{
              deletedRowIds.push(id);
           }
        }
		tr.remove();
		check_table();   
		check_table_tr_edit();
		$(".layui-layer-close1").click();
	},function(index) {
	});
 

}
function addRow(ele)
{
   /*  if ( validateCGR() ) { */
        var code = $(ele).prev().data('code');
        //总注资校验
		if(code == 'grant-part' || code == 'grant-actual'){
			 if(!getTotalAppr(projectInfo.id)){
				  return;
			  }
		}
        $.getHtml({
            url:getDetailUrl(code),//模版请求地址
            data:"",//传递参数
            okback:function(){
				$('#qualifications_popup_name').html('添加简历');
				$('#qualifications_popup_name1').html('添加持股人');
				 $('#grant_popup_name').html('添加分期注资计划');
				 $("#complete_title").html('添加综合竞争比较');
				//交割前事项
				$("#delivery_popup_name").text("新增交割事项");
				$(".see_block").hide();
				$('#finace_popup_name').html('添加融资历史');
                $("#detail-form input[name='projectId']").val(projectInfo.id);
                $("#detail-form input[name='titleId']").val($(ele).prev().data('titleId'));
                $("#detail-form input[name='subCode']").val($(ele).prev().data('code'));

                selectContext("detail-form");

                $("#save-detail-btn").click(function(){
                    saveForm($("#detail-form"));
                    check_table();
                    check_table_tr_edit();
                });
                $("#save_person_learning").click(function(){
                	check_table();
                	check_table_tr_edit();
                });
            }//模版反回成功执行
        });
    /* } */
}

/* function validateCGR(){
    var flag = true;
    var trsNum = $("form[id='b_NO3_8']").find('table').find('tr').length-1;
    if(trsNum>=10){
        layer.msg('最多只能添加10条记录!');
        flag = false;
    }
    return flag;
} */

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
function saveRow(data)
{
	data = JSON.parse(data);
	console.log("*****************"+data);
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
			if(key.indexOf('field')>-1 || key == "updateTimeStr" || key == "updateUserName" || key == "updateTimeSign")
			{
				tr.data(key,data[key]);
				tr.find('td[data-field-name="'+key+'"]').text(data[key]);
			}
		}
	}
	resizetable($('table[data-title-id="'+titleId+'"].editable'))
	$("a[data-close='close']").click();
}

function refreshSection(id)
{
	var sec = $(".section[data-section-id='"+id+"']");
    sec.find("dd[data-type='3']").text('未选择');
	sec.showResults(true);
	btn_disable(0);
	
}
function getTableRowLimit(code)
{
	if(code == 'investor-situation' || code =='operation-indices')
	{
		return 20;
	}
	return 10;
}
/**
* 页面加载时，给类型12的题目，绑定change方法，用于第一次没有返回结果的情况
*/
function bindChange(){
    var dts = $("dt[data-type='12']");
    $.each(dts, function (i,n) {
        var dl = $(this).parent();
        var radios = dl.find('input[type="radio"]');
        var last_id = dl.find('input[type="radio"]:last').attr('data-id');
        var inputText = dl.find('input[type="text"]:last');
		if(dl.find('input[type="radio"]:last:checked')){
			 inputText.attr('required' , true);
		}
        $.each(radios , function ( i ,n )
        {
            $(this).unbind('change').bind('change',function(){
                if ( $(this).attr('data-id') == last_id )
                {
                    inputText.attr('disabled',false);
                    inputText.attr('required' , true);
                }
                else
                {
                    inputText.attr('disabled',true);
                    inputText.attr('required' , false);
                }
            });
        });
    });
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
        			  //团队三个核心亮点与核心团队具有的能力联动
        			var titleId=$(this).attr("data-title-id");
            		if(titleId=="1364"){
            			var inputText1=$(this).closest(".mb_16").siblings().find("dt[data-title-id='"+titleId+"']").siblings().find("input[type=\"text\"]:last");
            			if(inputText1.attr("disabled")=="disabled"){
           				  inputText1.attr('disabled',false);
                          inputText1.attr('required' , true);
	           			}else{
	           				inputText1.attr('disabled',true);
	                        inputText1.attr('required' , false);
	           			}
            			$(inputText).on("input",function(){
            				var val=$(this).val();
            				$(inputText1).val(val);
            			});
            			$(inputText1).on("input",function(){
            				var val=$(this).val();
            				$(inputText).val(val);
            			})
            		}
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



function getDetailUrl(code)
{
	if(code == 'equity-structure')
	{
		return path+'/html/funcing_add_gd.html';
	}
	else if(code == 'investor-situation')
	{
		return path+'/html/funcing_add_tz.html';
	}
	else if(code =='operation-indices')
	{
		return path+'/html/fincing_add_yx.html';
	}
	else if(code == 'valuation-reference')
	{
		return path+'/html/fincing_add_tl.html';
	}
	else if(code == 'financing-milestone')
	{
		return path+'/html/fincing_add_jd.html';
	}
	else if(code == 'finance-history')
	{
		return path+'/html/finace_history.jsp';
	}
	else if (code =='team-members'){

	    return path+'/html/team_compile.html';
	}else if(code == 'share-holding')
    {
        return path+'/html/team_add_cgr.html';
    }else if(code == 'competition-comparison')
	{
		return path+'/html/compete_save.jsp';
	}else if(code == 'delivery-before')
	{
		return path+'/html/delivery_matter.jsp';
	}else if(code == 'delivery-after')
	{
		return path+'/html/delivery_matter.jsp';
	}else if(code == 'grant-part' || code == 'grant-actual')
	{
	    if(reportType == 7){
	    	return path+'/html/operation_appr_part.html';
	    }
		return path+'/html/grant-part.jsp';
	}
	return "";
}



function tableDictColumn(code){
	var json;
	var arr=[];
	if(code == 'competition-comparison')
	{
        return json={"competition-comparison":["field5"]};
	}else if(code == 'finance-history'){
		return json={"finance-history":["field6","field7","field8"]};
	}else if(code=="equity-structure"){
		return json={"equity-structure":["field3","field4"]};
	}else if(code=="investor-situation"){
		return json={"investor-situation":["field1","field6"]};
	}
}

/*function dealTableDict(table){
    var dict_map = {}
    var title_id = table.attr("data-title-id")
    var  code = table.attr("data-code")
    var fields_json=tableDictColumn(code);
    if (code in fields_json){
        var fields = fields_json[code]
        for(var i=0;i<fields.length;i++){
            var v = fields[i]
            var dict = dictCache(title_id,code,v)
            dict_map[title_id+"-"+code+"-"+v] = dict
            table.find('td[data-field-name="'+v+'"]').each(function(){
                var o = $(this)
                o.text(dict[o.text()])
            })
        }
    }
}*/


/**
 * 获取总注资计划并校验
 * @param projectId
 * @returns {Boolean}
 */
function getTotalAppr(projectId){
	var flag = false;
	var params={};
	params.projectId = projectId;
	params.titleId = "3004";
	sendPostRequestByJsonObj(
				Constants.sopEndpointURL+'/galaxy/infoProject/getTotalAppr' , 
				params,
				function(data){
					if(data.result.status == "OK"){
						if(typeof(data.userData) == "object"){
							if(data.userData.totalMoney || data.userData.remainMoney){
								flag = true;
								totalMoney = data.userData.totalMoney;
								remainMoney = data.userData.remainMoney == null ? 0 : data.userData.remainMoney;
							}
						}
					}
				});
	if(!flag){
		layer.open({
			  type: 1,
			  skin: 'layui-layer layui-anim layui-layer-dialog', //加上边框
			  area: ['420px', '240px'], //宽高
			  content: '无法添加分期注资计划,需要补全以下信息:投决会结果中的投资金额、估值安排、星河投资方主体'
			});
	}
	return flag;
}


