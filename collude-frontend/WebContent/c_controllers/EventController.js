(function () {
    'use strict';

    console.log('Inside EventController.js');
    
    angular
        .module('MainApp')
        .controller('EventCtrl', EventCtrl)

    /** @ngInject */
    function EventCtrl($scope, $location, $rootScope, EventService) {
        var vm = this;
        vm.evt = null;
        vm.events = [];

        vm.getAllEvents = getAllEvents;
        vm.getEventById = getEventById;
        vm.create = create;
        vm.update = update;
        vm.submit = submit;
        vm.reset = reset;

        init();

        function init() {
        	console.log('Inside EventController::init()....');
            vm.getAllEvents();
        }

        function getAllEvents() {
            console.log('Inside EventController::getAllEvents()....');
            EventService.getAllEvents().then(
                function (data) {
                    vm.events = data;
                }, logError(err)
            );
        }

        function getEventById(eventId) {
        	console.log('Inside EventController::getEventById()....');
            EventService.getEventById(eventId).then(
                function (response) {
                    $location.path('/eventDetails');
                }, logError(err)
            );
        }

        function create(evt) {
        	console.log('Inside EventController::create()....');
            EventService.create(evt).then(
                function (response) {
                	if (response.success) {
                		alert('Event Created Successfully!');
                	}
                    $location.path('/viewEvents');
                }, logError(err)
            );
        }

        function update(evt) {
        	console.log('Inside EventController::update()....');
            EventService.update(evt).then(
                function (response) {
                    $location.path('/viewEvents');
                }, logError(err)
            );
        }

        function submit() {
        	console.log('Inside EventController::submit()....');
            vm.create(vm.evt);
            vm.reset();
        }

        function reset() {
        	console.log('Inside EventController::reset()....');
            vm.evt = {};
            $scope.form.$setPristine();
        }

        function logError(err) {
        	console.log('Inside EventController::logError()....');
            console.error(err);
        }

        $scope.checkTitle = function (data) {
        	console.log('Inside EventController::checkTitle()....');
            if (data === '')
                return 'Event Title Cannot Be Empty';
        }

        $scope.checkDesc = function (data) {
        	console.log('Inside EventController::checkDesc()....');
            if (data === '')
                return 'Event Description Cannot Be Empty';
        }

        $scope.checkVenue = function (data) {
        	console.log('Inside EventController::checkVenue()....');
            if (data === '')
                return 'Event Venue Cannot Be Empty';
        }
    }

} ());