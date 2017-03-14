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

</c:if>
