var itemStyle = {
  normal: {
    // color:function(d){return U.randomColor();}
    color: function (params) {
      var colorList = ['#C33531', '#EF6128', '#64BD3D', '#EE9201', '#29AAE3', '#B74AE5', '#0AAF9F', '#E89589', '#16A085', '#4A235A', '#C39BD3 ', '#F9E79F', '#BA4A00', '#ECF0F1', '#616A6B', '#EAF2F8', '#4A235A', '#3498DB'];
      return colorList[params.dataIndex];
    }
  }
}

var optionPie = {
  title: {
    text: '',
    textStyle: {
      color: '#00ffea'
    }
  },
  /* 
      textStyle: {
        color: '#fff'
      }, */
  legend: {
    orient: 'vertical',
    right: 0,
    data: [],
    textStyle: {
      color: '#fff'
    }
  },
  tooltip: {
    trigger: 'item',
    formatter: "{a} <br/>{b} : {c} ({d}%)"
  },
  itemStyle: itemStyle,
  series: [{
    name: '',
    type: 'pie',
    selectedMode: 'single',
    center: ['35%', '60%'],
    data: [],
    label: {
      normal: {
        show: true,
        // position: 'inner',
        formatter: '{c}',
      }
    },
    itemStyle: {
      emphasis: {
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowColor: 'rgba(0, 0, 0, 0.5)'
      }
    }
  }]
};
var optionBar = {
  title: {
    text: '',
    textStyle: {
      color: '#00ffea'
    }
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        formatter: function (params) {
          var val = params.value;
          if (U.typeOf(val) === 'number') {
            return ~~val;
          } else {
            return val;
          }
        }
      }
    }
  },
  xAxis: [{
    type: 'category',
    data: [],
    axisPointer: {
      type: 'shadow'
    }
  }],
  yAxis: [{
    type: 'value',
    name: '次数',
    offset: -6,
    axisLabel: {
      formatter: '{value}'
    }
  }],
  itemStyle: itemStyle,
  textStyle: {
    color: '#fff'
  },
  series: []
};

/**
 * 图表绘制类
 * @constructor
 * @param {JSON Object} opts 参数
 * @return
 */
function ChartRenderer(opts) {
  this._opts = {
    element: {
      pie: '',
      bar: ''
    },
    charts: {
      pieCharts: [],
      barCharts: []
    },
    chartUrl: ''
  }

  this._init(opts);
}
/**
 * 初始化
 * @param {JSON Object} opts
 */
ChartRenderer.prototype._init = function (opts) {
  var me = this;
  $.extend(me._opts, opts, {
    deptId: U.parseQueryStr().deptId || -1
  });

  me._renderDOM();
  me._opts.charts.pieCharts = me._initEcharts(me._opts.element.pie);
  me._opts.charts.barCharts = me._initEcharts(me._opts.element.bar);
}

$.extend(ChartRenderer.prototype, {
  /**
   * 获取数据绘制图表
   * @return
   */
  draw: function () {
    var me = this;
    var url = me._opts.chartUrl;
    var deptId = me._opts.deptId;

    $.ajax({
      type: 'POST',
      url: url,
      data: {
        deptId: deptId
      },
      dataType: 'json',
      success: function (res) {
        console.log('chart', res)
        if (res.status !== 0) {
          console.log('fail to get charts data: status = ' + res.status);
          return;
        }
        var chartsData = res.data;

        document.querySelector('.J_chart-time').innerHTML = '更新于' + chartsData.modifyTime;
        me._drawPieCharts(chartsData);
        me._drawBarCharts(chartsData);
      }
    })
  },
  /**
   * 绘制饼图
   * @param {JSON Object} chartsData
   * @return
   */
  _drawPieCharts: function (chartsData) {
    var me = this;
    var pieCharts = me._opts.charts.pieCharts;

    var index = 0;
    Object.keys(chartsData).forEach(function (chartKey) {
      var chartData = chartsData[chartKey];
      // 非饼图数据
      if (U.typeOf(chartData) !== 'object' || chartKey === 'dict') {
        return;
      }
      var chartName = chartData['infoName'];
      var dict = chartData['dict'];
      Object.keys(dict).forEach(function (key) {
        var _name = dict[key];
        optionPie.legend.data.push(_name);
        optionPie.series[0].data.push({
          name: _name,
          value: chartData[key]
        });
      });
      optionPie.series[0].name = chartName;
      optionPie.title.text = chartName;

      pieCharts[index].setOption(optionPie);
      optionPie.legend.data = [];
      optionPie.series[0].data = [];

      index++;
    });
  },
  /**
   * 绘制柱状图
   * @param {JSON Object} chartsData
   * @return
   */
  _drawBarCharts: function (chartsData) {
    var me = this;
    var barCharts = me._opts.charts.barCharts;

    var index = 0;
    var dicts = chartsData['dict'];
    Object.keys(dicts).forEach(function (dictKey) {
      var _dictNames = dicts[dictKey].split(',');
      var chartData = chartsData[dictKey]
      var len = chartData.length;
      for (var i = 0; i < len; i++) {
        var date = U.getDateByCount(-i);
        optionBar.xAxis[0].data.push(date);
      }
      optionBar.title.text = _dictNames[0];
      optionBar.yAxis[0].name = _dictNames[1];
      optionBar.series.push({
        type: 'bar',
        barWidth: '40%',
        data: chartData
      })
      barCharts[index].setOption(optionBar);
      optionBar.xAxis[0].data = [];
      optionBar.series = [];
      index++;
    });
  },
  /**
   * 绘制 Chart DOM
   * @return
   */
  _renderDOM: function () {
    var me = this;
    var tpl = '<div class="{{chartClass}}-wrapper"> <div class="J_{{chartClass}} chart"></div> </div>';
    var html = [];
    var clsName = me._opts.element.pie.substr(3);
    for (var i = 0; i < 4; i++) {
      var chartNum = 2;
      if (i === 3) {
        clsName = me._opts.element.bar.substr(3);
        chartNum = 3;
      }
      var _arr = [];
      for (var j = 0; j < chartNum; j++) {
        var chart = tpl.replace(/\{\{\s*chartClass\s*\}\}/g, clsName);
        _arr.push(chart);
      }
      html.push('<section>' + _arr.join('') + '</section>');
    }

    $('#J_tab_content').html(html.join(''));
  },
  /**
   * 实例化 每个Echart 
   * @param {String} selector
   * @return {ECharts Array}
   */
  _initEcharts: function (selector) {
    var echartsArr = [];
    document.querySelectorAll(selector).forEach(function (dom) {
      echartsArr.push(echarts.init(dom));
    });

    return echartsArr;
  }
})