function tabFinanChange(index){
	$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
	$('.anchor_nav').remove();
	$("#tab-content").remove();
	$("#tab-content1").remove();
	$(".tip-yellowsimple").remove();
  switch(index){
	case '0':projectFinanInfo1(); break;  //标签1:项目
	case '1': initTeamFinanInfo1(); break;  //标签2: 团队
	case '2': initOperateFinanInfo1();   break;  //标签3:运营数据
	case '3': initCompeteFinanInfo1();   break;  //标签4:竞争
	case '4': initPlanFinanInfo1();   break;  //标签5:战略及策略
	case '5': initFinanceFinanInfo1();   break;  //标签6:财务
	case '6': initJusticeFinanInfo1();   break;  //标签7:法务
	case '7': initValuationFinanInfo1();   break;  //标签8:融资及估值
	default: return false;
}
}
	   //项目
		function projectFinanInfo1(){
			window.location.href=platformUrl.toFinancingP;
		}
		 //团队
		function initTeamFinanInfo1(){
			$("#page_all").html("");
			$.getTabHtmlInfo({
				url : platformUrl.toFinancingT ,
				okback:function(){
				right_anchor("GNO3?reportType=5","seven","hide");
				}
			});
		}
		 //运营数据
		function initOperateFinanInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingO ,
				okback:function(){
					right_anchor("GNO4?reportType=5","seven","hide");
				}
			});
		}
		//竞争
		function initCompeteFinanInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingC ,
				okback:function(){
					right_anchor("GNO5?reportType=5","seven","hide");
				}
			});
		}
		//战略以及策略
		function initPlanFinanInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingPlan ,
				okback:function(){
					right_anchor("GNO6?reportType=5","seven","hide");
				}
			});
		}
		//财务
		function initFinanceFinanInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingF,
				okback:function(){
					right_anchor("GNO7?reportType=5","seven","hide");
				}
			});
		}
		//法务
		function initJusticeFinanInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingJ,
				okback:function(){
					right_anchor("GNO8?reportType=5","seven","hide");
				}
			});
		}
		//融资及估值
		function initValuationFinanInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toFinancingV ,
				okback:function(){
					right_anchor("GNO9?reportType=5","seven","hide");
				}
			});
		}
