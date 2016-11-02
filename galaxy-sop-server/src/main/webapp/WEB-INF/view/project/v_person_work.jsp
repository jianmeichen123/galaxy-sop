<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="qualificationstc">
	<div class="title_bj" id="qualifications_popup_name"></div>
        <div class="qualifications_all">
            <div class="info clearfix">
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;时间：</dt>
                    <dd><input type="text" class="datetimepickerHour txt time fl"/><span class="fl">&nbsp;至&nbsp;</span><input type="text" class="datetimepickerHour txt time fl"/></dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;毕业院校：</dt>
                    <dd class="clearfix">
                        <input type="text" class="txt"/>
                    </dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;专业：</dt>
                    <dd><input type="text" class="txt"/></dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;学历：</dt>
                    <dd>
                       <select>
                           <option value=""></option>
                       </select>
                    </dd>
                </dl>
            </div>
        </div>
    <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="save_file" >确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>
<script>

</script>