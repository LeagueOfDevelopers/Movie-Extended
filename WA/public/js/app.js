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

       .state('account', {
        url: '/account/:id',
        abstract: true,
        template: '</ui-view>'
       })

       .state('account.cinemas', {
        url: '/account/:id/cinemas',
        views: {
          'page_content': {
            templateUrl: 'partials/account/account_main.html',
            controller: 'accountMainCtrl'
          },
          'account_content' : {
            templateUrl: 'partials/account/account_cinemas.html'
          }
        }
       })

       .state('account.movies', {
        url: '/account/:id/movies',
        views: {
          'page_content': {
            templateUrl: 'partials/account/account_main.html',
            controller: 'accountMainCtrl'
          },
          'account_content' : {
            templateUrl: 'partials/account/account_movies.html'
          }
        }
       })
     }]);
