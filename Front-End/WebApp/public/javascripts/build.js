angular.module("extendedMovie.controllers", [])

  .controller("MoviesCtrl", ['$scope', '$http', function($scope, $http) {

    getMovies();

    $scope.addNewMovie = function () {
      var newMovie = {id:3, name: "dnwndw", description: "dnwndjwd2d2", photoUri: "dnwdjwdwdw"};
        $http.post('/api/movies', newMovie)
        .success(function (data) {
            getMovies();
            console.log(data);
        })
      }

       function getMovies() {

      $http.get('/api/movies').success(function (data) {
        $scope.movies = data;
      })
    }
}]); 

 var extendedMovie = angular.module("extendedMovie", ['ngRoute', 'extendedMovie.controllers'])

.config(["$routeProvider", function ($routeProvider) {
  $routeProvider.when("/movies", {
    templateUrl: "/partials/profile_movies.html",
    controller: "MoviesCtrl"
  })
    .when('/cinemas', {
          templateUrl: "/partials/profile_cinemas.html",
          controller: "MoviesCtrl"

          })

}]);

