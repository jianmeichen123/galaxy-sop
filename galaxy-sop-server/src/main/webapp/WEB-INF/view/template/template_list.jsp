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
<form id="upload-from" method="post" enctype="multipart/form-data">
	<input type="hidden" name="id">
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
        	<select name="department"></select>
        </dd>
    </dl>
    <div class="fmload clearfix">
    	<p class="loadname"></p>
        <input type="file" class="load" onchange="selectFile(this)"/>
        <a href="javascript:;" class="pubbtn fffbtn">选择档案</a>
    </div>
    <div class="fmarea">
    	<textarea name="remark"></textarea>
    </div>
</form>
    <a href="javascript:;" class="pubbtn bluebtn">上传保存</a>
</div>
<!-- upload dialog end -->
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
$(function(){
	createMenus(1);
	loadTempList();
	loadRelatedData();
});

function loadTempList()
{
	sendGetRequest(
			"<%=path %>"+platformUrl.queryTemplate,
			null,
			function(data){
				$("#template-table tbody").empty();
				$.each(data.entityList,function(){
					var $tr = $('<tr></tr>');
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
					
					handleDownload();
					handleUpload();
				});
			}
	);
}

function loadRelatedData()
{
	sendGetRequest(
			"<%=path %>"+platformUrl.getTempRelatedData,
			null,
			function(data){
				$.each(data.fileType,function(){
					$("#upload-from [name='docType']").append("<option value='"+this.value+"'>"+this.name+"</option>")
				});
				$.each(data.fileWorktype,function(){
					$("#upload-from [name='worktype']").append("<option value='"+this.value+"'>"+this.name+"</option>")
				});
				$.each(data.department,function(){
					$("#upload-from [name='department']").append("<option value='"+this.id+"'>"+this.name+"</option>")
				});
			}
	);
}

function handleDownload()
{
	$("[data-act='download']").click(function(){
		
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
				$("#upload-from input[name='id']").val($self.data("tid"));
				if($self.data('act') == 'update')
				{
					$("#popTxt").find("h2").text("模板更新");
				}
				else
				{
					$("#popTxt").find("h2").text("模板上传");
				}
			}
		});
	});
}
function selectFile(ele)
{
	var file = ele.value;
	var dotPos = file.lastIndexOf(".");
	var ext = file.substring(dotPos);
	if(/\.(doc|docx|xls|xlsx|pdf)$/.test(ext.toLowerCase()))
	{
		$("#upload-from [name='docType']").val(1);
	}
	else if(/\.(mp3|wmv)$/.test(ext.toLowerCase()))
	{
		$("#upload-from [name='docType']").val(2);
	}
	else if(/\.(avi|mov|wmv|mkv)$/.test(ext.toLowerCase()))
	{
		$("#upload-from [name='docType']").val(3);
	}
	else
	{
		$("#upload-from [name='docType']").val(4);
	}
	
}
</script>
</body>
</html>
