let Utils = {};
import iView from 'iview';
import moment from 'moment';

Utils.jumpTo = function(url) {
	if (window.__router) {
		window.__router.push(url);
	} else {
		window.location.href = url;
	}
};

Utils.redirectTo = function(url) {
	window.location.replace(url);
};

Utils.get = function(url, param, callback, errorCallback) {
	$.get(url, param ? param : {}, function(d) {
		if (d && d.code == 0) {
			callback(d.data)
		} else {
			iView.Message.error(d.msg ? d.msg : '服务器发生异常，请重试');
			errorCallback ? errorCallback(d) : '';
		}
	});
};

Utils.post = function(url, param, callback, errorCallback) {
	$.post(url, param ? param : {}, function(d) {
		if (d && d.code == 0) {
			callback(d.data)
		} else {
			iView.Message.error(d.msg ? d.msg : '服务器发生异常，请重试');
			errorCallback ? errorCallback(d) : '';
		}
	});
};

Utils.strToDateTimeMillis = function(dateStr) {
	if (dateStr)
		return moment(dateStr).format('YYYY-MM-DD HH:mm:ss.fff')
	return dateStr
};

Utils.strToDateTimeLite = function(dateStr) {
	if (dateStr)
		return moment(dateStr).format('MM-DD HH:mm')
	return dateStr
};

Utils.strToDateTime = function(dateStr) {
	if (dateStr)
		return moment(dateStr).format('YYYY-MM-DD HH:mm:ss')
	return dateStr
};

Utils.strToDate = function(dateStr) {
	if (dateStr)
		return moment(dateStr).format('YYYY-MM-DD')
	return dateStr
};

Utils.strToSlashDate = function(dateStr) {
	if (dateStr)
		return moment(dateStr).format('DD/MM/YYYY')
	return dateStr
};

export default Utils;
