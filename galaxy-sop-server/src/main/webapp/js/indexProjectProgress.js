/**
 * 
 */
var chartIndexPProgressUtils = {
		chartIndexPProgressOptions : {
			chart: {
				renderTo :'histogram',
		        type: 'column'
		    },
		    title: {
		        text: ''
		    },
		    credits: {
		        enabled:false
		    },
		    exporting: {
		    	enabled:false
		    },
		    xAxis: {
		        //categories: ['朱玟','牟敏','关屿','赵广智','陈丛翀','王飞韵','蔡燕','王晓宇'],
		        tickWidth:0,
		    	labels: {
		            rotation:-45,
		            //align: 'right',
		            //staggerLines:2,
		            style: {
		                fontSize: '12px',
		                fontFamily: 'Verdana, sans-serif'
		                //writingMode:'tb-rl'   //文字竖排样式,
		            },
		        }
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text:''
		        }
		    },
		    legend: {
		        enabled: false,
		        x:5,
		    },
		    tooltip: {
		    	backgroundColor: 'rgba(255,255,255,0.9)',   // 背景颜色
		        borderColor: '#9dd2fc',         // 边框颜色
		        borderRadius: 1,             // 边框圆角
		        borderWidth: 1,               // 边框宽度
		        shadow: false,                 // 是否显示阴影
		        animation: true,               // 是否启用动画效果
		        style: {                      // 文字内容相关样式
		            color: "#ff0000",
		            fontSize: "12px",
		            fontWeight: "normal",
		            fontFamily: "宋体"
		        },
		    	/*useHTML: true,
		    	formatter: function(){
		    		return this.point.x +'<br/>项目数:'+ this.point.y +'个';
		    	}*/
		    	enabled:true
		    },
		    series: [{
		        name: '项目数',
		        //data: [8,5,4,3,3,2,2,2,2],
		        dataLabels: {
		            enabled: false,
		            rotation: 0,
		            color: '#FFFFFF',
		            align: 'center',
		            x: 0,
		            y: 25,
		            style: {
		                fontSize: '13px',
		                fontFamily: 'Verdana, sans-serif',
		                textShadow: '0 0 3px black'
		            },
		            /*formatter:function(){
		     			return this.point.y;
					},*/
		        }
		    }]
		},
		init : function(formdata){
			
			if(formdata.domid){
				chartIndexPProgressUtils.chartIndexPProgressOptions.chart.renderTo = formdata.domid;
			}
			var form = {
					createUid : formdata.createUid ? formdata.createUid : userId,
					startTime :  DateUtils.getTime(DateUtils.getYearFirstDay())
			};		
			sendPostRequestByJsonObj(platformUrl.searchOverView,form,function(data){
				if(data.result.status == 'OK'){
					if(data.entityList){
						var projectCountArr = new Array();
						var projectProgressArr = new Array();
						$.each(data.entityList,function(){
							projectCountArr.push(this.projectCount);
							projectProgressArr.push(this.projectProgressName);
						});
						chartIndexPProgressUtils.chartIndexPProgressOptions.series[0].data = projectCountArr;
						chartIndexPProgressUtils.chartIndexPProgressOptions.xAxis.categories = projectProgressArr;
						var chart = new Highcharts.Chart(chartIndexPProgressUtils.chartIndexPProgressOptions);
					}else{
						layer.msg('后端数据为空');
					}
				}else{
					layer.msg(data.result.errorCode);
				}
			});
		}
}