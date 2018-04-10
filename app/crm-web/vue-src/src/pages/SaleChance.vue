<template>
  <div class="wrapper-content ivu-article">
    <article>
      <h2>{{title}}</h2>

      <!--<div class="ivu-row example-vertical" style="margin-bottom: 20px;">
        <div class="ivu-col ivu-col-span-24 example-case">
          <Card>
            <Form :label-width="80">
              <FormItem label="规则名称">
                <div class="ivu-row">
                  <div class="ivu-col ivu-col-span-11">
                    <Input placeholder="请输入规则名称(RULE_NAME)" v-model=""/>
                  </div>
                  <div class="ivu-col ivu-col-span-2">
                    <label style="margin-left: 20px;">时间</label>
                  </div>
                  <div class="ivu-col ivu-col-span-11">
                    <DatePicker type="datetimerange" :editable="false"
                                style="width: 300px;" placeholder="选择日期和时间"
                                v-model="">

                    </DatePicker>
                  </div>
                </div>
              </FormItem>
            </Form>
          </Card>
        </div>
      </div>-->
    </article>
  </div>
</template>

<script>
    export default {
      name: "sale-chance",
      data () {
          return {
            title : '销售机会管理'
          }
      }
    }
</script>

<style scoped>

</style>
