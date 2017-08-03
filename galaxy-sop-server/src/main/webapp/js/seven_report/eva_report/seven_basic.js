//这里不需要window.onload  和 $(document).ready(function(){})
	function　mouserover(obj){
		if($(obj).data('edit') == 'true') return;
		var target = $(obj).find('.editPic');
		 target.show();
	};
	function mouseout(obj){
		var target = $(obj).find('.editPic');
		target.hide();
	};
	
	
	//编辑按钮
	function typeEdit(obj){
		//编辑类型
		var _this=$(obj);
		var id_code=$(obj).attr("attr-id")
		var _td = _this.closest('td');
		var e_type = _this.attr("e-type");
		var radioShow = _td.find('.radioShow');
		$(obj).removeAttr("parent_dom");		
		console.log(e_type);
		if(e_type=="inside"){
			//内部编辑
			//编辑数据请求
			//请求成功，数据添加
			get_result(id_code,1,radioShow);
			var pText = $(obj).parent().find('p');
			_this.hide();
			_td.data('edit','true');
			pText.hide();			
			//对号，×号显示
			$(obj).closest('td').find('.Button').show();
			$(obj).closest('td').find('.radioShow').show();
			iCheck();
		}else if(e_type=="small_pop"){
			$('.gapPopup').show();
			$(obj).attr("parent_dom","show");
			var  leftNum = _this.offset().left-34;
			var  topNum = _this.offset().top-$(".gapPopup").height()-22;
			$('.gapPopup').css('left',leftNum).css('top',topNum);
			$('.mashLayer').show();
			//请求成功，数据渲染模板edit_tmpl1
			get_result(id_code,2,$(".gapPopup"));
			
			$(obj).hide();
			//对号，×号显示
			$(obj).closest('td').find('.Button').show();
			$(obj).closest('td').find('.radioShow').show();
			
		}else if(e_type=="cover_pop"){
			$(obj).attr("parent_dom","show");
			//$('.ch_income_evaluation').show();
			$('.mashLayer').show();
			var  leftNum = $(".new_left").offset().left;
			adjust(".ch_opration");
			//请求数据
			//数据渲染模板edit_tmpl2
			get_result(id_code,3,$(".ch_opration"));			
			$('.ch_opration').show();			
			$('.ch_opration').css('left',leftNum);
		}
		
		
	}
//code--第几道题的code    e_type--1-inside-在td里面编辑    2-small_pop-在小浮层里面编辑    3-cover_pop-在打弹窗里面编辑
function get_result(code,e_type,dom){
	console.log(code);
	 sendGetRequest(platformUrl.queryAllTitleValues+code+"?reportType=1",null,function(data){
		 console.log(data);
		 var result = data.result.status;
		 if(result == 'OK'){
			 var entity = data.entity;
			 var valueList = data.entity.valueList;
			 if(e_type==1){
				 var result_html = ""
				 if(entity.type==14||entity.type==3||entity.type==2||entity.type==5||entity.type==6||entity.type==12||entity.type==13){
					 $.each(valueList,function(i,n){
						 if(n.name=="其他"){
						 result_html += "<input type=\"radio\" class=\"others\" name="+n.titleId+" value="+n.id+" data-title-id="+n.titleId+" value="+n.code+"/><label>"+n.name+"</label><input type=\"text\" name=\"\" class=\"others_text\" value=\"\">"	 
						 }else{
						 result_html += "<input type=\"radio\" name="+n.titleId+" value="+n.id+" data-title-id="+n.titleId+" value="+n.code+"/><label>"+n.name+"</label><br/>"	 
						 }
					 })
				}else if(entity.type==1){
					result_html ="<input type=\"text\" palceholder="+entity.placeholder+" />";
				}
				 dom.html(result_html);
				 
			 }else if(e_type==2){
				 $("#edit_tmpl1").tmpl(entity).appendTo(dom);
			 }else if(e_type==3){
				 $("#edit_tmpl2").tmpl(entity).appendTo(dom);
				 adjust(".ch_opration");
			 }
			 
			//插件回调方法
				
		 }
	 })  

}	
	
//小弹窗关闭按钮
function closeX(obj){
	//对号,x号消失
	$(obj).parent().hide();
	//radio 消失
	$(obj).parent().parent().find('.radioShow').hide();
	//p内容展示
	$(obj).parent().parent().find('p').show();
	$(obj).parent().parent().find('p').css('color','#000');
	$(obj).closest('td').data('edit','false');
	
	//select下拉框消失
	$(obj).closest('td').find('.selectTips').hide();
	//弹窗消失
	//$(obj).parent().parent().hide();
	$(obj).closest('.gapPopup').hide();
	$(obj).parents(".gapPopup").find(".div_tmpl").remove();
	$('.mashLayer').hide();
	$("span[parent_dom='show']").removeAttr("parent_dom");
}	
	
//小弹窗保存方法
function right(obj,type){
	//对号，x号消失
	$(obj).parent().hide();
	//raido消失
	var other =$(obj).parent().siblings(".radioShow").find(".others");
	var align_left = $(obj).parent().parent().find(".align_left");
	//取值判断
	if(type=="radio"){
		var val_id = $(obj).parent().parent().find('input[type="radio"]:checked').val();
		var val = $(obj).parent().parent().find('input[type="radio"]:checked').parent(".iradio_flat-blue").next("label").html();
		align_left.find('p').attr("val_id",val_id);
	}else if(type=="checkbox"){
		var val_checkbox = $(obj).parent().parent().find('input[type="checkbox"]:checked');
		var val='';
		$.each(val_checkbox,function(){
			if($(this).val()=="其他"){
				val += $(this).parents(".radioShow").find(".others_text").val()+'、';
			}else{
				val+=$(this).val()+'、';
			}
		})
		var val=val.substring(0,val.length-1);
	}else if(type=="textarea"){
		align_left = $("span[parent_dom='show']").parent().find(".align_left");
		var val = $(obj).parent().parent().find("textarea").val();
	}

	if(other.attr("checked") == "checked"){
		var input_text = other.parents(".radioShow").find(".others_text").val();
	}
	$(obj).parent().parent().find('.radioShow').hide();
	debugger;
	console.log(val);
	if(val=="其他"){
		debugger;
		align_left.find('p').html(input_text);
	}else{
		align_left.find('p').html(val);
	}
	$(obj).parent().parent().find('p').show();
	$(obj).parent().parent().find('p').css('color','#000')
	$(obj).closest('td').data('edit','false');
	//select下拉框的值
	var selectText = $(obj).closest('td').find('.select_long').find("option:selected").text();
	$(obj).parent().parent().find('.seclect_choose').html(selectText);
	//console.log(selectText)
	//select下拉框消失
	$(obj).closest('td').find('.selectTips').hide();
	//新添加，弹窗中的
	$(obj).parents(".gapPopup").find(".div_tmpl").remove();
	$(obj).closest('.gapPopup').hide();
	$('.mashLayer').hide();
	$("span[parent_dom='show']").removeAttr("parent_dom");

}


//大弹窗 取消方法
$('div').delegate(".h_cancel_btn","click",function(event){
	var _this = $(this).parents(".ch_opration");
	_this.find("form").remove();
	_this.hide();
	$(".mashLayer").hide();
	event.stopPropagation();
});
//大弹窗 保存方法
$('div').delegate(".h_save_btn","click",function(event){
	var _this = $(this).parents(".ch_opration");
	_this.find("form").remove();
	_this.hide();
	$(".mashLayer").hide();
	event.stopPropagation();
	$("span[parent_dom='show']").removeAttr("parent_dom");
});	
	
//div模拟select下拉框
	$(".input_select").click(function(){ 
		var ul = $("#dropdown ul"); 
		var _this = $(this);
		if(ul.css("display")=="none"){
			_this.addClass('up');
			ul.slideDown("fast"); 
		}else{ 
		ul.slideUp("fast");
		_this.removeClass('up');
		_this.addClass('input_select')
		} 
	}); 

	$("#dropdown ul li a").click(function(){ 
		var target = $(this).closest('#dropdown').find('input');
		target.removeClass('up')
		var txt = $(this).text(); 
		$(".input_select").val(txt); 
		$("#dropdown ul").hide(); 
}); 


		
	//遮罩层
	function pageHeight(){
		return document.body.scrollHeight+200;
	}
	function pageWidth(){
		return document.body.scrollWidth;
	}
	$('.mashLayer').height(pageHeight());
	$('.mashLayer').width(pageWidth());
	
	//弹窗

	/* 定位到页面中心 */
	function adjust(id) {
	    var w = $(id).width();
	    var h = $(id).height();
	    var t = scrollY() + (windowHeight()/2) - (h/2);
	    if(t < 0) t = 0;
	    $(id).css('top',t+'px');
	}
    
	
	//浏览器视口的高度
	function windowHeight() {
	    var de = document.documentElement;
	    return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
	}

	//浏览器视口的宽度
	function windowWidth() {
	    var de = document.documentElement;
	    return self.innerWidth || (de && de.clientWidth) || document.body.clientWidth
	}

	/* 浏览器垂直滚动位置 */
	function scrollY() {
	    var de = document.documentElement;
	    return self.pageYOffset || (de && de.scrollTop) || document.body.scrollTop;
	}

	/* 浏览器水平滚动位置 */
	function scrollX() {
	    var de = document.documentElement;
	    return self.pageXOffset || (de && de.scrollLeft) || document.body.scrollLeft;
	}
	
	
	//详情，展开，  收起方法
	$('.detail').click(function(){
		$('.income_structor_content').removeClass('income_structor_content');
		$(this).hide();
		$('.detail').prev().show();
	})
	$('.packup').click(function(){
		$('.income_structor span').addClass('income_structor_content');
		$(this).hide();
		$(this).next().show();
	})
	
	
	
	//===================================================新添加
	//填空题的点击编辑按钮弹窗
		$('.editPic1111').click(function(){
			if($(this).hasClass('choose_question')){
				return false;
			}
			//股权结构
			if($(this).hasClass('several_question'))
			{
				$('.ch_stock').show();
				$('.mashLayer').show();
				var _target = $(this).closest('td');
				var  leftNum = _target.offset().left-230;
				var  topNum = _target.offset().top-200;
				console.log(topNum);
				$('.ch_stock').css('left',leftNum).css('top',topNum);
				return false;
			}
			//收入模式测评
			if($(this).hasClass('ch_income_multiple'))
			{
				$('.ch_income_evaluation').show();
				$('.mashLayer').show();
				var _target = $(this).closest('td');
				var  leftNum = _target.offset().left-230;
				var  topNum = _target.offset().top-200;
				console.log(topNum);
				$('.ch_income_evaluation').css('left',leftNum).css('top',topNum);
				return false;
			}

			//收入结构
			if($(this).hasClass('income_struct')){
				$('.ch_opration').show();
				$('.mashLayer').show();
				var _target = $(this).closest('td');
				var  leftNum = _target.offset().left-230;
				var  topNum = _target.offset().top-200;
				console.log(topNum);
				$('.ch_opration').css('left',leftNum).css('top',topNum);
				return false;

			}

			$('.gapPopup').show();
			var _target = $(this).closest('td');
			var  leftNum = _target.offset().left+220;
			var  topNum = _target.offset().top-122;
			console.log(topNum);
			$('.gapPopup').css('left',leftNum).css('top',topNum);
			$('.mashLayer').show();



		})

		//股权表格点击弹窗
		$('.content_show').click(function(){
			$('.reasonable_stock').show();
			var _target = $(this).closest('td');
			var  leftNum = _target.offset().left+220;
			var  topNum = _target.offset().top-148;
			console.log(topNum);
			$('.reasonable_stock').css('left',leftNum).css('top',topNum);
			$('.mashLayer').show();
		});

		//图片点击弹窗
		$('.income_pic').click(function(){
			$('.customer_income').show();
			var _target = $(this);
			var  leftNum = _target.offset().left-20;
			var  topNum = _target.offset().top-188;
			console.log(topNum);
			$('.customer_income').css('left',leftNum).css('top',topNum);
			$('.mashLayer').show();
		})



//弹窗消失按钮
$('.h_cancel_btn').click(function(){
	$('.mashLayer').hide();
	$('.ch_stock').hide();
	$('.ch_opration').hide();
	$('.reasonable_stock').hide();
	$('.customer_income').hide();
	$('.ch_income_evaluation').hide();


})
//radio checkbox 插件 渲染方法
function iCheck(){
	$('input').iCheck({
		checkboxClass: 'icheckbox_flat-blue',
		radioClass: 'iradio_flat-blue'
	})
	$('.others').on('ifChecked',function(event){
		 $(this).parents('.radioShow').find('.others_text').show();
		 $(this).attr("checked",true);
	});
	$('.others').on('ifUnchecked',function(event){
		$(this).parents('.radioShow').find('.others_text').hide();
	})
}
	
	
	
	
	
	
	
	
	
	
