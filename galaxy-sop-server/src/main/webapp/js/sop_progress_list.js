	var i=$(".next_box").attr("data-progress")  //获取阶段值
	progressBtnToggle();
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
	//阶段加载
	function progress(){
		progressBtnToggle()					
		if(i==1){
			$(".tabtitle h3").text("接触访谈");
			//interviewList();
		}else if(i==2){
			$(".tabtitle h3").text("内部评审");
			//CEOlist();
		}else if(i==3){
			$(".tabtitle h3").text("CEO评审");
		}else if(i==4){
			$(".tabtitle h3").text("立项会");
			//含tab切换
		}else if(i==5){
			$(".tabtitle h3").text("会后商务谈判");
		}else if(i==6){
			$(".tabtitle h3").text("投资意向书");
		}else if(i==7){
			$(".tabtitle h3").text("尽职调查");
			//尽职调查  上传附件
		}else if(i==8){
			$(".tabtitle h3").text("投决会");
		}else if(i==9){
			$(".tabtitle h3").text("投资协议");
			//投资协议  上传附件
		}else if(i==10){
			$(".tabtitle h3").text("股权交割");
			//股权交割   上传附件
		}else if(i==11){
			//静态页
			$(".tabtitle h3").text("投后运营");
			
		}								
	}
	//点击下一步
	$(".next_box").click(function(){
		i++;
		$(".next_box").attr("data-progress",i);
		progress();
		
	})
	//点击上一步
	$(".pre_box").click(function(){
		i--;
		progress();

	})

