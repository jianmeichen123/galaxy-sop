$(function(){ 
	$('.edit_basic_table tr').hover(function(){
		return false;
	})
	$('[data-on="close"]').on('click',function(){
		var close=$(this).attr('data-name')
		$('.'+close+'_current').hide();//basic_current
		$('.'+close+'_on').hide();
		$('.'+close+'_center').show();
		$('.bj_hui_on').hide();
		$('.bj_hui_on_common').hide();
		$('.tip-yellowsimple').hide();
		$("label.error").hide();
		$("body").css('overflow-y','auto');
		$('.radio_cont').removeClass('radio_checked');
		
	})
	 $("[data-on='data-open-basic']").click(function (){ 
        	    isDelete=[];
				if($(this).hasClass('limits_gray'))
				{
					return;
				}
				var open=$(this).attr('data-cont');
				var common = $(this).attr('data-name');
				//外层div一直显示 basic_on  show
				$('.'+common+'_on').show();
				//内部弹窗根据条件显示
				$('.'+open+'_current').show();
				$('.bj_hui_on_common').show();//遮罩层
				$("body").css('overflow','hidden');
				//浏览器窗口带下改变，弹层重新定位
				popMiddle()
				function popMiddle(){
					var wh = parseInt($(".sop_common_width").outerWidth(true)),
					ht = parseInt($(".sop_common_width").outerHeight(true));
					var win_w = $(window).width(),
					win_h = $(window).height(),
					win_x = (win_w-wh)/2,
					win_y = (win_h-ht)/2;
					//弹出层定位+显示
					$(".sop_common_width").Fixed({
						x:win_x,
						y:win_y
					});
				}
				$(window).resize(function(){
					popMiddle()
				});
				
				//错误提示
				$('#finance_status_sel').closest('tr').css('height','auto');
				$('.finance_status_ul').css('margin-top','0px');
				//基本信息修改
				$("#project_name_edit").val(projectInfoDetail.projectName);
				$("#create_date_edit").text(projectInfoDetail.createDate);
				$("#updateDate_edit").text(projectInfoDetail.updateDate);
				$("#createUname_edit").text(projectInfoDetail.createUname);
				$("#projectCareerline_edit").text(projectInfoDetail.projectCareerline);
				$("#projectType_edit").text(projectInfoDetail.type);
				$("#projectProgress_edit").text(projectInfoDetail.progress);
				$("#projectStatusDs_edit").text(projectInfoDetail.projectStatusDs);
				$("#financeStatusDs_edit").text(projectInfoDetail.financeStatusDs);
				$("#industry_own_sel").val(projectInfoDetail.industryOwnDs);
				$("#finance_status_sel").val(projectInfoDetail.financeStatusDs)
				$("input[name='projectSource']").val(projectInfoDetail.faFlagStr).attr('data-flag',projectInfoDetail.faFlag);
				$("#faNameEdit").val(projectInfoDetail.faName);
				//投资形式合投，领头编辑页面投资列表处理
				
				 p=projectInfoDetail.industryOwn;
			    fs=projectInfoDetail.financeStatus;
			    var sectionName = $(this).data('name');
			    if('basic' == sectionName)
		    	{
			    	//融资
			    	sendGetRequest(platformUrl.queryAllTitleValues+'FNO1?reportType=4', null,CallBackB);
			    	sendGetRequest(platformUrl.searchDictionaryChildrenItems+"industryOwn",null,CallBackA);
			    	
		    	}
			    $("input[name='projectSource']").val(projectInfoDetail.faFlagStr).attr("m-val",projectInfoDetail.faFlag);
				if(projectInfoDetail.faFlagStr=="创业者"||projectInfoDetail.faFlagStr=="外部独立合伙人"||projectInfoDetail.faFlagStr=="自开发"){
					$(".historyData").show();
					$("input[name=projectSource]").val("请选择").attr({
						"data-flag":"",
						"m-val":"",
						
					})
				}else{
					$(".historyData").hide();
				}
			    if(projectInfoDetail.faFlag){
					$(".trSouce").hide();					
					var val = projectInfoDetail.faFlag;
					var className = $("#selectSource").find("li[value="+val+"]").attr("code");
					$(".trSouce."+className).show(); 
				} 
				
				sendGetRequest(platformUrl.editProjectAreaInfo + projectInfoDetail.id + "/NO1_1",null,resultId);
				function resultId(data){
					var domList = $(".trSouce");
					var valList =data.entity.childList ;
					$.each(domList,function(){
						var domId = $(this).find("input").attr("data-title-id");
						if(!domId){return false;}
						var resList = valList.filter(function(val){return val.id==domId})[0]; 
						if(resList.resultList){
							$(this).find("input").attr("data-result-id",resList.resultList[0].id);		
							if(resList.resultList[0].contentDescribe1){
								$(this).find("input").val(resList.resultList[0].contentDescribe1)
							}					
						}
					})
					projectInfoDetail.faName 
					
				}
				//数据互通加的resultID
				var reportly = reportResult[0].childList.filter(function(val){return val.titleId=="1120"})[0].resultId; 
				$("input[data-title-id=1120]").attr("data-result-id",reportly);
				var reportSelect = reportResult[0].childList.filter(function (val){return val.titleId=="1118"});
				$.each(reportSelect,function(){
					 
					$("select#selectRadio").find("option[value="+$(this).value+"]").attr("data-result-id",$(this).resultId)
				})
				
				//项目承揽人 
				var clPerson=projectInfoDetail.listInfoTitle; 
				if(projectInfoDetail.faFlag=="2262"&&clPerson.resultList){
					$(".trSouce").hide();
					$("select[data-title-id=1118]").closest(".trSouce").show();
					var valueL="";
					$.each(projectInfoDetail.listInfoTitle.resultList,function(){ 
						var that=$(this)[0];
						
						$("#selectRadio").find("option[value="+that.contentChoose+"]").attr("selected",true); 
						$.each($("ul.selectpicker li"),function(){ 
							var that2=$(this);							
							var spanValue = that2.find("span").attr("data-value");							
							if(that.contentChoose==spanValue){ 
								var n=that2.find("span").text().indexOf('|');
								valueL+=that2.find("span").text().substring(0,n)+"、";
								//valueL+=that2.find("span").text()+"、";
								that2.addClass("selected");
								if(that2.text()=="非投资线员工"){
									$(".addpro-input").show().val(that.contentDescribe1).attr("ovalue",that2.find("span").data("value"));
								}
								return false;
							}
						}) 
					}) 
					valueL=valueL.substring(0,valueL.length-1);
					/*var _title=title.split('、');
		            for(var i=0;i<_title.length;i++){
		            	var n=_title[i].indexOf('|');
		            	arr.push(_title[i].substring(0,n));
		            }*/
					$("button.selectpicker ").attr("title",valueL);
					$("button.selectpicker span").text(valueL)
					$('#selectRadio').selectpicker({
					 	dropupAuto:false
		            });
					selectRadio();
					
				}
				if(typeof(projectInfoDetail.faFlag)!="underfined" && projectInfoDetail.faFlag=="projectSource:1"){
					$("select[name='projectSource']").find("option[value='"+projectInfoDetail.faFlag+"']").prop("selected",true);
					$("#faNameEdit").css("display","block");
					$("#faNameEdit").attr('required','required');
				}else{
					$("select[name='projectSource']").find("option[value='"+projectInfoDetail.faFlag+"']").prop("selected",true);
					$("#faNameEdit").css("display","none");
				}
			
			})
			function CallBackB(data){
		    var _dom=$("#finance_status_sel").next('ul');
		        _dom.html("");
		        //_dom.append('<option value="">--请选择--</option>');
		    var childNum = _dom.find("option").length;
		    var valueId=$("#financeStatusDs").attr("value");
		    var resultId=$("#financeStatusDs").attr("data-result-id");
		    var entity=data.entity.childList[0];
		    $("#finance_status_sel").attr({"data-type":entity.type,"data-result-id":resultId});
		    if(!childNum || childNum !=0 ){
		    	$.each(entity.valueList,function(){ 
		    		_dom.append("<li value='"+this.id+"' data-title-id='"+this.titleId+"' text='"+this.name+"'>"+this.name+"</li>");
					
				});
		    }
		    CallBackD(data)
		    $("select[data-title-id]").change(function(){
		    	$(this).attr("tochange",true)
		    })
		    $("#selectSource li").click(function(){ 
				var code = $(this).attr("code"); 
				$(".trSouce").hide();
				$(".trSouce."+code).show(); 
				$("#selectRadio option").attr("selected",false);
				$("button.selectpicker").attr("title",'请选择');
				$("button.selectpicker span").text("请选择");
				$("ul.selectpicker li").removeClass("selected");
				$(".trSouce input").val("");
				$(".trSouceOther").hide().val("");
				$(".historyData").hide();
				 $('#selectRadio').selectpicker({
		   			 dropupAuto:false
	               });
		    })
		    $(".selectcheck ul.selectpicker li").click(function(){
		    	$("#selectRadio-error").remove();
		    })
			$("#dropdown ul li").click(function(){
				var target = $(this).closest('#dropdown').find('input');
				target.removeClass('up')
				var txt = $(this).text(); 
				target.val(txt);
				$("#dropdown ul").hide(); 
				$(this).closest('#dropdown').find('input').attr('tochange',true);
				if(txt!=''){
					$('#finance_status_sel-error').hide();
					$('#finance_status_sel-error').closest('tr').css('height','auto');
					$('.finance_status_ul').css('margin-top','0px');
				}
		});
		    			 
		}
	function CallBackD(data){
		   var _dom=$(".input_select[name='projectSource']").next('ul');
		        _dom.html("");
		        //_dom.append('<option value="">--请选择--</option>');
		    var childNum = _dom.find("option").length;
		    var valueId=$("#financeStatusDs").attr("value");
		    var resultId=$("#financeStatusDs").attr("data-result-id"); 
		    var entity=data.entity.childList.filter(function(val){return val.id=="7032"})[0];
		    $("#finance_status_sel").attr({"data-type":entity.type,"data-result-id":resultId});
		    if(!childNum || childNum !=0 ){
		    	$.each(entity.valueList,function(){ 
		    		_dom.append("<li value='"+this.id+"' code='"+this.code+"' data-title-id='"+this.titleId+"' text='"+this.name+"'>"+this.name+"</li>");
					
				});
		    }
		  //项目承揽人渲染器
		    /*var dataresu =data.entity.childList.filter(function(val){return val.id=="7033"})[0];
		    var res=""
		    $.each(dataresu.valueList,function(){	
		    	res+="<option value='"+this.id+"' data-title-id='"+this.titleId+"'>"+this.name+"</option>"
		    } ); 
				$("#selectRadio").html(res).show();


		    $('#selectRadio').selectpicker({
	   			 dropupAuto:false
               });
		    $("#selectRadio").css("display","block")
		    selectRadio();*/
		}
	//项目承揽人下拉渲染
	 sendGetRequest(platformUrl.searchCLR, null,CallBackE);
	 function CallBackE(data){ 
	 	 var data_list = data.entityList;
	 	var res="";
	 	 $.each(data_list,function(){	
	 		 res+="<option value='"+this.id+"' data-type='23' data-title-id='1118'>"+this.realName+'|'+this.departmentName+"</option>"
		    } ); 
				$("#selectRadio").html(res).show();


		    $('#selectRadio').selectpicker({
	   			 dropupAuto:false
            });
		    $("#selectRadio").css("display","block")
		    selectRadio();
	 	}
	 
		function CallBackA(data){
		       var _dom=$("#industry_own_sel").next('ul');
		           _dom.html("");
		           //_dom.append('<option value="">--请选择--</option>');
		       var childNum = _dom.find("option").length;
			    if(!childNum || childNum !=0 ){
			    	$.each(data.entityList,function(){
							if(this.code==p){
								_dom.append("<li selected value='"+this.code+"'>"+this.name+"</li>");
							}else{
								_dom.append("<li value='"+this.code+"'>"+this.name+"</li>");
							}
					});
			    }
				$("#dropdown ul li").click(function(){
					var target = $(this).closest('#dropdown').find('input');
					target.removeClass('up')
					var txt = $(this).text(); 
					target.val(txt)
					$("#dropdown ul").hide(); 
			});
				 
			}
		function CallBackC(data){
		       var _dom=$("input[name='projectSource']").next('ul');
		           _dom.html("");
		           //_dom.append('<option value="">--请选择--</option>');
		       var childNum = _dom.find("option").length;
			    if(!childNum || childNum !=0 ){
			    	$.each(data.entityList,function(){
							if(this.code==p){
								_dom.append("<li selected value='"+this.code+"'>"+this.name+"</li>");
							}else{
								_dom.append("<li index='"+this.value+"' value='"+this.code+"'>"+this.name+"</li>");
							}
					});
			    }
				$("#dropdown ul li").click(function(){
					var target = $(this).closest('#dropdown').find('input');
					target.removeClass('up')
					var txt = $(this).text(); 
					var faFlag=$(this).attr('value');
					target.val(txt).attr('data-flag',faFlag);
					$("#dropdown ul").hide(); 
					if(txt=="FA"){
						$("#projectSource-error").hide();
						$(this).closest('td').find(".input_FA").show();
						$(this).closest('td').find(".input_FA").attr("required","required");
					}else{
						$(this).closest('td').find(".input_FA").hide();
						$(this).closest('td').find(".input_FA").remove("required");
						$("#faNameEdit-error").hide();
					}
			});
			}
		
		//保存
		$("[data-on='save_basic']").click(function(){
			var s_type=$(this).attr("save_type");
			var data="";
			if(s_type=="save_basic"){
				data=getUpdateDataBasic();
				if(!$("#basicForm1").validate().form())
				{						
					if($('#finance_status_sel-error').is(':visible')){
						$('#finance_status_sel-error').closest('tr').css('height','65px');
						$('.finance_status_ul').css('margin-top','-30px');
					}else{
						$('#finance_status_sel-error').closest('tr').css('height','auto');
						$('.finance_status_ul').css('margin-top','0px');
					}
					return;
				}
				if($("input[name=projectSource]").val()=="请选择"){
					$(".historyData").text("项目来源不能为空")
					return
				}
				sendPostRequestByJsonObj(platformUrl.updateProject,data, function(data2){					
					if(data2.result.status=="OK"){ 
						saveBaseInfo("basicForm1");
						if(data2.result.errorCode=='mccf'){   //项目名重复
							//layer.msg(data2.result.message);
							return;
						}
						//弹窗关闭
							var close="basic"
							$('.'+close+'_current').hide();//basic_current
							$('.'+close+'_on').hide();
							$('.'+close+'_center').show();
							$('.bj_hui_on_common').hide();
							$("body").css('overflow-y','auto');						
							//updateReportMoneyBasic();						 
					}else {
						if(data2.result.errorCode!="mccf"){
							layer.msg(data2.result.message);
						}
					}
					
				});
			}
		})
		
		function getUpdateDataBasic(){
			var id=pid;
			var pname=$("#project_name_edit").val().trim();
			var industry_own=$("#industry_own_sel").attr('m-val');
			//处理投资形式
			var investForm= $(".radio_checked input").attr("value");
			var arr=[];
			if(investForm=="financeMode:1"||investForm=="financeMode:2"){
				var jointDeliverys= $(".block_inputs");
				for(var i=0;i<jointDeliverys.length;i++){
					var obj={"deliveryName":"",
							 "deliveryAmount":"",
							 "deliveryCurrency":"",
							 "deliveryShareRatio":"",
						    };
					var jointDelivery=jointDeliverys[i];
					    var isUpdate=jointDelivery.childNodes[0].childNodes[0].getAttribute("data-id");
					    if(isUpdate!=null){
					    	obj.id=isUpdate;
					    }
				        obj.deliveryName=jointDelivery.childNodes[0].childNodes[0].value;
				        obj.deliveryAmount=jointDelivery.childNodes[1].childNodes[0].value;
				        obj.deliveryCurrency=jointDelivery.childNodes[2].childNodes[0].firstElementChild.getAttribute("m-val");
				        obj.deliveryShareRatio=jointDelivery.childNodes[3].childNodes[0].value;
				        arr[i]=obj;
				}
			}
			var formatData={"id":id,
					       "projectName":pname,
					       "industryOwn":industry_own,
		  	               "financeMode":investForm,
                           "jointDeliveryList":arr,
                           "isDelete":isDelete
			};
			return formatData;
		}
		
		//保存后刷新
	/*	function updataReportBasic(projectInfoList){
			if(projectInfoList && projectInfoList.length>0){
		    	$.each(projectInfoList,function(i,o){
			    	if(o.nodeName=='本轮融资轮次'){
			    		$("label[data-title-id='"+o.titleId+"']").attr({"value":o.value,"data-result-id":o.resultId});
			    	}		    	
			    })
		    }
		}*/
})
function selectRadio(){
	$("#selectRadio").change(function(){
		var otherValue = $(this).find("option").last().val();
		var value = $(this).val();
		if(value==null){
			$(".selectcheck .trSouceOther").hide().val("");
			return;
		}
		var filt = value.filter(function(val){
			return val==otherValue
		});
		if(filt.length>0){
			$(".selectcheck .trSouceOther").show();
			$(".selectcheck .trSouceOther").attr("ovalue",filt[0])
		}else{
			$(".selectcheck .trSouceOther").hide().val("");
		}
	})
}