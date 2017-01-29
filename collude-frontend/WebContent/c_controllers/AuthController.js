(function(){
    'use strict';

    console.log('Inside AuthController.js');
    
    angular
        .module('MainApp')
        .controller('AuthCtrl', AuthCtrl)

    /** @ngInject */
    function AuthCtrl($location, AuthService, MsgService){
        var vm = this;
        vm.user = null;
        vm.login = login;
        vm.logout = logout;
        
        init();

        function init(){
        	console.log('Inside AuthController::init()....');
            AuthService.clearCredentials();
        }

        function login() {
        	console.log('Inside AuthController::login()....');
        	console.log('Logging in using username & password....');
        	console.log(vm.user.username + ' & ' + vm.user.password);
            vm.dataLoading = true;
            AuthService.login(vm.user.username, vm.user.password, function(response){
                if (response.success) {
                    AuthService.setCredentials(response.data);
                    $location.path('/');
                } else {
                    MsgService.flashError(response.message);
                    vm.dataLoading = false;
                }
            });
        }

        function logout() {
        	console.log('Inside AuthController::logout()....');
            vm.dataLoading = true;
            AuthService.logout().then(
                function(response) {
                    if (response.success) {
                        MsgService.flashSuccess('You Logged Out Successfully!', true);
                        $location.path('/login');
                    } else {
                        MsgService.flashError(response.message);
                        vm.dataLoading = false;
                    }
                }
            );
        }
    }

}());