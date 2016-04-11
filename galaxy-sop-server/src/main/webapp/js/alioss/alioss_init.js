  var client = new OSS({
    region: 'oss-cn-hangzhou',
    accessKeyId: '3Yvy23QypBtVsJhz',
    accessKeySecret: 'zAXajwCAO51Qw2g8xg4WL5asmH31nr',
//stsToken: '<Your securityToken(STS)>',
    bucket: 'galaxydev-xhhl-fx'
  });
  
//  var co = require('co');
//  var OSS = require('ali-oss');
  var ossClient = {
		  signatureUrl : function(data){			  
			  var objectKey = data.fileKey;
		      console.log(data.fileKey + ' => ' + data.fileName);
		      var result = client.signatureUrl(objectKey, {
		        'content-disposition' : 'attachment; filename="' + data.fileName + '"'
		      });
		      console.log(result);
		      return result;
		  },
		  uploadAliCloud : function(){
			  
		  }
  }