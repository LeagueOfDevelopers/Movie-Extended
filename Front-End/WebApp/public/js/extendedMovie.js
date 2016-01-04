 var MovieExtended= {
    Main: angular.module('MovieExtended.Main', ['ui.router', 'MovieExtended.controllers.main']),
    Profile: angular.module('MovieExtended.Profile', ['ui.router', 'MovieExtended.controllers.profile'])
  };

MovieExtended.Profile.config(['$stateProvider','$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('movies', {
        url: '/movies',
         views: {
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
            url:'/:movieName/:movieId',
            views: {
              'popup': {
                templateUrl: '/partials/profile/about_movie.html'
              }
            }
          })
        .state('cinemas', {
              url: '/cinemas',
              views: {
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