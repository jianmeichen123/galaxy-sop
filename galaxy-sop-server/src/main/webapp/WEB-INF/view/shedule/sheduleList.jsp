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
				    		  <dl data-tab='nav' onclick="getShedule(<%=shcedule.getId()%>)">
			                            <dt><%=shcedule.getContent() %>
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
                    <!-- <input class="form-control" type="date" id="itemDate" name="itemDateStr" value="<%=timestr%>"/> -->
                    <input name="itemDateStr" id="itemDate" type="text" value="<%=timestr%>" id="date" onclick="calendar.show(this);" size="15" maxlength="10" readonly="readonly"/>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                <dt>事项类型：</dt>
                <dd class="clearfix">
                    <label for=""><input type="radio" id="itemType0" name="itemType" value="0" checked/>单位</label>
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
                    <textarea id="content" name="content"><%=contentstr %></textarea>
                </dd>
            </dl>
            <div class="btnbox">
                <a href="javascript:;" class="pubbtn fffbtn"data-close="close" onclick="deleteShedule()">删除</a>
                <a href="javascript:;" class="pubbtn bluebtn" onclick="saveShedule()">编辑</a>
            </div>
            </form>
        </div>
    </div>
</div>
    <script type="text/javascript">
    
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
    	$("#id").remove();
    	sendPostRequestByJsonObj('/galaxy/sopUserSchedule/addOrUpdateSopUserSchedule/', JSON.parse($("#shedule_form").serializeObject()), sheduleCallBack,null);
    }
    //新建日程
    function newShedule(){
    	$("#itemDate").val('');
    	$("#content").val('');
    	$("#id").val('');
    	$("#id").remove();
    	$("#itemType0").attr("checked","checked");
    	$("#itemOrder0").attr("checked","checked");
    }
    //获取单日程信息
    function getShedule(id){
    	var url='/galaxy/sopUserSchedule/getSchedule/'+id;
    	sendGetRequest(url,'',getSheduleCallBack,null);
    }
    //获取单日程信息回调函数
    function getSheduleCallBack(data){
    	
    	var result = data.result.status;
    	if(result == "ERROR"){ //OK, ERROR
    		alert("error")
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
    	var url='/galaxy/sopUserSchedule/delete/'+id;
    	sendGetRequest(url,'',sheduleCallBack,null);
    }
    //新建|修改|删除回调函数
    function sheduleCallBack(data){
    	var result = data.result.status;
    	if(result == "ERROR"){ //OK, ERROR
    		alert("操作失败!");
    		return;
    	}
    	$("#powindow").remove();
    	$("#popbg").remove();
    	loadAjaxSopUserSchedule('',sopContentUrl + '/galaxy/sopUserSchedule/selectSopUserSchedule/1'); 
    	shecudle();
    }
    
  
    $(function() {
        $(".bottom_l .nav_list").click(function(event) {
            $(this).siblings().stop().slideToggle().parent().siblings().children('dl').slideUp();
        });
    });
    </script>

