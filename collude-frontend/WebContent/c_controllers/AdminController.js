(function(){
    'use strict';

    console.log('Inside AdminController.js');
    
    angular
        .module('MainApp')
        .controller('AdminCtrl', AdminCtrl)

    /** @ngInject */
    function AdminCtrl($rootScope, $location, UserService, BlogService, JobService, MsgService){
        var vm = this;
        vm.blog = null;
        vm.user = null;
        vm.job = null;
        vm.blogs = [];
        vm.users = [];
        vm.jobs = [];

        vm.getAllUsers = getAllUsers;
        vm.getAllBlogs = getAllBlogs;
        vm.getAllJobs = getAllJobs;
        vm.acceptUser = acceptUser;
        vm.rejectUser = rejectUser;
        vm.approveBlog = approveBlog;
        vm.rejectBlog = rejectBlog;
        vm.postNewJob = postNewJob;
        /*vm.removeJob = removeJob;*/

        init();

        function init(){
        	console.log('Inside AdminController::init()....');
            vm.getAllBlogs();
            vm.getAllJobs();
            vm.getAllUsers();
        }

        function getAllUsers() {
        	console.log('Inside AdminController::getAllUsers()....');
            UserService.getAllUsersExceptLoggedIn().then(
                function (data) {
                    vm.users = data;
                }, handleError('Error Getting Users!')
            );
        }

        function acceptUser(user) {
        	console.log('Inside AdminController::acceptUser()....');
            user.enabled = true;
            UserService.update(user).then(
                vm.getAllUsers, handleError('Error Approving User')
            );
        }

        function rejectUser(user) {
        	console.log('Inside AdminController::rejectUser()....');
            user.enabled = false;
            UserService.update(user).then(
                vm.getAllUsers, handleError('Error Rejecting User')
            );
        }

        function getAllBlogs() {
        	console.log('Inside AdminController::getAllBlogs()....');
            BlogService.getAllBlogs().then(
                function (data) {
                    vm.blogs = data;
                }, handleError('Error Getting Blogs!')
            );
        }

        function approveBlog(blog) {
        	console.log('Inside AdminController::approveBlog()....');
            blog.status = 'APPROVED';
            BlogService.update(blog).then(
                vm.getAllBlogs,  handleError('Error Approving Blog')
            );
        }

        function rejectBlog(blog) {
        	console.log('Inside AdminController::rejectBlog()....');
            blog.status = 'REJECTED';
            BlogService.update(blog).then(
                vm.getAllBlogs,  handleError('Error Rejecting Blog')
            );
        }

        function getAllJobs() {
        	console.log('Inside AdminController::getAllJobs()....');
            JobService.getAllJobs().then(
            	function(data) { vm.jobs = data; }, handleError('Error Getting Jobs')
            );
        }
        function postNewJob(job) {
        	console.log('Inside AdminController::postNewJob()....');
            JobService.create(job).then(
            	function (res) {
            		if (res.success) {
            			MsgService.flashSuccess('Job Posted Successfully!');
            			vm.getAllJobs();
            		}
            	}, handleError(errRes.message)
            );
        }
/*
        function removeJob(jobId) {
        	console.log('Inside AdminController::removeJob()....');
            // to be implemented....
        }
*/
        function handleError(errorMsg) {
            console.error(errorMsg);
        }

    }

}());