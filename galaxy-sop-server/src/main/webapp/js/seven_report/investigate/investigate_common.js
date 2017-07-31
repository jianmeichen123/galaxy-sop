function tabInvestChange(index){
	$(".h_navbar li").eq(index).addClass("active").siblings().removeClass("active");
	$('.anchor_nav').remove();
	$("#tab-content").remove();
	$("#tab-content1").remove();
	$(".tip-yellowsimple").remove();
  switch(index){
    case '0':initProjectInfo11(); break;  //标签1:项目
	case '1':initProjectInfo1(); break;  //标签1:项目
	case '2': initTeamInfo1(); break;  //标签2: 团队
	case '3': initOperateInfo1();   break;  //标签3:运营数据
	case '4': initCompeteInfo1();   break;  //标签4:竞争
	case '5': initPlanInfo1();   break;  //标签5:战略及策略
	case '6': initFinanceInfo1();   break;  //标签6:财务
	case '7': initJusticeInfo1();   break;  //标签7:法务
	case '8': initValuationInfo1();   break;  //标签8:融资及估值
	default: return false;
}
}
function initProjectInfo11(){
	/*$("#page_all").html("");
	$.getTabHtmlInfo({
		url : platformUrl.toBaseInfo,
		okback:function(){
			right_anchor(1);
		}
	});*/
	window.location.href=platformUrl.toInvestigateP;
   }
	   //项目
		function initProjectInfo1(){
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
				//	right_anchor(3);
				}
			});
		}
		 //运营数据
		function initOperateInfo1(){
			alert(33333);
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateO ,
				okback:function(){
			//		right_anchor(4);
				}
			});
		}
		//竞争
		function initCompeteInfo1(){
			alert(444444);
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateC ,
				okback:function(){
			//		right_anchor(5);
				}
			});
		}
		//战略以及策略
		function initPlanInfo1(){
			alert(555555);
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigatePlan ,
				okback:function(){
			//		right_anchor(6);
				}
			});
		}
		//财务
		function initFinanceInfo1(){
			alert(66666);
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateF ,
				okback:function(){
			//		right_anchor(7);
				}
			});
		}
		//法务
		function initJusticeInfo1(){
			alert(77777);
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateJ ,
				okback:function(){
			//		right_anchor(8);
				}
			});
		}
		//融资及估值
		function initValuationInfo1(){
			alert(8888);
			$("#page_all").html("");
			$.getTabHtml({
				url : platformUrl.toInvestigateV ,
				okback:function(){
			//		right_anchor(9);
				}
			});
		}
