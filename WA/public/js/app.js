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
             'page_content': {
               templateUrl: 'partials/auth.html',
               controller: 'authCtrl'
             }
           }
       })

       .state('cinemas', {
        url: '/cinemas',
        views: {
          'page_content': {
            templateUrl: 'partials/account/account_cinemas.html',
            controller: 'accountCinemasCtrl'
          }
        }
       })
           .state('movies', {
               url: '/movies',
               views: {
                   'page_content': {
                       templateUrl: 'partials/account/account_movies.html',
                       controller: 'accountCinemasCtrl'
                   }
               }
           })


       .state('account', {
        url: '/account/',
        abstract: true,
        template: '</ui-view>'
       })

       .state('account.cinemas', {
        url: '/account/cinemas',
        views: {
          'page_content': {
            templateUrl: 'partials/account/account_main.html'

          },
          'account_content' : {
            templateUrl: 'partials/account/cinema_look.html'
          }
        }
       })

       .state('account.movies', {
        url: '/account/movies',
        views: {
          'page_content': {
            templateUrl: 'partials/account/movie_look.html',
            controller: 'accountMainCtrl'
          },
          'account_content' : {
            templateUrl: 'partials/account/account_movies.html'
          }
        }
       })


     }]);
