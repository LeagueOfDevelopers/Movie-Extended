angular.module('app.account',
    [
        'ui.router'
    ])

    .controller('accountMainCtrl',
        ['$scope',
            'API',
            function ($scope, API) {
                $scope.company = $scope.currentUser.company;
                $scope.breadCrumbs = {};
                $scope.on('crumbsRefresh', function (e, args) {
                    $scope.breadCrumbs.push(args.crumb);
                })
            }
        ])

    .controller('accountMoviesCtrl',
        ['$scope',
            'API',
            '$state',
            function ($scope, API, $state) {

                $scope.moviesList = [];

                API.query('movies.getAll', {companyId: $scope.company.id}).then(function (res) {
                    $scope.moviesList = res;
                });

                $scope.addMovie = function () {
                    $state.go('account.movies.add');
                };

                $scope.showMovieDetail = function (id) {
                    $state.go('account.movies.detail', {movieId: id});
                };
            }
        ])


    .controller('accountCinemasCtrl',
        ['$scope',
            '$state',
            'API',
            function ($scope, $state, API) {

                $scope.cinemaList = [];

                $scope.addCinema = function () {
                }
            }
        ])


    .controller('accountMovieDetailCtrl',
        ['$scope', 
            '$state',
            'API',
            function ($scope, API) {

            }
        ])


    .controller('accountCinemaDetailCtrl',
        ['$scope',
            'API',
            function ($scope, API) {

            }
        ])


    .controller('accountMovieAddCtrl',
        ['$scope',
            'API',
            function ($scope, API) {

            }
        ])

    .controller('accountCinemaAddCtrl',
        ['$scope',
            'API',
            function ($scope, API) {

            }
        ])

