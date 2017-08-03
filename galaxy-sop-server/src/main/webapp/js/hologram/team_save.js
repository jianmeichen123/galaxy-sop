
    //添加成员简历保存
    $(function(){
        $("#detail-form").validate({
             submitHandler:function(form){
                 save_person();
             }
        });
        $.validator.setDefaults({
            errorElement:'span'
        });
    })
    function save_person (){
        //row 为列表显示json
        //data为列表的tr data属性值
        var row={};
        var data={};
        row = getData($("#detail-form"))
        row["field3"] = $("#detail-form").find("input:radio[name='field3']:checked").attr("data-value");
        data = row;
        var titleId = data["titleId"]
        var projectId = data["projectId"]
        //学习经历
        var list1 = $("#team_learn").find("div[data-flag]");
        var studyList = [];
        $(list1).each(function(){
            var div = $(this);
            var t1 = getData(div);
            t1["code"]="study-experience";
            t1["titleId"]=data["titleId"];
            t1["projectId"]=data["projectId"];
            if(!t1["id"] || t1["id"] == 'null' || t1["id"] == "undefined"){
            	delete t1.id;
            }
           var field1 = div.find("[name='field1']").text();
           var field2 = div.find("[name='field2']").text();
           var field3 = div.find("[name='field3']").text();
           var field4 = div.find("[name='field4']").text();
           var field5 = div.find("[name='field5']").text();
           if(field1.indexOf("毕业")>0){
               t1["field1"]=field1.substring(0,field1.length-2);

           }else{
               t1["field1"]=field1;
           }
           t1["field2"]=field2;
           t1["field3"]=field3;
           t1["field4"]=field4;
           t1["field5"]=field5;
           studyList.push(t1);
            //studyList.push(t1);
        })
        data["studyList"]=studyList;
        //工作经历
         var list2 =  $("#team_work").find("div[data-flag]");
         var workList = [];
         $(list2).each(function(){
              var div = $(this);
              var t2 = getData(div);
              t2["code"]="work-experience";
              t2["titleId"]=titleId;
              t2["projectId"]=projectId;
              if(!t2["id"] || t2["id"] == 'null' || t2["id"] == "undefined"){
              	delete t2.id;
              }
              workList.push(t2);
         })
         data["workList"]=workList;
         //创业经历
         var list3 =  $("#team_startup").find("div[data-flag]");
         var startupList = [];
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

               if(!isNaN(id)){
                 json["id"]=id;
               }
               else
        	   {
            	  json["id"]=null;
        	   }

               json["field1"]=field1;
               json["field2"]=field2;
               json["field3"]=field3;
               json["field4"]=field4;
               json["field5"]=field5;
               json["field6"]=field6;
               json["field7"]=field7;

               json["code"]=code;
               json["titleId"]=titleId;
               json["projectId"]=projectId;

               startupList.push(json);
         })

        data["startupList"]=startupList;
        data["code"]="team-members";
        var index = data["index"]
        //获取表头,
        var headerList =   $('table[data-title-id="'+titleId+'"].editable').find('tbody').find('tr:eq(0)').find("th[data-field-name!='opt']");
        if(typeof index == 'undefined' || index == null || index == '')
        {
            var tr = buildMemberRow(headerList,row,true);
            tr.data("obj",data);
            $('table[data-title-id="'+titleId+'"].editable').append(tr);
        }
        else
        {
            var tr = $('table[data-title-id="'+titleId+'"].editable').find('tr:eq('+index+')');
            tr.data("obj",data);
            var i = tr.data("obj");
            //解决字段无值列错位的问题
            for(var key in row)
            {
                tr.data(key,row[key]);
            }
            $(headerList).each(function(){
                var key = $(this).attr("data-field-name");
                if(key.indexOf('field')>-1)
                {
                    if(row[key]){
                        tr.find('td[data-field-name="'+key+'"]').text(row[key]);
                    }else{
                        tr.data(key,"未知");
                        tr.find('td[data-field-name="'+key+'"]').text("未知");
                    }
                }
             })
             //解决字段无值列错位的问题
        }
        $("a[data-close='close']").click();
    }
    $("#save_person_learning").on("click",function(){
    	$("#detail-form").submit();
    	if($("span.error").length>0){
    		$(".team_porp").scrollTop(0);
    	}
        
    })

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
                 value = $(this).find("option:selected").text();
              }
              if(tagName == "SPAN"){
                key = $(this).attr("name");
                value = $(this).text();
              }
              json[key]=value;
        })

        return json;
	}

	/****************common********************/

	$("div").delegate(".team_learn_add","click",function(event){
		event.stopPropagation();
	  	var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				$("#learn_form input[name='titleId']").val($("#detail-form input[name='titleId']").val());
				$("#learn_form input[name='subCode']").val($self.attr("data-code"));
				selectContext("learn_form");
			}//模版反回成功执行
		});
		return false;
	});
	$("div").delegate(".team_work_add","click",function(event){
		event.stopPropagation();
	  	var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){

			}//模版反回成功执行
		});
		return false;
	});
	$("div").delegate(".team_startup_add","click",function(event){
		event.stopPropagation();
	  	var $self = $(this);
		var _url = $self.attr("href");
		var _name= $self.attr("data-name");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				
			}//模版反回成功执行
		});
		return false;
	});

    //验证手机号
    jQuery.validator.addMethod("phone", function (value, element) {
      var mobile = /^[\+\-\(\)0-9]{0,40}$/;
   	return this.optional(element) || (mobile.test(value));
    }, "手机格式不对");

