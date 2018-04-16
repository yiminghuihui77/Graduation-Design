<template>
	<Menu active-name="" width="auto" @on-select="navi">
		<Submenu name="1" >
			<template slot="title">
				<Icon type="ios-navigate"></Icon>
				营销管理
			</template>
			<Menu-item name="/saleChance.html"><Icon type="ios-crop-strong"></Icon>销售机会管理</Menu-item>
			<Menu-item name="/customerDevelop.html"><Icon type="ios-crop-strong"></Icon>客户开发计划</Menu-item>
		</Submenu>
		<Submenu name="2">
			<template slot="title">
				<Icon type="ios-cog"></Icon>
				客户管理
			</template>
			<Menu-item name ="/customerInfo.html"><Icon type="ios-crop-strong"></Icon>客户信息管理</Menu-item>
      <Menu-item name ="/customerLoss.html"><Icon type="ios-crop-strong"></Icon>客户流失监控</Menu-item>
		</Submenu>
		<Submenu name="3">
			<template slot="title">
				<Icon type="monitor"></Icon>
				服务管理
			</template>
			<Menu-item name=""><Icon type="ios-crop-strong"></Icon>创建服务</Menu-item>
      <Menu-item name =""><Icon type="ios-crop-strong"></Icon>处理服务</Menu-item>
			<Menu-item name=""><Icon type="ios-crop-strong"></Icon>归档服务</Menu-item>
		</Submenu>
		<Submenu name="4">
			<template slot="title">
				<Icon type="speakerphone"></Icon>
				统计报表
			</template>
			<Menu-item name =""><Icon type="ios-crop-strong"></Icon>客户贡献统计</Menu-item>
			<Menu-item name =""><Icon type="ios-crop-strong"></Icon>客户流失统计</Menu-item>
			<Menu-item name = ""><Icon type="ios-crop-strong"></Icon>服务概况统计</Menu-item>
		</Submenu>

	</Menu>
</template>
<script>
    export default {
        props : {},
        data() {
            return {}
        },
        computed : {},
        methods : {
            navi : function(name) {
                Utils.jumpTo(name)
            }
        },
        mounted : function() {},
        components : {}
    }
</script>

<style lang="less" ref="stylesheet/less">


</style>
