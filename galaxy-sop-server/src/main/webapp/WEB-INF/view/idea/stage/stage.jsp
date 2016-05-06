<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<div class="creativetc" >
  <a href="javascript:;" data-close="close" class="close null">关闭</a>
          <!-- 创意动态 -->
          <div class="pjt_introduct clearfix">
            <div class="clearfix">
              <h3>食乐淘</h3>       
            </div>
            <ul class="pjt_brf clearfix">
              <li>最新变更动态<span>2016年04月10日</span>&nbsp;<span>张志成</span>&nbsp;创意名称由<span>食品</span>变更为<span>食乐淘</span>。</li>
            </ul>
          </div>

          <div class="tabtable">
          <!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav" id="ideaProgress_1">基本信息</li>
                <li data-tab="nav" id="ideaProgress_2">调研</li>
                <li data-tab="nav" id="ideaProgress_3">创建立项会</li>           
                <li data-tab="nav" id="ideaProgress_5">创建项目</li>           
                <li data-tab="nav" id="ideaOperateLog">变更动态</li>           
            </ul>
            <!-- tab内容 -->
            <div class="tabtable_con tabtable_con_close">
            <!-- 创意基本信息 -->
              <div class="block block_t show aa"  data-tab="con" id="ideaDetail">
                  <div class="btnbox_f1 btnbox_m clearfix">
                    <button class="pubbtn fffbtn" href="tchtml/creative_claimtc.html" data-btn="claim">认领</button>
                    <button class="pubbtn fffbtn" href="<%=path%>/galaxy/idea/goIdeaEdit/" data-btn="edit">编辑</button>
                    <input type="hidden" id="IdeaId">
                    <button class="pubbtn fffbtn" href="<%=path %>/galaxy/idea/showHistory?id=${id}" data-btn="history">历史信息</button>
                  </div>
                  <div class="top clearfix">
                    <dl>
                      <dt>创意编号：</dt>
                      <dd id="ideaCode"></dd>
                    </dl>
                    <dl>
                      <dt>创意名称：</dt>
                      <dd id="ideaName">食乐淘</dd>
                    </dl>
                    <dl>
                      <dt>创意来源：</dt>
                      <dd id="ideaSource"></dd>
                    </dl>
                    <dl>
                      <dt>提出人：</dt>
                      <dd id="createdUname">投资经理</dd>
                    </dl>
                    <dl>
                      <dt>所属事业线：</dt>
                      <dd id="departmentDesc">O2O</dd>
                    </dl>
                  </div>
                  <div class="mid clearfix">
                    <dl>
                      <dt>提出时间：</dt>
                      <dd id="createdTime" data-formatter="dateFormatter">2016-05-08</dd>
                    </dl>
                  </div>
                  <div class="bottom clearfix">
                    <dl>
                      <dt>创建阐述：</dt>
                      <dd id="ideaDesc"></dd>
                    </dl>
                  </div>
              </div>

              <!-- 调研 -->
              <!-- 调研   idea-->
			<div class="block block_t" data-tab="con" id="ideaProgress_2_con">
				<!--按钮-->
				<div id="options_point2" class="btnbox_f btnbox_f1 btnbox_m clearfix">
					<a href="javascript:;" id="cy_up_report"  onclick="showUploadPopup('u','ideaProgress:2','')" class="pubbtn fffbtn lpubbtn option_item_mark">上传可行性报告</a> 
					<a href="javascript:;" id="cy_start_lxh" onclick="stratLxh()"  class="pubbtn fffbtn lpubbtn option_item_mark">启动创建立项会</a>
					<a href="javascript:;" id="cy_low" onclick="ideaReload()" class="pubbtn fffbtn lpubbtn option_item_mark">放弃</a>
				</div>
				
				<div id="ideaProgress_2_params">
					<input type="hidden" name="projectId" data-id="ideaId" value="${id}">
					<input type="hidden" name="projectProgress" value="ideaProgress:2">
				</div>
				<!--表格内容-->
				<table id="ideaProgress_2_table" data-id-field="id"
					data-url="<%=path%>/galaxy/idea/queryIdeaDyList"  data-page-list="[3,5,10]"
					data-toolbar="#ideaProgress_2_params">
					<thead>
						<tr>
							<th data-field="fileUName" data-align="center" >上传者</th>
							<th data-field="fType" data-align="center" >存档类型</th>
							<th data-field="projectProgress" data-align="center" data-formatter="progressFormatter">创意状态</th>
							<th data-field="careerLineName" data-align="center" >所属事业线</th>
							<th data-field="updatedDate" data-align="center"  data-formatter="datetimeFormatter2">更新时间</th>
							<th data-align="center" data-formatter="ideaOperateFormat">操作</th>
							<th data-align="center" data-formatter="ideaFileDownFormat">附件查看</th>
						</tr>
					</thead>
				</table>
				
			</div>
			
			
			
		<!-- 创建立项会 -->
              <div class="block block_t" data-tab="con">
                <!--按钮-->
                <div class="btnbox_f btnbox_f1 btnbox_m clearfix">
                    <button class="pubbtn fffbtn lpubbtn" href="<%=path %>/galaxy/idea/ideaGoMeeting" data-btn="meeting">添加会员纪要</button>
                    <button class="pubbtn fffbtn lpubbtn" href="tchtml/creative_e_n.html" data-btn="create">创建成项目</button>
                    <button class="pubbtn fffbtn" href="tchtml/creative_abandontc.html" data-btn="abandon">放弃</button>
                </div>

              <!--表格内容-->
                <table width="100%" cellspacing="0" cellpadding="0" class='table_l'>
                      <thead>
                          <tr>
                              <th>会议概况</th>
                              <th>会议纪要</th>
                          </tr>
                      </thead>                                                                                                                                    
                      <tbody>
                          <tr>
                              <td class="td1">会议时间:<span>2016-04-02</span><br/>会议结论:<span>2016-04-02</span><br/>会议录音:<a href="javascript:;" class="blue">录音.mp3</a></td>
                              <td class="td2">头脑风暴法（Brainstorming）是最为人所熟悉的创意思维策略，该方法是由美国人奥斯本（Osborn）早于1937年所倡导，此法强调集体思考的方法，着重互相激发思考，鼓励参加者于指定时间内，构想出大量的意念，并从中引发新颖的构思。</td>
                          </tr>
                 
                      </tbody>
                  </table>
              </div>
		<!-- 创建项目 -->
              <div class="block block_t" data-tab="con">
                <!--按钮-->
                <div class="btnbox_f btnbox_f1 btnbox_m clearfix">
                    <button class="pubbtn fffbtn lpubbtn" href="tchtml/creative_e_n.html" data-btn="edit_name">编辑项目名称</button>
                </div>

              <!--表格内容-->
                <table width="100%" cellspacing="0" cellpadding="0">
                      <thead>
                          <tr>
                              <th>创意编码</th>
                              <th>创意名称</th>
                              <th>关联项目</th>
                              <th>投资事业线</th>
                              <th>项目进度</th>
                              <th>合伙人</th>
                          </tr>
                      </thead>                                                                                                                                    
                      <tbody>
                          <tr>
                              <td>10000002</td>
                              <td><a href="javascript:;" class="blue" id="project_name">食乐淘</a></td>
                              <td><a href="javascript:;" class="blue">食乐淘</a></td>
                              <td>O2O</td>
                              <td>接触访谈</td>
                              <td>关屿</td>
                          </tr>
                 
                      </tbody>
                  </table>
              </div>
              		<!-- 变更动态 -->
              <div class="block block_t" data-tab="con">
              <!--表格内容-->
                <table width="100%" cellspacing="0" cellpadding="0">
                      <thead>
                          <tr>
                              <th>操作时间</th>
                              <th>操作人</th>
                              <th>变更动态</th>
                              <th>进度</th>
                          </tr>
                      </thead>                                                                                                                                    
                      <tbody>
                          <tr>
                              <td>2016-03-22 16:20:34</td>
                              <td>徐茂栋</td>
                              <td>添加</td>
                              <td>待认领</td>
                          </tr>
                          <tr>
                              <td>2016-03-22 16:20:34</td>
                              <td>投资经理甲</td>
                              <td>认领</td>
                              <td>调研</td>
                          </tr>
                          <tr>
                              <td>2016-03-22 16:20:34</td>
                              <td>投资经理甲</td>
                              <td>上传</td>
                              <td>创建立项会</td>
                          </tr>
                 
                      </tbody>
                  </table>
              </div>            
              </div>              
           </div>
          </div>  
      <script type="text/javascript">
		/* $(function(){
			getProjectInfo();
		});
            
       function getProjectInfo()
        {
	     var url = platformUrl.detailIdea+"/${id}";
	    sendGetRequest(
		url,
		{"id":"${id}"},
		function(data){
			if(data.result.status == "Error")
			{
				alert(data.result.message );
				return;
			}
			var idea = data.entity;
			stockTransfer = idea.stockTransfer;
			
			$("#ideaDetail dd")
			.each(function(){
				var self = $(this);
				if(self.attr('id') != 'undefined')
				{
					var id = self.attr('id');
					var formatter = self.data('formatter');
					var text = idea[id]
					if($.isFunction(window[formatter]))
					{
						text = window[formatter].call(window,text);
					}
					self.text(text);
				}
				
			});
		}
	);
} */
</script>