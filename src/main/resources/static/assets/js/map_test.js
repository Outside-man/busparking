var centerPoint = baseLocation.centerPoint;
var fencePoints = baseLocation.fence;

var busMarkers = {};
var busQueue = [];

var map = new BMap.Map('map', {
  mapType: BMAP_HYBRID_MAP
});
var BM_CENTER_POINT = new BMap.Point(centerPoint.x, centerPoint.y);
map.centerAndZoom(BM_CENTER_POINT, 19); // 中心点
map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
map.enableScrollWheelZoom(); //启用滚轮放大缩小
//添加地图类型控件
map.addControl(new BMap.MapTypeControl({
  mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP, BMAP_HYBRID_MAP]
}));

/* map.addEventListener('click', function (e) {
  console.log(e.point.lng, e.point.lat)
}); */

addMarker(BM_CENTER_POINT);
renderParkingZone(fencePoints);

init();


function init() {
  U.get('/test/getBusByDeptId', function (res) {
    if (res.status !== 0) {
      console.log('fail to get bus data');
      return;
    }
    var prevList = res.data.preBusList;
    var curList = res.data.afterBusList;

    var points = [];
    for (var i = 0; i < curList.length; i++) {
      var prevBus = prevList[i];
      var curBus = curList[i];
      points.push(new BMap.Point(prevBus.lastx, prevBus.lasty));

      /* addMarker(new BMap.Point(prevBus.lastx, prevBus.lasty), {
        icon: new BMap.Icon('/assets/images/car.png', new BMap.Size(10, 10))
      });
      addMarker(new BMap.Point(curBus.lastx, curBus.lasty), {
        icon: new BMap.Icon('/assets/images/car.png', new BMap.Size(10, 10))
      }); */

      var drv = new BMap.WalkingRoute(map, {
        renderOptions: {
          map: map,
          autoViewport: false
        },
        // 清除起点、终点图标
        onMarkersSet: function (routes) {
          map.removeOverlay(routes[0].marker);
          map.removeOverlay(routes[1].marker);
        },
        // 清除路线
        onPolylinesSet: function (routes) {
          map.removeOverlay(routes[0].getPolyline());
        },
        onSearchComplete: function (res) {
          if (drv.getStatus() !== BMAP_STATUS_SUCCESS) {
            // console.log('!BMAP_STATUS_SUCCESS');
            return;
          }
          var perPathPoints = res.getPlan(0).getRoute(0).getPath();

          perPathPoints.forEach(function (point) {
            addMarker(point);
          });

        }
      });
      drv.search(new BMap.Point(prevBus.lastx, prevBus.lasty), new BMap.Point(curBus.lastx, curBus.lasty));
    }
    var options = {
      size: BMAP_POINT_SIZE_NORMAL,
      shape: BMAP_POINT_SHAPE_CIRCLE,
      color: '#f30'
    }
    var pointCollection = new BMap.PointCollection(points, options);
    pointCollection.addEventListener('click', function (e) {
      console.log(e.point);
    });
    map.addOverlay(pointCollection);
    // setTimeout(init, 3000);
  });
}
/**
 * 绘制停车场区域
 * @param {Array} fencePoints
 */
function renderParkingZone(fencePoints) {
  var polyStyle = {
    strokeColor: "blue",
    strokeWeight: 2,
    strokeOpacity: 0.5,
    fillOpacity: 0.3
  };

  var points = [];
  for (var i = 0; i < fencePoints.length; i++) {
    var point = new BMap.Point(fencePoints[i].x, fencePoints[i].y);
    points.push(point);
  }
  // var polyline = new BMap.Polyline(points, polyStyle);
  var polygon = new BMap.Polygon(points, polyStyle);
  // map.addOverlay(polyline);
  map.addOverlay(polygon);

}

/**
 * 添加标注
 * @param {BMap.Point} point
 * @param {BMap.MarkerOptions} opts
 */
function addMarker(point, opts) {
  var marker = new BMap.Marker(point, opts);
  map.addOverlay(marker);
}