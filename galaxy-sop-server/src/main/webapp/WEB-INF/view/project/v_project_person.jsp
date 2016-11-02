<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="addPersontc">
	<div class="title_bj" id="popup_name"></div>
        <div class="addPerson_all">
            <div class="info clearfix">
                <h3>基本信息</h3>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;姓名：</dt>
                    <dd><input type="text" class="txt"/></dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;性别：</dt>
                    <dd class="clearfix">
                        <label><input type="radio" name="sex" checked="checked" />男</label>
                        <label><input type="radio" name="sex" />女</label>
                    </dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;当前职务：</dt>
                    <dd><input type="text" class="txt"/></dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;出生日期：</dt>
                    <dd>
                        <input type="text" class="datetimepickerHour txt time" readonly="" value="" valtype="required" msg="<font color=red>*</font>出生日期不能为空">
                    </dd>
                </dl>
                 <dl class="fmdl fl">
                    <dt>电话号码：</dt>
                    <dd><input type="text" class="txt" placeholder="请输入电话号码"/></dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;是否为联系人：</dt>
                    <dd class="clearfix">
                        <label><input type="radio" checked="checked" />是</label>
                        <label><input type="radio" />否</label>
                    </dd>
                </dl>
                <dl class="fmdl fl block">
                    <dt>备注：</dt>
                    <dd><textarea maxlength="50"></textarea></dd>
                </dl>
            </div>
            <div class="qualifications">
                <h3>学历背景</h3>
                <span onclick="addPersonLearning();" class="blue fr add" data-btn="qualifications" data-name="学历背景">添加</span>
                <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                    <tr>
                        <th>毕业院校</th>
                        <th>专业</th>
                        <th>时间</th>
                        <th>学历</th>
                        <th>操作</th>
                    </tr>
                    <tr>
                        <td>毕业院校</td>
                        <td>专业</td>
                        <td>2016-01-05 - 2016-10-12</td>
                        <td>学历</td>
                        <td><a class="blue" href="javascript:void(0)">删除</a></td>
                    </tr>
                </table>
            </div>
            <div class="qualifications">
                <h3>工作履历</h3>
                <span class="blue fr add">添加</span>
                <table style="width:94%;"  cellspacing="0" cellpadding="0" class="basic_table table">
                    <tr>
                        <th>时间</th>
                        <th>任职公司名称</th>
                        <th>职位</th>
                        <th>操作</th>
                    </tr>
                    <tr>
                        <td>2016-01-05 - 2016-10-12</td>
                        <td>公司名称</td>
                        <td>交互</td>
                        <td><a class="blue" href="javascript:void(0)">删除</a></td>
                    </tr>
                </table>
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