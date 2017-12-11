var centerPoint = baseLocation.centerPoint;
var fencePoints = baseLocation.fence;
var STATUS_NORMAL = 0;
var STATUS_NEW = 1;
var STATUS_REMOVED = -1;

var busMarkerMap = {};
var lushuMap = {};
var requestCount = 0;
var isNew = 0;
var busIcon = '/assets/images/car_orange.png';

var map = new BMap.Map('map', {
    // mapType: BMAP_HYBRID_MAP
});
var BM_CENTER_POINT = new BMap.Point(centerPoint.x, centerPoint.y);
map.centerAndZoom(BM_CENTER_POINT, 19); // 中心点
// map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
// map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
map.enableScrollWheelZoom(); //启用滚轮放大缩小
//添加地图类型控件
// map.addControl(new BMap.MapTypeControl({
//     mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP, BMAP_HYBRID_MAP]
// }));
// 地图自定义样式
map.setMapStyle(mapStyle);

map.addEventListener('click', function (e) {
    if (e.overlay && !U.isEmptyObject(busMarkerMap)) {
        var busMarkerKey = e.overlay.ba;
        var bus = busMarkerMap[busMarkerKey];
        if (!bus) {
            // console.log('no');
            return;
        }
        // console.log(busMarker);
        var point = e.point;

        var infoWindow = new BMap.InfoWindow(bus.busid);
        map.openInfoWindow(infoWindow, point);
    }
});

addMarker(BM_CENTER_POINT);
renderParkingZone(fencePoints);


var deptId = location.href.split('?')[1].split('=')[1].trim();
init();

function init() {
    if (requestCount === 0) {
        busIcon = '/assets/images/car.png';
    } else {
        busIcon = '/assets/images/car_orange.png';
    }
    U.ajax({
        url: '/getBusByDeptId',
        method: 'post',
        data: {
            deptId: deptId,
            isNew: isNew
        },
        responseType: 'json',
        success: function (res) {
            console.log(res);
            if (res.status !== 0) {
                console.log('fail to get bus data: status = ' + res.status);
                return;
            }

            var curBusList = res.data;
            // console.log('cur', curBusList)
            for (var i = 0; i < curBusList.length; i++) {
                var curBus = curBusList[i];
                var status = curBus.curstatus;
                var busid = curBus.busid;

                switch (status) {
                    case STATUS_NORMAL:
                        var existedBus = lushuMap[busid];
                        var prevBusPoint = existedBus.point;
                        var curBusPoint = new BMap.Point(curBus.lastx, curBus.lasty);
                        existedBus.lushu.moveTo(prevBusPoint, curBusPoint);
                        break;
                    case STATUS_NEW:
                        var curBusPoint = new BMap.Point(curBus.lastx, curBus.lasty);
                        renderLushu({
                            // bus: curBusList[i],
                            busid: busid,
                            defaultContent: '',
                            iconUrl: busIcon,
                            point: curBusPoint
                        });
                        console.log('%c[add]bus length = ' + Object.keys(lushuMap).length, 'color: #00f');
                        break;
                    case STATUS_REMOVED:
                        var busMarkerKey = lushuMap[busid].busMarkerKey;
                        map.removeOverlay(lushuMap[busid].lushu._marker);
                        // console.log('remove ' + busid);
                        delete lushuMap[busid];
                        delete busMarkerMap[busMarkerKey];
                        console.log('%c[removed]bus length = ' + Object.keys(lushuMap).length, 'color: #f00');
                        break;
                    default:
                        break;
                }
            }
        }
    })
    // if (requestCount < 2) {
        setTimeout(init, 10000);
    // }
    requestCount++;
    if(requestCount >= 1) {
      isNew = 1;
    }
}

/**
 *
 * 绘制路书
 * @param {JSON Object} opts
 * @param {String} opts.defaultContent 默认显示内容
 * @param {BMap.Point} opts.from 起点
 * @param {BMap.Point} opts.to  终点
 */
function renderLushu(opts) {
    // console.log('renderLushu');
    var busid = opts.busid;
    var defaultContent = opts.defaultContent || '';
    var curPoint = opts.point;
    var iconUrl = opts.iconUrl || '/assets/images/car.png';
    var lushu;
    lushu = new BMapLib.LuShu(map, [curPoint, curPoint], {
        defaultContent: defaultContent,
        autoView: false, //是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
        icon: new BMap.Icon(iconUrl, new BMap.Size(25, 13)),
        speed: 50,
        enableRotation: true, //是否设置marker随着道路的走向进行旋转
        landmarkPois: []
    });
    lushu.start();

    var busMarkerKey = lushu._marker.ba;
    busMarkerMap[busMarkerKey] = {
        busid: busid,
    };
    // var busid = bus.busid;
    lushuMap[busid] = {
        busMarkerKey: busMarkerKey,
        lushu: lushu,
        point: curPoint
    };
    /* var drv = new BMap.DrivingRoute(map, {
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
        }
    });
    drv.search(curPoint, curPoint);
    drv.setSearchCompleteCallback(function (res) {
        if (drv.getStatus() !== BMAP_STATUS_SUCCESS) {
            console.log('!BMAP_STATUS_SUCCESS');
            return;
        }
        var plan = res.getPlan(0);
        var arrPois = [];
        for (var j = 0; j < plan.getNumRoutes(); j++) {
            arrPois = arrPois.concat(plan.getRoute(j).getPath());
        }
        console.log(arrPois);


    }) */

}


function renderArea() {
    var polyStyle = {
        strokeColor: '#333',
        strokeWeight: 2,
        strokeOpacity: 0.8,
        fillOpacity: 0.3
    };
    var points = [];
    // for(var i = 0; i < .length; i++) {}
    var polyline = new BMap.Polyline();
    map.addOverlay(polyline);
}

/**
 * 绘制停车场区域
 * @param {Array} fencePoints
 */
function renderParkingZone(fencePoints) {
    var polyStyle = {
        strokeColor: '#fff',
        strokeWeight: 2,
        strokeOpacity: 0.8,
        fillOpacity: 0.3
    };

    var points = [];
    for (var i = 0; i < fencePoints.length; i++) {
        var point = new BMap.Point(fencePoints[i].x, fencePoints[i].y);
        points.push(point);
    }
    points.push(new BMap.Point(fencePoints[0].x, fencePoints[0].y));
    var polyline = new BMap.Polyline(points, polyStyle);
    // var polygon = new BMap.Polygon(points, polyStyle);
    map.addOverlay(polyline);
    // map.addOverlay(polygon);

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

/**
 * 坐标转换
 * @description 每次限制10个坐标点
 *  默认 wgs84 -> 百度经纬度坐标
 * @param {Object} opts
 */
function pointsConvertor(opts) {
    var originPoints = opts.points;
    var successCbk = opts.success || function () {};
    var errorCbk = opts.error || function () {};
    var from = opts.from || 1;
    var to = opts.to || 5;

    var convertor = new BMap.Convertor();
    convertor.translate(originPoints.slice(0, 10), from, to, function (res) {
        if (res.status === 0) {
            var convertedPoints = res.points;
            successCbk(convertedPoints);
        } else {
            console.log('坐标转换错误！')
            errorCbk(res);
        }
    })
}