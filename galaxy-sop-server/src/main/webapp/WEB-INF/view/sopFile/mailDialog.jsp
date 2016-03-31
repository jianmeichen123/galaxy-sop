<%@ page language="java" pageEncoding="UTF-8"%>
<div id="mail-dialog" style="display:none;">
<form id="mail-form" >
<div class="emailtc" >
    <h2>档案管理-邮件分享</h2>
    <dl class="fmdl clearfix">
        <dt>收件人：</dt>
        <dd class="clearfix">
            <input type="text" name="toAddress" class="txt" data-rule-required="true"/>
        </dd>
        <dd>            
            <label class="red">&#42;必填</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
        <dt>邮件分类：</dt>
        <dd class="clearfix">
            <table width="100%" cellspacing="0" cellpadding="0" id="attach-table">
              <thead>
                  <tr>
                      <th>序号</th>
                      <th>档案名称</th>
                     <!--   <th>档案大小 </th>-->
                  </tr>
              </thead>                                                                                                                     
              <tbody>
              </tbody>
          </table> 

        </dd>
    </dl>
    
    <div class="btnbox">
    	<a href="javascript:;" class="pubbtn bluebtn" id="send-mail-btn">发送</a>
    </div>
</div>
</form>
</div>