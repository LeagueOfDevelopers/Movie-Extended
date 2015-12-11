angular.module("extendedMovie",['ng','ngRoute'])
  .controller("MoviesCtrl", ['$scope', '$http', function($scope, $http) {

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

    $http.get('/getsome').success(function(data) {
      $scope.some = data;
      console.log(data);
    })
  }]);
