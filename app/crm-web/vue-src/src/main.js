// Created by minghui.y on 2018.04.01
import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import IView from 'iview'
import 'iview/dist/styles/iview.css'
import 'font-awesome/css/font-awesome.min.css'
import $ from 'jquery'
import Utils from './src/libs/Utils'
import ViewPage from './src/components/view-page'
import Routers from './src/router/routers'
Vue.config.productionTip = false;

//路由
Vue.use(VueRouter);
const router = new VueRouter({
  mode : 'history',
  routes : Routers
});
window.__router = router;

//http请求
Vue.use(VueResource);

//IView组件
Vue.use(IView);

//引入jQuery
window.jQuery = window.$ = $;
window.Utils = Utils;

new Vue({
  el: '#app',
  router,
  components: { ViewPage },
  template: '<view-page/>'
});
