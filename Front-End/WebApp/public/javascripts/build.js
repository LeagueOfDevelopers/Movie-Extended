angular.module('MovieExtended.controllers.profile', ['ui.router'])
  
  .controller('MoviesCtrl', 
    ['$scope',
     '$rootScope',
     '$http',
     '$state',

     function($scope, $rootScope, $http, $state) {

    $rootScope.showPopup = false;
    getMovies('/api/movies');
    $scope.newMovie = {};
    $scope.tracksToAdd = {};
    $scope.showMovieDetail = showMovieDetail;
    
    $scope.createMovie = function () {
      $rootScope.showPopup = false;
      var newMovie = {id:3, name: $scope.newMovie.name, description: $scope.newMovie.description, photoUri: $scope.newMovie.photoUri};
        $http.post('/api/movies', newMovie)
        .success(function (data) {
          getMovies('/api/movies');
            console.log(data);
            console.log(newMovie);
            $scope.newMovie = {};
            $state.go('movies');
        });
        
      };

        function showMovieDetail(id, name) {
          $state.go('movies.detail', {movieId: id, movieName: name});
          $rootScope.showPopup = true;
    };

       function getMovies(url) {
      $http.get(url).success(function (data) {
        $scope.movies = data;
      })
    }
}])

.controller('movieDetailCtrl',
 ['$scope',
  '$http',
  '$state',
  'movieToShowResolve', 

  function($scope, $http, $state, movieToShowResolve) {

    $scope.movie = movieToShowResolve.data;
  
  }])

.controller('moviesHeaderCtrl', 
  ['$scope',
   '$rootScope', 
   '$state',

  function($scope, $rootScope, $state){

    $scope.title = 'Фильмы';
    $scope.showAddButton = true;

    $scope.createItem = function() {
      $rootScope.showPopup = true;
      $state.go('movies.create_movie');
    }
  
}])


.controller('CinemasCtrl', 
  ['$scope', 
  '$http',
  '$state', 

  function($scope, $http, $state) {

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
  
  .controller('cinemaDetailCtrl', 
    ['$scope',
     '$rootScope',
     '$http',
     '$state',
     'cinemaToShowResolve',

      function($scope, $rootScope, $http, $state, cinemaToShowResolve) {
    $rootScope.showAddButton = false;
    $scope.cinema = cinemaToShowResolve.data;
    console.log(cinemaToShowResolve);
    var apiUrl = '/api/cinemas';
    
  }])

  .controller('cinemasHeaderCtrl', 
    ['$scope',
     '$rootScope',
     '$state', 

      function($scope, $rootScope, $state){
        $rootScope.showAddButton = true;
        $scope.title = 'Кинотеатры';
        $rootScope.showAddButton;
  }]);


angular.module('MovieExtended.controllers.main', [])

.controller('RegistryCtrl',
 ['$scope',
  '$http',
  '$rootScope', 

  function($scope, $http) {

  $scope.newCompany = {};

  $scope.createCompany = function() {
    $http.post('/api/movies', newCompany)
      .success(function(data) {
        console.log('Company created');
      })
  }
  }]);


 var MovieExtended= {
    Main: angular.module('MovieExtended.Main', ['ui.router', 'MovieExtended.controllers.main']),
    Profile: angular.module('MovieExtended.Profile', ['ui.router', 'MovieExtended.controllers.profile'])
  };

MovieExtended.Profile.config(['$stateProvider','$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('movies', {
        url: '/movies',
         views: {
                'header@': {
                  templateUrl:'/partials/profile/profile_header.html',
                  controller:'moviesHeaderCtrl'
                },
                'content@': {
                    templateUrl: '/partials/profile/profile_movies.html',
                    controller: 'MoviesCtrl'
                }
              }
      })
        .state('movies.create_movie', {
            url:'/create_movie',
            views: {
              'popup': {
                templateUrl: '/partials/profile/create_movie.html'
              }
            }
          })
        .state('movies.detail', {
            url:'/:movieId',
            views: {
              'popup': {
                templateUrl: '/partials/profile/about_movie.html',
                controller: 'movieDetailCtrl',
                resolve: {
                      movieToShowResolve: function ($http, $stateParams) {
                          var url = '/api/movies' + '/' + $stateParams.movieId;
                          return $http.get(url);
                          
                        }  
                      }
                    }
              }
            
          })
        .state('cinemas', {
              url: '/cinemas',
              views: {
                'header@': {
                  templateUrl:'/partials/profile/profile_header.html',
                  controller:'cinemasHeaderCtrl'
                },
                'content@': {
                    templateUrl: '/partials/profile/cinemas/cinemas_list.html',
                    controller: 'CinemasCtrl'
                }
              }
            })
        .state('cinemas.detail', {
          url:'/:cinemaName/:cinemaId',
          views: {
                'content@': {
                    templateUrl: '/partials/profile/cinemas/cinema_detail.html',
                    controller: 'cinemaDetailCtrl',
                    resolve: {
                      cinemaToShowResolve: function ($http, $stateParams) {
                          var url = '/api/cinemas' + '/' + $stateParams.cinemaId;
                          return $http.get(url);
                        }  
                      }
                    }
                }
              
        });
        $urlRouterProvider.otherwise('/movies');
        

    }]);
 

 MovieExtended.Main.config(['$stateProvider','$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('registry', {
        url: '/stage_1_registry',
        templateUrl: '/partials/registry/registry_form.html',
        controller: 'RegistryCtrl'
      })
      

        $urlRouterProvider.otherwise('/stage_1_registry');

    }]);