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
<script src="<%=path%>/js/hologram/hologram_common.js"></script>
<script src="<%=path%>/js/axure_ext.js"></script>
<script src="<%=path%>/js/hologram/team.js"></script>
</head>
<body>
<ul class="h_navbar clearfix">
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基本<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
                </ul>


 <div class="h_tab_con"></div>
			
			<script id="ifelse" type="text/x-jquery-tmpl">
			<div class="h radius"">
				<div class="h_look clearfix">
                    <div class="h_btnbox"><span class="h_edit_btn" id="\${code}">编辑</span></div>
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
                        <dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>

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
						<dd class="fl_none">未填写</dd>

						{{else type=="4"}}
						{{each(i,valueList) valueList}}
                        <dd>未选择</dd>
						{{/each}}

						{{else type=="11"}}
                        <dd>项目带过来的数据</dd>

						{{else type=="1"}}
                        <dd>未填写</dd>
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
                        <dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>

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
						<dd class="fl_none">未填写</dd>

						{{else type=="4"}}
						{{each(i,valueList) valueList}}
                        <dd>未选择</dd>
						{{/each}}

						{{else type=="11"}}
                        <dd>项目带过来的数据</dd>

						{{else type=="1"}}
                        <dd>未填写</dd>
						{{/if}}                      
						</dl>		
                    </div>

					{{/if}}
					{{/each}}
                  </div>

				<div class="h_edit">
                    <div class="h_btnbox"><span class="h_save_btn">保存</span><span class="h_cancel_btn" data-on="h_cancel">取消</span></div>
					<div class="h_title">\${name}</div>					
					{{each(i,childList) childList}}  
 
                 	{{if sign=="3"}}
						{{each(i,childList) childList}}
						<div class="mb_16">
                       <dl class="h_edit_txt clearfix">
						<dt data-type="\${type}"  data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
						{{if type=="1"}}
                        <dd><input type="text"></dd>

						{{else type=="2"}}
						<dd>
						<ul class="h_radios clearfix">
							{{each(i,valueList) valueList}}
                            <li><input type="radio" data-value="\${value}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
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
                            <li><input type="radio" data-value="\${value}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
							{{/each}}
                          </ul>
						</dd>
						<dd class="fl_none">
							<textarea class="textarea_h" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}"></textarea>
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
							<textarea class="textarea_h" data-titleId="\${titleId}"data-value="\${value}"data-parentId="\${parentId}"></textarea>
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
							<span class="pubbtn bluebtn" id="test">新增</span>
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
                        <dd><input type="text"></dd>

						{{else type=="2"}}
						<dd>
						<ul class="h_radios clearfix">
							{{each(i,valueList) valueList}}
                            <li><input type="radio" data-value="\${value}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
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
                            <li><input type="radio" data-value="\${value}" data-id="\${id}" data-code="\${code}" placeholder="\${placeholder}"/>\${name}</li>
							{{/each}}
                          </ul>
						</dd>
						<dd class="fl_none">
							<textarea class="textarea_h" data-titleId="\${titleId}" data-value="\${value}" data-parentId="\${parentId}" placeholder="\${placeholder}"></textarea>
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
							<textarea class="textarea_h" data-titleId="\${titleId}" data-value="\${value}" data-parentId="\${parentId}" placeholder="\${placeholder}"></textarea>
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
                            <a href="<%=path%>/html/team_compile.html" data-btn="addmen" onclick="addmen_fun()" data-name="添加工作经历"><span class="pubbtn bluebtn" id="add_member">新增</span></a>


                          </dd>

						{{else type=="11"}}
						<dd>项目带过来的数据</dd>

						{{/if}}
                      </dl>
                    </div>
				

					{{/if}}
					
					{{/each}}
                    <div class="h_edit_btnbox clearfix">
                      <span class="pubbtn bluebtn fl" data-on="save">保存</span>
                      <span class="pubbtn fffbtn fl" data-name="basic" data-on="h_cancel">取消</span>
                    </div>
                  </div>

</div>
			</script>
			
			
                <script>
                $(function(){
                	  sendGetRequest(platformUrl.queryAllTitleValues + "NO3_1", null,function(data) {
      					console.log(data);
      					var result = data.result.status;
      					if (result == 'OK') {
      						var entity = data.entity;
      						//$("#ifelse_look").tmpl(entity).appendTo('#div_block1');
      						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
      					} else {

      					}
      				});
                	  sendGetRequest(platformUrl.queryAllTitleValues + "NO3_2", null,function(data) {
             					console.log(data);
             					var result = data.result.status;
             					if (result == 'OK') {
             						var entity = data.entity;
             						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
             					} else {

             					}
             				});
                	 sendGetRequest(platformUrl.queryAllTitleValues + "NO3_3", null,function(data) {
      					console.log(data);
      					var result = data.result.status;
      					if (result == 'OK') {
      						var entity = data.entity;
      						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
      					} else {

      					}
      				});
                	 sendGetRequest(platformUrl.queryAllTitleValues + "NO3_4", null,function(data) {
       					console.log(data);
       					var result = data.result.status;
       					if (result == 'OK') {
       						var entity = data.entity;
       						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
       					} else {

       					}
       				});
                	 sendGetRequest(platformUrl.queryAllTitleValues + "NO3_5", null,function(data) {
        					console.log(data);
        					var result = data.result.status;
        					if (result == 'OK') {
        						var entity = data.entity;
        						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
        					} else {

        					}
        				});
                	  sendGetRequest(platformUrl.queryAllTitleValues + "NO3_6", null,function(data) {
        					console.log(data);
        					var result = data.result.status;
        					if (result == 'OK') {
        						var entity = data.entity;
        						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
        					} else {

        					}
        				});
                	     sendGetRequest(platformUrl.queryAllTitleValues + "NO3_7", null,function(data) {
     					console.log(data);
     					var result = data.result.status;
     					if (result == 'OK') {
     						var entity = data.entity;
     						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
     					} else {

     					}
     				});
                	   sendGetRequest(platformUrl.queryAllTitleValues + "NO3_8", null,function(data) {
     					console.log(data);
     					var result = data.result.status;
     					if (result == 'OK') {
     						var entity = data.entity;
     						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
     					} else {

     					}
     				}); 
                	  sendGetRequest(platformUrl.queryAllTitleValues + "NO3_9", null,function(data) {
                	    	console.log("11")
     					console.log(data);
     					var result = data.result.status;
     					if (result == 'OK') {
     						var entity = data.entity;
     						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
     					} else {

     					}
     				}); 
                	    sendGetRequest(platformUrl.queryAllTitleValues + "NO3_10", null,function(data) {
     					console.log(data);
     					var result = data.result.status;
     					if (result == 'OK') {
     						var entity = data.entity;
     						$("#ifelse").tmpl(entity).appendTo('.h_tab_con');
     					} else {

     					}
     				}); 
                	 
     				 editOpen();   //编辑按钮点击时间
                })

                function addmen_fun(){
                    	var $self = $(this);
                    		var _url = $self.attr("href");
                    		$.getHtml({
                    			url:_url,//模版请求地址
                    			data:"",//传递参数
                    			okback:function(){}//模版反回成功执行
                    		});
                    		return false;
                }
       			</script>
               
</body>
</html>
