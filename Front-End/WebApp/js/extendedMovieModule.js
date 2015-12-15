
angular.module("extendedMovie", ['ngRoute'])

  .config(['$routeProvider','$urlRouterProvider', function($routeProvider, $urlRouterProvider) {
    $urlRouterProvider.html5Mode(true);
    $routeProvider.when('/profile', {
      templateUrl: '../partials/profile_movies.html',
      controller: 'AppCtrl'
    })

  }])
  .controller('AppCtrl', [$scope, function($scope) {
     $scope.title = "djwdjw";
  }]);
