$(function(){
		sendGetRequest(platformUrl.queryTsTvalues+"NO1",null,function(data){
		console.log(data);
		var result = data.status;
		if(result == 'OK'){
			/*var con='<div class="mb_24 clearfix">
                      <dl class="clearfix">
                        <dt>商业模式：</dt>
                        <dd>该项目是一个通过或基于（技术或模式）的（选择三级以下分类) 的（具体品类：平台、运营商、服务商、技术提供商、解决方案提供商、工具），连接（服务一端）和（服务另一端），为（用户）提供（产品服务即内容）的产品或服务，满足了（需求，如有）的刚需或解决了（痛点，如有）。</dd>
                      </dl>
                    </div>
                     <div class="mb_24 clearfix">
                      <dl class="clearfix">
                        <dt>商业模式进化：</dt>
                        <dd>尚未验证</dd>
                      </dl>
                    </div>'
*/			
		}else{
			
		}
		})
})