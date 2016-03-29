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
							max_file_size : '25mb',
							mime_types: paramsFilter(null)
						}
					}
				}
			var settings = $.extend(defaults,options);
			var uploader = new plupload.Uploader(settings.props);
			uploader.init();
			return uploader;
		}
});