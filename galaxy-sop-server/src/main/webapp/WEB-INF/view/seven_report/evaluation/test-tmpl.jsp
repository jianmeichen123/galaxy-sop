<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<script id="test_tmpl" type="text/x-jquery-tmpl">

{{each(i,childList) childList}}
	<tr id="\${relateCode}" class="evl_module">
		<td rowspan="\${childList.length}">\${name}</td>				
		{{each(i,childList) childList}}
			{{if i==0}}
				<td data-type="\${type}" parentid="\${parentId}" data-title-id="\${titleid}" data-reletid="\${id}">\${name}</td>
				{{if sign!=3&&sign!=1}}
					{{if type==="1"}}
						
					{{else type=="2"}}
						  <!-- 单选（Radio） --> 
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside"  onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="3"}}
						<!-- 复选 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="4"}}
					{{else type=="5"}}
						<!-- 单选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="6"}}
						<!-- 复选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="7"}}
					{{else type=="8"}}
						<td class="condition" onmouseover="mouserover(this)"  onmouseout="mouseout(this)">
							<span class="editPic" e-type="small_pop" onclick="typeEdit(this)"></span>		
							<div class="align_left">未填写</div>										
						</td>
					{{else type=="9"}}
					{{else type=="10"}}
					{{else type=="11"}}
					{{else type=="12"}}
 					<!-- 单选（Radio）带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="13"}}
 					<!-- 复选带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="14"}}
 					<!-- 单选select -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="15"}}
					{{else type=="16"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="  content_16 align_left">\${content}</div>
							<span class="editPic" e-type="" onclick="gapEdit(this)"></span>
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
                            <em>(0-\${scoreMax}分)</em>
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
							<span class="editPic" e-type="inside"  onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="3"}}
						<!-- 复选 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="4"}}
					{{else type=="5"}}
						<!-- 单选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="6"}}
						<!-- 复选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="7"}}
					{{else type=="8"}}
						<td class="condition" onmouseover="mouserover(this)"  onmouseout="mouseout(this)">
							<span class="editPic" e-type="small_pop" onclick="typeEdit(this)"></span>		
							<div class="align_left">未填写</div>										
						</td>
					{{else type=="9"}}
					{{else type=="10"}}
					{{else type=="11"}}
					{{else type=="12"}}
 					<!-- 单选（Radio）带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="13"}}
 					<!-- 复选带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="14"}}
 					<!-- 单选select -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)"  ></span>
							<div class="align_left"><p>未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="15"}}
					{{else type=="16"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="  content_16 align_left">\${content}</div>
							<span class="editPic" e-type="" onclick="gapEdit(this)"></span>
					</td>
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
                              <em>(0-\${scoreMax}分)</em>
						</td>
					{{else isScore==4}}
					<!-- 根据其他子项分值计算得出 -->
						<td class="">0</td>
					{{/if}}
				{{/each}}
				{{/if}}	
			</tr>
		{{/if}}		
	{{/each}}


{{/each}}
</script>





<!-- 编辑悬浮弹窗模板 -->
<script id="edit_tmpl1" type="text/x-jquery-tmpl">

</script>






<!-- 编辑遮罩弹窗模板 -->
<script id="edit_tmpl2" type="text/x-jquery-tmpl">

</script>








