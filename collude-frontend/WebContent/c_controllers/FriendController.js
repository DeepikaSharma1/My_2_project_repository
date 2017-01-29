(function () {
    'use strict';

    console.log('Inside FriendController.js');
    
    angular
        .module('MainApp')
        .controller('FriendCtrl', FriendCtrl)

    /** @ngInject */
    function FriendCtrl($scope, $location, $rootScope, FriendService, UserService, MsgService) {
        var vm = this;
        vm.friend = null;
        vm.friends = [];
        vm.user = null;
        vm.users = [];
        vm.friendRequests = [];

        vm.sendFriendRequest = sendFriendRequest;
        vm.getMyFriends = getMyFriends;
        vm.getFriendRequests = getFriendRequests;
        vm.getOtherUsers = getOtherUsers;
        vm.acceptRequest = acceptRequest;
        vm.rejectRequest = rejectRequest;
        vm.startPrivateChat = startPrivateChat;

        init();

        function init() {
        	console.log('Inside FriendController::init()....');
            vm.getOtherUsers();
            vm.getMyFriends();
            vm.getFriendRequests();
        }

        function getOtherUsers() {
        	console.log('Inside FriendController::getOtherUsers()....');
            UserService.getAllUsersExceptLoggedIn().then(
                function (data) {
                    vm.users = data;
                }, function (err) {
                    MsgService.flashError(err, false);
                }
            );
        }

        function getMyFriends() {
        	console.log('Inside FriendController::getMyFriends()....');
            FriendService.getMyFriends().then(
                function (data) {
                    vm.friends = data;
                },
                function (errResponse) {
                    MsgService.flashError(errResponse, false);
                }
            );
        }

        function getFriendRequests() {
        	console.log('Inside FriendController::getFriendRequests()....');
            FriendService.getFriendRequests().then(
                function (data) {
                    vm.friendRequests = data;
                },
                function (errResponse) {
                    MsgService.flashError(errResponse, false);
                }
            );
        }

        function sendFriendRequest(toUserId) {
        	console.log('Inside FriendController::sendFriendRequest()....');
            FriendService.sendFriendRequest(toUserId).then(
                function (data) {
                    vm.friend = data;
                    MsgService.flashSuccess('Friend Request Sent Successfully!');
                },
                function (errResponse) {
                    MsgService.flashError(errResponse, false);
                }
            );
        }

        function acceptRequest(ofUserId) {
        	console.log('Inside FriendController::acceptRequest()....');
            FriendService.acceptFriendRequest(ofUserId).then(
                function (data) {
                    vm.getFriendRequests();
                },
                function (errResponse) {
                    MsgService.flashError(errResponse, false);
                }
            );
        }

        function rejectRequest(ofUserId) {
        	console.log('Inside FriendController::rejectRequest()....');
            FriendService.rejectFriendRequest(ofUserId).then(
                function (data) {
                    vm.getFriendRequests();
                },
                function (errResponse) {
                    MsgService.flashError(errResponse, false);
                }
            );
        }

        function startPrivateChat(friendName) {
        	console.log('Inside FriendController::startPrivateChat()....');
            $rootScope.friendName = friendName;
            $location.path('/privateChat');
        }

    }

} ());