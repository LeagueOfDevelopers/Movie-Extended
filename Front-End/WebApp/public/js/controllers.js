angular.module('MovieExtended.controllers.profile', [])

  .controller('MoviesCtrl', ['$scope', '$http','$rootScope', function($scope, $http) {

    
    getMovies('/api/movies');
    $scope.showPopup = false;
    $scope.newMovie = {};
    $scope.tracksToAdd = {};
    
    $scope.createMovie = function () {
      $scope.showPopup = false;
      var newMovie = {id:3, name: $scope.newMovie.name, description: $scope.newMovie.description, photoUri: $scope.newMovie.photoUri};
        $http.post('/api/movies', newMovie)
        .success(function (data) {
          getMovies('/api/movies');
            console.log(data);
            console.log(newMovie);
            $scope.newMovie = {};
        });
        
      };

       function getMovies(url) {
      $http.get(url).success(function (data) {
        $scope.movies = data;
      })
    }
}])


.controller('CinemasCtrl', ['$scope', '$http','$rootScope', function($scope, $http) {

  $scope.cinemas = {};
  
  getCinemas('/api/movies');


  function getCinemas(url) {
     $http.get(url).success(function (data) {
        $scope.cinemas = data;
      })
    }
  }]);


angular.module('MovieExtended.controllers.main', [])

.controller('RegistryCtrl', ['$scope', '$http','$rootScope', function($scope, $http) {

  $scope.newCompany = {};

  $scope.createCompany = function() {
    $http.post('/api/movies', newCompany)
      .success(function(data) {
        console.log('Company created');
      })
  }
  }]);

