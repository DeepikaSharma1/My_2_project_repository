(function(){
    'use strict';

    angular
        .module('MainApp')
        .factory('FriendService', FriendService)

    /** @ngInject */
    function FriendService($http){
        var BASE_URL = 'http://localhost:9080/collude-restbackend';
        return {
            sendFriendRequest: sendFriendRequest,
            acceptFriendRequest: acceptFriendRequest,
            rejectFriendRequest: rejectFriendRequest,
            getMyFriends: getMyFriends,
            getFriendRequests: getFriendRequests
        }

        function sendFriendRequest(toUserId){
            return $http.get(BASE_URL + '/friend/sendRequest/' + toUserId).then(handleSuccess, handleError('Error Sending Friend Request To ' + toUserId));
        }

        function getFriendRequests() {
            return $http.get(BASE_URL + '/friend/friendRequests/').then(handleSuccess, handleError('Error Getting Your Friend Requests!'));
        }

        function getMyFriends() {
            return $http.get(BASE_URL + '/friend/friends/').then(handleSuccess, handleError('Error Getting Your Friends!'));
        }

        function acceptFriendRequest(ofUserId) {
            return $http.get(BASE_URL + '/friend/accept/' + ofUserId).then(handleSuccess, handleError('Error Accepting Friend Request of ' + ofUserId));
        }

        function rejectFriendRequest(ofUserId) {
            return $http.get(BASE_URL + '/friend/reject/' + ofUserId).then(handleSuccess, handleError('Error Rejecting Friend Request of ' + ofUserId));
        }

        function handleSuccess(response) {
            return response.data;
        }

        function handleError(errorMsg) {
            var errResponse = {success: false, message: errorMsg};
            return errResponse;
        }
    }

}());