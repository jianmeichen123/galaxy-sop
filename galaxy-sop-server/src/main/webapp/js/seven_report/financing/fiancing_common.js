function tabFinanChange(index){
	$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
	$('.anchor_nav').remove();
	$("#tab-content").remove();
	$("#tab-content1").remove();
	$(".tip-yellowsimple").remove();
  switch(index){
	case '0':projectFinanInfoF(); break;  //标签1:项目
	case '1': initTeamFinanInfoF(); break;  //标签2: 团队
	case '2': initOperateFinanInfoF();   break;  //标签3:运营数据
	case '3': initCompeteFinanInfoF();   break;  //标签4:竞争
	case '4': initPlanFinanInfoF();   break;  //标签5:战略及策略
	case '5': initFinanceFinanInfoF();   break;  //标签6:财务
	case '6': initJusticeFinanInfoF();   break;  //标签7:法务
	case '7': initValuationFinanInfoF();   break;  //标签8:融资及估值
	default: return false;
}
}
	   //项目
		function projectFinanInfoF(){
			window.location.href=platformUrl.toFinancingP+'?pid='+pid;
		}
		 //团队
		function initTeamFinanInfoF(){
			$("#page_all").html("");
			$.getTabHtmlInfo({
				url : platformUrl.toFinancingT ,
				okback:function(){
				right_anchor("GNO3?reportType=5","seven","hide");
				}
			});
		}
		 //运营数据
		function initOperateFinanInfoF(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingO ,
				okback:function(){
					right_anchor("GNO4?reportType=5","seven","hide");
				}
			});
		}
		//竞争
		function initCompeteFinanInfoF(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingC ,
				okback:function(){
					right_anchor("GNO5?reportType=5","seven","hide");
				}
			});
		}
		//战略以及策略
		function initPlanFinanInfoF(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingPlan ,
				okback:function(){
					right_anchor("GNO6?reportType=5","seven","hide");
				}
			});
		}
		//财务
		function initFinanceFinanInfoF(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingF,
				okback:function(){
					right_anchor("GNO7?reportType=5","seven","hide");
				}
			});
		}
		//法务
		function initJusticeFinanInfoF(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingJ,
				okback:function(){
					right_anchor("GNO8?reportType=5","seven","hide");
				}
			});
		}
		//融资及估值
		function initValuationFinanInfoF(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingV ,
				okback:function(){
					right_anchor("GNO9?reportType=5","seven","hide");
				}
			});
		}
