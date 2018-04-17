const routers = [
  {
    path : '',
    keepAlive : true,
    component : resolve => require(['../pages/HelloWorld.vue'], resolve)
  },
  {
    path : '/helloWorld.html',
    keepAlive : true,
    component : resolve => require(['../pages/HelloWorld.vue'], resolve)
  },
  {
    path : '/index.html',
    keepAlive : true,
    component : resolve => require(['../components/view-page.vue'], resolve)
  },
  {
    path : '/saleChance.html',
    keepAlive : true,
    component : resolve => require(['../pages/SaleChance.vue'], resolve)
  },
  {
    path : '/customerDevelop.html',
    keepAlive : true,
    component : resolve => require(['../pages/CustomerDevelop.vue'], resolve)
  },
  {
    path : '/customerInfo.html',
    keepAlive : true,
    component : resolve => require(['../pages/CustomerInfo.vue'], resolve)
  },
  {
    path : '/customerLoss.html',
    keepAlive : true,
    component : resolve => require(['../pages/CustomerLoss.vue'], resolve)
  },
  {
    path : '/createService.html',
    keepAlive : true,
    component : resolve => require(['../pages/ServiceCreated.vue'], resolve)
  },
  {
    path : '/processService.html',
    keepAlive : true,
    component : resolve => require(['../pages/ServiceProcess.vue'], resolve)
  },
  {
    path : "/archiveService.html",
    keepAlive : true,
    component : resolve => require(['../pages/ServiceArchive.vue'], resolve)
  }
];
export default routers;
