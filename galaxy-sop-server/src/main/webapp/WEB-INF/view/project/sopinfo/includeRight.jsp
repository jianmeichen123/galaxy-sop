<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>




        
        <!--右边        li  class="green_dot"  span class="green_dot_on"    <span class="gray_dot"></span> -->  
        <div class="new_right">
        	<b class="sj_ico null">三角</b>
        	<!-- 投后运营Start -->
        	<c:if test="${aclViewProject }">
        	
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
				</div>
	
				<div class="correlation">近期会议纪要 <span class="more null new_righ" id="thyy_meet_more" style="cursor: pointer;">more</span>
				</div>
				<div class="new_correlation_cen new_correlation_cen_con" id="thyy_meet_div">
					<div class="no_con">
	            		暂无会议纪要
	            	</div>
				</div>
			</div>
			</c:if>
			<!-- 投后运营End -->
			<!-- 投前Start -->
			<div id="tq_div" style="display:none;">
			
        	<div class="new_right_flow">
            	<div class="new_right_flow_line">
                	<ul class="line_ul_p" id="lct_ul">
                    	<li class="line_ul_li"><span class="one_dot"></span></li>
                    	<li><span class="gray_dot"></span>接触访谈</li>
                        <li><span class="gray_dot"></span>内部评审</li>
                        <li><span class="gray_dot"></span>C<br/>E<br/>O评审</li>
                        <li><span class="gray_dot"></span>立项会</li>
                        <li><span class="gray_dot"></span>投资意向书</li>
                        <li><span class="gray_dot"></span>尽职调查</li>
                        <li><span class="gray_dot"></span>投决会</li>
                        <li><span class="gray_dot"></span>投资协议</li>
                        <li><span class="gray_dot"></span>股权交割</li>
                        <li><span class="gray_dot"></span>投后运营</li>
                    </ul>
                </div>
                 <!-- <span class="bluebtn new_btn" style="display: none;">项目流程</span> -->
            </div>
           
           
            
            <div class="correlation">相关操作</div> 
            <div class="new_correlation_cen">
            	<span class="bluebtn new_btn" onclick="closePro(this)" id="fjxm_but">否决项目</span>
            	<span class="bluebtn new_btn" onclick="transferPro()" style="display:none" id="yjxm_btn">移交项目</span>
                <span class="bluebtn new_btn" onclick="revokePro()" style="display:none" id="cxxm_btn">撤销移交</span>
            </div>
            
            
            
            
            <c:if test="${fx:hasRole(1) || fx:hasRole(2) || fx:hasRole(3)|| fx:isCreatedByUser('project',pid) }">
            <div class="correlation">
            	近期会议纪要
				<span class="more null new_righ" id="meet_more" style="cursor: pointer;" >more</span>
			</div>
            <div class="new_correlation_cen new_correlation_cen_con" id="near_meet">
            <div class="no_con">
            		暂无会议纪要
            	</div>
            </div>
             
             
            <div class="correlation">近期访谈记录
				<span class="more null new_righ" id="view_more" style="cursor: pointer;" >more</span>
			</div>
            <div class="new_correlation_cen new_correlation_cen_con"  id="near_view">
            	<div class="no_con">
            		暂无访谈记录
            	</div>
            </div>
            </c:if>
            
        </div>
        </div>
        <!-- 投前End -->
        <!--右边 end-->
   
<script>
var proid = pid;
var prograss = projectInfo.projectProgress;
/* if(isCreatedByUser == "true"){
	$("#fjxm_but").attr("style","background:#d6d8e1;");
	$("#yjxm_btn").attr("style","display:none;");
	$("#cxxm_btn").attr("style","display:block;");
	
} */

if(isTransfering == 'true')
{
	$('#fjxm_but').addClass('limits_gray');
	$("#yjxm_btn").attr("style","display:none;");
	$("#cxxm_btn").attr("style","display:block;");
	
}else{
	$('#fjxm_but').removeClass('limits_gray');
	$("#yjxm_btn").attr("style","display:block;");
	$("#cxxm_btn").attr("style","display:none;");
}

if(!prograss){
	prograss = 'projectProgress:0';
}
var index = Number(prograss.substring("projectProgress:".length,prograss.length));
var admin = "${fx:isCreatedByUser('project',pid) }";
var isGG = "${fx:hasRole(1) || fx:hasRole(2) || fx:hasRole(3)}";
$(function(){
	//显示投前或投后信息
	if(prograss == 'projectProgress:10')
	{
		if($("#thyy_div").length>0)
		{
			$("#thyy_div").show();
			$("#thyy_meet_more").click(function(){
				showTabs(proid,9)
			});
			setThyyInfo();
		}
	}
	else
	{
		$.each($('.new_left .tablinks li'),function(){
			var tab = $(this);
			if(tab.find('a').text() == '运营分析')
			{
				tab.remove();
			}
		});
		
		$("#tq_div").show();
	}
	init_lct(); //流程图初始化
	
	if(projectInfo.projectStatus == 'meetingResult:3' || projectInfo.projectStatus == 'projectStatus:2' || projectInfo.projectStatus == 'projectStatus:3' || admin!="true"){
		$("#fjxm_but").removeAttr("onclick").attr("disabled","disabled").addClass("disabled");
	}
		
	//获取近期访谈、会议 记录
	if(prograss != 'projectProgress:10' && (admin == 'true' || isGG == 'true'))
	{

		toFormatNearNotes();
		//more 链接初始化
		initMoreLine();
	}
	
	
	setJgqrProgress();
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
		showTabs(proid,4)
	});
	$("#view_more").on("click", function(){
		showTabs(proid,3)
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
function init_lct(){
	var ul_li = $("#lct_ul").children("li");
	$(ul_li).each(function(i){
		if(index!=0 && i!=0){
			if(index > i){
				$(this).addClass("green_dot");
			}else if(index == i){
				$(this).addClass("green_dot");
				$(this).addClass('green_dot_color');
				$(this).prepend("<span class='green_dot_on'></span>");
			}
		}
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
	if($("#fjxm_but").hasClass('limits_gray')){
		return;
	}
	layer.confirm('你确定要否决项目吗?', 
			{
			  btn: ['确定', '取消'] 
			}, 
			function(index, layero){
				sendGetRequest(platformUrl.closeProject+proid,null,closeback);
			}, 
			function(index){
				
			}
		);
}

function transferPro(){
	var _url=platformUrl.toProjectTransfer;
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			doSumbit(proid);
		}//模版反回成功执行	
	});
}
function revokePro(){
	var _url=platformUrl.toProjectTransfer;
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			doSumbit(proid);
		}//模版反回成功执行	
	});
}



//关闭回调
function closeback(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		alert("error "+data.result.message);
		return;
	}else{
		layer.msg("该项目已关闭");
		//toDetail(proid);
		showTabs(proid,0)
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


</script>


