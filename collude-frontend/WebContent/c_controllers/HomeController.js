(function(){
    'use strict';

    console.log('Inside HomeController.js');
    
    angular
        .module('MainApp')
        .controller('HomeCtrl', HomeCtrl)

    /** @ngInject */
    function HomeCtrl($rootScope, UserService){
        var vm = this;
        vm.user = null;

        init();

        function init(){
        	console.log('Inside HomeController::init()....');
            getLoggedInUser();
        }

        function getLoggedInUser() {
        	console.log('Inside HomeController::getLoggedInUser()....');
            vm.user = $rootScope.loggedInUser;
        }
    }

}());