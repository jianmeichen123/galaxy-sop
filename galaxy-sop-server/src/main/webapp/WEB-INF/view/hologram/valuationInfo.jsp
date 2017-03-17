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
</head>
<body>
<ul class="h_navbar clearfix">
     <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基本<br/>信息</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
    <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
    <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
    <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('8')">融资及<br/>估值</li>

  </ul>
  <div id="tab-content">
		<div class="tabtxt" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>
	</div>

<!--点击编辑例子 -->
<div class="h radius" id="div_ifelse"></div>
<!--点击编辑例子 -->
<script id="ifelse" type="text/x-jquery-tmpl">
<div class="h_edit" id="b_\${code}">
	<div class="h_btnbox">
		<span class="h_save_btn">保存</span><span class="h_cancel_btn"
			data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}
		<div class="mb_16">
			<dl class="h_edit_txt clearfix">
			{{if type=="1"}}
				<dt class="" data-type="\${type}">\${name}</dt>
                <dd class=""><input type="text" name="\${titleId}" data-titleId="\${titleId}" data-value="\${value}" data-parentId="\${parentId}"></dd>
			{{else type=="2"}}
				<dt class="fl_none" data-type="\${type}">\${name}</dt>
				<dd class="fl_none">
					<ul class="h_radios clearfix">
						{{each(i,valueList) valueList}}
                    	<li><input type="radio" name="\${titleId}" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}">\${name}</li>
						{{/each}}
                 	</ul>
				</dd>
			{{else type=="3"}}
						<dt class="fl_none" data-type="\${type}"  data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>	
                        <dd class="fl_none">
						<ul class="h_edit_checkbox clearfix">
							{{each(i,valueList) valueList}}
                            <li class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</li>
							{{/each}}
                          </ul>
						</dd>

			{{else type=="5" }}
				<dl class="h_edit_checkbox clearfix">
                   	<dt data-type="\${type}">\${name}</dt>
					<dd>
                   		<ul class="h_radios clearfix">
                            {{each(i,valueList) valueList}}
                    		<li><input type="radio" name="\${titleId}" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}">\${name}</li>
							{{/each}}
                          </ul>
                          <textarea name="" class="textarea_h" id="textarea_h" placeholder="备注信息" style="resize: none; height: 70px;"></textarea>
                          <p class="num_tj"><label for="">0</label>/500</p>
                 	</dd>
                </dl>
			{{else type=="6" }}
				<dl class="h_edit_checkbox clearfix">
                   	<dt data-type="\${type}">\${name}</dt>
					<dd>
                   		<ul class="h_radios clearfix">
                            {{each(i,valueList) valueList}}
                    		<li><input type="radio" name="\${titleId}" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}">\${name}</li>
							{{/each}}
                          </ul>
                          <textarea name="" class="textarea_h" id="textarea_h" placeholder="备注信息" style="resize: none; height: 70px;"></textarea>
                          <p class="num_tj"><label for="">0</label>/500</p>
                 	</dd>
                </dl>
			{{else type=="7"}}
				<dl>
                 	<dt class="fl_none">除去非主营业务外，运营数据曲线变化（细分项目、拆分到年度、月度、周、日）：</dt>
                     <dd class="fl_none clearfix">
                     <ul class="h_imgs">
                             
                     </ul>
                     <ul class="h_imgs">
                      	<li class="h_imgs_add"><input type="file"></li>
                      </ul>
                    </dd>
                  	<dd class="fl_none red">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>
             	</dl>
			{{else type=="8"}}
				<dt class="fl_none" data-type="\${type}">\${name}</dt>
				<dd class="fl_none">
					<textarea class="textarea_h" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}"></textarea>
					<p class="num_tj">
						<label for="">0</label>/2000
					</p>
				</dd>
			{{else}}
			<dd class="fl_none">
				<textarea class="textarea_h">eee</textarea>
				<p class="num_tj">
					<label for="">0</label>/2000
				</p>
			</dd>
			{{/if}}
			</dl>
			
		</div>
	{{/each}}
	
	<div class="h_edit_btnbox clearfix">
		<span class="pubbtn bluebtn fl" data-on="save" data-code="\${code}">保存</span> <span
			class="pubbtn h_cancel_btn fffbtn fl" data-name="basic" data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>
</div>										
</script>



<!--页面例子 -->
<script id="page_list" type="text/x-jquery-tmpl">
{{each(i,childList) childList}}
<div class="h radius" id="a_\${code}">
  <div class="h_look h_team_look clearfix" id="\${code}">
	<div class="h_btnbox"><span class="h_edit_btn" attr-id="\${code}">编辑</span></div>
	<div class="h_title">\${name}</div>
	<div class="mb_24 clearfix">
	  <dl class="clearfix">
		<dt>商业模式：</dt>
		<dd>该项目是一个通过或基于（技术或模式）的（选择三级以下分类) 的（具体品类：平台、运营商、服务商、技术提供商、解决方案提供商、工具），连接（服务一端）和（服务另一端），为（用户）提供（产品服务即内容）的产品或服务，满足了（需求，如有）的刚需或解决了（痛点，如有）。</dd>
	  </dl>
	</div>
	 <div class="mb_24 clearfix">
	  <dl class="clearfix">
		<dt>商业模式进化：</dt>
		<dd>尚未验证</dd>
	  </dl>
	</div>
  </div>
</div>
{{/each}}
</script>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script type="text/javascript">
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO4", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				console.log(entity);
				$("#page_list").tmpl(entity).appendTo('#page_all');
			} else {

			}
		})
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
		var id_code = $(this).attr('attr-id');
		alert(id_code)
		
		event.stopPropagation();
		$("#"+id_code).hide();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					console.log(entity);
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
				} else {

				}
		}) 
	});
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var id_code = $(this).attr('attr-hide');
		alert(id_code);
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		event.stopPropagation();
	});
	
</script>
</body>


</html>
