<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>繁星</title>
	<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<%-- 	<link rel="stylesheet" href="<%=path %>/css/bootstrap.min-v3.3.5.css"  type="text/css">
 --%>	<!-- 校验样式 -->
<%--     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/reset.css" /> --%>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
	<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<!-- jsp文件头和头部 -->
	<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
    <script type="text/javascript" src="<%=path %>/js/project.js"></script>

    <script src="<%=path %>/js/init.js"></script>

    <!-- 表单验证 -->
	<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
	<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
    <script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>
    
   <!-- 日历插件 -->
	<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
	<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
	<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
	<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
	<style type="text/css">
		.content{
			float: left;
			padding:0 10px;
		}
	</style>
</head>

<body>

<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	 <!--右中部内容-->
 	<div class="ritmin">
    <h2>我的项目</h2>
      <!-- 面包屑
      <ul class="breadcrumb">
        <li><a href="javascript:;" class="bcfirst">待办任务</a></li>
        <li><span>&gt;</span><a href="javascript:;">项目基本信息</a></li>
        <li><span>&gt;</span><a href="javascript:;" class="active">项目详情信息</a></li>
      </ul> -->
      <div class="clearfix"></div>
        <!--项目详细信息内容-->
        <div class="projectmsg_d clearfix">
          <h2 id="project_name"></h2>
          <form action="" method="post" id="update_form_basic">
          <input type="hidden" id="pid" name="id" value="${requestScope.pid}"/>
          <!-- 第1部分 -->
          <div class="block block1">
            <table width="100%" cellspacing="5" cellpadding="0" >
             <tbody>
                  <tr>
                      <td><dl><dt>项目编码：</dt><dd id="project_code"></dd></dl></td>
                      <td><dl><dt>创建时间：</dt>
                     <%--  <dd>
                      <input type="text" class="datepicker-text time" name="createDate" id="create_date" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空"/>
                      </dd> --%>
                      <dd id="create_date">
                      </dd>
                      </dl></td>
                  </tr>
                  <tr>
                      <td><dl><dt>项目名称：</dt><dd id="projectName"></dd></dl></td>
                      <td><dl><dt>项目类型：</dt><dd id="projectType"></dd></dl></td>
                  </tr>
                  <tr><td><dl><dt>计划额度：</dt>
                          <dd><input id="project_contribution" name="formatContribution" type="text" value="" placeholder="计划额度" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>只能为整数或两位小数点的数字"/></dd>
                        </dl></td>
                      <td><dl><dt>估值：</dt><dd>
                      <input type="text" id="project_valuations" name="formatValuations" value="" placeholder="估值" allowNULL="yes" valType="LIMIT_11_NUMBER" msg="<font color=red>*</font>只能为整数或两位小数点的数字">
                      </dd></dl></td>
                  </tr>
                  <tr>
                      <td><dl><dt>出让股份：</dt>
                          <dd><input type="text" id="project_share_ratio" name="formatShareRatio" value="" class="transferSharesTxt" allowNULL="yes" valType="LIMIT_2_INTEGER" msg="<font color=red>*</font>0-100间整数"><span>&nbsp;%</span></dd>
                        </dl></td>
                      <td>
                        <dl>
                          <dt>单位（万）：</dt>                          <dd>
                          	<label id="currencyUnitBlock"><input id="currencyUnit" name="formatUnit" type="radio" value="" />请选择</label>
                            <label><input id="currencyUnit0" name="formatUnit" type="radio" value="0" />人民币</label>
                            <label><input id="currencyUnit1" name="formatUnit" type="radio" value="1" />美元</label>
                            <label><input id="currencyUnit2" name="formatUnit" type="radio" value="2" />英镑</label>
                            <label><input id="currencyUnit3" name="formatUnit" type="radio" value="3" />欧元</label>
                          </dd>
                        </dl>
                      </td>
                  </tr>
                  <tr>
                      <td>
                        <dl>
                          <dt>公司名称：</dt>
                          <dd><input type="text" value="" id="project_company" name="projectCompany" placeholder="公司名称"/></dd>
                        </dl>
                      </td>
                      <td>
                        <dl>
                          <dt>组织机构代码：</dt>
                          <dd><input type="text" value="" class="zzjg_txt" name="projectCompanyCode" id="project_company_code" placeholder="组织机构代码" allowNULL="yes" valType="CODE" msg='<font color=red>*</font>由字母或数字或"-"组成'/></dd>
                        </dl>
                      </td>
                  </tr>                  
                </tbody>
              </table>
              <div class="btnbox">
                <a href="javascript:;" onclick="update()" class="pubbtn bluebtn">保存</a><!-- <a href="javascript:;" class="pubbtn fffbtn"data-close="close">关闭项目</a> -->
                 <a href="javascript:;" onclick="history.go(-1);" class="bluebtn pubbtn">返回</a>
              </div>
          </div>
          </form>
          
          
          
          <!-- 商业计划  -->      
          <div class="block block2 shadow">
            <dl>
              <dt>商业计划书</dt>
               <dd id="business_plan_dd" class="fctbox">
<!--                 <a href="javascript:;" class="ico f1" data-btn="upload" onclick="uploadBusinessPlan()" >更新</a> -->
<!--                 <a href="javascript:;" class="ico f1" data-btn="download" onclick="downloadBusinessPlan()" >下载</a> -->
              </dd>
            </dl>
          </div> 
          
          <!-- 第2部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>项目概述</dt>
              <dd class="edit">
              	  <div type="text/plain" id="describe_editor" class="um_width" style="width:100%;height:100px;">
				  </div>
			  </dd>
			  <dd class="describe" id="describe_show"></dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit" onclick="editModelEditor('describe_editor')">编辑</a>
                <a href="javascript:;" id="save_describe" class="ico f4" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第3部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>商业模式</dt>
              <dd class="edit">
              	  <div type="text/plain" id="business_model_editor" class="um_width" style="width:100%;height:100px;">
				  </div>
		      </dd>
		      <dd class="describe" id="model_show"></dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit" onclick="editModelEditor('business_model_editor')">编辑</a>
                <a href="javascript:;" id="save_business_model" class="ico f4" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第4部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>公司定位</dt>
              <dd class="edit">
			      <div type="text/plain" id="location_editor" class="um_width" style="width:100%;height:100px;">
				  </div>
			  </dd>
			  <dd class="describe" id="location_show"></dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit" onclick="editModelEditor('location_editor')">编辑</a>
                <a href="javascript:;" class="ico f4" id="save_location" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第5部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>用户分析</dt>
              <dd class="edit">
			  	  <div type="text/plain" id="portrait_editor" class="um_width" style="width:100%;height:100px;">
				  </div>
			  </dd>
			   <dd class="describe" id="portrait_show"></dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit" onclick="editModelEditor('portrait_editor')">编辑</a>
                <a href="javascript:;" class="ico f4" id="save_portrait" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第6部分 -->
          <div class="block block2 shadow">
            <dl>
              <dt>竞情分析</dt>
              <dd class="edit">
			      <div type="text/plain" id="analysis_editor" class="um_width" style="width:100%;height:100px;">
				  </div>
			  </dd>
			  <dd class="describe" id="analysis_show"></dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f1" data-btn="edit" onclick="editModelEditor('analysis_editor')">编辑</a>
                <a href="javascript:;" id="save_analysis" class="ico f4" data-btn="submit">保存</a>
                <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
                <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>
          <!-- 第7部分 -->
          <div class="block block2 shadow clearfix">
            <dl>
              <dt>团队成员</dt>
              <dd class="full_w describe clearfix">
              	<div class="btnbox_f" style="margin-bottom:10px;">
                  <a href="javascript:;" class="ico b1 fffbtn" onclick="addPerson();">添加</a>
                  <a href="javascript:;" class="ico b2 fffbtn" onclick="toSureMsg();">完善简历</a>
                  <!--  
                  <a href="javascript:;" class="ico b2">修改</a>
                  <a href="javascript:;" class="ico b3">删除</a>-->
                </div>
                <div class="clearfix"></div>
                <div class="tab-pane active" id="view">	
	               	<table id="tablePerson"  data-height="555" 
	               	data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" >
					</table> 
				</div>
				
              </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div> 
          <!-- 第8部分  -->      
          <div class="block block2 shadow">
            <dl>
              <dt>股权结构</dt>
              <dd class="full_w describe clearfix">
              <div class="btnbox_f">
                  <a href="javascript:;" onclick="addSharesView();" class="ico b1 fffbtn">添加</a>
                  <!-- 
                  <a href="javascript:;" class="ico b2">修改</a>
                  <a href="javascript:;" class="ico b3">删除</a>
                   -->
                </div>
                <div class="clearfix"></div>
                  <div class="tab-pane active" id="pView">	
	               <table id="table" data-height="555" data-method="post"
	               	 data-page-list="[10,20,30]" data-show-refresh="true">
					</table>
				</div>
              </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div> 
          <!-- 第9部分     
          <div class="block block2 shadow">
            <dl>
              <dt>档案库</dt>
              <dd class="full_w describe clearfix">
              	<div class="clearfix">
                	<div class="btnbox_f">
                  <a href="javascript:;" class="ico b4">上传</a>
                  <a href="javascript:;" class="ico b5">发送选中</a>
                </div>
                <div class="search tsear clearfix">
                    <a href="javascript:;" class="bluebtn ico cx">查询</a>
                    <div class="searoption">
                      <span>更新时间:</span>
                      <input type="text" value="2016-01-01" data-date="time" class="time"/>
                      <span>至</span>
                      <input type="text" value="2016-05-01" data-date="time"/>
                    </div>
                </div>
                </div>
                <table width="100%" cellspacing="0"  cellpadding="0">
                  <thead>
                      <tr>
                          <th></th>
                          <th>文件来源</th>
                          <th>起草者</th>
                          <th>存储类型</th>
                          <th>业务分类</th>
                          <th>更新日志</th>
                          <th>档案状态</th>
                          <th>操作</th>
                          <th>附件查看</th>
                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td><input type="checkbox" name="checkbox" checked="checked"/></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                      <tr>
                          <td><input type="checkbox" name="checkbox" checked="checked"/></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                      <tr>
                          <td><input type="checkbox" name="checkbox" /></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                      <tr>
                          <td><input type="checkbox" name="checkbox" checked="checked"/></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                      <tr>
                          <td><input type="checkbox" name="checkbox"/></td>
                          <td>内部</td>
                          <td>投资经理</td>
                          <td>文档</td>
                          <td>访谈备忘</td>
                          <td>2016-01-23</td>
                          <td>可用</td>
                          <td>更新</td>
                          <td>文件1</td>
                      </tr>
                  </tbody>
                </table>
                 <ul>
                  <li><a href="javascript:;">首页</a></li>
                  <li><a href="javascript:;">上一页</a><span class="active">/</span><a href="javascript:;" class="active">下一页</a></li>
                  <li><a href="javascript:;" class="active">末页</a></li>
                </ul>
              </dd>
              <dd class="fctbox">
                <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a><a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
              </dd>
            </dl>
          </div>  -->         
        </div>
        <!-- 关闭按钮 -->
        <a href="javascript:;" class="pubbtn fffbtn close"  onclick="closePro()" >否决项目</a>
    </div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/init.js"></script>
<!-- bootstrap-table -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=request.getContextPath() %>/js/axure.js"></script>
	<!-- 富文本编辑器 -->
	  <link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
	<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
    <script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
	$(function(){
		createMenus(5);
	});
    var pid='${requestScope.pid}';
    
    function closePro(){
    	if(confirm("确定要否决项目吗？")){
    		sendGetRequest(platformUrl.closeProject+pid,null,closeback);
    	}
    }


//关闭回调
function closeback(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		alert("error "+data.result.message);
		return;
	}else{
		alert("该项目已关闭");
		history.go(-1);
		//forwardWithHeader(platformUrl.mpl);
	}
	location.reload(true);
}
    
    
    function toSureMsg(){
    	sendGetRequest(
    			Constants.sopEndpointURL + '/galaxy/soptask/toSureMsg/'+pid, 
				 null, function(data){
					 layer.msg(data.result.message);
				 });
    }
    
    
	function editor(value, row, index){
		var id=row.id;
		var url='<%=path %>/galaxy/project/updatePro/'+id;
		var options = '<div class="btnbox_f"><a href="javascript:;" class="ico b2">人人简历</a>'+
			'<a href="javascript:;"  class="ico b2" onclick="updatePer('+id+')">修改</a>'+
            '<a href="javascript:;"  class="ico b2" onclick="deletePer('+id+')">删除</a></div>';
		return options;
	}
	function formatGender(index, row) {
		if (row.gender == true) {
			return "男";
		} else {
			return "女";
		}
	}
	
	  function editStock(id){
	    	var _url = platformUrl.editStockView+id;
			$.getHtml({
				url:_url,//模版请求地址
				data:"",//传递参数
				okback:function(){
				
				}//模版反回成功执行	
			});
			return false;
	    }
	    
	    //添加团队成员
	    function addPerson(){
	    	sendGetRequest(platformUrl.getDegreeByParent + "/" + "null",null,addPersonCallBack);
			return false;
	   }
	   function addPersonCallBack(data){
		   $.getHtml({
				url:platformUrl.addPersonView,//模版请求地址
				data:"",//传递参数
				okback:function(){
					var _dom =  $("#highestDegree");
				    _dom.empty();
					_dom.append("<option value='all'>请选择</option>");
					$.each(data.entityList,function(){
						if(this.code){
							_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
						}else{
							_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
						}
						
					});	
				}//模版反回成功执行	
					
			});     
	   }
	    
	   //添加股权结构
	   function addSharesView(){
			$.getHtml({
				url:platformUrl.addSharesView,//模版请求地址
				data:"",//传递参数
				okback:function(){
					
					
					
					
					
					
				}//模版反回成功执行	
					
			});
			return false;
	   }
	    
	   
	function updatePer(id){
	
		sendGetRequest(platformUrl.getDegreeByParent+"/"+id,null,updatePerCallBack);
		return false;
	}
	
	function updatePerCallBack(data){
		var _url = platformUrl.updatePerView+data.result.message;
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				
				var _dom =  $("#highestDegree");
			    _dom.empty();
				_dom.append("<option value='all'>请选择</option>");
				$.each(data.entityList,function(){
					if(_dom.data("value")==this.code){
						_dom.append("<option value='"+this.code+"' selected=true >"+this.name+"</option>");
					}else{
						_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
					}
					
				});	
			
			}//模版反回成功执行	
		});
	}
	getTabPerson();
	getTabShare();
	
	function getTabPerson(){
		var html='<table id="tablePerson"  data-height="555" data-method="post" data-page-list="[10,20,30]" data-show-refresh="true" ></table>';
		$("#view").html(html);
		var $table = $('#tablePerson');
	    $table.bootstrapTable({
	    url: platformUrl.projectPersonList,
	    dataType: "json",
	    pagination: true, //分页
	    search: false, //显示搜索框
	    pageList: [10,20,30],
	    queryParamsType: 'size|page',
	    queryParams: function(params){params.projectId="${pid}"; return params;},
	    sidePagination: "server", //服务端处理分页
	          columns: [
	                  {
	                    title: '姓名',
	                      field: 'personName',
	                      align: 'center',
	                      valign: 'middle'
	                  },
	                  {
                        title: '性别',
                        field: 'personSex',
                        align: 'center',
                        valign: 'middle',
                        formatter:function(value,row,index){ 
                         	if (row.personSex == 0) {
                    			return "男";
                    		}else if (row.personSex == 1) {
                    			return "女";
                    		}else {
                    			return "-";
                    		}
                        }
	                    },
	                    {
	                        title: '年龄',
	                          field: 'personAge',
	                          align: 'center',
	                          valign: 'middle'
	                     },
	                     {
	                          title: '当前职务',
	                            field: 'personDuties',
	                            align: 'center',
	                            valign: 'middle'
	                  },
	                  {
	                      title: '电话',
	                        field: 'personTelephone',
	                        align: 'center',
	                        valign: 'middle'
	                  },
	                  {
	                      title: '最高学历',
	                        field: 'highestDegree',
	                        align: 'center',
	                        valign: 'middle',
	                        formatter:function(value,row,index){ 
	                         	if (row.highestDegree == 1) {
	                    			return "高中";
	                    		}else if (row.highestDegree == 2) {
	                    			return "大专";
	                    		}else if (row.highestDegree == 3) {
	                    			return "本科";
	                    		}else if (row.highestDegree == 4) {
	                    			return "硕士";
	                    		}else if (row.highestDegree == 5) {
	                    			return "MBA";
	                    		}else if (row.highestDegree == 6) {
	                    			return "博士";
	                    		}else if (row.highestDegree == 7) {
	                    			return "其他";
	                    		}
	                    		else {
	                    			return "-";
	                    		}
	                        }
	                  },
	                  {
	                      title: '工作年限',
	                        field: 'workTime',
	                        align: 'center',
	                        valign: 'middle'
	                  },
	                  {
	                      title: '操作',
	                      field: 'id',
	                      align: 'center',
	                      formatter:function(value,row,index){  
		                   var a = '<a href="javascript:;" mce_href="javascript:;" class="blue" onclick="tiaozhuan(\''+ row.id + '\')" DATA-btn="resume" >个人简历</a>';;
		                   var e = '<a href="javascript:;" mce_href="javascript:;" class="blue" onclick="updatePer(\''+ row.id + '\')">修改</a> ';  
		                   var d = '<a href="javascript:;" mce_href="javascript:;" class="blue" onclick="deletePer(\''+ row.id +'\')">删除</a> ';  
	                        return a+e+d;  
	                    } 
	                  }
	              ]
	      });
	      $table.bootstrapTable('refresh');
		}
	//股权结构列表
	function getTabShare(){
	var html='<table id="table" data-height="555" data-method="post" data-page-list="[10,20,30]" data-show-refresh="true"></table>';
	$("#pView").html(html);
	var $table = $('#table');
    $table.bootstrapTable({
    url: platformUrl.projectSharesList,  
    dataType: "json",
    pagination: true, //分页
    search: false, //显示搜索框
    showRefresh: true,
    pageList: [10,20,30],
    queryParamsType: 'size|page',
    queryParams: function(params){params.projectId="${pid}"; return params;},
    sidePagination: "server", //服务端处理分页
          columns: [
                  {
                    title: '类型',
                      field: 'sharesType',
                      align: 'center',
                      valign: 'middle'
                  },
                  {
                      title: '所有权人',
                        field: 'sharesOwner',
                        align: 'center',
                        valign: 'middle'
                    },
                    {
                        title: '占比(%)',
                          field: 'sharesRatio',
                          align: 'center',
                          valign: 'middle'
                     },
                     {
                          title: '获取方式',
                            field: 'gainMode',
                            align: 'center',
                            valign: 'middle'
                  },
                  {
                      title: '备注',
                        field: 'remark',
                        align: 'center',
                        valign: 'middle'
                  },
                  {
                      title: '操作',
                      field: 'id',
                      align: 'center',
                      formatter:function(value,row,index){  
                   var e = '<a href="javascript:;" mce_href="javascript:;" class="blue" onclick="editStock(\''+ row.id + '\')">修改</a> ';  
                   var d = '<a href="javascript:;" mce_href="javascript:;" class="blue" onclick="delStock(\''+ row.id +'\')">删除</a> ';  
                        return e+d;  
                    } 
                  }
              ]
      });
      $table.bootstrapTable('refresh');
	}
    //页面传参
    function queryParams(params) {
    	return {
	    	pageSize: params.limit,
	    	pageNum: params.offset,
	    	order: params.order,
	    	projectId:pid
    	};
    }
    
    function editModelEditor(id){
    	UM.getEditor(id);
    }
 
    $("#project_share_ratio").blur(function(){
		var valuations = calculationValuations();
		if(valuations){
			$("#project_valuations").val(valuations.toFixed(2));
		}
	});
	$("#project_contribution").blur(function(){
		var valuations = calculationValuations();
		if(valuations){
			$("#project_valuations").val(valuations.toFixed(2));
		}
	});
	function calculationValuations(){
		var projectShareRatio = $("#project_share_ratio").val();
		var projectContribution = $("#project_contribution").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			return projectContribution * (100/projectShareRatio);
		}
		return null;
	}

	//个人简历
	function tiaozhuan(id){
		
		var url =platformUrl.personHHr
		$.getHtml({
			url: url+"?personId="+id, 
			data:"",//传递参数
			okback:function(){
			/* alert("进入完善简历页面"); */
			$(".resumetc .tabtable").tabchange2();
			}//模版反回成功执行	
		}); 
		
	}
	
	function uploadBusinessPlan(){
		var projectId = $("#pid").val();
		var projectName = $("#projectName").html();
		formData = {
    			_fileType : "fileType:1",
    			_fileTypeAuto : true,
    			_workType : "fileWorktype:12",
    			_projectId : projectId,
    			_projectName : projectName,
    			_isProve : "hide",
    			_remark : "hide",
				callFuc : function(){
					console.log("刷新商业计划模块");
					window.location.reload(Constants.sopEndpointURL + "/galaxy/upp/" + $("#pid").val());
				},
				_url : platformUrl.commonUploadFile, //兼容老板插件
				_localUrl : platformUrl.commonUploadFile
		};
		win.init(formData);
		
	}
	
	function downloadBusinessPlan(id){
		window.location.href=platformUrl.downLoadFile+'/'+id ;
	}
	
	
</script>	   
<%-- <script src="<%=request.getContextPath() %>/js/axure_ext.js"></script> --%>
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path %>/js/teamSheetNew.js"></script>


</html>

