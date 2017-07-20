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
<script src="<%=path %>/js/validate/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/validate/messages_zh.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/hologram/hologram_common.js" type="text/javascript"></script>
<script src="<%=path%>/js/hologram/team_pop.js"></script>
<c:set var="projectId" value="${sessionScope.curr_project_id}" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>

</head>
<body>
<!-- <ul class="h_navbar clearfix">
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基础<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
                </ul> -->
  <!--隐藏-->
<div class="bj_hui_on"></div>
 <div id="tab-content">
 <jsp:include page="jquery-tmpl.jsp" flush="true"></jsp:include>
		<div class="tabtxt" id="page_all">
		<!--tab-->


			<!--tab end-->
		</div>
	</div>

<script src="<%=path%>/js/hologram/jquery.tmpl.js"></script>
<script type="text/javascript">
    // 核心创始团队 表格删除行使用
    var deletedRowIds = new Array();
    // 股权结构合理性 表格删除行使用
    var deletedRowIdsGq = new Array();

	//整体页面显示
	sendGetRequest(platformUrl.queryAllTitleValues + "NO3", null,
		function(data) {
			var result = data.result.status;
			if (result == 'OK') {
				var entity = data.entity;
				$("#page_list").tmpl(entity).appendTo('#page_all');
				$(".section").each(function(){
					$(this).showResults(true);
					var table = $(this).find('.mb_24 table');
					table.each(function(){
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
			} else {

			}
		})
	//通用编辑显示
	$('div').delegate(".h_edit_btn","click",function(event){
		var section = $(this).parents('.section');
		var id_code = $(this).attr('attr-id');
		var sec = $(this).closest('.section');
		var sTop=$(window).scrollTop();
		event.stopPropagation();
		 sendGetRequest(platformUrl.queryAllTitleValues + id_code, null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK') {
					var entity = data.entity;
					$("#ifelse").tmpl(entity).appendTo("#a_"+id_code);
                    bindChange();
					sec.showResults();
					 bindChangeType13();
					$(".h#a_"+id_code).css("background","#fafafa");
					$("#"+id_code).hide();
					validate();
					btn_disable(1);
					setReqiured();
					/* isMust("#b_"+id_code); */
					$("#b_"+id_code).validate();
					$(".bj_hui_on").show();
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
					//检查表格tr是否10行
					check_table_tr_edit();
					edit_bsaicfun();
				} else {

				}
				
		})
		$('body,html').scrollTop(sTop);  //定位
		//编辑表格显示隐藏
		 check_table();
	});
	//点击事件

	//通用取消编辑
	$('div').delegate(".h_cancel_btn","click",function(event){
		var id_code = $(this).attr('attr-hide');
		$('#'+id_code).show();
		$('#b_'+id_code).remove();
		$(".bj_hui_on").hide();
		btn_disable(0);
		$(".h#a_"+id_code).css("background","#fff");
		dtWidth();
		event.stopPropagation();
        if (id_code =='NO3_1')
        {
            deletedRowIds = new Array();
        }
        else if (id_code=='NO3_8')
        {
            deletedRowIdsGq = new Array();
        }
	});
	//通用保存
	$('div').delegate(".h_save_btn","click",function(event){
		var btn = this;
		var save_this = $(btn).parents('.radius');
		event.stopPropagation();
        var sec = $(this).closest('form');
        var id_code = $(this).attr('attr-save');
        var dt_type_3 = $("#b_" + id_code).find("dt[data-type='3'],dt[data-type='13']");
		var fields = sec.find("input[type='text'][data-title-id],input:checked,textarea,radio,li[class='check_label active'],select");
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
        				var row = $(this).data("obj");
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
                    	updateInforTime(projectInfo.id,"teamTime");
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
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type') || field.closest('.h_edit_txt').find(':first-child').data('type');
			var _tochange =field.parents("dd").prev().attr("tochange");
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
            else if(type==13)
            {
                    infoMode.value = field.data('id');
                    var field_v = field.data('id');
                    var last_id = field.closest('ul').find('li.check_label:last').attr('data-id');
                    var dt = field.closest('dt[data-type="13"]');
                    if ( field_v == last_id)
                    {
                        infoMode.remark1 = field.closest('.h_edit_txt').find('input:last').val();
                    }
                    else
                    {
                        infoMode.remark1 = '' ;
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
			else if(type==1)
			{
				infoMode.remark1 = field.val();
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

        var h_cancel_btn_code = $(btn).next().attr('attr-hide');

        if (h_cancel_btn_code=='NO3_1'){
            data.deletedRowIds = deletedRowIds;
        }else if (h_cancel_btn_code=='NO3_8'){
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
        console.log("@#$#%#$%^$%^%");
        console.log(data);
        data.deletedResultTids = deletedResultTids;
        sendPostRequestByJsonObj(
        			platformUrl.saveOrUpdateInfo ,
        			data,
        			function(data) {
        				var result = data.result.status;
        				if (result == 'OK') {
        					updateInforTime(projectInfo.id,"teamTime");
        					layer.msg('保存成功');
        					$(".bj_hui_on").hide();
                            if (h_cancel_btn_code=='NO3_1'){
                                deletedRowIds = new Array();
                            }else if (h_cancel_btn_code=='NO3_8'){
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
function refreshSection(id)
{
	var sec = $(".section[data-section-id='"+id+"']");
    sec.find("dd[data-type='3']").text('未选择');
	sec.showResults(true);
	btn_disable(0);
	
}
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
	}else if (code =='team-members'){

	    return '<%=path%>/html/team_compile.html';
	}else if(code == 'share-holding')
    {
        return '<%=path%>/html/team_add_cgr.html';
    }
	return "";
}
function editRow(ele)
{
	var code = $(ele).closest('table').data('code');
	var row = $(ele).closest('tr');
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			$.each($("#detail-form").find("input, select, textarea"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				ele.val(row.data(name));
			});
			$("#detail-form input[name='index']").val(row.index());
			$("#save-detail-btn").click(function(){
                saveForm($("#detail-form"));
			});
		}//模版反回成功执行
	});
}

function delRow(ele)
{
	layer.confirm('是否删除?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero){
		var tr = $(ele).closest('tr');
		var id = tr.data('id');
        var formId = $(ele).closest('form').attr('id');
		if(typeof id != 'undefined' && id>0)
		{
            if( formId =='b_NO3_1') {
                deletedRowIds.push(id);
            }else if (formId =='b_NO3_8'){
                deletedRowIdsGq.push(id);
            }
		}
		tr.remove();
		check_table();   
		check_table_tr_edit();
		$(".layui-layer-close1").click();
	},function(index) {
	});
 

}
function addRow(ele)
{
   /*  if ( validateCGR() ) { */
        var code = $(ele).prev().data('code');
        $.getHtml({
            url:getDetailUrl(code),//模版请求地址
            data:"",//传递参数
            okback:function(){
				$('#qualifications_popup_name').html('添加简历');
				$('#qualifications_popup_name1').html('添加持股人');
                $("#detail-form input[name='projectId']").val(projectInfo.id);
                $("#detail-form input[name='titleId']").val($(ele).prev().data('titleId'));
                $("#detail-form input[name='code']").val($(ele).prev().data('code'));
                $("#save-detail-btn").click(function(){
                    saveForm($("#detail-form"));
                    check_table();
                    check_table_tr_edit();
                });
                $("#save_person_learning").click(function(){
                	check_table();
                	check_table_tr_edit();
                });
            }//模版反回成功执行
        });
    /* } */
}

/* function validateCGR(){
    var flag = true;
    var trsNum = $("form[id='b_NO3_8']").find('table').find('tr').length-1;
    if(trsNum>=10){
        layer.msg('最多只能添加10条记录!');
        flag = false;
    }
    return flag;
} */

function saveForm(form)
{
    if($(form).validate().form())
    {
        var data = $(form).serializeObject();
        saveRow(data);
    }
}

/**
 * 保存至到tr标签data属性
 */
function saveRow(data)
{
	data = JSON.parse(data);
	var titleId = data.titleId;
	var index = data.index;
	if(typeof index == 'undefined' || index == null || index == '')
	{
		var tr = buildRow(data,true);
		$('table[data-title-id="'+titleId+'"].editable').append(tr);
	}
	else
	{
		var tr = $('table[data-title-id="'+titleId+'"].editable').find('tr:eq('+index+')');
		for(var key in data)
		{
			if(key.indexOf('field')>-1)
			{
				tr.data(key,data[key]);
				tr.find('td[data-field-name="'+key+'"]').text(data[key]);
			}
		}
	}
	$("a[data-close='close']").click();
}

/**
* 页面加载时，给类型12的题目，绑定change方法，用于第一次没有返回结果的情况
*/
function bindChange(){
    var dts = $("dt[data-type='12']");
    $.each(dts, function (i,n) {
        var dl = $(this).parent();
        var radios = dl.find('input[type="radio"]');
        var last_id = dl.find('input[type="radio"]:last').attr('data-id');
        var inputText = dl.find('input[type="text"]:last');
		if(dl.find('input[type="radio"]:last:checked')){
			 inputText.attr('required' , true);
		}
        $.each(radios , function ( i ,n )
        {
            $(this).unbind('change').bind('change',function(){
                if ( $(this).attr('data-id') == last_id )
                {
                    inputText.attr('disabled',false);
                    inputText.attr('required' , true);
                }
                else
                {
                    inputText.attr('disabled',true);
                    inputText.attr('required' , false);
                }
            });
        });
    });
}

function bindChangeType13(){
    var dts = $("dt[data-type='13']");
    $.each(dts, function (i,n) {
        var dl = $(this).parent();
        var lis = dl.find('li.check_label');
        var last_id = dl.find('li.check_label:last').attr('data-id');
        var inputText = dl.find('input[type="text"]:last');
		if(dl.find('li.check_label:last').hasClass("active")){
			 inputText.attr('required' , true);
		}
        $.each(lis, function ( i ,n )
        {
        	$(this).click(function(){
        		if ( $(this).attr('data-id') == last_id ){
        			if(inputText.attr("disabled")=="disabled"){
        				 inputText.attr('disabled',false);
                         inputText.attr('required' , true);
        			}else{
        				inputText.attr('disabled',true);
                        inputText.attr('required' , false);
        			}
        		}        		 
        	})
        });


    });
}
</script>
</body>


</html>
