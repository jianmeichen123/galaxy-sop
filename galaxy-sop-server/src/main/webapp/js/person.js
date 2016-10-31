/***
 * 可作为通用的tab切换
 * url:路径
 * data:项目id等参数
 *//*
function changeTab(url,data) {
	$("#tab2").attr("class","on");
    $.ajax({
		type:"GET",
		data:"",
		dataType:"html",
		url:url+data,
		beforeSend : function(xhr) {
			*//**清楚浏览器缓存**//*
			xhr.setRequestHeader("If-Modified-Since","0"); 
			xhr.setRequestHeader("Cache-Control","no-cache");
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
		},
		success:function(html){
			$("#cur_tab").html(html);
		},
		error:function(){
		}	
	})
}*/

//个人简历
function changeTab(url,data){
	$.getHtml({
		url: url+data, 
		data:"",//传递参数
		okback:function(){
		
		}//模版反回成功执行	
	}); 
	
}

function getTabPerson(){
	var html='<table id="tablePerson"  data-height="555" data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" ></table>';
	$("#view").html(html);
	var $table = $('#tablePerson');
    $table.bootstrapTable({
    url: platformUrl.projectPersonList,
    dataType: "json",
    pagination: true, //分页
    search: false, //显示搜索框
    pageList: [10,20,30],
    queryParamsType: 'size|page',
    queryParams: function(params){params.projectId=projectId; return params;},
    sidePagination: "server", //服务端处理分页
          columns: [
                  {
                    title: '姓名',
                      field: 'personName',
                     
                      valign: 'middle',
                      'class':'personName',
                      formatter:'personName'
                  },
                  {
                      title: '当前职务',
                        field: 'personDuties',
                       
                        valign: 'middle',
                        formatter:'personDuties'
              },
                  {
                    title: '性别',
                    field: 'personSex',
                   
                    valign: 'middle',
                    formatter:function(value,row,index){ 
                     	if (row.personSex == 0) {
                			return "男";
                		}else if (row.personSex == 1) {
                			return "女";
                		}else {
                			return "-";
                		}
                    }
                    },
                    {
                        title: '年龄',
                          field: 'personAge',
                         
                          valign: 'middle'
                     },
                  {
                      title: '最高学历',
                        field: 'highestDegree',
                       
                        valign: 'middle',
                        formatter:function(value,row,index){ 
                         	if (row.highestDegree == 1) {
                    			return "高中";
                    		}else if (row.highestDegree == 2) {
                    			return "大专";
                    		}else if (row.highestDegree == 3) {
                    			return "本科";
                    		}else if (row.highestDegree == 4) {
                    			return "硕士";
                    		}else if (row.highestDegree == 5) {
                    			return "MBA";
                    		}else if (row.highestDegree == 6) {
                    			return "博士";
                    		}else if (row.highestDegree == 7) {
                    			return "其他";
                    		}
                    		else {
                    			return "-";
                    		}
                        }
                  },
                  {
                      title: '工作年限',
                        field: 'workTime',
                       
                        valign: 'middle'
                  },
                  {
                      title: '电话',
                        field: 'personTelephone',
                       
                        valign: 'middle'
                  },
                  {
                      title: '操作',
                      field: 'id',
                     
                      formatter:function(value,row,index){  
	                   var content = '<span class="resume" data-id="'+row.id+'">个人简历</span>';
	                   if(isCreatedByUser == 'true' && isTransfering == 'false')
                	   {
	                	   content += '<span class="edit" data-id="'+row.id+'">编辑</span>';  
	                	   content += '<span class="del" data-id="'+row.id+'">删除</span>';  
                	   }
                        return content;  
                    } 
                  }
              ],
          onLoadSuccess:function(data){
        	  if(data.pageList.total>0 && isTransfering == 'true')
        	  {
        		  $table.find("tr span").parent().addClass('limits_gray');
        	  }
        	  else
    		  {
        		  $table.find("tr span").click(function(){
        			  var id = $(this).data('id');
        			  if($(this).hasClass('resume'))
    				  {
        				  tiaozhuan(id);
    				  }
        			  else if($(this).hasClass('edit'))
    				  {
        				  updatePer(id);
    				  }
        			  else if($(this).hasClass('del'))
    				  {
        				  deletePer(id);
    				  }
        		  });
    		  }
          }
      });
      $table.bootstrapTable('refresh');
	}


/** 添加团队成员
 */
function savePerson(){
if(beforeSubmit()){
	var projectId = $("#pid").val();
	if(projectId != ''){
		$("#projectId").val(projectId);
		sendPostRequestByJsonObj(platformUrl.addPerson, JSON.parse($("#person_form").serializeObject()), savePersonCallBack);
	}
}
}
//保存成功回调
function savePersonCallBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		layer.msg(data.result.message);
		//return;
	}
	//启用滚动条
	 $(document.body).css({
	   "overflow-x":"auto",
	   "overflow-y":"auto"
	 });
	$("#popbg,#powindow").remove();
	var projectId = $("#pid").val();
	getTabPerson(projectId);
}

/**
 * 修改团队成员
 */
function updatePerson(){
	if(beforeSubmit()){
		var projectId = $("#pid").val();
		if(projectId != ''){
			$("#projectId").val(projectId);
			sendPostRequestByJsonObj(platformUrl.updatePerson, JSON.parse($("#up_person_form").serializeObject()),savePersonCallBack);
		}
	}
}
/**
 * 删除团队成员
 * @param id
 */
function deletePer(id,url){
	layer.confirm('是否删除事项?',
			{
			  btn: ['确定', '取消'] 
			}, 
			function(index, layero){
				var projectId = $("#pid").val();
				var url = platformUrl.deletePPerson+id+"/"+projectId;
				if(projectId != ''){
					$("#projectId").val(projectId);
					sendGetRequest(url,'',savePersonCallBack);
					//removePop1();
					layer.close(index);
					layer.msg("删除成功");
				}
			}, 
			function(index){
			}
		);
	
	
	/*if(confirm("确定要删除该团队成员吗？")){
		var projectId = $("#pid").val();
		var url = platformUrl.deletePPerson+id+"/"+projectId;
		if(projectId != ''){
			$("#projectId").val(projectId);
			sendGetRequest(url,'',savePersonCallBack);
		}
	}*/
}

function personName(value,row,index){
	var str=row.personName;
	if(str.length>20){
		subStr = str.substring(0,20);
		var options = "<label title='"+str+"'>"+subStr+"</label>";
		return options;
	}
	else{
		var options = "<label title='"+str+"'>"+str+"</label>";
		return options;
	}
}

function personDuties(value,row,index){
	var str=row.personDuties;
	if(str.length>10){
		subStr = str.substring(0,10);
		var options = "<label title='"+str+"'>"+subStr+"</label>";
		return options;
	}
	else{
		var options = "<label title='"+str+"'>"+str+"</label>";
		return options;
	}
}

