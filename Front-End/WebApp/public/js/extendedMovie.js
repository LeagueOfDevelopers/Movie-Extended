 var MovieExtended= {
    Main: angular.module("MovieExtended.Main", ['ui.router', 'extendedMovie.controllers']),
    Profile: angular.module("MovieExtended.Profile", ['ui.router', 'extendedMovie.controllers'])
  };

  MovieExtended.Profile.config(["$stateProvider","$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
  
    $stateProvider.state("movies", {
      url: "/movies",
      templateUrl: "/partials/profile_movies.html",
      controller: "MoviesCtrl"
    })
        .state("movies.create_movie", {
          url:"/movies",
          templateUrl: "/partials/create_movie.html"
        })
        .state("movies.about_movie", {
          url:"/movies",
          templateUrl: "/partials/about_movie.html"
        })
      .state('cinemas', {
            url: "/cinemas",
            templateUrl: "/partials/profile_cinemas.html",
            controller: "MoviesCtrl"

          });
      $urlRouterProvider.otherwise("/movies");

}]);
