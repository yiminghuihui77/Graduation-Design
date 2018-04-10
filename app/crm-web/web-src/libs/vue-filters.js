import moment from 'moment';
import Vue from 'vue'

Vue.filter('formatToDateTimeLite', function(value) {
	if (value) {
		return moment(String(value)).format('MM-DD HH:mm:ss')
	}
});

Vue.filter('formatToDateTime', function(value) {
	if (value) {
		return moment(String(value)).format('YYYY-MM-DD HH:mm:ss')
	}
});

Vue.filter('formatToDate', function(value) {
	if (value) {
		return moment(String(value)).format('YYYY-MM-DD')
	}
});

Vue.filter('formatToSlashDate', function(value) {
	if (value) {
		return moment(String(value)).format('DD/MM/YYYY')
	}
});
