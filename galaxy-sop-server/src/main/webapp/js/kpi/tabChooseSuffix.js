/**
 * 
 */
var reportChooseSuffix = {
		init : function(){
			$.getHtml({
				url:platformUrl.toChooseReportSuffix,//模版请求地址
				data:"",//传递参数
				okback:function(_this){
					$("#button_confirm").click(function(){
						var suffix = $("#chooseForm").find("input[name='suffix']:checked").val();
						window.location.href = platformUrl.exportKpiGrade + "?suffix=" + suffix;
						reportChooseSuffix.close(_this);
					})
					$("#button_close").click(function(){
						reportChooseSuffix.close(_this);
					})
				}
			});
		},
		close : function(_this){
			//启用滚动条
			 $(document.body).css({
			   "overflow-x":"auto",
			   "overflow-y":"auto"
			 });
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