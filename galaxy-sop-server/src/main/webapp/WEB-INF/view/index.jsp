<%@ page language="java" pageEncoding="UTF-8"
import="com.galaxyinternet.framework.core.oss.OSSConstant"
%>
<% 
	String path = request.getContextPath(); 
String endpoint = (String)application.getAttribute(OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
Map<String, Object> endpointMap = new com.google.gson.Gson().fromJson(endpoint,new TypeToken<Map<String, Object>>() {}.getType()); 
String reportEndpoint = String.valueOf(endpointMap.get("galaxy.project.report.endpoint"));
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
                                    <th>#</th>
                                    <th>优先级</th>
                                    <th>任务类型</th>
                                    <th>任务名称</th>
                                    <th>任务状态</th>
                                    <th>所属项目</th>
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
                    	<div id="histogram" class="histogram"></div>
                    </dd>
                    <dd class="clearfix">
                    	<a href="<%=reportEndpoint %>/galaxy/report/dataBriefing" class="more null">more</a>
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
		var obj ={url:Constants.reportEndpointURL+"/galaxy/report/projectprogress"};
		obj.contentType="application/json";
		ajaxCallback(obj,function(data){
			console.log(data);
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
			console.log(re);
			console.log(categories);
			var chart = new Highcharts.Chart(chartOptions);
		});
	}
});
//通用ajax数据回调
function ajaxCallback(obj,callback){
	$.ajax({
		url:obj.url,
		dataType:obj.dataType||'json',
		//contentType: obj.contentType ||"application/x-www-form-urlencoded; charset=UTF-8",
		contentType:obj.contentType ||"application/json",
		type:obj.type||'POST',
		data: JSON.stringify( obj.data||{} ),
		async : false,
		beforeSend:function(xhr){
			if(sessionId){
				xhr.setRequestHeader("sessionId",sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId",userId);
			}
		},
		error : function(request) {
			//alert("connetion error");
		},
		success:function(data){
			if(data.hasOwnProperty("result")&&data.result.errorCode=="3"){
				location.href = platformUrl.toLoginPage;
			}
			callback.call(this,data);
		}
	});
}

var chartOptions={
	chart: {
		renderTo :'histogram',
        type: 'column'
    },
    title: {
        text: ''
    },
    credits: {
        enabled:false
    },
    exporting: {
    	enabled:false
    },
    xAxis: {
        //categories: ['朱玟','牟敏','关屿','赵广智','陈丛翀','王飞韵','蔡燕','王晓宇'],
    	labels: {
            rotation: -45,
            align: 'right',
            style: {
                fontSize: '13px',
                fontFamily: 'Verdana, sans-serif'
            },
        }
    },
    yAxis: {
        min: 0,
        title: {
            text:''
        }
    },
    legend: {
        enabled: false
    },
    tooltip: {
    	/*useHTML: true,
    	formatter: function(){
    		return this.point.x +'<br/>项目数:'+ this.point.y +'个';
    	}*/
    	enabled:true
    },
    series: [{
        name: '项目数',
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
            /*formatter:function(){
     			return this.point.y;
			},*/
        }
    }]
};
</script>
</html>

