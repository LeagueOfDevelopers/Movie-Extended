angular.module('app.auth', 
[
 'ui.router'
])

.controller('authCtrl', 
 [
  '$scope',
 function($scope) {
 	$scope.submit = function() {
 		if($scope.auth.email == 'jj' && $scope.auth.password == '111' ) {
 			$scope.$emit('auth');
 		}
 	}
 }
 ])