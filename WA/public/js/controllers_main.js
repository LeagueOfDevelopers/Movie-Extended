angular.module('app.commonCtrl',
 [
   'ui.router'
 ])

.controller('appCtrl',
  ['$scope',
   '$state',
   'UserManager',
   '$timeout',
  function ($scope, $state, UserManager, $timeout) {
    
     $scope.BASE_URI = 'http://localhost:3000/';
     $scope.title = 'Movie Extended';
     $scope.$on('changeTitle', function(e, args) {
     $scope.title = args.title;
     })
     $scope.showMessage = false;
     $scope.msg = '';
     $scope.currentUser = {
      id: '12345'
     };
      auth();

      
      function auth() {
          UserManager.Current().then(function (result) {
            if(!result) {
              $scope.currentUser = null;
            } else {
              $scope.currentUser = result;
            }
        });
      };



      $scope.$on('auth', function (e, args) {
           auth();
           $scope.$broadcast('userUpdated');
      })     

      $scope.$on('userUpdate', function (e, args) {
           UserManager.update().then(function() {
            $scope.$emit('auth');
           })
      })    

    $scope.logout = function() {
        UserManager.logout();
        $scope.$emit('userUpdate');
        $state.go('mainPage');
      };

    }])

.controller('pageCtrl',
  ['$scope',
   '$state',
   '$http',
   'UserManager',
  function ($scope, $state, $http) {

      $scope.showMobileMenu = false;
      angular.element(document.querySelector('.mobile_nav_bar_background')).css('visibility', 'visible');
      $scope.$on('needAuth', function (e, args) {
           if(!$scope.currentUser) {
            $state.go('auth');
            console.log('needAuth')
           }
      })

}]);