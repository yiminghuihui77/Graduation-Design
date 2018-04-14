<template>
  <div class="wrapper-content ivu-article">
    <article>
      <h2>客户开发计划</h2>

      <div class="ivu-row example-vertical" style="margin-bottom: 20px;">
        <div class="ivu-col ivu-col-span-24 example-case">
          <Card>
            <Form :label-width="100" inline>
              <FormItem label="客户姓名">
                <Input placeholder="客户姓名(CUST_NAME)" v-model="searchParam.custName" style="width: 300px"/>
              </FormItem>

              <FormItem label="开发状态">
                <Select v-model="searchParam.status" style="width: 300px">
                  <Option value="0">开发中</Option>
                  <Option value="1">已开发</Option>
                </Select>
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
            <Table :loading="tableLoading" border :columns="columns" :data="developArr" ref="table"></Table>
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
          <span>删除客户开发计划</span>
        </p>
        <div style="text-align:center">
          <p>删除操作不可逆</p>
          <p>您确定要删除该客户开发计划?</p>
        </div>
        <div slot="footer">
          <Button type="error" size="large" long :loading="modal_loading" @click="remove">Delete</Button>
        </div>
      </Modal>

      <!--新增/更新客户-->
      <Modal width="700" v-model="showInsertModal">

        <div slot="header" style="color:#1197EC;text-align:center;font-size: large">
          <Icon type="plus-round"></Icon>
          <span>{{modalTitle}}</span>
        </div>
        <Form :model="insertForm" :label-width="120">
          <FormItem label="客户姓名">
            <Input v-model="insertForm.cusName" placeholder="请输入客户姓名.." style="width: 300px"></Input>
          </FormItem>
          <FormItem label="客户电话">
            <Input v-model="insertForm.phone" placeholder="请输入客户电话.." style="width: 300px"></Input>
          </FormItem>
          <FormItem label="客户地址">
            <Input v-model="insertForm.address" placeholder="请输入客户地址.." style="width: 300px"></Input>
          </FormItem>
          <FormItem label="机会来源">
            <Input v-model="insertForm.source" placeholder="请输入机会来源.." style="width: 300px"></Input>
          </FormItem>
          <FormItem label="成功几率">
            <Slider v-model="insertForm.successRate" :step="1" show-input style="width: auto;"></Slider>
          </FormItem>
          <FormItem label="创建人(销售主管)">
            <Select v-model="insertForm.createId" style="width: 300px">
              <Option v-for="item in createArr" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
          </FormItem>
          <FormItem label="指派人(客户经理)">
            <Select v-model="insertForm.dueId" style="width: 300px">
              <Option v-for="item in dueArr" :value="item.id" :key="item.id">{{item.name}}</Option>
            </Select>
          </FormItem>
          <FormItem label="开发状态">
            <Select v-model="insertForm.status" style="width: 300px">
              <Option value="0">开发中</Option>
              <Option value="1">已开发</Option>
            </Select>
          </FormItem>
          <FormItem label="开发描述">
            <Input v-model="insertForm.developDesc" type="textarea" :rows="6" placeholder="请输入备注信息.."></Input>
          </FormItem>
        </Form>
        <div slot="footer" align="center">
          <Button type="primary" size="large" @click="saveDevelop">保存</Button>
          <Button type="primary" size="large" @click="showInsertModal = false">关闭</Button>
        </div>
      </Modal>

    </article>
  </div>
</template>

<script>
    export default {
      name: "customer-develop",
      data () {
          return {
            rowIndex : 0,
            insertForm : {
              cusName : '',
              phone : '',
              address : '',
              source : '',
              successRate : 0,
              developDesc : '',
              createId : 0,
              dueId : 0,
              status : 0
            },
            showDeleteModal : false,
            showInsertModal : false,
            modalTitle : '',
            searchParam : {
              custName : '',
              status : ''
            },
            tableLoading : false,
            columns : [
              {
                title : '客户姓名',
                key : 'cusName',
                width : 120,
                fixed : 'left',
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
                title : '客户电话',
                key : 'phone',
                width : 150,
                align : 'center'
              },
              {
                title : '客户地址',
                key : 'address',
                align : 'center',
                width : 120
              },
              {
                title : '机会来源',
                key : 'source',
                align : 'center',
                width : 160
              },
              {
                title : '成功几率(%)',
                key : 'successRate',
                align : 'center',
                width : 120
              },
              {
                title : '开发描述',
                key : 'developDesc',
                align : 'center',
                width : 160
              },
              {
                title : '创建人',
                key : 'createName',
                align : 'center',
                width : 100
              },
              {
                title : '指派人',
                key : 'dueName',
                align : 'center',
                width : 100
              },
              {
                title : '开发状态',
                key : 'status',
                align : 'center',
                width : 100,
                render : (h, params) => {
                  if (params.row.status === 0) {
                    return h('div', "开发中");
                  } else {
                    return h ('div', "已开发");
                  }
                }
              },
              {
                title : '创建时间',
                key : 'gmtCreated',
                align : 'center',
                width : 150,
                //日期格式化
                render : (h, params) => {
                  return h('div', Utils.strToDateTime(params.row.gmtCreated))
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
                          this.updateModalShow(params.index)
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
            developArr : [],
            total : 0,
            createArr : [],
            dueArr : []
          }
      },
      methods : {
        //刷新当前页面
        refresh : function () {
          location.reload();
        },
        //弹出插入对话框
        insertModalShow () {
          var me = this;
          me.insertForm.cusName = '';
          me.insertForm.phone = '';
          me.insertForm.address = '';
          me.insertForm.source = '';
          me.insertForm.successRate = 0;
          me.insertForm.developDesc = '';
          me.insertForm.createId = 0;
          me.insertForm.dueId = 0;
          me.insertForm.status = 0;
          //显示对话框
          me.modalTitle = '新增客户开发计划';
          me.showInsertModal = true;
        },
        updateModalShow (index) {
          var me = this;
          me.insertForm.cusName = me.developArr[index].cusName;
          me.insertForm.phone = me.developArr[index].phone;
          me.insertForm.address = me.developArr[index].address;
          me.insertForm.source = me.developArr[index].source;
          me.insertForm.successRate = me.developArr[index].successRate;
          me.insertForm.developDesc = me.developArr[index].developDesc;
          me.insertForm.createId = me.developArr[index].createId;
          me.insertForm.dueId = me.developArr[index].dueId;
          me.insertForm.status = me.developArr[index].status + '';
          //显示对话框
          me.rowIndex = index;
          me.modalTitle = '修改客户开发计划';
          me.showInsertModal = true;
        },
        //模糊查询
        search () {
          var me = this;
          var params = {
            custName : me.searchParam.custName,
            status : me.searchParam.status
          };
          Utils.post('/api/developFuzzyQuery.json', params, function (d) {
            me.developArr = d.developList;
            me.total = d.total;
          });
        },
        //导出数据
        exportData : function (type) {
          if (type === 1) {
            this.$refs.table.exportCsv({
              filename: 'customerDev_origin'
            });
          } else if (type === 2) {
            this.$refs.table.exportCsv({
              filename: 'customerDev_sort&filter',
              original: false
            });
          }
        },
        viewShow (index) {
          this.$Modal.info({
            title: '其他信息',
            content: 'hello customerDev'
          })
        },
        loadData () {
          var me = this;
          me.tableLoading = true;
          Utils.post('/api/queryAllDevelops.json', {}, function (d) {
            me.developArr = d.developList;
            me.createArr = d.createList;
            me.dueArr = d.dueList;
            me.total = d.total;
          });
          me.tableLoading = false;
        },
        remove () {
          var me =this;
          var params = {
            id : me.developArr[me.rowIndex].id
          };
          Utils.post('/api/removeDevelop.json', params, function (d) {
            alert(d)
          });
          me.showDeleteModal = false;
          me.refresh();
        },
        //添加or删除客户开发计划
        saveDevelop () {
          var me = this;
          //插入
          if (me.modalTitle === '新增客户开发计划') {
            var params = {
              cusName : me.insertForm.cusName,
              phone : me.insertForm.phone,
              address : me.insertForm.address,
              source : me.insertForm.source,
              successRate : me.insertForm.successRate,
              developDesc : me.insertForm.developDesc,
              createId : me.insertForm.createId,
              dueId : me.insertForm.dueId,
              status : me.insertForm.status
            };
            Utils.post('/api/addDevelop.json', params, function (d) {
              alert(d);
            });
            me.developArr.push(0);
            me.showInsertModal = false;
            me.refresh();
          } else  if (me.modalTitle === '修改客户开发计划') {
            var params = {
              id : me.developArr[me.rowIndex].id,
              cusName : me.insertForm.cusName,
              phone : me.insertForm.phone,
              address : me.insertForm.address,
              source : me.insertForm.source,
              successRate : me.insertForm.successRate,
              developDesc : me.insertForm.developDesc,
              createId : me.insertForm.createId,
              dueId : me.insertForm.dueId,
              status : me.insertForm.status
            };
            Utils.post('/api/updateDevelop.json', params, function (d) {
              alert(d);
            });
            me.showInsertModal = false;
            me.refresh();
          }
        }
      },
      mounted : function() {
        this.loadData();
      }
    }
</script>

<style scoped>

</style>
