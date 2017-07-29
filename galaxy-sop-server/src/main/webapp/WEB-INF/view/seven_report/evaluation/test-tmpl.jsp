<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<script id="test_tmpl" type="text/x-jquery-tmpl">

{{each(i,childList) childList}}
	<tr class="evl_module">
		<td rowspan="\${childList.length}">\${name}</td>				
		{{each(i,childList) childList}}
			{{if i==0}}
				<td data-type="\${type}" parentid="\${parentId}" data-title-id="\${titleid}" data-reletid="\${id}">\${name}</td>
				{{if sign!=3&&sign!=1}}
					{{if type==="1"}}
						
					{{else type=="2"}}
						  <!-- 单选（Radio） --> 
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="3"}}
						<!-- 复选 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="4"}}
					{{else type=="5"}}
						<!-- 单选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="6"}}
						<!-- 复选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
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
 					<!-- 单选（Radio）带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="13"}}
 					<!-- 复选带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="14"}}
 					<!-- 单选select -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
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
				{{each(i,informationGrades) informationGrades }}
					<td>\${scoreMax}</td>
					<td>\${scoreExplain}</td>
					{{if isScore==0}}
					<!-- 系统打分 -->
						<td class="">0</td>
					{{else isScore==1}}
					<!-- 人工打分（select） -->
						<td class="">
							<select>
								<option>请选择</option>
							</select>
						</td>
					{{else isScore==2}}
					<!-- 人工打分（数值范围） -->
						<td class="">
							<input type="text" value="" placeholder="请打分">
                            <em>(0-10分)</em>
						</td>
					{{else isScore==4}}
					<!-- 根据其他子项分值计算得出 -->
						<td class="">0</td>
					{{/if}}
				{{/each}}
				
			{{/if}}		
		{{/each}}
		<td rowspan="\${childList.length}">10</td>
	</tr>
	{{each(i,childList) childList}}
		{{if i>0}}
			<tr parentid="\${parentId}">
				<td data-title-id="\${titleid}" data-type="\${type}" data-reletid="\${id}">\${name}</td>
				{{if sign!=3&&sign!=1}}
					{{if type==="1"}}
						
					{{else type=="2"}}
						  <!-- 单选（Radio） --> 
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="3"}}
						<!-- 复选 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="4"}}
					{{else type=="5"}}
						<!-- 单选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="6"}}
						<!-- 复选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
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
 					<!-- 单选（Radio）带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="13"}}
 					<!-- 复选带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
					{{else type=="14"}}
 					<!-- 单选select -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><i>未选择</i></div>
							<span class="editPic" onclick="canEdit(this)" style="display: block;"></span>
						</td>
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
				
				{{each(i,informationGrades) informationGrades }}
					<td>\${scoreMax}</td>
					<td>\${scoreExplain}</td>
				{{/each}}
				<td>0</td>
				{{/if}}	
			</tr>
		{{/if}}		
	{{/each}}


{{/each}}
</script>