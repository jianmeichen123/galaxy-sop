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
	//$(".projectmsg_d .block").today();
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
		var $self = $(this);
		var	_name = $self.attr("data-btn");
		var	_parent = $self.parent();
		//点击编辑 
		if(_name=="edit"){
	        _parent.siblings("."+_name).show();
			_parent.parent().parent('.block2').removeClass('shadow');	        
	        $(".edui-body-container").focus();
			$self.hide();
			$self.siblings().hide();
			$self.siblings("[data-btn='submit'],[data-btn='reset']").show();	
		}
		//点击收起
		if(_name=="hide"){
			//关闭展开层
			_parent.siblings("dd").hide();
			_parent.parent().parent('.block2').addClass('shadow');			
			$self.hide();
			$self.siblings().hide();
			$self.siblings("[data-btn='describe'],[data-btn='edit']").show();
		}
		//点击查看详情
		if(_name=="describe"){
			_parent.siblings("."+_name).show();
			_parent.parent().parent('.block2').removeClass('shadow');			
			$self.hide();
			$self.siblings().hide();
			$self.siblings("[data-btn='hide']").show();
		}
		
		//点击取消保存
		if(_name=="reset"){
			_parent.siblings("dd").hide();
			_parent.parent().parent('.block2').addClass('shadow');			
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
	$(".project_analysis").tabchange2();
	$(".chartbox_bottom").tabchange1();
	$(".ytxm_block").tabchange2();

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
			okback:function(){
				/*if (_url.indexOf("?") != -1){
					var index = _url.indexOf("?");
					var str =_url.substring(index+1);
					alert(str)
					if (str =="type=1") {
						showList();
					} else {
						//投诀会
						showList1();
					}
					
				}else {
					showList();
				}*/
				showList();
				}//模版反回成功执行	
		});
		return false;
	});
	
	$("[data-btn='vote']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				showList1();
				}//模版反回成功执行	
		});
		return false;
	});
	//ceo秘书排期弹窗
	$("[data-btn='ceops']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				showList2();
				}//模版反回成功执行	
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
				initMeetUpload();
				queryMeetPerPro();
				$('.edui-container').show();
			}//模版反回成功执行	
		});
		return false;
	});
	//新增访谈记录弹窗
	$("[data-btn='interview']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				initViewUpload();
				queryViewPerPro();
				$('.edui-container').show();
			}//模版反回成功执行	
		});
		return false;
	});
	//个人简历弹窗
	$("[data-btn='resume']").on("click",function(){ 
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".resumetc .tabtable").tabchange2();
				$(".rating-kv").rating();
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
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$(".myprojecttc .tabtable").tabchange();
			}//模版反回成功执行	
		});
		return false;
	});	
	
	//修改密码验证弹窗
	$("[data-btn='login_infor']").on("click",function(){
		$('.pop').remove();
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				var test = getQueryString(_url,"realName");
				
				$('.register_all_two1').html(getQueryString(_url,"realName"));
				$('.register_all_two2').html(getQueryString(_url,"deptName"));
				$('.register_all_two3').html(getQueryString(_url,"roleName"));
				//$("#hid").href=getUrl(_url);
				
				$("[data-btn='change_password']").on("click",function(){
					$('.pop').remove();
					var $self = $(this);
					var _url = $self.attr("href");
					$.getHtml({
						url:_url,//模版请求地址
						data:"",//传递参数
						okback:function(){
							//$(this).change_password();
						}//模版反回成功执行	
					});
					return false;
				});	
			}//模版反回成功执行	
		});
		return false;
	});
	
	// 创意弹层
	$("[data-btn='creative']").on("click",function(){ 
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
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
				$("[data-btn='abandon']").on("click",function(){
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
				$("[data-btn='edit_name']").on("click",function(){
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
				$("[data-btn='edit']").on("click",function(){
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
				$("[data-btn='claim']").on("click",function(){
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
				$(".creativetc .tabtable").tabchange2();
				$('#project_name').click(function(){
					/*$('.block').css({
						display: 'none',
					});*/
					$('.block').css("display","none");
					$(".aa").show();
					$('.tablink li').eq(0).addClass('on').siblings().removeClass('on');
				});
				$('#project_name').click(function(){
					$(".tabtable_con .block").eq(0).show().siblings().hide();
					$('.tablink li').eq(0).addClass('on').siblings().removeClass('on');
				});
				$("[data-btn='create']").on("click",function(){
					$(".tabtable_con .block").eq(3).show().siblings().hide();
					$('.tablink li').eq(3).addClass('on').siblings().removeClass('on');
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
			}//模版反回成功执行	
		});
		return false;
	});
	
	//日程列表
	//修改密码验证弹窗
	$("[data-btn='shecudle_list']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(date){				
				$('#shecudle_list').bootstrapTable({
					method: 'post',
					url: platformUrl.shecudle_list,
					height: $(window).height() - 200,
					striped: true,
					dataType: "json",
					pagination: true,
					"queryParamsType": "limit",
					singleSelect: false,
					contentType: "application/x-www-form-urlencoded"
				});
			}//模版反回成功执行	
		});
		return false;
	});
	
	/*//实际拨款信息列表
	$("[data-btn='health_case']").on("click",function(){ 
		var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$("#popup_name").html(_name);
			}//模版反回成功执行	
		});
		return false;
	});*/
	

	
	
	//编辑分期拨款计划
	$("[data-btn='actual_aging']").on("click",function(){ 
		var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$("#popup_name").html(_name);
			}//模版反回成功执行	
		});
		return false;
	});
	
});

function getQueryString(url,name){

     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");

     var r = url.substr(1).match(reg);

     if(r!=null)return  unescape(r[2]); return null;

}

function getUrl(_url) {
	var location = (_url+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
	var url = basePath + '/html/change_password.html'; 
	return url;
}
function GetRequest(url) { 
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) { 
	var str = url.substr(1); 
	strs = str.split("&"); 
	for(var i = 0; i < strs.length; i ++) { 
	theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
	} 
	} 
	return theRequest; 
	} 
