function tabInfoChange(index){
	$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
	$('.anchor_nav').remove();
	$("#tab-content").remove();
	$("#tab-content1").remove();
	$(".tip-yellowsimple").remove();
		switch(index){
	case '1':initProjectInfo(); break;  //标签1:项目
	case '2': initTeamInfo(); break;  //标签2: 团队
	case '3': initOperateInfo();   break;  //标签3:运营数据
	case '4': initCompeteInfo();   break;  //标签4:竞争
	case '5': initPlanInfo();   break;  //标签5:战略及策略
	case '6': initFinanceInfo();   break;  //标签6:财务
	case '7': initJusticeInfo();   break;  //标签7:法务
	case '8': initValuationInfo();   break;  //标签8:融资及估值
	default: return false;
}
}
	   //项目
		function initProjectInfo(){
			$("#page_all").html("");
		 $.getTabHtmlInfo({
				url : platformUrl.toInvestigateP ,
				okback:function(){
					right_anchor(2);
					$("[data-id='tab-block']").next("ul").remove();
					$("#tab-content").remove()
				}
			}); 
		}
		 //团队
		function initTeamInfo(){
			$("#page_all").html("");
			$.getTabHtmlInfo({
				url : platformUrl.toInvestigateT ,
				okback:function(){
				//	right_anchor(3);
				}
			});
		}
		 //运营数据
		function initOperateInfo(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateO ,
				okback:function(){
			//		right_anchor(4);
				}
			});
		}
		//竞争
		function initCompeteInfo(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateC ,
				okback:function(){
			//		right_anchor(5);
				}
			});
		}
		//战略以及策略
		function initPlanInfo(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigatePlan ,
				okback:function(){
			//		right_anchor(6);
				}
			});
		}
		//财务
		function initFinanceInfo(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateF ,
				okback:function(){
			//		right_anchor(7);
				}
			});
		}
		//法务
		function initJusticeInfo(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateJ ,
				okback:function(){
			//		right_anchor(8);
				}
			});
		}
		//融资及估值
		function initValuationInfo(){
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateV ,
				okback:function(){
			//		right_anchor(9);
				}
			});
		}
