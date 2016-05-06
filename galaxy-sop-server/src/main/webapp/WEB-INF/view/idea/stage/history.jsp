<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div style=" width:1200px" class="ritmin">
	<div class="clearfix">
	  <h3>调研</h3>       
	</div>
	<div id="research-history-params">
		<input type="hidden" name="projectId" value="${id}">
	</div>
	<table id="research-history" data-id-field="id" data-url="<%=path%>/galaxy/idea/queryIdeaDyList" data-toolbar="#research-history-params">
		<thead>
			<tr>
				<th data-field="fileUName" data-align="center" >上传者</th>
				<th data-field="fWorktype" data-align="center" >存档类型</th>
				<th data-field="projectProgress" data-align="center" data-formatter="progressFormatter">创意状态</th>
				<th data-field="careerLineName" data-align="center" >所属事业线</th>
				<th data-field="updatedDate" data-align="center" >更新时间</th>
				<th data-align="center" data-formatter="ideaFileDownFormat">附件查看</th>
			</tr>
		</thead>
	</table>
	<div class="clearfix">
	  <h3>创建立项会</h3>       
	</div>
	<table width="100%" cellspacing="0" cellpadding="0" class='table_l'>
	    <thead>
	        <tr>
	            <th>会议概况</th>
	            <th>会议纪要</th>
	        </tr>
	    </thead>                                                                                                                                    
	    <tbody>
	        <tr>
	            <td class="td1">会议时间:<span>2016-04-02</span><br/>会议结论:<span>2016-04-02</span><br/>会议录音:<a href="javascript:;" class="blue">录音.mp3</a></td>
	            <td class="td2">头脑风暴法（Brainstorming）是最为人所熟悉的创意思维策略，该方法是由美国人奥斯本（Osborn）早于1937年所倡导，此法强调集体思考的方法，着重互相激发思考，鼓励参加者于指定时间内，构想出大量的意念，并从中引发新颖的构思。</td>
	        </tr>
	
	    </tbody>
	</table>
</div>
<script>
$("#research-history").bootstrapTable({
	queryParamsType: 'size|page', // undefined
	pageSize:3,
	showRefresh : false ,
	sidePagination: 'server',
	method : 'post',
	pagination: true,
    search: false
});
</script>