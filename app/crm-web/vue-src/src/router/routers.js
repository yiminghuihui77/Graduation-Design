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
  }
];
export default routers;
