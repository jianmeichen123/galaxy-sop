<%@ page language="java" pageEncoding="UTF-8"%>
<!--法人信息-->
<div class="tabtable_con">
	<div class="new_r_compile new_bottom_color">
		<span class="new_ico_project"></span> <span class="new_color size16">法人信息</span>
		<div class="new_r_compile">
			<span class="new_fctbox"> 
				<a href="#" class="ico f1" onclick="editCompanyInfo()">编辑</a>
			</span>
		</div>
		<table width="100%" cellspacing="0" cellpadding="0" class="new_table" id="company-info">
			<tr>
				<td>
					<span class="new_color_gray">公司名称：</span> 
					<span class="new_color_black" id="projectCompany">星河互联</span></td>
				<td>
					<span class="new_color_gray">组织代码：</span> 
					<span class="new_color_black" id="projectCompanyCode">456789</span>
				</td>
			</tr>
			<tr>
				<td>
					<span class="new_color_gray">法人：</span> 
					<span class="new_color_black" id="companyLegal">徐茂栋</span></td>
				<td>
					<span class="new_color_gray">成立时间：</span> 
					<span class="new_color_black" id="formationDate">2000-02-02</span>
				</td>
			</tr>
		</table>
	</div>
</div>
<!--股权结构-->
<div class="tabtable_con_on">
	<div class="new_r_compile new_bottom_color">
		<span class="new_ico_industry"></span> <span class="new_color size16">股权结构</span>
	</div>

		<a href="javascript:;" onclick="addSharesView();" class="bluebtn new_btn">添加</a>
	<div id="shares-custom-toolbar">
		<input type="hidden" name="projectId" value="${projectId }">
	</div>
	<div class="tab-pane active" id="pView">	
    	<table id="shares-table" data-height="555" data-page-list="[10, 20, 30]" data-toolbar="#shares-custom-toolbar" data-show-refresh="true">
	    	<thead>
			    <tr>
			    	<th data-field="sharesType" data-align="center" class="data-input">类型</th>
		        	<th data-field="sharesOwner" data-align="center" class="data-input">所有人</th>
		        	<th data-field="sharesRatio" data-align="center" class="data-input">占比</th>
		        	<th data-field="gainMode" data-align="center" class="data-input">获取方式</th>
		        	<th data-field="remark" data-align="center" class="data-input" >备注</th>
		        	<th data-align="center" class="col-md-2" data-formatter="shareOperatFormater">操作</th>
					</tr>	
				</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	refreshCompanyInfo();
	//股权结构列表
	$("#shares-table").bootstrapTable({
		queryParamsType: 'size|page', 
		pageSize:10,
		url: platformUrl.projectSharesList,  
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
        onLoadSuccess: function (data) {
        }
	});
	
	
	function refreshCompanyInfo()
	{
		var dtd = $.Deferred();
		 
		$.when(top.getProjectInfo(dtd))
		.done(function(){
			$("#company-info #projectCompany").text(getVal(projectInfo.projectCompany,''));
			$("#company-info #projectCompanyCode").text(getVal(projectInfo.projectCompanyCode,''));
			$("#company-info #companyLegal").text(getVal(projectInfo.companyLegal,''));
			var date = '';
			if(!isNaN(projectInfo.formationDate))
			{
				date = new Date(projectInfo.formationDate).format('yyyy-MM-dd');
			}
			$("#company-info #formationDate").text(date);
		});
		 
	}
	function editCompanyInfo()
	{
		var url = platformUrl.editCompanyInfo+"/${projectId}"
		$.getHtml({
			url:url
		});
	}
	function shareOperatFormater(val,row,index)
	{
		var e = '<a href="javascript:;" mce_href="javascript:;" class="blue" onclick="editStock(\''+ row.id + '\')">修改</a> ';  
        var d = '<a href="javascript:;" mce_href="javascript:;" class="blue" onclick="delStock(\''+ row.id +'\')">删除</a> ';  
        return e+d;  
	}
	function editStock(id){
    	var _url = platformUrl.editStockView+id;
		$.getHtml({
			url:_url,
			hideback:function(){
				$("#shares-table").bootstrapTable('refresh');
			}
		});
		return false;
    }
	function delStock(id)
	{
		layer.confirm(
			'确定要删除数据？',
			function(index){
				layer.close(index);
				var url = platformUrl.deleteProjectShares+id+"/${projectId}";
				sendGetRequest(
					url,
					{},
					function(data){
						if(data.result.status=="OK")
						{
							layer.msg('删除成功');
							$("#shares-table").bootstrapTable('refresh');
						}
						else
						{
							layer.msg(data.result.message);
						}
						
					}
				);
			}
		);
		
	}
	function addSharesView(){
		$.getHtml({
			url:platformUrl.addSharesView,
			okback:function(){
				$("#stock_form #projectId").val("${projectId}")
			},
			hideback:function(){
				$("#shares-table").bootstrapTable('refresh');
			}
		});
		return false;
	}
	function updateStock()
	{
		savaStock();
	}
	function savaStock(){
		if(beforeSubmit())
		{
				sendPostRequestByJsonObj(
					platformUrl.addStock, 
					JSON.parse($("#stock_form").serializeObject()), 
					function(data){
						if(data.result.status=="OK")
						{
							layer.msg('保存成功');
							$("#shares-table").bootstrapTable('refresh');
						}
						else
						{
							layer.msg(data.result.message);
						}
					}
				);
		}
	}
	
</script>
