<%@ page language="java" pageEncoding="UTF-8"%>


<div class="meetingtc">
	<div class="top clearfix">
    	<div class="searchall clearfix">
            <dl>
            	<dt>项目 :</dt>
                <dd>
                	<select id="projectId" name="projectId" >
                    </select>
                    
                	<!-- <input type="text" id="proName" name = "proName"  placeholder="请输入关键字查找" class="txt"/>
                	<input type="hidden" id="projectId" name = "projectId" value="" /> -->
                </dd>
            </dl>
           <!--  <a href="javascript:;" class="searchbtn null">搜索</a> -->
        </div>
        <dl class="fmdl clearfix">
            <dt>访谈日期：</dt>
            <dd>
            	<input class="form-control" type="date"  id="viewDate" name = "viewDate" placeholder="访谈日期"/>
            </dd>
        </dl>
    </div>
    
    
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>访谈对象：</dt>
            <dd>
           		<input type="text" id="viewTarget" name = "viewTarget"  placeholder="访谈对象" class="txt" />
            </dd>
        </dl>
    </div>
    

<!-- 赋值     var um = UM.getEditor('describe_editor');
		um.setContent(data.entity.projectDescribe); 
		
	取值 	var um = UM.getEditor('viewNotes');
		var projectDescribe = um.getContent();
		-->
          
	    <dl class="fmdl clearfix">
	        <dt>访谈纪要:</dt>
	        <dd>
	        	  <div type="text/plain" id="viewNotes" style="width:100%;height:100px;"></div>
			</dd>
	          <!--   <dd class="fctbox">
	          <a href="javascript:;" class="ico f1" data-btn="edit">编辑</a>
	            <a href="javascript:;" id="save_viewNotes" class="ico f4" data-btn="submit">保存</a>
	            <a href="javascript:;" class="ico f5" data-btn="reset">取消保存</a>
	            <a href="javascript:;" class="ico f2" data-btn="describe">查看详情</a>
	            <a href="javascript:;" data-btn="hide" class="ico f3">收起</a>
	          </dd> -->
        </dl>
            
    
    <dl class="fmdl clearfix">
        <dt>访谈录音：</dt>
        
            <div class="fmload clearfix">
            <dd>
	        	<input type="text" name="fileName" id="fileName" class="txt" readonly="readonly"/>
	        </dd>
	        <dd>
	        	<a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">上传录音</a>
    		</dd>
            </div>
        
    </dl>
    
    <div class="btnbox"> <!-- saveInterView() -->
    	<a href="javascript:;" id="saveInterView" class="pubbtn bluebtn">保存</a><a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
    </div>
</div>


<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>

<script src="${ctx}/js/plupload.full.min.js" type="text/javascript"></script>
 
 <script type="text/javascript">
		UM.getEditor('viewNotes');
</script>
    
<!-- <script type="text/javascript">
$(function(){

	var um = UM.getEditor('viewNotes');
	um.setContent(""); 


});

</script> -->
