<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.galaxyinternet.framework.core.constants.Constants,com.galaxyinternet.framework.core.oss.OSSConstant"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page import="com.galaxyinternet.model.user.User"%>
<%@ page import="com.galaxyinternet.framework.core.constants.Constants"%>
<%
User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
String sessionId = "";
String realName = "";
Long departmentId=null;
Long userId=null;
Long roleId=null;
if(null != user) {
	sessionId = user.getSessionId();
	if(null != user.getRealName()){
		realName = user.getRealName();
	}
    if(null != user.getRoleId()){
	   roleId = user.getRoleId();
    }
	userId = user.getId();
	departmentId = user.getDepartmentId();
}
String endpoint = (String)application.getAttribute(OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
%>

<!-- 这里写js和css文件---------start -->
<script src="<%=request.getContextPath() %>/js/jquery-1.12.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jquery-validate.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/json2.js" type="text/javascript"></script>
<!-- end -->

<script type="text/javascript">
	var console = console || {
	    log : function(){
	        return false;
	    }
	}
	var contextEndPoint = '<%=endpoint%>';
	endpointObj = JSON.parse(contextEndPoint);
	console.log(endpointObj);
	var sessionId = '<%=sessionId%>';
	var realName = '<%=realName%>';
	var userId = '<%=userId%>';
	var departmentId='<%=departmentId%>';
	var roleId = '<%=roleId%>';
	
	if ( !Array.prototype.forEach ) {

		  Array.prototype.forEach = function forEach( callback, thisArg ) {

		    var T, k;

		    if ( this == null ) {
		      throw new TypeError( "this is null or not defined" );
		    }
		    var O = Object(this);
		    var len = O.length >>> 0; 
		    if ( typeof callback !== "function" ) {
		      throw new TypeError( callback + " is not a function" );
		    }
		    if ( arguments.length > 1 ) {
		      T = thisArg;
		    }
		    k = 0;

		    while( k < len ) {

		      var kValue;
		      if ( k in O ) {

		        kValue = O[ k ];
		        callback.call( T, kValue, k, O );
		      }
		      k++;
		    }
		  };
		}
	
	if (!Object.keys) {
		  Object.keys = (function () {
		    var hasOwnProperty = Object.prototype.hasOwnProperty,
		        hasDontEnumBug = !({toString: null}).propertyIsEnumerable('toString'),
		        dontEnums = [
		          'toString',
		          'toLocaleString',
		          'valueOf',
		          'hasOwnProperty',
		          'isPrototypeOf',
		          'propertyIsEnumerable',
		          'constructor'
		        ],
		        dontEnumsLength = dontEnums.length;

		    return function (obj) {
		      if (typeof obj !== 'object' && typeof obj !== 'function' || obj === null) throw new TypeError('Object.keys called on non-object');

		      var result = [];

		      for (var prop in obj) {
		        if (hasOwnProperty.call(obj, prop)) result.push(prop);
		      }

		      if (hasDontEnumBug) {
		        for (var i=0; i < dontEnumsLength; i++) {
		          if (hasOwnProperty.call(obj, dontEnums[i])) result.push(dontEnums[i]);
		        }
		      }
		      return result;
		    }
		  })()
		};
</script>
<script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/alioss/aliyun-oss-sdk.min.js"></script>
<script src="<%=request.getContextPath() %>/js/alioss/alioss_init.js"></script>
<script src="<%=request.getContextPath() %>/js/axure.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/axure_ext.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/platformUrl.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/my.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/my_ext.js"></script>
<link rel="shortcut icon" href="<%=request.getContextPath() %>/img/favicon.ico" />
