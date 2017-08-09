	function tabDecisionChange(index){
		$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
		$('.anchor_nav').remove();
		$("#tab-content").remove();
		$("#tab-content1").remove();
		$(".tip-yellowsimple").remove();
	  switch(index){
		case '0':decisionPlan(); break;  //标签1:投资方案
		case '1': other1(); break;  //标签2: 其他
		default: return false;
	}
}
	   //项目
		function decisionPlan(){
			window.location.href=platformUrl.toDecisionPlan;
		}
		 //运营数据
		function other1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toDecisionOther ,
				okback:function(){
					right_anchor("PNO2?reportType=3","seven","hide");
				}
			});
		}
	