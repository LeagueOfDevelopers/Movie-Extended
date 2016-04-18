angular.module('app.services', [])

  .service('API', 
    ['$rootScope',
    '$q',
    '$http',
   function($rootScope, $q, $http){ 
      var config = { 
        apiUrls : {
          BASE_URL: 'localhost:3000',

      user: {
        login: {
          method: 'POST',
          url: function(params) {
            return '/api' + '/login';
          }
        },
        getCurrent: {
          method: 'POST',
          url: function(params) {
            return '/api' + '/isAuth';
          }
        },
        logout: {
          method: 'POST',
          url: function(params) {
            return '/api' + '/logout';
          }
        }
      },

    } 
  }

    function query(path, params, log) {

 
      var apiMethod = parsePath(path, this.apiUrls);
      return $q.when(send(apiMethod, params)).then(function(result) {
        if(log) {
          console.log(result);
          }
          return result;

      });

    function parsePath(pathString, obj) {
      var path = pathString.split('.');
      if(Array.isArray(path)) {
        for (var i = 0, l = path.length; i < l; i++) {
          var item = path[i];
          if(obj[item]) {
            obj = obj[item]
          } else {
            return 'parse error';
          }
        } 
        return obj;
      } else {
        return obj[path];
      }
    };

    function send(apiMethod, params) {
      if(log) {
        console.log(apiMethod.url(params));
      }
      return $http({
        method: apiMethod.method,
        url   : this.apiUrls.BASE_URL + apiMethod.url(params),
        data  : params && params.data ? params.data : null
      }).success(function(res) {
        return {
          data: res,
          method: apiMethod
        };
      });
    };

  };

  return {
    query: query.bind(config)
  }


  }])


  .service('avatar',
    [ 
    function() {
    return  function(student) {
       return student.photoUri ? 'background-image: url(' + student.photoUri + ')' : ''; 
      }
  }])

 

  .service('UserManager',
   ['$rootScope',
    '$q',
    '$http',
   function ($rootScope, $q, $http) {
        
        var apiUrl = '/api';

        function getUser() {
          var reqUrl = apiUrl + '/auth/isAuth';
            return $http.post(reqUrl).success(function (data) {
                    return data.user;
            });
        }

        function Current() {
            return $q.when(getUser()).then(function (result) {
                return  result.data.user || result;
            });

        }

       function update() {
        return $q.when(updateUser()).then(function(res) {
          return res;
        })
        } 
      

        function updateUser() {
          return $http.get('/api/auth/update').success(function(result) {
            console.log('ok');
            return result.data;
         })
        };

        function logout() {
            return $http.post('/api/auth/logout').success(function (data) {
                var defaultAction = true;
                return data && data.result
                    ? data.result : defaultAction;
            });
        }

        return {
            Current: Current,
            logout: logout,
            update: update
        }
    }])

