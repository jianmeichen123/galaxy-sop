<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投-直接上传到</title>
</head>
<body>
<input type="text" id="fileName">
<button id="selectFile">选择文件</button>
</body>
<script src="<%=request.getContextPath() %>/js/jquery-1.12.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script type="text/javascript">
var fileUploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : "selectFile", 
	url : 'http://oss.aliyun.com',
	multipart:true,
	multi_selection:false,
	filters : {
		max_file_size : '50MB',
		mime_types : [ 
	        { title : "Image files", extensions : "jpg,gif,png,bmp" },
	        { title : "Zip files", extensions : "zip" },
	        { title : "Audio files", extensions : "mp3,mp4,avi,wav,wma,aac,m4a,m4r,MP3,MP4,AVI,WAV,WMA,AAC,M4A,M4R"},
	        { title : "Document files", extensions : "pdf,doc,docx,xls,xlsx"},
	        ]
	},
	init: {
		PostInit: function(up){
			$.getJSON('getOSSSignature',function(data){
				var params = data.userData;
				up.setOption({
					url : params.host,
					multipart_params : {
						'key' : new Date().getTime(),
						'policy': params.policy,
						'OSSAccessKeyId': params.accessid,
						'signature': params.signature,
						'success_action_status' : '200'
					}
				});
			})
		},
		FilesAdded: function(up, files) {
			var file = files[0];
			$("#fileName").val(file.name);
			fileUploader.start();
		},
		FileUploaded: function(up, files, rtn) {  //上传回调
			console.log(rtn);
		},
		BeforeUpload:function(up){
		},
		Error: function(up, err) {
		}
	}
});
fileUploader.init();

</script>
</html>