function tabOperateChange(index){
	$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
	$('.anchor_nav').remove();
	$("#tab-content").remove();
	$("#tab-content1").remove();
	$(".tip-yellowsimple").remove();
  switch(index){
	case '0':investFinancePlanO(); break;  //标签1:投资方案
	case '1': initTeamInfoO(); break;  //标签2: 团队
	case '2': initOperateInfoO();   break;  //标签3:运营数据
	case '3': initMarkDevelopO();   break;  //标签4:市场开发
	case '4': initCompeteInfoO();   break;  //标签4:竞争
	case '5': initPlanInfoO();   break;  //标签5:战略及策略
	case '6': initFinanceInfoO();   break;  //标签6:财务
	case '7': initJusticeInfoO();   break;  //标签7:法务
	case '8': initValuationO();   break;  //标签8:融资及估值
	default: return false;
}
}
	   //投资方案
		function investFinancePlanO(){
			window.location.href=platformUrl.toOperationP;
		}
		 //团队
		function initTeamInfoO(){
			$("#page_all").html("");
			$.getTabHtmlInfo({
				url : platformUrl.toOperationT ,
				okback:function(){
					right_anchor("ONO2?reportType=7","seven","hide");
				}
			});
		}
		 //运营数据
		function initOperateInfoO(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationO ,
				okback:function(){
					right_anchor("ONO3?reportType=7","seven","hide");
				}
			});
		}
		//市场与开发
		function initMarkDevelopO(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationMD ,
				okback:function(){
					right_anchor("ONO4?reportType=7","seven","hide");
				}
			});
		}
		//竞争
		function initCompeteInfoO(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationC ,
				okback:function(){
					right_anchor("ONO5?reportType=7","seven","hide");
				}
			});
		}
		//战略以及策略
		function initPlanInfoO(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationPlan ,
				okback:function(){
					right_anchor("ONO6?reportType=7","seven","hide");
				}
			});
		}
		//财务
		function initFinanceInfoO(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationF ,
				okback:function(){
					right_anchor("ONO7?reportType=7","seven","hide");
				}
			});
		}
		//法务
		function initJusticeInfoO(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationJ ,
				okback:function(){
					right_anchor("ONO8?reportType=7","seven","hide");
				}
			});
		}
		//融资及估值
		function initValuationO(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationV ,
				okback:function(){
					right_anchor("ONO9?reportType=7","seven","hide");
				}
			});
		}
		
		/**
		 * 运营报告-分期拨款:增加弹窗
		 * @param ele
		 */
		function addRow(ele)
		{
			  var code = $(ele).prev().data('code');
			  var titleId = $(ele).prev().data("titleId");
			  $.getHtml({
		          url:path+'/html/operation_appr_part.html',//模版请求地址
		          data:"",//传递参数
		          okback:function(){
		        	   $("#save_appr_part").click(function(){
		        		   var data = getData($("#detail-form"));
		        		   var dataList = getDataList($("#appr_part"));
		        		   var index = data['index'];
		        		   var headerList =   $('table[data-title-id="'+titleId+'"].editable').find('tbody').find('tr:eq(0)').find("th[data-field-name!='opt']");
		        		   if(typeof index == 'undefined' || index == null || index == '')
		        	        {
		        			    data["titleId"]=titleId;
		        			    data["projectId"]=projectInfo.id;
		        			    data["dataList"] = dataList;
		        	            var tr = buildTableRow(headerList,data,true);
		        	            $('table[data-title-id="'+titleId+'"].editable').append(tr);
		        	        }
		        		   check_table();
		       			   check_table_tr_edit();
		        		  $("#part_cancle").click();
		              }); 
		          }
			  });
		}
		
		/**
		 * 运营报告-分期拨款:表格追加数据
		 * @param headerList
		 * @param row
		 * @param showOpts
		 * @returns
		 */
		function buildTableRow(headerList,row,showOpts)
		{
			var tr=$("<tr data-row-id='"+row.id+"'></tr>");
		    for(var key in row)
		   	{
		    	//设置data
		   		tr.data(key,row[key]);
		   	}
		    $(headerList).each(function(){
		        var key = $(this).attr("data-field-name");
		        tr.data(key,row[key]);
		        if(key.indexOf('field')>-1)
		        {
		            if(row[key]){
		               tr.append('<td data-field-name="'+key+'">'+row[key]+'</td>');
		            }
		        }
		
		    })
			var td = $('<td data-field-name="opt"></td>');
		    if(showOpts == true)
		    {
		        td.append('<span class="blue" data-btn="btn" onclick="showRow(this)">查看</span>');
		        td.append('<span class="blue" data-btn="btn" onclick="editRow(this)">编辑</span>');
		        td.append('<span class="blue" data-btn="btn" onclick="delRow(this)">删除</span>');
		        tr.append(td);
		    }else{
		        td.append('<span class="blue" data-btn="btn" onclick="showRow(this)">查看</span>');
		        tr.append(td);
		    }
			return tr;
		}

		/**
		 * 编辑实际拨款计划
		 * @param ele
		 */
		function editActual(ele){
		        div=$(ele).closest('div[data-flag]');
		        var index = div.index();
			       $.getHtml({
		       		url:"/sop/html/operation_appr_actual.html",//模版请求地址
		       		data:"",//传递参数
		       		okback:function(){
		       			  $("#appr_actual_title").html('编辑实际注资计划');
		                   var json = getData(div);
		                   if(json['id']){
		                   $("#actual-form").find("[name='id']").val(json['id']);
		                   }
		                   $("#actual-form").find("[name='code']").val(json['code']);
		                   $("#actual-form").find("[name='field1']").val(json['field1']);
		                   $("#actual-form").find("[name='field2']").val(json['field2']);
		                   $("#actual-form").find("[name='field3']").val(json['field3']);
		                   $("#actual-form").find("[name='index']").val(index);
		                   
		       		}
			      }); 
		}
		/**
		 * 删除实际拨款计划
		 * @param ele
		 */
		function delActualRow(ele){
		      div=$(ele).closest('div[data-flag]');
		      var id = div.find("[name='id']").text();
		      if(typeof id != 'undefined' && id>0)
			  {
					deletedRowIds.push(id);
			  }
		      div.remove();
		}

		/**
		 * 获取分期拨款表单数据
		 * @param div
		 * @returns {___anonymous6377_6378}
		 */
		function getData(div){
			var json={};
		    var list = div.find("*[name]");
	        $(list).each(function(){
	           var key = "";
	           var value = "";
	           var tagName = $(this).get(0).tagName;

	           if(tagName == "INPUT"|| tagName == "TEXTAREA"){
					 key = $(this).attr("name");
					 value = $(this).val();
	           }
	           if(tagName=="SELECT"){
	              key = $(this).attr("name");
	              value = $(this).find("option:selected").val();
	           }
	           if(tagName == "SPAN"){
	             key = $(this).attr("name");
	             value = $(this).text();
	             if(!value){
	             	value= null;
	             }
	           }
	           json[key]=value;
	     })

	     return json;
		}
		/**
		 * 获取实际拨款计划列表数据
		 * @param div
		 * @returns {Array}
		 */
		function getDataList(div){
			var dataList = [];
		    var list = div.find(".team_div");
		     $(list).each(function(){
		     	 var json = {};
		     	 var data = $(this).find("*[name]");
		          $(data).each(function(){
		                var key = "";
		                var value = "";
		                var tagName = $(this).get(0).tagName;
		
		                if(tagName == "INPUT"|| tagName == "TEXTAREA"){
		  				 key = $(this).attr("name");
		  				 value = $(this).val();
		                }
		                if(tagName=="SELECT"){
		                   key = $(this).attr("name");
		                   value = $(this).find("option:selected").val();
		                }
		                if(tagName == "SPAN"){
		                  key = $(this).attr("name");
		                  value = $(this).text();
		                  if(!value){
		                  	value= null;
		                  }
		                }
		                json[key]=value;
		          })
		          dataList.push(json);
		     })
	     return dataList;
		}
		
		
		