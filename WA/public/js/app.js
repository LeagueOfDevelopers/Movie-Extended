angular.module('meApp',
 [
   'ui.router',
   'app.commonCtrl',
   'app.auth',
   'app.account',
   'app.services',
   'ngSanitize',
   'ngFileUpload'
 ])

  .config(
    [
      '$urlRouterProvider',
      '$stateProvider',
      '$locationProvider',
     function ($urlRouterProvider, $stateProvider, $locationProvider) {
       $urlRouterProvider.otherwise("/auth");
       //$locationProvider.html5Mode(true);

       $stateProvider

       .state('auth', {
          url: '/auth',
           views: {
             'account_content': {
               templateUrl: 'partials/auth.html',
               controller: 'authCtrl'
             }
           }
       })

       .state('cinemas', {
        url: '/cinemas',
        views: {
          'account_content': {
            templateUrl: 'partials/account/account_cinemas.html'
          }
        }
       })
       .state('movies', {
           url: '/movies',
           views: {
               'account_content': {
                   templateUrl: 'partials/account/account_movies.html'
               }
           }
       })

       .state('cinema', {
        url: '/cinema',
        views: {
          'account_content@' : {
            templateUrl: 'partials/account/cinema_look.html'
          }
        }
       })


       .state('movie', {
        url: '/movie',
        views: {
          'account_content' : {
            templateUrl: 'partials/account/movie_look.html'
          }
        }
       })

        .state('addmovie', {
         url: '/addmovie',
         views: {
           'account_content' : {
             templateUrl: 'partials/account/movie_add.html'
            }
         }
        })

     }]);
