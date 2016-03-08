function init(){

	
	var path = $("#pathInput").val();
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : 'selectBtn', // you can pass in id...
		url : path + '/galaxy/sopFile/simpleUpload',
		multipart:true,
		filters : {
			max_file_size : '10mb',
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip,rar"},
				{title : "Offices files", extensions : "doc,docx,excel"}
			]
		},

		init: {
			PostInit: function() {
				document.getElementById('uploadBtn').onclick = function() {
					uploader.multipart_params = {
							id : "asd",
							name : ""
					}
					uploader.start();
					return false;
				};
			},

			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
				});
			},

			UploadProgress: function(up, file) {
//				document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
			},
			FileUploaded:function(up,file,result){
				alert(1111);
				alert(file.id);
				alert(file.name);
				alert(result.response);//服务器返回的文本
				alert(result.responseHeaders)//服务器返回头信息
				alert(result.status);//服务器返回状态
			},
			Error: function(up, err) {
				document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
			}
		}
	});
	uploader.init();
	$("#uploadBtn").click(function(){
		
	});
	
}

$(document).ready(init());