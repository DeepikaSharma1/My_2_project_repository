(function () {
    'use strict';

    console.log('Inside app.js');
    
    angular
        .module('MainApp', ['ngRoute', 'ngCookies', 'xeditable'])
        .config(Config)
        .run(Run)

    function Config($routeProvider, $locationProvider) {
    	console.log('Inside Config()....');
        $routeProvider
            .when('/', {
                templateUrl: 'c_home/home.html',
                controller: 'HomeCtrl',
                controllerAs: 'vm'
            }).when('/contactUs', {
                templateUrl: 'c_home/contactUs.html'
            }).when('/aboutUs', {
                templateUrl: 'c_home/aboutUs.html'
            })
            /** Admin related mappings */
            .when('/manageBlogs', {
                templateUrl: 'c_admin/manageBlogs.html',
                controller: 'AdminCtrl',
                controllerAs: 'vm'
            }).when('/manageUsers', {
                templateUrl: 'c_admin/manageUsers.html',
                controller: 'AdminCtrl',
                controllerAs: 'vm'
            }).when('/manageJobs', {
                templateUrl: 'c_admin/manageJobs.html',
                controller: 'AdminCtrl',
                controllerAs: 'vm'
            })
            /** User related mappings */
            .when('/login', {
                templateUrl: 'c_login/login.html',
                controller: 'AuthCtrl',
                controllerAs: 'vm'
            }).when('/register', {
                templateUrl: 'c_register/register.html',
                controller: 'RegCtrl',
                controllerAs: 'vm'
            }).when('/groupChat', {
            	templateUrl: 'c_group_chat/groupChat.html',
            	controller: 'GrChatCtrl',
            	controllerAs: 'vm'
            })
            /** Blog related mappings */
            .when('/viewBlogs', {
            	templateUrl: 'c_blog/viewBlogs.html',
            	controller: 'BlogCtrl',
            	controllerAs: 'vm'
            }).when('/blogDetails', {
            	templateUrl: 'c_blog/blogDetails.html',
            	controller: 'BlogCtrl',
            	controllerAs: 'vm'
            }).when('/newBlog', {
            	templateUrl: 'c_blog/newBlog.html',
            	controller: 'BlogCtrl',
            	controllerAs: 'vm'
            })
            /** Event related mappings */
            .when('/viewEvents', {
            	templateUrl: 'c_event/viewEvents.html',
            	controller: 'EventCtrl',
            	controllerAs: 'vm'
            }).when('/eventDetails', {
            	templateUrl: 'c_event/eventDetails.html',
            	controller: 'EventCtrl',
            	controllerAs: 'vm'
            }).when('/createEvent', {
            	templateUrl: 'c_event/createEvent.html',
            	controller: 'EventCtrl',
            	controllerAs: 'vm'
            })
            /** Job related mappings */
            .when('/viewJobs', {
            	templateUrl: 'c_job/viewJobs.html',
            	controller: 'JobCtrl',
            	controllerAs: 'vm'
            })
            .when('/jobDetails', {
            	templateUrl: 'c_job/jobDetails.html',
            	controller: 'JobCtrl',
            	controllerAs: 'vm'
            })
            .when('/jobsApplied', {
            	templateUrl: 'c_job/jobsApplied.html',
            	controller: 'JobCtrl',
            	controllerAs: 'vm'
            })
            /** Friend related mappings */
            .when('/viewFriends', {
            	templateUrl: 'c_friend/viewFriends.html',
            	controller: 'FriendCtrl',
            	controllerAs: 'vm'
            }).when('/viewFriendRequests', {
            	templateUrl: 'c_friend/viewFriendRequests.html',
            	controller: 'FriendCtrl',
            	controllerAs: 'vm'
            }).when('/searchFriends', {
            	templateUrl: 'c_friend/searchFriends.html',
            	controller: 'FriendCtrl',
            	controllerAs: 'vm'
            }).when('/privateChat', {
            	templateUrl: 'c_private_chat/privateChat.html',
            	controller: 'PrChatCtrl',
            	controllerAs: 'vm'
            }).otherwise({
                redirectTo: '/login'
            });
    }
    /** @ngInject */
    function Run($rootScope, $location, $cookies, $http) {
    	console.log('Inside Run()....');
        $rootScope.loggedInUser = $cookies.getObject('loggedInUser') || {};
        if ($rootScope.loggedInUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.loggedInUser;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
        	console.log('Inside Run()::$on()....');
            var restrictedPage = $.inArray($location.path(), ['/', '/login', '/register', '/viewBlogs', '/blogDetails', '/viewEvents', '/eventDetails', '/jobDetails', '/viewJobs']) === -1;
            var loggedIn = $rootScope.loggedInUser.username;
            if (!loggedIn) {
                if (restrictedPage) {
                    $location.path('/login');
                }
            } else {
                var role = $rootScope.loggedInUser.role;
                var userRestrictedPage = $.inArray($location.path(), ['/manageJobs', '/manageUsers', '/manageBlogs']) === 0;
                if (userRestrictedPage && role != 'ADMIN') {
                    alert('You Are Not Authorized To Do This Operation, As You Are Registered As: ' + role);
                    $location.path('/');
                }
            }
        });
    }

} ());