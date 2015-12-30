 var MovieExtended= {
    Main: angular.module('MovieExtended.Main', ['ui.router', 'MovieExtended.controllers.main']),
    Profile: angular.module('MovieExtended.Profile', ['ui.router', 'MovieExtended.controllers.profile'])
  };

MovieExtended.Profile.config(['$stateProvider','$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('movies', {
        url: '/movies',
        templateUrl: '/partials/profile/profile_movies.html',
        controller: 'MoviesCtrl'
      })
        .state('movies.create_movie', {
            url:'/create_movie',
            templateUrl: '/partials/profile/create_movie.html'
          })
        .state('movies.about_movie', {
            url:'/about_movie',
            templateUrl: '/partials/profile/about_movie.html'
          })
        .state('cinemas', {
              url: '/cinemas',
              templateUrl: '/partials/profile/profile_cinemas.html',
              controller: 'CinemasCtrl'

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