'use strict';

angular.module('app').controller('ContactController',
    ['ContactService', '$scope',  function( ContactService, $scope) {

        var self = this;
        self.contact = {};
        self.contacts=[];

        self.submit = submit;
        self.getAllContacts = getAllContacts;
        self.createContact = createContact;
        self.updateContact = updateContact;
        self.removeContact = removeContact;
        self.editContact = editContact;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            if (self.contact.id === undefined || self.contact.id === null) {
                createContact(self.contact);
            } else {
                updateContact(self.contact, self.contact.id);
            }
        }

        function createContact(contact) {
            ContactService.createContact(contact)
                .then(
                    function (response) {
                        self.successMessage = 'Contact created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.contact={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        self.errorMessage = 'Error: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateContact(contact, id){
            ContactService.updateContact(contact, id)
                .then(
                    function (response){
                        self.successMessage='Contact updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        self.errorMessage='Error: '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeContact(id){
            ContactService.removeContact(id)
                .then(
                    function(){
                        console.log('Contact '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Contact '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllContacts(){
            return ContactService.getAllContacts();
        }

        function editContact(id) {
            self.successMessage='';
            self.errorMessage='';
            ContactService.getContact(id).then(
                function (contact) {
                    self.contact = contact;
                },
                function (errResponse) {
                    console.error('Contact ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.contact={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);