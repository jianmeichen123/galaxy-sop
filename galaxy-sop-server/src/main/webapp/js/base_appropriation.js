
$(function(){	
	assembleHtml();
	assembleSingleTabHtml();
	assembleHtml();
});

function  assembleHtml(){
	var html=
		'<div class="agreement">'
	     +'<div class="b_agreement clearfix">'
		  +'<div class="b_agreement_l fl">'
		     +'<h3>创业服务协议</h3>'
	         +'<dl><dt>计划总拨款金额：</dt><dd>20,000,000</dd></dl>'
             +'<dl><dt>编辑人：</dt><dd>徐文秀</dd></dl>'    
             +'<dl><dt>编辑日期：</dt><dd>2016-07-18</dd></dl>'
          +'</div>'    
         +'<div class="b_agreement_r fr">'
            +'<button class="pbtn bluebtn" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name="添加分期拨款计划">添加分期拨款计划</button>'
            +'<label class="blue" href="/sop/html/actual_all.html" data-btn="actual_all" data-name="编辑总拨款计划">编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name="提示">删除</label>'
         +'</div>'
    +'</div>'                      
  //  <!--表格内容-->
   +'<table width="100%" cellspacing="0" cellpadding="0" class="commonsize delivery">'
     + '<thead>'
          +'<tr>'
             +'<th>分拨</th>'
              +'<th>拨款时间</th>'
              +'<th>计划拨款金额（元）</th>'
               +'<th>实际拨款金额（元）</th>'
              +'<th>附件数</th>'
              +'<th>操作</th>'
             +'</tr>'
      +'</thead>' 
      +'<tbody id="tabApprSingleList"></tbody>'
   +'</table>'
 +'</div>';
	 $("#tabApprAllList").append(html);
} 

function  assembleSingleTabHtml(){
	 var value='<tr>'	 
		   +'<td><a class="blue" href="/sop/html/actual.html" data-btn="actual" data-name="实际拨款信息列表">分拨1 </a></td>'
		   +'<td>完成条款4-15个工作日内</td>'
		   +'<td>6,000,000</td>'
		   +'<td>6,000,000</td>'
		   +'<td>6</td>'                                 
		   +'<td><label class="blue" href="/sop/html/actual_aging.html" data-btn="actual_aging" data-name="编辑分期拨款计划">编辑</label><label class="blue" href="/sop/html/1tips.html" data-btn="tips" data-name="提示">删除</label><label class="blue noMargin">下载附件</label></td>' 
		   +'</tr>';
	  $("#tabApprSingleList").append(value);
}


