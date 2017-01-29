(function(){
    'use strict';

    console.log('Inside UserService.js');
    
    angular
        .module('MainApp')
        .factory('UserService', UserService)

    /** @ngInject */
    function UserService($http, $rootScope){
        var BASE_URL = 'http://localhost:9080/collude-restbackend';
        return {
            getAllUsers: getAllUsers,
            getAllUsersExceptLoggedIn: getAllUsersExceptLoggedIn,
            getUserById: getUserById,
            getUserByUsername: getUserByUsername,
            create: create,
            update: update,
            remove: remove
        };

        function getAllUsers(){
        	console.log('Inside UserService::getAllUsers()....');
            return $http.get(BASE_URL + '/user/all/').then(successHandler, errorHandler('Error Getting All Users!'));
        }

        function getAllUsersExceptLoggedIn() {
        	console.log('Inside UserService::getAllUsersExceptLoggedIn()....');
            return $http.get(BASE_URL + '/user/others/').then(successHandler, errorHandler('Error Getting All Users Except Currently Logged In!'));
        }

        function getUserById(userId) {
        	console.log('Inside UserService::getUserById()....');
            return $http.get(BASE_URL + '/user/id/' + userId).then(successHandler, errorHandler('Error Getting User with Id: ' + userId));
        }

        function getUserByUsername(username) {
        	console.log('Inside UserService::getUserByUsername()....');
            return $http.get(BASE_URL + '/user/username/' + username).then(successHandler, errorHandler('Error Getting User with username: ' + username));
        }

        function create(user) {
        	console.log('Inside UserService::create()....');
            return $http.post(BASE_URL + '/user/', user).then(createSuccessHandler, errorHandler('Error Creating User!!!'));
        }

        function update(user) {
        	console.log('Inside UserService::update()....');
            return $http.put(BASE_URL + '/user/' + user.userId, user).then(successHandler, errorHandler('Error Updating User with userId: ' + user.userId));
        }

        function remove(userId) {
        	console.log('Inside UserService::remove()....');
            return $http.delete(BASE_URL + '/user/' + userId).then(successHandler, errorHandler('Error Removing User with Id: ' + userId));
        }

        function successHandler(response) {
            return response.data;
        }

        function createSuccessHandler(response) {
            var res = {
                success: true,
                message: 'Registration Successful!'
            };
            return res;
        }

        function errorHandler(errorMsg) {
            return function() {
                return {
                    success: false,
                    message: errorMsg
                };
            };
        }
    }

}());