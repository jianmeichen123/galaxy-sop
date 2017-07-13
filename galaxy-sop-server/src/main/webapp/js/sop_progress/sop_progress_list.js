	var flow = [
		'projectProgress:1',
		'projectProgress:2',
		'projectProgress:3',
		'projectProgress:4',
		'projectProgress:11',
		'projectProgress:5',
		'projectProgress:6',
		'projectProgress:7',
		'projectProgress:8',
		'projectProgress:9',
		'projectProgress:10'
		];
	var _project_;
	var btnData = {};
	var projectDtd;
	var btnDtd;
	var i = 0;//获取阶段值
	
	//加载阶段信息
	refreshIndex(true)
	
	/**
	 * 刷新当前阶段值并跳转
	 * @param reload 是否重新加载project数据 true/false
	 * @returns
	 */
	function refreshIndex(reload)
	{
		if(reload)
		{
			loadProjectData();
			$.when(btnDtd).done(function(){
				for(var j=0;j<flow.length;j++)
				{
					if(flow[j] == _project_.projectProgress)
					{
						i=j+1;
						break;
					}
				}
				$(".next_box").attr("data-progress",i);
				goToProgress();
			});
		}
		else
		{
			for(var j=0;j<flow.length;j++)
			{
				if(flow[j] == _project_.projectProgress)
				{
					i=j+1;
					break;
				}
			}
			$(".next_box").attr("data-progress",i);
			goToProgress();
		}
	}
	/**
	 * 刷新按钮
	 * @returns
	 */
	function refreshButton()
	{
		loadProjectData();
		$.when(btnDtd).done(function(){
			buttonData();
		});
	}
	
	/**
	 * 项目信息，{@link _project_}
	 * @returns
	 */
	function loadProjectData()
	{
		projectDtd = $.Deferred();
		sendGetRequest(platformUrl.detailProject + projectId, {}, function(data){
			_project_ = data.entity;
			projectDtd.resolve();
			loadButtonData();
		});
	}
	/**
	 * j加载按钮信息 - 隐藏/显示
	 * @returns
	 */
	function loadButtonData()
	{
		btnDtd = $.Deferred();
		sendGetRequest(
			platformUrl.buttonToggle+projectId, 
			{}, 
			function(data){
				btnData = data.userData;
				btnDtd.resolve();
			}
		);
	}
	
	//显示当前阶段
	//showProgress(progress);
	
	//上一步、下一步显示隐藏
	function progressBtnToggle(){
		if(i==1){
			$(".pre_box").hide();
		}else if(i==11){
			$(".next_box").hide();
		}else{
			$(".next_box").show();
			$(".pre_box").show();
		}
	}
	//会议文档tab显示
	function tab_show(status){
		if(status==1){
//			1的时候是只有会议
			$(".tab_con").show();
			$(".new_poptxt .tab_1").show();
			$(".new_poptxt .bootstrap-table").show();
			$(".new_poptxt .tab_2").hide();
			$(".new_poptxt .file_list").hide();
			$(".new_poptxt .tab_1").addClass("on");
			$(".new_poptxt .tab_2").removeClass("on");
			$(".operation_warning").hide();
		}else if(status==2){
//			2的时候是只有文档
			$(".tab_con").show();
			$(".new_poptxt .tab_1").hide();
			$(".new_poptxt .bootstrap-table").hide();
			$(".new_poptxt .tab_2").show();
			$(".new_poptxt .file_list").show();
			$(".new_poptxt .tab_2").addClass("on");
			$(".new_poptxt .tab_1").removeClass("on");
			$(".operation_warning").hide();
		}else if(status ==3){
//			3的时候是都有
			$(".tab_con").show();
			$(".new_poppage").show();
			$(".new_poptxt .tab_1").show();
			$(".new_poptxt .bootstrap-table").show();
			$(".new_poptxt .tab_2").show();
			$(".new_poptxt .file_list").hide();
			$(".new_poptxt .tab_1").addClass("on");
			$(".new_poptxt .tab_2").removeClass("on");
			$(".operation_warning").hide();
		}else if(status==4){
			//投后运营
			$(".tab_con").hide();
			$(".operation_warning").show();
		}
	}
	//阶段加载
	function goToProgress(){		
		progressBtnToggle();
		if(i==1){
			interviewList();
			toobarData("接触访谈","添加访谈记录","");
			$(".tablink .icon").hide();
			tab_show(1);
			hideCurrent('projectProgress:1');
		}else if(i==2){
			meetList("meetingType:1");
			toobarData("内部评审","添加内部评审","meetingType:1");
			$(".tablink .icon").hide();
			tab_show(1);
			hideCurrent('projectProgress:2');
		}else if(i==3){
			meetList("meetingType:2");
			toobarData("CEO评审","添加CEO评审","meetingType:2");
			$(".tablink .icon").hide();
			tab_show(1);
			hideCurrent('projectProgress:3');
		}else if(i==4){
			meetList("meetingType:3");
			toobarData("立项会","添加立项会","meetingType:3");
			$(".tablink .icon").hide();
			toobarfile("立项会",4);
			tab_show(3);
			hideCurrent('projectProgress:4');
		}else if(i==5){
			$(".tabtitle h3").text("会后商务谈判");
			toobarData("会后商务谈判","会后商务谈判","meetingType:5");
			$(".tablink .icon").hide();
			tab_show(1);
			meetList("meetingType:5");
			hideCurrent('projectProgress:11');
		}else if(i==6){
			$(".tabtitle h3").text("投资意向书");
			toobarfile("投资意向书",6);
			$(".tablink .icon").show();
			tab_show(2);
			hideCurrent('projectProgress:5');
		}else if(i==7){
			$(".tabtitle h3").text("尽职调查");
			$(".tablink .icon").show();
			toobarfile("尽职调查",7);
			tab_show(2);
			hideCurrent('projectProgress:6');
			//尽职调查  上传附件
		}else if(i==8){
			meetList("meetingType:4");
			toobarData("投决会","添加投决会","meetingType:4");
			$(".tablink .icon").hide();
			tab_show(1);
			hideCurrent('projectProgress:7');
		}else if(i==9){
			$(".tabtitle h3").text("投资协议");
			$(".tablink .icon").show();
			toobarfile("投资协议",9);
			tab_show(2);
			hideCurrent('projectProgress:8');
			//投资协议  上传附件
		}else if(i==10){
			$(".tabtitle h3").text("股权交割");
			$(".tablink .icon").show();
			toobarfile("股权交割",10);
			tab_show(2);
			hideCurrent('projectProgress:9');
			//股权交割   上传附件
		}else if(i==11){
			//静态页
			$(".tabtitle h3").text("投后运营");
			$(".tablink .icon").hide();
			tab_show(4);
			hideCurrent('projectProgress:10');
		}
		buttonData();
		
		initFileShow(); //file about
	}
	//点击下一步
	$(".next_box").click(function(){
		var pi = $(".next_box").attr("data-progress");
		i = parseInt(pi)+parseInt(1); 
		$(".next_box").attr("data-progress",i);
		goToProgress();
	})
	//点击上一步
	$(".pre_box").click(function(){
		var pi = $(".next_box").attr("data-progress");
		i = parseInt(pi)-parseInt(1); 
		goToProgress();
		$(".next_box").attr("data-progress",i);

	})
/*function selectFile(input,fileName){
	if(fileName.length> 1 && fileName){
		var ldot = fileName.lastIndexOf("."); 
	var type = fileName.substr(Number(ldot + 1)); 
	var li_f =input.parents("li");
	li_f.find(".cover_box").show();
	li_f.find(".file_btn").hide();
	if (type=="pdf") {
		li_f.find('.file_box').find('img').removeClass("add_img")
		li_f.find('.file_box').find('img').attr(
			"src", '../img/sop_progress/pdf.png');
	}else{
		li_f.find('.file_box').find('img').removeClass("add_img")
		li_f.find('.file_box').find('img').attr(
		"src", '../img/sop_progress/image.png');
	}
	li_f.siblings('.file_box').find('.cover_box').show();
	}else{
		li_f.find('.file_box').find('img').addClass("add_img")
		li_f.find('.file_box').find('img').attr(
		"src", '../img/sop_progress/plus_icon.png');
		li_f.find('.file_box').find('.cover_box').hide();
	}
}
//文件取消
$(".file_box .cover_box .cancel").click(function(event) {
	$(this).parents(".cover_box").hide();
	$(this).parents(".cover_box").siblings('img').addClass("add_img").attr(
		"src", '../img/sop_progress/plus_icon.png');
	$(this).parents("li").find("input").val("");
});*/
//tab点击事件
$(".tab_2").click(function(event) {
	$(this).addClass('on');
	$(this).siblings().removeClass('on');
	$('.file_list').show();
	$(".new_poptxt .bootstrap-table").next().hide();
	$(".new_poptxt .bootstrap-table").hide();
	$(".add_list").hide();
	$(".tablink .icon").show();
});
$(".tab_1").click(function(event) {
	$(this).addClass('on');
	$(this).siblings().removeClass('on');
	$('.file_list').hide();
	$(".new_poptxt .bootstrap-table").show();
	$(".new_poptxt .bootstrap-table").next().show()
	$(".add_list").show();
	$(".tablink .icon").hide();
});
// 添加访谈记录
 
$(".new_poppage").on("click",function(){ 
	var $self = $(this);
	var _url = $self.attr("href");
	var _name=$self.attr("data-name");
	var _type = $self.attr("data-type");
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			$(".tabtitle h3#popup_name").text(_name);
			var arrName=[];
			switch(_type){
			  case "":
				  //访谈结论radio
				  radioSearch(platformUrl.searchDictionaryChildrenItems+"meetingResult");
				  arrName.push("meetingUndeterminedReason");
				  arrName.push("meetingVetoReason");
				  $("#targetView").attr("style","display:block");
				  break;
			  case "meetingType:3":
				  //会议结论radio
				  radioSearch(platformUrl.searchDictionaryChildrenItems+"meeting3Result");
				  arrName.push("meetingVetoReason");
				  meetingColumns();
				  break;
			  case "meetingType:5":
				  //会议结论radio
				  radioSearch(platformUrl.searchDictionaryChildrenItems+"meeting5Result");
				  arrName.push("meetingFollowingReason");
				  arrName.push("meetingVetoReason");
				  meetingColumns();
				  break;
			  default:
				  arrName.push("meetingUndeterminedReason");
			      arrName.push("meetingVetoReason");
				  radioSearch(platformUrl.searchDictionaryChildrenItems+"meetingResult");
				  meetingColumns();
			}
			//结论原因下拉框的值
			selectDict(arrName);
			//判断是否选择其他原因  			
			reason('select[name="meetingUndeterminedReason"]','meetingUndeterminedReason:2');
			reason('select[name="meetingVetoReason"]','meetingVetoReason:5');
		}//模版反回成功执行	
	});
	return false;
});
//原因选择其他时
function reason(obj,value){
	$(obj).change(function(){
		var val=$(this).children("option:selected").val();
		var _this= $(this).parent().siblings(".reason_box").find("input");
		if(val==value){
			_this.attr("required","true").removeAttr("disabled").removeClass("disabled");
		}else{
			_this.val("").attr("disabled","true").addClass("disabled");
		}
	})
}
//会议界面弹出层处理
function meetingColumns(){
	  $("#toobar_time").text("会议时间");
	  $("#toobar_content").text("会议纪要");
	  $("#toobar_voice").text("会议录音");
	  $("#toobar_result").text("会议结论");
	  $("#targetView").attr("style","display:none");
}
/**
 * 填充会议信息数据
 * @param title
 * @param add_title
 * @param meetingType
 */
function toobarData(title,add_title,meetingType){
	$("#add_button a").show();
	$(".tabtitle h3").text(title);
	$("#add_button a").text(add_title);
	$("#meetingType").val(meetingType);
	$("#pop_button").attr("data-name",add_title);
	$("#pop_button").attr("data-type",meetingType);
}
/**
 * 填充会议信息数据
 * @param title
 * @param add_title
 * @param meetingType
 */
function toobarfile(title,index){
	$(".tabtitle h3").text(title);
	$("#add_button a").hide();
	$(".file_list li").css("display","none");
	$(".show_"+index).show();
}
/**
 * 填充会议信息数据
 * @param title
 * @param add_title
 * @param meetingType
 */
function buttonData(){
	var btnTitle="";
	var nextProgress="";
	var currProgress="projectProgress:1";
	var rejectBtn = $("#reject-btn");
	var btn1=$("#btn1");
	var btn2=$("#btn2");
	
	rejectBtn.hide();
	btn1.hide();
	btn2.hide();
	
	if('projectStatus:0' != _project_.projectStatus)
	{
		return;
	}
	
	switch(i){
	case 1:
		btnTitle="启动内部评审";
		currProgress="projectProgress:1";
		nextProgress='projectProgress:2';
		if(_project_.projectProgress == currProgress)
		{
			rejectBtn.show();
			btn1.show();
		}
		break;
	case 2:
		btnTitle="申请CEO评审";
		currProgress="projectProgress:2";
		nextProgress='projectProgress:3';
		if(_project_.projectProgress == currProgress)
		{
			rejectBtn.show();
			btn1.show();
		}
		break;
	case 3:
		btnTitle="申请立项会排期";
		currProgress="projectProgress:3";
		nextProgress='projectProgress:4';
		if(_project_.projectProgress == currProgress)
		{
			rejectBtn.show();
			btn1.show();
		}
		break;
	case 4:
		btnTitle="进入会后商务谈判";
		currProgress="projectProgress:4";
		nextProgress='projectProgress:11';
		if(_project_.projectProgress == currProgress)
		{
			rejectBtn.show();
			btn1.show();
		}
		break;
	case 5:
		btnTitle="签订投资协议书（闪投）";
		currProgress="projectProgress:11";
		nextProgress='projectProgress:8';
		btn2.text("签订投资意向书（投资）");
		btn2.data("next-progress","projectProgress:5");
		if(_project_.projectProgress == currProgress)
		{
			rejectBtn.show();
			btn1.show();
			btn2.show();
		}
		break;
	case 6:
		btnTitle="进入尽职调查";
		currProgress="projectProgress:5";
		nextProgress='projectProgress:6';
		if(_project_.projectProgress == currProgress)
		{
			btn1.show();
		}
		break;
	case 7:
		btnTitle="申请投决会排期";
		currProgress="projectProgress:6";
		nextProgress='projectProgress:7';
		if(_project_.projectProgress == currProgress)
		{
			rejectBtn.show();
			btn1.show();
		}
		break;
	case 8:
		btnTitle="签订投资协议";
		currProgress="projectProgress:7";
		nextProgress='projectProgress:8';
		btn2.text("进入股权交割");
		btn2.data("next-progress","projectProgress:9");
		if(_project_.projectProgress == currProgress)
		{
			rejectBtn.show();
			btn1.toggle(btnData.next1Valid);
			btn2.toggle(btnData.next2Valid);
		}
		break;
	case 9:
		btnTitle="进入尽职调查";
		currProgress="projectProgress:8";
		nextProgress='projectProgress:6';
		btn2.text("进入股权交割");
		btn2.data("next-progress","projectProgress:9");
		if(_project_.projectProgress == currProgress)
		{
			btn1.toggle(btnData.next1Valid);
			btn2.toggle(btnData.next2Valid);
		}
		break;
	case 10:
		btnTitle="进入投后运营";
		currProgress="projectProgress:9";
		nextProgress='projectProgress:10';
		if(_project_.projectProgress == currProgress)
		{
			btn1.show();
		}
		break;
    case 11:
		break;
	default:
	
	}
	btn1.text(btnTitle);
	btn1.data("next-progress",nextProgress);
	if(_project_.projectProgress == currProgress)
	{
		rejectBtn.toggleClass('disabled',btnData.rejectValid==false);
		btn1.toggleClass('disabled',btnData.next1Valid==false);
		btn2.toggleClass('disabled',btnData.next2Valid==false);
	}
}
function whichOne(index){
	if(index=="8")
	{
		if(_project_.businessTypeCode == 'TZ')
		{
			return 'tzxy';
		}
		return '';
	}else if(index=="9")
	{
		if(_project_.businessTypeCode == 'ST')
		{
			return 'jzdc';
		}
		return '';
	}else{
		return 'st';
	}
}
$("#btn1,#btn2").click(function(){
	if($(this).hasClass('disabled'))
	{
		return;
	}
	var next = $(this).data('next-progress');
	nextProgress(this,next);
});
/**
 * 项目阶段推进
 * @param nextProgress 下一阶段编码。 e.g. projectProgress:2
 * @returns
 */
function nextProgress(btn,nextProgress)
{
	$(btn).addClass('disabled');
	sendPostRequestByJsonObj(
		platformUrl.projectStageChange,
		{id:projectId, stage:nextProgress},
		function(data){
			$(btn).removeClass('disabled');
			if(data.result.status == 'OK')
			{
				layer.msg('提交成功');
				refreshIndex(true);
			}
			else if(data.result.message != null)
			{
				layer.msg(data.result.message);
			}
		}
	);
}

/***
 * 因为之前项目阶段是按照顺序进行来处理,现增加商务谈判阶段数据库为11
 * 而页面是在5阶段
 * 所以单独处理渲染,不走下一步方法
 * @param progress
 */
function showProgress(progress){
	var i = 1;
	var strs= new Array();
	if(progress.indexOf(":") > 0){
		istr = progress.split(":");
		if(istr[1]){
			i = istr[1];
		}
	}
	switch(i){
	   case "1":
		    interviewList();
			toobarData("接触访谈","添加访谈记录","");
			tab_show(1);
			$(".next_box").attr("data-progress",1);
			$(".pre_box").hide();
			$(".tablink .icon").hide();
			hideCurrent('projectProgress:1');
			break;
	   case "2":
		    meetList("meetingType:1");
			toobarData("内部评审","添加内部评审","meetingType:1");
			tab_show(1);
			 $(".next_box").attr("data-progress",2);
			 $(".tablink .icon").hide();
			hideCurrent('projectProgress:2');
			break;
	   case "3":
		    meetList("meetingType:2");
			toobarData("CEO评审","添加CEO评审","meetingType:2");
			tab_show(1);
			$(".next_box").attr("data-progress",3);
			$(".tablink .icon").hide();
			hideCurrent('projectProgress:3');
			break;
	   case "4":
		    meetList("meetingType:3");
			toobarData("立项会","添加立项会","meetingType:3");
			$(".tablink .icon").hide();
			tab_show(3);
			$(".next_box").attr("data-progress",4);
			hideCurrent('projectProgress:4');
			break;
	   case "5":
		  
		    $(".tabtitle h3").text("投资意向书");
		    $(".tablink .icon").show();
		    tab_show(2);
		    $(".next_box").attr("data-progress",6);
		    hideCurrent('projectProgress:5');
		    break;
	   case "6":
		    $(".tabtitle h3").text("尽职调查");
		    $(".tablink .icon").show();
		    tab_show(2);
		    $(".next_box").attr("data-progress",7);
		    hideCurrent('projectProgress:6');
		    break;
	   case "7":
		   meetList("meetingType:4");
		    toobarData("投决会","添加投决会","meetingType:4");
		    $(".tablink .icon").hide();
		    tab_show(1);
		    $(".next_box").attr("data-progress",8);
		    hideCurrent('projectProgress:7');
		    break;
	   case "8":
		    $(".tabtitle h3").text("投资协议");
		    $(".tablink .icon").show();
		    tab_show(2);
		    $(".next_box").attr("data-progress",9);
		    hideCurrent('projectProgress:8');
		    break;
	   case "9":
		    $(".tabtitle h3").text("股权交割");
		    $(".tablink .icon").show();
		    tab_show(2);
		    $(".next_box").attr("data-progress",10);
		    hideCurrent('projectProgress:9');
		    break;
	   case "10":
		    $(".tabtitle h3").text("投后运营");
		    tab_show(4);
		    $(".next_box").attr("data-progress",11);
		    $(".tablink .icon").hide();
		    $(".next_box").hide();
		    hideCurrent('projectProgress:10');
		    break;
	   case "11":
		    meetList("meetingType:5");
		    $(".tabtitle h3").text("会后商务谈判");
		    $(".tablink .icon").hide();
		    tab_show(1);
		    toobarData("会后商务谈判","添加会议记录","meetingType:5");
		    $(".next_box").attr("data-progress",5);
		    hideCurrent('projectProgress:11');
		    break;
	   default :
	        break;
	
	}
	initFileShow(); //file about
}
//会议结论原因数据字段获取
function radioSearch(url, name){
	sendGetRequest(url,null, function(data){
		radionDiv(data);
	});
	
}
function radionDiv(data){
	var dd=$("#resultRadion");
	$.each(data.entityList, function(i, value){
		var lable;
		if(i==0){
			lable='<label><input name="interviewResult" type="radio" required data-msg-required="<font color=red>*</font><i></i>必选" value='+value.code+' />'+value.name+'</label>';
		}else{
			lable='<label><input name="interviewResult" type="radio" value='+value.code+' />'+value.name+'</label>';
		}
		var htmlDiv= 
		'<div id="div_'+i+'">'+lable
		     var parentCode=changeSelect(value);
	       if(parentCode!=""){
	    	   var htmlSelect='<div class="resel_box"><select disabled="disabled" class="disabled" name="'+parentCode+'" id="'+parentCode+'" data-msg-required="<font color=red>*</font><i></i>必填">'+
	           '<option value="">请选择原因</option>'+
	           '</select></div>'+
	         '<div class="reason_box"><input type="text" disabled="disabled" name="reasonOther_'+i+'" id="reasonOther" class="txt disabled" placeholder="请填写其它原因" data-msg-required="<font color=red>*</font><i></i>必填" maxLength="50" data-rule-reasonOther="true" data-msg-reasonOther="<font color=red>*</font><i></i>必填"></div>';
	    	 htmlDiv=htmlDiv+htmlSelect;
		  }
	     htmlDiv=htmlDiv+'</div>';	     
		dd.append(htmlDiv);		
//		判断选择原因并清空select
		$("#resultRadion input[type='radio']").click(function(){
			var _select = $(this).parent("label").next().find("select");
			var oh_select = $(this).parents("#resultRadion").find("select");
//			var _input = $(this).parent().siblings(".reason_box").find("input[type='text']");
			var oh_input = $(this).parent().parent().siblings().find("input[type='text']");
//			_input.val("").removeClass("disabled").removeAttr("disabled");
			oh_input.val("").addClass("disabled").attr("disabled","true");
			oh_select.val("").addClass("disabled").attr("disabled","true");
			_select.attr("required","true");
			_select.removeClass("disabled").removeAttr("disabled")
		})
//		清空input的val
		/*$("#resultRadion input[type='radio']").change(function(){
			var se_input = $(this).parents("#resultRadion").find("input[type='text']");
			var se_select = $(this).parents("#resultRadion").find("select");
			se_input.val("").addClass("disabled").attr("disabled","true");
			se_select.val("").addClass("disabled").attr("disabled","true");
		})*/
	});
}

/**
 * 此方法判断该会议结果后是有会议结论的下拉框
 * @param value
 */
function changeSelect(value){
	//meeting5Result:1:跟进中
	//meeting5Result:2:否决
	//meeting3Result:6:否决
	//meetingResult:2:待定
	//meetingResult:3:否决
	var parentCode="";
	if(value.code=='meeting5Result:1'){
		parentCode="meetingFollowingReason";
	 }
	if(value.code=='meetingResult:2'){
		parentCode="meetingUndeterminedReason";
	}
	if(value.code=='meetingResult:3'||value.code=='meeting5Result:2'||value.code=='meeting3Result:6'){
		parentCode="meetingVetoReason";
	}
	return parentCode;
}
function selectDict(arr){
	if(null!=arr){
		for(var i=0;i<arr.length;i++){
			createDictionaryOptions(platformUrl.searchDictionaryChildrenItems+arr[i],arr[i]);
		}
	}
}
/**
 * 判断该项目阶段是否为走过的阶段
 */
function  isPassCurrentProgress(currentProgress){
	var progress = _project_.progressHistory;
	if(progress){
	   return progress.indexOf(currentProgress) >= 0;
	}
	return false;
}
/**
 * 判断该阶段是否到未到此阶段
 * @param progress
 */
function  hideCurrent(progress){
	if(isPassCurrentProgress(progress)){
		$(".not_stage").hide();
		$(".pop .poptxt").css({
			"height":"auto",
			"overflow":"visible"
			
		});
	}else{
		$(".not_stage").show();
		$(".pop .poptxt").css({
			"height":"300px",
			"overflow":"hidden"
			
		});
	}
	if(currentProgress(progress)){
		if($(".tabtitle .current_progress").length == 0)
		{
			$(".tabtitle h3").after('<span class="current_progress">(当前阶段)</span>');
		}
	}else{
		$(".tabtitle .current_progress").remove();
	}
}
function currentProgress(currentProgress){
    var progress = _project_.projectProgress;
	return progress == currentProgress;
}

