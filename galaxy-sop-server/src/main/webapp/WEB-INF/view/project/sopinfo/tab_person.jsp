<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 这里写js和css文件 start -->
<script src="<%=path%>/js/axure.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<div class="member">
      <div class="top clearfix">
          <!--按钮-->
          <div class="btnbox_f btnbox_f1 clearfix">
              <a href="javascript:;" class="pubbtn bluebtn ico c4 add_prj add_profile" onclick="addPerson();">添加</a>
              <a href="javascript:;" class="pubbtn bluebtn edit_profile" onclick="toSureMsg();">完善简历</a>
          </div>
      </div>
              <!--表格内容-->
               <div class="tab-pane active" id="view">	
      <table id="tablePerson" width="100%" data-height="555" 
            	data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" >
	  </table> 
	  </div>
     
</div>
<script>
function loadJs(file) {
    var head = $("head").remove("script[role='reload']");
    $("<scri" + "pt>" + "</scr" + "ipt>").attr({ role: 'reload', src: file, type: 'text/javascript' }).appendTo(head);
}
var id='${pid}';
getTabPerson(id);

//添加团队成员
function addPerson(){
	reloadJS();
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
	reloadJS(); 
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
	reloadJS();
	var url =platformUrl.personHHr
	$.getHtml({
		url: url+"?personId="+id, 
		data:"",//传递参数
		okback:function(){
		/* alert("进入完善简历页面"); */
		$(".resumetc .tabtable").tabchange2();
		}//模版反回成功执行	
	}); 
	
}
function toSureMsg(){
	alert(9)
	sendGetRequest(
			Constants.sopEndpointURL + '/galaxy/soptask/toSureMsg/'+pid, 
			 null, function(data){
				 layer.msg(data.result.message);
			 });
}


function reloadJS(){
	loadJs('<%=request.getContextPath() %>/js/axure.js'); 
}
</script>
<script src="<%=path %>/js/person.js"></script>
