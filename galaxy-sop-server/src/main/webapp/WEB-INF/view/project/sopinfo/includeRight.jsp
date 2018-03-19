<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
	response.setHeader("Pragma","no-cache"); //HTTP 1.0    
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isThyy" value="${fx:hasRole(20)}" scope="request"/>  
  <!--删除项目的弹窗div  -->
        	 <!-- 二次确认弹窗 -->
     <!--  <div class="basic_on basic_common_width tab_info_common_width basic_second_confirm">
	      <form id="basicForm" onsubmit="return false;">
				 <div class="second_confirm">
					 <div class="title_bj_tzjl">
						  提示
						<em class="agency_close" data-on="close" data-name="basic"></em>
					 </div>
					 <div>
					 	<p>是否删除项目？</p>
					 	<div class="btn btnbox basic_mes_button basicSpButton">
			              <button class="pubbtn bluebtn version19_save_btn" data-on="save_basic" save_type="save_basic">保存</button>
			              <button class="pubbtn fffbtn version19_cancel_btn" data-name="basic" data-on="close">取消</button>
			            </div>
					 </div>
				 
				 </div>
			</form>
		</div>   --> 
        	<!-- 投后运营Start -->
        	<c:if test="${aclViewProject or isThyy}">
        	
        	<div id="thyy_div" style="display:none;">
					<div class="correlation">投后运营</div>
					<div class="new_correlation_cen status">
						<span>运营状态</span><img src="<%=path %>/img/status/s01.png" id="yyzt_img">
  						<div class="delivery">
  							<div class="title">交割前确认事项</div>
  							<ul class="clearfix">
  								<li class="fl">已完成：<span class="delivery_complete">3</span></li>
  								<li class="fr">总计：<span class="delivery_total">16</span></li>
  							</ul>
  							<div class="progressBar">
  								<div id="bar"></div>
  							</div>
  						</div>
  						 注资进度
  	              <div class="money">
  	                <div class="title">注资进度</div>
  	                <input type="hidden" id="planMoney">
  	                <ul class="clearfix">
  	                  <li class="fl">已注资：<span class="money_complete"></span>万</li>
  	                  <li class="fr">计划注资：<span class="money_total"></span>万</li>
  	                </ul>
  	                <div class="progressBar"><div id="bar_m"></div></div>
  	              </div>
  	              
					</div> 
			<div class="new_right_flow new_right_flow_after clearfix">
        	<div class="new_right_flow_line">
            	<ul class="line_ul_p" id="lct_ul">
                	<li><span class="gray_dot"></span><label>接触访谈</label></li>
                    <li><span class="gray_dot"></span><label>内部评审</label></li>
                    <li><span class="gray_dot"></span><label>C<br/>E<br/>O评审</label></li>
                    <li><span class="gray_dot"></span><label>立项会</label></li>
                    <li><span class="gray_dot"></span><label>会后商务谈判</label></li>
                    <li><span class="gray_dot"></span><label>投资意向书</label></li>
                    <li><span class="gray_dot"></span><label>尽职调查</label></li>
                    <li><span class="gray_dot"></span><label>投资决策会</label></li>
                    <li><span class="gray_dot"></span><label>投资协议</label></li>
                    <li><span class="gray_dot"></span><label>股权交割</label></li>
                    <li><span class="gray_dot on"></span><label>投后运营</label></li>
                </ul>
            </div>
             <span class="bluebtn new_btn_right tzlc_btn">进入投资流程</span>
        </div>

      	
<!-- <div class="correlation">近期会议纪要 <span class="more null new_righ" id="thyy_meet_more" style="cursor: pointer;">more</span>
</div>
<div class="new_correlation_cen new_correlation_cen_con" id="thyy_meet_div">
	<div class="no_con">
	            		暂无会议纪要
	            	</div>
</div> -->
</div>
</c:if>
			<!-- 投后运营End -->
			<!-- 投前Start -->
			<div class="tq_div clearfix" style="display:none;">
        	<div class="new_right_flow">
            	<div class="new_right_flow_line">
                	<ul class="line_ul_p" id="lct_ul">
                    	<li><span class="gray_dot"></span><label>接触访谈</label></li>
                        <li><span class="gray_dot"></span><label>内部评审</label></li>
                        <li><span class="gray_dot"></span><label>C<br/>E<br/>O评审</label></li>
                        <li><span class="gray_dot"></span><label>立项会</label></li>
                        <li><span class="gray_dot"></span><label>会后商务谈判</label></li>
                        <li><span class="gray_dot"></span><label>投资意向书</label></li>
                        <li><span class="gray_dot"></span><label>尽职调查</label></li>
                        <li><span class="gray_dot"></span><label>投资决策会</label></li>
                        <li><span class="gray_dot"></span><label>投资协议</label></li>
                        <li><span class="gray_dot"></span><label>股权交割</label></li>
                        <li><span class="gray_dot"></span><label>投后运营</label></li>
                    </ul>
                </div>
                 
            </div>
            <span class="bluebtn new_btn_right tzlc_btn">进入投资流程</span>
            </div>
           
        	<!-- 七大报告入口开始 -->
			<div class="tq_div">
				<div class="correlation">水晶球报告</div> 
				<ul class="sev_report clearfix">
				<c:choose>
				 <c:when test="${(fx:hasRoles('1,2,18,19') || (fx:hasRole(3) && fx:inOwnDepart('project',pid) ) ||(fx:isCooperative(projectId)) || (!fx:hasRole(4) &&fx:isForTask(pid)) || (fx:isCreatedByUser('project',pid) ))}">
					<li class="seven_link1 seven_link_qx" onclick="seven_link(1);">
						<span class='qx report_entrance'></span>
						<span>全息报告</span>
						<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
						</div>
					</li>
					<li class="seven_link2 seven_link_pc" onclick="seven_link(2);">
					<span class='pc report_entrance'></span>
						<span>评测报告</span>
						<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
						</div>	
					</li>
					<li class="seven_link3 seven_link_jd" onclick="seven_link(3);">
						<span class='jd report_entrance'></span>
						<span>尽调报告</span>
						<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
						</div>	
					</li>
					<li class="seven_link4 seven_link_jc" onclick="seven_link(4);">
						<span class='jc report_entrance'></span>
						<span>决策报告</span>
						<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
						</div>	
					</li>
					<li class="seven_link5 seven_link_cp" onclick="seven_link(5);">
						<span class='cp report_entrance'></span>
						<span>初评报告</span>
						<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
						</div>	
					</li>
					<li class="seven_link6 seven_link_rz" onclick="seven_link(6);">
						<span class='rz report_entrance'></span>
						<span>融资报告</span>
						<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
						</div>	
					</li>
					<li class="seven_link7 seven_link_yy" onclick="seven_link(7);">
						<span class='yy report_entrance'></span>
						<span>运营报告</span>
						<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
						</div>	
					</li>
					 </c:when>
					 <c:otherwise>
						<li class="seven_link1 gray_link1 gray">
							<span class='qx report_entrance'></span>
							<span>全息报告</span>	
							<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
							</div>
						</li>
						<li class="seven_link2 gray_link2 gray">
							<span class='pc report_entrance'></span>
							<span>评测报告</span>	
							<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
							</div>
						</li>
						<li class="seven_link3 gray_link3 gray">
							<span class='jd report_entrance'></span>
							<span>尽调报告</span>	
							<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
							</div>
						</li>
						<li class="seven_link4 gray_link4 gray">
							<span class='jc report_entrance'></span>
							<span>决策报告</span>	
							<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
							</div>
						</li>
						<li class="seven_link5 gray_link5 gray">
							<span class='cp report_entrance'></span>
							<span>初评报告</span>	
							<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
							</div>
						</li>
						<li class="seven_link6 gray_link6 gray">
							<span class='rz report_entrance'></span>
							<span>融资报告</span>	
							<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
							</div>
						</li>
						<li class="seven_link7 gray_link7 gray">
							<span class='yy report_entrance'></span>
							<span>运营报告</span>	
							<div class="progress-contain">
							  <div class="processcontainer">  
							      <div class="processbar" style="width:0%;"></div>
							  </div>  
							  <span class="percent_number"></span>
							</div>
						</li>
					 </c:otherwise>
					 </c:choose>
				</ul>
			</div>
			<!-- 七大报告入口结束 -->
            <div class="tq_div">
	                <div class="correlation" id="correlation" style="display:none">相关操作</div>
	             <div class="new_correlation_cen">
	            	<!-- <span class="bluebtn new_btn fjxm_but" onclick="closePro(this)">否决项目</span> -->
	            	        <span class="bluebtn new_btn_right yjxm_btn btn_left" onclick="transferPro('transfer')" style="display:none" id="tansfer_btn" >移交项目</span>
	                        <span class="bluebtn new_btn_right yjxm_btn btn_left" onclick="transferPro('assign')" style="display:none" id="assign_btn" >指派项目</span>
                            <span class="bluebtn new_btn_right btn_right" onclick="deletePro()" style="display:none" id="delete_btn" >删除项目</span>
	            </div>
            </div>
            
            
            <div class="tq_div" style="display:none;">
          <c:if test="${fx:hasRole(1) || fx:hasRole(2) || fx:hasRole(3)|| fx:isCreatedByUser('project',pid) }">
           <!--  <div class="correlation">
            	近期会议纪要
				<span class="more null new_righ" id="meet_more" style="cursor: pointer;">more</span>
			</div>
            <div class="new_correlation_cen new_correlation_cen_con" id="near_meet">
            <div class="no_con">
            		暂无会议纪要
            	</div>
            </div> -->
             
             
           <!--  <div class="correlation">近期访谈记录
				<span class="more null new_righ" id="view_more" style="cursor: pointer;" >more</span>
			</div>
            <div class="new_correlation_cen new_correlation_cen_con"  id="near_view">
            	<div class="no_con">
            		暂无访谈记录
            	</div>
            </div> -->
            </c:if>
            
            
        </div>
        <!-- 投前End -->
        <!-- <div class="tq_div" style="font-size:12px;font-family:'宋体';border-top:1px solid #e9ebf2;">
        </div> -->
        
     
	 
        
 <style>
 .delete_reason span.error{display:block;margin-left:118px;}
 .delete_reason_left span.error{display:block;margin-left:-3px;}
 </style>       
        
        
<script src="<%=path %>/js/refuseProject.js"></script>
<script type="text/javascript" src="<%=path %>/js/sop.js"></script>
<script type="text/javascript" src="<%=path %>/js/sop_progress/sop_progress.js"></script>
<script type="text/javascript" src="<%=path %>/js/sop_progress/sop_file.js"></script>
<script>

var pRigthInfo = ${proinfo}
var proid = pid;
var prograss = pRigthInfo.projectProgress;
var prograss_name=pRigthInfo.progress;
var prostatus = pRigthInfo.projectStatusDs
$(".tzlc_btn").attr("onclick","progress("+pid+",'detail')"); 
  if(isCreatedByUser == "true"|| ${fx:isCooperative(projectId)}){
	/*   $(".yjxm_btn").attr("style","display:block;"); */
	}else{
	  $(".tzlc_btn").remove();
	}
  

if(!prograss){
	prograss = 'projectProgress:0';
}
var index = Number(prograss.substring("projectProgress:".length,prograss.length));
var admin = "${fx:isCreatedByUser('project',pid) }";
var isGG = "${fx:hasRole(1) || fx:hasRole(2) || fx:hasRole(3)}";
function seven_link(data){
	var _href=window.location.href;
	setCookie("back_url",_href,24,'/');
	if(data==1){
		buryPoint("109");
		window.location.href=platformUrl.toBaseInfo+'?pid=${pid}'+"&sid="+sessionId+"&guid="+userId;
	}else if(data==2){
		buryPoint("110");
		window.location.href=platformUrl.toEvalindex+'?pid=${pid}'+"&sid="+sessionId+"&guid="+userId;
	}else if(data==3){
		buryPoint("111");
		window.location.href=platformUrl.investigate+'?pid=${pid}'+"&sid="+sessionId+"&guid="+userId;
	}else if(data==4){
		buryPoint("112");
		window.location.href=platformUrl.toDecision+'?pid=${pid}'+"&sid="+sessionId+"&guid="+userId;
	}else if(data==5){
		buryPoint("113");
		window.location.href=platformUrl.toPreEva+'?pid=${pid}'+"&sid="+sessionId+"&guid="+userId;
	}else if(data==6){
		buryPoint("114");
		window.location.href=platformUrl.toFinancing+'?pid=${pid}'+"&sid="+sessionId+"&guid="+userId;
	}else if(data==7){
		buryPoint("115");
		window.location.href=platformUrl.toOperation+'?pid=${pid}'+"&sid="+sessionId+"&guid="+userId;
	}
	
}
$(function(){
	//显示投前或投后信息
	if(prograss == 'projectProgress:10')
	{
		$('.fjxm_but').remove();
		if($("#thyy_div").length>0)
		{
			$("#thyy_div").show();
			$("#thyy_meet_more").click(function(){
				
				//showTabs(proid,9);
				initTabPostMeeting();
				//initTabMeeting(proid);
				$(".projectDetail li").eq(7).addClass("on").siblings().removeClass("on");
			});
			setThyyInfo();
		}
	}
	else
	{
		$(".tq_div").show();
	}
	
	 //删除项目按钮是否显示
	if((isCreatedByUser == "true"&&${fx:hasPremission('project_delete')}&&index<3)||(${fx:hasPremission('project_delete')}&&!${fx:hasRole(4)})){
		$("#delete_btn").attr("style","display:block");
	}
	 //移交项目按钮是否显示
	if((isCreatedByUser == "true"&&${fx:hasPremission('project_transfer')})||(${fx:hasPremission('project_transfer')}&&!isCreatedByUser&&!${fx:hasRole(4)})){
		$("#tansfer_btn").attr("style","display:block");
	}
	 //指派项目按钮是否显示
	if(${fx:hasPremission('project_assign')}){
		$("#assign_btn").attr("style","display:block");
	}
	/*  if(${fx:hasPremission('project_transfer')}||${fx:hasPremission('project_assign')}||${fx:hasPremission('project_delete')&&index>3}){
		 $("#correlation").css("display","block"); 
	 } */
	if($("#delete_btn").css("display")=="block"||$("#tansfer_btn").css("display")=="block"||$("#assign_btn").css("display")=="block"){
		 $("#correlation").css("display","block"); 
	 } 
	init_lct(); //流程图初始化
	if(pRigthInfo.projectStatus == 'meetingResult:3' || pRigthInfo.projectStatus == 'projectStatus:2' || pRigthInfo.projectStatus == 'projectStatus:3' || admin!="true"){
		$(".fjxm_but").removeAttr("onclick").attr("readonly","readonly").addClass("disabled");
	}
		
	//获取近期访谈、会议 记录
	//if(prograss != 'projectProgress:10' && (admin == 'true' || isGG == 'true'))
//	{

		//toFormatNearNotes();
		//more 链接初始化
		//initMoreLine();
	//}
	 setJgqrProgress();
	 sendPostRequest(platformUrl.getApprProcess+"/"+proid,appropriationProcessBack);
	 function appropriationProcessBack(data){
	 	var result = data.result.status;
	 	if(result == "ERROR"){ //OK, ERROR
	 		layer.msg(data.result.message);
	 		return;
	 	}else{
	 		 var grantTotal = data.userData;
	 		 var sumPlanMoney=grantTotal.sumPlanMoney;
	 		 var sumActualMoney=grantTotal.sumActualMoney;
	 		 $("#planMoney").val(sumPlanMoney);
	 		  setData(sumPlanMoney,sumActualMoney);
	 		 if(typeof(sumActualMoney)=="underfined"||null==sumActualMoney||sumActualMoney==0){
	 			sumActualMoney=0;
	 		 }else{
	 			 //var format=addCommas(fixSizeTwo(sumActualMoney/10000));
	 			 if(sumActualMoney==0.0000){
	 				sumActualMoney=0;
	 			 }
	 			 /* else{
	 				sumActualMoney=format ;
	 			 } */
	 		 }
	 		 if(null==sumPlanMoney||typeof(sumPlanMoney)=="underfined"||sumPlanMoney==0){
	 			    sumPlanMoney=0;
		 		 }else{
		 			 //var format=addCommas(fixSizeTwo(sumPlanMoney/10000));
		 			 if(sumPlanMoney==0.00){
		 				sumPlanMoney=0;
		 			 }
		 			 /* else{
		 				sumPlanMoney=format ;
		 			 } */
		 			
		 		 }
	 		$(".money_complete").text(sumActualMoney);
	 		$(".money_total").text(sumPlanMoney);
	  	}
	 }
		function setData(sumPlanMoney,sumActualMoney){
			 //注资进度
			  $("#bar_m").css("width","0px");  //初始化进度条宽度；
			    var moneyComplete=sumActualMoney;
			        moneyTotal=sumPlanMoney;
			        m_width=$(".progressBar").width();
			        if(moneyComplete==0){
			        	barWidth=0+"px";
			        }else{
			        	barWidth=parseInt(moneyComplete/moneyTotal*m_width)+"px";
			        }
			        
			    $("#bar_m").css("width",barWidth)
			    //获取表格除第一行，第二行之外的元素
			    var tr_n=$(".moneyAgreement tbody tr")
			    var tr_s=$(".moneyAgreement tbody tr").eq(1).nextAll();
			    tr_s.css("display","none");
			    if(tr_n.length>2){
			      $(".agreement .show_more").show();
			      $(".agreement .show_more").click(function(){
			        $(this).hide();
			        $(".agreement .show_hide").show();
			        tr_n.show();
			      })
			       $(".agreement .show_hide").click(function(){
			        $(this).hide();
			        $(".agreement .show_more").show();
			        tr_s.css("display","none");
			      })
			    }

		}

});

function toCheckShowIcon(){
	//无会议记录
	var len=$("#near_meet .new_b_bottom").length;
	if(len==0){
		$("#near_meet .no_con").show();
	}else{
		$("#near_meet .no_con").hide();
	}
	if(len<3){
		$("#meet_more").hide();
	}else{
		$("#meet_more").show();
	}
	//无访谈记录
	var len=$("#near_view .new_b_bottom").length;
	if(len==0){
		$("#near_view .no_con").show();
	}else{
		$("#near_view .no_con").hide();
	}
	if(len<3){
		$("#view_more").hide();
	}else{
		$("#view_more").show();
	}
}

//more 链接控制
function initMoreLine(){

	$("#meet_more").on("click", function(){
		$('ul.projectDetail li').eq(4).addClass('on').siblings().removeClass("on");
		initTabMeeting(proid);
	});
	$("#view_more").on("click", function(){
		$('ul.projectDetail li').eq(3).addClass('on').siblings().removeClass("on");
		initTabInterview();
	});

	/* if(projectInfo.projectStatus != 'meetingResult:3' && projectInfo.projectStatus != 'projectStatus:2' && projectInfo.projectStatus != 'projectStatus:3'){
	} else{
		$("#meet_more").attr("disabled","disabled").addClass("disabled");
		$("#view_more").attr("disabled","disabled").addClass("disabled");
	} */
}


/**
 * 流程图 ，动态生成初始化
 */
 //prograss_name
function init_lct(){
	var ul_li = $(".line_ul_p").children("li");
	$(ul_li).each(function(i){ 
		//if(index!=0 && i!=0){
			if($(this).text()==prograss_name){
				$(this).addClass("green_dot");
				$(this).prevAll().addClass("green_dot");
				$(this).addClass('green_dot_color');
				$(this).prepend("<span class='green_dot_on'></span>");
			}
		//}
	});
}

/**
 * 格式化 近期 访谈 会议
 */
 var viewList;
function toFormatNearNotes(){
	sendGetRequest(Constants.sopEndpointURL+"/galaxy/project/getnearnotes/" + proid, null, formatNearNotes);
}
function formatNearNotes(data){
	
	var len=$("#near_meet .new_b_bottom").length;
	if(len > 0){
		$("#near_meet").empty();
	}
	var len=$("#near_view .new_b_bottom").length;
	if(len > 0){
		$("#near_view").empty();
	}
	
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		layer.msg(data.result.message);
		return;
	}
	
	viewList = data.userData.viewList;
	formatNearMeet(data.userData.meetList);
	formatNearView(viewList);
	toCheckShowIcon();
}


function formatNearView(viewList){
	if(viewList && viewList.length>0){
		for(var i=0;i<viewList.length;i++){
			var target = viewList[i].viewTarget;
			var time = viewList[i].viewDateStr;
			var notes = viewList[i].viewNotes;
			
			//访谈对象 处理
			var subStr = "";
			var targerHtml="";
			if(target.length>8){
				subStr = target.substring(0,8)+"...";
				targerHtml = "<span class=\"new_b_li_one\" title="+target+">"+subStr+"</span>";
			}else{
				targerHtml = "<span class=\"new_b_li_one\" >"+target+"</span>";
			}
			
			//记录  处理
			var notesStr = "";
			if(notes){
				notesStr = delHtmlTag($.trim(notes));
				if(notesStr && notesStr.length > 15){
					notesStr = notesStr.substring(0,15)+"...";
				}
			}
        
			//返回会议记录
			var str = "<div class=\"new_b_bottom\">"+
						 "<div class=\"div_ul\">"+
		        			targerHtml +
		        			"<span class=\"new_b_li_three pull-right\">"+time+"</span>"+
		        		  "</div>"+
						"<p>"+notesStr+"</p>"+
					"</div>";
			
	    	$("#near_view").append(str);
	    } 
	}
}
function formatNearMeet(meetList){
	if(meetList && meetList.length>0){
		for(var i=0;i<meetList.length;i++){
			var meetType = meetList[i].meetingTypeStr;
			var result = meetList[i].meetingResultStr;
			var time = meetList[i].meetingDateStr;
			var notes = meetList[i].meetingNotes;
			
			//会议结论 处理
			var resultStr = "";
			if(meetList[i].meetingResult == 'meetingResult:1'){ //通过
				resultStr = "<span class='color_pass'>通过</span>";
			}else if(meetList[i].meetingResult == 'meetingResult:2'){ //待定
				resultStr = "<span class='color_undetermined'>待定</span>";
			}else if(meetList[i].meetingResult == 'meetingResult:3'){ //否决
				resultStr = "<span class='color_veto'>否决</span>";
			}
			
			//会议记录  处理
			var notesStr = "";
			if(notes){
				notesStr = delHtmlTag($.trim(notes));
				if(notesStr && notesStr.length > 15){
					notesStr = notesStr.substring(0,15)+"...";
				}
			}
			
			//返回会议记录
			var str = "<div class=\"new_b_bottom\">"+
						"<ul>"+
							"<li class=\"new_b_li_one new_b_li_one1\">"+meetType+"</li>"+
							"<li class=\"new_b_li_two new_b_li_two1\">"+resultStr +"</li>"+
							"<li class=\"new_b_li_three new_b_li_three1\">"+ time +"</li>"+
						"</ul>"+
						"<p>"+notesStr+"</p>"+
					"</div>";
			
	    	$("#near_meet").append(str); 	
	    	
	    } 
	}
}



function closePro(){
	if($(".fjxm_but").hasClass('limits_gray')){
		return;
	}
// 	layer.confirm('你确定要否决项目吗?', 
// 			{
// 			  btn: ['确定', '取消'] 
// 			}, 
// 			function(index, layero){
// 				sendGetRequest(platformUrl.closeProject+proid,null,closeback);
// 			}, 
// 			function(index){
				
// 			}
// 		);
	var formdata = {
			projectId : proid
	}
	refuseProjectDialog.init(formdata);
	
	
}

function transferPro(obj){
	var _url="";
	if(obj=="transfer"){
		_url='<%=path%>/html/handover_project.html';	
	}else if(obj=="assign"){
		_url='<%=path%>/html/assign_project.html';
	}
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			$('.close').addClass('tast-close')//添加关闭按钮
			$("#actionStyle").val(obj);
			$("#numCheck").css("display","none");
			var arr=[proid];
			doSumbit(arr);
		}//模版反回成功执行	
	});
}
function revokePro(){
	var _url=platformUrl.toRevokeProTransfer;
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			revokeTransfer(proid);
		}//模版反回成功执行	
	});
}



//关闭回调
function closeback(data,fuc){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		if(data.result.message && data.result.message.length > 0)
		{
			layer.msg(data.result.message);
		}
		return;
	}else{
		layer.msg("该项目已关闭");
		//toDetail(proid);
		fuc();
		//forwardWithHeader(platformUrl.mpl);
	}
}
/**
 * 设置投后运营信息
 */
function setThyyInfo()
{
	var url = "<%=path%>/galaxy/project/postOperation/getThyyInfo?projectId=${pid}";
	sendGetRequest(url,null,function(data){
		if(data.result.status == 'OK')
		{
			//运营状态
			var healthState = data.userData.healthState;
			var imgSrc = '<%=path %>/img/status/s0'+healthState+'.png';
			$("#yyzt_img").attr('src',imgSrc);
			//交割前确认事项
			var total = data.userData.total;
			var complete = data.userData.complete;
			$(".delivery_complete").text(complete);
		    $(".delivery_total").text(total);
			if(total>0)
			{
		    	var Wh=$(".progressBar").width();
		    	var barWidth=parseInt(complete/total*Wh)+"px";
		    	$("#bar").css("width",barWidth)
			}
			//运营会议
			if(data.entityList.length<3){
				$("#thyy_meet_more").hide();
			}else{
				$("#thyy_meet_more").show();
			}
			if(data.entityList != null && data.entityList.length>0)
			{
				$("#thyy_meet_div .new_b_bottom").remove();
				$("#thyy_meet_div .no_con").hide();
				$.each(data.entityList,function(){
					
					var div = $('<div class="new_b_bottom"></div>');
					var ul = $('<ul class="clearfix "></ul>');
					div.append(ul);
					ul.append('<li class="new_b_li_one">'+this.meetingTypeStr+'</li>');
					ul.append('<li class="new_b_li_three" style="width:66%">'+this.meetingDateStr.substr(0,16)+'</li>');
					//会议记录  处理
					var notes = this.meetingNotes;
					var notesStr = "";
					if(notes)
					{
						notesStr = delHtmlTag($.trim(notes));
						if(notesStr && notesStr.length > 15)
						{
							notesStr = notesStr.substring(0,15)+"...";
						}
					}
					div.append('<p>'+notesStr+'</p>');
					$("#thyy_meet_div").append(div);
				});
			}
			else
			{
				$("#thyy_meet_div .no_con").show();
			}
		}
	});
}
/**
 * 交割前确认事项完成进度
 */
function setJgqrProgress()
{
	$("#bar").css("width","0px");  //初始化进度条宽度；
    var deliveryComplete=$(".delivery_complete").text();
        deliveryTotal=$(".delivery_total").text();
        Wh=$(".progressBar").width();
    barWidth=parseInt(deliveryComplete/deliveryTotal*Wh)+"px";
    $("#bar").css("width",barWidth)
}


$(function(){
	/*   if($(".new_correlation_cen").find(".fjxm_but").length==0){
		$(".yjxm_btn").removeClass("new_btn_right").addClass("new_btn");
		$(".cxxm_btn").removeClass("new_btn_right").addClass("new_btn");
	}  */ 
})
var reportProgress = '${reportProgress}';
console.log(reportProgress);
var progressObject = JSON.parse(reportProgress);
var no = Math.round(progressObject.no*100);//全息报告
var en = Math.round(progressObject.en*100);//评测报告
var dn = Math.round(progressObject.dn*100);//尽调报告
var pn = Math.round(progressObject.pn*100);//决策报告
var cn = Math.round(progressObject.cn*100);//初评报告
var gn = Math.round(progressObject.gn*100);//融资报告
var on = Math.round(progressObject.on*100);//运营报告



/* var progressObject = {
		"projectId":837,
		"uid":104,
		"no":0.03896,
		"dn":0.02273,
		"pn":0.0,
		"gn":0.02336,
		"on":0.0,
		"en":0.0,
		"cn":0.0,
		"id":150,
		"createdTime":1505285294341,
		"updatedTime":1505298252539
} */

/* 进度条 */
function setProcess(num){  
  var processbar = document.getElementsByClassName("processbar"); 
  var percent_number =document.getElementsByClassName("percent_number");  
 // console.log(processbar[3]);
    for(var i=0;i<processbar.length;i++){
    	if(num === no){
    		 processbar[0].style.width = parseInt(num)+"%";
    	  	 percent_number[0].innerHTML = parseInt(num)+"%";
    	}
    	if(num === en){
    		 processbar[1].style.width = parseInt(num)+"%";
    	  	 percent_number[1].innerHTML = parseInt(num)+"%";
    	}
    	if(num === dn){
	   		 processbar[2].style.width = parseInt(num)+"%";
		  	 percent_number[2].innerHTML = parseInt(num)+"%";
		}
    	if(num === pn){
	   		 processbar[3].style.width = parseInt(num)+"%";
		  	 percent_number[3].innerHTML = parseInt(num)+"%";
		}  
    	if(num === cn){
	   		 processbar[4].style.width = parseInt(num)+"%";
		  	 percent_number[4].innerHTML = parseInt(num)+"%";
		}  
    	if(num === gn){
	   		 processbar[5].style.width = parseInt(num)+"%";
		  	 percent_number[5].innerHTML = parseInt(num)+"%";
		}
    	if(num === on){
	   		 processbar[6].style.width = parseInt(num)+"%";
		  	 percent_number[6].innerHTML = parseInt(num)+"%";
		}
		
	 	
  	}
  
  
 }  
setProcess(no);
setProcess(en);
setProcess(dn);
setProcess(pn);
setProcess(cn);
setProcess(gn);
setProcess(on); 
		

  //删除项目请求
function deletePro(){
	var _url = "<%=path %>/galaxy/project/deletePro?_="+new Date().getTime();
	var data = {
			'id'	:	proid,
			'deleteReason':null
		};
	if(pRigthInfo.createUid==userId){
		 layer.confirm("确定删除？",{
			 	title:'提示',
			 	area:['480px','212px'],
			 	btn:['确定','取消'],
			 	skin:"manager_role",
			 	content:'是否删除项目？'
			 
		 },function(i){
			layer.close(i);
			sendPostRequestByJsonObj(
				_url,
				data,
				function(data){
					if(data.result.status=='OK')
					{
						layer.msg("删除成功");
						var url = $("#menus .on a").attr('href');
						window.location = url;
					}
					else
					{
						layer.msg("删除失败。");
					}
				}
			);
		}); 
		
	/* 	layer.open({
			title:'提示',
			area:['600px','405px'],
			btn:['确定','取消'],
			skin:"delete_content",
			content:"<div id='wraper_delete'>"+
        		"<p><span class='delete_msg'></span>是否删除项目</p>"+
        		'<p>删除创投项目会通知该项目投资经理</p>'+
        		'<div class="delete_reason">'+
        			'<span>删除原因：</span>'+
        			'<span>'+
        				'<textarea onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" onkeypress="LimitTextArea(this)" rows="" cols="" placeholder="请输入原因"></textarea>'+
        			'</span>'+
        		'</div>'+
        		'</div>',
        		yes:function(index){
        			sendPostRequestByJsonObj(
        					_url,
        					data,
        					function(data){
        						if(data.result.status=='OK')
        						{
        							 layer.msg('删除成功');
        							var url = $("#menus .on a").attr('href');
        							window.location = url;
        						}
        						else
        						{
        							layer.msg("删除失败。");
        						}
        					}
        				);
        			
        			//layer.close(index)
        		}
					
			
		}); */
		
		
		
	}else{
	<%-- 	var _url = "<%=path %>/galaxy/project/toDeleteProject";	
		$.getHtml({
			url:_url,
			data:"",//传递参数
			okback:function(){
				$('input[name="projectId"]').val(proid);
			}//模版反回成功执行	
			
		}); --%>
		layer.open({
			title:'提示',
			area:['600px','405px'],
			btn:['确定','取消'],
			skin:"delete_content",
			content:"<div id='wraper_delete'>"+
        		"<p><span class='delete_msg'></span>是否删除项目</p>"+
        		'<p>删除创投项目会通知该项目投资经理</p>'+
        		'<div class="delete_reason delete_reason_left">'+
        			'<span>删除原因：</span>'+
        			'<span>'+
        				'<textarea oninput="LimitTextArea(this)" id="deleteReason" rows="" cols="" placeholder="请输入原因"></textarea>'+
        				'<span class="error none"><font>*</font>删除原因不能为空</span>'+
        			'</span>'+
        		'</div>'+
        		'</div>',
        		yes:function(index){
        			var deleteReason=$("#deleteReason").val();
        			data.deleteReason=deleteReason;
        			if(deleteReason.trim().length==0){
        				$('span.error').removeClass('none');
        				return;
        			}
        			sendPostRequestByJsonObj(
        					_url,
        					data,
        					function(data){
        						if(data.result.status=='OK')
        						{
        							 layer.msg('删除成功');
        							var url = $("#menus .on a").attr('href');
        							window.location = url;
        						}
        						else
        						{
        							layer.msg("删除失败。");
        						}
        					}
        				); 
        			
        			//layer.close(index)
        		}
					
			
		});
	}
}
	
//浏览器窗口带下改变，弹层重新定位
popMiddle()
function popMiddle(){
	var wh = parseInt($(".tab_info_common_width").outerWidth(true)),
	ht = parseInt($(".tab_info_common_width").outerHeight(true));
	var win_w = $(window).width(),
	win_h = $(window).height(),
	win_x = (win_w-wh)/2,
	win_y = (win_h-ht)/2;
	//弹出层定位+显示
	$(".tab_info_common_width").Fixed({
		x:win_x,
		y:win_y
	});
}
$(window).resize(function(){
	popMiddle()
})
   			
 		function LimitTextArea(obj){
			var length = $(obj).val().length;
			if($(obj).val().trim().length>0){
				$(obj).next('.error').addClass('none');
			}
			if(length>100){
				var content = $(obj).val().substring(0,100);
				$(obj).val(content);
			}
	
		}
  
  
  

</script>



