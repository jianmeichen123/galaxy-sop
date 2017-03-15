$(function(){
		sendGetRequest(platformUrl.queryAllTitleValues+"NO3_2",null,function(data){
		console.log(data);
		var result = data.result.status;
		if(result == 'OK'){
			var con='<div class="mb_24">'
                +'<dt class="fl_none"></dt>'
                +'<dd class="fl_none">'
                  +'<table>'
                    +'<tr>'
                     +' <th>姓名</th>'
                      +'<th>职务</th>'
                      +'<th>性别</th>'
                      +'<th>最高学历</th>'
                      +'<th>操作</th>'
                    +'</tr>'
                    +'<tr>'
                    +' <td>罗振宇</td>'
                     +'<td>CEO</td>'
                     +'<td>男</td>'
                     +'<td>本科</td>'
                     +'<td><span class="blue" data-btn="btn">查看</span></td>'
                   +'</tr>'                    
                  +'</table>'
               +' </dd>'
             +'</div>';
             $(".h_look").append(con);
		}else{
			
		}
		})
})