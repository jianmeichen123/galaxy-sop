<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<script id="test_tmpl" type="text/x-jquery-tmpl">

{{each(i,childList) childList}}
	<tr id="a_\${relateCode}" class="evl_module">
		<td rowspan="\${childList.length}">\${name}<span class="title-weight" data-relate-id="\${id}"></span></td>				
		{{each(i,childList) childList}}
			{{if i==0}}
				<td data-type="\${type}" parentid="\${parentId}" data-title-id="\${titleId}" data-reletid="\${id}">\${name}</td>
				{{if sign!=3&&sign!=1}}
					{{if type=="1"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside"  onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未填写</p></div>	
							<div class="radioShow radioShow_1"></div>						
							<div class="Button">
								<em onclick="right(this,'input')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="2"}}
						  <!-- 单选（Radio） --> 
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside"  onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>
					{{else type=="3"}}
						<!-- 复选 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'checkbox')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>
					{{else type=="4"}}
						<!-- 级联选择  没有 -->	
					{{else type=="5"}}
						<!-- 单选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>
					{{else type=="6"}}
						<!-- 复选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'textarea')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="7"}}
						<!-- 附件 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							
							<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
						</td>
					{{else type=="8"}}
						<!-- 文本域 -->
						<td class="condition" onmouseover="mouserover(this)"  onmouseout="mouseout(this)">
							<span class="editPic" e-type="small_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>		
							<div class="align_left"><p class="title-value type_8" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未填写</p><em class="packup">收起</em><em class="detail">详情</em></div>										
						</td>
					{{else type=="9"}}
						<!-- 固定表格 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"></p></div>	
							<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
						</td>
					{{else type=="10"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"></p></div>	
							<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
						</td>
					{{else type=="11"}}
						<!-- 静态数据 -->
						 <td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"></p></div>	
						</td>
					{{else type=="12"}}
 					<!-- 单选（Radio）带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>
					{{else type=="13"}}
 					<!-- 复选带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
			                <form>
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'checkbox')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
					        </form>
						</td>
					{{else type=="14"}}
 					<!-- 单选select -->
						<td class="condition condition_select" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'select')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="16"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left content_16"><p class="title-value" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未填写</p></div>
							<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>
						</td>
					{{else type=="18"}}
						 <td class="condition div_select" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							
							<div class="Button">
								<em onclick="selectMethod(this,'select')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
				 	{{/if}}
				{{else sign==3}}
					<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
					<div class="align_left">						
						{{each(i,childList) childList}}
						{{if type=="1" || type=="20"|| type=="10"}}
						<div class="sign_3" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"><p class="title-value income_structor_content" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">\${name}：<span>未填写</span></p></div>
						{{else type=="8"}}
						<div class="sign_3" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"><p class="title-value type_8 income_structor_content" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">\${name}：<span>未填写</span><em class="packup">收起</em><em class="detail">详情</em></p> </div>
						{{else type=="2" || type=="12" || type=="14" || type=="13"}}
						<div class="sign_3" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"><p class="title-value income_structor_content" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">\${name}：<span>未选择</span></p></div>
						{{else type=="7"}}
						<div class="sign_3" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"><p class="title-value income_structor_content" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">\${name}：<span>未添加</span></p></div>
						{{/if}}
					{{/each}}
					</div>
						<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>
					</td>
				{{else sign==1}}
					<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
						<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">sign==1</p></div>
						<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>
					</td>
				{{/if}}	
				{{each(i,informationGrades) informationGrades }}
					<td>\${scoreMax}</td>
					<td>\${scoreExplain}</td>
					{{if isScore==0}}
					<!-- 系统打分 -->
						<td class="score-column" data-relate-id="\${titleRelateId}">0</td>
					{{else isScore==1}}
					<!-- 人工打分（select） -->
						<td class="score-column" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}">
							<select>
								<option>请选择</option>
							</select>
						</td>
					{{else isScore==2}}
					<!-- 人工打分（数值范围） -->
						<td class="score-column score-columns" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}">
							<input type="text" value="" placeholder="请打分" name="\${isScore}" rulermarket="\${scoreMax}">
                            <em>(0-\${scoreMax}分)</em>
						</td>
					{{else isScore==4}}
					<!-- 根据其他子项分值计算得出 -->
						<td class="score-column" data-relate-id="\${titleRelateId}">0</td>
					{{/if}}
				{{/each}}
			{{/if}}		
		{{/each}}
		<td rowspan="\${childList.length}" class="score-column" data-relate-id="\${id}">10</td>
	</tr>
	{{each(i,childList) childList}}
		{{if i>0}}
			<tr parentid="\${parentId}">
				<td data-title-id="\${titleId}" data-type="\${type}" data-reletid="\${id}">\${name}</td>		
				{{if sign!=3&&sign!=1}}
					{{if type=="1"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside"  onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未填写</p></div>
							<div class="radioShow radioShow_1"></div>							
							<div class="Button">
								<em onclick="right(this,'input')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="2"}}
						  <!-- 单选（Radio） --> 
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside"  onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>
					{{else type=="3"}}
						<!-- 复选 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'checkbox')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>
					{{else type=="4"}}
						<!-- 级联选择  没有 -->	
					{{else type=="5"}}
						<!-- 单选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'textarea')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="6"}}
						<!-- 复选带备注textarea -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>							
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'textarea')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="7"}}
						<!-- 附件 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							
							<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
						</td>
					{{else type=="8"}}
						<!-- 文本域 -->
						<td class="condition" onmouseover="mouserover(this)"  onmouseout="mouseout(this)">
							<span class="editPic" e-type="small_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>		
							<div class="align_left"><p class="title-value type_8" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未填写</p><em class="packup">收起</em><em class="detail">详情</em></div>										
						</td>
					{{else type=="9"}}
						<!-- 固定表格 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"></p></div>	
							<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
						</td>
					{{else type=="10"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"></p></div>	
							<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
						</td>
					{{else type=="11"}}
						<!-- 静态数据 -->
						 <td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"></p></div>	
						</td>
					{{else type=="12"}}
 					<!-- 单选（Radio）带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'radio')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>
					{{else type=="13"}}
 					<!-- 复选带备注input -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'checkbox')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>
					{{else type=="14"}}
 					<!-- 单选select -->
						<td class="condition condition_select" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'select')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
						</td>
					{{else type=="15"}}
						<td colspan="4"  class="condition td_15">
							<div>
								<table class="table_15">
										{{each(i,informationGrades) informationGrades }}
									<tr>
										<td  onmouseover="mouserover(this)"  onmouseout="mouseout(this)">
											<div class="align_left"><p class="title-value type_8" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}" data-sub-id="\${subId}">未填写</p><em class="packup">收起</em><em class="detail">详情</em></div>
											<span class="editPic" e-type="small_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
											<div class="radioShow"></div>
										</td>
											
												<td>\${scoreMax}</td>
												<td>\${scoreExplain}</td>
												{{if isScore==0}}
												<!-- 系统打分 -->
													<td class="score-column" data-relate-id="\${titleRelateId}" data-sub-id="\${subId}">0</td>
												{{else isScore==1}}
												<!-- 人工打分（select） -->
													<td class="score-column" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}" data-sub-id="\${subId}">
														<select>
															<option>请选择</option>
														</select>
													</td>
												{{else isScore==2}}
												<!-- 人工打分（数值范围） -->
													<td class="score-column score-columns" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}" data-sub-id="\${subId}">
														
														<input type="text" value="" placeholder="请打分"  name="\${isScore}" rulermarket="\${scoreMax}">
							                            <em>(0-\${scoreMax}分)</em>
													</td>
												{{else isScore==4}}
												<!-- 根据其他子项分值计算得出 -->
													<td class="score-column" data-relate-id="\${titleRelateId}" data-sub-id="\${subId}">0</td>
												{{/if}}
											

									</tr>
										{{/each}}
									
								</table>
							</div>
						</td>
						
					{{else type=="16"}}
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<div class="align_left content_16"><p class="title-value" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未填写</p></div>
							<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>
						</td>
					{{else type=="18"}}
						 <td class="condition div_select" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'select')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div> 
						</td>
				 	{{/if}}
				{{else sign==3}}
					<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
					<div class="align_left">						
						{{each(i,childList) childList}}
						{{if type=="1" || type=="20"||type=="10"}}
						<div class="sign_3" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"><p class="title-value income_structor_content" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">\${name}：<span>未填写</span></p></div>
						{{else type=="8"}}
						<div class="sign_3" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"><p class="title-value type_8 income_structor_content" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">\${name}：<span>未填写</span> <em class="packup">收起</em><em class="detail">详情</em></p></div>
						{{else type=="2" || type=="12" || type=="14" || type=="13"}}
						<div class="sign_3" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"><p class="title-value income_structor_content" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">\${name}：<span>未选择</span></p></div>
						{{else type=="7"}}
						<div class="sign_3" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}"><p class="title-value income_structor_content" data-code="\${code}" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">\${name}：<span>未添加</span></p></div>
						{{/if}}
					{{/each}}
					</div>
						<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>
					</td>
				{{else sign==1}}
					<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
						<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">sign==1</p></div>
						<span class="editPic" e-type="cover_pop" onclick="typeEdit(this)" attr-id="\${relateCode}"></span>
					</td>
				{{/if}}	
				{{if type}}
					{{if type!=15}}
				{{each(i,informationGrades) informationGrades }}
					<td>\${scoreMax}</td>
					<td>\${scoreExplain}</td>
					{{if isScore==0}}
					<!-- 系统打分 -->
						<td class="score-column" data-relate-id="\${titleRelateId}">0</td>
					{{else isScore==1}}
					<!-- 人工打分（select） -->
						<td class="score-column" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}">
							<select>
								<option>请选择</option>
							</select>
						</td>
					{{else isScore==2}}
					<!-- 人工打分（数值范围） -->
						<td class="score-column score-columns" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}">
							<input type="text" value="" placeholder="请打分"  name="\${isScore}" rulermarket="\${scoreMax}">
                              <em>(0-\${scoreMax}分)</em>
						</td>
					{{else isScore==4}}
					<!-- 根据其他子项分值计算得出 -->
						<td class="score-column" data-relate-id="\${titleRelateId}">0</td>
					{{/if}}
				{{/each}}
				{{/if}}
			{{else}}
			{{each(i,informationGrades) informationGrades }}
					<td>\${scoreMax}</td>
					<td>\${scoreExplain}</td>
					{{if isScore==0}}
					<!-- 系统打分 -->
						<td class="score-column" data-relate-id="\${titleRelateId}">0</td>
					{{else isScore==1}}
					<!-- 人工打分（select） -->
						<td class="score-column" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}">
							<select>
								<option>请选择</option>
							</select>
						</td>
					{{else isScore==2}}
					<!-- 人工打分（数值范围） -->
						<td class="score-column score-columns" data-title-id="\${titleId}" data-relate-id="\${titleRelateId}">
							<input type="text" value="" placeholder="请打分" name="\${isScore}" rulermarket="\${scoreMax}">
                              <em>(0-\${scoreMax}分)</em>
						</td>
					{{else isScore==4}}
					<!-- 根据其他子项分值计算得出 -->
						<td class="score-column" data-relate-id="\${titleRelateId}">0</td>
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
	<div class="div_tmpl">
		{{if type==8 || type==15}}
			<textarea id='myTextarea' placeholder="\${placeholder}"  data-content="\${content}" data-id="\${id}" maxlength="\${valRuleMark}" data-must="\${isMust}"></textarea>
		
		{{/if}}
	</div>
</script>






<!-- 编辑遮罩弹窗模板 -->
<script id="edit_tmpl2" type="text/x-jquery-tmpl">
	<form>
		<div class="h_edit section">
			<div class="h_btnbox">
				<span class="h_save_btn">保存</span>
				<span class="h_cancel_btn">取消</span>
			</div>
			{{if sign=="3"}}
		 	{{each(i,childList) childList}}
					<div class="mb_16">
					    <dl class="h_edit_txt clearfix">
							<dt data-type="\${type}"  data-title-id="\${id}" data-id="\${id}" data-code="\${code}" data-parentId="\${parentId}" data-must="\${isMust}">\${name}</dt>
							{{if type=="1"}}
							<dd><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}" data-must="\${isMust}"/></dd>

							{{else type=="2"}}
							<dd class="fl_none">
							<ul class="h_radios clearfix">
								{{each(i,valueList) valueList}}
								<li><input type="radio" value="\${id}" data-title-id="\${titleId}" data-type="\${type}" name="\${titleId}" data-must="\${isMust}"/>\${name}</li>
								{{/each}}
							  </ul>
							</dd>

							{{else type=="3"}}
							<!-- 复选 -->
						<td class="condition" onmouseover="mouserover(this)" onmouseout="mouseout(this)">
							<form>
							<span class="editPic" e-type="inside" onclick="typeEdit(this)" attr-id="\${relateCode}"  ></span>
							<div class="align_left"><p class="title-value" data-type="\${type}" data-title-id="\${titleId}" data-relate-id="\${id}">未选择</p></div>
							<div class="radioShow"></div>
							<div class="Button">
								<em onclick="right(this,'checkbox')" class="right"></em><i onclick="closeX(this)" class="wrong"></i>
							</div>
							</form>
						</td>

							{{else type=="4"}}
								{{each(i,valueList) valueList}}
								<dd>
								  <select name="" id="" data-must="\${isMust}">
									<option value="">\${name}</option>
								  </select>
								</dd>
								{{/each}}

							{{else type=="5"}}
							<dd>
							<ul class="h_radios clearfix">
								{{each(i,valueList) valueList}}
								<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" data-must="\${isMust}"/>\${name}</li>
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
								<dd class="fl_none">
									<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}" id="\${id}" onInput='countChar("\${id}","label_\${id}","\${valRuleMark}");'></textarea>
									<p class="num_tj">
										<label for="" id="label_\${id}">\${valRuleMark}</label>/\${valRuleMark}
									</p>
								</dd>
							{{else type=="7"}}
								<dd class="fl_none clearfix">
								 <ul class="h_imgs" id="edit-\${id}">

								 </ul>
								 <ul class="h_imgs"  id="edit-\${id}">
									<li class="h_imgs_add" id="h_imgs_add_\${id}"><input type="file" onchange="img_fun(this)" file-title-id="\${id}" id="selected_file_\${id}"></li>
								</ul>
								</dd>
								<dd class="fl_none red img_prompt">最多支持5张图片，最大上传大小2M，格式限定为jpg、png、gif、bmp</dd>
							{{else type=="8"}}
								<dd class="fl_none">
									<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}" id="\${id}" onInput='countChar("\${id}","label_\${id}","\${valRuleMark}");' data-must="\${isMust}"  name="\${id}"></textarea>
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
									 <th class="fixed_table_td">上游</th>
									 <td class="fixed_table_td">供应商</td>
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
									 	<th rowspan="2" class="fixed_table_td">下游</th>
									 	<td class="fixed_table_td">主要渠道</td>
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
									 	<td class="fixed_table_td">主要客户</td>
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
									<table data-title-id="\${id}" data-code="share-holding" class="editable" data-funflag="0" class="editable"><tbody><tr><th data-field-name="field1">持股人</th><th data-field-name="field2">持股比例</th><th data-field-name="opt">操作</th></tr></tbody></table>
									<span class="pubbtn bluebtn margin_btn" onclick="addRow(this)">新增</span>
								</dd>

							{{else type=="11"}}
								<dd>项目带过来的数据</dd>
							
							{{else type=="12"}}
								<dd class="fl_none">
								<ul class="h_radios clearfix">
									{{each(i,valueList) valueList}}
									<li><input type="radio" value="\${id}" data-value="\${value}" name="\${titleId}" data-id="\${id}" data-code="\${code}" data-must="\${isMust}"/>\${name}</li>
									{{/each}}
									<li class="text_li"><input type="text" data-value="\${value}" disabled="true" name="\${id}" data-id="\${id}" data-code="\${code}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}" maxlength="\${valRuleMark}"/></li>
								  </ul>
								</dd>
							{{else type=="13"}}
							<dd class="fl_none">
									<ul class="h_radios h_edit_checkbox  clearfix" data-type="\${type}">
										{{each(i,valueList) valueList}}
										<li class="check_label" data-value="\${value}" data-title-id="\${titleId}" value="\${id}" data-id="\${id}" data-code="\${code}" data-type="\${type}">\${name}</li>
										{{/each}}
										<li class="text_li text_li_13"><input data-type="\${type}" type="text" data-value="\${value}" disabled="true" name="\${id}" data-id="\${id}" data-code="\${code}"  placeholder="\${placeholder}" data-valrulemark="\${valRuleMark}" maxlength="\${valRuleMark}"/></li>
								 	 </ul>
								</dd>				

							{{else type=="14"}}
								<select data-id="\${id}" data-must="\${isMust}" data-title-id="\${id}">
								<option data-value="" data-type="\${type}" data-id="" data-title-id="\${id}" value="1" data-code="">请选择</option>
								{{each(i,valueList) valueList}}
									<option data-value="\${value}" data-type="\${type}" data-id="\${id}" data-title-id="\${titleId}" value="\${id}" data-code="\${code}">\${name}</option>
								{{/each}}
							</select>
							{{else type=="15"}}

								<dt data-type="\${type}">\${name}</dt>
								<dd class="fl_none">
									<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${placeholder}" id="\${id}" onInput='countChar("\${id}","label_\${id}","\${valRuleMark}");' data-must="\${isMust}" name="\${id}"></textarea>
									<p class="num_tj">
										<label for="" id="label_\${id}">\${valRuleMark}</label>/\${valRuleMark}
									</p>
								</dd>
									<dd class="fl_none">
									<textarea class="textarea_h" data-title-id="\${id}" data-type="\${type}" placeholder="\${content}" id="\${id}" onInput='countChar("\${id}","label_\${id}","\${valRuleMark}");' data-must="\${isMust}" name="\${id}"></textarea>
									<p class="num_tj">
										<label for="" id="label_\${id}">\${valRuleMark}</label>/\${valRuleMark}
									</p>
								</dd>
							{{else type=="16"}}
								<dt data-type="\${type}" data-must="\${isMust}">\${name}</dt>
								{{each(i,childList) childList}}
								<dt data-type="\${type}" data-must="\${isMust}">\${name}</dt>
								{{each placeholder.split('&')}}
								<dd class="fl_none"><input type="text" data-title-id="\${id}" data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder.split('&')[$index]}" /></dd>
								{{/each}}
								{{/each}}
							{{else type=="20"}}
								<dd><input type="text" data-title-id="\${id}"  data-type="\${type}" data-valrule="\${valRule}" data-valrulemark="\${valRuleMark}" placeholder="\${placeholder}"  data-must="\${isMust}"/></dd>
								<dd class="tmpl_con">\${content}</dd>
								<dd>
									<select id="\${id}_select" class="valuationInfo_20_select">
									{{each(i,valueList) valueList}}
										<option data-value="\${value}" data-type="\${type}" data-id="\${id}" data-title-id="\${titleId}" value="\${id}" data-code="\${code}">\${name}</option>
									{{/each}}
									</select>
								</dd>
							{{/if}}
					    </dl>
					</div>
				{{/each}}
			
			{{else}}
			<!-- 商业模式特殊情况 -->
			<div class="mb_16">
				<dl class="h_edit_txt clearfix">		
				{{if type=="16"}}
					<input data-type="\${type}" data-must="\${isMust}" class="hidden" data-code="\${code}" data-title-id="\${id}" type="text"/>
					{{each(i,childList) childList}}
							<dt class="title_dt" data-type="\${type}" data-must="\${isMust}">\${name}</dt>
							{{each placeholder.split('&')}}
							<dd class="fl_none"><input class="big_input" data-title-id="\${id}" data-index="\${id}_\${$index+1}" data-valrule="\${valRule}" placeholder="\${placeholder.split('&')[$index]}" maxlength="\${valRuleMark}"/></dd>
					{{/each}}
				{{/each}}
			{{/if}}
				</dl>
			</div>
		{{/if}}
			<div class="h_edit_btnbox clearfix">
				<span class="pubbtn bluebtn h_save_btn fl">保存</span>
				<span class="pubbtn fffbtn fl h_cancel_btn">取消</span>
			</div>


		</div>
	</form>	
</script>









