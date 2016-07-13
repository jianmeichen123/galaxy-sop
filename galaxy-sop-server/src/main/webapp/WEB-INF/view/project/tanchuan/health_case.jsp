<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<style>
.bars{margin:0 !important;}
</style>
<div class="addmentc post_operatetc">
	<div class="title_bj" id="popup_name"></div>
	
    <div class="form clearfix">
        <div class="conference_all scroll_table">
        
        
	     	<div class="min_document clearfix" id="health-custom-toolbar" style="display:none;" >
				<div class="bottom searchall clearfix">
					<input type="hidden" name="projectId" value=""> 
				</div>
			</div>
			
			<table id="project_health_table" class="health_case"
				data-url="<%=path%>/galaxy/health/queryhealthpage" 
				data-id-field="id"  data-page-list="[5,10, 20]"
				data-toolbar="#health-custom-toolbar">
				<thead>
					<tr>
						<th data-field="healthStateStr" >健康状况　</th>
						<th data-field="rematk" >风险点</th>
						<th data-field="userName" >分析人</th>
						<th data-field="createdTime" data-formatter="longTime_Format" >分析日期</th>
					</tr>
				</thead>
			</table>
               
        </div>
    </div>
    
  	
</div>

<style>
div.tip-yellowsimple {
    visibility: hidden;
    position: fixed;
    top: 0;
    left: 0;
}
</style>
