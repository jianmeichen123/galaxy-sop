<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="com.galaxyinternet.bo.SheduleCommon" %>
<%@ page language="java" import="com.galaxyinternet.model.soptask.SopUserSchedule" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<div class="title_bj">日程列表</div>
<div class="schedule1tc margin_45">
    <div class="bottom clearfix">
        <div class="bottom_l none">
            <% 
              String contentstr="";
              String timestr="";
              Integer itemType=null;
              String itemOrder="";
              Long id=null;
              
              List<SheduleCommon> nodelist = (List)request.getAttribute("sheduleList");
              for(int i=0;i<nodelist.size();i++){
            	SheduleCommon common=nodelist.get(i);
		    	String month=common.getMonths();
		    	Integer count=common.getCount();
		    	/**初始化第一页日程详情**/
		    	contentstr = nodelist.get(0).getList().get(0).getContent();
	    		String strtime[] = nodelist.get(0).getList().get(0).getItemDate().toString().split(" ");
	    		timestr = strtime[0];
	    		
	    		itemType = nodelist.get(0).getList().get(0).getItemType();
	    		itemOrder = nodelist.get(0).getList().get(0).getItemOrder();
	    		id = nodelist.get(0).getList().get(0).getId();
	    
		    	%>
		    	 <dl>
		    	 <dt class="nav_list" ><%=month %><span>(</span><span><%=count %></span><span>)</span></dt>
		    	 <% if(i==0){%><dd class="on tips"> <%} %>
		    	 <% if(i!=0){%><dd class="tips"> <%} %>
		    	       <div>
		    	          <%
				    	  for(int j=0;j<common.getList().size();j++){
				    		  SopUserSchedule shcedule=common.getList().get(j);
				    		  String str[] = shcedule.getItemDate().toString().split(" ");
				    	  %>  
				    		  <dl id="currentShedule" class="nav" data-tab='nav' onclick="getShedule(<%=shcedule.getId()%>)">
			                            <dt><span><%=shcedule.getContent() %></span>
			                            <label class="red">
			                            <% if("1".equals(shcedule.getItemOrder())){%>紧急<%} %>
			                            <% if("0".equals(shcedule.getItemOrder())){%>正常<%} %>
			                            </label>
			                            </dt>
			                            <dd><%=str[0] %>
			                            <label>
			                            <% if(shcedule.getItemType() == 0){%>工作<%} %>
			                            <% if(shcedule.getItemType() == 1){%>个人<%} %>
			                            </label></dd>
			                  </dl> 
				    	  <%}
		    	          %>
                       </div>
                      <div class="con"></div>
		    	 </dd>
		    	 </dl>
		    <%}%>
        </div>
      
        <div id="editShedule" class="bottom_r"  data-tab='con'> 
           <form action="" id="shedule_form" method="post">   
            <input type="hidden" id="id" name="id" value=""/>
            <dl class="fmdl clearfix">
                <dt>处理日期：</dt>
                <dd class="clearfix">
                    <input type="text" id="itemDate" class="datepicker txt time" name="itemDateStr" readonly value="" valType="required" msg="<font color=red>*</font>处理日期不能为空"/>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                <dt>事项类型：</dt>
                <dd class="clearfix">
                    <label for=""><input type="radio" id="itemType0" name="itemType" value="0" checked/>工作</label>
                    <label for=""><input type="radio" id="itemType1" name="itemType" value="1"/>个人</label>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                <dt>优先级别：</dt>
                <dd class="clearfix">
                    <label for=""><input type="radio" id="itemOrder0" name="itemOrder" value="0" checked/>正常</label>
                    <label for=""><input type="radio" id="itemOrder1" name="itemOrder" value="1"/>紧急</label>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                <dt>事项内容：</dt>
                <dd>
                    <textarea id="content" name="content" valType="OTHER" regString="^.{1,200}$" msg="<font color=red>*</font>事项内容不能超过200字符"></textarea>
                </dd>
            </dl>
            <div class="btnbox">
                <div id="addShow">
	                <a href="javascript:;" class="pubbtn bluebtn" onclick="saveShedule()">保存</a>
	                <a href="javascript:;" class="pubbtn fffbtn"data-close="close">关闭</a>
                </div>
                <div id="updateShow">
	                <a href="javascript:;" class="pubbtn bluebtn" onclick="saveShedule()">保存</a>
	                <a href="javascript:;" class="pubbtn fffbtn" onclick="deleteShedule()">删除</a>
	                <a href="javascript:;" class="pubbtn fffbtn"data-close="close">关闭</a>
                </div>
              

            </div>
            </form>
        </div>
    </div>
</div>
    <script type="text/javascript">
        $("#updateShow").css("display","none");
        $("#addShow").css("display","block");
      //日期视图
    	$('.datepicker').datepicker({
    	    format: 'yyyy-mm-dd',
    	    language: "zh-CN",
    	    autoclose: true,
    	    todayHighlight: false,
    	    //calendarWeeks: true,
    	    defaultDate : Date,
    	    //weekStart:1,
    	    today: "Today",
    	    todayBtn:'linked',
    	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
    	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
    	    forceParse:false,
    	    currentText: 'Now',
    	    setDate:'2016-09-09'
    	    //defaultViewDate: new Date()
    	    //daysOfWeekDisabled: "0",
    	    //daysOfWeekHighlighted: "0",
    	    //clearBtn: true,
    	    //startView: 1, //0,month 1,year 2,decade
    	    //minViewMode: 1,  //0,days 1,month 2,years
    	    //maxViewMode: 1, //0,days 1,month 2,years
    	    //startDate: '-3d',
    	    //endDate: '+3d'
    	});
    	$('.datepicker').datepicker("setDate",new Date());

<%-- if('<%=timestr%>' !=''){
    	 $("#itemDate").val('<%=timestr%>');
     }
    
     var itemType="<%=itemType%>";//0:工作;1：个人
     var itemOrder="<%=itemOrder%>";//0:正常;1：紧急
     
     if(itemType == '0'){
    	 $("#itemType0").attr("checked","checked");
     }
     if(itemType == '1'){
    	 $("#itemType1").attr("checked","checked");
     }
     
     if(itemOrder == '0'){
    	 $("#itemOrder0").attr("checked","checked");
     }
     if(itemOrder == '1'){
    	 $("#itemOrder1").attr("checked","checked");
     } --%>
    //保存日程
    function saveShedule(){
    	if(beforeSubmit()){
	    	var id=$("#id").val();
	    	if(id == '' || id == 'null' || id == null){
	    	  $("#id").remove();
	    	}
	    	sendPostRequestByJsonObj(platformUrl.saveShedule, JSON.parse($("#shedule_form").serializeObject()), sheduleCallBack);
	    	$.locksCreenOpen();
    	}
    	
    }
    //新建日程
  /*   function newShedule(){
    	$("#changeSpan").html("添加日程安排");
    	$("#updateShow").css("display","none");
    	$("#addShow").css("display","block");
    	$("#itemDate").val(new Date().format("yyyy-MM-dd"));
    	$("#content").val('');
    	$("#id").val('');
    	$("#id").remove();
    	uncheckAll('itemType','1');
    	uncheckAll('itemOrder','1');
    } */
    //取消选中  
    function uncheckAll(type,value)   
    {   
    var code_Values = document.all[type];   
	    if(code_Values.length){   
		    for(var i=0;i<code_Values.length;i++)   
		    {   
			    if(code_Values[i].value == value){
			    	 code_Values[i].checked = false;   
			    }else{
			    	code_Values[i].checked = true;   
			    }
		   
		    }   
	    }else{   
	        code_Values.checked = false;   
	    }   
    }
    //获取单日程信息
    function getShedule(id){
    	sendGetRequest(platformUrl.sheduleInfo+id,'',getSheduleCallBack);
    }
    //获取单日程信息回调函数
    function getSheduleCallBack(data){
    	
    	var result = data.result.status;
    	if(result == "ERROR"){ //OK, ERROR
    		layer.msg("获取失败!");
    		return;
    	}
    	var content = data.entity.content;
    	var itemDate = data.entity.timeTask;
    	var itemType = data.entity.itemType;
    	var itemOrder = data.entity.itemOrder;
    	
    	$("#id").val(data.entity.id);
    	$("#content").val(content);
    	$("#itemDate").val(itemDate);
    	$("#id").val(data.entity.id);
    	
    	if(itemType == '0'){
    		uncheckAll('itemType','1');
   	    }
   	    if(itemType == '1'){
   	    	uncheckAll('itemType','0');
   	    }
   	    if(itemOrder == '0'){
   	    	uncheckAll('itemOrder','1');
   	    }
   	    if(itemOrder == '1'){
   	    	uncheckAll('itemOrder','0');
   	    }
   	    //$("#changeSpan").html("修改日程安排");
   		$("#updateShow").css("display","block");
    	$("#addShow").css("display","none");
    	
    }
    //删除日程
    function deleteShedule(){
    	var id=$("#id").val();
    	if(id){
    		sendGetRequest(platformUrl.deleteShedule+id,'',sheduleCallBack);
    	}
    }
    //新建|修改|删除回调函数
    function sheduleCallBack(data){
    	var result = data.result.status;
    	if(result == "ERROR"){ //OK, ERROR
    		layer.msg("操作失败!");
    		return;
    	}
    	$("#powindow").remove();
    	$("#popbg").remove();
    	loadAjaxSopUserSchedule(platformUrl.sheduleMoreThree); 
    	<%request.removeAttribute("sheduleList");%>
    	//shecudle();
    	//shecudle();
    	layer.msg("操作成功!");
    }
    
    
  
    $(function() {
        $(".bottom_l .nav_list").click(function(event) {
            $(this).siblings().stop().slideToggle().parent().siblings().children('dd').slideUp();
        });
    });
   

   $(".nav").click(function(event) {
           $(this).addClass('on').siblings().removeClass('on');
           $(".bottom_r .block").show().siblings().hide();
   });

    </script>

<script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>  
