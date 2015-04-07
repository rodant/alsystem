/**
 * Setup the application with require.js
 *
 * Created by antonio on 06.04.15.
 */

"use strict";

requirejs.config({
    paths: {
        'angular': ['../lib/angularjs/angular'],
        'angular-route': ['../lib/angularjs/angular-route']
    },
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular'],
            exports: 'angular'
        }
    }
});

/* Angular app */

require(['angular', 'angular-route'],
    function (angular) {
        var header = angular.module('header', []);
        var inactiveStyle = "-webkit-user-select: none; cursor: pointer; ";
        var headerItems = [
            {"label": "Home", "class": "current", "style": "", "link": "#/house"},
            {"label": "Courses", "class": "opener", "style": inactiveStyle, "link": "/courses"},
            {"label": "Logout", "class": "opener", "style": inactiveStyle, "link": "/logout"}
        ];
        header.controller('HeaderController', function () {
            this.items = headerItems;
        });

        var homeModule = angular.module('home', ['ngRoute']);
        homeModule.controller('HomeController', function () {
        });

        homeModule.config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/house', {
                    templateUrl: 'partials/home-view.html',
                    controller: 'HomeController'
                });
        }]);

        var app = angular.module('sparkmind', ['header', 'home']);
        app.controller('MainController', function () {});

        angular.bootstrap(document.body, ['sparkmind'])
    }
);
