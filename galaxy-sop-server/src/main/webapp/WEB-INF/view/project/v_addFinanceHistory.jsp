<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="historytc">
	<div class="title_bj" id="popup_name"></div>
	
    <div class="form clearfix">
        <div class="conference_all">
            <dl class="fmdl clearfix">
                <dt>融资时间：</dt>
                <dd>
                    <input type="text" class="datetimepickerHour txt time" readonly="" value="" valtype="required" msg="<font color=red>*</font>创建时间不能为空">
                </dd>
            </dl>
            
            <dl class="fmdl clearfix">
                 <dt>投资方(机构或个人)：</dt>
                <dd>
                    <input type="text" class="txt"/>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                 <dt>投资金额：</dt>
                <dd>
                    <input type="text" class="txt fl" />&nbsp;<span>万元</span>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                 <dt>币种：</dt>
                <dd>
                    <select>
                        <option value="">人民币</option>
                    </select>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                 <dt>占比：</dt>
                <dd>
                    <input type="text" class="txt"/>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                 <dt>融资轮次：</dt>
                <dd>
                    <select>
                        <option value="">请选择</option>
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