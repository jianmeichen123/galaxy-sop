<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]--> 
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include> 
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/> 
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>  
</head>

<body >
<div class="pagebox">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	
		<div class="ritmin"> 
			<p class='searchNumber'>共搜索到<span>24</span>条结果</p>
			
			<div class='search-top'>
				<ul class="clearfix to-task-tips">
					<li class="bottomColor">创投项目<span>（12）</span></li>
					<li>外部项目<span>（6）</span></li>
					<li>咨询<span>（6）</span></li>
				</ul>
				
			</div>
			<div data-id="tab-block">
	    		<div id='tab-content' data-id="tab-content">
			    	<div class='tabtxt'>
			    	
			    	</div>
		    	</div>
	    	</div>
			<!-- <div class='one'>
			<table id='searchTable'  class='createProject' style="width:100%;">
				<thead>
					<th>项目</th>
					<th>融资状态</th>
					<th>项目进度</th>
					<th>投资经理</th>
					<th>项目状态</th>
					<th>最后编辑时间</th>
				</thead> 
				 <tbody>
					<tr>
						<td>蚂蚁优厂</td>
						<td>天使</td>
						<td>内部评审</td>
						<td>陆亲<span>|</span>互联网旅游</td>
						<td>跟进中</td>
						<td>2017-08-21</td>
						
					</tr>
					<tr>
						<td>蚂蚁优厂</td>
						<td>天使</td>
						<td>内部评审</td>
						<td>陆亲<span>|</span>互联网旅游</td>
						<td>跟进中</td>
						<td>2017-08-21</td>
						
					</tr>
					<tr>
						<td>蚂蚁优厂</td>
						<td>天使</td>
						<td>内部评审</td>
						<td>陆亲<span>|</span>互联网旅游</td>
						<td>跟进中</td>
						<td>2017-08-21</td>
						
					</tr>
					<tr>
						<td>蚂蚁优厂</td>
						<td>天使</td>
						<td>内部评审</td>
						<td>陆亲<span>|</span>互联网旅游</td>
						<td>跟进中</td>
						<td>2017-08-21</td>
						
					</tr>
					
				
				</tbody>
			</table> 
			</div>
			<div class='two'>
				<table class='outerProject'>
					<tr>
						<td>
							<div class='tdContent'>
								<div class='fl leftPic'>
									<img/>
								</div>
								<div class='rightContent'>
									<h3>花心区块链研究院<span>A轮</span></h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm picEmOne'><em></em>江苏</span>
										<span class='picEm picEmTwo'><em></em>企业服务</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class='tdContent'>
								<div class='fl leftPic'>
									<img/>
								</div>
								<div class='rightContent'>
									<h3>花心区块链研究院<span>A轮</span></h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm'><em></em>江苏</span>
										<span class='picEm picEmTwo'><em></em>企业服务</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class='tdContent'>
								<div class='fl leftPic'>
									<img/>
								</div>
								<div class='rightContent'>
									<h3>花心区块链研究院<span>A轮</span></h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm'><em></em>江苏</span>
										<span class='picEm picEmTwo'><em></em>企业服务</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class='tdContent'>
								<div class='fl leftPic'>
									<img/>
								</div>
								<div class='rightContent'>
									<h3>花心区块链研究院<span>A轮</span></h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm'><em></em>江苏</span>
										<span class='picEm picEmTwo'><em></em>企业服务</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class='tdContent'>
								<div class='fl leftPic'>
									<img/>
								</div>
								<div class='rightContent'>
									<h3>花心区块链研究院<span>A轮</span></h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm'><em></em>江苏</span>
										<span class='picEm picEmTwo'><em></em>企业服务</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr>
				
				</table>
			
			
			
			</div>
			咨询
			<div class='three'>
				<table class='outerProject newsProject'>
					<tr>
						<td>
							<div class='tdContent'>
								<div class='fl leftPic'>
									<img/>
								</div>
								<div class='rightContent'>
									<h3>花心区块链研究院</h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm picEmOne'>1小时前</span>
										<span class='picEm picEmTwo'>来自投资中国</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class='tdContent'>
								<div class='fl leftPic'>
									<img/>
								</div>
								<div class='rightContent'>
									<h3>花心区块链研究院</h3>
									<p class='outerProjectTitle'>简介:引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮培养高素质人才的摇篮引领全国区块链行业，培养高素质人才的摇篮</p>
									<p>
										<span class='picEm picEmOne'>1小时前</span>
										<span class='picEm picEmTwo'>来自投资中国</span>
									</p>	
								</div>							
							</div>
						</td>
					</tr>
					
				
				</table>
			
			
			
			</div> -->
		
		</div> 
		
</div>

</body> 
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
	  
</html>
<script>
$(function(){
	//导航
	createMenus(5);
 $('.selectpicker').selectpicker({
  style: 'btn-info',
  size: 4
});
/* 导航切换 */
 $('.search-top ul li').click(function(){
	var _this = $(this);
	_this.addClass('bottomColor').siblings().removeClass('bottomColor');
 });

});
$('.to-task-tips').tabLazyChange({
	defaultnum:0,
	onchangeSuccess : function(index){
		switch(index){
			case 0 : initTabventrueProject();break;
			case 1 :initTabOuterProject();break;
			case 2 :initTabConsultProject();break;
		}
	}
})	
	//页面请求地址
	function initTabventrueProject(){
		$.getTabHtml({
			url : platformUrl.toVentrueProject;
		});
	}
	function initTabOuterProject(){
		console.log(platformUrl.toTaskLog)
		$.getTabHtml({
			url : platformUrl.toOuterProject;
		});
	}
	function initTabConsultProject(){
		console.log(platformUrl.toConsultProject)
		$.getTabHtml({
			url : platformUrl.toConsultProject;
		});
	}




</script>
