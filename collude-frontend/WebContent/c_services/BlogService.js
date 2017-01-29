(function () {
    'use strict';

    console.log('Inside BlogService.js');
    
    angular
        .module('MainApp')
        .factory('BlogService', BlogService)

    /** @ngInject */
    function BlogService($http, $rootScope) {
        var BASE_URL = 'http://localhost:9080/collude-restbackend';
        return {
            getAllBlogs: getAllBlogs,
            getApprovedBlogs: getApprovedBlogs,
            getBlogById: getBlogById,
            create: create,
            update: update,
            remove: remove
        };

        function getAllBlogs() {
        	console.log('Inside BlogService::getAllBlogs()....');
            return $http.get(BASE_URL + '/blog/all/').then(successHandler, errorHandler('Error Getting Blogs List!'));
        }

        function getApprovedBlogs() {
        	console.log('Inside BlogService::getApprovedBlogs()....');
            return $http.get(BASE_URL + '/blog/approved/').then(successHandler, errorHandler('Error Getting Approved Blogs List!'));
        }

        function getBlogById(blogId) {
        	console.log('Inside BlogService::getBlogById()....');
            return $http.get(BASE_URL + '/blog/' + blogId).then(
                function (response) {
                    $rootScope.selectedBlog = response.data;
                    return response.data;
                }, errorHandler('Error Getting Blog with Id: ' + blogId)
            );
        }

        function create(blog) {
        	console.log('Inside BlogService::create()....');
            return $http.post(BASE_URL + '/blog/', blog).then(createSuccessHandler, errorHandler('Error Creating Blog!'));
        }

        function update(blog) {
        	console.log('Inside BlogService::update()....');
            return $http.put(BASE_URL + '/blog/' + blog.blogId, blog).then(successHandler, errorHandler('Error Updaing Blog with Id: ' + blog.blogId));
        }

        function remove(blogId) {
        	console.log('Inside BlogService::remove()....');
            return $http.delete(BASE_URL + '/blog/' + blogId).then(successHandler, errorHandler('Error Removing Blog with ID: ' + blogId));
        }

        function successHandler(response) {
            return response.data;
        }

        function createSuccessHandler(response) {
            var res = {success: true};
            return res;
        }

        function errorHandler(errorMsg) {
            return function () {
                return {
                    success: false,
                    message: errorMsg
                };
            };
        }
    }

} ());