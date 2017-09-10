var app = angular.module('app',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/',
    USER_SERVICE_API : 'http://localhost:8080/contactmanager/api/contact/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'ContactController',
                controllerAs:'ctrl',
                resolve: {
                    users: function ($q, ContactService) {
                        var deferred = $q.defer();
                        ContactService.loadAllContacts().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

