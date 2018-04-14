<template>
  <div class="wrapper-content ivu-article">
    <article>
      <h2>客户信息管理</h2>

      <div class="ivu-row example-vertical" style="margin-bottom: 20px;">
        <div class="ivu-col ivu-col-span-24 example-case">
          <Card>
            <Form :label-width="80" inline>
              <FormItem label="客户姓名">
                <Input placeholder="客户姓名(CUST_NAME)" v-model="searchParam.custName" style="width: 300px"/>
              </FormItem>

              <FormItem label="负责经理">
                <Input placeholder="负责经理(MANAGER_NAME)" v-model="searchParam.managerName" style="width: 300px"/>
              </FormItem>

              <FormItem label="状态">
                <div class="ivu-col ivu-col-span-11">
                  <Select placeholder="请选择状态" v-model="searchParam.status" filterable style="width: 150px;">
                    <Option value="ENABLE">ENABLE</Option>
                    <Option value="DISABLE">DISABLE</Option>
                  </Select>
                </div>
              </FormItem>

              <FormItem style="margin-bottom:-8px;" align="center">
                <Button type="primary" @click="refresh" icon="ios-loop">刷新</Button>
                <Button type="primary" @click="insertModalShow" icon="ios-plus-outline">新增</Button>
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
            <Table :loading="tableLoading" border :columns="columns" :data="customerArr" ref="table" ></Table>
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
          <span>禁止or使能客户状态</span>
        </p>
        <div style="text-align:center">
          <p>您确定要{{this.oppositeStatus}}该客户状态?</p>
        </div>
        <div slot="footer">
          <Button type="warning" size="large" long :loading="modal_loading" @click="changeStatus">{{this.oppositeStatus}}</Button>
        </div>
      </Modal>
      <!--交往记录-->
      <Modal  title="交往记录" v-model="showActivity" width="650">
        <div class="ivu-row example-vertical">
          <div class="ivu-col ivu-col-span-24">
            <div class="example-case" >
              <Table :columns="columnsActivity" :data="custActivity"></Table>
            </div>
          </div>
        </div>
      </Modal>

      <!--新增/更新客户-->
      <Modal width="700" v-model="showInsertModal">

        <div slot="header" style="color:#1197EC;text-align:center;font-size: large">
          <Icon type="plus-round"></Icon>
          <span>{{modalTitle}}</span>
        </div>
        <Form :model="insertForm" :label-width="80">
          <FormItem label="客户姓名">
            <Input v-model="insertForm.cusName" placeholder="请输入客户姓名.." style="width: 300px"></Input>
          </FormItem>
          <FormItem label="性别">
            <Select v-model="insertForm.sex" style="width: 300px">
              <Option value="男">男</Option>
              <Option value="女">女</Option>
            </Select>
          </FormItem>
          <FormItem label="负责经理">
            <Select v-model="insertForm.managerId" style="width: 300px">
              <Option v-for="item in managerArr" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
          </FormItem>
          <FormItem label="客户等级">
            <Select v-model="insertForm.cusLevel" style="width: 300px">
              <Option value="VIP用户">VIP用户</Option>
              <Option value="普通用户">普通用户</Option>
              <Option value="高级用户">高级用户</Option>
            </Select>
          </FormItem>
          <FormItem label="信用度">
            <Rate v-model="insertForm.credit"></Rate>
          </FormItem>
          <FormItem label="地区">
            <Input v-model="insertForm.region" placeholder="请输入地区.." style="width: 300px"></Input>
          </FormItem>
          <FormItem label="详细地址">
            <Input v-model="insertForm.addr" placeholder="请输入详细地址.."></Input>
          </FormItem>
          <FormItem label="手机号码">
            <Input v-model="insertForm.phone" placeholder="请输入手机号码.." style="width: 300px"></Input>
          </FormItem>
          <FormItem label="状态">
            <Select v-model="insertForm.status" style="width: 300px">
              <Option value="ENABLE">ENABLE</Option>
              <Option value="DISABLE">DISABLE</Option>
            </Select>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="insertForm.memo" type="textarea" :rows="6" placeholder="请输入备注信息.."></Input>
          </FormItem>
        </Form>
        <div slot="footer" align="center">
          <Button type="primary" size="large" @click="saveCustomer">保存</Button>
          <Button type="primary" size="large" @click="showInsertModal = false">关闭</Button>
        </div>
      </Modal>

    </article>
  </div>
</template>

<script>
  export default {
    name: "customer-info",
    data () {
      return {
        insertForm : {
          cusName : '',
          sex : '',
          managerId : 0,
          cusLevel : '',
          credit : 0,
          region : '',
          addr : '',
          phone : '',
          status : '',
          memo : ''
        },
        modalTitle : '',
        showInsertModal : false,
        columnsActivity : [
          {
            title : '交往日期',
            key : 'date',
            width : 200,
            align : 'center',
            //日期格式化
            render : (h, params) => {
              return h('div', Utils.strToDateTime(params.row.date))
            }
          },
          {
            title : '交往地点',
            key : 'place',
            width : 100,
            align : 'center'
          },
          {
            title : '详情描述',
            key : 'activityDesc',
            width : 100,
            align : 'center'
          },
          {
            title : '创建时间',
            key : 'gmtCreated',
            width : 200,
            align : 'center',
            //日期格式化
            render : (h, params) => {
              return h('div', Utils.strToDateTime(params.row.gmtCreated))
            }
          }
        ],
        custActivity : [],
        showActivity : false,
        oppositeStatus : '',
        custStatus : '',
        modal_loading : false,
        showDeleteModal : false,
        rowIndex : 0,
        searchParam : {
          custName : '',
          managerName : '',
          status : ''
        },
        tableLoading : false,
        total : 0,
        columns : [
          {
            title : '客户编号',
            key : 'id',
            width : 100,
            align : 'center'
          },
          {
            title : '客户姓名',
            key : 'cusName',
            width : 160,
            align : 'center',
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    type: 'person'
                  }
                }),
                h('strong', params.row.cusName)
              ]);
            }
          },
          {
            title : '性别',
            key : 'sex',
            align : 'center',
            width : 100
          },
          {
            title : '负责经理',
            key : 'managerName',
            align : 'center',
            width : 160
          },
          {
            title : '客户等级',
            key : 'cusLevel',
            align : 'center',
            width : 160
          },
          {
            title : '信用度',
            key : 'credit',
            align : 'center',
            width : 180,
            render : (h, params) => {
              return h('Rate', {
                props : {
                  count : 5,
                  disabled : true,
                  value : params.row.credit}
              });
            }
          },
          {
            title : '状态',
            key : 'status',
            align : 'center',
            width : 100
          },
          {
            title : '其他信息',
            key : 'detail',
            width : 150,
            align : 'center',
            render : (h, params) =>{
              return h('div',[
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
                      this.viewShow(params.index)
                    }
                  }
                }, '详细'),
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
                      this.activityShow(params.index)
                    }
                  }
                }, '交往')
              ]);
            }
          },
          {
            title : '操作',
            key : 'action',
            width : 150,
            align : 'center',
            render : (h, params) => {
              return h('div', [
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
                      this.updateModalShow(params.index);
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
                      this.showDeleteModal = true;
                      this.rowIndex = params.index;
                      this.custStatus = params.row.status;
                      if (params.row.status === 'ENABLE') {
                        this.oppositeStatus = 'DISABLE';
                      } else if (params.row.status === 'DISABLE') {
                        this.oppositeStatus = 'ENABLE';
                      }
                    }
                  }
                }, 'Status')
              ]);
            }
          }
        ],
        customerArr : [],
        managerArr : []
      }
    },
    methods : {
      //DISABLE客户信息
      changeStatus () {
        var me = this;
        var params = {
          id : me.customerArr[this.rowIndex].id,
          status : this.oppositeStatus
        };
        Utils.post('/api/changeStatus.json', params, function (d) {

        });
        this.showDeleteModal = false;
      },
      //刷新当前页面
      refresh : function () {
        location.reload();
      },
      //弹出插入对话框
      insertModalShow () {
        //初始化表单绑定数据
       this.insertForm.cusName = '';
       this.insertForm.sex = '';
       this.insertForm.managerId = 0;
       this.insertForm.cusLevel = '';
       this.insertForm.credit = 0;
       this.insertForm.region = '';
       this.insertForm.addr = '';
       this.insertForm.phone = '';
       this.insertForm.status = '';
       this.insertForm.memo = '';
        //显示对话框
        this.modalTitle = "新增客户信息";
        this.showInsertModal = true;
      },
      updateModalShow (index) {
        var me = this;
        //记录当前行
        me.rowIndex = index;
        //回显数据
        me.insertForm.cusName = me.customerArr[index].cusName;
        me.insertForm.sex = me.customerArr[index].sex;
        me.insertForm.managerId = me.customerArr[index].managerId;
        me.insertForm.cusLevel = me.customerArr[index].cusLevel;
        me.insertForm.credit = me.customerArr[index].credit;
        me.insertForm.region = me.customerArr[index].region;
        me.insertForm.addr = me.customerArr[index].addr;
        me.insertForm.phone = me.customerArr[index].phone;
        me.insertForm.status = me.customerArr[index].status;
        me.insertForm.memo = me.customerArr[index].memo;
        //显示对话框
        me.modalTitle = '修改客户信息';
        me.showInsertModal = true;
      },
      //模糊查询
      search () {
        var me = this;
        var params = {
          cusName : this.searchParam.custName,
          managerName : this.searchParam.managerName,
          status : this.searchParam.status
        };
        Utils.post('/api/fuzzyQueryCustomer.json', params, function (d) {
          me.customerArr = d.customerList;
          me.total = d.total;
        })

      },
      //导出数据
      exportData : function (type) {
        if (type === 1) {
          this.$refs.table.exportCsv({
            filename: 'customerInfo_origin'
          });
        } else if (type === 2) {
          this.$refs.table.exportCsv({
            filename: 'customerInfo_sort&filter',
            original: false
          });
        }
      },
      viewShow (index) {
        this.$Modal.info({
          title: '其他信息',
          content: `地区：${this.customerArr[index].region}<br>详细地址：${this.customerArr[index].addr}<br>手机号码：${this.customerArr[index].phone}<br>备注：${this.customerArr[index].memo}`
        });
      },
      activityShow (index) {
        this.custActivity = this.customerArr[index].activityList;
        this.showActivity = true;
      },
      loadCustomers : function () {
        var me = this;
        me.tableLoading = true;
        Utils.post('/api/queryAllCustomer.json', {}, function (d) {
            me.customerArr = d.customerList;
            me.managerArr = d.managerList;
            me.total = d.total;
        });
        me.tableLoading = false;
      },
      //新增&更改客户信息
      saveCustomer () {
        var me = this;
        if (this.modalTitle === '新增客户信息') {
          var params = {
            cusName : me.insertForm.cusName,
            sex : me.insertForm.sex,
            region : me.insertForm.region,
            managerId : me.insertForm.managerId,
            cusLevel : me.insertForm.cusLevel,
            credit : me.insertForm.credit,
            addr : me.insertForm.addr,
            phone : me.insertForm.phone,
            memo : me.insertForm.memo,
            status : me.insertForm.status
          };
          Utils.post('/api/addCustomer.json', params, function (d) {
            alert(d);
          });
          me.customerArr.push(0);
          me.showInsertModal = false;
        } else if (this.modalTitle === '修改客户信息') {
          var params = {
            id : me.customerArr[me.rowIndex].id,
            cusName : me.insertForm.cusName,
            sex : me.insertForm.sex,
            region : me.insertForm.region,
            managerId : me.insertForm.managerId,
            cusLevel : me.insertForm.cusLevel,
            credit : me.insertForm.credit,
            addr : me.insertForm.addr,
            phone : me.insertForm.phone,
            memo : me.insertForm.memo,
            status : me.insertForm.status
          };
          Utils.post('/api/refreshCustomer.json', params, function (d) {
            alert(d);
          });
          me.showInsertModal = false;
        } else {
          alert('未知操作！');
        }
      }

    },
    mounted : function() {
      this.loadCustomers();
    }
  }
</script>

<style scoped>

</style>
