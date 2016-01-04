angular.module('MovieExtended.controllers.profile', ['ui.router'])

  .controller('MoviesCtrl', ['$scope', '$http','$state', function($scope, $http, $state) {

    
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

        function showMovieDetail(id, name) {
          $state.go('movies.detail', {movieId: id, movieName: name});
    };

       function getMovies(url) {
      $http.get(url).success(function (data) {
        $scope.movies = data;
      })
    }
}])


.controller('CinemasCtrl', ['$scope', '$http','$state', function($scope, $http, $state) {
  var apiUrl = '/api/cinemas';
  $scope.cinemas = {};
  $scope.showCinemaDetail = showCinemaDetail;
  
  getCinemas(apiUrl);

  function showCinemaDetail(id, name) {
    $state.go('cinemas.detail', {cinemaId: id, cinemaName: name});
  }

  function getCinemas(url) {
     $http.get(url).success(function (data) {
        $scope.cinemas = data;
      })
    }
  }])
  
  .controller('cinemaDetailCtrl', ['$scope', '$http','$state','cinemaToShowResolve', function($scope, $http, $state, cinemaToShowResolve) {
    $scope.cinema = cinemaToShowResolve.data;
    console.log(cinemaToShowResolve);
    var apiUrl = '/api/cinemas';
    
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

