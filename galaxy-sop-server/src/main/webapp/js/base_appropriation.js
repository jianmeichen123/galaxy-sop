
function reloadData(searchPartMoney,pid){
	var data = {};
	  data.searchPartMoney=searchPartMoney;
	  data.projectId=pid;
	  sendPostRequestByJsonStr(platformUrl.queryGrantTotalList,JSON.stringify(data),queryBack);

}
function queryBack(data){
	$("#tabApprAllList").children('div').remove(); 
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		layer.msg(data.result.message);
		return;
	}else{ 
	    var entityList = data.pageList;
	    //暂无拨款计划
	    if(entityList.total==0){
	    	var noContent='<div class="no_con" style="display: block;">暂无内容</div>';
	    	$("#tabApprAllList").append(noContent);
	    }
		if(typeof(entityList)!="underfined"&&entityList!=null){
			var content=entityList.content;			
			if(content.length>0){
				for(var i=0;i<content.length;i++){
					var grantTotal=content[i];
					var _this=$("#tabApprAllList");
					var kk=assembleHtml(grantTotal,i);
					$("#tabApprAllList").append(kk);
					var partList=grantTotal.partList;
					if(null!=partList&&partList.length>0){
						for(var k=0;k<partList.length;k++){
							  var grantPart=partList[k];
							  var o=_this;
							  $("#tabApprSingleList_"+i+"").append(assembleSingleTabHtml(grantPart,grantTotal.grantName));
							}
						}else{
							var noData =
								'<tr>'+
								 '<td colspan="6" class="no_info no_info01" style="text-align:center;"><p class="no_info_icon">没有找到匹配的记录</p></td>'+
								' </tr>'; 			
							 $("#tabApprSingleList_"+i+"").append(noData)
						}
					}
					if(isCreatedByUser != 'true')
					{
					 	$("#tabApprAllList .b_agreement_r").hide();
					 	$("#tabApprAllList .edit-btn, #tabApprAllList .del-btn").hide();
					}
			}
		}
	}
}
function  assembleHtml(grantTotal,i){
	var createName=grantTotal.createUname;
	if($(window).width()<1400){  //编辑人针对窄屏显示器截断显示
		if(createName.length>5){
			createName=createName.substring(0,5);
		}else{
			createName=createName;
		}
	}	
	var html=
		'<div class="agreement">'
	     +'<div class="b_agreement clearfix">'
		  +'<div class="b_agreement_l fl">'
		     +'<h3>'+grantTotal.grantName+'</h3>'
	         +'<dl><dt>计划总拨款金额（元）：</dt><dd>'+addCommas(fixSizeDecimal(grantTotal.grantMoney))+'</dd></dl>'
             +'<dl><dt>编辑人：</dt><dd title="'+grantTotal.createUname+'">'+createName+'</dd></dl>'    
             +'<dl><dt>编辑日期：</dt><dd>'+time_zh(grantTotal.updatedTime)+'</dd></dl>'
          +'</div>'    
         +'<div class="b_agreement_r fr">'
            +'<button class="pbtn bluebtn" href="/sop/html/actual_aging.html" data_type="add" data-btn="actual_aging" data-id="'+grantTotal.id+'" data-name="添加分期拨款计划" data-total-name="'+grantTotal.grantName+'">添加分期拨款计划</button>'
            +'<label class="blue" href="/sop/html/actual_all.html" data-btn="actual_all" data-on="edit" data-val="'+grantTotal.id+'"data-name="编辑总拨款计划">编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" onclick="deleteAppr('+grantTotal.id+')" data-name="提示">删除</label>'
         +'</div>'
    +'</div>'                      
  //  <!--表格内容-->
   +'<table width="100%" cellspacing="0" cellpadding="0" class="commonsize delivery">'
     + '<thead>'
          +'<tr>'
             +'<th>分拨</th>'
              +'<th>拨款时间</th>'
              +'<th>计划拨款金额（元）</th>'
               +'<th>实际拨款金额（元）</th>'
              +'<th>附件数</th>'
              +'<th>操作</th>'
             +'</tr>'
      +'</thead>' 
      +'<tbody id="tabApprSingleList_'+i+'"></tbody>'
   +'</table>'
 +'</div>';
	 return html ;
} 

function  assembleSingleTabHtml(grantPart,grantName){
	 var value='<tr>'	 
		   +'<td><a class="blue" href="javascript:void(0)" data-part-id='+grantPart.id+' data-btn="actual" data-name="实际拨款信息列表">'+grantPart.grantName+'</a></td>'
		   +'<td>'+grantPart.grantDetail+'</td>'
		   +'<td>'+addCommas(fixSizeDecimal(grantPart.grantMoney))+'</td>'
		   +'<td>'+addCommas(fixSizeDecimal(grantPart.actualMoney))+'</td>'
		   +'<td>'+grantPart.fileNum+'</td>'                                 
		   +'<td><label class="blue edit-btn" href="/sop/html/actual_aging.html" data_type="edit" data-btn="actual_aging" data-part-id="'+grantPart.id+'" data-id="'+grantPart.totalGrantId+'" data-name="编辑分期拨款计划" data-total-name="'+grantName+'">编辑</label><label class="blue del-btn" href="javascript:void(0);" onclick="to_del_grantPart('+grantPart.id+')" data-btn="tips" data-name="提示">删除</label>';
		   if(grantPart.fileNum != 0){
			   value +='<label class="blue noMargin" onclick="to_download_grantPart('+grantPart.id+')">下载附件</label></td>';
		   }
		   value += '</tr>';
	  return value;
}
function deleteAppr(id){
	var _url = platformUrl.deleteAlert;
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			$("#popup_name").html("信息");
			$("[data-btn='appr_delete']").click(function(){
				del_appr(id);
			})
			
		}//模版反回成功执行	
	});
 		
 	}
function del_appr(id){
	sendPostRequest(platformUrl.deleteGrantTotal+"/"+id,function(data){
			if (data.result.status=="OK") {
				layer.msg("删除成功");
                window.location=location;
			} else {
				if(data.result.errorCode=='cantDelete'){
					var _url = Constants.sopEndpointURL+"/galaxy/grant/total/tipsDelete";
					$.getHtml({
						url:_url,//模版请求地址
						data:"",//传递参数
						okback:function(){
							$('#powindow[data-id="popid0"]').remove();	
					}//模版反回成功执行	
				});
				 		
				}else{
					layer.msg(data.result.message);
				}
				
			}
		});
}