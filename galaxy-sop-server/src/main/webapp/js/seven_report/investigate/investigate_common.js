function tabInvestChange(index){
	$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
	$('.anchor_nav').remove();
	$("#tab-content").remove();
	$("#tab-content1").remove();
	$(".tip-yellowsimple").remove();
  switch(index){
	case '0':investProjectInfo1(); break;  //标签1:项目
	case '1': initTeamInfo1(); break;  //标签2: 团队
	case '2': initOperateInfo1();   break;  //标签3:运营数据
	case '3': initCompeteInfo1();   break;  //标签4:竞争
	case '4': initPlanInfo1();   break;  //标签5:战略及策略
	case '5': initFinanceInfo1();   break;  //标签6:财务
	case '6': initJusticeInfo1();   break;  //标签7:法务
	case '7': initValuationInfo1();   break;  //标签8:融资及估值
	default: return false;
}
}
	   //项目
		function investProjectInfo1(){
			window.location.href=platformUrl.toInvestigateP;
			/*$("#page_all").html("");
		   $.getTabHtmlInfo({
				url : platformUrl.toInvestigateP ,
				okback:function(){
					right_anchor(2);
					$("[data-id='tab-block']").next("ul").remove();
					$("#tab-content").remove()
				}
			}); */
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
		function initOperateInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateO ,
				okback:function(){
				right_anchor("DNO4?reportType=2","seven","hide");
				}
			});
		}
		//竞争
		function initCompeteInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateC ,
				okback:function(){
				right_anchor("DNO5?reportType=2","seven","hide");
				}
			});
		}
		//战略以及策略
		function initPlanInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigatePlan ,
				okback:function(){
				right_anchor("DNO6?reportType=2","seven","hide");
				}
			});
		}
		//财务
		function initFinanceInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateF ,
				okback:function(){
				right_anchor("DNO7?reportType=2","seven","hide");
				}
			});
		}
		//法务
		function initJusticeInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateJ ,
				okback:function(){
				right_anchor("DNO8?reportType=2","seven","hide");
				}
			});
		}
		//融资及估值
		function initValuationInfo1(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateV ,
				okback:function(){
				right_anchor("DNO9?reportType=2","seven","hide");
				}
			});
		}
