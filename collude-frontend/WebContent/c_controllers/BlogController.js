(function () {
    'use strict';

    console.log('Inside BlogController.js');
    
    angular
        .module('MainApp')
        .controller('BlogCtrl', BlogCtrl)

    /** @ngInject */
    function BlogCtrl($scope, $rootScope, $location, BlogService) {
        var vm = this;
        vm.blog = null;
        vm.blogs = [];
        vm.approvedBlogs = [];
        vm.getAllBlogs = getAllBlogs;
        vm.getApprovedBlogs = getApprovedBlogs;
        vm.getBlogDetails = getBlogDetails;
        vm.newBlog = newBlog;
        vm.updateBlog = updateBlog;
        vm.removeBlog = removeBlog;
        vm.submit = submit;
        vm.reset = reset;

        init();

        function init() {
        	console.log('Inside BlogController::init()....');
            vm.getAllBlogs();
            vm.getApprovedBlogs();
        }

        function getAllBlogs() {
        	console.log('Inside BlogController::getAllBlogs()....');
            BlogService.getAllBlogs().then(
                function (data) {
                    vm.blogs = data;
                }, function (errRes) {
                    console.error(errRes);
                }
            );
        }

        function getApprovedBlogs() {
        	console.log('Inside BlogController::getApprovedBlogs()....');
            BlogService.getApprovedBlogs().then(
                function (data) {
                    vm.approvedBlogs = data;
                }, function (errRes) {
                    console.error(errRes);
                }
            );
        }

        function getBlogDetails(blogId) {
        	console.log('Inside BlogController::getBlogDetails()....');
            BlogService.getBlogById(blogId).then(
                function (response) {
                    $location.path('/blogDetails');
                }, function (errRes) {
                    console.error(errRes);
                }
            );
        }

        function newBlog(blog) {
        	console.log('Inside BlogController::newBlog()....');
            BlogService.create(blog).then(
                function (response) {
                	if (response.success) {
                		alert('Blog Created Successfully! Waiting for Admin\'s Approval');
                	}
                    $location.path('/viewBlogs');
                }, function (errRes) {
                    console.error(errRes);
                }
            );
        }

        function updateBlog(blog) {
        	console.log('Inside BlogController::updateBlog()....');
            BlogService.update(blog).then(
                function (response) {
                    $location.path('/viewBlogs');
                }, function (errRes) {
                    console.error(errRes);
                }
            );
        }

        function removeBlog(blogId) {
        	console.log('Inside BlogController::removeBlog()....');
            BlogService.remove(blogId).then(
                function (response) {
                    $location.path('/viewBlogs');
                }, function (errRes) {
                    console.error(errRes);
                }
            );
        }

        function submit() {
        	console.log('Inside BlogController::submit()....');
            vm.blog.user = $rootScope.loggedInUser;
            vm.newBlog(vm.blog);
            vm.reset();
        }

        function reset() {
        	console.log('Inside BlogController::reset()....');
            vm.blog = {};
            $scope.form.$setPristine();
        }

        $scope.checkTitle = function (data) {
            if (data === '')
                return 'Blog title cannot be empty';
        };

        $scope.checkContent = function (data) {
            if (data === '')
                return 'Content cannot be empty';
        }
    }

} ());