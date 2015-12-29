angular.module("extendedMovie.controllers", [])

  .controller("MoviesCtrl", ['$scope', '$http','$rootScope', function($scope, $http) {

    getMovies();
    $scope.showPopup = false;
    $scope.cinema = {};


    $scope.createMovie = function () {
      $scope.showPopup = false;
      var newMovie = {id:3, name: $scope.cinema.name, description: $scope.cinema.description, photoUri: "dnwdjwdwdw"};
        $http.post('/api/movies', newMovie)
        .success(function (data) {
          getMovies();
            console.log(data);
        })
      };

       function getMovies() {
      $http.get('/api/movies').success(function (data) {
        $scope.movies = data;
      })
    }
}]);
