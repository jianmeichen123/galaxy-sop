/**
 * Created by wangkun on 16/2/18.
 */
$(function () {

    /*项目数目标追踪(个)*/
    $('#container_1').highcharts({
        chart: {
            type: 'bar',
            height :200,
            width:1200
        },
        title: {
            text: '项目目标追踪',
             style: {
                    color: "#fff",
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
            reversed: true
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
                    enabled: true
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
            width:1200,
        },
        title: {
            text: '投资金额追踪',
            style: {
                    color: "#fff",
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
            categories: ['项目数'],

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
            reversed: true
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
                    enabled: true
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
            height :340,
            width:450
        },
        title: {
            text: '项目进度分布',
            style: {
                    color: "#fff",
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
            x: 20,
            y: 100,
            floating: true,
            backgroundColor: '#FFFFFF',
        },

        series: [{
            type: 'pie',
            name: '项目状态分布',
            data: [
                {
                    name:'接触访谈',
                    y:5,
                    num:98,
                    color:"#86c664"
                },
                { name:'内部评审', y: 20,num:98,color:"#ff955b"},
                { name:'立项会', y: 6,num:98,color:"#4a97da"},
                { name:'投资意向书', y: 0,num:98,color:"#f26363"},
                { name:'尽职调查', y: 23,num:98,color:"#7e91d2"},
                { name:'投资决策会', y: 0,num:98,color:"#5cdaba"},
                { name:'投资协议', y: 15,num:98,color:"#e09b50"},
                { name:'股权交割', y: 0,num:98,color:"#796f65"},
                { name:'投后运营', y: 6,num:98,color:"#ffbf9f"},
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
                    textShadow: '0 0 3px black',
                }
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
            text: '已完成项目占比'
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
        },

        series: [{
            type: 'pie',
            name: '项目状态分布',
            data: [
                {
                    name:'投资',
                    y:5,
                    num:98,
                    color:"#86c664"
                },
                { name:'自建', y: 20,num:98,color:"#ff955b"},
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
    <!-- 项目完成增涨率 -->
    $('#container_41').highcharts({
        chart: {
            zoomType: 'xy',
            height :350,
            width:1200
        },
        title: {
            text: '',

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
            }
        }, { // Secondary yAxis
            title: {
                text: '完成项目数',
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
            name: '项目完成率',
            color: '#ff955b',
            type: 'spline',
            data: [49.5, 61.8, 59.1, 92.9, 87.3, 56.7, 78.4, 90.8, 65.3, 88.4],
            tooltip: {
                valueSuffix: '%'
            }
        }]
    });    
<!-- 项目完成增涨率 -->
    $('#container_42').highcharts({
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
                text: '项目完成增长率',
                style: {
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '完成项目数',
                style: {
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
    <!-- 投资事业线项目跟踪 -->
    $('#container_52').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:800
        },
        title: {
            text: '业务线—目标完成对比',
            style: {
                    color: "#fff",
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
    <!-- 过会率统计 -->
    $('#container_53').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:1200
        },
        title: {
            text: '过会率TOP10'
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
    <!-- 投决率统计 -->
    $('#container_54').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:1200
        },
        title: {
            text: '投决率TOP10'
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
    <!-- 过会率统计 -->
    $('#container_gh').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:1200
        },
        title: {
            text: '过会率TOP10'
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
    <!-- 投资经理项目数TOP10 -->
    $('#container_55').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80],
            height :340,
            width:1200
        },
        title: {
            text: '投资经理项目数TOP10',
            style: {
                    color: "#fff",
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
            style: {
                color: "#fff",
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



});