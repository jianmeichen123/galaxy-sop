	function tabInvestChange(index){
		$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
		$('.anchor_nav').remove();
		$("#tab-content").remove();
		$("#tab-content1").remove();
		$(".tip-yellowsimple").remove();
	  switch(index){
		case '0':financePlan1(); break;  //标签1:投资方案
		case '1': other1(); break;  //标签2: 其他
		default: return false;
	}
}
	   //项目
		function financePlan1(){
			window.location.href=platformUrl.toInvestigateP;
		}
		 //团队
		function initTeamInfo1(){
			$("#page_all").html("");
			$.getTabHtmlInfo({
				url : platformUrl.toInvestigateT ,
				okback:function(){
					right_anchor("DNO3?reportType=2","seven","hide");
				}
			});
		}
		 //运营数据
		function other1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateO ,
				okback:function(){
					right_anchor("DNO4?reportType=2","seven","hide");
				}
			});
		}
	