angular.module("extendedMovie.controllers", [])

  .controller("MoviesCtrl", ['$scope', '$http', function($scope, $http) {

    getMovies();
    $scope.showPopup = false;


    $scope.addNewMovie = function () {
      var newMovie = {id:3, name: "dnwndw", description: "dnwndjwd2d2", photoUri: "dnwdjwdwdw"};
        $http.post('/api/movies', newMovie)
        .success(function (data) {
            $scope.movies.push(newMovie);
            console.log(data);
        })
      };

       function getMovies() {
      $http.get('/api/movies').success(function (data) {
        $scope.movies = data;
      })
    }
}]);

 var extendedMovie = angular.module("extendedMovie", ['ui.router', 'extendedMovie.controllers'])

.config(["$stateProvider","$urlRouterProvider", function ($stateProvider, $urlRouterProvider) {
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
