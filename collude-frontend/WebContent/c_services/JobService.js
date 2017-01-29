(function(){
    'use strict';

    console.log('Inside JobService.js');
    
    angular
        .module('MainApp')
        .factory('JobService', JobService)

    /** @ngInject */
    function JobService($http, $q, $rootScope){
        var BASE_URL = 'http://localhost:9080/collude-restbackend';
        return {
            getAllJobs: getAllJobs,
            getJobById: getJobById,
            create: create,
            update: update,
            applyJob: applyJob,
            getJobsApplied: getJobsApplied
        };

        function getAllJobs(){
            return $http.get(BASE_URL + '/job/').then(handleSuccess, handleError('Error Getting List of Jobs!'));
        }

        function getJobById(jobId) {
            return $http.get(BASE_URL + '/job/' + jobId).then(
                function (response) {
                    $rootScope.selectedJob = response.data;
                    return response.data;
                }, handleError('Error getting job details with ID: ' + jobId)
            );
        }

        function create(job) {
            return $http.post(BASE_URL + '/job/', job).then(handleCreateSuccess, handleError('Error Posting New Job!!!'));
        }

        function update(job) {
            return $http.put(BASE_URL + '/job/' + job.jobId, job).then(
                getAllJobs, handleError('Error Updating Job with Id: ' + job.jobId)
            );
        }

        function applyJob(jobId) {
            return $http.post(BASE_URL + '/job/apply/' + jobId).then(handleSuccess, handleError('Error Applying For Job!'));
        }

        function getJobsApplied() {
            return $http.get(BASE_URL + '/job/applied/').then(handleSuccess, handleError('Error Geting Applied Jobs List!'));
        }

        function handleSuccess(response) {
            return response.data;
        }

        function handleCreateSuccess(res) {
            var response = {success: true};
            return response;
        }

        function handleError(errorMsg) {
            var errResponse = {success: false, message: errorMsg};
            return errResponse;
        }
    }

}());