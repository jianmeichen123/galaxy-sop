<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
    <!-- 查看详情 -->
    <div class="myprojecttc new_poptxt myproject_detail">
        <div class="tabtitle edit">
            <h3>访谈记录</h3>
        </div>
        <div class="tab_con">
        <!-- time+interviewee-->
            <div class="clearfix ">
                 <dl class="fmdl clearfix intw_time">
                    <dt>访谈时间：</dt>
                    <dd>
                        2017-09-22 10:28
                    </dd>
                </dl>   
                <dl class="fmdl fml clearfix interviewee">
                    <dt>访谈对象：</dt>
                    <dd class="clearfix intw_name">
                       达康书记、老张
                    </dd>
                </dl>
            </div>
           <!-- Interview summary -->
            <div class="intw_summary">
                <dl class="fmdl clearfix">
                    <dt>访谈纪要：</dt>
                    <dd>
                        <blockquote>
                        	Harvey说他跟着乐队巡演过几百次，这次是最特别的一次。“我们在沙漠里！我可以感受到沙子。”说着，他光着脚扬起了脚边的沙子。他说他在成都生活，这是第一次来到怀来这个中国北方城市。“你看周围还有起伏的山脉。”他感觉这里非常棒，舞台体验也非常棒。“他是乐队的VJ（Visual Jockey），负责播放和制作Visual，“18:30有我们的演出，一会儿过来听音乐吧！”
                        </blockquote> 
                    </dd>
                </dl>           
            </div>
            <dl class="fmdl clearfix">
                <dt>访谈录音：</dt>
                <dd>
                	<p class="audio_name download_name">访谈录音.mp3</p>
                </dd>
            </dl>  
            <dl class="fmdl clearfix check_detail">
                <dt>访谈结论：</dt>
                <dd>
                    <div>
                       <span>否决 &nbsp;</span>
                       <span>原因：投资方式未达成一致（如并购、财务投资，服务费等）</span>
                    </div>
                   
                </dd>
            </dl>  
           
        </div>               
    </div>





