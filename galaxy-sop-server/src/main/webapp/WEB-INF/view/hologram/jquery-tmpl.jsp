<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!--点击编辑例子 -->
<script id="ifelse" type="text/x-jquery-tmpl">
<form id="b_\${code}">
<div class="h_edit section" >
	<div class="h_btnbox">
		<span class="h_save_btn" attr-save="\${code}">保存</span><span class="h_cancel_btn"
			data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}
		
	{{if sign=="3"}}
		<div>\${name}</div>
		{{each(i,childList) childList}}
		<div class="mb_16">
	   <dl class="h_edit_txt clearfix">
		<dt data-type="\${type}"  data-title-id="\${id}" data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
		{{if type=="1"}}
		<dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>

		{{else type=="2"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-title-id="\${titleId}" data-type="\${type}" name="\${titleId}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="3"}}
		<dd class="fl_none">
		<ul class="h_edit_checkbox clearfix" data-type="\${type}">
			{{each(i,valueList) valueList}}
			<li class="check_label" data-value="\${value}" data-title-id="\${titleId}" value="\${id}" data-id="\${id}" data-code="\${code}" data-type="\${type}">\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="4"}}
		{{each(i,valueList) valueList}}
		<dd>
		  <select name="" id="">
			<option value="">\${name}</option>
		  </select>
		</dd>
		{{/each}}

		{{else type=="5"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">500</label>/500
			</p>
		</dd>


		{{else type=="6"}}
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}

		{{else type=="7"}}
			<dd class="fl_none clearfix">
			 <ul class="h_imgs" id="edit-\${id}">

			 </ul>
			 <ul class="h_imgs">
				<li class="h_imgs_add"><input type="file" file-title-id="\${id}" id="selected_file_\${id}"></li>
			</ul>
			</dd>
			<dd class="fl_none red">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>
		{{else type=="8"}}
		              <dd class="fl_none">
							<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}" id="\${id}" onKeyDown='countChar("\${id}","label_\${id}","\${valRuleMark}");' onKeyUp='countChar("\${id}","label_\${id}","\${valRuleMark}");'></textarea>
							<p class="num_tj">
								<label for="" id="label_\${id}">\${valRuleMark}</label>/\${valRuleMark}
							</p>
						</dd>
{{else type=="9"}}
						<dd class="fl_none">
                            <table>
                              <tr>
                                <th></th>
                                 <th colspan="2">\${$data.childList[4].childList[0].name}</th>
								<th>\${$data.childList[4].childList[1].name}</th>
                              </tr>
                               <tr>
                             	 <th>上游</th>
                             	 <td>供应商</td>
								{{each(i,childList) childList}}
                             	 <td>
									<ul class="h_radios clearfix">
										{{each(i,valueList) valueList}}
                                  		<li><input type="radio"/>\${name}</li>
										{{/each}}

                               		 </ul>
								</td>
								{{/each}} 
                           	 </tr>
							<tr>
                             	 <th rowspan="2">下游</th>
                             	 <td>主要渠道</td>
								{{each(i,childList) childList}}
                             	 <td>
									<ul class="h_radios clearfix">
										{{each(i,valueList) valueList}}
                                  		<li><input type="radio"/>\${name}</li>
										{{/each}}

                               		 </ul>
								</td>
								{{/each}} 
                           	 </tr>
							<tr>
                             	 <td>主要客户</td>
								{{each(i,childList) childList}}
                             	 <td>
									<ul class="h_radios clearfix">
										{{each(i,valueList) valueList}}
                                  		<li><input type="radio"/>\${name}</li>
										{{/each}}

                               		 </ul>
								</td>
								{{/each}} 
                           	 </tr>
							

                            </table>
                          </dd>
		{{else type=="10"}}
		<dd class="fl_none">
			< data-title-id="\{id}" class="editable"></table>
			<span class="pubbtn bluebtn margin_btn" onclick="addRow(this)">新增</span>
		</dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>
		
		{{else type=="12"}}
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>


		{{else type=="13"}}
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" placeholder="\${placeholder}"/></dd>
		
		{{else type=="14"}}
		<select data-id="\${id}">
		{{each(i,valueList) valueList}}
		<option data-value="\${value}" data-type="\${type}" data-id="\${id}" data-title-id="\${titleId}" value="\${id}" data-code="\${code}">\${name}</option>
		{{/each}}
		</select>


		{{/if}}
	  </dl>
	</div>

		{{/each}}
	{{else}}
	<div class="mb_16">
	   <dl class="h_edit_txt clearfix">		
		{{if type=="1"}}
		<dt data-type="\${type}">\${name}</dt>
		<dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>

		{{else type=="2"}}
		<dt data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" name="\${titleId}" value="\${id}" data-title-id="\${titleId}" data-type="\${type}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="3"}}
		<dt data-type="\${type}"  data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>
		<dd class="fl_none">
		<ul class="h_edit_checkbox clearfix" data-type="\${type}">
			{{each(i,valueList) valueList}}
			<li class="check_label" data-value="\${value}" data-title-id="\${titleId}" value="\${id}" data-id="\${id}" data-code="\${code}" data-type="\${type}">\${name}</li>
			{{/each}}
		  </ul>
		</dd>

		{{else type=="4"}}
		<dt data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd>
		  <select name="" id="">
			<option value="">\${name}</option>
		  </select>
		</dd>
		{{/each}}

		{{else type=="5"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" data-value="\${value}" data-type="\${type}" placeholder="\${placeholder}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd class="fl_none">
			<textarea class="textarea_h" data-title-id="\${titleId}" data-type="\${type}" data-parentId="\${parentId}" placeholder="\${placeholder}"></textarea>
			<p class="num_tj">
				<label for="">500</label>/500
			</p>
		</dd>

		{{else type=="6"}}
		<dt data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}

		{{else type=="7"}}
		<dt data-type="\${type}">\${name}</dt>
        <dd class="fl_none clearfix">
        <ul class="h_imgs mgedit"  id="edit-\${id}"></ul>
        <ul class="h_imgs" id="edit-\${id}">
        <li class="h_imgs_add"><input type="file" file-title-id="\${id}" id="selected_file_\${id}"></li>
        </ul>
        </dd>
        <dd class="fl_none red">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>

		{{else type=="8"}}
		<dt data-type="\${type}">\${name}</dt>
		<dd class="fl_none">
							<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}" id="\${id}" onKeyDown='countChar("\${id}","label_\${id}","\${valRuleMark}");' onKeyUp='countChar("\${id}","label_\${id}","\${valRuleMark}");'></textarea>
							<p class="num_tj">
								<label for="" id="label_\${id}">\${valRuleMark}</label>/\${valRuleMark}
							</p>
						</dd>
        {{else type=="9"}}
						<dd class="fl_none">
                            <table data-type="\${type}" data-test="\${id}">
                              <tr>
                                <th></th>
                                 <th colspan="2">\${$data.childList[4].childList[0].name}</th>
								<th>\${$data.childList[4].childList[1].name}</th>
                              </tr>
                               <tr>
                             	 <th>上游</th>
                             	 <td>供应商</td>
								{{each(i,childList) childList}}
                             	 <td  data-flag="\${i+1}">
									<ul class="h_radios clearfix">
										{{each(i,valueList) valueList}}
                                  		<li><input type="radio" data-title-id="\${id}" data-row="row1" name="row1_\${titleId}" value="\${id}" data-type="9"/>\${name}</li>
										{{/each}}

                               		 </ul>
								</td>
								{{/each}} 
                           	 </tr>
									<tr>
                             	 <th rowspan="2">下游</th>
                             	 <td>主要渠道</td>
								{{each(i,childList) childList}}
                             	 <td data-flag="\${i+1}">
									<ul class="h_radios clearfix">
										{{each(i,valueList) valueList}}
                                  		<li><input type="radio" data-title-id="\${id}" data-row="row2" name="row2_\${titleId}" value="\${id}" data-type="9"/>\${name}</li>
										{{/each}}

                               		 </ul>
								</td>
								{{/each}} 
                           	 </tr>
							<tr>
                             	 <td>主要客户</td>
								{{each(i,childList) childList}}
                             	 <td data-flag="\${i+1}">
									<ul class="h_radios clearfix">
										{{each(i,valueList) valueList}}
                                  		<li><input type="radio" data-title-id="\${id}" data-row="row3" name='row3_\${titleId}' value="\${id}" data-type="9"/>\${name}</li>
										{{/each}}

                               		 </ul>
								</td>
								{{/each}} 
                           	 </tr>
               
							

                            </table>
                          </dd>
		{{else type=="10"}}
		<dt data-type="\${type}">\${name}</dt>
		<dd class="fl_none">
			<table data-title-id="\${id}"  class="editable">

			</table>
			<span class="pubbtn bluebtn margin_btn" onclick="addRow(this)">新增</span>
		  </dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dt data-type="\${type}">\${name}</dt>
		<dd>
		<ul class="h_radios clearfix">
			{{each(i,valueList) valueList}}
			<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}"/>\${name}</li>
			{{/each}}
		  </ul>
		</dd>
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"/></dd>


		{{else type=="13"}}
		<dt data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd class="check_label" data-value="\${value}" data-id="\${id}" data-code="\${code}">\${name}</dd>
		{{/each}}
		<dd><input type="text" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" placeholder="\${placeholder}"/></dd>
		
		{{else type=="14"}}
		<dt data-type="\${type}">\${name}</dt>
		<select data-id="\${id}">
       <option data-value="" data-type="" data-id="" data-title-id="" value="" data-code="">请选择</option>
		{{each(i,valueList) valueList}}
		<option data-value="\${value}" data-type="\${type}" data-id="\${id}" data-title-id="\${titleId}" value="\${id}" data-code="\${code}">\${name}</option>
		{{/each}}
		</select>


		{{/if}}
	  </dl>
	</div>


	{{/if}}

	{{/each}}
	<div class="h_edit_btnbox clearfix">
	  <span class="pubbtn bluebtn h_save_btn fl" data-on="save" attr-save="\${code}">保存</span>
	  <span class="pubbtn fffbtn fl h_cancel_btn" data-name="basic" data-on="h_cancel" attr-hide="\${code}">取消</span>
	</div>

</div>
</form>
</script>



<!--页面例子 -->
<script id="page_list" type="text/x-jquery-tmpl">
{{each(i,childList) childList}}
<div class="h radius section" id="a_\${code}" data-section-id="\${id}">
  <div class="h_look h_team_look clearfix" id="\${code}">
	<c:if test="${isEditable}">
	<div class="h_btnbox"><span class="h_edit_btn" attr-id="\${code}">编辑</span></div>
	</c:if>
	<div class="h_title">\${name}</div>
	{{each(i,childList) childList}}                    
	{{if sign=="3"}}
	<div>\${name}</div>
		{{each(i,childList) childList}}
			<div class="mb_24 clearfix">
	  <dl class="clearfix">
		<dt data-type="\${type}" data-id="\${id}" data-title-id="\${id}" data-code="\${code}" data-parentId="\${parentId}">\${name}</dt>

		{{if type=="1"}} 
         <dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="5"}}                             
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		<dd>备注</dd>

		{{else type=="2"}}
		<dd class="field" data-value="\${value}" data-title-id="\${id}" data-code="\${code}">未选择</dd>

		{{else type=="3"}}
		{{each(i,valueList) valueList}}
		<dd class="border_dd" data-value="\${value}" data-type="3" value="\${id}" data-title-id="\${id}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="6"}}
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="7"}}
		<dd class="fl_none mglook" id="look-\${id}" ata-value="\${value}" data-id="\${id}" data-code="\${code}">
			</dd>

		{{else type=="8"}}
		<dd class="fl_none field division_dd" data-title-id="\${id}">未填写</dd>
         {{else type=="9"}}
						<dd class="fl_none">
                            <table>
                              <tr>
                                <th></th>
                                <th colspan="2">\${$data.childList[3].childList[4].childList[0].name}</th>
								<th>\${$data.childList[3].childList[4].childList[1].name}</th>
                              </tr>
                               <tr>
                             	 <th>上游</th>
                             	 <td>供应商</td>
                             	 <td data-format='1_1'></td>
                             	 <td data-format='1_2'></td>
                           	 </tr>
							<tr>
                              <th rowspan='2'>下游</th>
                              <td>供应商</td>
                              <td data-format='2_1'></td>
                              <td data-format='2_2'></td>
                            </tr>
                            <tr>
                              <td>供应商</td>
                              <td data-format='3_1'></td>
                              <td data-format='3_2'></td>
                            </tr>

                            </table>
							<span class="pubbtn bluebtn">新增</span>
                          </dd>
		{{else type=="4"}}
		{{each(i,valueList) valueList}}
		<dd>未选择</dd>
		{{/each}}
		
		{{else type=="10"}}
		<dd class="fl_none"><table data-title-id="\${id}"></table></dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="13"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="14"}}
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{/if}}                      
		</dl>		
	</div>
		{{/each}}

	{{else}}
	<div class="mb_24 clearfix">
	  <dl class="clearfix">
		{{if type=="1"}}
		<dt  data-type="\${type}" >\${name}</dt>  
         <dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="5"}}       
		<dt  data-type="\${type}">\${name}</dt>                 
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		<dd>备注</dd>

		{{else type=="2"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-value="\${value}" data-title-id="\${id}" data-code="\${code}">未选择</dd>

		{{else type=="3"}}
		<dt  data-type="\${type}" data-title-id="\${id}" >\${name}</dt>
		{{each(i,valueList) valueList}}
		 <dd class="border_dd"  data-value="\${value}" data-type="3" value="\${id}" data-title-id="\${id}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="6"}}
		<dt  data-type="\${type}" title-id="\${id}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd data-value="\${value}" data-id="\${id}" data-code="\${code}">未选择</dd>
		{{/each}}

		{{else type=="7"}}
		<dt data-type="\${type}">\${name}</dt>
		<dd class="fl_none mglook" id="look-\${id}" ata-value="\${value}" data-id="\${id}" data-code="\${code}">
			</dd>

		{{else type=="8"}}
		<dt data-type="\${type}">\${name}</dt>
		<dd class="fl_none field division_dd" data-title-id="\${id}">未填写</dd>
         {{else type=="9"}}
						<dd class="fl_none">
                            <table>
                              <tr>
                                <th></th>
                                <th colspan="2">\${$data.childList[3].childList[4].childList[0].name}</th>
								<th>\${$data.childList[3].childList[4].childList[1].name}</th>
                              </tr>
                              <tr>
                             	 <th>上游</th>
                             	 <td>供应商</td>
                             	 <td data-format='1_1'></td>
                             	 <td data-format='1_2'></td>
                           	 </tr>
							<tr>
                              <th rowspan='2'>下游</th>
                              <td>主要渠道</td>
                              <td data-format='2_1'></td>
                              <td data-format='2_2'></td>
                            </tr>
                            <tr>
                              <td>主要客户</td>
                              <td data-format='3_1'></td>
                              <td data-format='3_2'></td>
                            </tr>

                            </table>
                          </dd>

		{{else type=="4"}}
		<dt data-type="\${type}">\${name}</dt>
		{{each(i,valueList) valueList}}
		<dd>未选择</dd>
		{{/each}}

		{{else type=="10"}}
		<dt data-type="\${type}">\${name}</dt>
		<dd class="fl_none"><table data-title-id="\${id}"></table></dd>

		{{else type=="11"}}
		<dd>项目带过来的数据</dd>

		{{else type=="12"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="13"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{else type=="14"}}
		<dt  data-type="\${type}">\${name}</dt>
		<dd class="field" data-title-id="\${id}">未填写</dd>
		{{/if}}                      
		</dl>		
	</div>

	{{/if}}
	{{/each}}
  </div>
</div>
{{/each}}
</script>
