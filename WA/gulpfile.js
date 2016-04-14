var gulp = require('gulp');
var concat = require('gulp-concat');
var autoprefixer = require('gulp-autoprefixer');
var sass = require('gulp-sass');

gulp.task('styles', function () {
	return gulp.src('public/styles/*.scss')
		.pipe(sass().on('error', sass.logError))
		.pipe(gulp.dest('public/styles/'));
});


gulp.task('scripts', function() {
  return gulp.src('public/js/*.js')
    .pipe(concat('build.js'))
    .pipe(gulp.dest('public/js/build/'));
});



//Watch task
gulp.task('default', function() {
    gulp.watch('public/styles/main.scss',['styles']);
    gulp.watch('public/js/*.js',['scripts']);
});