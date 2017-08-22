<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目详情</title>
<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script src="<%=path%>/js/hologram/hologram_common.js"></script>
</head>

<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>


<body>
<!--隐藏-->

<div class="bj_hui_on"></div>
<jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include>               
<div class="tabtxt" id="page_all">
<!--tab-->


<!--tab end-->
</div>


				

<script type="text/javascript">
var key = Date.parse(new Date());
var keyJSON={};
var deleteJSON={};
var mustids = "${mustids}";
	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO5", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				console.log(entity);
				$("#page_list").tmpl(entity).appendTo('#page_all');
				$(".section").each(function(){
					$(this).showResults(true);
					var table = $(this).find('.mb_24 table');
					table.each(function(){
    					resizetable($(this))
						if($(this).find('tr').length<=1){
							$(this).hide();
							if($(this).parents('dl').find('dd:gt(0)').length<=0){
								$(this).parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
							} 
						}
						else{
							$(this).show();
						}
					})
				});
				mustData(projectInfo.id,0);
				fun_click();
			} else {

			}
		})


	
$(function() {
	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var _this = $(this).parents(".radius");
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		$(".bj_hui_on").hide();
		btn_disable(0);
		$(".h#a_"+id_code).css("background","#fff");
		event.stopPropagation();
	});
	
	//通用编辑显示
	$('div').delegate(".h_edit_btn", "click", function(event) {	
		var section = $(this).parents('.section');
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');
		var str ="";
		if($(this).parents(".h_btnbox").siblings(".h_title").find("span").is(":visible")){
			str =" <span style='color:#ff8181;display:inline'>（如果该项目涉及此项内容，请进行填写，反之可略过）</span>";
		}else{
			str ="";
		}
		event.stopPropagation();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
					if(id_code=="NO5_1"){   //竞争俩字
						$("#b_"+id_code).closest(".section").find(".h_title").text("竞争");
					}
					sec.showResults();
					var table = $(this).find('.mb_24 table');
					table.each(function(){
					    resizetable($(this))
						if($(this).find('tr').length<=1){
							$(this).hide();
							if($(this).parents('dl').find('dd:gt(0)').length<=0){
								$(this).parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
							} 
						}
						else{
							$(this).show();
						}
					})
					$(".h#a_"+id_code).css("background","#fafafa");
					$("#"+id_code).hide();
					validate();
					btn_disable(1);
					$("#b_"+id_code).validate();
					$(".bj_hui_on").show();
					section.find(".h_title span").remove();
					section.find(".h_title").append(str);
					//文本域剩余字符数
					var textarea_h = section.find('.textarea_h');
					for(var i=0;i<textarea_h.length;i++){
						var len=textarea_h.eq(i).val().length;
						var initNum=textarea_h.parent('dd').find(".num_tj").eq(i).find("label").text();
						textarea_h.parent('dd').find(".num_tj").eq(i).find("label").text(initNum-len);
					}
					/* 文本域自适应高度 */
					for(var i=0;i<$("textarea").length;i++){
						var textareaId=$("textarea").eq(i).attr("id");
						autoTextarea(textareaId);
					}
					edit_bsaicfun();
				} else {

				}
		}) 
		 $(".editable").each(function(){resizetable($(this))})
		//编辑表格显示隐藏
		 check_table();
	});
	//通用保存
	$('div').delegate(".h_save_btn","click",function(event){
		var btn = this;
		var save_this = $(btn).parents('.radius');
		event.stopPropagation();
        var sec = $(this).closest('form');
        var id_code = $(this).attr('attr-save');
        var dt_type_3 = $("#b_" + id_code).find("dt[data-type='3'],dt[data-type='13']");
		var fields = sec.find("input[type='text'][data-title-id],input:checked,textarea,radio,select");
		var fields_value1=sec.find("li[class='check_label active'],li.active");
		var data = {
			projectId : projectInfo.id
		};
		if(!$("#b_"+id_code).validate().form())
		{
			return;
		}
		if($(this).closest('form').attr("id") =="b_NO3_1"){
        		//表格
        		var titleId = sec.find("table.editable").attr("data-title-id");
                var json = {"projectId":projectInfo.id,"titleId":titleId};
        		var dataList = new Array();
        		$.each(sec.find("table.editable"),function(){
        			$.each($(this).find('tr:gt(0)'),function(){
        				var row = $(this).data("person");
        				if(row.id=="")
        				{
        					row.id=null;
        				}
        				row.projectId=projectInfo.id;
        				dataList.push(row);
        			});
        		});
                json["dataList"]=dataList;
                if(dataList.length>10){
                    alert("最多只能添加10条记录!")
                    return false;
                }
              //团队表格显示隐藏
        		$.each($('table.editable'),function(){
        			var table_id = $(this).attr('data-title-id');
        			var noedi_table = $('table[data-title-id='+table_id+']');
        			if($(this).find('tr:gt(0)').length<=0){
        				if(noedi_table.parents('dl').find('dd').length<= 2){
        					$('table[data-title-id='+table_id+']').parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
        				}
        				noedi_table.hide();
        			}
        			else{
        				noedi_table.show();
        				noedi_table.parents('dl').find('.no_enter').remove();
        				
        			}
        		})
                sendPostRequestByJsonObj(
                platformUrl.saveTeamMember,
                json,
                function(data) {
                    var result = data.result.status;
                    if (result == 'OK') {
                    	updateInforTime(projectInfo.id,"NO3");
                        layer.msg('保存成功');
                    	$(".h#a_"+id_code).css("background","#fff");
                        var parent = $(sec).parent();
                        var id = parent.data('sectionId');
                        $(btn).next().click();
                        refreshSection(id);
                        toggle_btn($('.anchor_btn span'),0,save_this);
                    } else {

                    }
            })
            return;
        }
          //团队表格显示隐藏
    		$.each($('table.editable'),function(){
    			var table_id = $(this).attr('data-title-id');
    			var noedi_table = $('table[data-title-id='+table_id+']')
    			if($(this).find('tr:gt(0)').length<=0){
    				if(noedi_table.parents('dl').find('dd').length<= 2){
    					$('table[data-title-id='+table_id+']').parents('dl').find('dt').after('<dd class="no_enter">未填写</dd>');
    				}
    				noedi_table.hide();
    			}
    			else{
    				noedi_table.show();
    				noedi_table.parents('dl').find('.no_enter').remove();
    				
    			}
    		})

        //股权结构合理性不能超过10条记录
      /*   if($(this).closest('form').attr("id") =="b_NO3_8"){
            if ( !validateCGR() ){
                return false;
            }
        } */

		//普通结果
        var fieldsValidate = true;
		var infoModeList = new Array();
		//13type
		$.each(fields_value1, function() {			
			var field = $(this);			
			var _tochange =field.parents("dd").prev().attr("tochange");
			if(_tochange==undefined){
				_tochange=false;
			}			
			if(_tochange == true||_tochange == "true"){
				var _resultId = field.attr("resultId");
				if(_resultId==undefined  || _resultId=="undefined"){
					_resultId=null
				}
				var infoMode = {
						titleId : field.data('titleId'),
						type : field.data('type'),
						tochange:_tochange,
						resultId:_resultId,
						value : field.attr('value')
					};
				var type = field.data('type');
				if(type==13){
					var field_v = field.data('id');
	                 var last_id = field.closest('ul').find('li.check_label:last').attr('data-id');
	                 var dt = field.closest('dt[data-type="13"]');
	                 console.log(field_v);
	                 console.log(last_id);
	                 if ( field_v == last_id)
	                 {
	                 	//其他
	                     infoMode.remark1 = field.closest('.h_edit_txt').find('input:last').val();
	                 }
	                 else
	                 {
	                     infoMode.remark1 = '' ;
	                 }
				}
                 
				console.log(infoMode);
				infoModeList.push(infoMode);
			}
			
		});
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type') || field.closest('.h_edit_txt').find(':first-child').data('type');
			var _tochange =field.parents("dl.h_edit_txt").find("dt").attr("tochange");
			var sele = field.parent().get(0).tagName;
			if(sele=="SELECT"){
				var _resultId = field.parent().attr("resultId");
			}else{
				var _resultId = field.attr("resultId");
			}
			if(_tochange==undefined){
				_tochange=false;
			}
			var infoMode = {
				titleId	: field.data('title-id') || field.closest('.h_edit_txt').find(':first-child').data('title-id'),
				tochange:_tochange,
				resultId:_resultId,
				type : type
			};
			if(type==2 || type==4 || type==14)
			{
				infoMode.value = field.val();
				console.log(infoMode+'--------')
			}
			else if (type==3)
			{
				infoMode.value = field.data('id');
			}
            else if(type==12)
            {
                if (field.is('textarea') || field.is('input[type="text"]')){

                    //infoMode.remark1 = field.val()

                }else{
                    infoMode.value = field.val();
                    var field_v = field.val();
                    var last_id = field.closest('ul').find('input[type="radio"]:last').attr('data-id');
                    var dt = field.closest('dt[data-type="12"]');

                    if ( field_v == last_id)
                    {
                        infoMode.remark1 = field.closest('.h_edit_txt').find('input:last').val();
                        /*if(infoMode.remark1 == null || infoMode.remark1 == undefined || $.trim(infoMode.remark1) == '') {
                            layer.msg('不能为空!');
                            field.closest('.h_edit_txt').find('input:last').focus();
                            fieldsValidate = false;
                        }*/
                    }
                    else
                    {
                        infoMode.remark1 = '' ;
                    }
                }
            }
            
			else if(type == 15)
			{
                var _has = false;
                var str=field.val();
				var str=str.replace(/\n|\r\n/g,"<br>");
				var str=str.replace(/\s/g,"&nbsp;");
                $.each(infoModeList,function(i,n){
                    if(infoModeList[i].type == 15 && infoModeList[i].titleId == infoMode.titleId) {
                        _has = true;
                        if(!infoModeList[i].hasOwnProperty('remark1')){
                            infoModeList[i].remark1 = str;
                        }else{
                            infoModeList[i].remark2 = str;
                        }
                    }
                });

                if( !_has ) {
                    infoMode.remark1 = str;
                }else {
                    infoMode = null;
                }
            }
			else if(type==1 || type==19 )
			{	
				infoMode.remark1 = field.val();
			}
			else if( type==20 )
			{	
				infoMode.remark1 = field.val();
				//infoMode.remark2 = field.parent().parent().children($('select option:selected')).text();
				var id  = infoMode.titleId
				var options=$("#"+id+"_select option:selected")
				var name = options.text();
				var value =options.val();
				infoMode.remark2 = name+"p"+value;
			}
			else if(type==8)
			{
				var str=field.val();
				var str=str.replace(/\n|\r\n/g,"<br>");
				var str=str.replace(/\s/g,"&nbsp;");
				infoMode.remark1 = str;
			}

			if (infoMode != null) {
                infoModeList.push(infoMode);
            }

		});
 		data.infoModeList = infoModeList;
        if ( fieldsValidate == false )
        {
            return false;
        }

		//表格
		var infoTableModelList = new Array();
		$.each(sec.find("table.editable"),function(){
			$.each($(this).find('tr:gt(0)'),function(){
				var row = $(this).data();
				if(row.id=="")
				{
					row.id=null;
				}
				infoTableModelList.push($(this).data());
			});
		});

		data.infoTableModelList = infoTableModelList;

        var h_cancel_btn_code = $(btn).next().attr('attr-session');
        if (h_cancel_btn_code=='1324'){
            data.deletedRowIds = deletedRowIdsGq;
        }
 		
        //多选不选择的时候：
        var deletedResultTids = new Array();
        $.each(dt_type_3, function() {
            var _this = $(this);
            var active = _this.parent().find('dd .active');
            if(!(active && active.length > 0)){
                var tid = _this.data('id');
                deletedResultTids.push(tid);
            }
        });
        data.deletedResultTids = deletedResultTids;
        sendPostRequestByJsonObj(
        			platformUrl.saveOrUpdateInfo ,
        			data,
        			function(data) {
        				var result = data.result.status;
        				if (result == 'OK') {
        					updateInforTime(projectInfo.id,"NO3");
        					layer.msg('保存成功');
        					$(".bj_hui_on").hide();
                            if (h_cancel_btn_code=='1324'){
                                deletedRowIdsGq = new Array();
                            }
        					var parent = $(sec).parent();
        					//console.log(parent[0]);
        					var id = parent.data('sectionId');
        					//console.log(id);
        					$(btn).next().click();
        					refreshSection(id);
        					toggle_btn($('.anchor_btn span'),0,save_this);
        					
        				} else {

        				}
        		})
	});
});
	function getDetailUrl(code)
	{
		if(code == 'equity-structure')
		{
			return '<%=path%>/html/funcing_add_gd.html';
		}
		else if(code == 'investor-situation')
		{
			return '<%=path%>/html/funcing_add_tz.html';
		}
		else if(code =='operation-indices')
		{
			return '<%=path%>/html/fincing_add_yx.html';
		}
		else if(code == 'valuation-reference')
		{
			return '<%=path%>/html/fincing_add_tl.html';
		}
		else if(code == 'financing-milestone')
		{
			return '<%=path%>/html/fincing_add_jd.html';
		}else if(code == 'competition-comparison')
		{
			return '<%=path%>/html/compete_save.jsp';
		}
		return "";
	}
</script>

</body>


</html>