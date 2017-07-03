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
	sendGetRequest(platformUrl.detailProject + projectId, {}, function(data){
		_project_ = data.entity;
	});
	
	var i = $(".next_box").attr("data-progress"); //获取阶段值
	var progress = $(".next_box").attr("data-project-progress"); 
	//显示当前阶段
	showProgress(progress);
	
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
		console.log(status)
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
			tab_show(1);
		}else if(i==2){
			meetList("meetingType:1");
			toobarData("内部评审","添加内部评审","meetingType:1");
			tab_show(1);
		}else if(i==3){
			meetList("meetingType:2");
			toobarData("CEO评审","添加CEO评审","meetingType:2");
			tab_show(1);
		}else if(i==4){
			meetList("meetingType:3");
			toobarData("立项会","添加立项会","meetingType:3");
			toobarfile("投资意向书",4);
			tab_show(3);
		}else if(i==5){
			$(".tabtitle h3").text("会后商务谈判");
			toobarData("会后商务谈判","会后商务谈判","meetingType:3");
			tab_show(1);
		}else if(i==6){
			$(".tabtitle h3").text("投资意向书");
			toobarfile("投资意向书",6);
			tab_show(2);
		}else if(i==7){
			$(".tabtitle h3").text("尽职调查");
			toobarfile("尽职调查",7);
			tab_show(2);
			//尽职调查  上传附件
		}else if(i==8){
			meetList("meetingType:4");
			toobarData("投决会","添加投决会","meetingType:4");
			tab_show(1);
		}else if(i==9){
			$(".tabtitle h3").text("投资协议");
			toobarfile("投资协议",9);
			tab_show(2);
			//投资协议  上传附件
		}else if(i==10){
			$(".tabtitle h3").text("股权交割");
			toobarfile("投资协议",10);
			tab_show(2);
			//股权交割   上传附件
		}else if(i==11){
			//静态页
			$(".tabtitle h3").text("投后运营");
			tab_show(4);
		}
		buttonData(i);
		
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
});
$(".tab_1").click(function(event) {
	$(this).addClass('on');
	$(this).siblings().removeClass('on');
	$('.file_list').hide();
	$(".new_poptxt .bootstrap-table").show();
	$(".new_poptxt .bootstrap-table").next().show()
	$(".add_list").show();
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
			$("#popup_name").text(_name);
			switch(_type){
			  case "":
				  $("#targetView").attr("style","display:block");
				  break;
			  default:
				  $("#toobar_time").text("会议时间");
				  $("#toobar_content").text("会议纪要");
				  $("#toobar_voice").text("会议录音");
				  $("#toobar_result").text("会议结论");
				  $("#targetView").attr("style","display:none");
			}
		}//模版反回成功执行	
	});
	return false;
});
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
function buttonData(i){
	var btnTitle="";
	var nextProgress="";
	var currProgress="projectProgress:1";
	var btnTitle5="";
	var btnHref5="";
	var btn1=$("#btn1");
	var btn2=$("#btn2");
	var isShow=false;
	switch(i){
	case 1:
		btnTitle="启动内部评审";
		currProgress="projectProgress:1";
		nextProgress='projectProgress:2';
		isShow=false;
		break;
	case 2:
		btnTitle="申请CEO评审";
		currProgress="projectProgress:2";
		nextProgress='projectProgress:3';
		isShow=false;
		break;
	case 3:
		btnTitle="申请立项会排期";
		currProgress="projectProgress:3";
		nextProgress='projectProgress:4';
		isShow=false;
		break;
	case 4:
		btnTitle="进入会后商务谈判";
		currProgress="projectProgress:4";
		nextProgress='projectProgress:11';
		isShow=false;
		break;
	case 5:
		btnTitle="签订投资协议书（闪投）";
		currProgress="projectProgress:11";
		nextProgress='projectProgress:8';
		 var result=whichOne(5);
		 if("st"){
			 btnTitle="签订投资意向书（投资）";
			 nextProgress='projectProgress:5';
		 }
		isShow=true;
		break;
	case 6:
		btnTitle="进入尽职调查";
		currProgress="projectProgress:5";
		nextProgress='projectProgress:6';
		isShow=false;
		break;
	case 7:
		btnTitle="申请投决会排期";
		currProgress="projectProgress:6";
		nextProgress='projectProgress:7';
		isShow=false;
		break;
	case 8:
		currProgress="projectProgress:7";
		 var result=whichOne(8);
		 if(result=="tzxy"){
			btnTitle="签订投资协议";
			nextProgress='projectProgress:8';
		 }else{
			btnTitle="进入股权交割";
			nextProgress='projectProgress:9'; 
		 }
		 isShow=false;
		break;
	case 9:
		 var result=whichOne(9);
		 currProgress="projectProgress:8";
		 if(result=="jzdc"){
			btnTitle="进入尽职调查";
			nextProgress='projectProgress:6';
		 }else{
			btnTitle="进入股权交割";
			nextProgress='projectProgress:9'; 
		 }
		 isShow=false;
		break;
	case 10:
		btnTitle="进入投后运营";
		currProgress="projectProgress:9";
		nextProgress='projectProgress:10';
		isShow=false;
		break;
    case 11:
		break;
	default:
	
	}
	btn1.text(btnTitle);
	btn1.data("next-progress",nextProgress);
	if('projectStatus:0'== _project_.projectStatus && _project_.projectProgress == currProgress)
	{
		btn1.removeClass('none');
	}
	else
	{
		btn1.addClass('none');
	}
}
function whichOne(index){
	if(index=="8")
	{
		if(_project_.businessTypeCode == 'ST')
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
$("#btn1").click(function(){
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
				_project_=data.entity;
				refreshIndex();
			}
			else if(data.result.message != null)
			{
				layer.msg(data.result.message);
			}
		}
	);
}
function refreshIndex()
{
	for(var j=0;j<flow.length;j++)
	{
		if(flow[j] == _project_.projectProgress)
		{
			i=j+1;
			break;
		}
	}
	goToProgress();
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
			break;
	   case "2":
		    meetList("meetingType:1");
			toobarData("内部评审","添加内部评审","meetingType:1");
			tab_show(1);
			 $(".next_box").attr("data-progress",2);
			break;
	   case "3":
		    meetList("meetingType:2");
			toobarData("CEO评审","添加CEO评审","meetingType:2");
			tab_show(1);
			 $(".next_box").attr("data-progress",3);
			break;
	   case "4":
		    meetList("meetingType:3");
			toobarData("立项会","添加立项会","meetingType:3");
			tab_show(3);
			 $(".next_box").attr("data-progress",4);
			break;
	   case "5":
		    $(".tabtitle h3").text("投资意向书");
		    tab_show(2);
		    $(".next_box").attr("data-progress",6);
		    break;
	   case "6":
		    $(".tabtitle h3").text("尽职调查");
		    tab_show(2);
		    $(".next_box").attr("data-progress",7);
		    break;
	   case "7":
		    toobarData("投决会","添加投决会","meetingType:4");
		    tab_show(1);
		    $(".next_box").attr("data-progress",8);
		    break;
	   case "8":
		    $(".tabtitle h3").text("投资协议");
		    tab_show(2);
		    $(".next_box").attr("data-progress",9);
		    break;
	   case "9":
		    $(".tabtitle h3").text("股权交割");
		    tab_show(2);
		    $(".next_box").attr("data-progress",10);
		    break;
	   case "10":
		    $(".tabtitle h3").text("投后运营");
		    tab_show(4);
		    $(".next_box").attr("data-progress",11);
		    break;
	   case "11":
		    $(".tabtitle h3").text("会后商务谈判");
		    tab_show(1);
		    $(".next_box").attr("data-progress",5);
		    break;
	   default :
	        break;
	
	}
	
	initFileShow(); //file about
}
