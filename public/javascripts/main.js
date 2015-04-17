/**
 * Setup the application with require.js
 *
 * Created by antonio on 06.04.15.
 */

"use strict";

requirejs.config({
    paths: {
        'angular': ['../lib/angularjs/angular'],
        'angular-route': ['../lib/angularjs/angular-route'],
        'jquery': ['../lib/jquery/jquery'],
        'dropotron': ['jquery.dropotron.min'],
        'skel': ['skel.min'],
        'skel-layers': ['skel-layers.min'],
        'init': ['init']
    },
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular'],
            exports: 'angular'
        },
        'jquery': {
            exports: 'jquery'
        },
        'dropotron': {
            deps: ['jquery'],
            exports: 'dropotron'
        },
        'skel': {
            deps: ['dropotron'],
            exports: 'skel'
        },
        'skel-layers': {
            deps: ['skel'],
            exports: 'skel-layers'
        },
        'init': {
            deps: ['skel'],
            exports: 'init'
        }
    }
});

/* Angular app */

require(['angular', 'init', 'angular-route'],
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

        var home = angular.module('home', ['ngRoute']);
        home.controller('HomeController', function () {
        });

        home.config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/house', {
                    templateUrl: 'partials/home-view.html',
                    controller: 'HomeController'
                });
        }]);

        var sparkmind = angular.module('sparkmind', ['header', 'home']);
        sparkmind.controller('MainController', function () {});

        angular.bootstrap(document, ['sparkmind'])
    }
);
