(function () {
    'use strict';

    console.log('Inside JobController.js');
    
    angular
        .module('MainApp')
        .controller('JobCtrl', JobCtrl)

    /** @ngInject */
    function JobCtrl($scope, $location, $rootScope, JobService, MsgService) {
        var vm = this;
        vm.job = null;
        vm.jobs = [];
        vm.jobsApplied = [];

        vm.getAllJobs = getAllJobs;
        vm.getJobById = getJobById;
        vm.createJob = createJob;
        vm.updateJob = updateJob;
        vm.applyJob = applyJob;
        vm.getJobsApplied = getJobsApplied;
        vm.submit = submit;
        vm.edit = edit;
        vm.reset = reset;

        init();

        function init() {
        	console.log('Inside JobController::init()');
            vm.getAllJobs();
        }

        function getAllJobs() {
        	console.log('Inside JobController::getAllJobs()');
            JobService.getAllJobs().then(
                function (data) { vm.jobs = data; }, function (errRes) { MsgService.flashError(errRes.message, false); }
            );
        }

        function getJobById(jobId) {
        	console.log('Inside JobController::getJobById()');
            JobService.getJobById(jobId).then(
                function (data) { $location.path('/jobDetails'); }, function (errRes) { MsgService.flashError(errRes.message, false); }
            );
        }

        function createJob(job) {
        	console.log('Inside JobController::createJob()');
            JobService.create(job).then(vm.getAllJobs, function (errRes) { MsgService.flashError(errRes.message, false); });
        }

        function updateJob(job) {
        	console.log('Inside JobController::updateJob()');
            JobService.update(job).then(vm.getAllJobs, function (errRes) { MsgService.flashError(errRes.message, false); });
        }

        function applyJob(jobId) {
        	console.log('Inside JobController::applyJob()');
            var user = $rootScope.loggedInUser;
            if (typeof user.userId == 'undefined') {
                alert('You Must Log In To Apply For Job!');
                $location.path('/login');
            }

            JobService.applyJob(jobId).then(
                function (data) { vm.job = data; }, function (errRes) { MsgService.flashError(errRes.message, false); }
            );
        }

        function getJobsApplied() {
        	console.log('Inside JobController::getJobsApplied()');
            var user = $rootScope.loggedInUser;
            if (typeof user.userId == 'undefined') {
                alert('You Must Log In To Apply For Job!');
                $location.path('/login');
            }

            JobService.getJobsApplied().then(
                function (data) { vm.jobsApplied = data; }, function (errRes) { MsgService.flashError(errRes.message, false); }
            );
        }

        function edit(jobId) {
        	console.log('Inside JobController::edit()');
            for (var i = 0; i < vm.jobs.length; i++) {
                if (vm.jobs[i].jobId === jobId) {
                    vm.job = angular.copy(vm.jobs[i]);
                    break;
                }
            }
        }

        function submit() {
        	console.log('Inside JobController::submit()');
            vm.createJob(vm.job);
            vm.reset();
        }

        function reset() {
        	console.log('Inside JobController::reset()');
            vm.job = {};
            $scope.form.$setPristine();
        }

        /*function logError(errRes) {
        	console.log('Inside JobController::logError()');
            MsgService.flashError(errRes.message, false);
        }*/
    }

} ());