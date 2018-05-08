function leicj(){
  //我的日程弹窗搜索框显示隐藏
  $('.show_more a').on("click",function(){ 
	  
    var $self=$(this),
        _name = $self.attr("data-btn"),
        _siblings = $self.siblings();
        //点击展开
          if(_name=="show"){
          _siblings.show();
          $self.hide();
          $self.parent().siblings("[data-btn='box']").show();
        };
        //点击隐藏
        if(_name=="hide"){
          _siblings.show();
          $self.hide();
          $self.parent().siblings("[data-btn='box']").hide();
        }
        return false;
  })
 

    //鼠标滑上显示提示框
   $('.tarea').hover(function(){
      if($(this).text()==''){
        $('.tips').hide();
      }
      else{
        $('.tips').show();
      }
    });
  $('.tarea').mousemove(function(e){
   var top = e.pageY+20;
   var left = e.pageX+20;
   $('.tips').css({
    'top' : top + 'px',
    'left': left+ 'px'
   });
  });
  
  $('.tarea').mouseout(function(){
   $('.tips').hide();
  });	
 //添加日程弹窗
         $(".bottom_l .nav_list").click(function(event) {
            $(this).siblings().stop().slideToggle().parent().siblings().children('dd').slideUp();
        });
        
        $(".nav1 .nav,.nav2 .nav,.nav3 .nav,.nav4 .nav").each(function(index, el) {
            $(this).click(function(event) {
                $(this).addClass('on').siblings().removeClass('on');
                $(".bottom_r .block").eq(index).show().siblings().hide();
            });
        });

//投后菜单显示隐藏    
    $(".pagebox .lft div").click(function(event) {
          $(this).siblings().stop().slideToggle();
          $(this).children('i').toggleClass('hide');
        });
//日期控件
  $("#datetimepicker3").on("click",function(e){
    e.stopPropagation();
    $(this).lqdatetimepicker({
      css : 'datetime-day',
      dateType : 'D',
      selectback : function(){
      }
    });
  });
  };		
/*  function leicj_prj(){
	  //我的日程弹窗搜索框显示隐藏
	  $('.show_more_prj a').on("click",function(){ 
		$(".fmdlSearch").hide();
	    var $self=$(this),
	        _name = $self.attr("data-btn"),
	        _siblings = $self.siblings();
	        //点击展开
	          if(_name=="show"){
	          _siblings.show();
	          $self.hide();
	          $self.parent().siblings("[data-btn='box']").show();
	      	$(".searchall_top .fmdlSearch").show();
	        };
	        //点击隐藏
	        if(_name=="hide"){
	          _siblings.show();
	          $self.hide();
	          $self.parent().siblings("[data-btn='box']").hide();
	      	$(".searchall_bottom .fmdlSearch").show();
	        }
	        return false;
	  })
  }*/