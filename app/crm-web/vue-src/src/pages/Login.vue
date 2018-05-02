<template>
    <div class="login" id="login" style="top: 450px">
      <a href="#" class="log-close"><i class="icons close"></i></a>
      <div class="log-bg">
        <div class="log-cloud cloud1"></div>
        <div class="log-cloud cloud2"></div>
        <div class="log-cloud cloud3"></div>
        <div class="log-cloud cloud4"></div>

        <div class="log-logo">{{pageMsg}}</div>
        <div class="log-text">{{author}}</div>
      </div>
      <div class="log-email">
        <span v-if="loginError" style="font-size: medium;color: red">{{loginError}}</span>
        <span v-if="errorInfo.password" style="font-size: medium;color: red">{{errorInfo.password}}</span>
        <span v-if="errorInfo.account" style="font-size: medium;color: red">{{errorInfo.account}}</span>
        <div>
          <input type="text" placeholder="Account" :class="'log-input' + (account==''?' log-input-empty':'')" v-model="account">
        </div>
        <div>
          <input type="password" placeholder="Password" :class="'log-input' + (password==''?' log-input-empty':'')"  v-model="password">
        </div>
        <a href="#" class="log-btn" @click="login">Login</a>
      </div>
      <Loading v-if="isLoging" marginTop="-30%"></Loading>
    </div>
</template>

<script>
  import Loading from '../pages/Loading'
  import Cookies from 'js-cookie'
    export default {
      name: "login",
      //数据
      data () {
          return {
            pageMsg : 'Welcome To CRM!',
            author : '@minghui.y',
            account : '',
            password : '',
            isLoging : false,
            errorInfo : {
              account : '',
              password : ''
            },
            loginError : ''
          }
      },
      //组件
      components : {
        Loading
      },
      //方法
      methods : {
        //登录方法
        login : function () {
          //清除所有错误信息
          this.loginError = '';
          this.errorInfo.account = '';
          this.errorInfo.password = '';

          //校验输入合法性
          if (!this.checkLogin(this.account, this.password)) {
            return;
          }
          // 向后端发送请求，执行路由
          this.toLogin();
        },

        //检查用户名、密码合法性
        checkLogin : function (act, pwd) {
          if (!act) {
            this.errorInfo.account = '请输入账号';
            return false;
          }
          if (!pwd) {
            this.errorInfo.password = '请输入密码';
            return false;
          }
          return true;
        },
        toLogin : function () {
          var me = this;
          //设置状态为正在登录
          me.isLoging = true;
          //延时执行,凸显动画效果
          setTimeout(() => {
            //设置状态为非登录
            me.isLoging = false;
            //向后端发送http请求
            Utils.post('/api/auth.json', {
              account : me.account,
              password : me.password
            }, function (d) {
              if (!d.user) {
                me.loginError = d;
                return;
              }

              me.loginError = '';
              // me.account = d.user.name;
              // me.password = d.user.password;
              Cookies.set('account', d.user.name);
              Cookies.set('password', d.user.password);
              Utils.jumpTo('/');
            });
          }, 2000);
        },
        //每次跳转到登录界面时，判断Cookie中是否存在用户信息
        checkCookie () {
          if (Cookies.get('account')) {
            this.$Modal.confirm({
              title: '登入会话',
              content: '<p style="font-size: large">您已经登录过，2秒后自动跳转到主界面</p>',
              loading: true,
              onOk: () => {
                setTimeout(() => {
                  this.$Modal.remove();
                  Utils.jumpTo('/')
                }, 2000);
              }
            });
          }
        }
      },
      mounted : function () {
        this.checkCookie();
      }
    }
</script>

<style scoped>
  .bck{
    background-image: url("../images/loginBack.jpg");
    width: 100%;

  }
  .login{position: fixed;
    overflow: hidden;
    left: 50%;
    margin-left: -250px;
    top:50%; margin-top: -350px;
    width: 500px; min-height: 555px;
    z-index: 10; right: 140px;
    background: #fff;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    -ms-border-radius: 5px;
    -o-border-radius: 5px;
    border-radius: 5px; -webkit-box-shadow:  0px 3px 16px -5px #070707; box-shadow:  0px 3px 16px -5px #070707}
  .log-close{display: block; position: absolute; top:12px; right: 12px; opacity: 1;}
  .log-close:hover .icons{transform: rotate(180deg);}
  .log-close .icons{opacity: 1; transition: all .3s}
  .log-cloud{background-image: url(../images/login-cloud.png); width: 63px ;height: 40px; position: absolute; z-index: 1}
  .login .cloud1{top:21px; left: -30px; transform: scale(.6); animation: cloud1 20s linear infinite;}
  .login .cloud2{top:87px; right: 20px; animation: cloud2 19s linear infinite;}
  .login .cloud3{top:160px; left: 5px;transform: scale(.8);animation: cloud3 21s linear infinite;}
  .login .cloud4{top:150px; left: -40px;transform: scale(.4);animation: cloud4 19s linear infinite;}
  .log-bg{background: url(../images/login-bg.jpg); width: 100%; height: 312px; overflow: hidden;}
  .log-logo{height: 80px; margin: 120px auto 25px; text-align: center; color: #1fcab3; font-weight: bold; font-size: 40px;}
  .log-text{color: #57d4c3; font-size: 25px; text-align: center; margin: 0 auto;}
  .log-logo,.log-text{z-index: 2}
  .icons{background:url(../images/icons.png) no-repeat; display: inline-block;}
  .close{height:16px;width:16px;background-position:-13px 0;}
  .login-email{height:17px;width:29px;background-position:-117px 0;}

  .log-btns{padding: 15px 0; margin: 0 auto;}
  .log-btn{width:402px; display: block; text-align: left; line-height: 50px;margin:0 auto 15px; height:50px; color:#fff; font-size:13px;-webkit-border-radius: 5px; background-color: #3B5999;
    -moz-border-radius: 5px;
    -ms-border-radius: 5px;
    -o-border-radius: 5px;
    border-radius: 5px;
    position: relative;}
  .log-btn.tw{background-color: #13B4E9}
  .log-btn.email{background-color: #50E3CE}
  .log-btn:hover,.log-btn:focus{color: #fff; opacity: .8;}

  .log-email{text-align: center; margin-top: 20px;}
  .log-email .log-btn{background-color: #50E3CE;text-align: center;}
  .log-input-empty{border: 1px solid #f37474 !important;}
  .isloading{background: #d6d6d6}
  .log-btn .icons{margin-left: 30px; vertical-align: middle;}
  .log-btn .text{left: 95px; line-height: 50px; text-align: left; position: absolute;}
  .log-input{width: 370px;overflow: hidden; padding: 0 15px;font-size: 13px; border: 1px solid #EBEBEB; margin:0 auto 15px; height: 48px; line-height: 48px; -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    -ms-border-radius: 5px;
    -o-border-radius: 5px;
    border-radius: 5px;}
  .log-input.warn{border: 1px solid #f88787}

  @-webkit-keyframes cloud1 {
    0%{left: 200px}
    100%{left:-130px;}
  }
  @keyframes cloud1{
    0%{left: 200px}
    100%{left:-130px;}
  }

  @-webkit-keyframes cloud2 {
    0%{left:500px;}
    100%{left:-90px;}
  }
  @keyframes cloud2{
    0%{left:500px;}
    100%{left:-90px;}
  }

  @-webkit-keyframes cloud3 {
    0%{left:620px;}
    100%{left:-70px;}
  }
  @keyframes cloud3{
    0%{left:620px;}
    100%{left:-70px;}
  }@-webkit-keyframes cloud4 {
     0%{left:100px;}
     100%{left:-70px;}
   }
  @keyframes cloud4{
    0%{left:100px;}
    100%{left:-70px;}
  }

</style>
