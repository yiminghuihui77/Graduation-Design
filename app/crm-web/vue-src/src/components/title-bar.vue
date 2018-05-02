<template>
	<div style="min-width: 1200px;">
		<Menu mode="horizontal" theme="light" active-name="1" class="border-top">
			<div class="wrapper-header-nav">
				<a href="/" class="wrapper-header-nav-logo router-link-active" :onclick="goHome">
					<img src="../images/CrmLogo.png">
				</a>
				<span class="wrapper-header-nav-title">CRM客户关系管理系统</span>
				<div class="wrapper-header-nav-list">

					<Dropdown transfer trigger="click" @on-click="handleClickUserDropdown">
						<a href="javascript:void(0)">
							<span class="main-user-name"><Icon type="person"></Icon> {{account}}({{password}})</span>
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
			    account : '',
				password : '',
			}
		},
		computed : {},
		methods : {
            handleClickUserDropdown (name) {
                if (name === 'ownSpace') {
                  //个人中心

                } else if (name === 'logout') {
                  //删除cookie
                  Cookies.remove('account');
                  Cookies.remove('password');
                    this.$Modal.confirm({
                        title: '登出系统',
                        content: '<p style="font-size: large">您确认退出CRM服务平台吗？</p>',
                        onOk: () => {
                            // 退出登录
                          /*  Utils.post('/api/logout.json', {}, function(d) {
                                location.reload();
                            });*/
                          Utils.jumpTo('/login.html')
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
      this.account = Cookies.get('account');
      this.password = Cookies.get('password');
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
