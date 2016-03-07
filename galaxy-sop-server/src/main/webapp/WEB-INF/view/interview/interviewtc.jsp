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
            	<input type="text" id="viewDate" name = "viewDate"  placeholder="访谈日期" class="txt" />
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
    
    <dl class="fmdl clearfix">
        <dt>访谈纪要：</dt>
        <dd>
        <div class="edit">
        	<textarea style="width:588px;height:200px;" id="viewNotes" name = "viewNotes"  placeholder="访谈纪要"  ></textarea>
         </div>
            <!-- <div class="edit">
               	<textarea  id="viewNotes" name = "viewNotes"  placeholder="访谈纪要"  ></textarea>
            </div> -->
        </dd>
    </dl>
    
    <dl class="fmdl clearfix">
        <dt>访谈录音：</dt>
        <dd>
            <div class="fmload clearfix">
                <p class="loadname"></p>
                <input type="file" class="load"/>
                <a href="javascript:;" class="pubbtn fffbtn">上传录音</a>
                
                <input type="hidden" id="viewfileID" name = "fileId" value="" /> 
            </div>
        </dd>
    </dl>
    
    <div class="btnbox">
    	<a href="javascript:saveInterView()" id="saveInterView" class="pubbtn bluebtn">保存</a><a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
    </div>
</div>


