(function () {
    'use strict';

    angular
        .module('MainApp')
        .controller('PrChatCtrl', PrChatCtrl)

    /** @ngInject */
    function PrChatCtrl($scope, PrChatService) {
        $scope.messages = [];
        $scope.message = '';
        $scope.MAX_LEN = 200;

        $scope.sendMessage = function () {
            console.log('Inside PrChatCtrl::sendMessage()...');
            PrChatService.send($scope.message);
            $scope.message = '';
        };

        PrChatService.receive().then(null, null, function (message) {
            console.log('Inside PrChatCtrl::PrChatService.receive()...');
            $scope.messages.push(message);
        });

    }

} ());