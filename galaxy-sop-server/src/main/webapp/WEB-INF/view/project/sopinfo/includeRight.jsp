<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>项目详情</title>
<!--
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

</head>



        
        <!--右边        li  class="green_dot"  span class="green_dot_on"    <span class="gray_dot"></span> -->  
        <div class="new_right">
        
        	<b class="sj_ico null">三角</b>
        	<div class="new_right_flow">
            	<div class="new_right_flow_line">
                	<ul class="line_ul_p" id="lct_ul">
                    	<li class="line_ul_li"><span class="one_dot"></span></li>
                    	<li><span class="gray_dot"></span>接触访谈</li>
                        <li><span class="gray_dot"></span>内部评审</li>
                        <li><span class="gray_dot"></span>CEO评审</li>
                        <li><span class="gray_dot"></span>立项会</li>
                        <li><span class="gray_dot"></span>投资意向书</li>
                        <li><span class="gray_dot"></span>尽职调查</li>
                        <li><span class="gray_dot"></span>投决会</li>
                        <li><span class="gray_dot"></span>投资协议</li>
                        <li><span class="gray_dot"></span>股权交割</li>
                        <li><span class="gray_dot"></span>投后运营</li>
                    </ul>
                </div>
                 <span class="bluebtn new_btn" >项目流程</span>
            </div>
           
           
            
            <div class="correlation">相关操作</div>
            <div class="new_correlation_cen">
            	<span class="bluebtn new_btn" onclick="closePro()">否决项目</span>
            </div>
            
            
            
            
            <div class="correlation">
            	近期会议纪要
				<span class="more null new_righ" id="meet_more" style="cursor: pointer;" >more</span>
			</div>
            <div class="new_correlation_cen" id="near_meet">
            </div>
            
             
             
            <div class="correlation">近期访谈记录
				<span class="more null new_righ" id="view_more" style="cursor: pointer;" >more</span>
			</div>
            <div class="new_correlation_cen" id="near_view">
            </div>
            
        </div>
        
        <!--右边 end-->
   
   

<script>

var proid = '${pid}';
var prograss = '${prograss}';
if(!prograss){
	prograss = 'projectProgress:0';
}
var index = Number(prograss.substring(prograss.length-1,prograss.length));


$(function(){
	init_lct(); //流程图初始化
	
	//获取近期访谈、会议 记录
	sendGetRequest(Constants.sopEndpointURL+"/galaxy/project/getnearnotes/" + proid, null, formatNearNotes);
	
	//more 链接初始化
	$("#meet_more").on("click", function(){
		toMeet(proid);
	});
	$("#view_more").on("click", function(){
		toInterView(proid);
	});
	
})



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
				$(this).prepend("<span class='green_dot_on'></span>");
			}
		}
	});
}

/**
 * 格式化 近期 访谈 会议
 */
function formatNearNotes(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		layer.msg(data.result.message);
		return;
	}
	
	var viewList = data.userData.viewList;
	var meetList = data.userData.meetList;
	
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
							"<li class=\"new_b_li_one\">"+meetType+"</li>"+
							"<li class=\"new_b_li_two\">"+resultStr +"</li>"+
							"<li class=\"new_b_li_three\">"+ time +"</li>"+
						"</ul>"+
						"<p>"+notesStr+"</p>"+
					"</div>";
			
	    	$("#near_meet").append(str);
	    } 
	}
	
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



function closePro(){
	if(confirm("确定要否决项目吗？")){
		sendGetRequest(platformUrl.closeProject+proid,null,closeback);
	}
}


//关闭回调
function closeback(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		alert("error "+data.result.message);
		return;
	}else{
		layer.msg("该项目已关闭");
		toDetail(proid);
		//forwardWithHeader(platformUrl.mpl);
	}
}



</script>
</html>

