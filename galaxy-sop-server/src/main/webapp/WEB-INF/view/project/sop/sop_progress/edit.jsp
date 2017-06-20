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
    <div class="myprojecttc new_poptxt myproject_add">
        <div class="tabtitle edit">
            <!--编辑状态显示  编辑访谈记录  -->
            <h3>添加访谈记录</h3>
        </div>
        <div class="tab_con">
        <!-- time+interviewee-->
         <!-- 编辑状态下 title改成 编辑访谈记录  移除INPUT  dd填入内容-->
            <div class="clearfix ">
                <dl class="fmdl clearfix intw_time">
                    <dt>访谈时间：</dt>
                    <dd>
                         <input type="text" class="datetimepickerHour txt time" id="viewDate" name="viewDate"  value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/>
                        <!-- <dd>2017-06-05 12:00</dd> -->
                    </dd>
                </dl>   
                <dl class="fmdl fml clearfix interviewee">
                    <dt>访谈对象：</dt>
                    <dd class="clearfix">
                        <input type="text" class="txt" id="viewTarget" name="viewTarget" regString="^.{1,50}$" placeholder="访谈对象" class="txt" valType="OTHER" msg="<font color=red>*</font>访谈对象不能为空且不能超过50字符"/>
                        <!-- <dd>刘丽君琉璃苣</dd> -->
                    </dd>
                </dl>
            </div>
           <!-- Interview summary -->
            <div class="intw_summary">
                <dl class="fmdl clearfix">
                    <dt>访谈纪要：</dt>
                    <dd>
                        <textarea id="viewNotes"></textarea> 
                    </dd>
                </dl>           
            </div>
            <dl class="fmdl clearfix">
                <dt>访谈录音：</dt>
                <dd>
                
			        <input type="text" name="fileName" id="file_object" class="txt" readonly="readonly"/>
                    <a href="javascript:;" class="pubbtn fffbtn" id="select_btn" style="position: relative; z-index: 1;">选择文件</a>
                    <!-- 添加文件后或者有文件的状态下改为 -->

                	<p class="audio_name">访谈录音名字.mp3</p>
                    <a href="javascript:;" class="pubbtn fffbtn" id="select_btn" style="position: relative; z-index: 1;">更新</a>
                </dd>
            </dl>  
            <dl class="fmdl clearfix check_result">
                <dt>访谈结论：</dt>
                <dd>
                    <div>
                        <label><input name="interviewResult" type="radio" value="" />待定</label> 
                        <select name="resultReason" id="">
                            <option value="">请选择原因</option>
                        </select>
                        <input type="text" name="reasonOther" class="txt" placeholder="请填写其它原因">
                    </div>
                    <div>
                        <label><input name="interviewResult" type="radio" value="" />否决</label> 
                        <select name="resultReason" id="">
                            <option value="">请选择原因</option>
                        </select>
                        <input type="text" name="reasonOther" class="txt" placeholder="请填写其它原因">
                    </div>
                    <div>
                        <label><input name="interviewResult" type="radio" value="" />通过</label> 
                    </div>
                </dd>
            </dl>  
            <!-- bottom button -->
            <div class="save_button">
                <a href="javascript:;" id="save_interview" class="pubbtn bluebtn">保存</a>
                <a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
            </div>
        </div>                
    </div>
<script>
//ckeditor实例化
var viewNotes=CKEDITOR.replace('viewNotes',{height:'100px',width:'538px'});

var _this={};
_this.id="interviewAdd";
//初始化文件上传
//plupload 上传对象初始化, 绑定保存saveViewFile
initViewUpload();
function initViewUpload() {
	var viewuploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#select_btn")[0], 
		url : Constants.sopEndpointURL + "/galaxy/progress/p1/add",
		multipart:true,
		multi_selection:false,
		filters : {
			max_file_size : '25mb',
			mime_types: paramsFilter(1)
		},

		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function(up) {
				$("#save_interview").click(function(){
					$("#save_interview").addClass("disabled");
					var res = getInterViewParams('y',projectId, "viewDate", "viewTarget", "viewNotes");
					if(res == false || res == "false"){
						up.stop();
						$("#save_interview").removeClass("disabled");
						return;
					}
					var inResult = $("input[name='interviewResult']:checked").val();
					var resultReason=$("#resultReason").find("option:selected").text();
					
					res.stage = "projectProgress:1";
					res.pid = projectId;
					res.createDate = res.viewDateStr;
					res.content = res.viewNotes;
					res.target = res.viewTarget;
					res.interviewResult = inResult;
					res.reasonOther = $("#reasonOther").val();
					res.resultReason = resultReason;
					res.otherReason = $("input[name='reasonOther']").val();
					
					if(up.files.length > 0){
						up.settings.multipart_params = res;  //viewuploader.multipart_params = { id : "12345" };
						viewuploader.start();
					}else{
						sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/progress/p1/add",res,function(data){
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
								/* toFormatNearNotes(); */
								var _this = $("#projectProgress_1_table");
								if(_this == null || _this.length == 0 || _this == undefined){
									$.popupTwoClose();
								}else{
									$("#projectProgress_1_table").bootstrapTable('refresh');
									$.popupTwoClose();
								}
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
					$("#file_object").val(file.name);
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
					$("#file_object").val("");
					viewuploader.splice(0, meetuploader.files.length)
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
				$("#file_object").val("");
				layer.msg(err.message);
			}
		}
	});

	viewuploader.init();
}
</script>

