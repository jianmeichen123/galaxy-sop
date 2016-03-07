<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
    <!--右中部内容-->
 	<div class="ritmin">
        <h2>模板管理</h2>       
        <!--页眉-->
        <div class="top clearfix">
        	<!--按钮-->
            <div class="btnbox_f btnbox_f1 clearfix">
                <a href="javascript:;" class="pubbtn bluebtn ico c3">发邮件给</a>
            </div>
        </div>
        <!--表格内容-->
        <table width="100%" cellspacing="0" cellpadding="0" id="template-table">
              <thead>
                  <tr>
                      <th></th>
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
          <!--分页-->
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
    </div>
 
</div>
<!-- upload dialog start -->
<div id="upload-dialog" class="archivestc" style="display:none;">
<form id="upload-form">
	<input type="hidden" name="id">
	<input type="hidden" name="fileKey">
	<h2>模板更新</h2>   
    <dl class="fmdl clearfix">
    	<dt>存储类型：</dt>
        <dd>
        	<select name="docType"></select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>业务分类：</dt>
        <dd>
        	<select name="worktype"></select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>所属部门：</dt>
        <dd>
        	<select name="departmentId"></select>
        </dd>
    </dl>
    <div class="fmdl clearfix">
    	<dd>
        <input type="text" name="bucketName" class="txt" onchange="selectFile(this)"/>
    	</dd>
    	<dd>
        <a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">选择档案</a>
    	</dd>
    </div>
    <div class="fmarea">
    	<textarea name="remark"></textarea>
    </div>
</form>
    <a href="javascript:;" class="pubbtn bluebtn" id="upload-btn">上传保存</a>
</div>
<!-- upload dialog end -->
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
var uploader;
$(function(){
	createMenus(13);
	loadTempList();
	loadRelatedData();
});
/**
 * 加载模板列表
 */
function loadTempList()
{
	sendGetRequest(
			"<%=path %>"+platformUrl.queryTemplate,
			null,
			function(data){
				$("#template-table tbody").empty();
				$.each(data.entityList,function(){
					var $tr = $('<tr data-id="'+this.id+'" data-file-key="'+this.fileKey+'" data-doc-type="'+this.docType+'" data-department-id="'+this.departmentId+'" data-bucket-name="'+this.bucketName+'" data-remark="'+this.remark+'" data-worktype="'+this.worktype+'"></tr>');
					$tr.append('<td><input type="checkbox" name="document" checked="checked"/></td>') ;
					$tr.append('<td>'+this.workTypeDesc+'</td>') ;
					$tr.append('<td>'+this.departmentDesc+'</td>') ;
					$tr.append('<td>'+this.docTypeDesc+'</td>') ;
					$tr.append('<td>'+this.updateUname+'</td>') ;
					$tr.append('<td>'+Number(this.updatedTime).toDate().format("yyyy/MM/dd")+'</td>') ;
					if(this.fileKey != null)
					{
						$tr.append('<td><a data-act="download" data-tid='+this.id+' href="javascript:; " class="blue">下载</a><a data-act="update" data-tid='+this.id+' href="javascript:; " class="blue">更新</a></td>') ; 
					}
					else
					{
						$tr.append('<td><a data-act="upload" data-tid='+this.id+' href="javascript:; " class="blue">上传</a></td>') ;
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
			"<%=path %>"+platformUrl.getTempRelatedData,
			null,
			function(data){
				$.each(data.fileType,function(){
					$("#upload-form [name='docType']").append("<option value='"+this.value+"'>"+this.name+"</option>")
				});
				$.each(data.fileWorktype,function(){
					$("#upload-form [name='worktype']").append("<option value='"+this.value+"'>"+this.name+"</option>")
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
		var url = "<%=path %>"+platformUrl.tempDownload+"/"+id;
		window.location.href=url;
	});
}

function handleUpload()
{
	$("[data-act='update'],[data-act='upload']").click(function(){
		var $self = $(this);
		var id = $self.data("tid");
		$.popup({
			txt:$("#upload-dialog").html(),
			callback:function(t,postionEve){
				postionEve();
				$("#popTxt").html($("#upload-dialog").html());
				$("#upload-form input[name='id']").val($self.data("tid"));
				if($self.data('act') == 'update')
				{
					$("#popTxt").find("h2").text("模板更新");
				}
				else
				{
					$("#popTxt").find("h2").text("模板上传");
				}
				
				initUpload();
				var row = $("tr[data-id='"+id+"']")[0];
				setForm($("#popTxt #upload-form"),row.dataset);
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

function initUpload()
{
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#popTxt").find("#file-select-btn")[0], 
		url : '<%=path %>'+platformUrl.tempUpload,
		multi_selection:false,
		filters : {
			max_file_size : '10mb'
		},

		init: {
			PostInit: function() {
				$("#popTxt").find("#upload-btn").click(function(){
					uploader.start();
					return false;
				});
			},

			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					$("#popTxt input[name='bucketName']").val(file.name);
				});
			},
			
			FileUploaded: function(up, files, rtn) {
				$("#popTxt input[name='fileKey']").val(rtn.response);
				$form = $("#popTxt #upload-form");
				var data = JSON.parse($form .serializeObject());
				var url = "<%=path %>"+platformUrl.tempSave
				sendPostRequestByJsonObj(
						url,
						data,
						function(data){
							alert("上传成功.");
							loadTempList();
						}
				);
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
			form.find("[name='"+key+"']").val(data[key]);
		}
	}
	
}
</script>
</body>
</html>