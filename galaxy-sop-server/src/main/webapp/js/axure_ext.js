// JavaScript Document
$(function(){
	//日历
	$("[data-date='time']").on("focus",function(){
		var $this = $(this);
		if($(".calendar").length>0){return false;}
		$("body").append($("#calendar").val());
		$(".calendar").calendar({
			stamp:+(new Date($this.val())),
			callback:function(date){
				var _date = date.split(",");
				for(var i=0,len=_date.length;i<len;i++){
					if(parseInt(_date[i])<10){
						_date[i] = "0"+_date[i]
					}	
				}
				$this.val(_date.join("-"));
				$(".calendar").remove();
			}
		});
		$(".calendar").css({
			top:$this.offset().top+$this.outerHeight(true),
			left:$this.offset().left-($(".calendar").outerWidth(true)-$this.outerWidth(true)),
			display:"block"
		})
		$(".calendar").closeDom(function(){
			$(".calendar").remove();	
		})
	});
	
	//日程时间
	$(".pagebox .rit .top .tody").today();
	//柱状图
	$(".histogram").histogram();
	//表格滑过
	$("table tr").changeClass();
	//label滑过
	$(".dictionary .tabbox .scroll label").changeClass({
		outback:function(){
			if($(this).find("input").is(":checked")){
				$(this).addClass("on");
			}
		}
	});
	//上传组件显示值
	$("body").on("change",".fmload .load",function(){
		var $self = $(this),
			_val = $self.val();
		$self.siblings(".loadname").html(_val);
	});
	//盒子展开隐藏
	$(".fctbox a").on("click",function(){
		var $self = $(this),
			_name = $self.attr("data-btn"),
			_parent = $self.parent();
		//点击收起
		if(_name=="hide"){
			//关闭展开层
			_parent.siblings("dd").hide();
			$self.hide();
			$self.siblings().hide();
			$self.siblings("[data-btn='describe'],[data-btn='edit']").show();
		}
		//点击查看详情
		if(_name=="describe"){
			_parent.siblings("."+_name).show();
			$self.hide();
			$self.siblings().hide();
			$self.siblings("[data-btn='hide']").show();
		}
		//点击编辑 
		if(_name=="edit"){
			_parent.siblings("."+_name).show();
			$self.hide();
			$self.siblings().hide();
			$self.siblings("[data-btn='submit'],[data-btn='reset']").show();	
		}
		//点击取消保存
		if(_name=="reset"){
			_parent.siblings("dd").hide();
			$self.hide();
			$self.siblings().hide();
			$self.siblings("[data-btn='describe'],[data-btn='edit']").show();	
		}
		return false;
	});
	//选项卡
	$(".dictionary").tabchange();
	$(".news").tabchange();
	$(".assessment").tabchange();
	$(".project_analysis").tabchange();

	//------------------------------弹窗部分
		//添加团队成员信息弹窗
	$("[data-btn='addmen']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
			//工作经历息弹窗
	$("[data-btn='workexprice']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
			//学习经历弹窗
	$("[data-btn='studyexprice']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
			//添加股权结构弹窗
	$("[data-btn='stock']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
			//上传模板弹窗
	$("[data-btn='muban']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
			//用户信息弹窗
	$("[data-btn='userinfro']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
			//发邮件弹窗
	$("[data-btn='email']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
	//认领弹窗
	$("[data-btn='claim']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
	//档案上传弹窗
	$("[data-btn='archives']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
	//添加日程弹窗
	$("[data-btn='schedule']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
		//添加日程新弹窗
	$("[data-btn='schedule1']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
	//立项会弹窗
	$("[data-btn='project']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
	//新增会议弹窗
	$("[data-btn='meeting']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				
			}//模版反回成功执行	
		});
		return false;
	});
	//人员信息弹窗
	$("[data-btn='resume']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
			}//模版反回成功执行	
		});
		return false;
	});
		//项目数统计弹窗
	$("[data-btn='xmstj']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
			//过会率统计弹窗
	$("[data-btn='ghltj']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
			//投决率统计弹窗
	$("[data-btn='tjltj']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		});
		return false;
	});
	//这里是例子
	$("[data-btn='myproject']").on("click",function(){
		//这里如果是复选框 你需要判断是否为选中
		//if(!$(this).is(":checked")){return false;}
		var $self = $(this);
		var _url = $self.attr("data-href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".myprojecttc .tabtable").tabchange();
			}//模版反回成功执行	
		});
		return true;
	});	


});