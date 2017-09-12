	var win = {
			fileKey : undefined,
			init : function(_formdata){
				win.fileKey = _formdata._fileKey;
				win.fileId = _formdata._fileId;
				win.callFuc = _formdata.callFuc;
						//plupload上传对象初始化
						var uploader = new plupload.Uploader({
							runtimes : 'html5,flash,silverlight,html4',
							browse_button : $("#select_btn")[0], // you can pass in id...
							url : platformUrl.uploadBpToSession,
							multipart:true,
							multi_selection:false,
							auto_start:true,
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
								/*PostInit: function(up, files) {
									//上传按钮点击事件开始上传
									uploader.start();
									return false;
								},*/
								FilesAdded: function(up, files) {
									uploader.start();
									return false;
								},
								UploadProgress: function(up, file) {
								},
								FileUploaded:function(up,file,result){
									if(result.status==200){
										layer.msg("上传成功");
									}else{
										layer.msg("上传失败");
									}
								},
								FileUploaded:function(up,file,result){
									if(result.status==200){
										console.log("上传成功了");
										win.callFuc();
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
			},
			callFuc : function(){
				
			}
	};
	
	function init(){
	}
$(document).ready(init());

