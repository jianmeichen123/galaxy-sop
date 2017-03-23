/* sendGetRequest("http://fx.local.galaxyinternet.com/sop/galaxy/team/queryRowsList/1302/1",null,function(data){

        alert(222)
 })*/
    //添加成员简历保存
    $("#save_person_learning").on("click",function(){
        var infoTableModelList=[];

        //基本信息
        var basejson = getData($("#teamInfo"));
        basejson["filed2"]=$("#teamInfo").find("[name='filed2']:checked").val();
        basejson["titleId"]=1303;
        basejson["code"]="team-members";
        if(!basejson["id"]){
            basejson["id"]=null
        }
        infoTableModelList.push(basejson);
        //学习经历
        var list1 = $("#team_learn").find("div[data-flag]");
        $(list1).each(function(){
            var div = $(this);
            var json = getData(div);
            json["code"]="study-experience";
            json["titleId"]=1303;
            if(!json["id"]){
                json["id"]=null
            }
            infoTableModelList.push(json);
            console.log("studyListjson:"+json)
        })
        //工作经历
         var list2 =  $("#team_work").find("div[data-flag]");
         $(list2).each(function(){
              var div = $(this);
              var json = getData(div);
              json["code"]="work-experience";
              json["titleId"]=1303;
              if(!json["id"]){
                  json["id"]=null
              }
              infoTableModelList.push(json);
              console.log("workListjson:"+json)
         })

         //创业经历
         var list3 =  $("#team_startup").find("div[data-flag]");
         $(list3).each(function(){
               var div = $(this);
               var json={};
               var field1 = div.find("[name='field1']").text();
               var field2 = div.find("[name='field2']").text();
               var field3 = div.attr("data-a");
               var field4 = div.attr("data-b")
               var field5 = div.attr("data-c")
               var field6 = div.attr("data-d")
               var field7 = div.find("[name='field7']").text();
               var code = "entrepreneurial-experience";
               var id = div.find("[name='id']").text();
               if(!id){
                 json["id"]=null
               }
               json["field1"]=field1;
               json["field2"]=field2;
               json["field3"]=field3;
               json["field4"]=field4;
               json["field5"]=field5;
               json["field6"]=field6;
               json["field7"]=field7;

               json["code"]=code;
                json["titleId"]=1303;
               console.log("startupListjson:"+json)
               infoTableModelList.push(json);
         })
        var json={"projectId":1,"infoTableModelList":infoTableModelList}
        sendPostRequestByJsonObj("http://fx.local.galaxyinternet.com/sop/galaxy/team/saveorUpdate",json,function(data){
               //查询列表
               sendGetRequest("http://fx.local.galaxyinternet.com/sop/galaxy/team/queryRowsList/1303/1",null,function(data){
                   var entityList = data.entityList;
                   if(entityList.length>0){
                     /*  var tmp = "";
                       var table = $("table[data-title-id='1303'].editable");
                       table.find("tbody").remove();
                       $(entityList).each(function(i,e){
                           tmp  +=  "<tr>"+
                                       "<td>"+e.field1+"</td>"+
                                       "<td>"+e.field2+"</td>"+
                                       "<td>"+e.field3+"</td>"+
                                       "<td>"+e.field4+"</td>"+
                                       "<td>"+e.field5+"</td>"+
                                       "<td><span class='blue show'  onclick='editRow1(this,"+e.id+")' data-id='"+e.id+"'>查看</span>"+
                                       "<span class='blue edit' onclick='editRow1(this,"+e.id+")' data-id='"+e.id+"'>编辑</span>"+
                                       "<span class='blue' data-btn='btn' onclick='delRow(this)'>删除</span>"+
                                       "</td>"+
                                     "</tr>"
                      })
                      table.append(tmp);*/

                   }
               })
        })
    })



 /****************common********************/
    function S4() {
	   return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
	}
	function guid() {
	   return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
	}

	function getData(div){
		var json={};
	    var list = div.find("*[name]");
        $(list).each(function(){
              var key = "";
              var value = "";
              var tagName = $(this).get(0).tagName;
              if(tagName == "INPUT"|| tagName == "TEXTAREA"){
				 key = $(this).attr("name");
				 value = $(this).val();
              }
              if(tagName=="SELECT"){
                 key = $(this).attr("name");
                 value = $(this).find("option:selected").val();
              }
              if(tagName == "SPAN"){
                key = $(this).attr("name");
                value = $(this).text();
              }
              if(tagName == "SPAN"){
                  key = $(this).attr("name");
                  value = $(this).text();
              }
              json[key]=value;
        })
        console.log(json)
        return json;
	}
	function list2form(myid,targetObj){
			var list = $('#'+myid).find("*[name]");
			var json ={};
			$(list).each(function(){
				  var key = "";
				  var value = "";
				  key = $(this).attr("name");
				  value = $(this).text();
				  json[key]=value;
			})
			json["myid"]=myid;
			var list = targetObj.find("*[name]");
			$(list).each(function(){
				  var key = "";
				  var tagName = $(this).get(0).tagName;
				  if(tagName == "INPUT"){
					 key = $(this).attr("name");
					 $(this).val(json[key]);
				  }
				  if(tagName == "TEXTAREA"){
					 key = $(this).attr("name");
					 $(this).text(json[key]);
				  }
				  if(tagName=="SELECT"){
					 key = $(this).attr("name");
					 $(this).find("option[value='"+json[key]+"']").attr("selected",true);
				  }

			})
	}
	/****************common********************/
	$("div").delegate(".team_learn_add","click",function(event){
		event.stopPropagation();
	  	var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		var myid =$self.attr("data-id");
		alert(myid)
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				if(myid){
					list2form(myid,$("#learn_form"))
				}

			}//模版反回成功执行
		});
		return false;
	});
	$("div").delegate(".team_work_add","click",function(event){
		event.stopPropagation();
	  	var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		var myid =$self.attr("data-id");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				if(myid){
					list2form(myid,$("#work_form"))
				}
			}//模版反回成功执行
		});
		return false;
	});
	$("div").delegate(".team_startup_add","click",function(event){
		event.stopPropagation();
	  	var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		var myid =$self.attr("data-id");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				if(myid){
					var div =$('#'+myid);
					var list = div.find("*[name]");
					var json ={};
					$(list).each(function(){
						  var key = "";
						  var value = "";
						  key = $(this).attr("name");
						  value = $(this).text();
						  json[key]=value;
					})
					json["myid"]=myid;
					$("#startup_form").find("[name='myid']").val(json["myid"]);
					$("#startup_form").find("[name='field1']").val(json["field1"]);
					$("#startup_form").find("[name='field2']").val(json["field2"]);
					$("#startup_form").find("[name='field3']").val(div.attr("data-a"));
					$("#startup_form").find("[name='field4'][value='" + div.attr("data-b") + "']").prop("checked", "checked");
					$("#startup_form").find("[name='field5']").val(div.attr("data-c"));
					$("#startup_form").find("[name='field6']").val(div.attr("data-d"));
					$("#startup_form").find("[name='field7']").text(json["field7"]);
				}
			}//模版反回成功执行
		});
		return false;
	});

