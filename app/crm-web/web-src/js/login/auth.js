//登录执行方法，想后端发送http请求
const AUTH_URL = '/api/auth.json';
export default {
  //登录方法
  login(context, account, password) {
    return context.$http.post(
      AUTH_URL,
      {
        'account' : account,
        'password' : password
      }
    );
  }
}
