 var extendedMovie = angular.module("extendedMovie", ['ui.router', 'extendedMovie.controllers'])

.config(["$stateProvider","$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
  $stateProvider.state("movies", {
    url: "/movies",
    templateUrl: "/partials/profile_movies.html",
    controller: "MoviesCtrl"
  })
    .state('cinemas', {
          url: "/cinemas",
          templateUrl: "/partials/profile_cinemas.html",
          controller: "MoviesCtrl"

        });
    $urlRouterProvider.otherwise("/movies");

}]);
