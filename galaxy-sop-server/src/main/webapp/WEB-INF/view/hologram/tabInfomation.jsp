<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<c:if test="${aclViewProject==true}">
<!--全息图-->
 				<ul class="h_navbar clearfix">
                  <li class="fl h_nav1 active">基本<br/>信息</li>
                  <li class="fl h_nav2">项目</li>
                  <li class="fl h_nav2">团队</li>
                  <li class="fl h_nav1">运营<br/>数据</li>
                  <li class="fl h_nav2">竞争</li>
                  <li class="fl h_nav1">战略及<br/>策略</li>
                  <li class="fl h_nav2">财务</li>
                  <li class="fl h_nav2">法务</li>
                  <li class="fl h_nav1">融资及<br/>估值</li>
                </ul>
                <script>
                $(function(){
                	$('.h_navbar').tabLazyChange({
                		onchangeSuccess:function(index){	
                			switch(index){
                				case 0: initBaseInfo(projectId);  break;  //标签0:基本信息
                				case 1: initProjectInfo(); break;  //标签1:项目
                				case 2: initTeamInfo(); break;  //标签2: 团队
                				case 3: initOperateInfo();   break;  //标签3:运营数据
                				case 4: initCompeteInfo();   break;  //标签4:竞争
                				case 5: initPlanInfo();   break;  //标签5:战略及策略
                				case 6: initFinanceInfo();   break;  //标签6:财务
                				case 7: initJusticeInfo();   break;  //标签7:法务
                				case 8: initValuationInfo();   break;  //标签8:融资及估值
                				default: return false;
                			}
                			
                		}
                });
                	
                })
                //基本信息
			function initBaseInfo(projectId){
				$.getTabHtml({
					url : platformUrl.toBaseInfo +'/'+ projectId
				});
			}
                //项目
    			function initProjectInfo(){
    				$.getTabHtml({
    					url : platformUrl.toProjectInfo ;
    				});
    			}
    			 //团队
    			function initTeamInfo(){
    				$.getTabHtml({
    					url : platformUrl.toTeamInfo ;
    				});
    			}
    			 //运营数据
    			function initOperateInfo(){
    				$.getTabHtml({
    					url : platformUrl.toOperateInfo ;
    				});
    			}
    			//竞争
    			function initCompeteInfo(){
    				$.getTabHtml({
    					url : platformUrl.toCompeteInfo ;
    				});
    			}
    			//战略以及策略
    			function initPlanInfo(){
    				$.getTabHtml({
    					url : platformUrl.toPlanInfo ;
    				});
    			}
    			//财务
    			function initFinanceInfo(){
    				$.getTabHtml({
    					url : platformUrl.toFinanceInfo ;
    				});
    			}
    			//法务
    			function initJusticeInfo(){
    				$.getTabHtml({
    					url : platformUrl.toJusticeInfo ;
    				});
    			}
    			//融资及估值
    			function initValuationInfo(){
    				$.getTabHtml({
    					url : platformUrl.toValuationInfo ;
    				});
    			}
    			
                </script>

</c:if>
