var gulp = require("gulp");
//gulp-uglify插件具有压缩功能
var uglify = require("gulp-uglify");

//task1:蒋指定文件压缩后输出到本地文件
gulp.task('default', function () {
    gulp.src('/js/login/auth.js')
      .pipe(uglify())  //压缩源码
      .pipe(gulp.dest('/js/login/first')) //输出到本地文件
  console.log('firstr任务完成...');
});
