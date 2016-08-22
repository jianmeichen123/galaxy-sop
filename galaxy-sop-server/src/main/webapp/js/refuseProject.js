/**
 * projectId : 项目ID
 */
var refuseProjectDialog = {
	init : function(formdata) {
		$.getHtml({
			url : platformUrl.toRefuseProject,// 模版请求地址
			data : "",// 传递参数
			okback : function(_this) {
				
				var operator = {
						save : function(){
							if(beforeSubmitById("form_refuse_project")){
								var form = $("#form_refuse_project").serializeObject();
								form = jQuery.parseJSON(form);
								form.projectId = formdata.projectId;
								sendPostRequestByJsonObj(platformUrl.closeProject ,form,operator.saveCallBackFuc);
							}
						},
						cancel : function(data){
							refuseProjectDialog.close(_this);
						},
						saveCallBackFuc : function(data){
							closeback(data,function(){
								refuseProjectDialog.close(_this)
							});
						}
				}
				
				//确定
				$('#form_refuse_project').find('#button_confirm').click(function(){
					operator.save();
				});
				//取消
				$('#form_refuse_project').find("#button_cancel").click(function(){
					operator.cancel();
				});
			}// end okback 模版反回成功执行
		});
	},
	close : function(_this) {
		//关闭对外接口
		_this.hideback.apply(_this);
		$(_this.id).remove();
		$('.tip-yellowsimple').hide();
		//判断是否关闭背景
		if($(".pop").length==0){
			$("#popbg").hide();	
		}
	}
}