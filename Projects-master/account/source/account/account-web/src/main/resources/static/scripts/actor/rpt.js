$(function() {
	bindData();
});

function bindData() {
	ajax("今年统计数据(按月统计)","/rpt/getTotalThisYearByMonth", "chart1");
	ajax("今年统计数据(按商品统计)","/rpt/getAllTotalThisYearByItem", "chart2");
	ajax("所有统计数据(按年统计)","/rpt/getTotalByYear", "chart3");
	ajax("所有统计数据(按商品统计)","/rpt/getTotalByItem", "chart4");
};

function ajax(title,url, id) {
	var chart = echarts.init(document.getElementById(id));
	chart.showLoading({
		text : '正在努力的读取数据中...', // loading
	});
	$.ajax({
		async : true,
		url : url,
		type : 'get',
		dataType : 'json',
		data : {
			name : 123
		},
		success : function(echartData) {
			var option = {
					 title: {
						    text: title,
						    textStyle: {
						      color: 'red'
						    },						   
						  },

				xAxis : [ {
					type : 'category',
					data : echartData.name
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					"name" : "销量",
					"type" : "bar",
					"data" : echartData.data,
					  itemStyle: {
		                    normal: {
		                        color: function(params) {
		                            // build a color map as your need.
		                            var colorList = [
		                            	'#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
		                              '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
		                               '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD'		                               
		                            ];
		                            return colorList[params.dataIndex]
		                        },
		　　　　　　　　　　　　　　//以下为是否显示，显示位置和显示格式的设置了
		                        label: {
		                            show: true,
		                            position: 'top',
//		                             formatter: '{c}'
		                            formatter: '{b}\n{c}'
		                        }
		                    }
		                }
				} ]
			};
			chart.setOption(option);
			chart.hideLoading(); // loading hidden
		}
	});
}
