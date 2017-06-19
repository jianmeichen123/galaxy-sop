<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!--ck富文本编辑器 -->
<link  href="<%=path %>/ckeditor/samples/css/samples.css" type="text/css" rel="stylesheet">
<link  href="<%=path %>/ckeditor/samples/toolbarconfigurator/lib/codemirror/neo.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/config.js"></script>
<script type="text/javascript" src="<%=path %>/ckeditor/lang/zh-cn.js"></script>
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
                         <input type="text" class="datetimepickerHour txt time" id="viewDate" name="viewDate" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/>
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
//初始化文件上传
toinitUpload(Constants.sopEndpointURL + "/galaxy/progress/p1/add", projectId,"select_btn","file_object","save_interview","",
		function getSaveCondition(){
			var	condition = {};
			var pid = projectId;
			var viewDateStr = $("#viewDate").val();
			var viewTarget = $.trim($("#viewTarget").val());
			var viewNotes = $("#viewNotes").val();
			var interviewResult = $('input:radio[name="interviewResult"]:checked').val();
			if(pid == null || pid == ""){
				alert(1);
				return;
			}
			if(viewDateStr == null ||  viewDateStr == ""){
				return false;
			}
			if(viewTarget == null ||  viewTarget == ""){
			$("#viewTarget").focus();
				return false;
			}
			condition.pid = pid;
			condition.stage = "projectProgress:1";
			condition.createDate = viewDateStr;
			condition.target = viewTarget;
			condition.content = viewNotes;
			
			return condition;
		},null,null,null);
</script>

