<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<% 
	String path = request.getContextPath(); 
%>
   
 	

        	<input type="hidden" id="pid" name="id" value="${pid}"/>

	            <c:if test="${aclViewProject==true}">
				<div class="member">
					<c:if test="${isEditable}">
				      <div class="top clearfix">
				          <!--按钮-->
				          <div class="btnbox_f btnbox_f1 clearfix">
				              <a id="add_person_btn" href="javascript:;" class="pubbtn bluebtn ico c4 add_prj add_profile" onclick="addPerson();">添加</a>
				             <!--  <a href="javascript:;" class="pubbtn bluebtn edit_profile" onclick="toSureMsg();">完善简历</a> -->
				          </div>
				      </div>
					</c:if>
				              <!--表格内容-->
				               <div class="tab-pane active commonsize" id="view">	
				      <table id="tablePerson" width="100%" data-height="555" 
				            	data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" >
					  </table> 
					  </div>
				     
				</div>
	            </c:if>

<script src="<%=path %>/js/person.js"></script>


<script>

	var proinfo = '${proinfo}';
	var proid = '${pid}';
	var pname = '${pname}';
	var interviewSelectRow = null;
	var projectId ='${pid}';
	var flag = '${flag}';
	var isCreatedByUser = "${fx:isCreatedByUser('project',pid) }";
	var isTransfering = "${fx:isTransfering(pid) }";
$(function(){
	getTabPerson();
	if(isTransfering == 'true')
	{
		$("#add_person_btn").addClass('limits_gray');
	}
	
	
});	
//添加团队成员
function addPerson(){
	if($("#add_person_btn").hasClass('limits_gray'))
	{
		return;
	}
	sendGetRequest(platformUrl.getDegreeByParent + "/" + "null",null,addPersonCallBack);
	return false;
}
function addPersonCallBack(data){
	   $.getHtml({
			url:platformUrl.addPersonView,//模版请求地址
			data:"",//传递参数
			okback:function(){
				var _dom =  $("#highestDegree");
			    _dom.empty();
				_dom.append("<option value='all'>请选择</option>");
				$.each(data.entityList,function(){
					if(this.code){
						_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
					}else{
						_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
					}
					
				});	
			}//模版反回成功执行	
				
		});     
	}
	function updatePer(id){
		sendGetRequest(platformUrl.getDegreeByParent+"/"+id,null,updatePerCallBack);
		return false;
	}

	function updatePerCallBack(data){
		var _url = platformUrl.updatePerView+data.result.message;
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				
				var _dom =  $("#highestDegree");
			    _dom.empty();
				_dom.append("<option value='all'>请选择</option>");
				$.each(data.entityList,function(){
					if(_dom.data("value")==this.code){
						_dom.append("<option value='"+this.code+"' selected=true >"+this.name+"</option>");
					}else{
						_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
					}
					
				});	
			
			}//模版反回成功执行	
		});
	}

	//个人简历
	function tiaozhuan(id){
		//创建者可编辑
		var url = platformUrl.personHHr;
		if(isCreatedByUser == "true"){
			url =platformUrl.personHr;
		}
		$.getHtml({
			url: url+"?personId="+id, 
			data:"",//传递参数
			okback:function(){
			$(".resumetc .tabtable").tabchange2();
			}//模版反回成功执行	
		}); 
		
	}
	function toSureMsg(){
		sendGetRequest(
				Constants.sopEndpointURL + '/galaxy/soptask/toSureMsg/'+projectId, 
				 null, function(data){
					 layer.msg(data.result.message);
				 });
	}


	function reloadJS(){
		loadJs('<%=request.getContextPath() %>/js/axure.js'); 
	}
</script>

