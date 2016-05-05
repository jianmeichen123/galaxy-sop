	function dateFormatter(val,row,index)
	{
		if(!isNaN(val))
		{
			return Number(val).toDate().format("yyyy-MM-dd");
		}
		return val;
	}
	function progressFormatter(val,row,index)
	{
		if(val != null)
		{
			return $('[name="ideaProgress"] option[value="'+val+'"]').text();
		}
	}
	function ideaNameLinkFormatter(val,row,index)
	{
		return '<a href="#" class="blue" onclick="showIdeaDetail(\'' + row.id + '\')">'+val+'</a>';
	}
	
	function showIdeaDetail(ideaId)
	{
		var $self = $(this);
		var _url = platformUrl.ideaGoStage+"?id="+ideaId;
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
					$('.block').css({
						display: 'none',
					});;
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

	}
	function refreshIdeaList()
	{
		$("#data-table").bootstrapTable('refresh');
	}
	function getDepartment($depField)
	{
		sendGetRequest(
				platformUrl.getIdeaDepartment,
				null,
				function(data){
					if(data.result.status = 'OK')
					{
						$depField.empty();
						if(data.entityList.length >1)
						{
							$depField.append('<option value="">全部</option>');
						}
						$.each(data.entityList,function(){
							$depField.append('<option value="'+this.id+'">'+this.name+'</option>');
						});
					}
				}
		);
	}