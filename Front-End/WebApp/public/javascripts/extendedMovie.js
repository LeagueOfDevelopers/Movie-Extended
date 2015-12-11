angular.module("extendedMovie",[])
  .controller("MoviesCtrl", [$scope, function($scope) {
    $scope.movies = [{
        Id: 1,
        Name: 'starwars',
        description: 'Loremdwdwdwdwdwdwdwdw',
        PhotoUri: 'https://www.npmjs.com/static/images/npm-logo.svg'
    },
    {
        Id: 2,
        Name: 'Пять звезд',
        description: 'loremdkwokdkdwd',
        PhotoUri: 'https://www.npmjs.com/static/images/npm-logo.svg'
    }];
  }]);
