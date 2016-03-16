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
<!-- jsp文件头和头部 -->
<jsp:include page="./common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path%>/js/sopUserSchedule.js" type="text/javascript"></script>
<script src="<%=path%>/js/index.js" type="text/javascript"></script>
<link href="<%=path %>/css/calendar.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/calendarnew.js"></script>
<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
</head>

<body>

<jsp:include page="./common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--右侧-->
    <div class="rit">
        <!--时间-->
        <div class="top"  onclick="shecudle();">
        	<b class="sj ico null">三角</b>
            <div class="tody ico">
            	<p class="time"></p>
                <p class="date"></p>
            </div>
            <div id="top">
            <!-- 
            <a href="javascript:;" class="link"><b class="b1 null">点</b>明天，要和创业团队见面</a>
            <a href="javascript:;" class="link"><b class="b2 null">点</b>后天，要和夹克的虾团队见面</a>
            <a href="javascript:;" class="link"><b class="b3 null">点</b>5天后，买飞机票</a>
             -->
            </div>
            <div class="morebox"><a href="javascript:;" class="more null">more</a></div>
        </div>
        <!--立项排期会-->
        <dl>
        	<dt>立项会排期</dt>
            <dd>
            	<table id="projectMeeting" width="100%" cellspacing="0" cellpadding="0" class="index">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>项目名称</th>
                            <th>上次过会时间</th>
                            <th>过会次数</th>
                        </tr>
                    </thead>
                    <tbody id="tlbody">
                    </tbody>
                </table>
            </dd>
            <dd class="clearfix">
           
<!--              <a href="/html/projectMeeting.html" data-btn="project" class="more null">more</a>
 -->            
              <a href="/sop/html/projectMeeting.html" data-btn="project" class="more null">more</a>
            </dd>
        </dl>
        <!--投决会排期-->
        <dl class="tjh_block">
        	<dt>投决会排期</dt>
            <dd>
            	<table width="100%" cellspacing="0" cellpadding="0" >
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>项目名称</th>
                            <th>上次过会时间</th>
                            <th>过会次数</th>
                        </tr>
                    </thead>
                    <tbody id="tbody">
                        
                    </tbody>
                </table>
            </dd>
            <dd class="clearfix">
                <a href="/sop/html/voteMeeting.html" data-btn="vote"  class="more null">more</a>
<!--                 <a href="/html/voteMeeting.html" data-btn="vote"  class="more null">more</a> -->
            </dd>
        </dl>
    </div>
    
    
	<jsp:include page="./common/menu.jsp" flush="true"></jsp:include>
	
	
    <!--中部内容-->
    <div class="min">
        <!--表格列表-->
        <div class="tablist clearfix">
        	<!--左侧列表-->
            <div class="l">
            	<dl>
                	<dt><h3 class="ico t1">待办任务</h3></dt>
                    <dd>
                    	<table width="100%" cellspacing="0"  cellpadding="0">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>紧急</th>
                                    <th>任务类型</th>
                                    <th>项目名称</th>
                                    <th>任务状态</th>
                                    <th>最后期限</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="sopStak">
                               
                            </tbody>
                        </table>
                    </dd>
                    <dd class="clearfix">
                    	<a href="<%=path %>/galaxy/soptask" class="more null">more</a>
                    </dd>
                </dl>
                <dl>
                	<dt><h3 class="ico t2">创意库</h3></dt>
                    <dd>
                    	<table width="100%" cellspacing="0"  cellpadding="0">
                            <thead>
                                <tr>
                                    <th>创意编号</th>
                                    <th>创意名称</th>
                                    <th>所属行业</th>
                                    <th>级别</th>
                                    <th>创建时间</th>
                                    <th>最后编辑时间</th>
                                    <th>创建人</th>
                                    <th>状态</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>cy001</td>
                                    <td>美好生活</td>
                                    <td>o2o电商</td>
                                    <td>高</td>
                                    <td>2016-1-29</td>
                                    <td>2016-1-21</td>
                                    <td>徐茂栋</td>
                                    <td><span class="blue">待认领</span></td>
                                </tr>
                                <tr>
                                    <td>cy001</td>
                                    <td>美好生活</td>
                                    <td>o2o电商</td>
                                    <td>高</td>
                                    <td>2016-1-29</td>
                                    <td>2016-1-21</td>
                                    <td>徐茂栋</td>
                                    <td><span class="blue">待认领</span></td>
                                </tr>
                                <tr>
                                    <td>cy001</td>
                                    <td>美好生活</td>
                                    <td>o2o电商</td>
                                    <td>高</td>
                                    <td>2016-1-29</td>
                                    <td>2016-1-21</td>
                                    <td>徐茂栋</td>
                                    <td><span class="blue">待认领</span></td>
                                </tr>
                            </tbody>
                        </table>
                    </dd>
                    <dd class="clearfix">
                    	<a href="javascript:;" class="more null">more</a>
                    </dd>
                </dl>
                <dl>
                	<dt><h3 class="ico t3">数据报表</h3></dt>
                    <dd class="zzbox">
                    	<div class="histogram">
                        	<!-- 
                            <ul>
                                <li data-pace="20"><span>全部</span></li>
                                <li data-pace="30"><span>接触<br>访谈</span></li>
                                <li data-pace="50"><span>内部<br>评审</span></li>
                                <li data-pace="10"><span>立项会</span></li>
                                <li data-pace="80"><span>投资<br>意向书</span></li>
                                <li data-pace="140"><span>尽职<br>调查</span></li>
                                <li data-pace="73"><span>投资<br>决策会</span></li>
                                <li data-pace="23"><span>投资<br>协议</span></li>
                                <li data-pace="1"><span>投后<br>运营</span></li>
                            </ul>
                            <ol class="clearfix">
                            	<li><span>140</span></li>
                                <li><span>120</span></li>
                                <li><span>100</span></li>
                                <li><span>80</span></li>
                                <li><span>60</span></li>
                                <li><span>40</span></li>
                                <li><span>20</span></li>
                                <li><span>0</span></li>
                            </ol> -->
                       </div>
                    </dd>
                    <dd class="clearfix">
                    	<a href="javascript:;" class="more null">more</a>
                    </dd>
                </dl>
            </div>
            <!--右侧列表-->
            <div class="r">
            	<dl class="r_news">
                	<dt><h3 class="ico t4">消息提醒</h3></dt>
                    <dd>
                    	<table width="100%" cellspacing="0"  cellpadding="0">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>更新时间</th>
                                    <th>办理人</th>
                                    <th>消息内容</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </dd>
                    <dd class="clearfix">
                    	<a href="<%=path %>/galaxy/operationMessage/index" class="more null">more</a>
                    </dd>
                </dl>
                <dl>
                	<dt><h3 class="ico t5">档案库</h3></dt>
                    <dd>
                    	<table width="100%" cellspacing="0"  cellpadding="0">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>文档名称</th>
                                    <th>所属部门</th>
                                    <th>文档类型</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>业务尽职调查清单</td>
                                    <td>投资事业部</td>
                                    <td>文档</td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>业务尽职调查清单</td>
                                    <td>投资事业部</td>
                                    <td>文档</td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>业务尽职调查清单</td>
                                    <td>投资事业部</td>
                                    <td>文档</td>
                                </tr>
                            </tbody>
                        </table>
                    </dd>
                    <dd class="clearfix">
                    	<a href="javascript:;" class="more null">more</a>
                    </dd>
                </dl>
                <dl  class="tool_radius">
                	<dt><h3 class="ico t6">常用工具</h3></dt>
                    <dd class="tool">
                    	<a href="javascript:;" class="light_gray"><b class="b1 ico null">ico</b>通讯录</a>
                        <a href="javascript:;" class="light_gray"><b class="b2 ico null">ico</b>估值计算</a>
                        <a href="javascript:;"><b class="b3 ico null">ico</b>新增会议</a>
                        <a href="javascript:;"><b class="b4 ico null">ico</b>新增访谈</a>
                        <a href="javascript:;" class="light_gray"><b class="b5 ico null">ico</b>发邮件</a>
                        <a href="javascript:;" class="add ico">&nbsp;</a>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
 
</div>

<jsp:include page="./common/footer.jsp" flush="true"></jsp:include>
<script type="text/javascript">
	$(function(){		
		top5ProjectMeeting();
		ProjectVoteWill();
		selectSopTask();
		createMenus(1);
		top5Message();
		loadAjaxSopUserSchedule(platformUrl.sheduleMoreThree); 
	});
</script>
<script>
$(function(){
	load_data_chart();
	function load_data_chart(){
		var obj ={url:"http://fx.qa.galaxyinternet.com/report/galaxy/report/projectprogress"};
		obj.contentType="application/json";
		ajaxCallback(obj,function(data){
			var result = data.result;
			var mapList = data.mapList;
			if(result.status=='ERROR'){
				$.popup(100,'消息',result.message);
				return false;
			}
			var re = [];
			var categories = [];
			for(var i=0;i<mapList.length;i++){
				re.push( mapList[i].c);
				categories.push(mapList[i].name);
			}
			chartOptions.series[0].data = re;
			chartOptions.xAxis.categories = categories;
			chartOptions.xAxis.labels.useHTML = true;
			var chart = new Highcharts.Chart(chartOptions);
		});
	}
	var chartOptions={
		chart: {
			renderTo :'histogram',
	        type: 'column'
	        //margin: [ 50, 50, 100, 80]
	    },
	    title: {
	        text: ''
	    },
	    //去除版权
	    credits: {
	        enabled:false
	    },
	    //去除右上角导出图标
	    exporting: {
	    	enabled:false
	    },
	    xAxis: {
	        //categories: ['朱玟','牟敏','关屿','赵广智','陈丛翀','王飞韵','蔡燕','王晓宇'],
	    	labels: {
	    		useHTML:true,
	    		/*formatter:function(){
	    			var temp = new Array();
	    			temp = this.value.split('-');
	    			return '<a href="javascript:;" onclick="showDetails("'+temp[1]+'");">' + temp[0] + '</a>';
	    		},*/
	            rotation: 0,
	            align: 'center',
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            },
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            //text: '项目数 (个)'
	            text:''
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    tooltip: {
	    	useHTML: true,
	    	formatter: function(){
	    		return this.x +'<br/>项目数:'+ this.y +'个';
	    	}
	    },
	    series: [{
	        name: '项目状态分布',
	        //data: [8,5,4,3,3,2,2,2,2],
	        dataLabels: {
	            enabled: true,
	            rotation: 0,
	            color: '#FFFFFF',
	            align: 'center',
	            x: 0,
	            y: 25,
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif',
	                textShadow: '0 0 3px black'
	            },
	            formatter:function(){
	     			return this.point.y;
				},
	        }
	    }]
	};
});
</script>
</html>

