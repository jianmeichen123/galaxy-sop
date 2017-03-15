<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目详情</title>
</head>
<body>
<ul class="h_navbar clearfix">
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('0')" >基本<br/>信息</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
                  <li data-tab="navInfo" class="fl h_nav2 active" onclick="tabInfoChange('2')">团队</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br/>数据</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br/>策略</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
                  <li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
                  <li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br/>估值</li>
                </ul>


 <div class="h radius">
                  <div class="h_look h_team_look clearfix">
                    <div class="h_btnbox"><span class="h_edit_btn">编辑</span></div>
                    <div class="h_title">核心创始团队</div>
                    
                  </div>
                  <div class="h_edit">
                    <div class="h_btnbox"><span class="h_save_btn">保存</span><span class="h_cancel_btn" data-on="h_cancel">取消</span></div>
                    <div class="h_title">公司定位/商业模式</div>
                    <div class="mb_16">
                       <dl class="h_edit_txt clearfix">
                        <dt class="fl_none">商业模式：</dt>
                        <dd class="fl_none">
                          <textarea class="textarea_h"></textarea>
                          <p class="num_tj"><label for="">0</label>/2000</p>
                        </dd>
                      </dl>
                    </div>
                    <div class="mb_16">
                       <dl class="clearfix">
                        <dt>商业模式进化：</dt>
                        <dd>
                           <ul class="h_radios clearfix">
                            <li><input type="radio"/>闪投</li>
                            <li><input type="radio"/>闪投</li>
                            <li><input type="radio"/>闪投</li>
                            <li><input type="radio"/>闪投</li>
                          </ul>
                        </dd>
                      </dl>
                    </div>
                    <div class="h_edit_btnbox clearfix">
                      <span class="pubbtn bluebtn fl" data-on="save">保存</span>
                      <span class="pubbtn fffbtn fl" data-name="basic" data-on="h_cancel">取消</span>
                    </div>
                  </div>
                </div>
               
</body>
<script src="<%=path %>/js/hologram/teamInfo.js" type="text/javascript"></script>
</html>
