<template>
  <div class="wrapper-content ivu-article">
    <article>
      <h2>客户服务统计分析</h2>
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

      <!--交往记录-->
      <Modal  title="服务记录" v-model="showService" width="850">
        <div class="ivu-row example-vertical">
          <div class="ivu-col ivu-col-span-24">
            <div class="example-case" >
              <Table :columns="columnsService" :data="serviceHistory"></Table>
            </div>
          </div>
        </div>
      </Modal>

    </article>
  </div>
</template>

<script>
    export default {
        name: "service-analyze",
      data () {
          return {
            serviceHistory : [],
            columnsService : [
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
                },
              },
              {
                title : '服务状态',
                width : 150,
                key : 'status',
                render : (h, params) => {
                  if (params.row.status === 0) {
                    return h('div', '处理中');
                  } else if (params.row.status === 1) {
                    return h('div', '已归档');
                  }
                }
              }
            ],
            showService : false,
            searchParam : {
              custName : '',
              prodName : ''
            },
            tableLoading : false,
            total : 0,
            columns : [
              {
                title : '客户编号',
                width : 200,
                key : 'custId',
                align: 'center'
              },
              {
                title : '客户姓名',
                width : 200,
                key : 'custName',
                align: 'center',
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
                title : '服务次数',
                width : 150,
                align: 'center',
                key : 'serviceCount'
              },
              {
                title : '查看详情',
                key : 'action',
                align : 'center',
                width : 250,
                render : (h, params) => {
                  return h('Button', {
                    props: {
                      type: 'success',
                      size: 'large',
                      icon : "ios-search"
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.showServiceHistory(params.index)
                      }
                    }
                  }, '服务历史');
                }
              }
            ],
            serviceArr : [],
          }
      },
      methods : {
        //导出数据
        exportData : function (type) {
          if (type === 1) {
            this.$refs.table.exportCsv({
              filename: 'contributionAnalyze_origin'
            });
          } else if (type === 2) {
            this.$refs.table.exportCsv({
              filename: 'contributionAnalyze_sort&filter',
              original: false
            });
          }
        },
        refresh () {
          location.reload();
        },
        search () {

        },
        showServiceHistory (index) {
          var me = this;
          me.serviceHistory = me.serviceArr[index].serviceHistory;
          me.showService = true;
        },
        //加载数据
        loadData() {
          var me = this;
          me.tableLoading = true;
          Utils.post('/api/queryServiceAnalyze.json', {}, function (d) {
            me.serviceArr = d.serviceAnalyzeList;
            me.total = d.total;
          });
          me.tableLoading = false;
        }
      },
      mounted : function() {
        this.loadData();
      }
    }
</script>

<style scoped>

</style>
