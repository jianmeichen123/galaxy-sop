/**
 * 该方法不包含团队成员复杂的表格处理
 * 表格增删改查通用方法   **************************************************** 开始
 */
//新增弹出页面渲染
function addRow(ele)
{
	
	var code = $(ele).prev().data('code');
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			$('#qualifications_popup_name').html('添加简历');
            $('#qualifications_popup_name1').html('添加持股人');
            $('#finace_popup_name').html('添加融资历史');
            $("#complete_title").html('添加综合竞争比较');
            $(".see_block").hide();
            $("#detail-form input[name='projectId']").val(projectInfo.id);
            $("#detail-form input[name='titleId']").val($(ele).prev().data('titleId'));
            $("#detail-form input[name='subCode']").val($(ele).prev().data('code'));

            selectContext("detail-form");

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
}
//提交表单处理
function saveForm(form)
{
	if($(form).validate().form())
	{
		var data = $(form).serializeObject();
		saveRow(data);
	}
}
*//**
 * 保存至到tr标签data属性
 *//*
function saveRow(data)
{
	data = JSON.parse(data);
	var titleId = data.titleId;
	var index = data.index;
	if(typeof index == 'undefined' || index == null || index == '')
	{
		var tr = buildRow(data,true,titleId);
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
	resizetable($('table[data-title-id="'+titleId+'"].editable'))
	$("a[data-close='close']").click();
}
function editRow(ele)	
{
	var code = $(ele).closest('table').data('code');
	var row = $(ele).closest('tr');
	var txt=$(ele).text();
	if(code == 'grant-part' || code == 'grant-actual'){
		 if(!getTotalAppr(projectInfo.id)){
			  return;
		  }
	}
	
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			var title = $("#pop-title");
			if(txt=="查看"){
				$("#detail-form").hide();
				$(".button_affrim").hide();
				$("#delivery_popup_name").text("查看交割事项");
				 $('#grant_popup_name').html('查看分期注资计划');
				 $('#finace_popup_name').html('查看融资历史');
				 $("#complete_title").html('查看综合竞争比较');
				
			}else{
				$(".see_block").hide();
				$("#delivery_popup_name").text("编辑交割事项");
				 $('#grant_popup_name').html('编辑分期注资计划');
				 $('#finace_popup_name').html('编辑融资历史');
				 $("#complete_title").html('编辑综合竞争比较');
				 $("#pop-title-tz").html('编辑投资人');
				 $("#pop-title-share").html('编辑股东');
				 $("#pop-title-yy").html('编辑运营指标');
				 $("#pop-title-gs").html('编辑同类公司');
				 $("#pop-title-time").html('编辑里程碑和时间节点');
			}
			$("#detail-form input[name='subCode']").val(code);
			$("#detail-form input[name='titleId']").val(row.parent().parent().attr("data-title-id"));
			selectContext("detail-form");
			$.each($("#detail-form").find("input, select, textarea"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				var type=ele.attr('type');
				var idVal=ele.attr('id');
				if(type=="radio"){
					if(ele.val()==row.data(name)){
						ele.attr("checked","chedcked");
					}
				}else{
					ele.val(row.data(name));
				}
			});
			//查看显示
			$.each($(".see_block").find("dd[name]"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				ele.text(row.data(name));
				//历史融资特殊处理select,radio
				$.each($("#financeDetail select"),function(){
					var selectId=$(this).val();
					var selectVal=$("#financeDetail select").find("option[value='"+selectId+"']").text();
					if(row.data(name)==selectId){
						ele.text(selectVal);
					}
				});
				$.each($("#financeDetail input[type='radio']"),function(){
					var selectId=$(this).val();
					var selectVal=$("#financeDetail").find("input[type='radio'][value='"+selectId+"']").parent().text();
					if(row.data(name)==selectId){
						ele.text(selectVal);
					}
				});
				
				
			})
			//文本框剩余字数
			$.each($(".team_textarea"),function(){
				var len=$(this).val().length;
				var initNum=$(this).siblings('.num_tj').find("span").text();
				$(this).siblings('.num_tj').find("span").text(initNum-len);
			})
			$("#detail-form input[name='index']").val(row.index());
			$("#save-detail-btn").click(function(){
				saveForm($("#detail-form"));
			});
		}//模版反回成功执行	
	});
}
var deletedRowIds = new Array();
 // 股权结构合理性 表格删除行使用
var deletedRowIdsGq = new Array();
function delRow(ele)
{
	layer.confirm('是否删除?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero) {
		var tr = $(ele).closest('tr');
		var id = tr.data('id');

		var sectionId =$(ele).closest('.radius').attr("data-section-id");
		var ch_opration =$(ele).closest('.h_team_look')
        if(typeof id != 'undefined' && id>0)
        {
            //股权合理性
            if (sectionId ==1324){
               deletedRowIdsGq.push(id);
            }else{
               deletedRowIds.push(id);
            }
            if (ch_opration.hasClass("ch_opration")){
            	
            }
        }
		tr.remove();
		check_table();
		check_table_tr_edit();
		$(".layui-layer-close1").click();
		//$(".layui-layer-btn1").click();
	}, function(index) {
	});
	
}
function refreshSection(id)
{
	var sec = $(".section[data-section-id='"+id+"']");
    sec.find("dd[data-type='3']").text('未选择');
	//var sec = $(".section[data-section-id='"+id+"']");
	sec.showResults(true);
}
/**
 * 表格增删改查通用方法   **************************************************** 结束
 */