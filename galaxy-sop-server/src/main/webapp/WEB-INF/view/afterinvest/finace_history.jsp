<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<style>
.bars{display:none;} 
</style>
<div class="addPersontc">
	<div class="title_bj" id="popup_name"></div>
	
        <div class="addPerson_all" id="person-pool">
            <div class="qualifications">
               <div id="pro_per_info_toolbar">
					<input type="hidden" name="id" id="pool_id" value="" />
				</div>
				<div style="border:1px solid #e9ebf2 !important;width:94% !important;margin:20px 20px;border-radius:6px;">
				   <table style="width:100%;margin:20px 0;"  cellspacing="0" cellpadding="0" class="table financeHistoryTable">
		            <thead>
		               <tr>
		                  <th>融资时间</th>
		                  <th>投资方(机构或个人)</th>
		                  <th>投资金额(万元)</th>
		                  <th>币种</th>
		                  <th>股权占比（%）</th>
		                  <th>融资轮次</th>
		                 </tr>
		             </thead>
			         <tbody id="financeHistory_table">
			         </tbody>
		         </table>
				</div>
            </div>
            
      </div>
</div>
<script type="text/javascript">
/**
 * 加载该项目的融资历史
 */
var pid='${projectId}';
searchFH();
function searchFH(){
	sendPostRequest(platformUrl.searchFH+"/"+pid,  function(data){
		formatterTable(data.entityList);
		if(data.entityList.length>=10){
            $("#add").css("display","none");
		}else{

            $("#add").css("display","block");
		}
	});
}
function formatterTable(entity){
	$("#financeHistory_table").children('tr').remove();
	var html;
	if(null==entity||""==entity){
		var html="<tr><td colspan='7' style='text-align:center !important;color:#bbb;border:0;line-height:32px !important' class='noinfo no_info01'><label class='no_info_icon_xhhl'>没有找到匹配的记录</label></td></tr>";
		$("#financeHistory_table").append(html);
		if(isEditable &&( isEditable==false || isEditable=='false' )){
			$(".noinfo").attr("colspan","6");
		}
	}else{
		if(entity.length>=10){
			$("#add_history").css("display","none");
		}else{
			$("#add_history").css("display","block");
		}
		 var data={
					"0":"人民币",
					"1":"美元", 
				    "2":"英镑" ,
				    "3":"欧元" 
				 };
		for(var i=0;i<entity.length;i++){
			var obj=entity[i];
			
			var financeAmount =  typeof(obj.financeAmount)!="undefined"?obj.financeAmount:"-";
			var financeProportion = typeof(obj.financeProportion)!="undefined"?obj.financeProportion:"-";
			var financeDateStr = typeof(obj.financeDateStr)!="undefined"?obj.financeDateStr:"-";
			var financeFrom = typeof(obj.financeFrom)!="undefined"?obj.financeFrom:"-";
			var financeStatusDs = typeof(obj.financeStatusDs)!="undefined"?obj.financeStatusDs:"-";
			
			html=
				"<tr>"+
					"<td>"+financeDateStr+"</td>"+
					"<td  title='"+financeFrom+"'>"+financeFrom+"</td>"+
					"<td>"+financeAmount+"</td>"+
					"<td>"+data[obj.financeUnit]+"</td>"+
					"<td>"+financeProportion+"</td>"+
					"<td>"+financeStatusDs+"</td>";
			var html_1 =
					"<td>"+
						"<a class='finance_edit blue'   onclick=\"toUpdateOrSave('"+obj.id+"')\" href='javascript:void(0)' data-name='编辑融资历史'>编辑 &nbsp;</a>"+
						"<a class='finance_delete blue' onclick=\"deleteFinance('"+obj.id+"')\" href='javascript:void(0)'>删除</a>"+
					"</td>";
			
			var html_2 = "</tr>";
			//if(isEditable &&( isEditable==false || isEditable=='false' )){
				html = html + html_2;
			//}else{
			//	html = html + html_1 + html_2;
			//}
			$("#financeHistory_table").append(html);
		}
		
	}
	
}
</script>
