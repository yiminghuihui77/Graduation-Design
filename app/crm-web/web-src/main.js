//Created by minghui.y on 2018.03.22
import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import iView from 'iview'
import $ from 'jquery'
import Utils from './libs/Utils'
import Routers from './router/routers'
import 'iview/dist/styles/iview.css'
import 'font-awesome/css/font-awesome.min.css'
import ViewPage from './components/view-page'

Vue.config.productionTip = false;

//引入jQuery
window.jQuery = window.$ = $;
window.Utils = Utils;

//路由
Vue.use(VueRouter);
const router = new VueRouter({
  mode : 'history',
  routes : Routers
});
window.__router = router;

//http请求
Vue.use(VueResource);
//iview组件
Vue.use(iView);


new Vue({
  el: '#app',
  router,
  components: {ViewPage},
  template : '<view-page/>'
});
