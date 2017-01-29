(function () {
    'use strict';

    angular
        .module('MainApp')
        .factory('PrChatService', PrChatService)

    /** @ngInject */
    function PrChatService($rootScope, $q, $timeout) {
        var user = $rootScope.loggedInUser;
        var friendName = $rootScope.friendName;
        var service = {};
        var listener = $q.defer();
        var socket = {
            client: null,
            stomp: null
        };
        var msgIds = [];

        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = '/collude-restbackend/privateChat';
        service.CHAT_TOPIC = '/queue/message/' + user.username;
        service.CHAT_BROKER = '/app/privateChat';

        service.receive = function () {
            console.log('Inside PrChatService::receive()');
            return listener.promise;
        };

        service.send = function (message) {
            console.log('Inside PrChatService::send()');
            var genId = Math.floor(Math.random() * 1000000);
            socket.stomp.send(service.CHAT_BROKER, {
                priority: 9
            }, JSON.stringify({
                id: genId,
                message: message,
                username: user.username,
                friendName: friendName
            }));
            msgIds.push(genId);
        };

        var reconnect = function () {
            console.log('Inside PrChatService::reconnect()....');
            $timeout(function () {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        var getMessage = function (data) {
            console.log('Inside PrChatService::getMessage()....');
            var message = JSON.parse(data);
            var outMsg = {};

            outMsg.message = message.message;
            outMsg.username = message.username;
            outMsg.time = new Date(message.time);

            return outMsg;
        };

        var startListener = function () {
            console.log('Inside PrChatService::startListener()....');
            socket.stomp.subscribe(service.CHAT_TOPIC, function (data) {
                listener.notify(getMessage(data.body));
            });
        };

        var initialize = function () {
            console.log('Inside PrChatService::initialize()....');
            socket.client = new SockJS(service.SOCKET_URL);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.connect({}, startListener);
            socket.stomp.onclose = reconnect;
        };

        initialize();

        return service;
    }

} ());