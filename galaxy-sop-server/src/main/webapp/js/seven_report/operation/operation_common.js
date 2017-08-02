function tabOperateChange(index){
	$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
	$('.anchor_nav').remove();
	$("#tab-content").remove();
	$("#tab-content1").remove();
	$(".tip-yellowsimple").remove();
  switch(index){
	case '0':investFinancePlan1(); break;  //标签1:投资方案
	case '1': initTeamInfo1(); break;  //标签2: 团队
	case '2': initOperateInfo1();   break;  //标签3:运营数据
	case '3': initMarkDevelop1();   break;  //标签4:市场开发
	case '4': initCompeteInfo1();   break;  //标签4:竞争
	case '5': initPlanInfo1();   break;  //标签5:战略及策略
	case '6': initFinanceInfo1();   break;  //标签6:财务
	case '7': initJusticeInfo1();   break;  //标签7:法务
	case '8': initValuationInfo1();   break;  //标签8:融资及估值
	default: return false;
}
}
	   //投资方案
		function investFinancePlan1(){
			window.location.href=platformUrl.toOperationP;
		}
		 //团队
		function initTeamInfo1(){
			$("#page_all").html("");
			$.getTabHtmlInfo({
				url : platformUrl.toOperationT ,
				okback:function(){
					right_anchor("ONO2?reportType=7","seven","hide");
				}
			});
		}
		 //运营数据
		function initOperateInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationO ,
				okback:function(){
					right_anchor("ONO3?reportType=7","seven","hide");
				}
			});
		}
		//市场与开发
		function initMarkDevelop1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationMD ,
				okback:function(){
					right_anchor("ONO4?reportType=7","seven","hide");
				}
			});
		}
		//竞争
		function initCompeteInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationC ,
				okback:function(){
					right_anchor("ONO5?reportType=7","seven","hide");
				}
			});
		}
		//战略以及策略
		function initPlanInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationPlan ,
				okback:function(){
					right_anchor("ONO6?reportType=7","seven","hide");
				}
			});
		}
		//财务
		function initFinanceInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationF ,
				okback:function(){
					right_anchor("ONO7?reportType=7","seven","hide");
				}
			});
		}
		//法务
		function initJusticeInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationJ ,
				okback:function(){
					right_anchor("ONO8?reportType=7","seven","hide");
				}
			});
		}
		//融资及估值
		function initValuationInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toOperationV ,
				okback:function(){
					right_anchor("ONO9?reportType=7","seven","hide");
				}
			});
		}
