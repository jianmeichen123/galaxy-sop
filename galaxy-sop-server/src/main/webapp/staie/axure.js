// JavaScript Document
(function($){
	//定位
	$.fn.Fixed = function(options) {
		var opts = $.extend({
			x: 0,
            y: 0
        }, options);
		var isIe6 = false;close
		if(window.ActiveXObject)//判断浏览器是否属于IE
		{
			var browser=navigator.appName
			var b_version=navigator.appVersion
			var version=b_version.split(";");
			var trim_Version=version[1].replace(/[ ]/g,"");
			if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0"){
				isIe6 = true;	
			}
		}
        if (isIe6) {
			var $html =  $("html");
			($html.css("backgroundAttachment") !== "fixed") && ($html.css("backgroundAttachment", "fixed"));
			($html.css("backgroundImage")=="none") && ($html.css("backgroundImage", "url(about:blank)"));
        };
        return this.each(function() {
			var $this = $(this),_this = this;
			if (isIe6){
				$this.css("position", "absolute");
				_this.style.setExpression("left", 'eval(document.documentElement.scrollLeft + ' + opts.x + ') + "px"');
				_this.style.setExpression("top", 'eval(document.documentElement.scrollTop + ' + opts.y + ') + "px"');
			}else{
				$this.css({
					position:"fixed",
					left:opts.x,
					top:opts.y
				});
			};
        });
    };
	/*
		弹出层{
			w:弹出层宽度
			tit:弹出层标题
			txt:弹出层内容模版
			callback:弹出后执行	
		}
	*/
	$.popup = function(options){
		var opts = $.extend({
			txt:"",
			showback:function(){},
			hideback:function(){}
		},options);
		function popEve(){
			this.strbg = "<div id=\"popbg\"><iframe frameborder=\"0\" src=\"about:blank\"></iframe></div>";
			this.strbg01 = "<div id=\"popbg01\"></div>";
			this.strpop = "<div id=\"powindow\" class=\"pop\"  data-id=\"popid"+$(".pop").length+"\"><a href=\"javascript:;\" data-close=\"close\" class=\"close null\">关闭</a><div class=\"poptxt\"><p class='popwait'>数据加载中，请稍候...</p></div></div>";
			this.txt = opts.txt;//弹层添加数据
			this.statusmove = true;//移动状态标识
			this.mousexy = {};//存放鼠标xy容器
			//显示出来之后执行
			this.showback = opts.showback;
			//隐藏后执行
			this.hideback = opts.hideback;
			this.id = "[data-id='popid"+$(".pop").length+"']";
		}
		popEve.prototype = {
			init:function(){
				var _this = this;
					_this.insertbg().inserttxt().postionEve().closepop();

			},
			//背景插入
			insertbg:function(dom){
				var _this = this;
				if($("#popbg").length==0){
					$("body").append(_this.strbg);
				}
				if($(".pop").length>0){
					$("body").append(_this.strbg01);
				}
				return _this;
			},
			//内容插入
			inserttxt:function(){
				var _this = this;
				//插入弹窗外部皮肤
				$("body").append(_this.strpop);
				//插入内容
				$(_this.id).children(".poptxt").html(_this.txt);
				//清楚zindex
				$(".pop").removeClass("popzx");
				//对外接口
				_this.showback.apply(_this);
				return _this;	
			},
			//弹层居中定位显示
			postionEve:function(){
				var _this = this;
				var wh = parseInt($(_this.id).outerWidth(true)),
					ht = parseInt($(_this.id).outerHeight(true));
				var win_w = $(window).width(),
					win_h = $(window).height(),
					win_x = (win_w-wh)/2,
					win_y = (win_h-ht)/2;
				//背景设置高度+显示
				$("#popbg,#popbg iframe").css({"height":win_h});
				$("#popbg01").css({"height":win_h});
				$("#popbg").show().animate({
					opacity:0.5
				},300);
				$("#popbg01").show().animate({
					opacity:0.5
				},0);
				//弹出层定位+显示
				$(_this.id).Fixed({
					x:win_x,
					y:win_y
				});
				$(_this.id).animate({
					opacity:1
				},400);
				return _this;	
			},
			//鼠标拖动
			mousedrag:function(){
				var _this = this;
				$(_this.id).on("dblclick",function(){
					var $self = $(this);
					//弹层zindex
					$(".pop").removeClass("popzx");
					$self.addClass("popzx");
					//移动状态
					_this.statusmove = true;
					$self.on("mousemove",function(e){
						if(!_this.statusmove){ return false;}
						var e = e||window.event;
						var x=e.pageX||e.clientX; //鼠标移动时获取x轴坐标
						var y=e.pageY||e.clientY; //鼠标移动时获取y轴坐标
						if(_this.mousexy.x==null){
							_this.mousexy.x = x;
						}
						if(_this.mousexy.y==null){
							_this.mousexy.y = y;
						}
						$self.Fixed({
							x:parseInt($self.css("left"))+(x-_this.mousexy.x),
							y:parseInt($self.css("top"))+(y-_this.mousexy.y)
						});
						_this.mousexy.x = x;
						_this.mousexy.y = y;
					});	
				});
				//接触绑定move
				$(_this.id).on("mouseup mouseleave",function(){
					_this.statusmove = false;
					delete _this.mousexy.x;
					delete _this.mousexy.y;
				});
				return _this;	
			},
			closepop:function(){
				var _this = this;
				$(_this.id).on("click","[data-close='close']",function(){
					if($("#popbg01").length>0){
						$("#popbg01").remove();
						 $(document.body).css({
							   "overflow-x":"hidden",
							   "overflow-y":"hidden"
							 });
					}else{
						//启用滚动条
						 $(document.body).css({
						   "overflow-x":"auto",
						   "overflow-y":"auto"
						 });
					}
						//关闭对外接口
						_this.hideback.apply(_this);
						$(_this.id).remove();
						$('.tip-yellowsimple').hide();
						//判断是否关闭背景
						if($(".pop").length==0){
							$("#popbg").hide();
							$('.tip-yellowsimple').hide();	//表单验证提示关闭
						}
						if($(".pop").length==0){
							
							$("#popbg01").remove();
						}
						return false;
				});
				return _this;
			}
		};
		var obj = new popEve();
		obj.init();
	};
	//屏幕开屏
	$.locksCreenOpen =function(){
		$(document.body).css({
		   "overflow-x":"auto",
		   "overflow-y":"auto"
		 });
	}
	//一次弹窗关闭
	$.popupOneClose =function(){
		$('div[data-id="popid0"]').remove();
		$("#popbg").remove();
	};
	//二次弹窗关闭
	$.popupTwoClose =function(){
		$('div[data-id="popid1"]').remove();
		$("#popbg01").remove();
	};
	//屏幕开屏
	$.locksCreenOpen =function(){
		$(document.body).css({
		   "overflow-x":"auto",
		   "overflow-y":"auto"
		 });
	}
	//切换样式控制
	$.fn.changeClass = function(options){
		if($(this).length==0){ return false};
		var $this = $(this);
		var opts = $.extend({
			calssName:"on",
			onback:function(){},
			outback:function(){}
		},options);
		$("body").on("mouseenter",$this.selector,function(){
			$(this).addClass(opts.calssName);
			opts.onback.apply(this);
		});
		$("body").on("mouseleave",$this.selector,function(){
			$(this).removeClass(opts.calssName);
			opts.outback.apply(this);
		});
	};
	//展开关闭控制
	$.fn.toggleshow = function(options){
		if($(this).length==0){ return false};
		var opts = $.extend({
			showCallback:function(t){},
			hideCallback:function(t){}
		},options);
		return $(this).each(function(index){	
			var $this = $(this);
			 $this.find("[data-btn='show']").on("click",function(){
				$this.find("[data-box='box']").show();
				opts.showCallback($this.find("[data-box='box']"));
				return false;
			});
			 $this.find("[data-btn='hide']").on("click",function(){
				 $this.find("[data-box='box']").hide();
				opts.hideCallback($this.find("[data-box='box']"));
				return false;
			});
		});
	};
	//点击空白关闭
	$.fn.closeDom = function(fn){
		if($(this).length==0){return false;}
		var $this = $(this);
		var documentHide = function(event){
			var whichOne = event.which,targetOne = event.target;
			var oneBox = $this.has($(targetOne)).length == 0?false:true;
			(whichOne==1||whichOne==0)&&!oneBox?$this.hide():false;
			//fn();
		};
		$(document).on("mousedown",documentHide);	
	}
	/*
		tab卡{
			defaultnum: 默认第几个显示	
		}
		使用规则
		占用属性{
			data-tab="nav",
			data-tab="con":座位导航和内容集合
			data-tab="suffix":下标标识	
		}
	*/
	$.fn.tabchange = function(options){
		if($(this).length==0) return false;
		var opts = $.extend({
			//defaultnum:0,   // report no ‘//’
			onClass:"on",
			eventType:"click",
			movetime:300,
			onchangeSuccess:function(data){}
		},options);
		function tab(t){
			this.nav = t.find("[data-tab='nav']");
			this.onclass = opts.onClass;
			this.con = t.find("[data-tab='con']");
			this.suffix = t.find("[data-tab='suffix']");
			this.num = opts.defaultnum;
			this.time = opts.movetime;
		};
		tab.prototype = {
			seton : function(){
				var _this = this;
				_this.nav.removeClass(_this.onclass);
				_this.con.hide();
				_this.nav.eq(_this.num).addClass(_this.onclass);
				_this.con.eq(_this.num).show();	
				_this.setsuffix();
			},
			setsuffix : function(n){
				if(this.suffix.length==0) return false;
				var _this = this,
					_width = _this.suffix.width();;
				_this.suffix.stop(true).animate({
					"left" : _width*this.num	
				},_this.time);
			}
		};
		return $(this).each(function() {
            var $this = $(this);
			var obj = new tab($this);
			obj.seton();
			//事件执行
			obj.nav.on(opts.eventType,function(){
				obj.num = $(this).index();
				obj.seton();
				opts.onchangeSuccess.apply(this,[obj.num]);
			});
        });
	};	
	$.fn.tabchange1 = function(options){
		if($(this).length==0) return false;
		var opts = $.extend({
			defaultnum:0,
			onClass:"on",
			eventType:"click",
			movetime:300
		},options);
		function tab(t){
			this.nav = t.find("[data-tab='nav1']");
			this.onclass = opts.onClass;
			this.con = t.find("[data-tab='con1']");
			this.suffix = t.find("[data-tab='suffix']");
			this.num = opts.defaultnum;
			this.time = opts.movetime;
		};
		tab.prototype = {
			seton : function(){
				var _this = this;
				_this.nav.removeClass(_this.onclass);
				_this.con.hide();
				_this.nav.eq(_this.num).addClass(_this.onclass);
				_this.con.eq(_this.num).show();	
				_this.setsuffix();
			},
			setsuffix : function(n){
				if(this.suffix.length==0) return false;
				var _this = this,
					_width = _this.suffix.width();;
				_this.suffix.stop(true).animate({
					"left" : _width*this.num	
				},_this.time);
			}
		};
		return $(this).each(function() {
            var $this = $(this);
			var obj = new tab($this);
			obj.seton();
			//事件执行
			obj.nav.on(opts.eventType,function(){
				obj.num = $(this).index();
				obj.seton();
			});
        });
	};	
	$.fn.tabchange2 = function(options){
		if($(this).length==0) return false;
		var opts = $.extend({
			defaultnum:0,
			onClass:"on",
			eventType:"click",
			movetime:300,
			onchangeSuccess:function(data){}
		},options);
		function tab(t){
			this.nav = t.find("[data-tab='nav']");
			this.onclass = opts.onClass;
			this.con = t.find("[data-tab='con']");
			this.suffix = t.find("[data-tab='suffix']");
			this.num = opts.defaultnum;
			this.time = opts.movetime;
		};
		tab.prototype = {
			seton : function(){
				var _this = this;
				_this.nav.removeClass(_this.onclass);
				_this.con.hide();
				_this.nav.eq(_this.num).addClass(_this.onclass);
				_this.con.eq(_this.num).show();	
				_this.setsuffix();
			},
			setsuffix : function(n){
				if(this.suffix.length==0) return false;
				var _this = this,
					_width = _this.suffix.width();;
				_this.suffix.stop(true).animate({
					"left" : _width*this.num	
				},_this.time);
			}
		};
		return $(this).each(function() {
            var $this = $(this);
			var obj = new tab($this);
			obj.seton();
			//事件执行
			obj.nav.on(opts.eventType,function(){
				obj.num = $(this).index();
				obj.seton();
				opts.onchangeSuccess.apply(this,[obj.num]);
			});
        });
	};
	$.fn.tabchange3 = function(options){
		if($(this).length==0) return false;
		var opts = $.extend({
			defaultnum:1,
			onClass:"on",
			eventType:"click",
			movetime:300,
			onchangeSuccess:function(data){}
		},options);
		function tab(t){
			this.nav = t.find("[data-tab='nav']");
			this.onclass = opts.onClass;
			this.con = t.find("[data-tab='con']");
			this.suffix = t.find("[data-tab='suffix']");
			this.num = opts.defaultnum;
			this.time = opts.movetime;
		};
		tab.prototype = {
			seton : function(){
				var _this = this;
				_this.nav.removeClass(_this.onclass);
				_this.con.hide();
				_this.nav.eq(_this.num).addClass(_this.onclass);
				_this.con.eq(_this.num).show();	
				_this.setsuffix();
			},
			setsuffix : function(n){
				if(this.suffix.length==0) return false;
				var _this = this,
					_width = _this.suffix.width();;
				_this.suffix.stop(true).animate({
					"left" : _width*this.num	
				},_this.time);
			}
		};
		return $(this).each(function() {
            var $this = $(this);
			var obj = new tab($this);
			obj.seton();
			//事件执行
			obj.nav.on(opts.eventType,function(){
				obj.num = $(this).index();
				obj.seton();
				opts.onchangeSuccess.apply(this,[obj.num]);
			});
        });
	};
	$.fn.tabchange5 = function(options){
		if($(this).length==0) return false;
		var opts = $.extend({
			//defaultnum:0,
			onClass:"on",
			eventType:"click",
			movetime:300,
			onchangeSuccess:function(data){}
		},options);
		function tab(t){
			this.nav = t.find("[data-tab='nav']");
			this.onclass = opts.onClass;
			this.con = t.find("[data-tab='con']");
			this.suffix = t.find("[data-tab='suffix']");
			this.num = opts.defaultnum;
			this.time = opts.movetime;
		};
		tab.prototype = {
			seton : function(){
				var _this = this;
				_this.nav.removeClass(_this.onclass);
				_this.con.hide();
				_this.nav.eq(_this.num).addClass(_this.onclass);
				_this.con.eq(_this.num).show();	
				_this.setsuffix();
			},
			setsuffix : function(n){
				if(this.suffix.length==0) return false;
				var _this = this,
					_width = _this.suffix.width();;
				_this.suffix.stop(true).animate({
					"left" : _width*this.num	
				},_this.time);
			}
		};
		return $(this).each(function() {
            var $this = $(this);
			var obj = new tab($this);
			obj.seton();
			//事件执行
			obj.nav.on(opts.eventType,function(){
				obj.num = $(this).index();
				obj.seton();
				opts.onchangeSuccess.apply(this,[obj.num]);
			});
        });
	};	
	//日期和时间
	$.fn.today = function(options){
		if($(this).length==0){return false;}
		var $this = $(this);
		var opts = $.extend({
			time:".time",
			date:".date"	
		},options);
		var _time = $this.find(opts.time),
			_date = $this.find(opts.date);
		function checkTime(i){
		  if(i<10){i="0"+ i}
		  return i
		}
		function week(i){
			switch(i){
				case 0:
					return "周末";
				break;	
				case 1:
					return "周一";
				break;	
				case 2:
					return "周二";
				break;	
				case 3:
					return "周三";
				break;	
				case 4:
					return "周四";
				break;	
				case 5:
					return "周五";
				break;	
				case 6:
					return "周六";
				break;	
			}	
		}
		function getdate(){
			var nowDate  = new Date(),
				nyr = nowDate.getFullYear()+"年"+(nowDate.getMonth()+1)+"月"+nowDate.getDate()+"日&nbsp;"+week(nowDate.getDay()),
				sfm = checkTime(nowDate.getHours())+":"+checkTime(nowDate.getMinutes())+":"+checkTime(nowDate.getSeconds());
			_time.html(sfm);
			_date.html(nyr);
			window.setTimeout(getdate,500);
		}
		getdate();
	};		
	//柱状图
	$.fn.histogram = function(){
		if($(this).length==0){ return false}
		function showEve(t){
			this.t = t;
			this.height = this.t.height();
			this.ul = this.t.children("ul");
			this.ulist = this.ul.children();
			this.ulen = this.ulist.length;
			this.ol = this.t.children("ol");
			this.olist = this.ol.children();
			this.olen = this.olist.length;
			//获取最大值
			this.maxNum = parseInt(this.olist.eq(0).children().html());
			//细分高度
			this.every = (this.height/this.maxNum).toFixed(2);
		}
		showEve.prototype = {
			init:function(){
				var _this = this;
				if(_this.ulen==0){return false;}
				_this.setbg().setwidth().pace();
			},
			//设置背景
			setbg:function(){
				var _this = this;
				_this.olist.each(function(){
					var $self = $(this),
						$num = parseInt($self.children().html());
					var _heght = $num*_this.every;
					$self.stop(true).animate({
						height:_heght
					},{ 
						easing: 'linear', 
						duration: 500
					})
				});
				return _this;
			},
			//设置柱状图宽度和间隔
			setwidth:function(){
				var _this = this;
				var _width = parseFloat(100/(_this.ulen*2+1)).toFixed(2);
				_this.ulist.css("width",_width+"%");
				var num = 1;
				_this.ulist.each(function(index){
					var $self = $(this);
					var _left = _width*(index+num);
					num++;
					$self.css("left",_left+"%").children().css("display","block");
				});
				return _this;
			},
			//柱状图展示
			pace:function(){
				var _this = this;
				_this.ulist.each(function(index){
					var $self = $(this),
						_pace = parseInt($self.attr("data-pace"))*_this.every;
					$self.stop(true).animate({
						height:_pace
					},{ 
						easing: 'linear', 
						duration: 500
					})
				});
				return _this;	
			}
		}
		return $(this).each(function(){
			var obj = new showEve($(this));
			obj.init();
		});
	};
	/*获取html模版弹窗
	$.getHtml = function(options){
		var opts = $.extend({
			url:"",//模版请求地址
			data:"",//传递参数
			okback:function(){},//模版反回成功执行	
			showback:function(){
				var _this = this;
				$.ajax({
					type:"GET",
					data:opts.data,
					dataType:"html",
					url:opts.url,
					beforeSend : function(xhr) {
						//锁定滚动条
						$(document.body).css({
						   "overflow-x":"hidden",
						   "overflow-y":"hidden"
						 });
						//清楚浏览器缓存
						xhr.setRequestHeader("If-Modified-Since","0"); 
						xhr.setRequestHeader("Cache-Control","no-cache");
						if (sessionId) {
							xhr.setRequestHeader("sessionId", sessionId);
						}
						if(userId){
							xhr.setRequestHeader("guserId", userId);
						}
					},
					success:function(html){
						$(_this.id).find(".poptxt").html(html);
						opts.okback(_this);
						//重新定位
						_this.postionEve();
					},
					error:function(){
					}	
				})
			}
		},options);
		//拉取静态模版
		$.popup(opts);
	};
	*/
	//====report
	$.getHtml = function(options){
		var opts = $.extend({
			url:"",//模版请求地址
			data:"",//传递参数
			okback:function(){}//模版反回成功执行	
		},options);
		//拉取静态模版
		$.popup({
			showback:function(){
				var _this = this;
				$.ajax({
					type:"GET",
					data:opts.data,
					dataType:"html",
					url:opts.url,
					beforeSend : function(xhr) {
						/**清楚浏览器缓存**/
						xhr.setRequestHeader("If-Modified-Since","0"); 
						xhr.setRequestHeader("Cache-Control","no-cache");
						
					},
					success:function(html){
						$(_this.id).find(".poptxt").html(html);
						opts.okback(_this);
						//重新定位
						_this.postionEve();
						$("body").css("overflow-y","hidden");
						//浏览器大小改变，弹窗重新定位
						$(window).resize(function(){  
							_this.postionEve();
							if($("#powindow").length==0){
								$("#popbg").remove();
							}
						})
					},
					error:function(){
					}	
				})
			}	
		});
	};
	
	
		/*
		Tab弹出层{
			w:弹出层宽度
			tit:弹出层标题
			txt:弹出层内容模版
			callback:弹出后执行	
			}
		 */
		$.tabup = function(options){
			var opts = $.extend({
				txt:"",
				showback:function(){},
				hideback:function(){}
			},options);
			function popEve(){
				this.strpopTab="<div id=\"tab-content\" data-id=\"tab-content\"><div class=\"tabtxt\"></div></div>"
				this.txt = opts.txt;//弹层添加数据
				//显示出来之后执行
				this.showback = opts.showback;
				//隐藏后执行
				this.hideback = opts.hideback;
				this.id = "[data-id='tab-content']";
			}
			popEve.prototype = {
				init:function(){
					var _this = this;
					$("#tab-content").remove();
					_this.inserttxt();
		//			$("#tab-content").hide();
		//			$("#tab-content").animate({left: '0px'}, 1000);  	
		
				},
				//内容插入
				inserttxt:function(){
					var _this = this;
					//插入弹窗外部皮肤
					$("[data-id='tab-block']").append(_this.strpopTab);
					//插入内容
					$(_this.id).children(".tabtxt").html(_this.txt);
					//对外接口
					_this.showback.apply(_this);
					return _this;	
				},
			};
			var obj = new popEve();
			obj.init();
		};
		
		
		/*获取html模版弹窗*/
		$.getTabHtml = function(options){
			var opts = $.extend({
				url:"",//模版请求地址
				data:"",//传递参数
				okback:function(){}//模版反回成功执行	
			},options);
			//拉取静态模版
			$.tabup({
				showback:function(){
					var _this = this;
					$.ajax({
						type:"GET",
						data:opts.data,
						dataType:"html",
						url:opts.url,
						cache:false,
						beforeSend: function () {
							var imgDiv='<div style="margin-top:50px;"><center><img src="'+Constants.sopEndpointURL+'img/pc_color.gif">'+'</img></center></div>';
							$(_this.id).find(".tabtxt").html(imgDiv);
						},
						success:function(html){
							var flag=$(html).find('form').find('#flagLogin').val();
							if(typeof(flag)!=="undefined"&&flag=="login"){
								window.location.href=endpointObj["galaxy.project.platform.endpoint"] +"/galaxy/userlogin/toLogin";
								return;
							}
							$(_this.id).find(".tabtxt").html(html);
							opts.okback();
						},
						error:function(){
						}	
					})
				}	
			});
		};	
		
		
		$.fn.tabLazyChange = function(options){
			if($(this).length==0) return false;
			var defaultnum = options.defaultnum;
			if(!defaultnum){
				defaultnum = 0;
			}
			var opts = $.extend({
				defaultnum:defaultnum,
				onClass:"on",
				eventType:"click",
				movetime:300,
				onchangeSuccess:function(data){}
			},options);
			function tab(t){
				this.nav = t.find("[data-tab='nav']");
				this.onclass = opts.onClass;
				this.suffix = t.find("[data-tab='suffix']");
				this.num = opts.defaultnum;
				this.time = opts.movetime;
			};
			tab.prototype = {
				seton : function(){
					var _this = this;
					_this.nav.removeClass(_this.onclass);
					_this.nav.eq(_this.num).addClass(_this.onclass);
					_this.setsuffix();
				},
				setsuffix : function(n){
					if(this.suffix.length==0) return false;
					var _this = this,
						_width = _this.suffix.width();;
					_this.suffix.stop(true).animate({
						"left" : _width*this.num	
					},_this.time);
				}
			};
			return $(this).each(function() {
		        var $this = $(this);
				var obj = new tab($this);
				obj.seton();
				opts.onchangeSuccess(opts.defaultnum);
				//事件执行
				obj.nav.on(opts.eventType,function(){
					$("#tab-content").remove();
					
					
				/*	$("#tab-content").animate({opacity: 'hide'}, 2000,
							function(){ 
								$("#tab-content").remove();
								});  	
					*/
					obj.num = $(this).index();
					obj.seton();
					opts.onchangeSuccess.apply(this,[obj.num]);
				});
		    });
		};
		
		
		
		/*
		Tab弹出层{
			w:弹出层宽度
			tit:弹出层标题
			txt:弹出层内容模版
			callback:弹出后执行	
			}
		 */
		$.divup = function(options){
			var opts = $.extend({
				txt:"",
				domid : "divId",
				showback:function(){},
				hideback:function(){}
			},options);
			function popEve(){
				this.strpopTab="<div id=\"div-content\" data-id=\"div-content\"><div class=\"divtxt\"></div></div>"
				this.txt = opts.txt;//弹层添加数据
				//显示出来之后执行
				this.showback = opts.showback;
				//隐藏后执行
				this.hideback = opts.hideback;
				this.id = "[data-id='div-content']";
				this.domid = opts.domid;
			}
			popEve.prototype = {
				init:function(){
					var _this = this;
					$("#" + _this.domid).find(_this.id).remove();
//					$("#tab-content").remove();
					_this.inserttxt();
		//			$("#tab-content").hide();
		//			$("#tab-content").animate({left: '0px'}, 1000);  	
		
				},
				//内容插入
				inserttxt:function(){
					var _this = this;
					//插入弹窗外部皮肤
					$("#"+ _this.domid).append(_this.strpopTab);
					//插入内容
					$("#"+ _this.domid).find(_this.id).children(".divtxt").html(_this.txt);
					//对外接口
					_this.showback.apply(_this);
					return _this;	
				},
			};
			var obj = new popEve();
			obj.init();
		};
		
		
		/*获取html模版弹窗*/
		$.getDivHtml = function(options){
			var opts = $.extend({
				domid : "divid",
				url:"",//模版请求地址
				data:"",//传递参数
				okback:function(){}//模版反回成功执行	
			},options);
			//拉取静态模版
			$.divup({
				domid : opts.domid,
				showback:function(){
					var _this = this;
					$.ajax({
						type:"GET",
						data:opts.data,
						dataType:"html",
						url:opts.url,
						success:function(html){
							$("#" + _this.domid).find(_this.id).find(".divtxt").html(html);
							opts.okback();
						},
						error:function(){
						}	
					})
				}	
			});
		};	
		
		
		
		
		
		
	
	
	/*
		日期{
			stamp:当前时间戳
			tit : 时间月份标题
			onClass:日期点击样式
			callback:点击日期后执行---参数为点击的时间戳年月日逗号分隔
		}
		使用规则
		占用 属性date="list" 存放日期天数列表 
		占用属性 date-day  用来存放当前日期
		占用 data-btn属性来标注前后按钮:prev,next
	*/
	$.fn.calendar = function(options){
		if($(this).length==0) return false;
		var opts = $.extend({
			stamp : +(new Date()),
			tit : "dt>strong",
			callback:function(){}
		},options);
		function date(t){	
			//节点属性
			this.listdom = t.find("[date='list']");
			this.title = t.find(opts.tit);
			this.prevBtn = t.find("[data-btn='prev']");
			this.nextBtn = t.find("[data-btn='next']");
			//date属性
			this.dobj = new Date(opts.stamp);
			this.year = this.dobj.getFullYear();
			this.month = this.dobj.getMonth();
			this.date = this.dobj.getDate();
			this.week = this.dobj.getDay();
			this.monthcount = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
			//记录属性
			this.readY = this.year;
			this.readM = this.month;
			//对外接口
			this.callback = opts.callback;
		};
		date.prototype = {
			init:function(){
				var _this = this;
				//设置二月天数，设置日期标题，设置日期列表，切换日历
				_this.february().setdateTit().setdatelist().changeDate().cutDate();
			},
			//2月份日期总数推算
			february : function(){
				var _this = this;
				if ((_this.year % 4 == 0 && _this.year % 100 != 0) || _this.year % 400 == 0){_this.monthcount[1] = 29; };	
				return _this;
			},
			//设置日历标题
			setdateTit : function(){
				var _this = this;
				_this.title.html(_this.readY+"年"+(_this.readM+1)+"月");
				return _this;
			},
			//日期列表创建
			setdatelist:function(){
				var _this = this;
				//当月第一天为周几
				var first = new Date(_this.readY,_this.readM,1).getDay();
				var monArr = [];
				for(var i=0; i<first; i++){
					monArr.push("<li></li>");
				}
				for(var i=1,len = _this.monthcount[_this.readM]; i<=len; i++){
					var _class = "";
					if(_this.year==_this.readY&&_this.month==_this.readM&&_this.date==i){
						_class = "on";
					}
					monArr.push("<li class=\""+_class+"\"><a href=\"javascript:;\" date-day=\""+_this.readY+","+(_this.readM+1)+","+i+"\">"+i+"</a></li>");	
				}
				_this.listdom.html(monArr.join(""));
				return _this;
			},
			//日历点击
			changeDate:function(){
				var _this = this;
				_this.listdom.on("click","li a",function(){
					var _date = $(this).attr("date-day");
					_this.callback(_date);
					return false;
				});
				return _this;
			},
			//切换日期
			cutDate:function(){
				var _this = this;
				_this.prevBtn.on("click",function(){
					_this.readM = _this.readM-1;
					_this.readY = _this.readM<0?_this.readY-1:_this.readY;
					_this.readM = _this.readM<0?11:_this.readM;
					//重置月份统计，标题，列表
					_this.february().setdateTit().setdatelist();
					return false;
				});
				_this.nextBtn.on("click",function(){
					_this.readM = _this.readM+1;
					_this.readY = _this.readM>11?_this.readY+1:_this.readY;
					_this.readM = _this.readM>11?0:_this.readM;
					//重置月份统计，标题，列表
					_this.february().setdateTit().setdatelist();
					return false;
				});	
			}
		};
		return $(this).each(function() {
			var $this = $(this);
			var obj = new date($this);
			obj.init();
        });
	};
	$.fn.loadHtml = function(options){
		var opts = {type:'GET',async:true};
		$.extend(opts,options);
		var _this = this;
		$.ajax({
			type:"GET",
			data:opts.data,
			async:opts.async,
			dataType:"html",
			url:opts.url,
			beforeSend : function(xhr) {
				/**清楚浏览器缓存**/
				xhr.setRequestHeader("If-Modified-Since","0"); 
				xhr.setRequestHeader("Cache-Control","no-cache");
				if (sessionId) {
					xhr.setRequestHeader("sessionId", sessionId);
				}
				if(userId){
					xhr.setRequestHeader("guserId", userId);
				}
			},
			success:function(html){
				$(_this).html(html);
			},
			error:function(){
			}	
		})
	}
})(jQuery);