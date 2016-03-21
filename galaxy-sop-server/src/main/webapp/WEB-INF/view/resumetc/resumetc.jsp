<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="<%=path %>/stylesheet" type="text/css" href="css/lq.datetimepick.css"/>
<link rel="<%=path %>/stylesheet" type="text/css" href="bootstrap/bootstrap-datepicker/css/bootstrap-datepicker.min.css"/>
<link href="<%=path %>/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
<script src="<%=path %>/js/resumetc.js" type="text/javascript"></script>
<script src="<%=path %>/star/jquery.raty.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<div class="resumetc" >
          <!-- 个人简历 -->
          <div class="tabtable resume">
          <form action="" id="up_person_form" method="post">
          <input hidden="hidden" id="personId" value="${personId}">
          <!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">个人信息</a></li>
                <li data-tab="nav"><a href="javascript:;">学习经历</a></li>
                <li data-tab="nav"><a href="javascript:;">工作经历</a></li>           
                <li data-tab="nav"><a href="javascript:;">外部项目信息</a></li>           
            </ul>
            <!-- tab内容 -->
            <div class="tabtable_con "  data-tab="con"  model="personPool" multi="false">
              <div class="tabtable_con1">
                 <h2>核心成员基本资料</h2>
                <table>
                  <tr>
                    <th>团队角色：</th>
                    <td data-by="id"><input name="teamRole" id="teamRole" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>姓名：</th>
                    <td><input name="personName" id="personName" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>成员关系：</th>
                    <td><input name="memberRelation"  id="memberRelation" type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>个人资料</h2>
                <table>
                  <tr>
                    <th>性别：</th>
                    <td>
                      	<label><input id="personSex0" name="personSex" type="radio" value="0" >男</label>
            			<label><input id="personSex1" name="personSex" type="radio" value="1" >女</label>
                    </td>
                  </tr>
<!--                <tr>
                    <th>出生日期：</th>
                    <td><input  type="text"  name="personBirthday" id="personBirthday" value=""/> </td>
                  </tr>  -->
                  <tr>
                    <th>身份证号码：</th>
                    <td><input name="personIdcard" id="personIdcard" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>电话：</th>
                    <td><input name="personTelephone" id="personTelephone" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>邮箱：</th>
                    <td><input name="personEmail" id="personEmail" type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>个人能力</h2>
                <table>
                  <tr>
                    <th>性格：</th>
                    <td><input name="personCharacter" id="personCharacter" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>优势：</th>
                    <td><input name="personGoodness" id="personGoodness" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>劣势：</th>
                    <td><input name="personDisparity" id="personDisparity" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>沟通能力：</th>
                    <td><input name="talkAbility " id="talkAbility " type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>团队协作能力：</th>
                    <td><input name="teamAbility" id="teamAbility" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>核心竞争力（业务相关能力）：</th>
                    <td><input name="businessStrength" id="businessStrength" type="text" value=""> </td>
                  </tr>
                </table>
              </div>

            </div>

            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personLearn" multi="true">
              <div class="tabtable_con1">
                <h2>学习经历</h2>
                <input type="button" class="add bluebtn" value="+">
                <table>
                  <tr>
                    <th>学历：</th>
                    <td data-by="id"><input name ="degree" id="degree" type="text" value=""></td>
                   	<td data-by="id"><input name ="degree" id="degree" type="text" value=""></td>
                    <td data-by="id"><input name ="degree" id="degree" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>学校：</th>
                    <td><input name="school" id="school" type="text" value=""></td>
                  	<td><input name="school" id="school" type="text" value=""></td>
                    <td><input name="school" id="school" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>专业：</th>
                    <td><input name="major"id="major" type="text" value=""></td>
                    <td><input name="major"id="major" type="text" value=""></td>
                    <td><input name="major"id="major" type="text" value=""></td> 
                  </tr>                  
                  <tr>
                    <th>学历性质：</th>
                    <td><input name="educationType" id="educationType" type="text" value=""></td>
                	<td><input name="educationType" id="educationType" type="text" value=""></td>
                    <td><input name="educationType" id="educationType" type="text" value=""></td>
                  </tr>                  
   				 <tr>
                    <th>毕业年份：</th>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker"></td>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker"></td>
                    <td><input name="overDateStr" id="overDateStr" type="text" value="" class="datepicker"></td>
                  </tr>             
                  <tr>
                    <th>毕业证书/学历证书的证书编号：</th>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value=""></td>
                   	<td><input name="certificateNumber" id="certificateNumber" type="text" value=""></td>
                    <td><input name="certificateNumber" id="certificateNumber" type="text" value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>教育背调联系人</h2>               
                <table>
                  <tr>
                    <th>老师姓名：</th>
                    <td><input name="teacherName" id="teacherName" type="text" value=""></td>
                   	<td><input name="teacherName" id="teacherName" type="text" value=""></td>
                    <td><input name="teacherName" id="teacherName" type="text" value=""></td> 
                  </tr>
                  <tr>
                    <th>部门：</th>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value=""></td>
					<td><input name="teacherPosition" id="teacherPosition" type="text" value=""></td>
                    <td><input name="teacherPosition" id="teacherPosition" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>老师座机：</th>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value=""></td>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value=""></td>
                    <td><input name="teacherPhone" id="teacherPhone" type="text" value=""></td>
                  </tr>
                  <tr>
                    <th>同学姓名：</th>
                    <td><input name="classmateName" id="classmateName" type="text" value=""></td>
                  	<td><input type="text" name="classmateName"></td>
                    <td><input type="text" name="classmateName"></td>
                  </tr>
                  <tr>
                    <th>同学电话：</th>
                    <td><input name="classmatePhone" id="classmatePhone" type="text" value=""></td>
	                <td><input type="text" value="" name="classmatePhone"></td>
                    <td><input type="text" value="" name="classmatePhone"></td>
                  </tr>
                </table>
              </div>
            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personWork" multi="true" >
              <div class="tabtable_con1">
                <h2>工作记录</h2>
                <input type="button" class="add bluebtn" value="+">
                <table>
                  <tr>
                    <th>公司名称:</th>
                    <td data-by="id" ><input type="text" name="companyName" id="companyName" value=""></td>
                  </tr>
                  <tr>
                    <th>部门:</th>
                    <td><input type="text" name="workDepart" id="workDepart" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" name="workPosition" id="workPosition" value=""></td>
                  </tr>
                  <tr>
                    <th>工作内容:</th>
                    <td><input type="text" name="workContent" id="workContent" value=""></td>
                  </tr>
                  <tr>
                    <th>工作业绩:</th>
                    <td><input type="text" name="workEffect" id="workEffect" value=""></td>
                  </tr>
                  <tr>
                    <th>薪酬水平（元/月）:</th>
                    <td><input type="text" name="workEmolument" id="workEmolument" value=""></td>
                  </tr>
                <tr>
                    <th>入职时间:</th>
                    <td><input type="text" name="beginWorkStr" id="beginWorkStr" value=""  class="datepicker"></td>
                  </tr>
                  <tr>
                    <th>离职原因:</th>
                    <td><input type="text" name="leaveReason" id="leaveReason" value=""></td>
                  </tr>
                  <tr>
                    <th>上级姓名:</th>
                    <td><input type="text" name="leaderName" id="leaderName" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" name="leaderPosition" id="leaderPosition" value=""></td>
                  </tr>
                  <tr>
                    <th>工作关系（如直接上下级）:</th>
                    <td><input type="text" name="leaderRelationship" id="leaderRelationship" value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式:</th>
                    <td><input type="text" name="leaderPhone" id="leaderPhone" value=""></td>
                  </tr>
                  <tr>
                    <th>同事姓名:</th>
                    <td><input type="text" name="colleagueName" id="colleagueName" value=""></td>
                  </tr>
                  <tr>
                    <th>职位:</th>
                    <td><input type="text" name="colleaguePosition" id="colleaguePosition" value=""></td>
                  </tr>
                  <tr>
                    <th>工作关系:</th>
                    <td><input type="text" name="colleagueRelationship" id="colleagueRelationship" value=""></td>
                  </tr>                  
                  <tr>
                    <th>座机:</th>
                    <td><input type="text" name="colleaguePhone" id="colleaguePhone" value=""></td>
                  </tr>
                </table>
              </div>

            </div>
            <!-- tab内容 -->
            <div class="tabtable_con"  data-tab="con" model="personInvest" multi="false">
              <div class="tabtable_con1">
                <h2>投资方信息（天使轮）</h2>
                   <table>
                  <tr>
                    <th>公司名称：</th>
                    <td data-by="id"><input type="text" name="icompanyName" id="icompanyName"  value=""></td>
                  </tr>
                  <tr>
                    <th>投资金额：</th>
                    <td><input type="text" name="investmentAmount" id="investmentAmount"  value=""></td>
                  </tr>
                  <tr>
                    <th>股权占比（%）：</th>
                    <td><input type="text" name="shareRatio" id="shareRatio"  value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" name="telephone" id="telephone"  value=""></td>
                  </tr>
                </table>
              </div>
              <div class="tabtable_con1">
                <h2>投资方信息（Pre A轮）</h2>
 				<table>
                  <tr>
                    <th>公司名称：</th>
                    <td><input type="text" name="acompanyName" id="acompanyName"  value=""></td>
                  </tr>
                  <tr>
                    <th>投资金额：</th>
                    <td><input type="text" name="ainvestmentAmount" id="ainvestmentAmount"  value=""></td>
                  </tr>
                  <tr>
                    <th>股权占比（%）：</th>
                    <td><input type="text" name="ashareRatio" id="ashareRatio"  value=""></td>
                  </tr>
                  <tr>
                    <th>联系方式：</th>
                    <td><input type="text" name="atelephone" id="atelephone"  value=""></td>
                  </tr>
                </table>
              </div>
            </div>
            <dl class="fmdl clearfix">
                <dt>是否有劳动纠纷：</dt>
                <dd>
                  <label for=""><input type="radio" name="laborDispute" value="1"/>是</label>
                  <label for=""><input type="radio" name="laborDispute"  value="0"/>否</label>
                </dd>                
              </dl>
              <div class="tabtable_con1 tabtable_con2">
                <table>
                  <tr>
                    <th>能力匹配：</th>
                    <td>
                    	<div id="abilityStar" action="star"></div>
                    	<input id="input-21c" name="abilityStar"  type="hidden" target="star">
                    </td>
                  </tr>
                  <tr>
                    <th>评级：</th>
                    <td>
                    	<div id="levelStar" action="star"></div>
                    	<input id="input-21c" name="levelStar" type="hidden" target="star">
                    </td>
                  </tr>
                  <tr>
                    <th>评语：</th>
                    <td><textarea id="endComment" name="endComment"></textarea></td>
                  </tr>
                </table>
              </div>
              </form>
              <div class="btnbox">
                <a href="javascript:;"  class="pubbtn bluebtn">保存</a>
                <a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
              </div>

          </div>
</div>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
 	sendGetRequest(platformUrl.toaddPersonHr+$("#personId").val(), null, wanshancbf);
 	$("body").delegate(".datepicker", "focusin", function(){
 		$(this).datepicker({
 		    format: 'yyyy-mm-dd',
 		    language: "zh-CN",
 		    autoclose: true,
 		    todayHighlight: false,
 		    calendarWeeks: true,
 		    weekStart:1,
 		    today: "Today",
 		    todayBtn:'linked',
 		    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
 		    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
 		    forceParse:false
 		    //defaultViewDate: { year: 1977, month: 04, day: 25 },
 		    //daysOfWeekDisabled: "0",
 		    //daysOfWeekHighlighted: "0",
 		    //clearBtn: true,
 		    //startView: 1, //0,month 1,year 2,decade
 		    //minViewMode: 1,  //0,days 1,month 2,years
 		    //maxViewMode: 1, //0,days 1,month 2,years
 		    //startDate: '-3d',
 		    //endDate: '+3d'
 		});
 	});
})
function wanshancbf(data){
	if(data.result.status == "OK"){
		var personInvest = data.entity.personInvest;
		var personLearns = data.entity.personLearn;
		var personPool = data.entity.personPool;
		var personWorks = data.entity.personWork;
		if(personInvest != undefined ){
			var model_personInvest = $("div[model='personInvest']");
			model_personInvest.find("input[type!='radio']").each(function(index,input_item){
				var input  = $(input_item);
				var name = input.attr("name");
				input.val(personInvest[name]);
			});
			model_personInvest.find("td[data-by]").attr("data-val",personInvest["id"]);
		}
		if(personPool != undefined ){
			var model_personPool = $("div[model='personPool']");
			model_personPool.find("input[type!='radio']").each(function(index,input_item){
				var input  = $(input_item);
				var name = input.attr("name");
				input.val(personPool[name]);
			});
			model_personPool.find("td[data-by]").attr("data-val",personPool["id"]);
			
 			$("input:radio[name='personSex'][value='"+personPool['personSex']+"']").attr("checked","checked"); 
			$("input:radio[name='laborDispute'][value='"+personPool['laborDispute']+"']").attr("checked","checked") ; 
			$("#endComment").val(personPool['endComment']);
			$("#levelStar").raty({
				starOn:"<%=path %>"+"/star/img/star-on.png",
			    starHalf:"<%=path %>"+ "/star/img/star-half.png",
			    starOff : "<%=path %>"+"/star/img/star-off.png",
			    starOn : "<%=path %>"+"/star/img/star-on.png",
				score: personPool['levelStar']});
			$("#abilityStar").raty({
				starOn:"<%=path %>"+"/star/img/star-on.png",
			    starHalf:"<%=path %>"+ "/star/img/star-half.png",
			    starOff : "<%=path %>"+"/star/img/star-off.png",
			    starOn : "<%=path %>"+"/star/img/star-on.png",
				score: personPool['abilityStar'] });
		}
		var model_personLearn =  $("div[model='personLearn']");
		var td_personLearn = model_personLearn.find("td[data-by]");
		for(var i = 0 ;i < personLearns.length ;i++){
			var personLearn = personLearns[i];
			model_personLearn.find("tr").each(function(m,tr_item){
				var input = $($(tr_item).find("input[name]")[i]);
				input.val(personLearn[input.attr("name")]);
			});
			$(td_personLearn[i]).attr("data-val",personLearn["id"]);
			
			if(personLearns.length > td_personLearn.length){
				appendTd(model_personLearn);				
			}
			td_personLearn = model_personLearn.find("td[data-by]");
		}
		
		var model_personWork =  $("div[model='personWork']");
		var td_personWork = model_personWork.find("td[data-by]");
		for(var i = 0 ;i < personWorks.length ;i++){
			var personWork = personWorks[i];
			model_personWork.find("tr").each(function(m,tr_item){
				var input = $($(tr_item).find("input[name]")[i]);
				input.val(personWork[input.attr("name")]);
			});
-			$(td_personWork[i]).attr("data-val",personWork["id"]);
			if(personWorks.length > td_personWork.length){
				appendTd(model_personWork);				
			}
			td_personWork = model_personWork.find("td[data-by]");
		}
	}
}
$("div[model]").on("click",".add",function(){
	var model = $(this).parent().parent();
	appendTd(model)
});
function appendTd(model){
	model.find("tr").each(function(index,tr){
		var input =  $($(tr).find("input")[0]);
		var name = input.attr("name");
		var type = input.attr("type");
		var class_name = input.attr("class");
		if(index == 0 ){
			$(tr).append("<td data-by='id'><input type='"+type+"' name='"+name+"'/></td>");
		}else{
			if(class_name == "datepicker"){
				$(tr).append("<td><input class='"+class_name+"' type='"+type+"' name='"+name+"'/></td>");
			}else{
				$(tr).append("<td><input  ' type='"+type+"' name='"+name+"'/></td>");
			}
		}
	});
}
function prependTd(model,model_data){
	model.find("tr[type!=hidden]").each(function(index,item){
		var tr = $(item);
		var td = tr.find("td")[0];
		tr.prepend("<td>"+td.innerHTML+"</td>");
	});
}
$(".btnbox").on("click",".bluebtn",function(){
	var models = $("div[model]");
	var data = {};
	models.each(function(i,item){
		var it = $(item);
		//单个实体
		var model = null ;
		var name = it.attr("model");
		var multi = it.attr("multi");
		if(multi == true || multi =="true"){
			model = new Array();
			var len = it.find("tr").eq(0).find("input[name]").length;
			for(var i = 0 ;i <len;i++){
				var son_model = {};
				it.find("tr").each(function(m,tr_item){
					var input = $(tr_item).find("input[name][type!=hidden]")[i];
					if($(input).val() != ''){
						son_model[$(input).attr("name")] = $(input).val();	
					}
					
				});
				var td = $($(it.find("tr")).find("td[data-by]").eq(i));
				son_model[td.attr("data-by")] = td.attr("data-val");
				model[i] = son_model;
			}
			data[name] = model;	
		}else{
			model = {};
			it.find("input[name]").each(function(index,input){
				if($(input).val() != ''){
					model[$(input).attr("name")] = $(input).val() ;
				}
				
			});
			var td = it.find("td[data-by]");
			model[td.attr("data-by")] = td.attr("data-val");
			data[name] = model;
		}
		
	});
	data['personId'] = $("#personId").val();
	data['personPool']['personSex'] = $("input[name='personSex']:checked").val();
	data['personPool']['laborDispute'] = $("input[name='laborDispute']:checked").val();
	data['personPool']['endComment'] = $("#endComment").val();
	data['personPool']['levelStar'] = $("#levelStar").find("input[name='score']").val();
	data['personPool']['abilityStar'] = $("#abilityStar").find("input[name='score']").val();
	sendPostRequestByJsonObj(platformUrl.addPersonHr, data, savecbf);
});
function savecbf(data){
	if(data.result.status == "OK"){
		layer.msg("成功");
		$("a[data-close='close']").trigger("click");
	}else{
		layer.msg(data.result.message);
	}
}
</script> 
           