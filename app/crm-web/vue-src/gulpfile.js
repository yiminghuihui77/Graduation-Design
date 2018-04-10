const gulp = require('gulp');
const webpack = require('webpack');
const gulpWebpack = require('webpack-stream');
const cssmin = require('gulp-minify-css');
const less = require('gulp-less');

let wpconfig = require('./webpack.config');
const browserSync =require('browser-sync');

gulp.task('less-dev', function() {
  gulp.src('./less/*.less').pipe(less()).pipe(gulp.dest('../target/classes/static'));
});

gulp.task('less', function() {
  gulp.src('./less/*.less').pipe(less()).pipe(cssmin()).pipe(gulp.dest('../src/main/resources/static'));
});

gulp.task('default', ['less'], function() {
  wpconfig.plugins = (wpconfig.plugins || []).concat([
    new webpack.DefinePlugin({
      'process.env' : {
        NODE_ENV : '"production"'
      }
    }),
    new webpack.optimize.UglifyJsPlugin({
      sourceMap : true,
      compress : {
        warnings : false
      },
      output: {
        comments: false
      }
    }),
    new webpack.LoaderOptionsPlugin({
      minimize : true
    })
  ]);

  gulp.src('.').pipe(gulpWebpack(wpconfig, webpack)).pipe(gulp.dest('../src/main/resources/public'));
});

gulp.task('live', function() {
  wpconfig.watch = true;
  gulp.src('.').pipe(gulpWebpack(wpconfig, webpack)).pipe(gulp.dest('../target/classes/public'));
  gulp.src('./less/*.less').pipe(less()).pipe(gulp.dest('../target/classes/static'));

  gulp.watch('./less/*.less', ['less-dev']);

  //start proxy server
  browserSync.create().init({
    proxy : 'http://localhost:8090',
    logLevel : 'silent',
    files: [require('path').join(__dirname, '../target/classes/public/**'), require('path').join(__dirname, '../target/classes/static/**')],
    open : false,
    online: true,
    notify : false,
    ui : false
  }, function(e, bs) {
    console.log(bs.getOption('urls'));
  });
});
//sudo browser-sync start --proxy "localhost:8080" --files "/Users/Val/IdeaProjects/authcoll/dtauthcollweb/config/target/root/static/prod-pages/**"
