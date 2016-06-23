var dom = document.getElementById("container_xmjd");
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
        // 图表标题
    title: {
        text: '60天',
        link: "http://www.baidu.com", //主标题链接地址
        subtext: '45%',
        sublink: "http://www.baidu.com", //副标题链接地址
        x: 'center',                 // 水平安放位置，默认为左对齐，可选为：
                                   // 'center' ¦ 'left' ¦ 'right'
                                   // ¦ {number}（x坐标，单位px）
        y: 'center',                  // 垂直安放位置，默认为全图顶端，可选为：
                                   // 'top' ¦ 'bottom' ¦ 'center'
                                   // ¦ {number}（y坐标，单位px）
        //textAlign: null          // 水平对齐方式，默认根据x设置自动调整
        backgroundColor: 'rgba(0,0,0,0)',
        borderColor: '#ccc',       // 标题边框颜色
        borderWidth: 0,            // 标题边框线宽，单位px，默认为0（无边框）
        padding: 5,                // 标题内边距，单位px，默认各方向内边距为5，
                                   // 接受数组分别设定上右下左边距，同css
        itemGap: 10,               // 主副标题纵向间隔，单位px，默认为10，
        textStyle: {
            fontSize: 18,
            fontWeight: 'bolder',
            color: '#4490d2'          // 主标题文字颜色
        },
        subtextStyle: {
            fontSize: 18,
            fontWeight: 'bolder',
            color: '#4490d2'          // 主标题文字颜色
        }
    },
    legend: {
        orient: 'vertical',
        y:'10px',
        x: 'left',
        data:['接触访谈','内部评审','CEO评审','立项会','投资意向书','尽职调查','投决会','投资协议','股权交割','投后运营']
    },
    color:['#cec938','#becb39','#a6cb29','#a6cb2b','#69bf56','#58b260','#36afa2','#159196','#4790d2','#3c84c6'
],
    series: [
        {
            name:'访问来源',
            type:'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [
                    {name:'接触访谈',y:8},
                    {name: '内部评审',y: 10},
                    { name:'CEO评审',y:16},
                    { name:'立项会',y:20},
                    { name:'投资意向书',y: 30},
                    { name:'尽职调查',y:40},
                    { name:'投决会',y:50},
                    { name:'投资协议',y:55},
                    { name:'股权交割',y:60},
                    { name:'投后运营',y:90},
                ],
        }
    ]
};
if (option && typeof option === "object") {
    var startTime = +new Date();
    myChart.setOption(option, true);
    var endTime = +new Date();
    var updateTime = endTime - startTime;
    console.log("Time used:", updateTime);
}