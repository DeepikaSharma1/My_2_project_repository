(function(){
    'use strict';

    console.log('Inside AuthService.js');
    
    angular
        .module('MainApp')
        .factory('AuthService', AuthService)

    /** @ngInject */
    function AuthService($http, $cookies, $rootScope, $timeout, UserService){
        var BASE_URL = 'http://localhost:9080/collude-restbackend';
        return {
            login: login,
            logout: logout,
            setCredentials: setCredentials,
            clearCredentials: clearCredentials
        }

        function login(username, password, callback){
        	console.log('Inside AuthService::login()....');
            var userCred = {
                username: username,
                password: password
            };
            $http.post(BASE_URL + '/user/auth/', userCred).then(
                function(response) {
                    var res = {
                        data: response.data,
                        success: true
                    };
                    callback(res);
                }, 
                function(errResponse) {
                    var res = {
                        success: false,
                        message: 'Invalid Username or Password!'
                    };
                    callback(res);
                }
            );
        }

        function logout() {
        	console.log('Inside AuthService::logout()....');
            $http.get(BASE_URL + '/user/logout/').then(
                function(response) {
                    var res = {success: true};
                    return res;
                },
                function(errRes) {
                    var res = {success: false, message: 'Error Logging Out!'}
                    return res;
                }
            );
            clearCredentials();
        }

        function setCredentials(user) {
        	console.log('Inside AuthService::setCredentials()....');
            $rootScope.loggedInUser = {
                userId: user.userId,
                username: user.username,
                password: user.password,
                email: user.email,
                role: user.role,
                enabled: user.enabled,
                online: user.online
            };
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.loggedInUser;
            var cookieExp = new Date();
            cookieExp.setDate(cookieExp.getDate() + 3);
            $cookies.putObject('loggedInUser', $rootScope.loggedInUser, { expires: cookieExp });
        }

        function clearCredentials() {
        	console.log('Inside AuthService::clearCredentials()....');
            $rootScope.loggedInUser = {};
            $cookies.remove('loggedInUser');
            $http.defaults.headers.common.Authorization = 'Basic';
        }
    }

}());