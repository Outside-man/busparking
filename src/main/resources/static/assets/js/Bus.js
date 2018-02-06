/**
 * @constructor
 * @param {Google Map} map 
 * @param {Google LatLng} position 初始位置
 * @param {JSON Object} opts 
 */
function Bus(map, position, opts) {
  if (!position) {
    return;
  }

  this._map = map;
  this._initPos = position;
  this._lastPos = position;
  this._opts = {
    id: undefined,
    showRoute: false,
    label: null,
    icon: null,
    speed: 50
  }
  this._setOptions(opts);
  this._init();
}

/**
 * 设置图标
 * @param {Google Map Icon} icon
 */
Bus.prototype.setIcon = function (icon) {
  var self = this;
  self._marker.setIcon(icon);
}

/**
 * 点击事件派发
 * @param {Function} clickHandler
 * @return
 */
Bus.prototype.click = function (clickHandler) {
  var self = this;
  // google.maps.event.addListener(self._marker, "click", function (e) {}
  self._marker.addListener('click', function (e) {
    clickHandler({
      busid: self._opts.id,
      event: e
    });
  })
}
/**
 * 获取车辆的最新位置
 * @return
 */
Bus.prototype.getLastPos = function () {
  var self = this;
  return self._lastPos;
}
/**
 * 将车辆从地图上移除
 * @return
 */
Bus.prototype.remove = function () {
  this.stop();
  this._marker.setMap(null);
}

/**
 * 在两点之间移动
 * @description
 * @param {Google LatLng} fromPos 起点
 * @param {Google LatLng} toPos 终点
 * @return void
 * 
 */
Bus.prototype.move = function (fromPos, toPos) {
  var self = this,
    curIndex = 0,
    timer = this._opts.speed,
    totalCount = 3;

  self._lastPos = toPos;
  if (fromPos.lat === toPos.lat && fromPos.lng === toPos.lng) {
    return;
  }
  if (self._opts.showRoute) {
    self._route = new google.maps.Polyline({
      map: self._map,
      path: [fromPos, toPos],
      strokeColor: '#09c',
      strokeOpacity: 1.0,
      strokeWeight: 2
    });
  }

  var points = self._getPoints(fromPos, toPos, totalCount);
  /* for (var i = 0, len = points.length; i < len; i++) {
    new google.maps.Marker({
      map: self._map,
      position: points[i],
      icon: self._opts.icon
    });
  } */
  // console.log(points);

  self._intervalFlag = setInterval(function () {
    if (curIndex >= totalCount) {
      self.stop();
    } else {
      self._marker.setPosition(points[curIndex]);
    }
    curIndex++;

  }, timer);
}

/**
 * 停止运动
 * @return
 */
Bus.prototype.stop = function () {
  var self = this;
  clearInterval(self._intervalFlag);
  if (self._opts.showRoute) {
    self._route.setMap(null);
    self._route = null;
  }
}

/**
 * 初始化 
 * @return void
 */
Bus.prototype._init = function () {
  var self = this;
  self._marker = new google.maps.Marker({
    map: self._map,
    position: self._initPos,
    icon: this._opts.icon,
    label: self._opts.label
  });

}

/**
 * 修改默认参数 _opts
 * @param {JSON Object} opts 自定义参数.
 * @return void.
 */
Bus.prototype._setOptions = function (opts) {
  if (!opts) {
    return;
  }
  for (var opt in opts) {
    if (opts.hasOwnProperty(opt)) {
      this._opts[opt] = opts[opt];
    }
  }
}

/**
 * 获取两点之间距离
 * @param {Google LatLng} ptA
 * @param {Google LatLng} ptB
 * @return {Number} 距离
 */
Bus.prototype._getDistance = function (ptA, ptB) {
  return Math.sqrt(Math.pow(ptA.lat - ptB.lat, 2) + Math.pow(ptA.lng - ptB.lng, 2));
}

/**
 * 获取从起点到终点的N个点
 * @param {Google LatLng} from 起点
 * @param {Google LatLng} to 终点
 * @param {Number} num 个数
 * @return {Google LatLng Array} 点集合
 */
Bus.prototype._getPoints = function (from, to, num) {
  var points = [];
  var perLat = (to.lat - from.lat) / num;
  var perLng = (to.lng - from.lng) / num;
  for (var i = 1; i <= num; i++) {
    points.push({
      lat: from.lat + perLat * i,
      lng: from.lng + perLng * i
    });
  }
  return points;
}