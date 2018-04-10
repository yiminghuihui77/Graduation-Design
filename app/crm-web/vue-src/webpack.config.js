const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');
const webpack = require('webpack');

module.exports = {
  //页面入口文件
  entry: {
    app : './main.js',
    login : './login.js'
  },
  //入口文件输出设置
  output: {
    // path: __dirname + './js',  //输出目录
    path : path.resolve(__dirname, '/dist'),
    filename: 'bundle.js'  //输出文件名  [name].[hash].js
  },
  module : {
    loaders: [
      {
        test : /\.js$/,
        loader: 'babel',
        exclude: /node_modules/
      },
      {
        test : /\.vue$/,
        loader: 'vue'
      }
    ],
    rules: [
      {
        test: /\.less$/,
        use: [{
          loader: "style-loader" // creates style nodes from JS strings
        }, {
          loader: "css-loader" // translates CSS into CommonJS
        }, {
          loader: "less-loader" // compiles Less to CSS
        }]
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      filename : 'index.html',
      template : 'index.html',
      inject : true
    })
  ]

};
