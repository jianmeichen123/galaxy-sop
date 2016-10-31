
var datepickerSelecter = {
		count : 0,
		/**
		 * 日期选择器初始化
		 * param : 
		 * 		title : 标题 
		 * 		domid : 挂在的元素ID (*必填)
		 * 		day : 支持到日
		 * 		month ：支持到月
		 * 		defaultShow : 默认显示
		 */
		init : function(formdata){
			if(formdata){
				var radioDayHtml = "<input type='radio' name='chooseDate' value='day' />日";
				var radioMonthHtml = "<input type='radio' name='chooseDate' value='month' />月";
				var pickerDayHtml = "<dd id='search_date_choose_day'  style='display: none;'>" +
									"<input type='text' class='txt time datepicker' id='search_start_time' name='startTime' value='' /> " +
									"<span>至</span> " +
									"<input type='text' class='txt time datepicker' id='search_end_time' name='endTime' value='' />" + 
								    "</dd>";
				var pickerMonthHtml = "<dd id='search_date_choose_month' style='display: none;'> " +
									  "<input type='text' class='txt time change_month' id='search_start_time' name='startTime' value='' /> " +
									  "<span>至</span> " +
									  "<input type='text' class='txt time change_month' id='search_end_time' name='endTime' value='' /> " +
									  "</dd>";
				var pickerHtml = "";
				var html = "<dl class='fmdl fmmr clearfix'>";
				html += "<dt>";
				html += formdata.title ? formdata.title : "项目创建日期："
				html += "</dt>";
				html += "<dd id='radio_dd'>";
				var count = 0;
				if(formdata.day == true){
					html += radioDayHtml;
					pickerHtml += pickerDayHtml;
					count++;
				}
				if(formdata.month == true){
					html += radioMonthHtml;
					pickerHtml += pickerMonthHtml;
					count++
				}
				datepickerSelecter.count = count;
				if(count == 0){
					//默认只支持日
					html += radioDayHtml;
					pickerHtml += pickerDayHtml;
					datepickerSelecter.count = 1;
				}
				
				html += "</dd>";
				html += pickerHtml;
				html += "</dl>";
				html += "</div>";
				if(formdata.domid){
					$("#" + formdata.domid).html(html);
					if(datepickerSelecter.count > 1){
						//进入选择模式
						$("#" + formdata.domid).find("input[name='chooseDate']").bind('change',function(){
							var chooseResult = $("input[name='chooseDate']:checked").val();
							if(chooseResult=="day"){ //日
								$("#" + formdata.domid).find("#search_date_choose_day").show();
								$("#" + formdata.domid).find("#search_date_choose_month").hide();
							}else if(chooseResult=="month"){//月
								$("#" + formdata.domid).find("#search_date_choose_day").hide();
								$("#" + formdata.domid).find("#search_date_choose_month").show();
							}else{
								return false;
							}
						});
					}else{
						//去掉radio
						$("#" + formdata.domid).find("#radio_dd").html("");
					}
					if(formdata.defaultShow){
						$("#" + formdata.domid).find("input[name='chooseDate'][value='"+formdata.defaultShow+"']").attr("checked",true);
						$("#" + formdata.domid).find("#search_date_choose_" + formdata.defaultShow).show();
					}else{
						if(datepickerSelecter.count > 1){
							$("#" + formdata.domid).find("input[name='chooseDate'][value='day']").attr("checked",true);
						}
						$("#" + formdata.domid).find("#search_date_choose_day").show();
					}
				}else{
					return;
				}
			}else{
				return;
			}
			
		}
}