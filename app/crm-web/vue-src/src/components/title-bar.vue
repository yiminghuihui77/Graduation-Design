<template>
	<div style="min-width: 1200px;">
		<Menu mode="horizontal" theme="light" active-name="1" class="border-top">
			<div class="wrapper-header-nav">
				<a href="/" class="wrapper-header-nav-logo router-link-active" :onclick="goHome">
					<img src="../images/CrmLogo.png">
				</a>
				<span class="wrapper-header-nav-title">CRM客户关系管理系统</span>
				<div class="wrapper-header-nav-list">
					<!--<Submenu name="1">-->
						<!--<template slot="title">-->
							<!--<Icon type="person"></Icon>-->
							<!--ShuliData-->
						<!--</template>-->
						<!--<Menu-group title="系统设置">-->
							<!--<Menu-item name="1-1">个人信息</Menu-item>-->
							<!--<Menu-item name="1-2" @click="logout">退出登录</Menu-item>-->
						<!--</Menu-group>-->
					<!--</Submenu>-->

					<Dropdown transfer trigger="click" @on-click="handleClickUserDropdown">
						<a href="javascript:void(0)">
							<span class="main-user-name"><Icon type="person"></Icon> {{userName}}({{userUid}})</span>
							<Icon type="arrow-down-b"></Icon>
						</a>
						<DropdownMenu slot="list">
							<DropdownItem name="ownSpace">个人中心</DropdownItem>
							<DropdownItem name="logout" divided>退出登录</DropdownItem>
						</DropdownMenu>
					</Dropdown>
				</div>
			</div>
		</Menu>
	</div>
</template>

<script>
    import Cookies from 'js-cookie';

    export default {
		props : {},
		data() {
			return {
			    userName : '',
				userUid : '',
				userMail : ''
			}
		},
		computed : {},
		methods : {
            handleClickUserDropdown (name) {
                if (name === 'ownSpace') {
//                    util.openNewPage(this, 'ownspace_index');
//                    this.$router.push({
//                        name: 'ownspace_index'
//                    });
                } else if (name === 'logout') {
                    this.$Modal.confirm({
                        title: '登出系统',
                        content: '<p>确认退出采集服务平台吗？</p>',
                        onOk: () => {
                            // 退出登录
                            Utils.post('logout.json', {}, function(data) {
                                location.reload();
                            });
                        },
                        onCancel: () => {
                        }
                    });

                }
            },
      goHome () {
              Utils.jumpTo('/index.html')
      }
		},
		mounted : function() {
            this.userName = Cookies.get('user');
            this.userUid = Cookies.get('uid');
            this.userMail = Cookies.get('mail');
		},
		components : {}
	}
</script>

<style lang="less" ref="stylesheet/less">
	.border-top {
		border-top: 1px solid #eedee1;
	}
	.wrapper-header-nav {
		width: 90%;
		height: 60px;
		margin: 0 auto;
		.wrapper-header-nav-logo {
			height: 50px;
			margin-top: 5px;
			float: left;
			img {
				height : 100%
			}
		}
		.wrapper-header-nav-title {
			margin-left: 10px;
			font-size: 20px;
		}
		.wrapper-header-nav-list {
			height : inherit;
			float: right;
		}
	}


</style>
