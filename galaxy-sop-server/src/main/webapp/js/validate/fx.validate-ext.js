jQuery.validator.addMethod("emails", function(value, element) {   
    var mail = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
    var isMail = true;
    if(value.indexOf(";")>0)
	{
    	var mails = value.split(";");
    	for(var i=0;i<mails.length;i++)
		{
    		if(mail.test(mails[i])==false)
			{
    			isMail = false;
    			break;
			}
		}
	}
    else
	{
    	isMail = mail.test(value);
	}
    return this.optional(element) || isMail;
}, "请输入有效的电子邮件地址,多个地址用分号（;）分割");