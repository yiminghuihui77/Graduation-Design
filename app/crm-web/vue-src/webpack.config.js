/*
const path = require('path');
const webpack = require('webpack');

module.exports = {
  //页面入口文件
  entry: {
    app : './src/main.js',
    login : './src/login.js'
  },
  //入口文件输出设置
  output: {
    path : path.resolve(__dirname, '../src/main/resources/public'),
    publicPath : '/',
    filename: '[name].js?[chunkhash]'  //输出文件名  [name].[hash].js
  },
  module : {
    rules : [
      {
        test : /\.vue$/,
        loader : 'vue-loader',
        // options : {
        // loaders : {
        // 	// Since sass-loader (weirdly) has SCSS as its default parse mode, we map
        // 	// the "scss" and "sass" values for the lang attribute to the right configs here.
        // 	// other preprocessors should work out of the box, no loader config like this necessary.
        // 	scss : 'vue-style-loader!css-loader!sass-loader',
        // 	sass : 'vue-style-loader!css-loader!sass-loader?indentedSyntax',
        // 	less : 'vue-style-loader!css-loader!less-loader'
        // }
        // other vue-loader options go here
        // }
      },
      {
        test : /\.js$/,
        loader : 'babel-loader',
        exclude : /node_modules/
      },
      {
        test : /\.(png|jpg|gif|svg|pdf)$/,
        loader : 'file-loader',
        options : {
          name : '[name].[ext]?[hash]'
        }
      },{
        test: /\.css$/,
        loader: 'style-loader!css-loader'
      }, {
        test: /\.(eot|svg|ttf|woff|woff2)$/,
        loader: 'file-loader'
      }
    ]
  },
  resolve : {
    alias : {
      vue$ : 'vue/dist/vue.common.js'
    }
  },
  plugins: [
    new webpack.optimize.CommonsChunkPlugin({
      name: 'vendor',
      minChunks: function (module, count) {
        return (
          module.resource &&
          (/\.js$/.test(module.resource) || /\.vue$/.test(module.resource)) &&
          (
            module.resource.indexOf(path.join(__dirname, './node_modules')) === 0
          )
        )
      }
    })
  ],
  performance : {
    hints : false
  }

};
*/

var HtmlWebpackPlugin = require('html-webpack-plugin');
module.exports = {
// 页面入口文件配置
  entry: {
    index : './src/main.js'
  },
// 入口文件输出配置
  output: {
    path: './dist', // 输出目录
    filename: '[name].[hash].js' // 设置输出文件名字，此例中为入口文件名字加上hash 值。使用 hash 值的原因是生成新文件后避免缓存导致用户没有更新到新的 js 文件
},
module: {
// 加载器配置
// 加载器会把 test 所匹配的文件加入 loader 进行处理
// 例如下面的 babel，起到的作用就是将匹配到的 js 文件中下一代的 JavaScript（即使用 ES2015 、 es6 等特性的 JavaScript）编译成能在当前浏览器环境下运行的 js 代码
  loaders: [
    {
      test: /\.js$/, // test 即为匹配规则，此处即为将所有后缀为 .js 的文件加载进来
      loader: 'babel', // loader 即为处理器，所有符合规则的文件会交由 loader进行处理
      exclude: /node_modules/ //
},
// vue-loader 是对于 .vue 文件专门的处理器，能将 .vue 文件中的模板、样式、 js代码解析并编译成可执行的代码
  {
    test: /\.vue$/,
      loader: 'vue'
  },
]
},
// plugins 为 webpack 的插件功能，可利用一些第三方插件完成一些额外的操作
// 例如 HtmlWebpackPlugin，这个插件可以帮助生成 HTML 文件，在 body 元素中使用script 来引用 output 中最后输出的 js 文件
  plugins : [
    new HtmlWebpackPlugin({
      filename: 'index.html',
      template: 'index.html',
      inject: true
    })
  ]
};
