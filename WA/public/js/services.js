angular.module('app.services', [])

  .service('API', 
    ['$rootScope',
    '$q',
    '$http',
   function($rootScope, $q, $http){
      var config = {    
        apiUrls : {} 
      }

    function parsePath(pathString, obj) {
      var path = pathString.split('.');
      if(Array.isArray(path)) {
        for (var i = 0, l = path.length; i < l; i++) {
          var item = path[i];
          if(obj[item]) {
            obj = obj[item]
          } else {
            return
          }
        } 
        return obj;
      } else {
        return obj[path];
      }
    };

    function query(path, params, log) {

      var apiMethod = parsePath(path, this.apiUrls);
      return $q.when(send(apiMethod, params)).then(function(result) {
        if(log) {
          console.log(result);
          }
          return result;

      });

      function send(apiMethod, params) {
        if(log) {
          console.log(apiMethod.url(params));
        }
        return $http({
          method: apiMethod.method,
          url   : apiMethod.url(params),
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


  .service('UserManager',
   ['$rootScope',
    '$q',
    '$http',
   function ($rootScope, $q, $http) {
        
        var apiUrl = '/api';
        var testUser = {
          id: '123451',
          firstName: 'Ilya',
          lastName: 'Zubkov',
          company: 'LoD'

        }

            function getUser() {
              var reqUrl = apiUrl + '/auth/isAuth';
                // return $http.post(reqUrl).success(function (data) {
                //         return data.user;
                // });
                return testUser;
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
         //  return $http.get('/api/auth/update').success(function(result) {
         //    console.log('ok');
         //    return result.data;
         // })
         return testUser;
        };

        function logout() {
            // return $http.post('/api/auth/logout').success(function (data) {
            //     var defaultAction = true;
            //     return data && data.result
            //         ? data.result : defaultAction;
            // });
            return null;
        }

        return {
            Current: Current,
            logout: logout,
           update: update
        }
    }])

