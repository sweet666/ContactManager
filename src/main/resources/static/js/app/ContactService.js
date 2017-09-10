'use strict';

angular.module('app').factory('ContactService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllContacts: loadAllContacts,
                getAllContacts: getAllContacts,
                getContact: getContact,
                createContact: createContact,
                updateContact: updateContact,
                removeContact: removeContact
            };

            return factory;

            function loadAllContacts() {
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API)
                    .then(
                        function (response) {
                            $localStorage.contacts = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading contacts');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllContacts(){
                return $localStorage.contacts;
            }

            function getContact(id) {
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API + id)
                    .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading contact with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createContact(contact) {
                var deferred = $q.defer();
                $http.post(urls.USER_SERVICE_API, contact)
                    .then(
                        function (response) {
                            loadAllContacts();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating contact : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateContact(contact, id) {
                var deferred = $q.defer();
                $http.put(urls.USER_SERVICE_API + id, contact)
                    .then(
                        function (response) {
                            loadAllContacts();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating contact with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeContact(id) {
                var deferred = $q.defer();
                $http.delete(urls.USER_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllContacts();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);