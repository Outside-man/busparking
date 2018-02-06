var busStatusMap = {
  NORMAL: 0, // 正常状态
  NEW: 1, // 新增车辆
  REMOVED: -1 // 移除车辆
}
var busDetailKeysMap = {
  busId: '车号',
  busNo: '车牌号',
  busType: '类型',
  deptId: '车场号',
  driverName: '司机',
  driverNo: '司机No',
  driverPhone: '电话',
  oilType: '燃料',
  remark: '车队',
  health: '健康值'
}
var INIT_ZOOM = 19;
var map = null;
var requestCount = 0; // for debug
var renderer = null; // for debug prod: 设为 private
/**
 * 地图初始化 Callback for Google Map
 *  
 * @return
 */
function initMap() {
  var center = new google.maps.LatLng(fencePoints.centerPoint.y, fencePoints.centerPoint.x);
  map = new google.maps.Map(document.querySelector('#map'), {
    /**  1：世界
     *   5：大陆/洲
     *   10：城市
     *   15：街道
     *   20：建筑物
     * google.maps.MapTypeId.HYBRID  19
     * google.maps.MapTypeId.SATELLITE 19 
     * google.maps.MapTypeId.TERRAIN 21
     * 
     * 17 -> 394px 
     * 18 -> 784px  1366x768
     * 19 -> 1566px 1920x1080
     */
    zoom: INIT_ZOOM,
    maxZoom: 19,
    center: center,
    styles: mapStyles,
    mapTypeId: google.maps.MapTypeId.TERRAIN,
    streetViewControl: false,
    fullscreenControl: false,
    mapTypeControl: false,
    zoomControl: false,
    scaleControl: true,
  });

  renderer = new MapRenderer(map, {
    timer: 5 * 1000,
    parking: {
      centerPoint: {
        lat: fencePoints.bgPoint.y,
        lng: fencePoints.bgPoint.x
      },
      bgFormatter: '/assets/images/bg_parking_{%d}.gif'
    },
    bus: {
      statusMap: busStatusMap,
      detailKeysMap: busDetailKeysMap,
      iconFormatter: '/assets/images/car_{%d}.png'
    },
    fencePoints: fencePoints,
    url: {
      ajaxRolling: '/getBusByDeptId',
      busDetail: '/getBusInfoById'
    }
  });

  // renderer.drawFence();
  renderer.renderParkingLotBg(INIT_ZOOM);
  renderer.ajaxRollingBusData();

  map.addListener('zoom_changed', function () {
    var zoom = map.getZoom();
    renderer._opts.dev && console.log('map zoom = ' + zoom);
    if (zoom >= 15 && zoom <= 19) {
      renderer.renderParkingLotBg(zoom);
    } else {
      renderer.renderParkingLotBg();
    }

  });
}

/**
 * 地图渲染类
 * @constructor
 * @param {Google Map} map 地图
 * @param {JSON} opts 参数
 * @return
 */
function MapRenderer(map, opts) {
  if (!map) {
    return;
  }
  this._map = map;
  this._opts = {
    parking: {
      centerPoint: null,
      bgFormatter: ''
    },
    bus: {
      statusMap: null,
      detailKeysMap: null,
      iconFormatter: ''
    },
    busMap: {},
    fencePoints: [],
    infoWins: [],
    isNew: 0,
    timer: 0,
    dev: false,
    url: {
      ajaxRolling: '',
      busDetail: ''
    }
  }

  this._init(opts);
}

/**
 * 初始化
 * @return void
 */
MapRenderer.prototype._init = function (opts) {
  var me = this;
  $.extend(me._opts, opts, {
    deptId: U.parseQueryStr().deptId || -1
  });
}

$.extend(MapRenderer.prototype, {
  /**
   * 轮询车辆数据
   * @return
   */
  ajaxRollingBusData: function () {
    var me = this;
    var url = me._opts.url.ajaxRolling;
    var deptId = me._opts.deptId;
    var statusMap = me._opts.bus.statusMap;
    var isNew = me._opts.isNew;
    var timer = me._opts.timer;

    me._postData(url, {
      deptId: deptId,
      isNew: isNew
    }, function (res) {
      me._opts.dev && console.table(res.data);
      var curBusList = res.data;
      for (var i = 0, len = curBusList.length; i < len; i++) {
        var busData = curBusList[i];
        var status = busData.curstatus;

        switch (status) {
          case statusMap.NEW:
            me.drawNewBus(busData);
            break;
          case statusMap.NORMAL:
            me.moveToNextPoint(busData);
            break;
          case statusMap.REMOVED:
            me.removeExistedBus(busData);
            break;
          default:
            console.error('error occured!!!! => busData', busData);
            break;
        }
        if (me._opts.dev && i === len - 1) {
          console.log('%cbus length = ' + Object.keys(me._opts.busMap).length, 'color: #00f');
        }
      }
    });

    isNew === 0 && (me._opts.isNew = 1);
    // if (!me._opts.dev && ++requestCount < 3)
      setTimeout(me.ajaxRollingBusData.bind(me), timer);
  },
  /**
   * 若之前不存在 在地图上画新车
   * @param {JSON} busData 每辆车的数据
   * @return
   */
  drawNewBus: function (busData) {
    if (!busData) {
      return;
    }
    var me = this;
    var map = me._map;
    var fmt = me._opts.bus.iconFormatter;
    var icon = fmt.replace('{%d}', busData.actstatus);

    var initPos = {
      lat: +busData.lasty,
      lng: +busData.lastx
    };
    var busid = busData.busid;
    var bus = new Bus(map, initPos, {
      id: busid,
      icon: icon
    });
    // 车辆点击事件
    bus.click(function (e) {
      me.busOnClickListener(e, bus);
    });
    // 缓存当前车
    me._opts.busMap[busid] = bus;
  },
  /**
   * 移动车辆到最新点
   * @param {JSON} busData 
   * @return
   */
  moveToNextPoint: function (busData) {
    if (!busData) {
      return;
    }
    var me = this;
    var busMap = me._opts.busMap;
    var fmt = me._opts.bus.iconFormatter;
    var icon = me._compileFmt(fmt, busData.actstatus);
    var curPos = {
      lat: +busData.lasty,
      lng: +busData.lastx
    };
    var bus = busMap[busData.busid];
    bus.stop();
    bus.setIcon(icon);
    bus.move(bus.getLastPos(), curPos);
  },
  /**
   * 从地图上将该车移除
   * @param {JSON} busData 
   * @return
   */
  removeExistedBus: function (busData) {
    if (!busData) {
      return;
    }
    var me = this;
    var busMap = me._opts.busMap;
    var busid = busData.busid;
    var bus = busMap[busid];
    bus.remove();
    delete busMap[busid];
    me._opts.dev && console.log('%cremoved ' + busid + ' bus.length = ' + Object.keys(busMap).length, 'color: #f00');
  },
  /**
   * 车辆点击监听
   * @param {Event} e 
   * @param {Bus} bus
   * @return
   */
  busOnClickListener: function (e, bus) {
    var me = this;
    var map = me._map;
    var url = me._opts.url.busDetail;
    var keysMap = me._opts.bus.detailKeysMap;
    var infoWins = me._opts.infoWins;
    var busId = e.busid || '0-0000';

    me._toggleBusDetailBox();

    me._postData(url, {
      busId: busId
    }, function (res) {
      me._opts.dev && console.log('busDetail', res);
      if (res.status !== 0) {
        console.log('fail to get bus detail data: status = ' + res.status);
        return;
      }
      var busData = res.data;
      U.el('.J_bus-time').innerHTML = '更新于' + busData['lastCheckTime'];

      var hmt = [];
      Object.keys(keysMap).forEach(function (key) {
        hmt.push('<tr><td>' + keysMap[key] + '</td><td>' + busData[key] + '</td></tr>')
      })
      U.el('#J_bus-detail-table').innerHTML = hmt.join('');
    });

    var infoWindow = new google.maps.InfoWindow({
      content: busId
    });
    infoWindow.open(map, bus._marker);
    if (infoWins.length) {
      infoWins.shift().close();
    }
    infoWins.push(infoWindow);
  },
  /**
   * 根据地图缩放级别改变停车场背景
   * @param {Number} zoom 地图缩放级别
   * @return
   */
  renderParkingLotBg: function (zoom) {
    var me = this;
    if (!zoom) {
      if (me._parkingBg) {
        me._parkingBg.setMap(null);
        me._parkingBg = null;
      }
      return;
    }
    var map = me._map;
    var point = me._opts.parking.centerPoint;
    var fmt = me._opts.parking.bgFormatter;
    var icon = me._compileFmt(fmt, zoom);

    if (!me._parkingBg) {
      me._parkingBg = new google.maps.Marker({
        clickable: false,
        // cursor: '',
        zIndex: -999,
        position: point,
        map: map
      });
    }
    me._parkingBg.setIcon(icon);
  },
  /**
   * 绘制停车场围栏
   * @param {JSON} fencePoints 边缘点集合
   * @return
   */
  drawFence: function () {
    var me = this;
    var fencePoints = me._opts.fencePoints;
    var coords = [];
    fencePoints.fence.forEach(function (item) {
      coords.push({
        lat: item.y,
        lng: item.x
      })
    })

    new google.maps.Polygon({
      map: me._map,
      paths: coords,
      clickable: false,
      strokeColor: '#f30',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      // fillColor: '#fff',
      fillOpacity: 0.35
    });
  },
  /**
   * 切换车辆信息显示框
   * @return
   */
  _toggleBusDetailBox: function () {
    var $infoBox = U.el('#J_bus-box');
    var clsName = 'show-info-box';
    !U.hasClass($infoBox, clsName) && U.addClass($infoBox, clsName);
  },
  /**
   * 输出匹配字符串
   * @param {String} fmt 模板
   * @param {Object} arg 变量
   */
  _compileFmt: function (fmt, arg) {
    return fmt.replace(/\{\%[a-zA-Z]\}/g, arg);
  },
  /**
   * 提取URL参数
   * @return {JSON}
   */
  _parseQueryStr: function () {
    var url = window.location.href;
    var search = url.substring(url.lastIndexOf('?') + 1);
    if (!search) {
      return {};
    }
    return JSON.parse('{"' + decodeURIComponent(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"') + '"}');
  },
  /**
   * post
   * @param {String} url
   * @param {JSON} data
   * @param {Function} successFun
   */
  _postData: function (url, data, successFun) {
    $.ajax({
      url: url,
      type: 'POST',
      data: data,
      dataType: 'json',
      success: successFun,
      error: function (err) {
        console.log(err);
      }
    })
  }
})