$.extend({
		fxUpload:function(options){
			var defaults = {
					beforeInit:function(){},
					props :{
						runtimes : 'html5,flash,silverlight,html4',
						browse_button : '', 
						url : '',
						multipart:true,
						multi_selection:false,
						filters : {
							max_file_size : '30mb',
							mime_types: [
							    {title : "YP files", extensions : "mp3,mp4,avi,wav,wma,aac,m4a,m4r"},
								{title : "Image files", extensions : "bmp,gif,jpg,jpeg,png"},
								{title : "Zip files", extensions : "zip,rar"},
								{title : "document files", extensions : "doc,docx,ppt,pptx,pps,xls,xlsx,pdf,txt"}
							]
						}
					}
				}
			var settings = $.extend(defaults,options);
			var uploader = new plupload.Uploader(settings.props);
			uploader.init();
			return uploader;
		}
});