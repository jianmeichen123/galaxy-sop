/**
 * Created by wangkun on 16/2/18.
 */
$(function () {

    /*项目数目标追踪(个)*/
    $('#container_1').highcharts({
        chart: {
            type: 'bar',
            height :200,
        },
        title: {
            text: '项目目标追踪',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        
        },
        subtitle: {
            text: '单位:个',
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: ['项目数']
        },
        yAxis: {
            min: 0,
            max:120,
            title: {text: ''},
            gridLineColor:'#EEE',
            plotLines: [{
                color: '#FF0000',
                width: 2,
                value: 100
            }]
        },
        legend: {
            backgroundColor: '#FFFFFF',
            reversed: true,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        plotOptions: {
            series: {
                stacking: 'normal'
            },
            bar: {
                pointPadding: 0.5,
                groupPadding: 0.2,
                borderWidth: 0,
                pointWidth: 20,
                dataLabels: {
                    enabled: true,
                    style:{
                        color:'#666',
                        fontWeight:'normal',
                        textShadow:'none',
                        fontFamily:'宋体',

                    },
                }
            }
        },
        series: [{
            name: '超额',
            data: [0],
            color: '#ff955b'
        }, {
            name: '未完成',
            data: [20],
            color: '#4c97da'
        }, {
            name: '已完成',
            data: [80],
            color:'#86c664'
        }]
    });

    /*资金目标追踪（万元）*/
    $('#container_11').highcharts({
        chart: {
            type: 'bar',
            height :200,
        },
        title: {
            text: '投资金额追踪',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        
        },
        subtitle: {
            text: '单位:万元',
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: ['投资额'],


        },
        yAxis: {
            min: 0,
            max:120000,
            title: {text: ''},
            gridLineColor:'#EEE',
            plotLines: [{
                color: '#FF0000',
                width: 2,
                value: 110000,

            }]
        },
        legend: {
            backgroundColor: '#FFFFFF',
            reversed: true,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        plotOptions: {
            series: {
                stacking: 'normal'
            },
            bar: {
                pointPadding: 0.5,
                groupPadding: 0.2,
                borderWidth: 0,
                pointWidth: 20,
                dataLabels: {
                enabled: true,
                style:{
                    color:'#fff',
                    fontWeight:'normal',
                    textShadow:'none',
                    fontFamily:'宋体'
                },
                }
            }
        },
        series: [{
            name: '超额',
            data: [0],
            color: '#ff955b'
        }, {
            name: '未使用',
            data: [40000],
            color: '#4c97da'
        }, {
            name: '已使用',
            data: [60000],
            color:'#86c664'
        }]
    });

    <!-- 个人完成项目占比 -->
    $('#container_2').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            height :340,
            width:500
        },
        title: {
            text: '个人完成项目占比'
        },
        //去除版权
        credits: {
            enabled:false
        },

        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/> 项目数: <b>{point.num}</b>'
        },
        /*plotOptions: {
         pie: {
         allowPointSelect: true,
         cursor: 'pointer',
         dataLabels: {
         enabled: false
         },
         showInLegend: true
         }
         },*/
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,

                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                        //return this.point.percentage.toFixed(1)+"%" + " , " +this.point.num + "个";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:-30
                },
                showInLegend: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'center',
            x: 0,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: '#FFFFFF',
            shadow: true
        },

        series: [{
            type: 'pie',
            name: '项目状态分布',
            data: [
                {
                    name:'我',
                    y:5,
                    num:98,
                    /*sliced: true,
                    selected: true,*/
                    color:"#ff955b"
                },
                {
                    name: '部门其他人',
                    y: 15,
                    num:98,
                    color:"#ffbf9f"
                },
                { name:'其他部门', y: 72,num:98,color:"#ffbf9f"},
            ],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                verticalAlign: 'middle',
                distance:-50,
                align: 'center',
                x: 0, y: 0,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
    <!-- 过会率 -->
    $('#container_21').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            height :340,
            width:250
        },
        title: {
            text: '过会率'
        },
        //去除版权
        credits: {
            enabled:false
        },

        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/> 项目数: <b>{point.num}</b>'
        },
        /*plotOptions: {
         pie: {
         allowPointSelect: true,
         cursor: 'pointer',
         dataLabels: {
         enabled: false
         },
         showInLegend: true
         }
         },*/
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,

                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                        //return this.point.percentage.toFixed(1)+"%" + " , " +this.point.num + "个";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:-30
                },
                showInLegend: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'center',
            x: 0,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: '#FFFFFF',
            shadow: true
        },

        series: [{
            type: 'pie',
            name: '项目状态分布',
            data: [
                {
                    name:'过会率',
                    y:25,
                    num:98,
                    color:"#ff955b"
                },
                { name:'未过会率', y: 72,num:98,color:"#ffbf9f"},
            ],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                verticalAlign: 'middle',
                distance:-50,
                align: 'center',
                x: 0, y: 0,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });

    <!-- 投决率 -->
    $('#container_22').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            height :340,
            width:250
        },
        title: {
            text: '投决率'
        },
        //去除版权
        credits: {
            enabled:false
        },

        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/> 项目数: <b>{point.num}</b>'
        },
        /*plotOptions: {
         pie: {
         allowPointSelect: true,
         cursor: 'pointer',
         dataLabels: {
         enabled: false
         },
         showInLegend: true
         }
         },*/
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,

                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                        //return this.point.percentage.toFixed(1)+"%" + " , " +this.point.num + "个";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:-30
                },
                showInLegend: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'center',
            x: 0,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: '#FFFFFF',
            shadow: true
        },

        series: [{
            type: 'pie',
            name: '项目状态分布',
            data: [
                {
                    name:'投决率',
                    y:5,
                    num:98,
                    color:"#ff955b"
                },
                { name:'未投决率', y: 72,num:98,color:"#ffbf9f"},
            ],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                verticalAlign: 'middle',
                distance:-50,
                align: 'center',
                x: 0, y: 0,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
    <!-- 平台项目状态分布 -->
    $('#container_23').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
        },
        title: {
            text: '项目进度分布',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        

        },
        //去除版权
        credits: {
            enabled:false
        },

        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/> 项目数: <b>{point.num}</b>'
        },
        /*plotOptions: {
         pie: {
         allowPointSelect: true,
         cursor: 'pointer',
         dataLabels: {
         enabled: false
         },
         showInLegend: true
         }
         },*/
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                borderWidth: 0,
                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                        //return this.point.percentage.toFixed(1)+"%" + " , " +this.point.num + "个";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:-30
                },
                showInLegend: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'center',
            x: 0,
            y: 90,
            floating: true,
            backgroundColor: '',
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },

        },

            series: [{
                type: 'pie',
                size:'80%',
                innerSize :'70%',
                name: '项目占比',
                data: [
                    {name:'接触访谈',color:'#cec938',y:8},
                    {name: '内部评审',color:'#becb39',y: 10},
                    { name:'CEO评审',color:'#a6cb29',y:16},
                    { name:'立项会',color:'#a6cb2b',y:20},
                    { name:'投资意向书',color:'#69bf56',y: 30},
                    { name:'尽职调查',color:'#58b260',y:40},
                    { name:'投决会',color:'#36afa2',y:50},
                    { name:'投资协议',color:'#159196',y:55},
                    { name:'股权交割',color:'#4790d2',y:60},
                    { name:'投后运营',color:'#3c84c6',y:90},
                ],
                dataLabels: {
                    enabled: false, 
                }
            }]
    });
    <!-- 平台项目状态分布 -->
    $('#container_24').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            height :340,
            width:450
        },
        title: {
            text: '已完成项目占比',
            align:'center',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },
        },

        //去除版权
        credits: {
            enabled:false
        },

        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/> 项目数: <b>{point.num}</b>'
        },
        /*plotOptions: {
         pie: {
         allowPointSelect: true,
         cursor: 'pointer',
         dataLabels: {
         enabled: false
         },
         showInLegend: true
         }
         },*/
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,

                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                        //return this.point.percentage.toFixed(1)+"%" + " , " +this.point.num + "个";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:-30
                },
                showInLegend: true
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'center',
            x: 10,
            y: 100,
            floating: true,
            backgroundColor: '#FFFFFF',
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },

        series: [{
            type: 'pie',
            name: '项目状态分布',
            data: [
                {
                    name:'投资',
                    y:5,
                    num:98,
                    color:"#ffbf9f"
                },
                { name:'内部自建', y: 20,num:98,color:"#ff955b"},
            ],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                verticalAlign: 'middle',
                distance:-50,
                align: 'center',
                x: 0, y: 0,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });

    <!-- 业务线-目标完成对比 -->
    $('#container_3').highcharts({
        chart: {
            type: 'column',
            height :350,
            width:500
        },
        title: {
            text: '业务线-目标完成对比'
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: [
                '物联网',
                '互联网钢铁',
                '互联网服装',
                '互联网金融',
                '互联网工业',
                '互联网房地产',
                '大数据云计算',
                '互联网工农业',
                '智能设备',
                'o2o及电商'
            ],
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '项目 (个)'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: '目标',
            data: [49, 71, 106, 129, 144, 176, 135, 148, 216, 194]

        }, {
            name: '已完成',
            data: [83, 78, 98, 93, 106, 84, 105, 104, 91, 83]

        }]
    });


    <!-- 项目完成增涨率 -->
    $('#container_4').highcharts({
        chart: {
            zoomType: 'xy',
            height :350,
            width:1200
        },
        title: {
            text: ''
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: [{
            categories: ['201501', '201502', '201503', '201504', '201505', '201506',
                '201507', '201508', '201509', '2015010', '2015011', '2015012'],
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value} %',
                style: {
                    color: '#89A54E'
                }
            },
            title: {
                text: '项目完成率',
                style: {
                    color: '#ff955b'
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '完成项目数',
                style: {
                    color: '#65ade7'
                }
            },
            labels: {
                format: '{value} 个',
                style: {
                    color: '#4572A7'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: '#FFFFFF'
        },
        series: [{
            name: '完成项目数',
            color: '#65ade7',
            type: 'column',
            yAxis: 1,
            data: [7, 6, 9, 14, 18, 21, 25, 26, 23, 18],
            tooltip: {
                valueSuffix: ' mm'
            }

        }, {
            name: '增长率',
            color: '#ff955b',
            type: 'spline',
            data: [49.5, 61.8, 59.1, 92.9, 87.3, 56.7, 78.4, 90.8, 65.3, 88.4],
            tooltip: {
                valueSuffix: '%'
            }
        }]
    });
    <!-- 项目完成率分析 -->
    $('#container_41').highcharts({
        chart: {
            zoomType: 'xy',
            height :350,
            width:1200,
        },
        title: {
            text: '项目完成率分析',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: [{
            categories: ['201501', '201502', '201503', '201504', '201505', '201506',
                '201507', '201508', '201509', '2015010', '2015011', '2015012'],
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value} %',
                style: {
                    color: '#606060'
                }
            },
            title: {
                text: '项目完成率',
            }
        }, { // Secondary yAxis
            title: {
                text: '完成项目数',
            },
            labels: {
                format: '{value} 个',
                style: {
                    color: '#606060'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            floating: true,
            y:20,
            backgroundColor: '#FFFFFF',
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },

        },
        series: [{
            name: '完成项目数',
            color: '#65ade7',
            type: 'column',
            yAxis: 1,
            data: [7, 6, 9, 14, 18, 21, 25, 26, 23, 18],
            tooltip: {
                valueSuffix: ' mm'
            }

        }, {
            name: '项目完成率',
            color: '#86c664',
            type: 'spline',
            data: [49.5, 61.8, 59.1, 92.9, 87.3, 56.7, 78.4, 90.8, 65.3, 88.4],
            tooltip: {
                valueSuffix: '%'
            }
        }],
        plotOptions: {
            column: {
                pointWidth: 40
            }
        }
    });    
<!-- 项目完成增涨率 -->
    $('#container_42').highcharts({
        chart: {
            type: 'column',
            width:1200,
        },
        title: {
            text: '项目数统计TOP10',
            align:'left',
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },
        },
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth:'0',
            categories: ['物联网', '互联网钢铁', '互联网服装', '互联网金融', '互联网工业', '互联网房地产', '大数据云计算', '互联网工农业', '智能设备','o2o及电商']
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        yAxis: {
            title: {
                text: '项目 (个)'
            }
        },
        legend: {
            backgroundColor: '#FFFFFF',
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },       
         tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>万元 <br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                borderWidth: 0,
            }
        },
            series: [{
            name: '创建',
            color:'#8fc4f7',
            data: [216, 210, 200, 180, 165, 155, 135, 125, 120, 100]
        }, {
            name: '投资',
            color:'#7cb5ec',
            data: [106, 106, 100, 90, 88, 66, 55, 44, 33, 22]
        }]
    });

    <!-- 项目访谈数统计 -->
    $('#container_5').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :400,
            width:1200
        },
        title: {
            text: '项目访谈数统计'
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: [
                '闪惠',
                '窝窝团',
                '蜂巢天下',
                '云纵',
                '艾格拉斯',
                '小熊',
                '食乐淘',
                '唇味',
                '卧底火锅',
                '锅否',
                '鲜肉仙',
                '小能科技'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '项目访谈数 (个)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '项目数: <b>{point.y} </b>',
        },
        series: [{
            name: 'Population',
            data: [9,8,15,4,23,3,2,30,20,2,1,11],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: 50,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
                    return this.point.y;

                },
            }
        }]
    });
    <!-- 项目进度分析 -->
    $('#container_51').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :400,
            width:1200
        },
        title: {
            text: '项目访谈数统计'
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: [
                '全部',
                '接触访谈',
                '内部评审',
                '立项会',
                '投资意向书',
                '尽职调查',
                '投资决策会',
                '投资协议',
                '投后运营'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '当前进度数 (个)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '项目数: <b>{point.y} </b>',
        },
        series: [{
            name: 'Population',
            data: [9,8,15,4,23,3,2,30,20],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: 50,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
                    return this.point.y;

                },
            }
        }]
    });
    <!-- 投资事业线目标完成对比 -->
    $('#container_52').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
        },
        title: {
            text: '投资事业线目标完成对比',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        

        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: [
                '互联网钢铁',
                '互联网金融',
                'O2O事业部',
                '互联网工业',
                '物联网',
                '互联网钢铁',
                'O2O事业部',
                '互联网金融',
                '物联网'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                y:50,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    /*writingMode:'tb-rl' ,*/
                    color:'#7a8798',
                }
            }
        },
        yAxis: {
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            min: 0,
            title: {
                text: '当前进度数 (个)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '项目数: <b>{point.y} </b>',
        },
       plotOptions: {
    column: {

        pointWidth: 30
    }
},
        series: [{
            name: 'Population',
            data: [30,23,20,18,10,7,5,2,2],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: 50,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
                    return this.point.y;

                },
            }
        }]
    });
    <!-- 过会率统计 -->
    $('#container_53').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:1200
        },
        title: {
            text: '过会率TOP10',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: [
                '互联网钢铁',
                '互联网金融',
                'O2O事业部',
                '互联网工业',
                '物联网',
                '互联网钢铁',
                'O2O事业部',
                '互联网金融',
                '物联网'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '过会率 (%)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '项目数: <b>{point.y} </b>',
        },
        series: [{
            name: 'Population',
            data: [9,8,15,4,23,3,2,30,20],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: -50,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
                    return this.point.y;

                },
            }
        }]
    });
    <!-- 投决率统计 -->
    $('#container_54').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:1200
        },
        title: {
            text: '投决率TOP10',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: [
                '互联网钢铁',
                '互联网金融',
                'O2O事业部',
                '互联网工业',
                '物联网',
                '互联网钢铁',
                'O2O事业部',
                '互联网金融',
                '物联网'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '投决率 (%)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '项目数: <b>{point.y} </b>',
        },
        series: [{
            name: 'Population',
            data: [30,27,23,20,15,9,8,4,3,2],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: 50,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
                    return this.point.y;

                },
            }
        }]
    });
    <!-- 过会率统计 -->
    $('#container_gh').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:1200
        },
        title: {
            text: '过会率TOP10',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: [
                '互联网钢铁',
                '互联网金融',
                'O2O事业部',
                '互联网工业',
                '物联网',
                '互联网钢铁',
                'O2O事业部',
                '互联网金融',
                '物联网'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '投决率 (%)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '项目数: <b>{point.y} </b>',
        },
        series: [{
            name: 'Population',
            data: [30,27,23,20,15,9,8,4,3,2],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#6b799f',
                align: 'center',
                x: 0,
                y: 0,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 0px #fff',
                    fontWeight:'normal',
                },
                formatter:function(){
                    return this.point.y;

                },
            }
        }]
    });
    <!-- 投资经理项目数TOP10 -->
    $('#container_55').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
        },
        title: {
            text: '投资经理项目数TOP10',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        

        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: [
                '李晓雨',
                '李一雨',
                '李二雨',
                '李三雨',
                '李四雨',
                '李五雨',
                '李六雨',
                '李雨',
                '李晓'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            min: 0,
            title: {
                text: '项目数 (个)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '项目数: <b>{point.y} </b>',
        },
        series: [{
            name: 'Population',
            data: [30,23,20,15,13,10,6,6,5],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: -20,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
                    return this.point.y;

                },
            }
        }]
    });
    <!-- 团队绩效考核 -->
    $('#container_56').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:1200
        },
        title: {
            text: '团队项目数TOP10',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: [
                '物联网',
                '互联网钢铁',
                '互联网服装',
                '互联网金融',
                '互联网工业',
                '互联网房地产',
                '互联网大数据',
                '互联网教育',
                '互联网工农业'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            min: 0,
            title: {
                text: '项目数 (个)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '项目数: <b>{point.y} </b>',
        },
        series: [{
            name: 'Population',
            data:  [30,23,20,15,13,10,6,6,5],
            dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: 50,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
                    return this.point.y;

                },
            }
        }]
    });

    <!-- 业务线-目标完成对比 -->
    $('#container_3').highcharts({
        chart: {
            type: 'column',
            height :350,
            width:500
        },
        title: {
            text: '业务线-目标完成对比'
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            categories: [
                '物联网',
                '互联网钢铁',
                '互联网服装',
                '互联网金融',
                '互联网工业',
                '互联网房地产',
                '大数据云计算',
                '互联网工农业',
                '智能设备',
                'o2o及电商'
            ],
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            min: 0,
            title: {
                text: '项目 (个)'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: '目标',
            data: [49, 71, 106, 129, 144, 176, 135, 148, 216, 194]

        }, {
            name: '已完成',
            data: [83, 78, 98, 93, 106, 84, 105, 104, 91, 83]

        }]
    });


    <!-- 企业交易走势 -->
    $('#container_jyzs').highcharts({
        chart: {
            zoomType: 'xy',
            height :350,
            width:1200
        },
        title: {
            text: '季度交易走势图'
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: [{
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: ['Q1', 'Q2', 'Q3', 'Q4'],
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value} %',
                style: {
                    color: '#89A54E'
                }
            },
            title: {
                text: '交易额',

            }
        }, { // Secondary yAxis
            title: {
                text: '',

            },
            labels: {
                style: {
                    color: '#4572A7'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: '#FFFFFF'
        },
        series: [{
            name: '2013年',
            color: '#65ade7',
            type: 'spline',
            yAxis: 1,
            data: [7, 6, 9, 14],
            tooltip: {
                valueSuffix: ' mm'
            }

        }, {
            name: '2014年',
            color: '#ff955b',
            type: 'spline',
            data: [49.5, 61.8, 59.1, 92.9],
            tooltip: {
                valueSuffix: ''
            }
        }]
    });
/*企业投资金额统计*/
    $('#container_qytzje').highcharts({                                   
        chart: {                                                   
            type: 'bar',
            height:'300',
        },                                                                 
        title: {                                                   
            text: '企业投资金额统计' ,
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },        
        },                                                                 
                
        //去除版权
        credits: {
          enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:false
        },
        xAxis: {                                                   
            categories: ['企业投资金额统计'],
            title: {                                               
                text: null 

            },
            labels: {
                rotation: -90,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },                                                                 
        yAxis: {                                                   
            min: 0,                                               
            title: {                                               
                text: '金额 (万元)',                             
                align: 'high'                                     
            },                                                             
            labels: {                                              
                overflow: 'justify'                               
            }                                                              
        },                                                                 
        tooltip: {                                                 
            valueSuffix: '万'                                       
        },                                                           
        plotOptions: {                                             
            bar: {
                pointPadding: 0.5,
                groupPadding: 0.2,
                borderWidth: 0,
                pointWidth: 20,
                dataLabels: {                                     
                    enabled: true                                 
                }                                                          
            }                                                              
        },                                                                 
        legend: {                                                 
            layout: 'horizontal',                                  
            align: 'center',                                       
            verticalAlign: 'bottom',  
            y:10,                              
            floating: true,                                       
            borderWidth: 0, 
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },                                                                
        credits: {                                                 
            enabled: false                                         
        },                                                                 
        series: [{                                                               
            name: '已使用金额',  
            color:'#7cb5ec',                                      
            data: [1730]                                
        }, {                                                               
            name: '剩余金额',
            color:'#33afa2',                                              
            data: [2860]                                  
        } ,{                                                         
            name: '投资总额',
            color:'#90ed7d',                                              
            data: [3070]                                   
        }]                                                                 
    });
/*项目退出占比*/        
        $('#container_xmtczb').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                height :380,
                width:1200

            },
            title: {
                text: '项目退出占比',
                align:'left',  
                style:{
                    fontSize:'18px',
                    fontFamily:'微软雅黑',
                    color:'#808e9b',
                },        
            },
            //去除版权
            credits: {
              enabled:false
            },
            //去除右上角导出图标
            exporting: {
                enabled:true
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
           pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:120
                },
                showInLegend: true
            }
        },
           
        legend: {                                                 
            layout: 'horizontal',                                  
            align: 'center',                                       
            verticalAlign: 'bottom',  
            floating: true,                                       
            borderWidth: 0, 
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },             
        series: [{
                type: 'pie',
                size:250,
                innerSize :80,
                name: '项目退出占比',
                data: [
                    {name: '被收购',color:'#33afa2',y: 17},
                    { name:'公司清算',color:'#69bf55',y:14},
                    { name:'公司上市',color:'#a6cb29',y:23},
                    { name:'股权转让',color:'#eedc5f',y:12}
                ],
                dataLabels: {
                    enabled: true, 
                    rotation: 0, 
                    color: '#FFFFFF', 
                    verticalAlign: 'middle',
                    distance:-50,
                    align: 'center', 
                    x: 0, y: 0, 
                    style: { 
                        fontSize: '13px', 
                        fontFamily: 'Verdana, sans-serif',                              
                        textShadow: '0 0 3px black'                      
                    } 
                }
            }]
        });
/*企业现金流*/
    $('#container_qyxjl').highcharts({
        chart: {
            type: 'column',
            width:'1200',
        },
        title: {
            text: '企业现金流表',
            align:'left',
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },
        },
        xAxis: {
            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        legend: {
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        yAxis: {
            title: {
                text: '企业现金流(万元)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '元'
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '经营活动净额',
            color:'#33afa2',
            data: [-180,80,90,100,90,120,150,200,140,120,110,210]
        }, {
            name: '投资活动净额',
            color:'#69bf55',
            data: [-80,50,100,150,70,130,110,210,270,400,460,310]
        }, {
            name: '筹款活动净额',
            color:'#a6cb29',
            data: [30,30,60,65,80,45,25,90,60,50,100,80]
        }, {
            name: '其它净额',
            color:'#eedc5f',
            data: [-40,70,90,55,110,95,80,-69,50,-40,70,30]
        }]
    });
/*资产负债*/
    $('#container_zcfz').highcharts({
        chart: {
            type: 'column',
            width:'1200',
        },
        title: {
            text: '企业资产负债表',
            align:'left',
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },
        },
        xAxis: {
            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        legend: {
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        yAxis: {
            title: {
                text: '金额(万元)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '元'
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '流动资产额',
            color:'#169196',
            data: [-180,80,90,100,90,120,150,200,140,120,110,210]
        }, {
            name: '可供出售金额资产额',
            color:'#33afa2',
            data: [-80,50,100,150,70,130,110,210,270,400,460,310]
        }, {
            name: '其他流动负债额',
            color:'#69bf55',
            data: [30,30,60,65,80,45,25,90,60,50,100,80]
        }, {
            name: '流动负债额',
            color:'#a6cb29',
            data: [-40,70,90,55,110,95,80,-69,50,-40,70,30]
        },{
            name: '其他非流动负债额',
            color:'#eedc5f',
            data: [-40,70,90,55,110,95,80,-69,50,-40,70,30]
        }]
    });
/*企业利润表*/
    $('#container_qylr').highcharts({
        chart: {
            type: 'column',
            width:'1200',
        },
        title: {
            text: '企业利润表',
            align:'left',
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#808e9b',
            },
        },
        xAxis: {
            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        yAxis: {
            title: {
                text: '企业利润(万元)'
            }
        },
        legend: {
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>万元 <br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                borderWidth: 0,
            }
        },
            series: [{
            name: '营业收入',
            color:'#4490d2',
            data: [180,80,90,100,90,120,150,200,140,120,110,210]
        }, {
            name: '管理费用',
            color:'#eedc5f',
            data: [-20,-30,-20,-30,-40,-30,-30,-20,-40,-20,-10,-30]
        }, {
            name: '财务费用',
            color:'#a6cb29',
            data: [-30,-20,-40,-50,-60,-70,-50,-60,-40,-50,-50,-60]
        }, {
            name: '营业利润',
            color:'#169196',
            data: [130,40,30,100,80,90,100,120,150,300,320,260]
        }, {
            name: '利润总和',
            color:'#33afa2',
            data: [-90,90,100,130,110,150,120,140,180,190,210,240]
        }, {
            name: '净利润',
            color:'#69bf55',
            data: [-50,30,20,90,80,70,90,110,130,260,280,200]
        }]
    });
/*季度交易走势图*/
    $('#container_jdjy').highcharts({
        title: {
            text: '季度交易走势图',
            align:'left',  
                style:{
                    fontSize:'18px',
                    fontFamily:'微软雅黑',
                    color:'#808e9b',
                },        
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: ['Q1', 'Q2', 'Q3', 'Q4']
        },
        yAxis: {
            title: {
                text: '交易金额(万元)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '元'
        },
        legend: {                                                 
            layout: 'horizontal',                                  
            align: 'center',                                       
            verticalAlign: 'bottom',  
            y:20,                              
            floating: true,                                       
            borderWidth: 0, 
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
                fontFamily:'宋体',
            },
        }, 
        series: [{
            name: '2013',
            color:'#78b3ec',
            data: [50, 80, 150, 350]
        }, {
            name: '2014',
            color:'#ff955b',
            data: [100, 200, 300, 400]
        }, {
            name: '2015',
            color:'#90ed7d',
            data: [270, 360, 450, 600]
        }]
    });
        $('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '企业利润表'
        },
        xAxis: {
            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        yAxis: {
            title: {
                text: '企业利润(万元)'
            }
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>万元 <br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'percent'
            }
        },
            series: [{
            name: '营业收入',
            data: [180,80,90,100,90,120,150,200,140,120,110,210]
        }, {
            name: '管理费用',
            data: [-20,-30,-20,-30,-40,-30,-30,-20,-40,-20,-10,-30]
        }, {
            name: '财务费用',
            data: [-30,-20,-40,-50,-60,-70,-50,-60,-40,-50,-50,-60]
        }, {
            name: '营业利润',
            data: [130,40,30,100,80,90,100,120,150,300,320,260]
        }, {
            name: '利润总和',
            data: [-90,90,100,130,110,150,120,140,180,190,210,240]
        }, {
            name: '净利润',
            data: [-50,30,20,90,80,70,90,110,130,260,280,200]
        }]
    });
/*活跃用户数*/
    $('#container_hyyhs').highcharts({
        chart: {
            height :300,
            width:1200
        },
        title: {
            text: '活跃用户数',
            x: -20 ,//center
            style:{
                color:'#fff',
            },
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        yAxis: {
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            title: {
                text: '用户数（个）'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '个'
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        series: [{
            name: '活跃用户数',
            data: [4800, 5200, 5000, 5100, 5100, 5300, 7000, 8100, 10100, 15000, 16000, 22000]
        }]
    });

/*新增用户数*/
    $('#container_xzyhs').highcharts({
        chart: {
            height :300,
            width:1200
        },
        title: {
            text: '新增用户数',
            x: -20 ,//center
            style:{
                color:'#fff',
            },
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        yAxis: {
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            title: {
                text: '用户数（个）'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '个'
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        series: [{
            name: '新增用户数',
            data: [4800, 5200, 5000, 5100, 5100, 5300, 7000, 8100, 10100, 15000, 16000, 22000]
        }]
    });
/*累计用户数*/
    $('#container_total').highcharts({
        chart: {
            height :300,
            width:1200
        },
        title: {
            text: '累计用户数',
            x: -20 ,//center
            style:{
                color:'#fff',
            },
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        yAxis: {
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            title: {
                text: '用户数（个）'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '个'
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        series: [{
            name: '累计用户数',
            data: [4800, 5200, 5000, 5100, 5100, 5300, 7000, 8100, 10100, 15000, 16000, 22000]
        }]
    });
/*流失用户数*/
    $('#container_lsyhs').highcharts({
        chart: {
            height :300,
            width:1200
        },
        title: {
            text: '流失用户数',
            x: -20 ,//center
            style:{
                color:'#fff',
            },
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        xAxis: {
            lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        yAxis: {
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            title: {
                text: '用户数（个）'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '个'
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        series: [{
            name: '流失用户数',
            data: [4800, 5200, 5000, 5100, 5100, 5300, 7000, 8100, 10100, 15000, 16000, 22000]
        }]
    });
/*投资资金*/
    $('#container_investmentFunds').highcharts({
        chart: {
            type: 'line',        
        },
        title: {
            text: '',
            x: -20 //center
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        xAxis: {
            tickInterval:2,
            lineWidth: 1,
            lineColor: "#edeff5",
            tickWidth: 0,
            labels: {
                y: 20, //x轴刻度往下移动20px
                style: {
                    color: '#7a8798',//颜色
                    fontFamily:'宋体',
                }
            },
            categories: ['201501', '201502', '201503', '201504', '201505', '201506', '201507', '201508', '201509', '201510', '201511', '201512']
        },
        yAxis: {
            gridLineColor: '#f6f7fa',
            gridLineWidth: 1,
            labels: {
                format: '{value} M',
                x: -10, //y轴刻度往左移动10px
                style: {
                    color: '#999',//颜色
                    fontFamily:'宋体',  //字体
                }
            },
            title: {
                text: '金额'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            tickPositions: [0, 500, 1000, 1500, 2000, 2500, 3000]
        },
        plotOptions: {
            series: {
                marker: {
                    enabled: false
                }
            },
        },
        tooltip: {
            valueSuffix: '万元'
        },
        legend: {
            itemMarginTop:-10,
            itemMarginBottom:-10,
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'top',
            borderWidth: 0,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        series: [{
            lineWidth: 1.5,
            name: '初始估值',
            color:'#65ade7',
            data: [10, 100, 500, 750,800, 900, 1000,1200, 1500, 1800,2000,3000]
        }, {
            lineWidth: 1.5,
            name: '投资金额',
            color:'#87c36c',
            data: [0, 90, 300, 700,700, 800, 900,1100, 1400, 1600,2000,2500]
        }]
    });
/*项目运营*/
  $('#container_operation').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: ''
        },
        xAxis: {
            lineWidth: 1,
            lineColor: "#edeff5",
            tickWidth: 0,
            labels: {
                y: 20, //x轴刻度往下移动20px
                style: {
                    color: '#999',//颜色
                    fontFamily:'宋体',  //字体
                }
            },
            categories: ['201501', '201502', '201503', '201504', '201505', '201506', '201507', '201508', '201509', '201510', '201511', '201512']
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        yAxis: {
            lineColor: '#edeff5',
            lineWidth: 1,
            //tickWidth: 1,
           //tickColor: '#edeff5',
            gridLineColor: '#f6f7fa',
            gridLineWidth: 1,
            labels: {
                x: -10, //y轴刻度往左移动10px
                style: {
                    color: '#999',//颜色
                    fontFamily:'宋体',  //字体
                }
            },            
            title: {
                text: '项目数'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            tickPositions: [0, 50, 100, 150, 200, 250, 300],
        },
        legend: {
            itemMarginTop:-10,
            itemMarginBottom:-10,
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'top',
            borderWidth: 0,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>个<br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                borderWidth: 0,
            }
        },
            series: [ {
            color:'#a5d5fb',
            name: '差',
            data: [10,100,100,90,80,50,30,20,60,100,50,30]
        }, {
            color:'#65ade7',
            name: '良',
            data: [30,20,120,30,40,130,30,120,140,120,10,130]
        }, {
            color:'#4c97da',
            name: '优',
            data: [180,150,120,100,70,70,50,40,40,30,10,10]
        }]
    });
/*绩效考核*/
$('#container_KPI').highcharts({
        chart: {
            zoomType: 'xy',
            backgroundColor: 'rgba(255, 255, 255, 0)',
        },
        title: {
            text: ''
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        /*exporting: {
         enabled:false
         },*/
        xAxis: {
            lineWidth: 1,
            lineColor: "#edeff5",
            tickWidth: 0,
            labels: {
                y: 20, //x轴刻度往下移动20px;
                staggerLines:2,
                style: {
                    color: '#7a8798',//颜色
                    fontFamily:'宋体',
                }
            },
            categories: ['物联网', 'O2O事业部', '互联网服装', '互联网金融', '互联网工业', '互联网服装', '互联网金融', '互联网工业']
        },
        yAxis: [{ // Primary yAxis
            lineColor: '#edeff5',
            lineWidth: 1,
            gridLineColor: '#f6f7fa',
            gridLineWidth: 1,
            min: 0,
            labels: {
                format: '{value} %',
                style: {
                    color: '#999',//颜色
                    fontFamily:'宋体',  //字体
                }
            },
               title: {
                    text: '过会率',
                    style: {
                        color: '#7a8798'
                    }
                }
            }, { // Secondary yAxis
            gridLineColor: '#f6f7fa',
            gridLineWidth: 1,
            title: {
                text: '项目数',
                style: {
                    color: '#7a8798'
                }
            },
            labels: {
                style: {
                    color: '#4572A7'
                }
            },
            opposite: true
        }],
                plotOptions: {
            column: {
                pointWidth: 20,
                dataLabels:{
                            enabled:false, 
                        }
            },
        },

        tooltip: {
            shared: true
        },
        legend: {
            itemMarginTop:-10,
            itemMarginBottom:-10,
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'top',
            borderWidth: 0,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },
        series: [{
            name: '项目数',
            color: '#65ade7',
            type: 'column',
            yAxis: 1,
            data: [20, 18, 9, 7, 18],
            tooltip: {
                valueSuffix: ' mm'
            }

        }, {
            lineWidth: 1,
            name: '过会率',
            color: '#87c36c',
            type: 'spline',
            data: [10.5, 15.8, 60.1, 50.9, 30.3, 60.1, 50.9, 30.3],
            tooltip: {
                valueSuffix: '%'
            }
        }]
    });
/*项目进度*/        
        $('#container_progress').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                backgroundColor: 'rgba(255, 255, 255, 0)',
            },
            title: {
                text: "<span style='color:#4490d2'>"+'46个'+"</span>"+"<br/>"+"<span>"+'25%'+"</span>",
                verticalAlign:'middle',
                y:-5,
                x:-95,
                style:{
                    fontFamily:'微软雅黑',
                    color:'#4490d2',
                    fontWeight:'bold',
                },
            },
            //去除版权
            credits: {
              enabled:false
            },
            //去除右上角导出图标
            exporting: {
                enabled:true
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
            pie: {
                borderWidth: 0,
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:120
                },
                showInLegend: true
            }
        },

        legend: {                                                 
            layout: 'horizontal', 
            floating: false,                                       
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            itemWidth:90,
            width:200,
            padding:-20,
            minHeight:100,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
            //x:0,
        },            

            series: [{
                type: 'pie',
                size:'140%',
                innerSize :'70%',
                name: '项目退出占比',
                data: [
                    {name:'接触访谈',color:'#d9c53e',y:8},
                    {name: '内部评审',color:'#cec938',y: 10},
                    { name:'CEO评审',color:'#becb39',y:16},
                    { name:'立项会',color:'#a6cb2b',y:20},
                    { name:'投资意向书',color:'#69bf56',y: 30},
                    { name:'尽职调查',color:'#58b260',y:40},
                    { name:'投决会',color:'#36afa2',y:50},
                    { name:'投资协议',color:'#159196',y:55},
                    { name:'股权交割',color:'#4790d2',y:60},
                    { name:'投后运营',color:'#3c84c6',y:90},
                ],

                dataLabels: {
                    enabled: false, 
                }
            }]
        });
/*项目历时*/        
        $('#container_time').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                backgroundColor: 'rgba(255, 255, 255, 0)',
            },
            title: {
                text: "<span style='color:#4490d2'>"+'60天'+"</span>"+"<br/>"+"<span>"+'45%'+"</span>",
                verticalAlign:'middle',
                y:-5,
                x:-95,
                style:{
                    fontFamily:'微软雅黑',
                    color:'#4490d2',
                    fontWeight:'bold',
                },
            },
            //去除版权
            credits: {
              enabled:false
            },
            //去除右上角导出图标
            exporting: {
                enabled:true
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
            pie: {
                borderWidth: 0,
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:120
                },
                showInLegend: true
            }
        },
        legend: {                                                
            layout: 'horizontal',                                  
            floating: false,                                       
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            itemWidth:90,
            width:200,
            padding:-20,
            minHeight:100,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
            //x:0,
        },            

            series: [{
                type: 'pie',
                size:'140%',
                innerSize :'70%',
                name: '项目退出占比',
                data: [
                    {name:'接触访谈',color:'#d9c53e',y:8},
                    {name: '内部评审',color:'#cec938',y: 10},
                    { name:'CEO评审',color:'#becb39',y:16},
                    { name:'立项会',color:'#a6cb2b',y:20},
                    { name:'投资意向书',color:'#69bf56',y: 30},
                    { name:'尽职调查',color:'#58b260',y:40},
                    { name:'投决会',color:'#36afa2',y:50},
                    { name:'投资协议',color:'#159196',y:55},
                    { name:'股权交割',color:'#4790d2',y:60},
                ],
                dataLabels: {
                    enabled: false, 
                }
            }]
        });
});