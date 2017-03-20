<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<li data-tab="navInfo" class="fl h_nav1 active" onclick="tabInfoChange('0')">基本<br />信息 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('1')">项目</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('2')">团队</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('3')">运营<br />数据 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('4')">竞争</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('5')">战略及<br />策略 </li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('6')">财务</li>
		<li data-tab="navInfo" class="fl h_nav2" onclick="tabInfoChange('7')">法务</li>
		<li data-tab="navInfo" class="fl h_nav1" onclick="tabInfoChange('8')">融资及<br />估值 </li>
	</ul>


	<div id="tab-content">
		<div class="tabtxt" id="page_all"> 
		
			<div class="h radius" id="NO1_1">
			</div>
			
			<div class="h radius" id="NO1_2">
			</div>
		</div>
	</div>








<script>
	
	//1:文本、2:单选、3:复选、4:级联选择、5:单选带备注、6:复选带备注、7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据
	
	// 1:文本
	function type_1_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd>未填写</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			hresult = "<dd data-rid="+results[0].id+" >"+results[0].contentDescribe1+"</dd>";
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	// 2:单选
	function type_2_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd>未选择</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			hresult = "<dd data-rid="+results[0].id+" >"+results[0].valueName+"</dd>";
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	// 3:复选、4:级联选择
	function type_3_4_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd>未选择</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			$.each(results,function(i,o){
				hresult +=  "<dd data-rid="+this.id+" >"+this.valueName+"</dd>";
			});
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	
	//5:单选带备注
	function type_5_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		
		var hresult_1 = "<dd>未选择</dd>";
		var hresult_2 = "<dd>未填写</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			if(results[0].valueName){
				hresult_1 = "<dd data-rid="+results[0].id+" >"+results[0].valueName+"</dd>";
			}
			if(results[0].contentDescribe1){
				hresult_2 = "<dd >"+results[0].contentDescribe1+"</dd>";
			}
		}

		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult_1 + hresult_2 + "</div>";
	}
	
	
	
	//6:复选带备注
	function type_6_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		
		var hresult_1 = "<dd>未选择</dd>";
		var hresult_2 = "<dd>未填写</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			
			var hasC = false;
			var cr = "";
			$.each(results,function(i,o){
				if(this.valueName){
					hasC = true;
					cr +=  "<dd >"+this.valueName+"</dd>";
				}
			});
			if(hasC == true){
				hresult_1 = cr;
			}
			
			if(results[0].contentDescribe1){
				hresult_2 = "<dd >"+results[0].contentDescribe1+"</dd>";
			}
		}

		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult_1 + hresult_2 + "</div>";
	}
	
	
	// 7:附件
	function type_7_html(title){
		
		return  "<dt class=\"fl_none\"> 除去非主营业务外，运营数据曲线变化（细分项目、拆分到年度、月度、周、日）：</dt>" +
				"<dd class=\"fl_none\">" +
					"<img src=\"img/loginbg.gif\" alt=''>" +
					"<img src=\"img/loginbg.gif\" alt=''>" +
				"</dd>";
	}
	
	
	
	// 8:文本域
	function type_8_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd>未填写</dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			hresult = "<dd data-rid="+results[0].id+" class=\"fl_none\" >"+results[0].contentDescribe1+"</dd>";
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	
	// 11:文本
	function type_11_html(title){
		
		var htitle = "<dt data-tid="+title.id+" data-tcode="+title.code+">"+title.name+"</dt>";
		var hresult = "<dd></dd>";
		
		var results = title.resultList;
		if(results && results[0] && results[0].id){
			hresult = "<dd data-rid="+results[0].id+" >"+results[0].contentDescribe1+"</dd>";
		}else{
			console.log("projectInfo: " + projectInfo);
			switch (title.code) {
	            case "NO1_1_1":  //项目编号
	            	hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
	                break;
	            case "NO1_1_2":  //项目负责人
	            	hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
	                break;
	            case "NO1_1_3":   //项目合伙人
	            	hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
	                break;
	            case "NO1_1_4":   //隶属事业部
	            	hresult = "<dd>"+ projectInfo.projectCode +"</dd>";
	                break;
	            default:
	                break;
	        }
		}
		
		return  "<div class=\"mb_24 clearfix\">" + htitle + hresult + "</div>";
	}
	
	
	
	
	function switchType(title){
		var html = "";
		switch (title.type) {
	        case 1:  
	        	html += type_1_html(title);
	            break;
	        case 2:  
	        	html += type_2_html(title);
	            break;
	        case 3:   
	        	html += type_3_4_html(title);
	            break;
	        case 4:   
	        	html += type_3_4_html(title);
	            break;
	        case 5:  
	        	html += type_5_html(title);
	            break;
	        case 6:  
	        	html += type_6_html(title);
	            break;
	        case 7:   
	        	html += type_7_html(title);
	            break;
	        case 8:   
	        	html += type_8_html(title);
	            break;
	        case 9:  
	        	//html += type_9_html(title);
	            break;
	        case 10:  
	        	//html += type_10_html(title);
	            break;
	        case 11:   
	        	html += type_11_html(title);
	            break;
	        default:
	            break;
	    }
		return html;
	}
	
	
	function toGetShowHtml(title){
		var tilelist = title.childList;
		var html = "";
		$.each(tilelist,function(i,o){
			if(this.sign  && this.sign == 3){
				sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/" + this.code, null, function(data) {
					var result = data.result.status;
					if (result == 'OK') {
						var sign_title = data.entity;
						html += toGetShowHtml(sign_title);
					}
				});
			}else{
				html += switchType(this);
			}
		});
		return html
	}
	
	
	//页面显示
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/NO1_1", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			console.log(entity);
			var html = toGetShowHtml(entity);
			$("#NO1_1").html(html);
		}
	});
	
	//页面显示
	sendGetRequest(platformUrl.queryProjectAreaInfo + pid +"/NO1_2", null, function(data) {
		var result = data.result.status;
		if (result == 'OK') {
			var entity = data.entity;
			console.log(entity);
			var html = toGetShowHtml(entity);
			$("#NO1_2").html(html);
		}
	});
	
	
</script>

</body>


</html>
