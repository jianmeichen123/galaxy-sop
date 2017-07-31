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
	
	
	//选择题的编辑按钮
	function typeEdit(obj){
		//编辑类型
		var _this=$(obj);
		var _td = _this.closest('td');
		var e_type = _this.attr("e-type");
		console.log(e_type);
		if(e_type=="inside"){
			//内部编辑
			//编辑数据请求
			//请求成功，数据渲染模板edit_tmpl1
			var radioShow = _td.find('.radioShow');
			var entity="";
			$("#edit_tmpl1").tmpl(entity).appendTo(radioShow);
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
			var  leftNum = _this.offset().left-34;
			var  topNum = _this.offset().top-$(".gapPopup").height()-22;
			$('.gapPopup').css('left',leftNum).css('top',topNum);
			$('.mashLayer').show();
			
			$(obj).hide();
			//对号，×号显示
			$(obj).closest('td').find('.Button').show();
			$(obj).closest('td').find('.radioShow').show();
			
		}else if(e_type=="cover_pop"){
			$('.ch_income_evaluation').show();
			$('.mashLayer').show();
			var  leftNum = _td.offset().left-230;
			var  topNum = _td.offset().top-200;
			//请求数据
			//数据渲染模板edit_tmpl2
			
			$('.ch_income_evaluation').css('left',leftNum).css('top',topNum);
		}
		
		
		/*$(obj).closest('td').addClass('edit_true');
		var pText = $(obj).parent().find('p');
		pText.hide();
		$(obj).hide();
		//对号，×号显示
		$(obj).closest('td').find('.Button').show();
		$(obj).closest('td').find('.radioShow').show();
		//给td加自定义属性
		$(obj).parent().data('edit','true')*/
	}



	//插件回调方法
	$('.others').on('ifChecked',function(event){
		 $(this).parents('.radioShow').find('.others_text').show();
		 $(this).attr("checked",true);


	});
	$('.others').on('ifUnchecked',function(event){
		$(this).parents('.radioShow').find('.others_text').hide();
	})

	//  对号函数
	function right(obj,type){
		//对号，x号消失
		$(obj).parent().hide();
		//raido消失
		var other =$(obj).parent().siblings(".radioShow").find(".others");
		//取值判断
		if(type=="radio"){
			var val = $(obj).parent().parent().find('input[type="radio"]:checked').val();
		}else if("checkbox"){
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
		}else if("textarea"){

		}

		if(other.attr("checked") == "checked"){
			var input_text = other.parents(".radioShow").find(".others_text").val();
		}
		$(obj).parent().parent().find('.radioShow').hide();
		if(val=="其他"){
			$(obj).parent().parent().find('p').html(input_text);
		}else{
			$(obj).parent().parent().find('p').html(val);
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
		$(obj).closest('.gapPopup').hide();
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
		$(obj).closest('.gapPopup').hide();
		$('.mashLayer').hide();
	}
	
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
}
	
	
	
	
	
	
	
	
	
	