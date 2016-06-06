<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>项目详情</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

</head>



        
        <!--右边-->
        <div class="new_right">
        	<b class="sj_ico null">三角</b>
        	<div class="new_right_flow">
            	<div class="new_right_flow_line">
                	<ul class="line_ul_p">
                    	<li class="line_ul_li"><span class="one_dot"></span></li>
                    	<li class="green_dot"><span class="gray_dot"></span>接触访谈</li>
                        <li class="green_dot"><span class="gray_dot"></span>内部评审</li>
                        <li class="green_dot"><span class="gray_dot"></span>CEO评审</li>
                        <li class="green_dot"><span class="green_dot_on"></span><span class="gray_dot"></span>立项会</li>
                        <li><span class="gray_dot"></span>投资意向书</li>
                        <li><span class="gray_dot"></span>尽职调查</li>
                        <li><span class="gray_dot"></span>投决会</li>
                        <li><span class="gray_dot"></span>投资协议</li>
                        <li><span class="gray_dot"></span>股权交割</li>
                    </ul>
                </div>
                 <span class="bluebtn new_btn" >项目流程</span>
            </div>
           
            
            <div class="correlation">相关操作</div>
            <div class="new_correlation_cen">
            	<span class="bluebtn new_btn" >否决项目</span>
            </div>
            
            <div class="correlation">近期会议纪要
				<span class="more null new_righ" id="platform_jxkh_more" style="cursor: pointer;" href="#">more</span>
			</div>
            <div class="new_correlation_cen">
            	<div class="new_b_bottom">
                	<ul><li class="new_b_li_one">投资意向会</li><li  class="new_b_li_two"><span class="color_pass">通过</span></li><li  class="new_b_li_three">2016-1-25</li></ul>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                	
                </div>
                <div class="new_b_bottom">
                	<ul><li class="new_b_li_one">投资意向会</li><li  class="new_b_li_two"><span class="color_undetermined">待定</span></li><li  class="new_b_li_three">2016-1-25</li></ul>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                </div>
                <div class="new_b_bottom">
                	<ul><li class="new_b_li_one">投资意向会</li><li  class="new_b_li_two"><span class="color_veto">否决</span></li><li  class="new_b_li_three">2016-1-25</li></ul>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                </div>
                
                
                
            </div>
            <div class="correlation">近期访谈记录
				<span class="more null new_righ" id="platform_jxkh_more" style="cursor: pointer;" href="#">more</span>
			</div>
            <div class="new_correlation_cen">
            	<div class="new_b_bottom">
                	<div class="div_ul"><span class="new_b_li_one">投资意向会</span><span  class="new_b_li_three pull-right">2016-1-25</span></div>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                </div>
                
                <div class="new_b_bottom">
                	<div class="div_ul"><span class="new_b_li_one">投资意向会</span><span  class="new_b_li_three pull-right">2016-1-25</span></div>
                	<p>会议结论会议结论会议结论会议结论会议结论会议结论会议结论会议结论</p>
                </div>
            </div>
            
        </div>
        
        <!--右边 end-->
   
   
<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="js/axure.js" type="text/javascript"></script>
<script src="js/axure_ext.js" type="text/javascript"></script>
<script>
	$('[data-on="compile"]').on('click',function(){
		$('.bj_hui_on').show();
		$('.compile_on').show();
	})
	$('[data-on="close"]').on('click',function(){
		$('.bj_hui_on').hide();
		$('.compile_on').hide();
	})

</script>
</html>
