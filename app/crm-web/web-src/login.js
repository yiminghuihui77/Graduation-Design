import 'iview/dist/styles/iview.css';    // 使用 CSS
import Utils from './libs/Utils.js'; //工具类

import LoginPage from './pages/Login.vue';

window.jQuery = window.$ = $;
window.Utils = Utils;

Vue.use(iView);

new Vue({
  components : {LoginPage},
  template : '<login-page>'
}).$mount('#app');
