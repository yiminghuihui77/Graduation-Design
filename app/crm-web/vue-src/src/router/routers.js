const routers = [
  {
    path : '',
    keepAlive : true,
    name : 'index',
    component : resolve => require(['../components/view-page.vue'], resolve),
    children : [
      {
        path : '/',
        keepAlive : true,
        component : resolve => require(['../pages/HelloWorld.vue'], resolve)
      },
      {
        path : 'saleChance.html',
        keepAlive : true,
        component : resolve => require(['../pages/SaleChance.vue'], resolve)
      },
      {
        path : 'customerDevelop.html',
        keepAlive : true,
        component : resolve => require(['../pages/CustomerDevelop.vue'], resolve)
      },
      {
        path : 'customerInfo.html',
        keepAlive : true,
        component : resolve => require(['../pages/CustomerInfo.vue'], resolve)
      },
      {
        path : 'customerLoss.html',
        keepAlive : true,
        component : resolve => require(['../pages/CustomerLoss.vue'], resolve)
      },
      {
        path : 'createService.html',
        keepAlive : true,
        component : resolve => require(['../pages/ServiceCreated.vue'], resolve)
      },
      {
        path : 'processService.html',
        keepAlive : true,
        component : resolve => require(['../pages/ServiceProcess.vue'], resolve)
      },
      {
        path : "archiveService.html",
        keepAlive : true,
        component : resolve => require(['../pages/ServiceArchive.vue'], resolve)
      },
      {
        path : 'contributionAnalyze.html',
        keepAlive : true,
        component : resolve => require(['../pages/ContributionAnalyze.vue'], resolve)
      },
      {
        path : 'serviceAnalyze.html',
        keepAlive : true,
        component : resolve => require(['../pages/ServiceAnalyze.vue'], resolve)
      }
    ]
  },
  {
    path : '/login.html',
    keepAlive : true,
    component : resolve => require(['../pages/Login.vue'], resolve)
  }

];
export default routers;
