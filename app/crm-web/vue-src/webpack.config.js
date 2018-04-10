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
