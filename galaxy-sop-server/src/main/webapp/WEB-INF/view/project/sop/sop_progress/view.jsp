<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
    <!-- 查看详情 -->
    <div class="myprojecttc new_poptxt myproject_detail">
        <div id="tabtitle" class="tabtitle edit">
            <h3>访谈记录</h3>
        </div>
        <div class="tab_con">
        <!-- time+interviewee-->
            <div class="clearfix ">
                 <dl class="fmdl clearfix intw_time">
                    <dt id="toobar_time">访谈时间：</dt>
                    <dd id="viewDate">
                       
                    </dd>
                </dl>   
                <dl class="fmdl fml clearfix interviewee" id="targetView">
                    <dt>访谈对象：</dt>
                    <dd class="clearfix intw_name" id="viewTarget">
                    </dd>
                </dl>
            </div>
           <!-- Interview summary -->
            <div class="intw_summary">
                <dl class="fmdl clearfix">
                    <dt id="toobar_content">访谈纪要：</dt>
                    <dd>
                        <blockquote id="viewNotes">
                        </blockquote> 
                    </dd>
                </dl>           
            </div>
            <dl class="fmdl clearfix">
                <dt id="toobar_voice">访谈录音：</dt>
                <dd>
                	<p class="audio_name download_name" id="file">访谈录音.mp3</p>
                </dd>
            </dl>  
            <dl class="fmdl clearfix check_detail">
                <dt id="toobar_result">访谈结论：</dt>
                <dd>
                    <div>
                       <span id="interviewResult">否决 &nbsp;</span>
                       <span id="resultReason"></span>
                    </div>
                   
                </dd>
            </dl>  
           
        </div>               
    </div>

<script type="text/javascript">
<!--

//-->
var viewNotes=CKEDITOR.replace('viewNotes',{height:'100px',width:'538px'});
</script>



