
function delete_row(ele){
    var div=$(ele).closest('div[data-flag]');
    div.remove();
}
//查看团队成员弹窗
function showMemberRow(ele){
	
    var row = $(ele).closest('tr');
     $.getHtml({
    		url:"/sop/html/team_xk.html",//模版请求地址
    		data:"",//传递参数
    		okback:function(){
    			var pop=$("#qualifications_popup_name");
    			$.each($("#detail-form").find("span"),function(){
    				var ele = $(this);
    				var name = ele.attr('name');
    				ele.text(row.data(name));
    			});
    			pop.text(row.data(pop.attr("name")));
    			//填充学习经历
                var obj = row.data("obj")
                var studyList = obj.studyList;
                if(studyList.length>0){
                    var study = getStudyList("add",studyList)
                    $("#team_learn").append(study);
                }
                var startupList = obj.startupList;
                if(startupList.length>0){
                    var startup = getStartupList("add",startupList);
                    $("#team_startup").append(startup);
                }
                var workList = obj.workList;
                if(workList.length>0){
                   var work = getWorkList("add",workList);
                   $("#team_work").append(work);
                }
              
    		}
     })

}
 //编辑成员简历弹窗
function editMemberRow(ele){
    var row = $(ele).closest('tr');
    var index = row.index();
    row.data("index",index);
    $.getHtml({
		url:"/sop/html/team_compile.html",//模版请求地址
		data:"",//传递参数
		okback:function(){
			$("#qualifications_popup_name").text("编辑简历");
			$.each($("#detail-form").find("input, select, textarea"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				ele.val(row.data(name));
				var tagName = $(this).get(0).tagName;
                if(tagName=="SELECT" && (ele.val()==null)){
                    ele.find("option:eq(0)").attr("selected",true);
                }
			});
			$("input:radio[name='field3'][data-value='" + row.data("field3") + "']").prop("checked", "checked");
            //填充学习经历
            var obj = row.data("obj")

            var studyList = obj.studyList;
            if(studyList.length>0){
                var study = getStudyList("edit",studyList)
                $("#team_learn").append(study);
            }

            var startupList = obj.startupList;
            if(startupList.length>0){
                var startup = getStartupList("edit",startupList);
                $("#team_startup").append(startup);
            }
            var workList = obj.workList;
            if(workList.length>0){
               var work = getWorkList("edit",workList);
               $("#team_work").append(work);
            }
		}//模版反回成功执行
	});
}

function getStudyList(flag,studyList){
        var study = "";
        $(studyList).each(function(){
               var o = $(this)[0];
               for(var item in o){

                           if(item.indexOf("field")>-1){

                               if(!o[item]){
                                   o[item]=""
                               }
                           }
                         }
               var tmp = "<div class=\"team_div\" data-flag><span name='id'  style='display:none'>"+o.id+"</span>"+
                        "<div class='team_p_one'><span class='team_ico team_ico_dot' ></span><span name='field1'>"+o.field1+"</span></div>"+
                        "<div>"+
                            "<ul style='margin-left:14px;'>"+
                                "<li><span name='field2'>"+o.field2+"</span></li>"+
                                "<li><span name='field3'>"+o.field3+"</span>&nbsp;·&nbsp;<span name='field4'>"+o.field4+"</span></li>"+

                            "</ul>";
                            if(flag=="edit"){
                                var str ="<div class='team_click'>"+
                                         "<span class='blue'  onclick='editStudy(this)' >编辑</span>"+
                                         "<span class='blue' onclick='delete_row(this)'>删除</span>"+
                                         "</div>"
                                tmp += str;
                            }

          tmp +="</div></div>"
          study += tmp;
        })
        return study;
}
function getStartupList(flag,startupList){

        var startup ="";
        $(startupList).each(function(){
             var o = $(this)[0];
             for(var item in o){

             if(item.indexOf("field")>-1){
                 if(!o[item]){
                     o[item]=""
                 }
             }
           }
             var str = "<div class=\"team_div\" data-flag data-a='"+o.field3+"' data-b='"+o.field4+"' data-c='"+o.field5+"' data-d='"+o.field6+"' >"+
                           "<span name='id' style='display:none'>"+o.id+"</span>"+
                           "<div class='team_p_one'><span class='team_ico team_ico_dot'></span><span name='field1'>"+o.field1+"</span><span>～</span><span name='field2'>"+o.field2+"</span>";

                         str +=  "</div><div class='team_p_two'><ul><li data-mix style='margin-bottom:0px;'>";
                        var ls = [];
                        if(o.field3){
                            ls.push("担任职务为"+o.field3)
                        }
                        if(o.field4){
                            if(o.field4 == 'undefined'){
                            	ls.push("未知")
                            }else{
                            	ls.push(o.field4)
                            }
                        }
                        if(o.field5){
                            ls.push("创立时股权比例为"+o.field5+"%")
                        }
                        if(o.field6){
                            ls.push("成功或失败或离职的原因为"+o.field6)
                        }
                        var temp = "";
                        $(ls).each(function(i,e){
                            if(i!= (ls.length-1)){
                                temp += e+","
                            }else{
                                temp += e;
                            }
                        })

            str=str +temp+"</li></ul>";
            if(flag=="edit"){
                str+="<div class='team_click'><span class='blue '  onclick='editStartup(this)' >编辑</span>";
                str+="<span class='blue' onclick='delete_row(this)'>删除</span></div>";
            }
            str+="</div><div class='team_p_two' style='margin-top:0px;'><span>项目概述:</span><span name='field7'>"+o.field7+"</span></div></div>"
            startup += str;
        })
        return startup;

}
function getWorkList(flag,workList){
       var work ="";
       $(workList).each(function(){
         var o =$(this)[0];
         for(var item in o){

            if(item.indexOf("field")>-1){

                if(!o[item]){
                    o[item]=""
                }
            }
          }

         var tmp="<div data-flag class=\"team_div\"><div class='team_p_one'><span class='team_ico team_ico_dot'></span><span name='id' style='display:none'>"+o.id+"</span>";
                if(o.field1){
                   tmp = tmp+"<span name='field1'>"+o.field1+"</span><span>～</span>";
                }

                if(o.field2){
                     tmp = tmp+"<span name='field2'>"+o.field2+"</span>"
                }

                tmp = tmp+ "</div>"+
                    "<ul style='margin-left:14px;'>"+
                        "<li><span name='field3'>"+o.field3+"</span>&nbsp;·&nbsp;<span name='field4'>"+o.field4+"</span></li>"+
                    "</ul>"
                    if(flag=="edit"){
                        var str =  "<div class='team_click'>"+
                                      "<span class='blue '  onclick='editWork(this)'  >编辑</span>"+
                                      "<span class='blue' onclick='delete_row(this)' >删除</span>"+
                                  "</div>"
                        tmp += str;
                    }
          tmp += "<ul style='margin-left:14px;'>"+
          "<li><span  name='field5'>"+o.field5+"</span></li>"+
          "</ul></div>"
          work += tmp;
       })
       return work;
}
 //编辑学习经历弹窗
 function editStudy(ele){
          var div=$(ele).closest('div[data-flag]');
           var index = div.index();
	      $.getHtml({
        		url:"/sop/html/team_learn.html",//模版请求地址
        		data:"",//传递参数
        		okback:function(){
        			$("#team_learn_name").html('编辑学习经历');
                    var json = getData(div);
        			var list = div.find("*[name]");
                    $(list).each(function(){
                          var key = "";
                          var value = "";
                          key = $(this).attr("name");
                          value = $(this).text();
                          json[key]=value;
                          json["index"]=index;
                    })

                    $.each($("#learn_form").find("input, select, textarea"),function(){
                        var ele = $(this);
                        var name = ele.attr('name');
                        if(name=="field1"){
                        	if(json[name] && json[name] != "未知"){
                        		if(json[name].indexOf("毕业")>0){
                        			var len=json[name].length;
                                    ele.val(json[name].substring(0,len-2));
                        		}else{
                        			ele.val(json[name]);
                        		}
                            }
                        }else{
                        	if(name.indexOf("field")>-1){
                                if(json[name] && json[name] != "未知"){
                                    ele.val(json[name]);
                                }
                            }else{
                                ele.val(json[name]);
                            }
                        }
                        
                    });
                    $("input:radio[name='field2'][value='" + json["field2"] + "']").prop("checked", "checked");

        		}
          })

 }
 //编辑工作经历弹窗
 function editWork(ele){
          var div=$(ele).closest('div[data-flag]');
          var index = div.index();
	      $.getHtml({
        		url:"/sop/html/team_work.html",//模版请求地址
        		data:"",//传递参数
        		okback:function(){
        			$("#team_work_name").html('编辑工作经历')
                    var json = getData(div);
        			var list = div.find("*[name]");
                    $(list).each(function(){
                          var key = "";
                          var value = "";
                          key = $(this).attr("name");
                          value = $(this).text();
                          json[key]=value;
                          json["index"]=index;
                    })

                    $.each($("#work_form").find("input, select, textarea"),function(){
                        var ele = $(this);
                        var name = ele.attr('name');
                        if(name.indexOf("field")>-1){
                            if(json[name] && json[name] != "未知"){
                                ele.val(json[name]);
                            }
                        }else{
                            ele.val(json[name]);
                        }

                    });
                    //文本框剩余字数
        			$.each($(".team_textarea"),function(){
        				var len=$(this).val().length;
        				var initNum=$(this).siblings('.num_tj').find("span").text();
        				$(this).siblings('.num_tj').find("span").text(initNum-len);
        			})
        		}
          })

          return false;
 }
 //编辑创业经历弹窗
  function editStartup(ele){
           var div=$(ele).closest('div[data-flag]');
           var index =div.index();

 	       $.getHtml({
         		url:"/sop/html/team_startup.html",//模版请求地址
         		data:"",//传递参数
         		okback:function(){
         			$("#team_startup_name").html('编辑创业经历');

                    var json = getData(div);
         			var list = div.find("*[name]");
                     $(list).each(function(){
                           var key = "";
                           var value = "";
                           key = $(this).attr("name");
                           value = $(this).text();
                           json[key]=value;
                           json["index"]=index;
                     })
                     $.each($("#startup_form").find("input, select, textarea"),function(){
                         var ele = $(this);
                         var name = ele.attr('name');
                         if(name.indexOf("field")>-1){
                             if(json[name] && json[name] != "未知"){
                                 ele.val(json[name]);
                             }else{
                            	 json[name]='';
                             }
                         }else{
                             ele.val(json[name]);
                         }
                     });
                   
                    $("#startup_form").find("[name='field1']").val(json["field1"]);
                    $("#startup_form").find("[name='field2']").val(json["field2"]);
                    $("#startup_form").find("[name='field3']").val(div.attr("data-a"));
                    $("#startup_form").find("[name='field4'][data-value='" + div.attr("data-b") + "']").prop("checked", "checked");
                    $("#startup_form").find("[name='field5']").val(div.attr("data-c"));
                    $("#startup_form").find("[name='field6']").val(div.attr("data-d"));
                    $("#startup_form").find("[name='field7']").text(json["field7"]);
//         			判断是否选择否
         			/*if($('input[name=field4]:checked').attr('attr-name') == "no"){
         				$('.team_stock_on').hide();
         			}else{
         				$('.team_stock_on').show();
         			}*/
         			console.log($(".team_textarea").length);
         			//文本框剩余字数
                    $.each($(".team_textarea"),function(){
        				var len=$(this).val().length;
        				var initNum=$(this).siblings('.num_tj').find("span").text();
        				$(this).siblings('.num_tj').find("span").text(initNum-len);
        			})
         		}
 	
           })
          
           return false;
  }


