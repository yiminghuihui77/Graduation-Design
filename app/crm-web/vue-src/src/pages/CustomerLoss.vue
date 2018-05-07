<template>
  <div class="wrapper-content ivu-article">
    <article>

      <h2>客户流失监控</h2>

      <div class="ivu-row example-vertical" style="margin-bottom: 20px;">
        <div class="ivu-col ivu-col-span-24 example-case">
          <Card>
            <Form :label-width="80" inline>
              <FormItem label="客户姓名">
                <Input placeholder="客户姓名(CUST_NAME)" v-model="searchParam.custName" style="width: 300px"/>
              </FormItem>

              <FormItem label="流失状态">
                <Select v-model="searchParam.status" style="width: 300px;">
                  <Option value="0">暂缓流失</Option>
                  <Option value="1">已流失</Option>
                </Select>
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
            <Table :loading="tableLoading" border :columns="columns" :data="custLossArr" ref="table" ></Table>
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
          <span>删除客户流失记录</span>
        </p>
        <div style="text-align:center">
          <p>您确定要删除该条客户流失记录?</p>
        </div>
        <div slot="footer">
          <Button type="warning" size="large" long :loading="modalLoading" @click="deleteLoss">删除</Button>
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
            <Select v-model="insertForm.custId" style="width: 300px">
              <Option v-for="item in custList" :value="item.id" :key="item.id">{{item.cusName}}</Option>
            </Select>
          </FormItem>
          <FormItem label="上次联系时间">
            <DatePicker type="datetime" v-model="insertForm.lastContactTime" placeholder="上次联系时间" style="width: 300px"></DatePicker>
          </FormItem>
          <FormItem label="确认流失时间">
            <DatePicker type="datetime" v-model="insertForm.confirmLossTime" placeholder="确认流失时间" style="width: 300px"></DatePicker>
          </FormItem>
          <FormItem label="流失状态">
            <Select v-model="insertForm.status" style="width: 300px">
              <Option value="0">暂缓流失</Option>
              <Option value="1">已流失</Option>
            </Select>
          </FormItem>
          <FormItem label="备注">
            <Input v-model="insertForm.remark" type="textarea" :rows="6" placeholder="请输入备注信息.."></Input>
          </FormItem>
        </Form>
        <div slot="footer" align="center">
          <Button type="primary" size="large" @click="saveLoss">保存</Button>
          <Button type="primary" size="large" @click="showInsertModal = false">关闭</Button>
        </div>
      </Modal>

    </article>
  </div>
</template>

<script>
    export default {
      name: "customer-loss",
      data () {
          return {
            custList : [],
            insertForm : {
              custId : 0,
              lastContactTime : new Date(),
              confirmLossTime : new Date(),
              status : 0,
              remark : ''
            },
            showInsertModal : false,
            modalTitle : '',
            rowIndex : 0,
            showDeleteModal : false,
            modalLoading : false,
            searchParam : {
              custName : '',
              status : ''
            },
            tableLoading : false,
            total : 0,
            columns : [
              {
                title : '客户姓名',
                width : 140,
                fixed : 'left',
                key : 'custName',
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
                title : '上次联系时间',
                width : 180,
                key : 'lastContactTime',
                //日期格式化
                render : (h, params) => {
                  return h('div', Utils.strToDateTime(params.row.lastContactTime))
                }
              },
              {
                title : '确认流失时间',
                width : 180,
                key : 'confirmLossTime',
                //日期格式化
                render : (h, params) => {
                  return h('div', Utils.strToDateTime(params.row.confirmLossTime))
                }
              },
              {
                title : '流失状态',
                width : 180,
                key : 'status',
                render : (h, params) => {
                  if (params.row.status === 0) {
                    return h('div', '暂缓流失');
                  } else if (params.row.status === 1) {
                    return h('div', '已流失');
                  }
                }
              },
              {
                title : '备注',
                width : 400,
                key : 'remark'
              },
              {
                title : '操作',
                key : 'action',
                width : 180,
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
                          this.deleteModalShow(params.index);
                        }
                      }
                    }, 'Delete')
                  ]);
                }
              }
            ],
            custLossArr : [],

          }
      },
      methods : {
        refresh () {
          location.reload();
        },
        insertShow () {
          var me = this;
          me.modalTitle = '添加客户流失记录';
          //清除数据
          me.insertForm.custId = 0;
          me.insertForm.lastContactTime = new Date();
          me.insertForm.confirmLossTime = new Date();
          me.insertForm.status = 0;
          me.insertForm.remark = '';
          me.showInsertModal = true;
        },
        search () {
          var me = this;
          var params = {
            custName : me.searchParam.custName,
            status : me.searchParam.status
          };
          Utils.post('/api/lossFuzzyQuery.json', params, function (d) {
            me.custLossArr = d.lossList;
            me.total = d.total;
          });
          //清空数据
          me.searchParam.custName = '';
          me.searchParam.status = '';
        },
        //导出数据
        exportData : function (type) {
          if (type === 1) {
            this.$refs.table.exportCsv({
              filename: 'customerLoss_origin'
            });
          } else if (type === 2) {
            this.$refs.table.exportCsv({
              filename: 'customerLoss_sort&filter',
              original: false
            });
          }
        },
        //加载数据
        loadData () {
          var me = this;
          me.tableLoading = true;
          Utils.post('/api/queryAllLoss.json', {}, function (d) {
            me.custLossArr = d.lossList;
            me.total = d.total;
            me.custList = d.customerList;
          });
          me.tableLoading = false;
        },
        //弹出删除对话框
        deleteModalShow (index) {
          var me = this;
          me.rowIndex = index;
          me.showDeleteModal = true;
        },
        //删除
        deleteLoss () {
          var me = this;
          var params = {
            id : me.custLossArr[me.rowIndex].id
          };
          Utils.post('/api/deleteLoss.json', params, function (d) {
            // alert(d);
          });
          me.showDeleteModal = false;
          me.$Modal.success({
            title: '客户流失监控',
            content: '<p style="font-size: large">客户流失记录删除成功！</p>',
            onOk : () => {
              //刷新页面
              me.refresh();
            }
          });
        },
        //插入or更新
        saveLoss () {
          var me = this;
          if (me.modalTitle === '添加客户流失记录') {
            var params = {
              custId : me.insertForm.custId,
              lastContactTime : me.insertForm.lastContactTime,
              confirmLossTime : me.insertForm.confirmLossTime,
              status : me.insertForm.status,
              remark : me.insertForm.remark
            };
            Utils.post('/api/addLoss.json', params, function (d) {
              // alert(d);
            });
            me.showInsertModal = false;
            me.$Modal.success({
              title: '客户流失监控',
              content: '<p style="font-size: large">客户流失记录创建成功！</p>',
              onOk : () => {
                //刷新页面
                me.refresh();
              }
            });

          } else if (me.modalTitle === '修改客户流失记录') {
            var params = {
              id : me.custLossArr[me.rowIndex].id,
              custId : me.insertForm.custId,
              lastContactTime : me.insertForm.lastContactTime,
              confirmLossTime : me.insertForm.confirmLossTime,
              status : me.insertForm.status,
              remark : me.insertForm.remark
            };
            Utils.post('/api/updateLoss.json', params, function (d) {
              // alert(d);
            });
            me.showInsertModal = false;
            me.$Modal.success({
              title: '客户流失监控',
              content: '<p style="font-size: large">客户流失记录修改成功！</p>',
              onOk : () => {
                //刷新页面
                me.refresh();
              }
            });
          }

        },
        updateModalShow(index) {
          var me = this;
          me.modalTitle = '修改客户流失记录';
          //回显
          me.insertForm.custId = me.custLossArr[index].custId;
          me.insertForm.lastContactTime = me.custLossArr[index].lastContactTime;
          me.insertForm.confirmLossTime = me.custLossArr[index].confirmLossTime;
          me.insertForm.status = me.custLossArr[index].status + '';
          me.insertForm.remark = me.custLossArr[index].remark;
          me.showInsertModal = true;
          me.rowIndex = index;
          //日期回显小时部分不对
          // alert(me.custLossArr[index].lastContactTime + '---' + me.custLossArr[index].confirmLossTime);
        }

      },
      mounted : function() {
        this.loadData();
      }
    }
</script>

<style scoped>

</style>
