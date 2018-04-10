import 'iview/dist/styles/iview.css';    // 使用 CSS
import Utils from './libs/Utils.js'; //工具类
import Vue from 'vue'
import LoginPage from './pages/Login.vue';
import IView from 'iview'
window.jQuery = window.$ = $;
window.Utils = Utils;

Vue.use(IView);

new Vue({
  components : {LoginPage},
  template : '<login-page>'
}).$mount('#app');

/*new Vue({
  components : {LoginPage},
  data : {
    eventBus : new Vue()
  }
}).$mount('#app');*/
