<template>
	<div id="viewpage">
		<title-bar></title-bar>

		<div class="layout">
			<div class="mask" id="global-mask" style="display: none;"></div>

			<div class="layout-content content-border">
				<Row>
					<i-col span="4">
						<navi-bar id="navibar" style="height:2000px;overflow: scroll;"></navi-bar>
					</i-col>
					<i-col span="20" class="layout-content-main">
						<transition name="fade">
							<router-view></router-view>
						</transition>
					</i-col>
				</Row>
			</div>
			<div class="layout-copy">
				Created by &copy; minghui.y on 2018.3
			</div>
		</div>
	</div>

</template>

<script>
    import naviBar from './navi-bar.vue';
    import titleBar from './title-bar.vue';

	export default {
		props : {},
		data() {
			return {
				loading : true
			}
		},
		computed : {},
		methods : {},
		mounted : function() {
			$(window).resize(function() {
                $('.layout-content-main').height($(window).height() - 150)
                $('#navibar').height($(window).height() - 150)
			});
			setInterval(function() {
                $('.layout-content-main').height($(window).height() - 150)
                $('.navibar').height($(window).height() - 150)
			}, 1000);
		},
		components : {naviBar, titleBar}
	}
</script>

<style lang="less" ref="stylesheet/less">
	.content-border {
		border: 1px solid #dddee1;
	}

	.mask {
		opacity: .7;
		position: fixed;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background-color: rgba(55,55,55,.6);
		height: 100%;
		z-index: 1000;
	}

	.demo-spin-icon-load {
		-webkit-animation: ani-demo-spin 1s linear infinite;
		animation: ani-demo-spin 1s linear infinite
	}

	@-webkit-keyframes ani-demo-spin {
		0% {
			-webkit-transform: rotate(0deg);
			transform: rotate(0deg)
		}
		50% {
			-webkit-transform: rotate(180deg);
			transform: rotate(180deg)
		}
		to {
			-webkit-transform: rotate(1turn);
			transform: rotate(1turn)
		}
	}

	@keyframes ani-demo-spin {
		0% {
			-webkit-transform: rotate(0deg);
			transform: rotate(0deg)
		}
		50% {
			-webkit-transform: rotate(180deg);
			transform: rotate(180deg)
		}
		to {
			-webkit-transform: rotate(1turn);
			transform: rotate(1turn)
		}
	}

	.loader {
		width: 30px;
		height: 30px;
		position: relative;
		margin: 0 auto;
	}

	.circular {
		-webkit-animation: rotate 2s linear infinite;
		animation: rotate 2s linear infinite;
		height: 100%;
		-webkit-transform-origin: center center;
		transform-origin: center center;
		width: 100%;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		margin: auto;
	}

	.path {
		stroke-dasharray: 1, 200;
		stroke-dashoffset: 0;
		-webkit-animation: dash 1.5s ease-in-out infinite, color 6s ease-in-out infinite;
		animation: dash 1.5s ease-in-out infinite, color 6s ease-in-out infinite;
		stroke-linecap: round;
	}

	@-webkit-keyframes rotate {
		to {
			-webkit-transform: rotate(1turn);
			transform: rotate(1turn)
		}
	}

	@keyframes rotate {
		to {
			-webkit-transform: rotate(1turn);
			transform: rotate(1turn)
		}
	}

	@-webkit-keyframes dash {
		0% {
			stroke-dasharray: 1, 200;
			stroke-dashoffset: 0
		}
		50% {
			stroke-dasharray: 89, 200;
			stroke-dashoffset: -35
		}
		to {
			stroke-dasharray: 89, 200;
			stroke-dashoffset: -124
		}
	}

	@keyframes dash {
		0% {
			stroke-dasharray: 1, 200;
			stroke-dashoffset: 0
		}
		50% {
			stroke-dasharray: 89, 200;
			stroke-dashoffset: -35
		}
		to {
			stroke-dasharray: 89, 200;
			stroke-dashoffset: -124
		}
	}

	@-webkit-keyframes color {
		0%, to {
			stroke: #d62d20
		}
		40% {
			stroke: #0057e7
		}
		66% {
			stroke: #008744
		}
		80%, 90% {
			stroke: #ffa700
		}
	}

	@keyframes color {
		0%, to {
			stroke: #d62d20
		}
		40% {
			stroke: #0057e7
		}
		66% {
			stroke: #008744
		}
		80%, 90% {
			stroke: #ffa700
		}
	}
</style>
