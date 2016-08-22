<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=path %>/css/bootstrap.min-v3.3.5.css"  />-->
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- 保存进度条 -->
<link href="<%=path %>/css/showLoading.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
    <!--右中部内容-->
 	<div class="ritmin">
        <h2>项目模板</h2>       
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="javascript:;" class="pubbtn bluebtn ico c3" id="show-mail-btn">发邮件给</a>
            </div>
        </div>
        <!--表格内容-->
        <table width="100%" cellspacing="0" cellpadding="0" id="template-table">
              <thead>
                  <tr>
                      <th style="width:5%;"></th>
                      <th class="width_gd">业务类型</th>
                      <th>所属部门</th>
                      <th>存储类型</th>
                      <th>上传者</th>
                      <th>更新日期</th>
                      <th>操作</th>
                  </tr>
              </thead>                                                                                                                                    
              <tbody>
              </tbody>
          </table>
          <!--分页
          <div class="pagright clearfix">
              <ul class="paging clearfix">
                  <li>每页<input type="text" class="txt" value="20"/>条/共<span>9</span>条记录</li>
                  <li class="margin">共1页</li>
                  <li><a href="javascript:;">|&lt;</a></li>
                  <li><a href="javascript:;">&lt;</a></li>
                  <li><a href="javascript:;">&gt;</a></li>
                  <li><a href="javascript:;">&gt;|</a></li>
                  <li class="jump clearfix">
                      第<input type="text" class="txt" value="1"/>页
                      <input type="button" class="btn margin" value="GO">
                  </li>
              </ul>
          </div>
          -->
    </div>
 
</div>
<!-- upload dialog start -->
<div id="upload-dialog"  class="archivestc " style="display:none;">

	<div class="title_bj">模板更新</div>
<form id="upload-form" style='margin-top:45px;'>
	<input type="hidden" name="id">
	<input type="hidden" name="fileKey">
	<input type="hidden" name="fileLength">
    <dl class="fmdl clearfix ">
    	<dt>存储类型：</dt>
        <dd>
        	<select name="docType" class="disabled" disabled="disabled"></select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>业务分类：</dt>
        <dd>
        	<select name="worktype" class="disabled" disabled="disabled"></select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>所属部门：</dt>
        <dd>
        	<select name="departmentId" disabled="disabled" class="disabled"></select>
        </dd>
    </dl>
    <div class="fmdl clearfix">
    	<dt>选择档案：</dt>
    	<dd>
        <input type="text" name="fileName" class="txt" onchange="selectFile(this)" disabled="disabled"/>
    	</dd>
    	<dd>
        <a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">选择档案</a>
    	</dd>
    </div>
</form>
    <a href="javascript:;" class="pubbtn bluebtn" id="upload-btn">上传保存</a>
</div>
<!-- upload dialog end -->
<!-- Mail dialog start-->
<div id="mail-dialog" style="display:none;">
<form id="mail-form">
<div class="title_bj">模板管理-邮件分享</div>
<div class="emailtc margin_45" >
    <dl class="fmdl clearfix">
        <dt>收件人：</dt>
        <dd class="clearfix">
            <input type="text" name="toAddress" class="txt" data-rule-required="true"/>
        </dd>
        <dd>            
            <label class="red">&#42;必填</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
        <dt>邮件分类：</dt>
        <dd class="clearfix">
            <table width="100%" cellspacing="0" cellpadding="0" id="attach-table">
              <thead>
                  <tr>
                      <th>序号</th>
                      <th>档案名称</th>
                 <!--       <th>档案大小 </th>-->
                  </tr>
              </thead>                                                                                                                     
              <tbody>
              </tbody>
          </table> 

        </dd>
    </dl>
    
    <div class="btnbox">
    	<a href="javascript:;" class="pubbtn bluebtn" id="send-mail-btn">发送</a>
    </div>
</div>
</form>
</div>
<!-- Mail dialog end -->
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type="text/javascript">
var uploader;
$(function(){
	createMenus(13);
	loadRelatedData();
	loadTempList();
	$("#show-mail-btn").click(function(){
		showMailPopup();
	});
	$("#upload-form .disabled").attr('disabled','disabled');
});
/**
 * 加载模板列表
 */
function loadTempList()
{
	sendGetRequest(
			platformUrl.queryTemplate,
			null,
			function(data){
				$("#template-table tbody").empty();
				var editableTypes = data.userData.editableTypes;
				$.each(data.entityList,function(){
					var $tr = $('<tr data-id="'+this.id+'" data-file-key="'+this.fileKey+'" data-doc-type="'+this.docType+'" data-department-id="'+this.departmentId+'" data-file-name="'+this.fileName+'" data-worktype="'+this.worktype+'" data-worktype-desc="'+this.workTypeDesc+'" data-file-length="'+this.fileLength+'"></tr>');
					$tr.append('<td style="text-align:center;"><input type="checkbox" name="document" /></td>') ;
					$tr.append('<td>'+getVal(this.workTypeDesc,"-")+'</td>') ;
					$tr.append('<td>'+getVal(this.departmentDesc,"-")+'</td>') ;
					$tr.append('<td>'+getVal(this.docTypeDesc,"-")+'</td>') ;
					$tr.append('<td>'+getVal(this.updateUname,"-")+'</td>') ;
					$tr.append('<td>'+Number(this.updatedTime).toDate().format("yyyy-MM-dd")+'</td>') ;
					if(this.fileName != null)
					{
						var $td = $('<td></td>');
						$td.append('<a data-act="download" data-tid='+this.id+' href="javascript:; " class="blue" style="margin-right:10px;">下载</a>');
						if(editableTypes.indexOf(this.worktype)>=0)
						{
							$td.append('<a data-act="update" data-tid='+this.id+' href="javascript:; " class="blue">更新</a>');
						}
						$tr.append($td) ; 
					}
					else
					{
						var $td = $('<td></td>');
						if(editableTypes.indexOf(this.worktype)>=0)
						{
							$td.append('<a data-act="upload" data-tid='+this.id+' href="javascript:; " class="blue">上传</a>');
						}
						$tr.append($td) ;
					}
					$("#template-table tbody").append($tr);
					
				});
				handleDownload();
				handleUpload();
			}
	);
}
/**
 * 加载下拉列表数据
 */
function loadRelatedData()
{
	sendGetRequest(
			platformUrl.getTempRelatedData,
			null,
			function(data){
				$.each(data.fileType,function(){
					$("#upload-form [name='docType']").append("<option value='"+this.code+"'>"+this.name+"</option>")
				});
				$.each(data.templateType,function(){
					$("#upload-form [name='worktype']").append("<option value='"+this.code+"'>"+this.name+"</option>")
				});
				$.each(data.department,function(){
					$("#upload-form [name='departmentId']").append("<option value='"+this.id+"'>"+this.name+"</option>")
				});
			}
	);
}

function handleDownload()
{
	$("[data-act='download']").click(function(){
		var $self = $(this);
		var id = $self.data("tid");
		var url = platformUrl.tempDownload+"?id="+id;
		forwardWithHeader(url);
	});
}

function handleUpload()
{
	$("[data-act='update'],[data-act='upload']").click(function(){
		var $self = $(this);
		var id = $self.data("tid");
		$.popup({
			txt:$("#upload-dialog").html(),
			showback:function(){
				var _dialog = this;
				$("#upload-form input[name='id']").val($self.data("tid"));
				if($self.data('act') == 'update')
				{
					$(_dialog.id).find("h2").text("模板更新");
				}
				else
				{
					$(_dialog.id).find("h2").text("模板上传");
				}
				
				initUpload(_dialog);
				var row = $("tr[data-id='"+id+"']")[0];
				var $form = $(_dialog.id).find("#upload-form");
				setForm($form,$(row).data());
			}
		});
	});
}
/**
 * 判断文档类型
 */
function selectFile(ele)
{
	var file = ele.value;
	var dotPos = file.lastIndexOf(".");
	var ext = file.substring(dotPos);
	if(/\.(doc|docx|xls|xlsx|pdf)$/.test(ext.toLowerCase()))
	{
		$("#upload-form [name='docType']").val(1);
	}
	else if(/\.(mp3|wmv)$/.test(ext.toLowerCase()))
	{
		$("#upload-form [name='docType']").val(2);
	}
	else if(/\.(avi|mov|wmv|mkv)$/.test(ext.toLowerCase()))
	{
		$("#upload-form [name='docType']").val(3);
	}
	else
	{
		$("#upload-form [name='docType']").val(4);
	}
	
}

function initUpload(_dialog)
{
	var uploader = new plupload.Uploader({
		runtimes : 'html5,html4,flash,silverlight',
		browse_button : $(_dialog.id).find("#file-select-btn")[0], 
		url : platformUrl.tempUpload+"?sid="+sessionId+"&guid="+userId,
		multi_selection:false,
		filters : {
			max_file_size : '25mb',
			mime_types: paramsFilter()
		},

		init: {
			PostInit: function() {
				$(_dialog.id).find("#upload-btn").click(function(){
					if(uploader.files.length==0)
					{
						layer.msg("请选择文件.");
						return;
					}
					uploader.start();
					return false;
				});
			},

			FilesAdded: function(up, files) {
				$.each(uploader.files,function(){
					if(this != files[0])
					{
						uploader.removeFile(this);
					}
				});
				$.each(files, function() {
					$(_dialog.id).find("input[name='fileName']").val(this.name);
					var fileType = getFileTypeByName(this.name);
					$(_dialog.id).find("[name='docType']").val(fileType);
				});
			},
			BeforeUpload : function(up,file){
				$("#powindow").showLoading(
						 {
						    'addClass': 'loading-indicator'						
						 });
				$form = $(_dialog.id).find("#upload-form");
				$form.find('.disabled').removeAttr('disabled');
				up.settings.multipart_params =  JSON.parse($form .serializeObject());
				$form.find('.disabled').attr('disabled','disabled');
			},
			FileUploaded: function(up, files, rtn) {
				$("#powindow").hideLoading();
				var data = $.parseJSON(rtn.response);
				if(data.result.status == 'OK')
				{
					layer.msg("上传成功.");
					$(_dialog.id).find("[data-close='close']").click();
					loadTempList();
				}
				else
				{
					layer.msg(data.result.message);
				}
			},
			Error: function(up, err) {
				$("#powindow").hideLoading();
				layer.msg("上传失败:"+err.message);
			}
		}
	});

	uploader.init();
	
}

function setForm(form,data)
{
	for(key in data)
	{
		if(data[key] != 'undefined' && data[key])
		{
			if(key != 'fileName')
			{
				form.find("[name='"+key+"']").val(data[key]);
			}
		}
	}
	
}

function showMailPopup()
{
	var flags = $("#template-table input[type='checkbox']:checked");
	var len = flags.length;
	if(len == 0 )
	{
		layer.msg("请选择模板.");
		return;
	}
	
	$.popup({
		txt:$("#mail-dialog").html(),
		showback:function(){
			var _dialog = this;
			var i=0;
			var opts = {
					rules : {
						toAddress:{
							required:true,
							emails:true
						}
						
					}
			};
			var valdator = $(_dialog.id).find('form').fxValidate(opts);
			var ids = new Array();
			$.each(flags,function(){
				var flag = $(this);
				i++;
				var $row = $(this).closest("tr");
				var $tr=$("<tr></tr>");
				$tr.append("<td>"+ i +"</td>");
				$tr.append("<td>"+ $row.data('worktype-desc') +"</td>");
			//	$tr.append("<td>"+ getFileSize($row.data('file-length')) +"</td>");
				$(_dialog.id).find("#attach-table tbody").append($tr);
				ids.push($row.data('id'));
			});
			$(_dialog.id).find("#send-mail-btn").click(function(){
				if(!valdator.form())
				{
					return;
				}
				$(this).addClass('disabled');
			 	var $form = $(_dialog.id).find("#mail-form");
				var data = JSON.parse($form .serializeObject());
				data['templateIds']=ids;
				var url = platformUrl.tempSendMail;
				sendPostRequestByJsonObj(
						url,
						data,
						function(data){
							if(data.status=="OK")
							{
								layer.msg("发送邮件成功.");
								$(_dialog.id).find("[data-close='close']").click();
							}
							else
							{
								layer.msg("发送邮件失败.");
								$(_dialog.id).find("#send-mail-btn").removeClass('disabled');
							}
						}
				); 
			});
		}
	});
}
</script>
</body>
</html>