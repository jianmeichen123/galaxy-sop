<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<script id="test_tmpl" type="text/x-jquery-tmpl">

{{each(i,childList) childList}}
	<tr>
		<td rowspan="\${childList.length}">\${name}</td>				
		{{each(i,childList) childList}}
			{{if i==0}}
				<td data_titleid="\${titleid}" data_reletid="\${id}">\${name}</td>
				{{if sign!=3}}
					{{if type==="1"}}
						
					{{else type=="2"}}
					{{else type=="3"}}
					{{else type=="4"}}
					{{else type=="5"}}
					{{else type=="6"}}
					{{else type=="7"}}
					{{else type=="8"}}
						<td class="condition" onmouseover="mouserover(this)"  onmouseout="mouseout(this)">
							<div class="align_left">未填写</div>
							<span class="editPic" onclick="canEdit(this)"></span>					
						</td>
					{{else type=="9"}}
					{{else type=="10"}}
					{{else type=="11"}}
					{{else type=="12"}}
					{{else type=="13"}}
					{{else type=="14"}}
					{{else type=="15"}}
					{{else type=="16"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left">该项目是一个通过或基于（技术或模
							式）的（选择三级以下分类) 的（具体
								品类：平台、运营商、服务商、技术
								提供商、解决方案提供商、工具）连
								接（服务一端）和（服务另一端）
							</div>
				
						<span class="editPic" onclick="gapEdit(this)" style="display: none;"></span>
					</td>
				 	{{/if}}
				{{/if}}	
				<td>111</td>
				<td>\${type}</td>
				<td>0</td>
			{{/if}}		
		{{/each}}
		<td rowspan="\${childList.length}">10</td>
	</tr>
	{{each(i,childList) childList}}
		{{if i>0}}
			<tr>
				<td>\${name}</td>
				{{if sign!=3}}
					{{if type==="1"}}
						
					{{else type=="2"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left">初步验证</div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="3"}}
					{{else type=="4"}}
					{{else type=="5"}}
					{{else type=="6"}}
					{{else type=="7"}}
					{{else type=="8"}}
						<td class="condition" onmouseover="mouserover(this)"  onmouseout="mouseout(this)">
							<div class="align_left">未填写</div>
							<span class="editPic" onclick="canEdit(this)"></span>					
						</td>
					{{else type=="9"}}
					{{else type=="10"}}
					{{else type=="11"}}
					{{else type=="12"}}
					{{else type=="13"}}
					{{else type=="14"}}
					{{else type=="15"}}
					{{else type=="16"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left">该项目是一个通过或基于（技术或模
							式）的（选择三级以下分类) 的（具体
								品类：平台、运营商、服务商、技术
								提供商、解决方案提供商、工具）连
								接（服务一端）和（服务另一端）
							</div>
				
						<span class="editPic" onclick="gapEdit(this)" style="display: none;"></span>
					</td>
				 	{{/if}}
				{{/if}}	
				<td>该项分为0分</td>
				<td>0</td>
				<td>0</td>
			</tr>
		{{/if}}		
	{{/each}}


{{/each}}
</script>