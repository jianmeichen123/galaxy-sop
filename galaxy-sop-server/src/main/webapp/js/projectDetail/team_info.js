//团队字典回显
var map_edu = selectCache("team-members","field2")
var map_pos = selectCache("team-members","field5")
var map_sex = selectCache("team-members","field3")
var map_stu = selectCache("study-experience","field4")
/**
团队select 字典缓存
*/
function selectCache(subCode,filed){
    var map = {};
	sendGetRequest(platformUrl.getDirectory+"1303"+'/'+subCode+"/"+filed,null,
			function(data) {
				var result = data.result.status;
				if (result == 'OK')
				{
					var dataMap = data.userData;
				    var list=dataMap[filed];
				    var name=""
					$.each(list, function(i, value){
					     map[value.id]=value.name;
					});
				}
			})
			return map;
}
//团队整体显示
sendGetRequest(platformUrl.queryMemberList+"1302/"+projectInfo.id,null,function(data){
    var result = data.result.status;
    if (result == 'OK')
    {
       var entityList = data.entityList;
        $(entityList).each(function(){
            if($(this)[0]["tableHeader"]){
                data = $(this)[0]
            }
        })
        buildMemberTable(data);
        var table=$("table[data-code=\"team-members\"]");
        check_table_tr_edit(table);
    }
 })
		
function buildMemberTable(title){
        //列表Header
	
    	if(title.tableHeader)
    	{
    		var header = title.tableHeader;
    		var tables = $("table.team_info");
    		$.each(tables,function(){
    			var table = $(this);
    			table.empty();
    			var tr="<tr>";
    			for(var key in header)
    			{   //过滤掉电话字段
                    if(key.indexOf('field')>-1 && key != "field4")
                    {
                        tr +='<th data-field-name="'+key+'">'+header[key]+'</th>';
                    }
                }
    			if(isTransferings=="false"){
    				tr +='<th data-field-name="opt">操作</th>';
    			}
                tr+="</tr>";
    			table.append(tr);
    		});
    	}
    	//列表Row
    	if(title.dataList)
    	{
    	    var list = title.dataList;
    		$.each(list,function(){
    			var row = this;
    			var tables = $("table.team_info");
    			$.each(tables,function(){
    				var table = $(this);
    				var headerList = table.find('tbody').find('tr:eq(0)').find("th[data-field-name!='opt']");
    				var tr = buildMemberRow(headerList,row);
    				table.append(tr);
    			});
    		});
    	}
}

/*创建团队表格*/
function buildMemberRow(headerList,row)
{
	var tr=$("<tr data-row-id='"+row.id+"'></tr>");
	tr.data("person",row);
	if(row.other && row.other!=''){
		row.field2=row.field2+"-"+row.other;
	}else{
		row.field2=row.field2;
	}
    for(var key in row)
   	{
    	//设置data
   		tr.data(key,row[key]);
   	}
    $(headerList).each(function(){
        var key = $(this).attr("data-field-name");
        tr.data(key,row[key]);
        if(key.indexOf('field')>-1)
        {
        	if(row[key]){
                //select字段在页面缓存根据id取value
                if(key == "field2"){
                	if(row[key].indexOf("1363")>-1){
                		var field=row.field2.split("-");
                		if(field.length>1){
                			map_edu[row[key]]=row.field2.substring(5,row.field2.length);
                		}
                	}
                	if(map_edu[row[key]]==""||map_edu[row[key]]==undefined||map_edu[row[key]]=="undefined"){
                		map_edu[row[key]]="—";
                	}
                     tr.append('<td data-field-name="'+key+'">'+map_edu[row[key]]+'</td>');
                     return;
                }else if(key == "field5"){
                	if(map_pos[row[key]]==""||map_pos[row[key]]==undefined||map_pos[row[key]]=="undefined"){
                		map_pos[row[key]]="—"
                	}
                     tr.append('<td data-field-name="'+key+'">'+map_pos[row[key]]+'</td>');
                     return;
                }else if(key == "field3"){
                	if(map_sex[row[key]]==""||map_sex[row[key]]==undefined||map_sex[row[key]]=="undefined"){
                		map_sex[row[key]]="—"
                	}
                    tr.append('<td data-field-name="'+key+'">'+map_sex[row[key]]+'</td>');
                    return;
               }else{
                     tr.append('<td data-field-name="'+key+'">'+row[key]+'</td>');
                }
            }else{
                tr.data(key,"未知");
                tr.append('<td data-field-name="'+key+'">—</td>');
            }
        }

    })
    if(isTransferings=="false"){
    	var td = $('<td data-field-name="opt"></td>');
        td.append('<label class="blue" data-btn="btn" onclick="showMemberRow(this)">查看</label>');
        if(isCreatedByUser=='true'){
        	 td.append('<label class="blue" data-btn="btn" onclick="editMemberRow(this)">编辑</label>');
             td.append('<label class="blue" data-btn="btn" onclick="delMemberRow(this)">删除</label>');
        }
        tr.append(td);
    }
	return tr;
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
    				if(name=="field5"){
                        ele.text(map_pos[row.data(name)]);
                    }else if(name=="field3"){
    					ele.text(map_sex[row.data(name)]);
                    }else{
                    	ele.text(row.data(name));
                    }
    			});
    			pop.text(row.data(pop.attr("name")));
    			//填充学习经历
                var obj = row.data("person")
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
			$("#detail-form input[name='titleId']").val(row.parent().parent().attr('data-title-id'));
			$("#detail-form input[name='subCode']").val(row.parent().parent().attr('data-code'));
			selectContext("detail-form");
			$("#qualifications_popup_name").text("编辑简历");
			var valNameOther;
			$.each($("#detail-form").find("input, select, textarea"),function(){
				var ele = $(this);
				var name = ele.attr('name');
				var valName=row.data(name);
				if(typeof(row.data(name))=='string' && row.data(name).indexOf("1363")>-1){
					valName=valName.split("-");
					console.log(valName)
					valNameNew=valName[0];
					$("input[name='other']").show();
					$("input[name='other']").attr('required',true);
					if(valName[1] && valName[1] !=undefined){
						valNameOther=row.data(name).substring(5,row.data(name).length);
					}
				}else{
					valNameNew=valName;
				}
				ele.val(row.data(name)==undefined?"":valNameNew);
				var tagName = $(this).get(0).tagName;
                if(tagName=="SELECT"){
                    if(ele.val()==null){
                         ele.find("option:eq(0)").attr("selected",true);
                    }
                }
			});
			$('input[name="other"]').val(valNameOther);
			$("input:radio[name='field3'][data-value='" + row.data("field3") + "']").prop("checked", "checked");
            //填充学习经历
            var obj = row.data("person")
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
            $("#save_person_learning").click(function(){
            	if(!$("#detail-form").validate().form()){
            		return;
            	}
            	save_person();
            	var v="保存成功";
                saveTeamInfo(v);
            });
		}//模版反回成功执行
	});
}
//删除学习经历等
function delete_row(ele){
    var div=$(ele).closest('div[data-flag]');
    div.remove();
}
/*删除团队成员*/
var deletedRowIds = new Array();
function delMemberRow(ele)
{
	layer.confirm('是否删除?', {
		btn : [ '确定', '取消' ],
		title:'提示'
	}, function(index, layero) {
		var tr = $(ele).closest('tr');
		var id = tr.data('id');
		var table=$(ele).closest('table');
        if(typeof id != 'undefined' && id>0)
        {
        	deletedRowIds.push(id);
        }
		tr.remove();
		var v="删除成功";
        saveTeamInfo(v);
		check_table_tr_edit(table);
		$(".layui-layer-close1").click();
		//$(".layui-layer-btn1").click();
	}, function(index) {
	});
	
}
//未知  undefined
function field_undefined(data){
	 if(data.field1=="undefined"||data.field1==undefined||data.field1==""){
         data.field1="未知";
     }
     if(data.field2=="undefined"||data.field2==undefined||data.field2==""){
         data.field2="未知";
     }
     if(data.field3=="undefined"||data.field3==undefined||data.field3==""){
         data.field3="未知";
     }
     if(data.field4=="undefined"||data.field4==undefined||data.field4==""){
         data.field4="未知";
     }
     if(data.field5=="undefined"||data.field5==undefined||data.field5==""){
         data.field5="未知";
     }
     if(data.field6=="undefined"||data.field6==undefined||data.field6==""){
         data.field6="未知";
     }
     if(data.field7=="undefined"||data.field7==undefined||data.field7==""){
         data.field7="未知";
     }
}
function field_undefineds(data){
	 if(data.field1=="undefined"||data.field1==undefined||data.field1==""){
         data.field1="未知";
     }
     if(data.field2=="undefined"||data.field2==undefined||data.field2==""){
         data.field2="未知";
     }
     if(data.field3=="undefined"||data.field3==undefined||data.field3==""){
         data.field3="";
     }
     if(data.field4=="undefined"||data.field4==undefined||data.field4==""){
         data.field4="";
     }
     if(data.field5=="undefined"||data.field5==undefined||data.field5==""){
         data.field5="";
     }
     if(data.field6=="undefined"||data.field6==undefined||data.field6==""){
         data.field6="";
     }
     if(data.field7=="undefined"||data.field7==undefined||data.field7==""){
         data.field7="未知";
     }
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
           var field1=o.field1;
           field_undefined(o);
           var tmp = "<div class=\"team_div\" data-flag><span name='id'  style='display:none'>"+o.id+"</span>"+
                    "<div class='team_p_one'><span class='team_ico team_ico_dot' ></span>" +
                    "<span name='field5'>"+o.field5+"</span>~"+
                    "<span name='field1'>"+o.field1+"</span></div>"+
                    "<div>"+
                        "<ul style='margin-left:14px;'>"+
                            "<li><span name='field2'>"+o.field2+"</span></li>"+
                            "<li><span name='field3'>"+o.field3+"</span>&nbsp;·&nbsp;<span name='field4' val_id='"+o.field4+"'>";
                            if(o.field4 !="未知"){
                               tmp +=  map_stu[o.field4]+"</span></li></ul>";
                            }else{
                               tmp += "未知</span></li></ul>"
                            }
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
         field_undefineds(o);
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
                        ls.push("创立时股权比例为"+_parsefloat(o.field5)+"%")
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
         if(temp==""){
            temp="未知"
         }         
        str=str +temp+"</li></ul>";

        if(flag=="edit"){
            str+="<div class='team_click'><span class='blue '  onclick='editStartup(this)' >编辑</span>";
            str+="<span class='blue' onclick='delete_row(this)'>删除</span></div>";
        }
        if(o.field7=="未知"){
        str+="</div><div class='team_p_two' style='margin-top:0px;'><span name='field7'>未知</span></div></div>"
        }else{
        str+="</div><div class='team_p_two' style='margin-top:0px;'><span>项目概述:</span><span name='field7'>"+o.field7+"</span></div></div>"
        }
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
     field_undefined(o);
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
                json["field4"]= div.find("span[name='field4']").attr("val_id");
    			var list = div.find("*[name]");
    			json["index"]=index;
                $("#learn_form input[name='titleId']").val($("#detail-form input[name='titleId']").val());
    		    $("#learn_form input[name='subCode']").val($(".team_learn_add").attr("data-code"));
    			selectContext("learn_form");
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
                 $("#team_work_name").attr("index",index);
                $.each($("#work_form").find("input, select, textarea"),function(){
                    var ele = $(this);
                    var name = ele.attr('name');
                    if(name.indexOf("field")>-1){
                        if(json[name] && json[name] != "未知"){
                            ele.val(json[name]);
                        }
                    }else{
                        ele.val("");
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
                $("#startup_form").find("[name='field5']").val(_parsefloat(div.attr("data-c")));
                $("#startup_form").find("[name='field6']").val(div.attr("data-d"));
                $("#startup_form").find("[name='field7']").text(json["field7"]);
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

//新增弹出页面渲染
function addRow(ele)
{
	var _url = $(ele).attr('data-href');
	var table = $(ele).closest(".member").find("table");
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			$('#qualifications_popup_name').html('添加简历');
            $("#detail-form input[name='projectId']").val(projectInfo.id);
            $("#detail-form input[name='titleId']").val($(ele).closest(".member").find("table").attr('data-title-id'));
            $("#detail-form input[name='subCode']").val($(ele).closest(".member").find("table").attr('data-code'));
            $("input[name=updateTimeStr]").val(new Date().format("yyyy-MM-dd"));
            selectContext("detail-form");
            $("#save_person_learning").click(function(){
            	if(!$("#detail-form").validate().form()){
            		return;
            	}
            	save_person();
            	check_table_tr_edit(table);
                var v="保存成功";
                saveTeamInfo(v);
            });
		}//模版反回成功执行	
	});
}

function resizetable(table){
    var dict_map = {};
    var title_id = table.attr("data-title-id")
    var  code = table.attr("data-code")
    var fields_json=tableDictColumn(code);
    if (fields_json && code in fields_json){
        var fields = fields_json[code]
        for(var i=0;i<fields.length;i++){
            var v = fields[i]
            var dict = dictCache(title_id,code,v)
            dict_map[title_id+"-"+code+"-"+v] = dict
            table.find('td[data-field-name="'+v+'"]').each(function(){
                var o = $(this)
                o.text(dict[o.text()])
            })
        }
    }
}

//保存团队成员数据
function saveTeamInfo(v){
	//表格
	var titleId = $("table.team_info").attr("data-title-id");
    var json = {"projectId":projectInfo.id,"titleId":titleId};
	var dataList = new Array();
	$.each($("table.team_info"),function(){
		$.each($(this).find('tr:gt(0)'),function(){
			var row = $(this).data("person");
			if(row.id=="")
			{
				row.id=null;
			}
			row.projectId=projectInfo.id;
			dataList.push(row);
		});
	});
    json["dataList"]=dataList;
    if(dataList.length>10){
        alert("最多只能添加10条记录!")
        return false;
    }
    sendPostRequestByJsonObj(
    platformUrl.saveTeamMember,
    json,
    function(data) {
        var result = data.result.status;
        if (result == 'OK') {
        	updateInforTime(projectInfo.id,"NO3");
            layer.msg(v);
        } else {

        }
})
}

function beTimeCompare(btime,etime){
	if(btime && etime){
		var startTime = (new Date(btime)).getTime();		
		var endTime = (new Date(etime)).getTime();
		if(startTime > endTime){
			layer.msg("开始时间不能大于结束时间");
			return false;
		}
		//兼容safari
		if(btime>etime){
			layer.msg("开始时间不能大于结束时间");
			return false;
		}
	}
	return true;
}

