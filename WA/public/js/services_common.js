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
        },
        update: {
          method: 'POST',
          url: function(params) {
            return '';
          }
        }
      },

      cinemas: {

        getAll: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId;
          }
        },
        getDetail: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId + params.cinemaId;
          }
        },
        addMovie: {
          method: 'POST',
          url: function(params) {
            return '' + params.userId + params.cinemaId;
          }
        },
        update: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId + params.cinemaId;
          }
        }
      },

      movies: {

        getAll: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId;
          }
        },
        getbySearchParams: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId;
          }
        },
        getbyCinema: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId;
          }
        },
        getDetail: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId;
          }
        },
        getTrackList: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId;
          }
        },
        addTrack: {
          method: 'POST',
          url: function(params) {
            return '' + params.userId;
          }
        },
        updateDetail: {
          method: 'POST',
          url: function(params) {
            return '' + params.userId;
          }
        },

      tracks: {

        play: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId;
          }
        },
         getQrToken: {
          method: 'GET',
          url: function(params) {
            return '' + params.userId;
          }
        },

      }

      }

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
            return $http.post(reqUrl).success(function (res) {
                    return res.user;
            });
        }

        function Current() {
            return $q.when(getUser()).then(function (res) {
                return  res.data.user || result;
            });

        }

       function update() {
          return $q.when(updateUser()).then(function(user) {
            return user;
          })
        } 
      

        function updateUser() {
          return $http.get('/api/auth/update').success(function(res) {
            console.log('ok');
            return res.data;
         })
        };

        function logout() {
            return $http.post('/api/auth/logout').success(function (res) {
                var defaultAction = true;
                return res && res.data
                    ? res.data : defaultAction;
            });
        }

        return {
            Current: Current,
            logout: logout,
            update: update
        }
    }])

