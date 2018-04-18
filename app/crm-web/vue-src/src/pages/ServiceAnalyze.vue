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


    </article>
  </div>
</template>

<script>
    export default {
        name: "service-analyze",
      data () {
          return {
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
                key : 'purchaseCount'
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
                  }, '交易历史');
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

        }
      }
    }
</script>

<style scoped>

</style>
