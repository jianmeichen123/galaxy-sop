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
                  <li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('5')">战略及<br/>策略</li>
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
<div class="h_edit" >
	<div class="h_btnbox">
		<span class="h_save_btn" attr-save="\${code}">保存</span><span class="h_cancel_btn"
			data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}  
 
                 	{{if sign=="3"}}
						{{each(i,childList) childList}}
						<div class="mb_16">
                       <dl class="h_edit_txt clearfix">
						<dt data-type="\${type}"  data-title-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
						{{if type=="1"}}
                        <dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}"/></dd>

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
						<ul class="h_edit_checkbox clearfix">
							{{each(i,valueList) valueList}}
                            <li class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</li>
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
                            <li><input type="radio" value="\${id}" data-value="\${value}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
							{{/each}}
                          </ul>
						</dd>
						<dd class="fl_none">
							<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}""></textarea>
							<p class="num_tj">
								<label for="">500</label>/500
							</p>
						</dd>
						
						
						{{else type=="6"}}
						{{each(i,valueList) valueList}}
                        <dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
						{{/each}}
						
						{{else type=="7"}}
                 			<dt class="fl_none">除去非主营业务外，运营数据曲线变化（细分项目、拆分到年度、月度、周、日）：</dt>
                    		<dd class="fl_none clearfix">
                    		 <ul class="h_imgs">
                             
                    		 </ul>
                    		 <ul class="h_imgs">
                      			<li class="h_imgs_add"><input type="file"></li>
                      		</ul>
                    		</dd>
                  			<dd class="fl_none red">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>
						{{else type=="8"}}
						<dt class="fl_none" data-type="\${type}">\${name}</dt>
						<dd class="fl_none">
							<textarea class="textarea_h" data-titleId="\${id}" data-type="\${type}"></textarea>
							<p class="num_tj">
								<label for="">0</label>/2000
							</p>
						</dd>

						{{else type=="10"}}
						<dd class="fl_none">
                            <table>
                              <tr>
                                <th>姓名</th>
                                <th>职位</th>
                                <th>性别</th>
                                <th>最高学历</th>
                                <th>操作</th>
                              </tr>
                              <tr>
                                <th>罗振宇</th>
                                <td>CEO</td>
                                <td>男</td>
                                <td>博士</td>
                                <td>
                                  <span class="blue" data-btn='btn'>查看</span><span class="blue" data-btn='btn'>编辑</span><span class="blue" data-btn='btn'>删除</span>
                                </td>
                              </tr>
                            </table>
							<span class="pubbtn bluebtn">新增</span>
                          </dd>

						{{else type=="11"}}
						<dd>项目带过来的数据</dd>

						{{/if}}
                      </dl>
                    </div>

						{{/each}}
					{{else}}
					<div class="mb_16">
                       <dl class="h_edit_txt clearfix">
						<dt data-type="\${type}"  data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
						{{if type=="1"}}
                        <dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" /></dd>

						{{else type=="2"}}
						<dd>
						<ul class="h_radios clearfix">
							{{each(i,valueList) valueList}}
                            <li><input type="radio" name="\${titleId}" value="\${id}" data-title-id="\${titleId}" data-type="\${type}"/>\${name}</li>
							{{/each}}
                          </ul>
						</dd>

						{{else type=="3"}}
						
                        <dd class="fl_none">
						<ul class="h_edit_checkbox clearfix">
							{{each(i,valueList) valueList}}
                            <li class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</li>
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
                            <li><input type="radio" data-value="\${value}" data-type="\${type}" placeholder="\${placeholder}"/>\${name}</li>
							{{/each}}
                          </ul>
						</dd>
						<dd class="fl_none">
							<textarea class="textarea_h" data-titleId="\${titleId}" data-type="\${type}" data-parentId="\${parentId}" placeholder="\${placeholder}"></textarea>
							<p class="num_tj">
								<label for="">500</label>/500
							</p>
						</dd>

						{{else type=="6"}}
						{{each(i,valueList) valueList}}
                        <dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
						{{/each}}

						{{else type=="7"}}
                 			<dt class="fl_none">除去非主营业务外，运营数据曲线变化（细分项目、拆分到年度、月度、周、日）：</dt>
                    		<dd class="fl_none clearfix">
                    		 <ul class="h_imgs">
                             
                    		 </ul>
                    		 <ul class="h_imgs">
                      			<li class="h_imgs_add"><input type="file"></li>
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
						<dd class="fl_none">
                            <table>
                              <tr>
                                <th>姓名</th>
                                <th>职位</th>
                                <th>性别</th>
                                <th>最高学历</th>
                                <th>操作</th>
                              </tr>
                              <tr>
                                <th>罗振宇</th>
                                <td>CEO</td>
                                <td>男</td>
                                <td>博士</td>
                                <td>
                                  <span class="blue" data-btn='btn'>查看</span><span class="blue" data-btn='btn'>编辑</span><span class="blue" data-btn='btn'>删除</span>
                                </td>
                              </tr>
                            </table>
							<span class="pubbtn bluebtn">新增</span>
                          </dd>

						{{else type=="11"}}
						<dd>项目带过来的数据</dd>

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
	<div class="h_btnbox"><span class="h_edit_btn" attr-id="\${code}">编辑</span></div>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}                    
                    {{if sign=="3"}}
						{{each(i,childList) childList}}
							<div class="mb_24 clearfix">
                      <dl class="clearfix">
                        <dt data-type="\${type}" data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>

						{{if type=="5"}}                        
						<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
						<dd>备注</dd>

						{{else type=="2"}}
                        <dd class="field" data-value="\${value}" data-title-id="\${id}"  data-code="\${code}">未选择</dd>

						{{else type=="3"}}
                        {{each(i,valueList) valueList}}
                        <dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
						{{/each}}

						{{else type=="6"}}
						{{each(i,valueList) valueList}}
                        <dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
						{{/each}}

						{{else type=="7"}}
                 			<dt class="fl_none">除去非主营业务外，运营数据曲线变化（细分项目、拆分到年度、月度、周、日）：</dt>
                    		 <dd class="fl_none">
                            	<img src="img/loginbg.gif" alt="">
                            	<img src="img/loginbg.gif" alt="">
                          	</dd>

						{{else type=="8"}}
						<dt class="fl_none" data-type="\${type}">\${name}</dt>
						<dd class="fl_none field" data-title-id="\${id}">未填写</dd>

						{{else type=="4"}}
						{{each(i,valueList) valueList}}
                        <dd>未选择</dd>
						{{/each}}

						{{else type=="11"}}
                        <dd>项目带过来的数据</dd>

						{{else type=="1"}}
                        <dd class="field" data-title-id="\${id}">未填写</dd>
						{{/if}}                      
						</dl>		
                    </div>
						{{/each}}

					{{else}}
					<div class="mb_24 clearfix">
                      <dl class="clearfix">
                        <dt data-type="\${type}" data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
						{{if type=="5"}}                        
						<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
						<dd>备注</dd>

						{{else type=="2"}}
                        <dd class="field" data-value="\${value}" data-title-id="\${id}"  data-code="\${code}">未选择</dd>

						{{else type=="3"}}
                        {{each(i,valueList) valueList}}
                        <dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
						{{/each}}

						{{else type=="6"}}
						{{each(i,valueList) valueList}}
                        <dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
						{{/each}}

						{{else type=="7"}}
                 			<dt class="fl_none">除去非主营业务外，运营数据曲线变化（细分项目、拆分到年度、月度、周、日）：</dt>
                    		 <dd class="fl_none">
                            	<img src="img/loginbg.gif" alt="">
                            	<img src="img/loginbg.gif" alt="">
                          	</dd>

						{{else type=="8"}}
						<dt class="fl_none" data-type="\${type}">\${name}</dt>
						<dd class="fl_none field" data-title-id="\${id}">未填写</dd>

						{{else type=="4"}}
						{{each(i,valueList) valueList}}
                        <dd>未选择</dd>
						{{/each}}

						{{else type=="11"}}
                        <dd>项目带过来的数据</dd>

						{{else type=="1"}}
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
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO6", null,
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
				} else {

				}
		}) 
	});
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		event.stopPropagation();
	});
	//通用保存
	$('div').delegate(".h_save_btn","click",function(event){
		var id_code = $(this).attr('attr-save');
		event.stopPropagation();
		var sec = $(this).closest('form');
		var fields = sec.find("input[type='text'],input:checked,textarea");
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
			if(type==2 || type==3 || type==4)
			{
				infoMode.value = field.val()
			}
			else if(type==1 || type==8)
			{
				infoMode.remark1 = field.val().replace(/\n|\r\n/g,"<br>");
			}
			infoModeList.push(infoMode);
		});
		data.infoModeList = infoModeList;
		//验证插件调用
		if(!$("#b_"+id_code).validate().form())
		{
			return ;
		}
		if( beforeSubmit()){
		sendPostRequestByJsonObj(
			platformUrl.saveOrUpdateInfo , 
			data,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					layer.msg('保存成功');
					$('#'+id_code).show();
					$('#b_'+id_code).remove();
					var pid=$('#a_'+id_code).attr("data-section-id");
					 setDate(pid,true);	
				} else {

				}
		}) 
		}

	});

</script>
               
</body>


</html>
