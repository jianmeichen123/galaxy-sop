$('#plan_business_table').bootstrapTable({
	url : platformUrl.getBusinessPlanFileInSession, // 请求后台的URL（*）
	queryParamsType : 'size|page', // undefined
	showRefresh : false,
	search : false,
	method : 'post', // 请求方式（*）
	// toolbar: '#toolbar', //工具按钮用哪个容器
	// striped: true, //是否显示行间隔色
	cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	pagination : false, // 是否显示分页（*）
	sortable : false, // 是否启用排序
	sortOrder : "asc", // 排序方式
	sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
	pageNumber : 1, // 初始化加载第一页，默认第一页
	pageSize : 10, // 每页的记录行数（*）
	pageList : [ 10, 20 ], // 可供选择的每页的行数（*）
	strictSearch : true,
	clickToSelect : true, // 是否启用点击选中行
	// height: 460, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	uniqueId : "id", // 每一行的唯一标识，一般为主键列
	cardView : false, // 是否显示详细视图
	detailView : false, // 是否显示父子表
	queryParams : function(param){
     	return param;
     },
     onLoadSuccess:function (data){
    	 initViewUpload(data);
     },
	//文件名称，状态，更新时间，下载
	//fileName + fileSuffix,fileStatusDesc,createDate
	columns : [{
		field : 'createDate',
		title : '更新时间'
	}, {
		field : 'fileName',
		title : '文档名称',
		formatter : fileNameFormatter
	}, {
		field : 'operate',
		title : '操作',
		formatter : operatorFormatter
	}]
});	
function fileNameFormatter(value,row,index){
	console.log(row.fileSuffix);
	if(row.fileName){
		return row.fileName +'.'+row.fileSuffix;
	}else{
		return "-";
	}
	
}

function operatorFormatter(value,row,index){
	var operator;
	if(row.fileName){
		operator = '更新附件';
	}else{
		operator = '上传附件';
	}
	return [
	        '<input type="file" class="uploadlink" id="select_btn" style="opacity:0;width:100px;"/><a class="blue ico_pgn 3333"  href="javascript:void(0)" style="margin-left:-100px;">'+ operator +'</a>'
			 ].join('');
	
}
function initViewUpload(queryParams) {
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#select_btn")[0], // you can pass in id...
		url : platformUrl.uploadBpToSession,
		multipart:true,
		multi_selection:false,
		filters : {
			max_file_size : '25mb',
			mime_types: [
			    {title : "Zip files", extensions : "zip,rar,ZIP,RAR"},
				{title : "Image files", extensions : "bmp,jpg,jpeg,gif,png,BMP,JPG,JPEG,GIF,PNG"},
				{title : "audio files", extensions : "mp3,mp4,avi,wav,wma,aac,m4a,m4r,flv,MP3,MP4,AVI,WAV,WMA,AAC,M4A,M4R,FLV"},
				{title : "doc files", extensions : "doc,docx,ppt,pptx,pps,xls,xlsx,pdf,txt,pages,key,numbers,DOC,DOCX,PPT,PPTX,PPS,XLS,XLSX,PDF,TXT,PAGES,KEY,NUMBERS"}
			]
		},
		init: {
			FilesAdded: function(up, files) {
				uploader.start();
				return false;
			},
			UploadProgress: function(up, file) {
			},
			FileUploaded:function(up,file,result){
				if(result.status==200){
					layer.msg("上传成功");
					$('#plan_business_table').bootstrapTable('refresh',queryParams);
				}else{
					layer.msg("上传失败");
				}
			},
			BeforeUpload:function(up){
			},
			Error: function(up, err) {
				layer.msg(err.message);
			}
		}
	});							
	//初始化plupload插件
	uploader.init();
}