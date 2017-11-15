<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
setPageId("meetingRecord_add");
</script>
<div class="title_bj">新增会议记录</div>
<form id="meeting_per_add_form">
	<input type="hidden" id="projectId" name="projectId"/>
<div class="meetingtc margin_45" id="add_meet_dialog" style='padding-left:20px;'>
    <div class="min clearfix">
        <dl class="fmdl clearfix">
        	<dt>项目名称:</dt>
            <dd class="clearfix">
            	<input type="text" id="projectName" name="projectName" class="txt s_txt" placeholder="请输入要搜索的项目名称"/>
            </dd>
        </dl>
     </div>
     <div class="min clearfix">  
        <dl class="fmdl clearfix">
        	<dt>投资经理:</dt>
            <dd class="clearfix"><span id="createUname"></span>
            </dd>
        </dl>
     </div>
     <div class="min clearfix">
        <dl class="fmdl clearfix">
        	<dt>事业部:</dt>
            <dd class="clearfix"><span id="projectCareerline"></span>
            </dd>
        </dl>
      </div>
      <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>会议类型：</dt>
            <dd class="clearfix">
            	<select id="meetingType">
            		<option value="">请选择</option>
            		<c:forEach var="meetingType" items="${meetingType }">
            		<option value="${meetingType.code }">${meetingType.name }</option>
            		</c:forEach>
            	</select>
            </dd>
        </dl>
    </div>
    <div class="btnbox" id="dialog-btns" style='margin-top:30px;'>
    	<a href="javascript:;" class="pubbtn bluebtn" id="submit-bnt">确认</a><a href="javascript:;" class="pubbtn fffbtn" data-close="close" id="cancel-btn">取消</a>
    </div>
</div>
</form>
<script>
$( "#meeting_per_add_form #projectName" ).blur(function(){
	var val = $(this).val();
	if(val == '')
	{
		$( "#meeting_per_add_form #projectId" ).val('');
		$( "#meeting_per_add_form #createUname" ).text('');
    	$( "#meeting_per_add_form #projectCareerline" ).text('');
	}
});
$( "#meeting_per_add_form #projectName" ).autocomplete({
    source: function( req, resp ){
    	var val = $( "#meeting_per_add_form #projectName" ).val();
    	var url = Constants.sopEndpointURL+"/galaxy/project/search";
    	var data = {
   			nameCodeLike:val,
   			createUid:0,
   			pageNum:0,
   			pageSize:10,
   			property:"updated_time",
   			direction:"desc"
   		};
   		var callback = function(data){
   			$( "#meeting_per_add_form #projectId" ).val('');
   			$( "#meeting_per_add_form #createUname" ).text('');
   	    	$( "#meeting_per_add_form #projectCareerline" ).text('');
   			resp($.map(data.pageList.content,function(item){
   				return {
   					label: item.projectName,
   					value: item.id,
   					createUname: item.createUname,
   					projectCareerline: item.projectCareerline
   				}
   			}));
   		};
   		sendPostRequestByJsonObj(url,data,callback);
    },
    select: function( event, ui ) {
    	$(this).val(ui.item.label);
    	$( "#meeting_per_add_form #projectId" ).val(ui.item.value);
    	$( "#meeting_per_add_form #createUname" ).text(ui.item.createUname);
    	$( "#meeting_per_add_form #projectCareerline" ).text(ui.item.projectCareerline);
    	return false; 
    }
  });
  
  $("#meeting_per_add_form #submit-bnt").click(function(){
	  var projectId = $( "#meeting_per_add_form #projectId" ).val();
	  var meetingType = $( "#meeting_per_add_form #meetingType" ).val();
	  if(projectId =="")
	  {
		  layer.msg("请选择项目名称");
		  return;
	  }
	  if(meetingType == "")
	  {
		  layer.msg("请选择会议类型");
		  return;
	  }
	  openMeetingDialog(projectId, meetingType);
    $.locksCreenOpen();
  });
  
  function openMeetingDialog(projectId, meetingType)
  {
	  var url = Constants.sopEndpointURL+"/galaxy/meeting/add";
	  var data = {
			projectId:  projectId,
			type: meetingType
	  };
	  $.getHtml({
			url:url,
			data:data,
			okback:function(){
				//$("#dialog-btns #cancel-btn").click();
			}
		});
  }

</script>