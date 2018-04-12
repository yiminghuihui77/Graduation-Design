<template>
  <div class="wrapper-content ivu-article">
    <article>
      <h2>销售机会管理</h2>

      <div class="ivu-row example-vertical" style="margin-bottom: 20px;">
        <div class="ivu-col ivu-col-span-24 example-case">
          <Card>
            <Form :label-width="80" inline>
              <FormItem label="客户姓名">
                    <Input placeholder="客户姓名(CUST_NAME)" v-model="searchParam.custName" style="width: 300px"/>
              </FormItem>

              <FormItem label="产品名称">
                    <Input placeholder="产品名称(PROD_NAME)" v-model="searchParam.prodName" style="width: 300px"/>
              </FormItem>

              <FormItem style="margin-bottom:-8px;" align="center">
                  <Button type="primary" @click="refresh" icon="ios-loop">刷新</Button>
                  <Button type="primary" @click="insertShow" icon="ios-plus-outline">新增</Button>
                  <Button type="primary" @click="search" icon="ios-search">查询</Button>
              </FormItem>
            </Form>
          </Card>
        </div>
      </div>
      <!--表格显示数据-->
      <div class="ivu-row example-vertical">
        <div class="ivu-col ivu-col-span-24">
          <div class="example-case">
            <Button type="primary" @click="exportData(1)"><Icon type="ios-download-outline"></Icon>到处原始数据</Button>
            <Button type="primary" @click="exportData(2)"><Icon type="ios-download-outline"></Icon>导出过滤或排序后的数据</Button>
            <Table :loading="loading" border :columns="columns" :data="saleChanceArr" ref="table" ></Table>
          </div>
          <!--分页-->
          <div class="example-case right top10" align="center">
            <Page :total="total"
                  show-total
                  show-sizer
                  show-elevator
                  @on-change=""
                  @on-page-size-change="">
            </Page>
          </div>
        </div>
      </div>

      <!--对话框-->
      <!--删除机会-->
      <Modal v-model="showDeleteModal" width="360">
        <p slot="header" style="color:#f60;text-align:center">
          <Icon type="information-circled"></Icon>
          <span>删除销售机会</span>
        </p>
        <div style="text-align:center">
          <p>删除操作不可逆</p>
          <p>您确定要删除该销售机会?</p>
        </div>
        <div slot="footer">
          <Button type="error" size="large" long :loading="modal_loading" @click="remove">Delete</Button>
        </div>
      </Modal>

      <!--新增/更新机会-->
      <Modal width="700" v-model="showInsertModal">

        <div slot="header" style="color:#1197EC;text-align:center;font-size: large">
          <Icon type="plus-round"></Icon>
          <span>{{modalTitle}}</span>
        </div>
        <Form :model="insertForm" :label-width="80">
          <FormItem label="客户姓名">
            <Select v-model="insertForm.custId" style="width: 300px">
              <Option v-for="item in custList" :value="item.id" :key="item.id">{{item.cusName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="产品名称">
            <Select v-model="insertForm.prodId" style="width: 300px">
              <Option v-for="item in prodList" :value="item.id" :key="item.id">{{item.prodName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="购买数量">
            <Slider v-model="insertForm.amount" :step="1" show-input style="width: auto;"></Slider>
          </FormItem>
          <FormItem label="机会描述">
            <Input v-model="insertForm.chanceDesc" type="textarea" :rows="6" placeholder="请输入机会描述.."></Input>
          </FormItem>
        </Form>
        <div slot="footer">
          <Button type="primary" size="large" @click="insertChance">保存</Button>
          <Button type="primary" size="large" @click="showInsertModal = false">关闭</Button>
        </div>
      </Modal>

    </article>
  </div>
</template>

<script>
    export default {
      name: "sale-chance",
      data() {
        return {
          prodList :[],
          custList : [],
          modalTitle : '',
          insertForm : {
            custId : 0,
            prodId : 0,
            amount : 0,
            chanceDesc : ''
          },
          rowIndex : 0,
          modal_loading : false,
          showDeleteModal : false,
          showInsertModal : false,
          searchParam: {
            custName : '',
            prodName : ''
          },
          loading : true,
          columns: [
            {
              title : '客户名称',
              key : 'custName',
              width : 150,
              align : 'center',
              fixed : 'left',
              sortable: true,
              //自定义渲染列(添加图标)
              render: (h, params) => {
                return h('div', [
                  h('Icon', {
                    props: {
                      type: 'person'
                    }
                  }),
                  h('strong', params.row.custName)
                ]);
              }
            },
            {
              title : '机会编号',
              key : 'id',
              width : 100
            },
            {
              title : '产品名称',
              key : 'prodName',
              width : 150
            },
            {
              title : '购买数量',
              key : 'amount',
              width : 120,
              sortable: true
            },
            {
              title :'机会描述',
              key : 'chanceDesc',
              width : 250
            },
            {
              title : '创建时间',
              key : 'gmtCreated',
              width : 250,
              sortable: true,
              //日期格式化
              render : (h, params) => {
                return h('div', Utils.strToDateTime(params.row.gmtCreated))
              }
            },
            {
              title : '操作',
              key : 'action',
              align : 'center',
              width : 230,
              render : (h, params) => {
                return h('div', [
                  h('Button', {
                    props: {
                      type: 'primary',
                      size: 'small'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.show(params.index)
                      }
                    }
                  }, 'View'),
                  h('Button', {
                    props: {
                      type: 'success',
                      size: 'small'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.update(params.index)
                      }
                    }
                  }, 'Update'),
                  h('Button', {
                    props: {
                      type: 'error',
                      size: 'small'
                    },
                    on: {
                      click: () => {
                        // this.remove(params.index)
                        this.showDeleteModal = true;
                        this.rowIndex = params.index;
                      }
                    }
                  }, 'Delete')
                ]);
              }
            }
          ],
          saleChanceArr: [],
          total: 0
        }
      },
      methods : {
        insertShow () {
          var me = this;
          me.insertForm.custId = 0;
          me.insertForm.prodId = 0;
          me.insertForm.amount = 1;
          me.insertForm.chanceDesc = '';
          me.modalTitle = '新增销售机会';
          me.showInsertModal = true
        },
        //插入&更新销售机会
        insertChance () {
          var me = this;
          //判断当期是更新操作还是插入操作
          if (me.modalTitle === '新增销售机会') {
            var params = {
              custId : me.insertForm.custId,
              prodId : me.insertForm.prodId,
              amount : me.insertForm.amount,
              chanceDesc : me.insertForm.chanceDesc
            };
            Utils.post('/api/insertChance.json', params, function (d) {
              alert(d);
            });
            this.saleChanceArr.push(0);
          } else if (me.modalTitle === '更新销售机会') {
            var params = {
              id : me.saleChanceArr[me.rowIndex].id,
              custId : me.insertForm.custId,
              prodId : me.insertForm.prodId,
              amount : me.insertForm.amount,
              chanceDesc : me.insertForm.chanceDesc
            };
            Utils.post('/api/updateChance.json', params, function (d) {
              alert(d);
            });
          } else {
            alert('未知操作！');
          }
          //重新加载数据
          this.loadData();
          //关闭对话框
          this.showInsertModal = false;
        },
        //模糊查询
        search : function () {
          // alert(this.searchParam.custName);
          var me = this;
          me.loading = true;
          Utils.post('/api/fuzzyQuery.json', this.searchParam, function (d) {
            me.total = d.total;
            me.saleChanceArr = d.chanceList;
          });
          me.loading = false;
        },
        //加载数据
        loadData : function () {
          var me = this;
          me.loading = true;
          Utils.post('/api/queryAllChance.json', {}, function (d) {
            me.total = d.total;
            me.saleChanceArr = d.chanceList;
            me.custList = d.customerList;
            me.prodList = d.productList;
          });
          me.loading = false;
        },
        //导出数据
        exportData : function (type) {
          if (type === 1) {
            this.$refs.table.exportCsv({
              filename: 'SaleChance_origin'
            });
          } else if (type === 2) {
            this.$refs.table.exportCsv({
              filename: 'SaleChance_sort&filter',
              original: false
            });
          }
        },
        //操作
        show (index) {
          this.$Modal.info({
            title: '销售机会详情',
            content: `客户姓名：${this.saleChanceArr[index].custId}<br>产品名称：${this.saleChanceArr[index].prodId}<br>机会描述：${this.saleChanceArr[index].chanceDesc}`
          })
        },
        update (index) {
          var me = this;
          me.insertForm.custId = me.saleChanceArr[index].custId;
          me.insertForm.prodId = me.saleChanceArr[index].prodId;
          me.insertForm.amount = me.saleChanceArr[index].amount;
          me.insertForm.chanceDesc = me.saleChanceArr[index].chanceDesc;
          me.rowIndex = index;
          me.modalTitle = '更新销售机会';
          me.showInsertModal = true;
        },
        remove () {
          //前端移除
          // this.saleChanceArr.splice(index, 1);
          //后端移除
          Utils.post('/api/deleteChance.json', {id : this.saleChanceArr[this.rowIndex].id}, function (d) {
            this.loadData();
          });
          this.showDeleteModal = false;
        },
        refresh () {
          location.reload();
        }
      },
      mounted : function() {
        this.loadData();
      }
    }
</script>

<style scoped>

</style>
