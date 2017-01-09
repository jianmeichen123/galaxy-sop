<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<style>
.bars{margin:0 !important;}
</style>
<div class="addmentc post_operatetc_750">
	<div class="title_bj" id="popup_name"></div>
	
    <div class="form clearfix"  style="margin:85px auto 5px;">
     <c:if test="${isEditable}">
            <div style="margin-top:-40px;margin-left:10px;">
             
            	<a href="javascript:void(0)"  class="pbtn bluebtn h_bluebtn" data-btn="health_status" data-name='健康状况' style="width:90px;"></a>
               
            </div>
      </c:if>
        
	     	<div class="min_document clearfix" id="health-custom-toolbar" style="display:none;" >
				<div class="bottom searchall clearfix">
					<input type="hidden" name="projectId" value=""> 
				</div>
			</div>
		
		<div class="conference_all_750 scroll_table" style="position:relative;">
			<table class="health_case table table_health_case">
				<thead>
					<tr>
						<th width="81px">健康状况　</th>
						<th width="400px">风险点</th>
						<th width="158px">分析人</th>
						<th width="89px">分析日期</th>
					</tr>
				</thead>
			</table>
			<table id="project_health_table" class="health_case"
				data-page-list="[5, 10, 20]" 
				data-url="<%=path%>/galaxy/health/queryhealthpage" 
				data-id-field="id"
				data-toolbar="#health-custom-toolbar">
				<thead>
					<tr>
						<th data-field="healthStateStr" data-width="81px">健康状况　</th>
						<th data-field="rematk" class='width_36' data-width="400px">风险点</th>
						<th data-field="userName" data-width="158px">分析人</th>
						<th data-field="createdTime" data-formatter="longTime_Format" data-width="89px">分析日期</th>
					</tr>
				</thead>
			</table>
		</div>
        </div>
    
    
  	
</div>
<script type="text/javascript">
$(function(){
	show_health_status();
});

/**
* 健康记录  添加
*/
function show_health_status(){
	$("[data-btn='health_status']").text("添加健康状况");
	$("[data-btn='health_status']").on("click",function(){
		if($(this).hasClass('limits_gray'))
		{
			return;
		}
		var $self = $(this);
		var _name= $self.attr("data-name");
		var _url = Constants.sopEndpointURL + '/galaxy/health/toaddhealth';
		$.getHtml({
			url:_url,
			data:"",
			okback:function(){
				$("#popup_name").html(_name);
				$("#health_form [name='projectId']").val(proid);
			}
		});
		return false;
	});
}
function save_health(){
	var content = JSON.parse($("#health_form").serializeObject());
	var _url =  Constants.sopEndpointURL + '/galaxy/health/addhealth'
	sendPostRequestByJsonObj(_url, content, function(data){
		if (data.result.status=="OK") {
			layer.msg("保存成功");	
			$.popupTwoClose();
			//init_bootstrapTable('project_health_table',5);
			$("#project_health_table").bootstrapTable('refresh');
			//启用滚动条
			 $(document.body).css({
			   "overflow-x":"auto",
			   "overflow-y":"auto"
			 });
			//刷新投后运营简报信息
			setThyyInfo();
			
			$("#project_delivery_table").bootstrapTable('refresh');
		} else {
			layer.msg(data.result.message);
		}
	});
	
}
$(".datepicker").datepicker({
   format: 'yyyy-mm-dd',
   language: "zh-CN",
   autoclose: true,
   todayHighlight: false,
   defaultDate : Date,
   today: "Today",
   todayBtn:'linked',
   leftArrow: '<i class="fa fa-long-arrow-left"></i>',
   rightArrow: '<i class="fa fa-long-arrow-right"></i>',
   forceParse:false,
});

</script>
