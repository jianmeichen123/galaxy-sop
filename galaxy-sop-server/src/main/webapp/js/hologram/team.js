

function editRow1(ele,rowId)
{
	var code = $(ele).closest('table').data('code');
	var row = $(ele).closest('tr');

	alert(rowId)
	$.getHtml({
		url:getDetailUrl(code),//模版请求地址
		data:"",//传递参数
		okback:function(){
			 if(rowId){
                alert(rowId);
                sendGetRequest("http://fx.local.galaxyinternet.com/sop/galaxy/team/queryOneRow/"+rowId,null,function(data){

                    var baseEntity = data.entity;
                    var studyList = data.studyList;
                    var  startupList = data.startupList;
                    var workList = data.workList;
                    //填充基本信息
                    $.each($("#teamInfo").find("input, select, textarea"),function(){
                        var ele = $(this);
                        var name = ele.attr('name');
                        ele.val(baseEntity[name]);
                    });
                    //填充学习经历

                    //填充工作经历

                    //填充创业经历


                })
            }
		}//模版反回成功执行
	});
}
function delete_row(obj){
		var id = obj.attributes["data-id"].nodeValue;
		$('#'+id).remove();
}