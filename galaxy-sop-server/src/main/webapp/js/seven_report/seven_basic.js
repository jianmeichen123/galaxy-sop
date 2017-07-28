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
	
	
	
	//弹窗   由不可以编辑变为可以编辑状态
	function canEdit(obj){
		var textarea = $(obj).parent().find('textarea');
		textarea.removeAttr('readonly');
	};
	
	//获取所有的可编辑的td所在列
	
	
	
	//选择题的编辑按钮
	function chooseEdit(obj){
		$(obj).closest('td').addClass('edit_true');
		var pText = $(obj).parent().find('p');
		pText.hide();
		$(obj).hide();
		//对号，×号显示
		$(obj).closest('td').find('.Button').show();
		$(obj).closest('td').find('.radioShow').show();
		//给td加自定义属性
		$(obj).parent().data('edit','true')
	}

	
	//填空题的编辑按钮
	function gapEdit(obj){
		$('.mashLayer').show();
		$('.popup').show();
		
	}
	//  对号函数
	function right(obj){
		//对号，x号消失
		$(obj).parent().hide();
		//raido消失
		$(obj).parent().parent().find('.radioShow').hide();
		var val = $(obj).parent().parent().find('input:checked').val();
		console.log(val)
		$(obj).parent().parent().find('p').html(val);
		$(obj).parent().parent().find('p').show();
		$(obj).parent().parent().find('p').css('color','#000')
		$(obj).closest('td').data('edit','false');
		//select下拉框的值
		var selectText = $(obj).closest('td').find('.select_long').find("option:selected").text();
		$(obj).parent().parent().find('.seclect_choose').html(selectText);
		console.log(selectText)
		//select下拉框消失
		$(obj).closest('td').find('.selectTips').hide();
		//弹窗消失
		$(obj).closest('.popup').hide();
		$('.mashLayer').hide();
		
	}
	//x号函数
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
		$(obj).closest('.popup').hide();
		$('.mashLayer').hide();
	}
	
	
	
	
	
	
	
	
	//遮罩层
	function pageHeight(){
		return document.body.scrollHeight;
	}
	function pageWidth(){
		return document.body.scrollWidth;
	}
	$('.mashLayer').height(pageHeight());
	$('.mashLayer').width(pageWidth());
	
	
	
	
	//弹窗
	var popup = document.getElementsByClassName('popup')[0];
	var popupTwo = document.getElementsByClassName("popup_two")[0];
	var popupThree = document.getElementsByClassName("table_three")[0];
	var ch_stock = document.getElementsByClassName("ch_stock")[0];
	adjust(popup);
	adjust(popupTwo);
	adjust(popupThree);
	adjust(ch_stock);
	/* 定位到页面中心 */
	function adjust(id) {
	    var w = $(id).width();
	    var h = $(id).height();
	    
	    var t = scrollY() + (windowHeight()/2) - (h/2);
	    if(t < 0) t = 0;
	    
	    var l = scrollX() + (windowWidth()/2) - (w/2);
	    if(l < 0) l = 0;
	    
	    $(id).css({left: l+'px', top: t+'px'});
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
