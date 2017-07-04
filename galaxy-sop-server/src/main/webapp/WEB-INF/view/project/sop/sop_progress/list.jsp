<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
	<!-- <p class='popwait'>数据加载中，请稍候...</p> -->
	<div class="myprojecttc new_poptxt">
        <div class="tabtitle">
            <i class="pre_box"></i>
            <h3>接触访谈</h3><span>(当前阶段)</span>
            <i class="next_box" data-progress="1" data-project-progress="${progress }"></i>
        </div>
        <div class="tab_con">
        	<!-- tab nav -->
        	<!-- 没有文档和提示就隐藏 -->
          <ul class="tablink">
          	<li class="tab_1 on">会议访谈</li>
          	<li class="tab_2">文档</li>
          	<span class="icon"><img src="<%=path%>/img/sop_progress/remind__icon.png" title="提示提示提示提示提示提示提示"><font>提示</font></span>
          </ul>
          <div class="tabcon">
          
         <!-- add button -->
			<div id="add_button">
				<a href="<%=path%>/galaxy/progress/p1" id="pop_button" data-type="" data-name="添加访谈记录" class="bluebtn add_list new_poppage">添加访谈记录</a>
			</div>
			<!-- 接触访谈信息 -->
			<div class="min_document clearfix" id="projectProgress_1_table_custom-toolbar" style="display:none; " >
			<div class="bottom searchall clearfix">
				<input type="hidden" id="projectId" name="projectId" value=""> <!-- 项目id -->
				<input type="hidden" id="meetingType" name="meetingType" value="">
			 
			</div>
			</div>
			<!-- table -->
			<table id="projectProgress_1_table" class="commonsize"
							data-url="<%=path%>/galaxy/progress/p1/queryInterview" 
							data-id-field="id" 
							data-toolbar="#projectProgress_1_table_custom-toolbar">
			</table>
			<!-- 文档没有就移除 -->			
			<ul class="file_list clearfix">
            		<!-- 文件上传以及本地选择文件后的状态 -->
                	<li>
                		<input type="file" title="">
                		<div class="file_box">
                			<img src="<%=path%>/img/sop_progress/plus_icon.png" class="add_img" alt="">
                			<div class="cover_box">
                				<span class="cancel">取消</span>
                				<span class="up_load">上传</span>
                				<p>loading…</p>
                			</div>
                		</div>
                		<span>项目立项报告</span>
                	</li>
                	<li>
                		<div class="file_box">
                			<p class="center_text" style="margin-top:-18px;">已认领<br/>(法务)</p>
                		</div>
                		<span>法务尽调文档</span>
                	</li>
                	<li>
                		<div class="file_box">
                			<p class="center_text">待认领</p>
                		</div>
                		<span>法务尽调文档</span>
                	</li>
                	<li>
                		<div class="file_box file_img">
                			<img class="bg_img" src="<%=path%>/img/sop_progress/pdf.png" alt="">
                			<div class="file_btn">
                				<span class="reupload_pdf"></span>
                				<span class="downlond_pdf"></span>
                			</div>
                		</div>
                		<span>财务尽调文档</span>
                	</li>
                	<li>
                		<div class="file_box file_img">
                			<img class="bg_img" src="<%=path%>/img/sop_progress/image.png" alt="">
                			<div class="file_btn">
                				<span class="reupload_jpg"></span>
                				<span class="downlond_jpg"></span>
                			</div>
                		</div>
                		<span>项目立项报告</span>
                	</li>
            </ul>
			<!--The bottom button -->
			<!--  否决项目和启动内部评审  禁用 就是分别添加类名 disabled-->
			<!-- 非此阶段的话就是 popbtm_btn全部隐藏-->
			<div class="popbtm_btn clearfix">
				<a href="javascript:;" class="fffbtn">否决项目</a>
				<a  id="btn1" href="javascript:;" class="bluebtn ">22222</a>
				<a  id="btn2" style="display:none;" href="" class="bluebtn "></a>
				<!-- 会后商务谈判按钮不同  直接添加相应按钮 -->
				<!-- <a href="javascript:;" class="bluebtn">闪投</a> -->
			</div>
			<!--empty data -->
			<!-- 当列表长度为0时隐藏table显示empty_warning -->
			<div class="empty_warning"></div>
			<!-- 未到此阶段时隐藏table和 add button 然后显示not_stage -->
			<div class="not_stage">
				<span class="stage_cover"></span>
				<p>未到此阶段～</p>
			</div>
			
          </div>
        </div>  
		   
	    <div class="operation_warning">
			<img src="<%=path %>/img/sop_progress/operation_empty.png" alt="">	
			<span>本页面暂无操作内容</span>
			<span>请跳转到项目详情页面继续填写～</span>
		</div>
    </div>
<script type="text/javascript" src="<%=path %>/js/sop_progress/sop_progress_list.js"></script>
<script type="text/javascript" src="<%=path %>/js/sop_progress/sop_progress.js"></script>

<script>
$("#projectId").val(projectId);
interviewList();
//PDF预览
$(".file_list .file_img").click(function(){
	var file_str = $(this).find(".bg_img").attr("src");
	window.open("<%=path %>/pdf/web/viewer.html?file="+file_str);
})
</script>

