// JavaScript Document
$(function(){
	// 判断文件后缀名分别进行不同状态展示
	function selectFile(input){
		var fileName = input.val();
		if(fileName.length> 1 && fileName){
			var ldot = fileName.lastIndexOf("."); 
			var type = fileName.substr(Number(ldot + 1)); 
			if (type=="pdf") {
				input.siblings('.file_box').find('img').removeClass("add_img")
				input.siblings('.file_box').find('img').attr(
					"src", '../img/pdf.png');
			}else{
				input.siblings('.file_box').find('img').removeClass("add_img")
				input.siblings('.file_box').find('img').attr(
				"src", '../img/image.png');
			}
			input.siblings('.file_box').find('.cover_box').show();
		}else{
			input.siblings('.file_box').find('img').addClass("add_img")
			input.siblings('.file_box').find('img').attr(
			"src", '../img/plus_icon.png');
			input.siblings('.file_box').find('.cover_box').hide();
		}
	}
	$(".file_list input[type='file']").change(function(event) {
		selectFile($(this));
	});
	//文件上传与取消
	$(".file_box .cover_box .cancel").click(function(event) {
		$(this).parents(".cover_box").hide();
		$(this).parents(".cover_box").siblings('img').addClass("add_img").attr(
			"src", '../img/plus_icon.png');
		$(this).parents("li").find("input").val("");
	});
	$(".file_box .cover_box .up_load").click(function(event) {
		$(this).parents(".cover_box").find("span").hide();
		$(this).siblings('p').show();
	});
	//tab点击事件
	$(".tab_2").click(function(event) {
		$(this).addClass('on');
		$(this).siblings().removeClass('on');
		$('.file_list').show();
		$(".tab_con table").hide();
		$(".add_list").hide();
	});
	$(".tab_1").click(function(event) {
		$(this).addClass('on');
		$(this).siblings().removeClass('on');
		$('.file_list').hide();
		$(".tab_con table").show();
		$(".add_list").show();
	});
	// 添加访谈记录
	
	$(".new_poppage").on("click",function(){ 
		var $self = $(this);
		var _url = $self.attr("href");
		var _name=$self.attr("data-name")
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$("#popup_name").text(_name);
				$("[data-btn='add_rzzx']").on("click",function(){ 
					var $self = $(this);
					var _url = $self.attr("href");
					var _name=$self.attr("data-name")
					$.getHtml({
						url:_url,//模版请求地址
						data:"",//传递参数
						okback:function(){
							$("#popup_name1").text(_name);							
						}//模版反回成功执行	
					});
					return false;
				});
				
			}//模版反回成功执行	
		});
		return false;
	});
})

function  progress(id){
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/progress/index",//模版请求地址
		data:"",//传递参数
		okback:function(){
			$(".close").addClass("progress_close")
		}
	});
}
