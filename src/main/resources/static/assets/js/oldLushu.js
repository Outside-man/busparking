/*! GG
  
无法添加点击事件 太TM迷
不过效果不错

var bus = new Bus(map, path, {
    icon: {
      path: google.maps.SymbolPath.CIRCLE,
      scale: 5,
      fillColor: '#e12a31',
      strokeColor: '#e12a31'
    }
  });
*/
function Bus(map, path, opts) {
  if (!path || !path.length) {
    return;
  }

  this._map = map;
  this._path = path;
  this._opts = {
    icon: null,
    speed: 50
  }
  this._setOptions(opts);
  this._init();
  this.start();
}

/**
 * 修改默认参数_opts
 * @param {Json Object} opts 自定义参数.
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
Bus.prototype._init = function () {
  var self = this;
  var icon = self._opts.icon || {
    path: google.maps.SymbolPath.CIRCLE,
    scale: 5,
    fillColor: '#e12a31',
    strokeColor: '#e12a31'
  };
  this._route = new google.maps.Polyline({
    path: self._path,
    icons: [{
      icon: icon,
      offset: '100%'
    }],
    strokeColor: '#09c',
    strokeWeight: 1,
    map: self._map
  });
  this.setOffset(0);

}
Bus.prototype.start = function () {
  var self = this;
  this._move();
}
Bus.prototype.stop = function () {
  clearInterval(this._intervalFlag);
}
Bus.prototype._move = function () {
  var self = this,
    MAX_PERCENT = 100,
    timer = self._opts.speed,
    step = 0,
    perStep = 5;

  this._intervalFlag = setInterval(function () {
    step += perStep;
    var offset = Math.min(step, MAX_PERCENT);
    self.setOffset(offset);
    self.getOffset() >= MAX_PERCENT && self.stop();
    // console.log(self.getOffset());
  }, timer)
}

Bus.prototype.getOffset = function () {
  var icon = this._route.get('icons')[0];
  return +icon.offset.split('%')[0];
}
Bus.prototype.setOffset = function (percent) {
  var offset = percent || 0;
  var icons = this._route.get('icons');
  icons[0].offset = offset + '%';
  this._route.set('icons', icons);
}