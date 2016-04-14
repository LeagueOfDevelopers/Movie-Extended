angular.module('meApp',
 [
   'ui.router',
   'app.commonCtrl',
   'app.auth',
   'app.services',
   'app.directives',
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
       $locationProvider.html5Mode(true);

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
            templateUrl: 'partials/account.html',
            controller: 'accountMainCtrl'
          }
        }
       })
     }]);
