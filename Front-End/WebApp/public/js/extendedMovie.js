 var extendedMovie = angular.module("extendedMovie", ['ngRoute', 'extendedMovie.controllers'])

.config(["$routeProvider", function ($routeProvider) {
  $routeProvider.when("/movies", {
    templateUrl: "/partials/profile_movies.html"
  })
    .when('/cinemas', {
          templateUrl: "/partials/profile_cinemas.html",
          controller: "MoviesCtrl"

          })

}]);
