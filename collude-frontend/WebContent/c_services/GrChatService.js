(function () {
    'use strict';

    angular
        .module('MainApp')
        .factory('GrChatService', GrChatService)

    /** @ngInject */
    function GrChatService($rootScope, $q, $timeout) {
        var user = $rootScope.loggedInUser;
        var service = {};
        var listener = $q.defer();
        var socket = {
            client: null,
            stomp: null
        };
        var msgIds = [];

        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = '/collude-restbackend/groupChat';
        service.CHAT_TOPIC = '/topic/message';
        service.CHAT_BROKER = '/app/groupChat';

        service.receive = function () {
            console.log('Inside GrChatService::receive()');
            return listener.promise;
        };

        service.send = function (message) {
            console.log('Inside GrChatService::send()');
            var genId = Math.floor(Math.random() * 1000000);
            socket.stomp.send(service.CHAT_BROKER, {
                priority: 9
            }, JSON.stringify({
                id: genId,
                message: message,
                username: user.username
            }));
            msgIds.push(genId);
        };

        var reconnect = function () {
            console.log('Inside GrChatService::reconnect()....');
            $timeout(function () {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        var getMessage = function (data) {
            console.log('Inside GrChatService::getMessage()....');
            var message = JSON.parse(data);
            var outMsg = {};

            outMsg.message = message.message;
            outMsg.username = message.username;
            outMsg.time = new Date(message.time);

            return outMsg;
        };

        var startListener = function () {
            console.log('Inside GrChatService::startListener()....');
            socket.stomp.subscribe(service.CHAT_TOPIC, function (data) {
                listener.notify(getMessage(data.body));
            });
        };

        var initialize = function () {
            console.log('Inside GrChatService::initialize()....');
            socket.client = new SockJS(service.SOCKET_URL);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.connect({}, startListener);
            socket.stomp.onclose = reconnect;
        };

        initialize();

        return service;
    }

} ());