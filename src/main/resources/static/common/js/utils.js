/**
 * @description [通用工具类]
 * 
 * @author [LiKai Lee]
 */
;
(function (window, undefined) {
  var log = function () {
    var args = Array.prototype.slice.call(arguments);
    console.log.apply(console, args);
  }

  var ajax = function (opt) {
    if (!opt) {
      console.log('request params is required!');
      return false;
    }
    var method = opt.method ? opt.method.toUpperCase() : 'GET';
    var url = opt.url || '';
    var async = opt.async || true;
    var header = opt.header || {};
    var data = opt.data || null;
    var responseType = opt.responseType || 'text';
    var success = opt.success || function () {};
    var error = opt.error || function () {};
    var xhr = null;
    if (XMLHttpRequest) {
      xhr = new XMLHttpRequest();
    } else {
      xhr = new ActiveXObject('Microsoft.XMLHTTP');
    }
    var params = [];
    for (var key in data) {
      params.push(encodeURIComponent(key) + '=' + encodeURIComponent(data[key]));
    }
    var postData = params.join('&');
    var method = method.toUpperCase();
    switch (method) {
      case 'POST':
        xhr.open(method, url, async);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=utf-8');
        var header = header;
        if (header) {
          for (let key in header) {
            xhr.setRequestHeader(key, header[key]);
          }
        }
        xhr.send(postData);
        break;
      case 'GET':
        xhr.open(method, url + '?' + postData, async);
        xhr.send(null);
        break;
      default:
        break;
    }
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        var status = xhr.status;
        if ((status >= 200 && status < 300) || status === 304) {
          var response = xhr.responseText;
          if (responseType === 'json') {
            success(JSON.parse(response));
          } else {
            success(response);
          }
          
        } else {
          console.log('Request was unsuccessful, error: ' + status);
          error(status);
        }
      }
    }
  }

  var get = function(url, successCbk, errorCbk) {
    ajax({
      url: url,
      responseType: 'json',
      success: successCbk,
      error: errorCbk
    });
  }
  /**
   * [hyphenToCamel 下划线转驼峰]
   * @example user_name --> userName
   * 
   * @param  {String} str [description]
   * @return {String}     [description]
   */
  var hyphenToCamel = function (str) {
    return str.replace(/_(\w)/g, function (args) {
      return args[1].toUpperCase();
    })
  }
  /**
   * [camelToHyphen 驼峰转下划线]
   * @example userName --> user_name
   * 
   * @param  {String} str [description]
   * @return {String}     [description]
   */
  var camelToHyphen = function (str) {
    return str.replace(/([A-Z])/g, '_$1').toLowerCase();
  }
  /**
   * [isEmptyObject 判断对象是否为空]
   * @param  {Object}  obj [对象字面量]
   * @return {Boolean}     [description]
   */
  var isEmptyObject = function (obj) {
    if (!obj || typeof obj !== 'object' || Array.isArray(obj)) {
      return false;
    }
    return !Object.keys(obj).length;
  }

  /*=============== CSS Class ======================*/
  function hasClass(ele, cls) {
    return (new RegExp('(\\s|^)' + cls + '(\\s|$)')).test(ele.className);
  }

  function addClass(ele, cls) {
    if (!hasClass(ele, cls)) {
      ele.className += ' ' + cls;
    }
  }

  function removeClass(ele, cls) {
    if (hasClass(ele, cls)) {
      var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
      ele.className = ele.className.replace(reg, ' ');
    }
  }
  /*=============== CSS Class End ======================*/
  function getCookie(name) {
    var arr = document.cookie.replace(/\s/g, '').split(';');
    for (var i = 0; i < arr.length; i++) {
      var tempArr = arr[i].split('=');
      if (tempArr[0] == name) {
        return decodeURIComponent(tempArr[1]);
      }
    }
  }

  function setCookie(name, value, days) {
    var date = new Date();
    date.setDate(date.getDate() + days);
    document.cookie = name + '=' + value + ';expires=' + date;
  }

  function removeCookie(name) {
    // 设置已过期，系统会立刻删除cookie
    setCookie(name, '1', -1);
  }

  /*=============== Cookie End ======================*/
  function getExplore() {
    var sys = {},
      ua = navigator.userAgent.toLowerCase(),
      s;
    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? sys.ie = s[1]:
      (s = ua.match(/msie ([\d\.]+)/)) ? sys.ie = s[1] :
      (s = ua.match(/edge\/([\d\.]+)/)) ? sys.edge = s[1] :
      (s = ua.match(/firefox\/([\d\.]+)/)) ? sys.firefox = s[1] :
      (s = ua.match(/(?:opera|opr).([\d\.]+)/)) ? sys.opera = s[1] :
      (s = ua.match(/chrome\/([\d\.]+)/)) ? sys.chrome = s[1] :
      (s = ua.match(/version\/([\d\.]+).*safari/)) ? sys.safari = s[1] : 0;
    if (sys.ie) return ('IE: ' + sys.ie);
    if (sys.edge) return ('EDGE: ' + sys.edge);
    if (sys.firefox) return ('Firefox: ' + sys.firefox);
    if (sys.chrome) return ('Chrome: ' + sys.chrome);
    if (sys.opera) return ('Opera: ' + sys.opera);
    if (sys.safari) return ('Safari: ' + sys.safari);
    return 'Unkonwn';
  }

  function getOS() {
    var sUserAgent = navigator.userAgent;
    var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
    var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
    if (isMac) return "Mac";
    var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
    if (isUnix) return "Unix";
    var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
    if (isLinux) return "Linux";
    if (isWin) {
      var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
      if (isWin2K) return "Windows 2000";
      var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
      if (isWinXP) return "Windows XP";
      var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
      if (isWin2003) return "Windows 2003";
      var isWinVista = sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
      if (isWinVista) return "Windows Vista";
      var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
      if (isWin7) return "Windows 7";
      var isWin8 = sUserAgent.indexOf("Windows NT 6.2") > -1 || sUserAgent.indexOf("Windows 8") > -1;
      if (isWin8) return "Windows 8";
      var isWin8 = sUserAgent.indexOf("Windows NT 6.3") > -1 || sUserAgent.indexOf("Windows 8.1") > -1;
      if (isWin8) return "Windows 8.1";
      var isWin10 = sUserAgent.indexOf("Windows NT 10") > -1 || sUserAgent.indexOf("Windows 10") > -1;
      if (isWin10) return "Windows 10";
    }
    return "other";
  }
  /**
   * [parseQueryStr 提取url参数转对象]
   * @example http://localhost:1337?a=1&b=2&c=3
   *          --> {a: 1, b: 2, c: 3}
   *
   * @param  {String} url [description]
   * @return {Object}     [description]
   */
  function parseQueryStr(url) {
    url = url == null ? window.location.href : url;
    var search = url.substring(url.lastIndexOf('?') + 1);
    if (!search) {
      return {};
    }
    return JSON.parse('{"' + decodeURIComponent(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"') + '"}');
  }

  function randomColor() {
    /*var r = Math.floor(Math.random()*256);
    var g = Math.floor(Math.random()*256);
    var b = Math.floor(Math.random()*256);
    return "rgb("+r+','+g+','+b+")";*/
    return '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).slice(-6);
  }
  /**
   * [randomNum 生成指定范围内的随机数]
   * @param  {Number} min [description]
   * @param  {Number} max [description]
   * @return {Number}     [description]
   */
  function randomNum(min, max) {
    return Math.floor(min + Math.random() * (max - min));
  }

  function isEmail(str) {
    return /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(str);
  }

  function isPhoneNum(str) {
    return /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(str);
  }

  function isUrl(str) {
    return /[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/i.test(str);
  }

  /**
   * [getPassedTime 获取距现在的时间]
   * @param  {Date} startTime [时间戳]
   * @return {String}           [description]
   */
  function getPassedTime(startTime) {
    var currentTime = Date.parse(new Date()),
      time = currentTime - startTime,
      day = parseInt(time / (1000 * 60 * 60 * 24)),
      hour = parseInt(time / (1000 * 60 * 60)),
      min = parseInt(time / (1000 * 60)),
      month = parseInt(day / 30),
      year = parseInt(month / 12);
    if (year) return year + "年前";
    if (month) return month + "个月前";
    if (day) return day + "天前";
    if (hour) return hour + "小时前";
    if (min) return min + "分钟前";
    else return '刚刚';
  }
  /**
   * [getRemainingTime 获取截止时间]
   * @param  {Date} endTime [时间戳]
   * @return {String}         [description]
   */
  function getRemainingTime(endTime) {
    var startDate = new Date(); //开始时间
    var endDate = new Date(endTime); //结束时间
    var t = endDate.getTime() - startDate.getTime(); //时间差
    var d = 0,
      h = 0,
      m = 0,
      s = 0;
    if (t >= 0) {
      d = Math.floor(t / 1000 / 3600 / 24);
      h = Math.floor(t / 1000 / 60 / 60 % 24);
      m = Math.floor(t / 1000 / 60 % 60);
      s = Math.floor(t / 1000 % 60);
    }
    return d + "天 " + h + "小时 " + m + "分钟 " + s + "秒";
  }
  /**
   * [parseArrayLike 将类数组转为数组]
   * @param  {Array} arrLike [description]
   * @return {Array}         [description]
   */
  function parseArrayLike(arrLike) {
    try {
      return Array.prototype.slice.call(arrLike);
    } catch (e) {
      var arr = [];
      for (var i = 0, len = arrLike.length; i < len; i++) {
        arr[i] = arrLike[i]; //据说这样比push快
      }
      return arr;
    }
  }

  function setAttr(el, name, value) {
    return el.setAttribute(name, value);
  }

  function getAttr(el, name) {
    return el.getAttribute(name);
  }

  function el(selector, context) {
    var ctx = document;
    var els = ctx.querySelectorAll(selector);
    return els.length === 1 ? els[0] : els;
  }
  /**
   * [typeOf 判断数据类型]
   * @param  {Object} obj [description]
   * @return {String}     [description]
   */
  function typeOf(obj) {
    var types = ['Boolean', 'Number', 'String', 'Function', 'Array', 'Date', 'RegExp', 'Object'];
    var typeClass = {};
    for (var i = 0; i < types.length; i++) {
      var type = types[i];
      typeClass['[object ' + type + ']'] = type.toLowerCase();
    }
    return (obj === null || obj === undefined) ?
      String(obj) :
      typeClass[Object.prototype.toString.call(obj)] || 'object';
  }

  function onload(ele, cb) {
    ele.onload = cb;
  }
  /**
   * [loadJS 动态加载JS]
   * @description 插入body后
   * @param  {Array} jsList [description]
   * @return {[type]}        [description]
   */
  function loadJS(jsList) {
    if (jsList.length <= 0) {
      return;
    }
    var js = jsList.shift();
    var script = document.createElement('script');
    script.src = js;// + '?t=' + randomNum(0, 1000) + Date.now();
    onload(script, function () {
      loadJS(jsList);
    })
    var existed = parseArrayLike(document.querySelectorAll('script'));
    var last = existed.pop();
    document.body.insertBefore(script, last);
  }
  /**
   * [shuffle 数组乱序]
   * @param  {Array} arr [description]
   * @return {Array}     [description]
   */
  function shuffle(arr) {
    if (typeOf(arr) !== 'array') {
      return;
    }
    var length = arr.length;
    var shuffled = new Array(length);

    for (var index = 0, rand; index < length; index++) {
      rand = ~~(Math.random() * (index + 1));
      if (rand !== index) {
        shuffled[index] = shuffled[rand];
      }
      shuffled[rand] = arr[index];
    }
    return shuffled;
  }
  /**
   * [unique 数组去重]
   * @param  {Array} arr [description]
   * @return {Array}     [description]
   */
  function unique(arr) {
    if (typeOf(arr) !== 'array') {
      return;
    }
    var ret = [];
    for (var i = 0, len = arr.length; i < len; i++) {
      for (var j = i + 1; j < len; j++) {
        if (arr[i] === arr[j]) {
          j = ++i;
        }
      }
      ret.push(arr[i]);
    }
    return ret;
  }
  var utils = {
    el: el, // 获取元素
    log: log, // 
    ajax: ajax, // 
    get: get,
    hyphenToCamel: hyphenToCamel, // 下划线转驼峰
    camelToHyphen: camelToHyphen, // 驼峰转下划线
    isEmptyObject: isEmptyObject, // 是否为空对象
    hasClass: hasClass, // 
    addClass: addClass, // 
    removeClass: removeClass, // 
    getCookie: getCookie, // 
    setCookie: setCookie, // 
    removeCookie: removeCookie, // 
    getExplore: getExplore, // 获取浏览器
    getOS: getOS, // 获取操作系统
    parseQueryStr: parseQueryStr, // 将参数转为对象
    randomColor: randomColor, // 随机颜色
    randomNum: randomNum, // 指定范围内随机数
    isEmail: isEmail, // 
    isUrl: isUrl, // 
    isPhoneNum: isPhoneNum, // 
    getPassedTime: getPassedTime, // 获取已经过的时间
    getRemainingTime: getRemainingTime, // 获取剩余时间
    parseArrayLike: parseArrayLike, // 类数组转换为数组
    setAttr: setAttr, // 
    getAttr: getAttr, // 
    typeOf: typeOf, // 获取数据类型
    onload: onload, // 元素加载完成事件
    loadJS: loadJS, // 加载js
    shuffle: shuffle, // 数组乱序
    unique: unique // 数组去重
  }
  window.U = utils;
})(window);