<template>
  <div class="wrapper-content ivu-article">
    <article>

      <h2>处理服务</h2>

      <div class="ivu-row example-vertical" style="margin-bottom: 20px;">
        <div class="ivu-col ivu-col-span-24 example-case">
          <Card>
            <Form :label-width="80" inline>
              <FormItem label="客户姓名">
                <Input placeholder="客户姓名(CUST_NAME)" v-model="searchParam.custName" style="width: 300px"/>
              </FormItem>

              <FormItem label="服务类型">
                <Select v-model="searchParam.type" style="width: 300px">
                  <Option v-for="item in typeArr" :value="item.value" :key="item.value">{{item.desc}}</Option>
                </Select>
              </FormItem>

              <FormItem style="margin-bottom:-8px;" align="center">
                <Button type="primary" @click="refresh" icon="ios-loop">刷新</Button>
                &nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
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
            <Table :loading="tableLoading" border :columns="columns" :data="serviceArr" ref="table" ></Table>
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
      <!--处理服务对话框-->
      <Modal v-model="showProcessModal" width="360">
        <p slot="header" style="color:#f60;text-align:center">
          <Icon type="information-circled"></Icon>
          <span>记录服务处理结果</span>
        </p>
        <Input v-model="processResult" type="textarea" :rows="6" placeholder="请输入服务处理结果.."></Input>
        <div slot="footer">
          <Button type="success" size="large" long @click="commitResult">记录完毕</Button>
        </div>
      </Modal>

    </article>
  </div>
</template>

<script>
    export default {
      name: "service-process",
      data () {
          return {
            //type数组
            typeArr : [
              { value : -1,
                desc : '建议'
              },
              { value : 0,
                desc : '咨询'
              },
              { value : 1,
                desc : '投诉'
              }
            ],
            searchParam : {
              custName : '',
              type : null
            },
            tableLoading : false,
            columns : [
              {
                title : '服务类型',
                width : 150,
                key : 'type',
                render : (h, params) => {
                  if (params.row.type === -1) {
                    return h('div', '建议');
                  } else if (params.row.type === 0) {
                    return h('div', '咨询');
                  } else if (params.row.type === 1) {
                    return h('div', '投诉');
                  }
                }
              },
              {
                title : '客户姓名',
                width :180,
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
                title : '负责经理',
                width : 180,
                key : 'dueName'
              },
              {
                title : '服务请求',
                width : 380,
                key : 'serviceDesc'
              },
              {
                title : '创建时间',
                width : 200,
                key : 'gmtCreated',
                //日期格式化
                render : (h, params) => {
                  return h('div', Utils.strToDateTime(params.row.gmtCreated))
                }
              },
              {
                title : '处理',
                key : 'action',
                align : 'center',
                width : 160,
                render : (h, params) => {
                  return h('Button', {
                      props: {
                        type: 'success',
                        size: 'small'
                      },
                      style: {
                        marginRight: '5px'
                      },
                      on: {
                        click: () => {
                          this.showModal(params.index)
                        }
                      }
                    }, 'Process')
                }
              }
            ],
            serviceArr : [],
            total : 0,
            rowIndex : 0,
            showProcessModal : false,
            processResult : ''
          }
      },
      methods : {
        //导出数据
        exportData : function (type) {
          if (type === 1) {
            this.$refs.table.exportCsv({
              filename: 'Service_origin'
            });
          } else if (type === 2) {
            this.$refs.table.exportCsv({
              filename: 'Service_sort&filter',
              original: false
            });
          }
        },
        //显示处理服务对话框
        showModal (index) {
          var me = this;
          //显示之前处理的结果
          me.processResult = me.serviceArr[index].remark;
          me.rowIndex = index;
          me.showProcessModal = true;
        },
        //加载数据
        loadData () {
          var me = this;
          me.tableLoading = true;
          Utils.post('/api/queryAllService.json', {}, function (d) {
            me.serviceArr = d.serviceList;
            me.total = d.total;
          });
          me.tableLoading = false;
        },
        //提交服务处理结果
        commitResult () {
          var me = this;
          var params = {
            id : me.serviceArr[me.rowIndex].id,
            remark : me.processResult
          };
          Utils.post('/api/recordProcessResult.json', params, function (d) {
            alert(d);
          });
          me.showProcessModal = false;
          me.refresh();
        },
        refresh () {
          location.reload();
        },
        //模糊查询
        search () {
          var me = this;
          var params = {
            custName : me.searchParam.custName,
            type : me.searchParam.type
          };
          Utils.post('/api/serviceFuzzyQuery.json', params, function (d) {
            me.serviceArr = d.serviceList;
            me.total = d.total;
          });
          //清除搜索框
          me.searchParam.custName = '';
          me.searchParam.type = null;
        }
      },
      mounted : function() {
        this.loadData();
      }
    }
</script>

<style scoped>

</style>
