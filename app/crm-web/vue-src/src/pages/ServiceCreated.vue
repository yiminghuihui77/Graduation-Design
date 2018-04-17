<template>
  <div class="wrapper-content ivu-article">

    <article>
      <h2>创建服务</h2>

      <Row>
        <Col span="11">
        <Card :bordered="false">
          <!--表单-->
          <Form :model="insertForm" :label-width="80">
            <FormItem label="服务类型">
              <Select v-model="insertForm.type" style="width: 300px">
                <Option v-for="item in typeArr" :value="item.value" :key="item.value">{{item.desc}}</Option>
              </Select>
            </FormItem>
            <FormItem label="客户">
              <Select v-model="insertForm.custId" style="width: 300px">
                <Option v-for="item in customerArr" :value="item.id" :key="item.id">{{item.cusName}}</Option>
              </Select>
            </FormItem>
            <FormItem label="客户经理">
              <Select v-model="insertForm.dueId" style="width: 300px">
                <Option v-for="item in dueArr" :value="item.id" :key="item.id">{{item.name}}</Option>
              </Select>
            </FormItem>
            <FormItem label="服务请求">
              <Input v-model="insertForm.serviceDesc" type="textarea" :rows="6" placeholder="请输入服务请求信息.." style="width: 400px;"></Input>
            </FormItem>
            <FormItem style="margin-bottom:-8px;">
              <Button type="primary" @click="createService" icon="ios-plus-outline">创建</Button>
              &nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;	&nbsp;
              <Button type="primary" @click="reset" icon="ios-loop">重置</Button>
            </FormItem>
          </Form>
        </Card>
        </Col>
        <Col span="11" offset="2">
        <Card :bordered="false">
          <p slot="title">服务管理步骤</p>
          <Steps :current="0" direction="vertical">
            <Step title="创建服务" icon="person-add" content="销售主管创建服务，并将服务指派给客户经理"></Step>
            <Step title="处理服务" icon="android-create" content="客户经理对客户进行服务，记录客户反馈的信息"></Step>
            <Step title="归档服务" icon="android-document" content="销售主管对服务进行评分、归档"></Step>
          </Steps>
          <br/><br/><br/><br/><br/>
        </Card>
        </Col>
      </Row>

    </article>

  </div>
</template>

<script>
    export default {
        name: "service-created",
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
            //表单数据
            insertForm : {
              type : -2,
              serviceDesc : '',
              dueId : 0,
              custId : 0
            },
            customerArr : [],
            dueArr : []

          }
      },
      methods : {
          clearForm () {
            var me = this;
            me.insertForm.custId = null;
            me.insertForm.dueId = null;
            me.insertForm.type = null;
            me.insertForm.serviceDesc = '';
          },
        createService () {
          var me = this;
          var params = {
            type : me.insertForm.type,
            serviceDesc : me.insertForm.serviceDesc,
            dueId : me.insertForm.dueId,
            custId : me.insertForm.custId
          };
          Utils.post('/api/addService.json', params, function (d) {
            alert(d);
          });
          me.clearForm();
        },
        reset () {
          var me = this;
          me.clearForm();
        },
        loadData () {
          var me = this;
          Utils.post('/api/loadDataOfCreated.json', {}, function (d) {
            me.customerArr = d.customerList;
            me.dueArr = d.dueList;
          });
        }
      },
      mounted : function() {
        this.loadData();
      }
    }
</script>

<style scoped>

</style>
