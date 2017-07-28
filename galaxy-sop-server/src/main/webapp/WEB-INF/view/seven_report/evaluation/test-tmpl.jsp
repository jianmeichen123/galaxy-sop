<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<script id="test_tmpl" type="text/x-jquery-tmpl">

{{each(i,childList) childList}}
	<tr>
		<td rowspan="\${childList.length}">\${name}</td>				
		{{each(i,childList) childList}}
			{{if i=="0"}}
				<td >\${name}</td>
				 	{{if type=="1"}} 
					<td class="condition" onmouseover="mouserover(this)"  onmouseout="mouseout(this)">
						<div class="align_left">哈哈哈98</div>
						<span class="editPic" onclick="canEdit(this)"></span>					
					</td>
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
				<td>11111111111111111111111111</td>
				<td>0</td>
				<td>该项分为0分</td>
				<td>0</td>
				<td>0</td>
			</tr>
		{{/if}}		
	{{/each}}


{{/each}}
</script>