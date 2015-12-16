var gulp = require('gulp');
var sass = require('gulp-sass');
var concat = require('gulp-concat');

gulp.task('styles', function() {
    gulp.src('sass/**/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest('public/stylesheets/'))
});
gulp.task('scripts', function() {
  return gulp.src('public/js/*.js')
    .pipe(concat('build.js'))
    .pipe(gulp.dest('public/javascripts/'));
});

//Watch task
gulp.task('default', function() {
    gulp.watch('sass/*',['styles']);
    gulp.watch('public/js/*.js',['scripts']);
});


