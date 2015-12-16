angular.module("extendedMovie.controllers", [])

  .controller("MoviesCtrl", ['$scope', '$http', function($scope, $http) {
    
    getMovies();


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
