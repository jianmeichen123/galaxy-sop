<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<div class="qualificationstc" id="compete">
	<div class="title_bj" id="complete_title"></div>
	
    <div class="qualifications_all">
    
    	<form action="" id="detail-form">
			
    <input name="index" type="hidden" value="">
    	<input name="id" type="hidden">
    	<input name="titleId" type="hidden">
    	
        <div class="info clearfix"> 
            <dl class="fmdl fl">
                <dt>竞争对手名称：</dt>
                <dd class="clearfix">
                    <input name="field1" type="text" class="txt" maxLength="200"/>
                </dd>
            </dl>
            <dl class="fmdl fl">
                <dt>胜算度：</dt>
                <dd class="clearfix">
                    <input name="field2" type="text" class="txt" maxLength="50" allowNULL="yes" valType="OTHER" regString="^([0-9](\.\d{0,1})|\d{0,1}|10|10.0|0)$" msg="<font color=red>*</font>0到10之间的一位小数"/>
                </dd>
            </dl>
            <dl class="fmdl fl">
                <dt>威胁度：</dt>
                <dd class="clearfix">
                    <input name="field3" type="text" class="txt" maxLength="50" allowNULL="yes" valType="OTHER" regString="^([0-9](\.\d{0,1})|\d{0,1}|10|10.0|0)$" msg="<font color=red>*</font>0到10之间的一位小数"/>
                </dd>
            </dl>
            <dl class="fmdl fl">
                <dt>应对竞争的最有效措施：</dt>
                <dd>
                   <textarea class="team_textarea" name="field4" id="com_save_field4" oninput='countChar("com_save_field4","label_com_save_field4","200")'></textarea>
                   <div class="font_num_m num_tj"><span for="" id="label_com_save_field4">200</span>/200</div>
                </dd>
            </dl>
            <dl class="fmdl fl">
                <dt>措施的有效性是否验证：</dt>
                <dd class="clearfix">
                	<label><input type="radio" name="field5" value="是" />是</label>
                	<label><input type="radio" name="field5" value="否" />否</label>
                </dd>
            </dl>
                        
        </div>
        
        </form>
    </div>
    
    <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="save-detail-btn">确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<script>
     $(function(){
        $("#detail-form").validate({});
        $.validator.setDefaults({
        	errorElement:'span'
        });
    })
    
</script>

