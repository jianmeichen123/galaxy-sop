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

<div class="schedule1tc">
    <div class="top clearfix">
        <a href="javascript:;" class="pubbtn bluebtn addbtn" onclick="newShedule()">新建</a>
        <div class="searchall clearfix">
            <!-- 
            <input type="text" placeholder="请输入关键字" class="txt"/>
            <a class="searchbtn null" href="javascript:;">搜索</a>
             -->
        </div>
    </div>
    <div class="bottom clearfix">
        <div class="bottom_l">
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
      
        <div id="editShedule" class="bottom_r2 bottom_r"  data-tab='con'> 
           <form action="" id="shedule_form" method="post">    
            <input type="hidden" id="id" name="id" value="<%=id%>"/>
            <h2>添加日程安排</h2>
            <dl class="fmdl clearfix">
                <dt>处理日期：</dt>
                <dd class="clearfix">
                    <input type="text" id="itemDate" class="datepicker time" name="itemDateStr" readonly value="<%=timestr %>" valType="required" msg="<font color=red>*</font>处理日期不能为空"/>
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
                    <textarea id="content" name="content" valType="OTHER" regString="^.{1,20}$" msg="<font color=red>*</font>事件内容不能超过200字符"><%=contentstr %></textarea>
                </dd>
            </dl>
            <div class="btnbox">
                <a href="javascript:;" class="pubbtn fffbtn"data-close="close" onclick="deleteShedule()">关闭</a>
                <a href="javascript:;" class="pubbtn bluebtn" onclick="saveShedule()">保存</a>
            </div>
            </form>
        </div>
    </div>
</div>
    <script type="text/javascript">
    
     if('<%=timestr%>' !=''){
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
     }
    //保存日程
    function saveShedule(){
    	if(beforeSubmit()){
	    	var id=$("#id").val();
	    	if(id == '' || id == 'null' || id == null){
	    	  $("#id").remove();
	    	}
	    	sendPostRequestByJsonObj(platformUrl.saveShedule, JSON.parse($("#shedule_form").serializeObject()), sheduleCallBack);
    	}
    }
    //新建日程
    function newShedule(){
    	var time=currentTime();
    	$("#itemDate").val(time);
    	$("#content").val('');
    	$("#id").val('');
    	$("#id").remove();
    	$("#itemType0").attr("checked","checked");
    	$("#itemOrder0").attr("checked","checked");
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
   	    }
    	
    }
    //删除日程
    function deleteShedule(){
    	var id=$("#id").val();
    	sendGetRequest(platformUrl.deleteShedule+id,'',sheduleCallBack);
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
   
   function currentTime(){
	   var myDate = new Date();
	   var year=myDate.getFullYear();        //获取当前年份(2位)
	   var month=myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
	   var day=myDate.getDate();  
	   return year+"-"+month+"-"+day;
   }

    </script>

<script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>  
