<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
    <script src="js/highmaps.js"></script>
<script src="js/modules/exporting.js"></script>
<script src="js/china-data.js"></script>


    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
	#container {
    height: 700px; 
    min-width: 310px; 
    max-width: 800px; 
    margin: 0 auto; 
}
.loading {
    margin-top: 10em;
    text-align: center;
    color: gray;
}
	</style>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	
	$(function () {

    // Prepare demo data   tw开头表示台湾的城市
    var data = [
        {
            "hc-key": "cn-sh",
            "value": 0
        },
        {
            "hc-key": "cn-zj",
            "value": 1
        },
        {
            "hc-key": "tw-ph",
            "value": 2
        },
        {
            "hc-key": "tw-km",
            "value": 3
        },
        {
            "hc-key": "tw-lk",
            "value": 4
        },
        {
            "hc-key": "tw-tw",
            "value": 5
        },
        {
            "hc-key": "tw-cs",
            "value": 6
        },
        {
            "hc-key": "cn-3664",
            "value": 7
        },
        {
            "hc-key": "cn-3681",
            "value": 8
        },
        {
            "hc-key": "tw-tp",
            "value": 9
        },
        {
            "hc-key": "tw-ch",
            "value": 10
        },
        {
            "hc-key": "tw-tt",
            "value": 11
        },
        {
            "hc-key": "tw-pt",
            "value": 12
        },
        {
            "hc-key": "cn-6657",
            "value": 13
        },
        {
            "hc-key": "cn-6663",
            "value": 14
        },
        {
            "hc-key": "cn-6665",
            "value": 15
        },
        {
            "hc-key": "cn-6666",
            "value": 16
        },
        {
            "hc-key": "cn-6667",
            "value": 17
        },
        {
            "hc-key": "cn-gs",
            "value": 118
        },
        {
            "hc-key": "cn-6669",
            "value": 19
        },
        {
            "hc-key": "cn-6670",
            "value": 20
        },
        {
            "hc-key": "cn-6671",
            "value": 21
        },
        {
            "hc-key": "tw-kh",
            "value": 22
        },
        {
            "hc-key": "tw-hs",
            "value": 23
        },
        {
            "hc-key": "tw-hh",
            "value": 24
        },
        {
            "hc-key": "cn-nx",
            "value": 25
        },
        {
            "hc-key": "cn-sa",
            "value": 26
        },
        {
            "hc-key": "tw-cl",
            "value": 27
        },
        {
            "hc-key": "cn-3682",
            "value": 28
        },
        {
            "hc-key": "tw-ml",
            "value": 29
        },
        {
            "hc-key": "cn-6655",
            "value": 30
        },
        {
            "hc-key": "cn-ah",
            "value": 31
        },
        {
            "hc-key": "cn-hu",
            "value": 32
        },
        {
            "hc-key": "tw-ty",
            "value": 33
        },
        {
            "hc-key": "cn-6656",
            "value": 34
        },
        {
            "hc-key": "tw-cg",
            "value": 35
        },
        {
            "hc-key": "cn-6658",
            "value": 36
        },
        {
            "hc-key": "tw-hl",
            "value": 37
        },
        {
            "hc-key": "tw-nt",
            "value": 38
        },
        {
            "hc-key": "tw-th",
            "value": 39
        },
        {
            "hc-key": "cn-6659",
            "value": 40
        },
        {
            "hc-key": "cn-6660",
            "value": 41
        },
        {
            "hc-key": "cn-6661",
            "value": 42
        },
        {
            "hc-key": "tw-yl",
            "value": 43
        },
        {
            "hc-key": "cn-6662",
            "value": 44
        },
        {
            "hc-key": "cn-6664",
            "value": 45
        },
        {
            "hc-key": "cn-6668",
            "value": 46
        },
        {
            "hc-key": "cn-gd",
            "value": 47
        },
        {
            "hc-key": "cn-fj",
            "value": 48
        },
        {
            "hc-key": "cn-bj",
            "value": 49
        },
        {
            "hc-key": "cn-hb",
            "value": 50
        },
        {
            "hc-key": "cn-sd",
            "value": 51
        },
        {
            "hc-key": "tw-tn",
            "value": 52
        },
        {
            "hc-key": "cn-tj",
            "value": 53
        },
        {
            "hc-key": "tw-il",
            "value": 54
        },
        {
            "hc-key": "cn-js",
            "value": 55
        },
        {
            "hc-key": "cn-ha",
            "value": 56
        },
        {
            "hc-key": "cn-qh",
            "value": 57
        },
        {
            "hc-key": "cn-jl",
            "value": 58
        },
        {
            "hc-key": "cn-xz",
            "value": 59
        },
        {
            "hc-key": "cn-xj",
            "value": 60
        },
        {
            "hc-key": "cn-he",
            "value": 61
        },
        {
            "hc-key": "cn-nm",
            "value": 62
        },
        {
            "hc-key": "cn-hl",
            "value": 63
        },
        {
            "hc-key": "cn-yn",
            "value": 64
        },
        {
            "hc-key": "cn-gx",
            "value": 65
        },
        {
            "hc-key": "cn-ln",
            "value": 66
        },
        {
            "hc-key": "cn-sc",
            "value": 67
        },
        {
            "hc-key": "cn-cq",
            "value": 68
        },
        {
            "hc-key": "cn-gz",
            "value": 69
        },
        {
            "hc-key": "cn-hn",
            "value": 70
        },
        {
            "hc-key": "cn-sx",
            "value": 71
        },
        {
            "hc-key": "cn-jx",
            "value": 172
        }
    ];

    // Initiate the chart
    $('#container').highcharts('Map', {

        title : {
            text : 'xxxx放量统计'
        },

        subtitle : {
            text : '子标题'
        },

        mapNavigation: {
            enabled: true,
            buttonOptions: {
                verticalAlign: 'bottom'
            }
        },

        colorAxis: {
            min: 0
        },

        series : [{
            data : data,
            mapData: Highcharts.maps['countries/china'],
            joinBy: 'hc-key',
            name: 'xxx量分布',
            states: {
                hover: {
                    color: '#BADA55'
                }
            },
            dataLabels: {
                enabled: true,
                format: '{point.name}'
            }
        }]
    });
});
	
	
	</script>
	
	
  </head>
  
  <body>
  <a href="<%=basePath%>chinaMap">后台取数</a>
  <div id="container"></div>
  </body>
</html>
