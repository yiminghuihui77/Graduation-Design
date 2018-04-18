<template>
  <div class="wrapper-content ivu-article">

    <article>
      <h2>归档服务</h2>

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


    </article>

  </div>
</template>

<script>
    import Utils from "../libs/Utils";

    export default {
      name: "service-archive",
      data () {
          return {
            searchParam : {
              custName : '',
              type : null
            },
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
            tableLoading : false,
            columns : [
              {
                title : '服务类型',
                width : 200,
                key : 'type',
                align : 'center',
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
                width :200,
                key : 'custName',
                align : 'center',
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
                width : 200,
                align : 'center',
                key : 'dueName'
              },
              {
                title : '查看详情',
                width : 180,
                align : 'center',
                key : 'detail',
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
                        this.showDetailModal(params.index)
                      }
                    }
                  }, '详情')
                }
              },
              {
                title : '服务评分',
                key : 'starLevel',
                align : 'center',
                width : 250,
                render : (h, params) => {
                  return h('Rate', {
                    props : {
                      count : 5,
                      disabled : this.getButtonStatus(params.row.status),
                      value : params.row.starLevel
                    },
                    on:{
                      'on-change':(value) => {
                        this.serviceArr[params.index].starLevel = value;
                      }
                    }
                  });
                }
              },
              {
                title : '归档状态',
                width : 180,
                key : 'status',
                align : 'center',
                render : (h, params) => {
                  return h('Button', {
                    props: {
                      type: 'warning',
                      size: 'small',
                      disabled : this.getButtonStatus(params.row.status)
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.archiveService(params.index)
                      }
                    }
                  }, this.getStatusStr(params.row.status))
                }
              }
            ],
            serviceArr : [],
            total : 0,
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
          Utils.post('/api/archiveFuzzyQuery.json', params, function (d) {
            me.serviceArr = d.serviceList;
            me.total = d.total;
          });
          //清除搜索框
          me.searchParam.custName = '';
          me.searchParam.type = null;
        },
        //查看详情
        showDetailModal (index) {
          this.$Modal.info({
            title: '服务详情',
            content: `创建时间：${Utils.strToDateTime(this.serviceArr[index].gmtCreated)}<br>服务请求：${this.serviceArr[index].serviceDesc}<br>服务结果：${this.serviceArr[index].remark}<br>归档状态：${this.getStatusStr(this.serviceArr[index].status)}`
          });
        },
        //执行归档
        archiveService (index) {
          var me = this;
          var params = {
            id : me.serviceArr[index].id,
            starLevel : me.serviceArr[index].starLevel
          };
          Utils.post('/api/archiveService.json', params, function (d) {
            alert(d);
          });
          me.refresh();
        },
        //只加载被处理过的服务
        loadData () {
          var me = this;
          me.tableLoading = true;
          Utils.post('/api/loadProcessedService', {}, function (d) {
            me.serviceArr = d.serviceList;
            me.total = d.total;
          });
          me.tableLoading = false;
        },
        getStatusStr (status) {
          if (status === 0) {
            return '处理中';
          } else if (status === 1) {
            return '已归档';
          }
        },
        getButtonStatus(status) {
          if (status === 0) {
            return false;
          } else if (status === 1) {
            return true;
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
