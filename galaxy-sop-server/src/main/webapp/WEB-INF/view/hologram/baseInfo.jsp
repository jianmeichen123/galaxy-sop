<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
</head>

<body>

<ul class="h_navbar clearfix">
	<li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('0')" >基本<br/>信息</li>
	<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
	<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
	<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
	<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
	<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
	<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
	<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
	<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
</ul>
<div id="tab-content">
		<div class="tabtxt" id="page_all">
		<!--tab-->

			
			<!--tab end-->
		</div>
	</div>

<!--点击编辑例子 -->
<script id="ifelse" type="text/x-jquery-tmpl">
<div class="h_edit" id="b_\${code}">
	<div class="h_btnbox">
		<span class="h_save_btn">保存</span><span class="h_cancel_btn"
			data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>
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
</div>
{{/each}}
</script>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script type="text/javascript">
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO1", null,
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

	<ul class="h_navbar clearfix">
		<li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('0')">基本<br />信息 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br />数据 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br />策略 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br />估值 </li>
	</ul>


	<div id="tab-content">
		<div class="tabtxt" id="page_all"> 
		
			<div class="h radius" id="NO1_1">
			</div>
			
			<div class="h radius" id="NO1_2">
			</div>
		</div>
	</div>








<script>
	
	//1:文本、2:单选、3:复选、4:级联选择、5:单选带备注、6:复选带备注、7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据
	
	// 1:文本
	function type_1_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd>未填写</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			hresult = "<dd data-rid="+results[0].id+" >"+results[0].contentDescribe1+"</dd>";
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	// 2:单选
	function type_2_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd>未选择</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			hresult = "<dd data-rid="+results[0].id+" >"+results[0].valueName+"</dd>";
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	// 3:复选、4:级联选择
	function type_3_4_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd>未选择</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			$.each(results,function(i,o){
				hresult +=  "<dd data-rid="+this.id+" >"+this.valueName+"</dd>";
			});
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	
	//5:单选带备注
	function type_5_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		
		var hresult_1 = "<dd>未选择</dd>";
		var hresult_2 = "<dd>未填写</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			if(results[0].valueName){
				hresult_1 = "<dd data-rid="+results[0].id+" >"+results[0].valueName+"</dd>";
			}
			if(results[0].contentDescribe1){
				hresult_2 = "<dd >"+results[0].contentDescribe1+"</dd>";
			}
		}

		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult_1 + hresult_2 + "</div>";
	}
	
	
	
	//6:复选带备注
	function type_6_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		
		var hresult_1 = "<dd>未选择</dd>";
		var hresult_2 = "<dd>未填写</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			
			var hasC = false;
			var cr = "";
			$.each(results,function(i,o){
				if(this.valueName){
					hasC = true;
					cr +=  "<dd >"+this.valueName+"</dd>";
				}
			});
			if(hasC == true){
				hresult_1 = cr;
			}
			
			if(results[0].contentDescribe1){
				hresult_2 = "<dd >"+results[0].contentDescribe1+"</dd>";
			}
		}

		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult_1 + hresult_2 + "</div>";
	}
	
	
	// 7:附件
	function type_7_html(title){
		
		return  "<dt class=\"fl_none\"> 除去非主营业务外，运营数据曲线变化（细分项目、拆分到年度、月度、周、日）：</dt>" +
				"<dd class=\"fl_none\">" +
					"<img src=\"img/loginbg.gif\" alt=''>" +
					"<img src=\"img/loginbg.gif\" alt=''>" +
				"</dd>";
	}
	
	
	
	// 8:文本域
	function type_8_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd>未填写</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			hresult = "<dd data-rid="+results[0].id+" class=\"fl_none\" >"+results[0].contentDescribe1+"</dd>";
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	
	// 11:文本
	function type_11_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd></dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			hresult = "<dd data-rid="+results[0].id+" >"+results[0].contentDescribe1+"</dd>";
		}else{
			console.log("projectInfo: " + projectInfo);
			switch (title.code) {
	            case "NO1_1_1":  //项目编号
	            	hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
	                break;
	            case "NO1_1_2":  //项目负责人
	            	hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
	                break;
	            case "NO1_1_3":   //项目合伙人
	            	hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
	                break;
	            case "NO1_1_4":   //隶属事业部
	            	hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
	                break;
	            default:
	                break;
	        }
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	
	
	
	function switchType(title){
		var html = "";
		switch (title.type) {
	        case 1:  
	        	html += type_1_html(title);
	            break;
	        case 2:  
	        	html += type_2_html(title);
	            break;
	        case 3:   
	        	html += type_3_4_html(title);
	            break;
	        case 4:   
	        	html += type_3_4_html(title);
	            break;
	        case 5:  
	        	html += type_5_html(title);
	            break;
	        case 6:  
	        	html += type_6_html(title);
	            break;
	        case 7:   
	        	html += type_7_html(title);
	            break;
	        case 8:   
	        	html += type_8_html(title);
	            break;
	        case 9:  
	        	//html += type_9_html(title);
	            break;
	        case 10:  
	        	//html += type_10_html(title);
	            break;
	        case 11:   
	        	html += type_11_html(title);
	            break;
	        default:
	            break;
	    }
		return html;
	}
	
	
	function toGetShowHtml(title){
		var tilelist = title.childList;
		var html = "";
		$.each(tilelist,function(i,o){
			if(this.sign  && this.sign == 3){
				sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/" + this.code, null, function(data) {
					var result = data.result.status;
					if (result == 'OK') {
						var sign_title = data.entity;
						html += toGetShowHtml(sign_title);
					}
				});
			}else{
				html += switchType(this);
			}
		});
		return html
	}
	
	
	//页面显示
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/NO1_1", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			console.log(entity);
			var html = toGetShowHtml(entity);
			$("#NO1_1").html(html);
		}
	});
	
	//页面显示
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/NO1_2", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			console.log(entity);
			var html = toGetShowHtml(entity);
			$("#NO1_2").html(html);
		}
	});
	
	
	
</script>


</body>


</html>
