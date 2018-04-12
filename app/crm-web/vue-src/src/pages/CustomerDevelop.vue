<template>
  <div class="wrapper-content ivu-article">
    <article>
      <h2>客户开发计划</h2>

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
                <Button type="primary" @click="modalShow" icon="ios-plus-outline">新增</Button>
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


    </article>
  </div>
</template>

<script>
    export default {
      name: "customer-develop",
      data () {
          return {
            rowIndex : 0,
            showDeleteModal : false,
            searchParam : {
              custName : '',
              prodName : ''
            },
            tableLoading : false,
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
                width : 120
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
                width : 160
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
                  }, 'View')
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
            customerArr : []
          }
      },
      methods : {
        //刷新当前页面
        refresh : function () {
          location.reload();
        },
        //弹出对话框
        modalShow () {

        },
        //模糊查询
        search () {

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
          Utils.post('', {}, function (d) {

          });
          me.tableLoading = false;
        }
      }
    }
</script>

<style scoped>

</style>
