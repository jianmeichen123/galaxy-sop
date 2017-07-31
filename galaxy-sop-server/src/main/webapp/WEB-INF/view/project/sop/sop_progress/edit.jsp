<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!--ck富文本编辑器 -->
<%-- <link  href="<%=path %>/ckeditor/samples/css/samples.css" type="text/css" rel="stylesheet">
<link  href="<%=path %>/ckeditor/samples/toolbarconfigurator/lib/codemirror/neo.css" type="text/css" rel="stylesheet"> --%>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/config.js"></script>
<script type="text/javascript" src="<%=path %>/ckeditor/lang/zh-cn.js"></script>
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForHour.js"></script>


    <!-- 添加访谈记录 /编辑访谈记录 -->
    <form class="myprojecttc new_poptxt myproject_add"  type="validate">
        <div id="tabtitle" class="tabtitle edit popup_name">
            <!--编辑状态显示  编辑访谈记录  -->
            <h3 id="popup_name">添加访谈记录</h3>
            <input type="hidden" name="meetingType" id="meetingType"/>
        </div>
        <div class="tab_con">
        <!-- time+interviewee-->
         <!-- 编辑状态下 title改成 编辑访谈记录  移除INPUT  dd填入内容-->
            <div class="clearfix ">
            <input type="hidden" id="recordId">
                <dl class="fmdl clearfix intw_time">
                    <dt id="toobar_time">访谈时间：</dt>
                    <dd>
                         <input type="text" class="datetimepickerHour txt time" readonly id="viewDate" name="viewDate" required data-msg-required="<font color=red>*</font><i></i>必填">
                        <!-- <dd>2017-06-05 12:00</dd> -->
                    </dd>
                </dl>   
                <dl class="fmdl fml clearfix interviewee" id="targetView">
                    <dt id="toobar_notes">访谈对象：</dt>
                    <dd class="clearfix viewTarget">
                        <input type="text" class="txt" id="viewTarget" name="viewTarget" placeholder="访谈对象" class="txt"   value="" required="" data-msg-required="<font color=red>*</font><i></i>必填" maxLength="40" data-rule-viewTarget="true" data-msg-viewTarget="<font color=red>*</font><i></i>访谈对象不能为空"/>
                        <!-- <dd>刘丽君琉璃苣</dd> -->
                    </dd>
                </dl>
            </div>
           <!-- Interview summary -->
            <div class="intw_summary">
                <dl class="fmdl clearfix">
                    <dt id="toobar_content">访谈纪要：</dt>
                    <dd>
                        <textarea id="viewNotes"></textarea> 
                        <span id="viewNotes-error" class="error" for="viewNotes"><font color="red">*</font><i></i>不能超过5000字</span>
                    </dd>
                </dl>           
            </div>
            <dl class="fmdl clearfix">
                <dt id="toobar_voice">访谈录音：</dt>
                <dd>
                
			        <!-- <input type="text" name="fileName" id="file_object" class="txt" readonly="readonly"/>
                    <a href="javascript:;" class="pubbtn fffbtn" id="select_btn" style="position: relative; z-index: 1;">选择文件</a> -->
                    <!-- 添加文件后或者有文件的状态下改为 -->

                	<p id="file_object"></p>
                    <a href="javascript:;" class="pubbtn fffbtn" id="select_btn" style="position: relative; z-index: 1;">选择文件</a>
                </dd>
            </dl>  
            <dl class="fmdl clearfix check_result">
                <dt id="toobar_result">访谈结论：</dt>
                <dd id="resultRadion">
                    
                </dd>
            </dl>  
            <!-- bottom button -->
            <div class="save_button">
            	<button type=button id="save_interview" class="pubbtn bluebtn">保存</button>
                <a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
            </div>
        </div>                
    </form>
<script>

/**
 * 获取会议待定原因下拉项
 * @version 2016-06-21
 */
createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"meetingUndeterminedReason","meetingUndeterminedReason");
/**
 * 获取会议否决原因下拉项
 * @version 2016-06-21
 */
createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"meetingVetoReason","meetingVetoReason");
/**
 * 获取会议跟进中原因下拉项
 * @version 2016-06-21
 */
createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"meetingFollowingReason","meetingFollowingReason");

//ckeditor实例化
var viewNotes=CKEDITOR.replace('viewNotes',{height:'100px',width:'538px'});

var _this={};
_this.id="interviewAdd";
//初始化文件上传
//plupload 上传对象初始化, 绑定保存saveViewFile
var meetingType = $("#meetingType").val();
var url = Constants.sopEndpointURL + "/galaxy/progress/p1/add";
if(meetingType != ""){
	url = Constants.sopEndpointURL + "/galaxy/progress/p2/add";
}
//验证
$(function(){
	$(".myproject_add").validate();
	viewNotes.on( 'change', function() {   //访谈纪要 
		var viewNotesLen=viewNotes.document.getBody().getText().trim().length;
		if(viewNotesLen>5000){
			$("#viewNotes-error").show();
		}else{
			$("#viewNotes-error").hide();
		}
       
    });
	viewNotes.on( 'keyup', function() {    //兼容ie10
		var viewNotesLen=viewNotes.document.getBody().getText().trim().length;
		if(viewNotesLen>5000){
			$("#viewNotes-error").show();
		}else{
			$("#viewNotes-error").hide();
		}
       
    })
})
//访谈对象
jQuery.validator.addMethod("viewTarget", function(value, element) {   
	var viewTarget = /\s*\S+/;
	return this.optional(element) || (viewTarget.test(value));
}, "不能全为空格"); 
//其他原因
jQuery.validator.addMethod("reasonOther", function(value, element) {  
	var reasonOther =/\s*\S+/;
	return this.optional(element) || (reasonOther.test(value));
}, "不能全为空格"); 

initViewUpload();
function initViewUpload() {
	var viewuploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#select_btn")[0], 
		url : url,
		multipart:true,
		multi_selection:false,
		filters : {
			max_file_size : '50mb',
			mime_types: paramsFilter(1)
		},

		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function(up) {
				$("#save_interview").click(function(){
					if($("#viewNotes-error").is(":visible")){  //访谈纪要
						return false;
					}
					var validator = $(".myproject_add").validate();
 					if(!validator.form()){
 						return;
  					}
				   $("#save_interview").addClass("disabled");
				   var res = getInterViewParams('y',projectId, "viewDate","viewNotes");
					if(res == false || res == "false"){
						up.stop();
						$("#save_interview").removeClass("disabled");
						return;
					}
					var radionResult=$("input[name='interviewResult']:checked");
					var inResult =radionResult.val();
				    var resultReason=radionResult.parent().siblings(".resel_box").find("select").val();
				    var resultReasonOther=radionResult.parent().siblings(".reason_box").find("input").val();
					var target="";
					switch (meetingType) {
					   case  "":
							res.stage = "projectProgress:1";
							res.pid = projectId;
							res.createDate = res.viewDateStr;
							res.content = res.viewNotes;
							res.target=$.trim($("#viewTarget").val());
							res.interviewResult = inResult;
							break;
					   default:
							if(meetingType == 'meetingType:1'){
								res.stage = "projectProgress:2";
							}else if(meetingType == 'meetingType:2'){
								res.stage = "projectProgress:3";
							}else if(meetingType == 'meetingType:3'){
								res.stage = "projectProgress:4";
							}else if(meetingType == 'meetingType:4'){
								res.stage = "projectProgress:7";
							}
							res.projectId = projectId;
							res.meetingDateStr = res.viewDateStr;
							res.meetingResult=inResult;
							res.meetingNotes = $.trim(CKEDITOR.instances.viewNotes.getData());
							res.meetingType = meetingType;
							delete res.viewNotes;
					}
					res.resultReason = resultReason;
					res.reasonOther = resultReasonOther;
					//该字段判断是新增还是编辑的保存操作
	                var recordId=$("#recordId").val();
					if(recordId){
						res.recordId=recordId;
					}
					if(up.files.length > 0){
						up.settings.multipart_params = res;  //viewuploader.multipart_params = { id : "12345" };
						viewuploader.start();
					}else{
						sendPostRequestByJsonObj(url,res,function(data){
							var result = data.result.status;
							if(result == "ERROR"){ //OK, ERROR
								$("#save_interview").removeClass("disabled");
								layer.msg(data.result.message);
								return;
							}else{
								layer.msg("保存成功", {time : 500});
								//启用滚动条
								 $(document.body).css({
								   "overflow-x":"auto",
								   "overflow-y":"auto"
								 });
								var _this = $("#projectProgress_1_table");
								if(_this == null || _this.length == 0 || _this == undefined){
									$.popupTwoClose();
								}else{
									$("#projectProgress_1_table").bootstrapTable('refresh');
									$.popupTwoClose();
								}
								//项目阶段按钮状态刷新
								refreshButton();
							}
						});
					} 
					return false;
				});
			},
			
			FilesAdded: function(up, files) {
				if(viewuploader.files.length >= 2){
					viewuploader.splice(0, viewuploader.files.length-1)
				}
				plupload.each(files, function(file) {
					var size=up.settings.filters.max_file_size.replace("mb","");   
					if(parseInt(file.size) > parseInt(size) * 1024 * 1024){
						layer.msg("最大支持"+size+"MB");
						return;
					}
					$("#file_object").removeClass("no_bg");
					$("#file_object").text(file.name);
					$("#select_btn").next().find("input").hide();
					$("#select_btn").text("更新");
					$("#file_object").addClass("audio_name");
				});
			},
			
			UploadProgress: function(up, file) { 
			},
			
			FileUploaded: function(up, files, rtn) {  //上传回调
				$("#powindow").hideLoading();
				var response = $.parseJSON(rtn.response);
				var rs = response.result.status;
				if(rs == "ERROR"){ //OK, ERROR
					$("#save_interview").removeClass("disabled");
					$("#file_object").text("");
					$("#select_btn").text("选择文件");
					$("#file_object").removeClass("audio_name")
					viewuploader.splice(0, viewuploader.files.length)
					layer.msg(response.result.message);
					return false;
				}else{
					layer.msg("保存成功", {time : 500});
					/* toFormatNearNotes(); */
					var _this = $("#projectProgress_1_table");
					if(_this == null || _this.length == 0 || _this == undefined){
						$.popupTwoClose();
					}else{
						$("#projectProgress_1_table").bootstrapTable('refresh');
						$.popupTwoClose();
					}
					//项目阶段按钮状态刷新
					refreshButton();
				}
			},
			
			BeforeUpload:function(up){
				$("#powindow").showLoading(
						 {
						    'addClass': 'loading-indicator'						
						 });
			},
			
			Error: function(up, err) {
				$("#powindow").hideLoading();
				$("#save_interview").removeClass("disabled");
				$("#file_object").text("");
				$("#select_btn").text("选择文件");
				$("#file_object").removeClass("audio_name")
				layer.msg(err.message);
			}
		}
	});

	viewuploader.init();
}
$("#viewDate").change(function(){
	if($(this).val()!=""){$(this).next().hide()}
})
</script>

