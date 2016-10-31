var searchPanel = {   
		initData : function(){
			sendPostRequestByJsonObj(platformUrl.sopFileCheckShow,null,searchPanel.initCheckShow);
			//档案类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,searchPanel.initDataCallBack);
			//业务类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorktype",null,searchPanel.initDataCallBack);
			//所属事业线
			sendGetRequest(platformUrl.getDepartMentDict+"/all",null,searchPanel.initDataCallBack);
			//注册发送邮件按钮
			$("#show-mail-btn").click(function(){
				var rows = $("#fileGrid").bootstrapTable('getSelections');
				if(rows.length==0)
				{
					layer.msg('请选择档案。');
					return;
				}

				var i = 0;
				$.each(rows,function(){
					if(this.fileLength){
						i++;
						return false;
					}	
				});
				if(i == 0){
					layer.msg('无文件。');
					return;
				}
				
				var data = {
						_rows : rows
				}
				mailWin.init(data);
			});
		},
		initDataCallBack : function(data){
			var _dom;
			var _type;
			switch(data.result.message){
			case "fileType" : 
				_dom = $("#searchFileType");
				break;
			case "fileWorktype":
				_dom = $("#searchFileWorktype");
				break;
			default :
				_dom = $("#searchCareerLine")
			}
			utils.each(data,_dom,"all");
		},
		initCheckShow : function(data){
			if(data.result.status!='OK'){
				$("#srearch_careerline_div").hide();
			}
		},
		serarchData : function(){
			$('#fileGrid').bootstrapTable('refresh',fileGrid.queryParams);	 
		 }
};

var fileGrid = {
	init : 	function(){
		
		$('#fileGrid').bootstrapTable({
		      url : platformUrl.searchSopFileList,     //请求后台的URL（*）
		      queryParamsType: 'size|page', // undefined
		      showRefresh : false ,
		      search: false,
		      method : 'post',           //请求方式（*）
//		      toolbar: '#toolbar',        //工具按钮用哪个容器
//		      striped: true,           //是否显示行间隔色
		      cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		      pagination: true,          //是否显示分页（*）
		      sortable: false,           //是否启用排序
		      sortOrder: "asc",          //排序方式
		      queryParams: fileGrid.queryParams,//传递参数（*）
		      sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
		      pageNumber:1,            //初始化加载第一页，默认第一页
		      pageSize: 10,            //每页的记录行数（*）
		      pageList: [10, 20],    //可供选择的每页的行数（*）
		      strictSearch: true,
		      clickToSelect: false,        //是否启用点击选中行
//		      height: 460,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		      uniqueId: "id",           //每一行的唯一标识，一般为主键列
		      cardView: false,          //是否显示详细视图
		      detailView: false,          //是否显示父子表
		      columns: [
				{
					field : 'checkbox',
					checkbox : "true",
					title : ''
				},{
		        field: 'id',
		        title: '序号'
		      }, {
		        field: 'careerLineName',
		        title: '所属业务线'
		      }, {
		        field: 'projectName',
		        title: '所属项目',
		        formatter: fileGrid.projectNameFormatter	
		      }, {
		        field: 'fSource',
		        title: '档案来源'
		      }, {
		        field: 'fileUName',
		        title: '起草者'
		      }, {
		        field: 'fType',
		        title: '存储类型'
		      }, {
		        field: 'fWorktype',
		        title: '业务分类'
		      }, {
			    field: 'voucherFile',
			    title: '签署凭证',
			    events : fileGrid.operateEvents,
			    formatter: fileGrid.operateVFormatter 	
			  }, {
		        field: 'updatedDate',
		        title: '更新日期'
		      }, {
		        field: 'fileStatusDesc',
		        title: '档案状态'
		      },{
		    	  field: 'operate', 
		    	  title: '操作', 
		    	  events: fileGrid.operateEvents, 
		    	  formatter: fileGrid.operateFormatter 

		      }]
		    });
		 //初始化查询按钮
		 $("#searchBtn").click(function(){
			 $('#fileGrid').bootstrapTable('querySearch',fileGrid.queryParams);	
		 });
		 $('#fileGrid').on("load-success.bs.table", function (data) {
			    // ... 
//			 $(".ritmin").hideLoading();
			});
		 

		  
	},
	projectNameFormatter : function(value, row, index){
		if(row.projectName.length>12){
			var str=row.projectName.substring(0,12);
			var options='<span title="'+row.projectName+'">'+str+'</span>'
		}else{
			var options='<span title="'+row.projectName+'">'+row.projectName+'</span>'
		}
		return options;
	},
	operateFormatter : function(value, row, index){
		var uploadOpt;
		if(row.fileKey){
			uploadOpt = "更新";
			uploadClass = "fileupdatelink";
			
		}else{
			uploadOpt = "上传";
			uploadClass = "fileuploadlink"
		}
		var isTransfing = false;
		if(typeof(transferingIds) != 'undefined' && transferingIds.contains(row.projectId))
		{
			isTransfing = true;
			uploadClass += " limits_gray";
		}
		var opts = new Array();
		if(row.fileKey && row.fileValid==1 && row.isEdit == "true"){
			
			if(isTransfing == false )
			{
				opts.push('<a class="' + uploadClass + ' blue"  href="javascript:void(0)">'+uploadOpt+'</a>  ');
			}
			opts.push('<a class="filedownloadlink blue" id="sopfile" href="javascript:void(0)">下载</a>');
			return opts.join('');
		}else if(!row.fileKey && row.isEdit == "true"){
			if(isTransfing == false )
			{
				opts.push('<a class="' + uploadClass + ' blue"  href="javascript:void(0)">'+uploadOpt+'</a>  ');
			}
			return opts.join('');
		}else if(row.fileKey && row.fileValid==1  && row.isEdit == "false"){
			return [
			        '<a class="filedownloadlink blue" id="sopfile" href="javascript:void(0)">',
			        '下载',
			        '</a>  '
		        ].join('');
		}else if(!row.fileKey && row.isEdit == "false"){
			return '';
		}
		return '';
	},
	operateEvents : {
		'click .filedownloadlink': function (e, value, row, index) {
			data = {
					fileKey : row.fileKey,
					fileName : row.fileName + "." + row.fileSuffix
			};
			layer.msg('正在下载，请稍后...',{time:2000});
			var keyvalue;
			if(e.target.id=="sopfile"){
				keyvalue=row.id;
			}else if(e.target.id=="vsopfile"){
				keyvalue=row.voucherId + "?type=voucher"; 	
			}else{
				keyvalue="";
			}
			window.location.href=platformUrl.downLoadFile+'/'+keyvalue ;
        },
        //更新文档
		'click .fileupdatelink' : function(e, value, row, index){
			if($(this).hasClass('limits_gray'))
			{
				return;
			}
			var formData = {
        			_fileKey : row.fileKey,
        			_fileSource : row.fileSource,
        			_fileType : "fileType:1",
        			_fileTypeAuto : true,
        			_workType : row.fileWorktype,
        			_projectId : row.projectId,
        			_projectName : row.projectName,
        			_remark : "hide",
    				callFuc : function(){
    					searchPanel.serarchData();
    				},
    				
    				_url : platformUrl.commonUploadFile, //兼容老板插件
    				_localUrl : platformUrl.commonUploadFile
    		};
			if($(this).text()=='更新'){
				$('.title_bj').html('更新文档');
			}
			if($(this).text()=='上传'){
				$('.title_bj').html('项目文档')
			}
			if('vsopfile'==e.currentTarget.id){
				//签署凭证
				formData._isProve = true;
			}else{
				formData._isProve = "hide";
			}
    		win.init(formData);
        },
        
        //上传文档
        'click .fileuploadlink' : function(e, value, row, index){
        	if($(this).hasClass('limits_gray'))
			{
				return;
			}

        	$('.title_bj').html('项目文档');
        	var uploadUrl = undefined;
        	var uploadFormFuc = undefined;
        	if(row.isChangeTask == "true"){
        		uploadUrl = platformUrl.stageChange;
        		uploadFormFuc = function(dom){
					//本地上传回掉参数获取事件, 通过dom.find(“”)获取表单数据 jquery 语法
					var form = {
							"pid" : dom.find("#win_sopProjectId").data("tid"),
							"stage":row.projectProgress,
							"type":dom.find("input[name='win_fileSource']:checked").val(),
							"fileType":dom.find("#win_fileType").val(),
							"fileWorktype":dom.find("#win_fileWorkType").val()
					}
					return form;
				}
        		if($(this).text()=='上传'){
    				$('.title_bj').html('项目文档')
    			}
        	}else{
        		uploadUrl = platformUrl.commonUploadFile;
        	}
                
        	formData = {
        			_fileKey : row.fileKey,
        			_fileSource : row.fileSource,
        			_fileType : "fileType:1",
        			_fileTypeAuto : true,
        			_workType : row.fileWorktype,
        			_projectId : row.projectId,
        			_projectName : row.projectName,
        			_isProve : "hide",
        			_remark : "hide",
    				callFuc : function(){
    					searchPanel.serarchData();
    				},
    				_url : platformUrl.stageChange, //兼容老板插件
    				_localUrl : uploadUrl,
    				_getLocalFormParam : uploadFormFuc
    		};
    		win.init(formData);
        },
        //上传签署凭证文档
        'click .voucherfileuploadlink' : function(e, value, row, index){
        	formData = {
        			_fileKey : row.fileKey,
        			_fileSource : row.fileSource,
        			_fileType : "fileType:1",
        			_fileTypeAuto : true,
        			_workType : row.fileWorktype,
        			_projectId : row.projectId,
        			_projectName : row.projectName,
        			_isProve : true,
        			_remark : "hide",
    				callFuc : function(){
    					searchPanel.serarchData();
    				},
    				_url : platformUrl.stageChange, //兼容老板插件
    				_localUrl : platformUrl.stageChange,
    				_getLocalFormParam : function(dom){
    					//本地上传回掉参数获取事件, 通过dom.find(“”)获取表单数据 jquery 语法
    					var form = {
    							"pid" : dom.find("#win_sopProjectId").data("tid"),
    							"stage":row.projectProgress,
    							"type":dom.find("input[name='win_fileSource']:checked").val(),
    							"fileType":dom.find("#win_fileType").val(),
    							"fileWorktype":dom.find("#win_fileWorkType").val(),
    							"voucherType" : $("input[id='win_isProve']:checked").val()
    					}
    					return form;
    				}
    		};
    		win.init(formData);
        }
	},
	queryParams : function(params){
		var fileSource = utils.confident($("input[name='source']:checked").val(),"all");
		var fileType = utils.confident($("#searchFileType").val(),"all");
		var projectName = $.trim($("#searchProjectId").val());
		if(projectName=="") projectName=undefined;
		var fileWorktype = utils.confident($("#searchFileWorktype").val(),"all");
		var careerLine = utils.confident($("#searchCareerLine").val(),"all");
		params.fileType = fileType;
		params.fileSource = fileSource;
		params.keyword = projectName;
		params.fileWorktype = fileWorktype;
		params.careerLine = careerLine;
//		$('#fileGrid').bootstrapTable('refresh',parameters);	
		return params;
	},
	operateVFormatter : function(value, row, index){
		if(row.Vstatus=="false"){
			if(row.fileKey){
				return [
						'<a class="voucherfileuploadlink blue"  href="javascript:void(0)">上传</a>'
				        ].join('');
			}else{
				return [
						'暂不能操作'
				        ].join('');
			}
		}
		if(row.Vstatus=="true"){
			if(row.isProveEdit=="true"){
				return [
						'<a class="filedownloadlink blue" id="vsopfile" href="javascript:void(0)">查看</a>',
						'<a class="fileupdatelink blue" id="vsopfile" href="javascript:void(0)">更新</a>'
				        ].join('');
			}else{
				return [
						'<a class="filedownloadlink blue" id="vsopfile" href="javascript:void(0)">查看</a>',
				        ].join('');
			}
		}
		if(row.Vstatus=="no"){
			return [
					'无'
			        ].join('');
		}
	
	},

	downloadCallBackfunction : function(data){
	},
	
};











var utils = {
		path : $("#pathInput").val(),
		each : function(_data,_dom,type){
			_dom.empty();
			if(type=="all"){
				_dom.append("<option value='all'>--全部--</option>");
			}
			$.each(_data.entityList,function(){
				if(this.code){
					_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
				}else{
					_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
				}
				
			});
		},
		confident : function(value,tem){
			if(value==tem){
				return;
			}else{
				return value;
			}
		}
		
}
function init(){
	
//	if(roleId == "")
	createMenus(14);
	searchPanel.initData();
	fileGrid.init();
	if(ossClient){
		ossClient.downLoadInit();
	}
	
	
	
}

$(document).ready(init());