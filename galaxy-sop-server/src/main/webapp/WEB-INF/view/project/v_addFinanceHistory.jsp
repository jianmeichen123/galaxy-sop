<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/financeDateForHour.js"></script>
<div class="historytc">
	<div class="title_bj" id="popup_name"></div>
	<form id="add_Historyform" method="post">
    <div class="form clearfix">
        <div class="conference_all">
            <dl class="fmdl clearfix">
                <dt>融资时间：</dt>
                <dd>
                    <input type="text" class="datetimepickerFinance txt time" readonly="" name="financeDate" value="" valtype="required" msg="<font color=red>*</font>创建时间不能为空">
                </dd>
            </dl>
            
            <dl class="fmdl clearfix">
                 <dt>投资方(机构或个人)：</dt>
                <dd>
                    <input type="text" class="txt" name="financeFrom"/>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                 <dt>投资金额：</dt>
                <dd>
                    <input type="text" class="txt fl" name="financeAmount"/>&nbsp;<span>万元</span>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                 <dt>币种：</dt>
                <dd>
                    <select name="financeUnit">
                        <option value="0" select="selected" name="financeUnit">人民币</option>
                        <option value="1" name="financeUnit">美元</option>
                        <option value="2" name="financeUnit">英镑</option>
                        <option value="3" name="financeUnit">欧元</option>
                    </select>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                 <dt>占比：</dt>
                <dd>
                    <input type="text" class="txt" name="financeProportion"/>
                </dd>
            </dl>
            <dl class="fmdl clearfix">
                 <dt>融资轮次：</dt>
                <dd>
					<select name="financeStatus" class='new_nputr'>
			         </select>
                </dd>
            </dl>
        </div>
    </div>
      	</form>
    
    <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="save_file"  onclick="addFinanceHistory()">确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>

</div>
<script>

/**
 * 获取融资状态下拉项
 * @version 2016-06-21
 */
createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+"financeStatus","financeStatus", 17);
</script>
