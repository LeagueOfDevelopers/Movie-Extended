angular.module("extendedMovie.controllers", [])

  .controller("MoviesCtrl", ['$scope', '$http', function($scope, $http) {
      $scope.hey = "heys";
      $scope.movies = [{
        Id: 1,
        name: 'starwars',
        description: 'Loremdwdwdwdwdwdwdwdw',
        photoUri: 'https://www.npmjs.com/static/images/npm-logo.svg'
    },
    {
        Id: 2,
        name: 'Пять звезд',
        description: 'loremdkwokdkdwd',
        photoUri: 'https://www.npmjs.com/static/images/npm-logo.svg'
    }];

    $scope.arr = [1,3,4,6];
}]);
