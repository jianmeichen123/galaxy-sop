//单选点击事件
	$('div').delegate(".h_radios input","click",function(){
		var val = $(this).parent().text();
		if(val=="其他"){
			var other_input = $(this).parent().next().find("input");
			other_input.attr("disabled",false);
		}else{
			var other_input = $(this).parent().next().find("input");
			if(!other_input){
				other_input.attr("disabled",true);
			}
		}
	})
	
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
			var pSpan = $(obj).parent().find('span');
			_this.hide();
			_td.data('edit','true');
			pText.hide();	
			pSpan.hide();
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
			$('.mashLayer').show();
			//adjust(".ch_opration");
			//请求数据
			//数据渲染模板edit_tmpl2
			get_result(id_code,3,$(".ch_opration"));			
			$('.ch_opration').show();			
		}
		editResult(obj);
		
	}
	//编辑回显
	function editResult(obj){
		var e_type=$(obj).attr("e-type");
		var id_code=$(obj).attr("attr-id")
		var p_box=$(obj).siblings(".align_left").find("p")
		var  val=p_box.text();
		var titleVal=p_box.attr("data-title-value");
		var type=p_box.attr("data-type");
		if(e_type!="cover_pop"){  //inside和小弹窗编辑回显
			if(type==1){
				var relateId=p_box.attr("data-relate-id");
				if(val!="未填写"){
					$(".condition").find("input[data-id='"+relateId+"']").val(val);
				}			
			}else if(type==2){
				var dom=$(obj).siblings(".radioShow").find("input[value='"+titleVal+"']").parent(".iradio_flat-blue");
				dom.addClass("checked");
				dom.children("input").attr("checked",true);
			}else if(type==8 || type==15){
				var relateId=p_box.attr("data-relate-id");
				if(val!="未填写"){
					$(".div_tmpl").find("textarea[data-id='"+relateId+"']").text(val);
				}			
			}else if(type==13){
				var titleValList=titleVal.split(",");
				var valList=val.split("、");
				for(var i=0;i<titleValList.length;i++){
					var radioShow=$(obj).closest("td").find(".radioShow")
					$(radioShow).find("input[value='"+titleValList[i]+"']").parent(".icheckbox_flat-blue").addClass("checked");
					$(radioShow).find("input[value='"+titleValList[i]+"']").parent(".icheckbox_flat-blue").children("input").attr("checked",true);
				}
				var last_id=$(radioShow).children(".icheckbox_flat-blue:last").hasClass("checked");
				if(last_id){
					$(radioShow).find(".others_text").show();
					$(radioShow).find(".others_text").val(valList[valList.length-1])
				}
			}
		}else{  //大弹窗编辑回显
			var data=[];
			var dom=$(obj).siblings(".align_left").find("span")
			$(dom).each(function(){
				var data_list={};
				var relateId=$(this).parent("p").attr("data-relate-id");
				var val=$(this).text();
				data_list.relateId=relateId;
				data_list.val=val;
				data.push(data_list);
			});
			console.log(data);
			$(data).each(function(){  //sign为3，且type为1 
				var n=$(this)[0];
				if(type==1){
					if(n.val!="未填写"){
						$("input[data-title-id='"+n.relateId+"']").val(n.val);
					}
				}
								
			})
		}
		
	}
//code--第几道题的code    e_type--1-inside-在td里面编辑    2-small_pop-在小浮层里面编辑    3-cover_pop-在打弹窗里面编辑
function get_result(code,e_type,dom){
	console.log(code);
	 sendGetRequest(platformUrl.queryAllTitleValues+code+"?reportType="+reportType,null,function(data){
		 console.log(data);
		 var result = data.result.status;
		 if(result == 'OK'){
			 var entity = data.entity;
			 console.log(entity);
			 var valueList = data.entity.valueList;
			 var type=entity.type;
			 if(e_type==1){
				 var result_html = ""
				 if(type==2||type==5||type==6||type==12){
					 $.each(valueList,function(i,n){
						 if(n.name=="其他"){
						 result_html += "<input type=\"radio\" class=\"others\" name="+n.titleId+" value="+n.id+" data-title-id="+n.titleId+" value="+n.code+"/><label>"+n.name+"</label><input type=\"text\" name=\"\" class=\"others_text\" value=\"\">"	 
						 }else{
						 result_html += "<input type=\"radio\" name="+n.titleId+" value="+n.id+" data-title-id="+n.titleId+" value="+n.code+"/><label>"+n.name+"</label><br/>"	 
						 }
					 })
				}else if(type==1){
					result_html ="<input type=\"text\" data-id="+data.id+" palceholder="+entity.placeholder+" />";
				}else if(type==13||type==3){
					 $.each(valueList,function(i,n){						 
						 if(n.name=="其他"){
						 result_html += "<input type=\"checkbox\" class=\"others\" name="+n.titleId+" value="+n.id+" data-title-id="+n.titleId+" value="+n.code+"/><label>"+n.name+"</label><input type=\"text\" name=\"\" class=\"others_text\" value=\"\">"	 
						 }else{
						 result_html += "<input type=\"checkbox\" name="+n.titleId+" value="+n.id+" data-title-id="+n.titleId+" value="+n.code+"/><label>"+n.name+"</label><br/>"	 
						 }
					 })
				}else if(type==18){
					result_html="<div class=\"dropdown\"> <input class=\"input_select\" type=\"text\" value=\"请选择\"/><ul class=\"select_list\"></ul></div>"
				}else if(type==14){
					var result_li='';
					$.each(valueList,function(i,n){
						result_li += "<li><a href=\"#\" data-code="+n.code+" id="+n.id+">"+n.name+"</a></li> "
					})
					result_html="<div class=\"dropdown\"> <input class=\"input_select\" type=\"text\" value=\"请选择\"/><ul class=\"select_list\">"+result_li+"</ul></div>"
				}
				 dom.html(result_html);
				 divSelect();
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
	
//保存方法
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
		var p = align_left.find('p');
		if(typeof val_id == 'undefined')
		{
			p.text('未选择');
			p.attr("data-title-value",'');
		}
		else
		{
			p.attr("data-title-value",val_id);
			p.text(val);
		}
	}else if(type=="checkbox"){
		var val_checkbox = $(obj).parent().parent().find('input[type="checkbox"]:checked');
		var val_id = $(obj).parent().parent().find('input[type="radio"]:checked').val();
		var p = align_left.find('p');
		if(val_checkbox.length==0){
			p.text('未选择');
			p.attr("data-title-value",val_id);
		}
		else
		{
			var content = new Array();
			var values = new Array();
			$.each(val_checkbox,function(){
				var val_text = $(this).parent(".icheckbox_flat-blue").next("label").html();
				var val_id =$(this).val();
				values.push(val_id);
				content.push(val_text);
			});
			p.html(content.join('、'));
			p.attr("data-title-value",values.join(','));
		}
			
	}else if(type=="textarea"){
		p = $("span[parent_dom='show']").parent().find(".align_left").find('p');
		var val = $(obj).parent().parent().find("textarea").val();
		if(val == null || val.length == 0)
		{
			val='未填写';
		}
		p.text(val);
		
	}else if(type=="input"){
		var val = $(obj).closest("tr").find("input").val();
		var p = align_left.find('p');
		if(val == null || val.length == 0)
		{
			val='未填写';
		}
		p.text(val);
	}else if(type=="select"){
		var p = align_left.find('p');
		var _select = $(obj).closest('td').find('.dropdown').find("input");
		var selectVal = _select.attr("value");
		var selectId = _select.attr("id");
		var selectCode = _select.data("code");
		if(typeof selectId == 'undefined')
		{
			p.text('未选择');
			p.attr("data-title-value",'');
		}
		else
		{
			p.attr("data-title-value",selectId);
			p.text(selectVal);
		}
	}
	//判断选中其他
	if(other.attr("checked") == "checked"){
		var input_text = other.parents(".radioShow").find(".others_text").val();
	}
	$(obj).parent().parent().find('.radioShow').hide();
	//填充结果
	if(val=="其他"){
		align_left.html(input_text);
	}else{
		//align_left.html(val); 
	}
	//$(".align_left span").last().remove();
	$(obj).parent().parent().find('p').show();
	$(obj).parent().parent().find('p').css('color','#000')
	$(obj).closest('td').data('edit','false');
	//新添加，弹窗中的
	$(obj).parents(".gapPopup").find(".div_tmpl").remove();
	$(obj).closest('.gapPopup').hide();
	$('.mashLayer').hide();
	$("span[parent_dom='show']").removeAttr("parent_dom");
	if(typeof afterTitleSaved == 'function')
	{
		afterTitleSaved();
	}
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
	var align_left = $("span[parent_dom='show']").parent().find(".align_left");
	var array_p = align_left.find("p");
	var form = $(this).parents(".ch_opration").find("form");
	var data=[];
	//获取保存的的数据
	$.each(form.find(".mb_16"),function(i,n){
		var data_list={};
		var _dt = $(this).find("dt");
		data_list.code=_dt.data("code");
		data_list.id=_dt.data("id");
		data_type=_dt.data("type");
		data_list.type=data_type;
		if(data_type==1||data_type==8){
			data_list.value=$(this).find("dd").children().val();
			if(data_list.value==""||!data_list.value){
				data_list.value=="未填写";
			}
		}else if(data_type==14){
			data_list.value=$(this).find("select").find("option:selected").text();
			data_list.value_id=$(this).find("select").val();
			if(data_list.value==""||!data_list.value){
				data_list.value=="未选择";
			}
		}else if(data_type==2||data_type==12){
			var h_radios = $(this).find(".h_radios");
			var value_check = h_radios.find('input[type="radio"]:checked');
			data_list.value=value_check.parent().text();
			data_list.value_id=value_check.val();
			if(data_list.value=="其他"){
				/*other_input.attr("disabled")*/
				data_list.value=value_check.parent().next().find("input").val();
			}
			if(data_list.value==""||!data_list.value){
				data_list.value=="未选择";
			}
		}
		data.push(data_list);
	})
	//填充保存的的数据
	var align_p = align_left.find("p");
	$.each(align_p,function(){
		var _this=$(this);
		var _code = _this.data("code");
		$.each(data,function(){
			var d_this = $(this)[0];
			var dcode = d_this.code;
			var _type =d_this.type;
			if(_code==dcode){
				if(_type==1||_type==8){
					_this.find("span").html(d_this.value);
				}else if(_type==14||_type==2||_type==12){
					_this.find("span").html(d_this.value);
					_this.attr("data-title-value",d_this.value_id);
				}
				
			}
		})
	})
	
	
	var _this = $(this).parents(".ch_opration");
	_this.find("form").remove();
	_this.hide();
	$(".mashLayer").hide();
	event.stopPropagation();
	$("span[parent_dom='show']").removeAttr("parent_dom");
});	
	
//div模拟select下拉框
function divSelect(){
	$(".input_select").click(function(){ 
		var ul = $(".dropdown ul"); 
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

	$(".dropdown ul li").click(function(){ 
		var target = $(this).closest('.dropdown').find('input');
		var _a=$(this).find('a');
		target.removeClass('up')
		var txt = _a.text(); 
		var _id=_a.attr("id");
		var _code=_a.data("code");
		target.attr("value",txt); 
		target.attr("id",_id);
		target.attr("data-code",_code);
		$(".dropdown ul").hide(); 
}); 
}



		window.onresize = function(){
			//遮罩层
			function pageHeight(){
				return document.body.scrollHeight;
			}
			function pageWidth(){
				return document.body.scrollWidth;
			}
			$('.mashLayer').height(pageHeight());
			$('.mashLayer').width(pageWidth());
			adjust(".ch_opration");
			
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

	/* 定位到页面中心 */
	function adjust(id) {
	    var w = $(id).width();
	    var h = $(id).height();
	    var t = scrollY() + (windowHeight()/2) - (h/2);
	    if(t < 0) t = 0;
	    $(id).css('top',t+'px');
	    console.log(t);
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
	
	
	
	
//	分割————表格和图片特殊类型
	
function getDetailUrl(code)
{
	if(code == 'equity-structure')
	{
		return '../../../html/funcing_add_gd.html';
	}
	else if(code == 'investor-situation')
	{
		return '../../../html/funcing_add_tz.html';
	}
	else if(code =='operation-indices')
	{
		return '../../../html/fincing_add_yx.html';
	}
	else if(code == 'valuation-reference')
	{
		return '../../../html/fincing_add_tl.html';
	}
	else if(code == 'financing-milestone')
	{
		return '../../../html/fincing_add_jd.html';
	}else if (code =='team-members'){

	    return '../../../html/team_compile.html';
	}else if(code == 'share-holding')
    {
        return '../../../html/team_add_cgr.html';
    }
	return "";
}
	//add新增表格按钮
	function addRow(ele)
{
   /*  if ( validateCGR() ) { */
        var code = $(ele).prev().data('code');
        $.getHtml({
            url:"../../../html/team_compile.html",//模版请求地址
            data:"",//传递参数
            okback:function(){
				/*$('#qualifications_popup_name').html('添加简历');
				$('#qualifications_popup_name1').html('添加持股人');
                $("#detail-form input[name='projectId']").val(projectInfo.id);
                $("#detail-form input[name='titleId']").val($(ele).prev().data('titleId'));
                $("#detail-form input[name='subCode']").val($(ele).prev().data('code'));
               
                selectContext("detail-form");
                
                $("#save-detail-btn").click(function(){
                    saveForm($("#detail-form"));
                    check_table();
                    check_table_tr_edit();
                });
                $("#save_person_learning").click(function(){
                	check_table();
                	check_table_tr_edit();
                });*/
            }//模版反回成功执行
        });
    /* } */
}
//图片缩略图
$('div').delegate(".h_imgs_add input[type='file']","change",function(){
	 var fi_this = $(this);
	 if ($(this).val() != '') {
    var files = !!this.files ? this.files : [];
    if (!files.length || !window.FileReader) return;
    if (/^image/.test( files[0].type)){
        var reader = new FileReader();
        reader.readAsDataURL(files[0]);
        reader.onloadend = function(){  
        fi_this.parent(".h_imgs_add").html("<a href=\"javascript:;\" class=\"h_img_del\" ></a><img src="+this.result+" />");

        }
    }
 }else{
	 fi_this.parent(".h_imgs_add").html("<a href=\"javascript:;\" class=\"h_img_del\" ></a><img src="+this.result+" />");

 }
	 console.log(this.result);
})
	
	
	
