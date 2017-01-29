(function(){
    'use strict';

    console.log('Inside RegController.js');
    
    angular
        .module('MainApp')
        .controller('RegCtrl', RegCtrl)

    /** @ngInject */
    function RegCtrl($rootScope, $location, UserService, MsgService){
        var vm = this;
        vm.register = register;

        function register() {
        	console.log('Inside RegController::register()....');
            vm.dataLoading = true;
            UserService.create(vm.user).then(
                function(response) {
                    if (response.success) {
                        MsgService.flashSuccess(response.message, true);
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