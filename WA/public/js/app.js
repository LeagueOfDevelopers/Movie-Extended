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
        views: {
          'page_content': {
            templateUrl: 'partials/account/account_main.html',
            controller: 'accountMainCtrl'
          },
          'account_content' : {
            templateUrl: 'partials/account/account_empty.html'
          }
        }
       })
     }]);
