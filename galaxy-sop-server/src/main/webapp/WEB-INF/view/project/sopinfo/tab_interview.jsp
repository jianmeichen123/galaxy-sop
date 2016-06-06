<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>项目详情</title>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- jsp文件头和头部 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>

<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />
</head>


<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
 	<div class="ritmin">
 	
    	<div class="new_tit_a"><a href="#">工作桌面</a>><a href="#">创投项目</a>>Utter绝对潮流</div>
    	
    	<div class="new_tit_b">
        	<span class="new_color size18">Utter绝对潮流</span><span class="new_color">ID987786600009</span>
        	<span class="b_span"><a href="#">返回项目列表></a></span>
        </div>
        
        
        <div class="new_left">
        	<div class="tabtable assessment label_static">
          	<!-- tab标签 -->
            <ul class="tablink">
                <li data-tab="nav"><a href="javascript:;">基本信息</a></li>
                <li data-tab="nav"><a href="javascript:;">团队成员</a></li>
                <li data-tab="nav"><a href="javascript:;">股权结构</a></li>
                <li data-tab="nav"><a href="javascript:;">访谈记录</a></li>
                <li data-tab="nav"><a href="javascript:;">会议纪要</a></li>
                <li data-tab="nav"><a href="javascript:;">项目文档</a></li>
                <li data-tab="nav" class="no"><a href="javascript:;">操作日志</a></li>
            </ul>

            
             <!-- 访谈记录 -->
            <div  data-tab="con" >   
            	<div class="tabtable_con">
                    <div class="new_r_compile">
                        <span class="new_fctbox">
                            <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
                          </span>
                    </div>  
                    	访谈记录  
                </div>                 
            </div>
            
            
            
            
            <!--tab end-->
          </div>
        </div>
        
        
        <!--右边-->
        <jsp:include page="./includeRight.jsp" flush="true"></jsp:include>
        
    </div>
 
</div>


<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="js/axure.js" type="text/javascript"></script>
<script src="js/axure_ext.js" type="text/javascript"></script>
<script>
	$('[data-ｏｎ="compile"]').on('click',function(){
		$('.bj_hui_on').show();
		$('.compile_on').show();
	})
	$('[data-ｏｎ="close"]').on('click',function(){
		$('.bj_hui_on').hide();
		$('.compile_on').hide();
	})

</script>
</html>
