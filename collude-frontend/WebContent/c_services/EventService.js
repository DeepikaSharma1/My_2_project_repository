(function () {
    'use strict';

    angular
        .module('MainApp')
        .factory('EventService', EventService)

    /** @ngInject */
    function EventService($http, $rootScope) {
        var BASE_URL = 'http://localhost:9080/collude-restbackend';
        return {
            getAllEvents: getAllEvents,
            getEventById: getEventById,
            create: create,
            update: update
        }

        function getAllEvents() {
            return $http.get(BASE_URL + '/event/').then(
                function (response) { return response.data; },
                handleError('Error Getting List of Events!')
            );
        }

        function getEventById(eventId) {
            return $http.get(BASE_URL + '/event/' + eventId).then(
                function (response) { $rootScope.selectedEvent = response.data; return response.data; },
                handleError('Error Getting Event with Id: ' + eventId)
            );
        }

        function create(evt) {
            return $http.post(BASE_URL + '/event/', evt).then(
                function (response) { var res = { success: true }; return res; },
                handleError('Error Creating Event!')
            );
        }

        function update(evt) {
            return $http.put(BASE_URL + '/event/' + evt.eventId, evt).then(
                getAllEvents, handleError('Error Updating Event with Id: ' + evt.eventId)
            );
        }
        
        function handleError(errorMsg) {
        	var response = {success: false, message: errorMsg};
        	return response;
        }
    }

} ());