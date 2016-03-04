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
<script src="<%=path %>/js/common.js" type="text/javascript"></script>
<script src="<%=path %>/js/platformUrl.js" type="text/javascript"></script>
<script src="<%=path %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure_ext.js" type="text/javascript"></script>
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
	<h2>模板更新</h2>   
    <dl class="fmdl clearfix">
    	<dt>存储类型：</dt>
        <dd>
        	<select name="docType">
            	<option value="1">文档</option>
            	<option value="2">音频文件</option>
            	<option value="3">视频文件</option>
            	<option value="4">图片</option>
            </select>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>业务分类：</dt>
        <dd>
        	<select>
            	<option value="1">业务尽职调查报告</option>
            	<option value="2">人力资源尽职调查报告</option>
            	<option value="3">法务尽职调查报告</option>
            	<option value="4">财务尽职调查报告</option>
            	<option value="5">投资意向书</option>
            	<option value="6">投资协议</option>
            	<option value="7">股权转让协议</option>
            	<option value="8">工商转让凭证</option>
            	<option value="9">资金拨付凭证</option>
            	<option value="10">公司资料</option>
            	<option value="11">财务预测报告</option>
            	<option value="12">商业计划</option>
            </select>
        </dd>
        <dd>
        	<label><input type="checkbox"/>签署凭证</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>所属部门：</dt>
        <dd>
        	<input type="text" placeholder="请输入项目名称或编号" class="txt"/>
        </dd>
        <dd><a class="searchbtn null" href="javascript:;">搜索</a></dd>
    </dl>
    <div class="fmload clearfix">
    	<p class="loadname"></p>
        <input type="file" class="load"/>
        <a href="javascript:;" class="pubbtn fffbtn">选择档案</a>
    </div>
    <div class="fmarea">
    	<textarea></textarea>
    </div>
    <a href="javascript:;" class="pubbtn bluebtn">上传保存</a>
</div>
<!-- upload dialog end -->
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<script type="text/javascript">
$(function(){
	createMenus(1);
	loadTempList();
});

function loadTempList()
{
	$.ajax({
		url:"<%=path %>"+platformUrl.queryTemplate,
		type : 'GET',
		dataType : "json",
		success:function(data){
			$("#template-table tbody").empty();
			$.each(data.entityList,function(){
				var $tr = $('<tr></tr>');
				$tr.append('<td><input type="checkbox" name="document" checked="checked"/></td>') ;
				$tr.append('<td>'+this.workTypeDesc+'</td>') ;
				$tr.append('<td>'+this.departmentDesc+'</td>') ;
				$tr.append('<td>'+this.docTypeDesc+'</td>') ;
				$tr.append('<td>'+this.updateUname+'</td>') ;
				$tr.append('<td>'+Number(this.updatedTime).toDate().format("yyyy/MM/dd")+'</td>') ;
				if(this.fileUri != null)
				{
					$tr.append('<td><a data-act="download" data-tid='+this.id+' href="javascript:; " class="blue">下载</a><a data-act="update" data-tid='+this.id+' href="javascript:; " class="blue">更新</a></td>') ; 
				}
				else
				{
					$tr.append('<td><a data-act="upload" data-tid='+this.id+' href="javascript:; " class="blue">上传</a></td>') ;
				}
				$("#template-table tbody").append($tr);
				setBtnHandler();
			});
		}
	});
}

function setBtnHandler()
{
	$("[data-act='download']").click(function(){
		
	});
	$("[data-act='upload']").click(function(){
		var $self = $(this);
		var id = $self.data("tid");
		$.popup({
			txt:$("#upload-dialog").html(),
			callback:function(t,postionEve){
				postionEve();
				$("#upload-dialog").show().children("h2").text("模板上传");
			}
		});
	});
	$("[data-act='update']").click(function(){
		var $self = $(this);
		var id = $self.data("tid");
		$.popup({
			txt:$("#upload-dialog").html(),
			callback:function(t,postionEve){
				postionEve();
				$("#upload-dialog").show().children("h2").text("模板更新");
			}
		});
	});
}



</script>
</body>
</html>
