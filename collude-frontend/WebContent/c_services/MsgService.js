(function(){
    'use strict';

    console.log('Inside MsgService.js');
    
    angular
        .module('MainApp')
        .factory('MsgService', MsgService)

    /** @ngInject */
    function MsgService($rootScope, $location){
        return {
            flashSuccess: flashSuccess,
            flashError: flashError
        }

        init();
        
        function init() {
        	console.log('Inside MsgService::init()....');
        	$rootScope.$on('$locationChangeStart', function() {
                clearFlashMsg();
            });
        	
        	function clearFlashMsg() {
        		console.log('Inside MsgService::init()::clearFlashMsg()....');
                var flashMsg = $rootScope.flashMsg;
                if (flashMsg) {
                    if (!flashMsg.keepAfterLocationChange) {
                        delete $rootScope.flashMsg;
                    } else {
                        flashMsg.keepAfterLocationChange = false;
                    }
                }
            }
        }
        
        function flashSuccess(message, keepAfterLocationChange){
        	console.log('Inside MsgService::flashSuccess()....');
            $rootScope.flashMsg = {
                message: message,
                type: 'success',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }

        function flashError(message, keepAfterLocationChange) {
        	console.log('Inside MsgService::flashError()....');
            $rootScope.flashMsg = {
                message: message,
                type: 'error',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }
    }

}());