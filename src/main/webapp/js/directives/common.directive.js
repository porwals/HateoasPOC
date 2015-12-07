angular
		.module('demoApp').directive('bannerSection', [function() {

	return {
		restrict: 'E',
		templateUrl: 'views/banner.html'
	};

}]);

angular
.module('demoApp').directive('logoSection',['$location', '$window', function($location, $window) {

	return {
		restrict: 'E',
		templateUrl: 'views/logo.html'

	};

}]);

angular
.module('demoApp').directive('inavHeader',['$location', '$window', function($location, $window) {

	return {
		restrict: 'E',
		templateUrl: 'views/US_en_NGN_H_Generic.html'

	};

}]);

angular
.module('demoApp').directive('inavFooter',['$location', '$window', function($location, $window) {

	return {
		restrict: 'E',
		templateUrl: 'views/US_en_NGN_F_Generic.html'

	};

}]);

angular
.module('demoApp').directive('showErrors', [function () {
    return {
        restrict: 'E',
        templateUrl: 'views/errors.html',
        link: function ($scope, el, attrs) {

            $scope.closeAlert = function (index) {
                $scope.errorAlerts.splice(index, 1);
            };


        }
    };
}]);


angular
.module('demoApp').directive('spinner', [function () {
    return {
        restrict: 'E',
        template: [
            '<div ng-show="showSpinner">',
            '<div id="resultLoading">',
            '<div class="loader dark"></div>',
            '<div class="bg"></div>',
            '</div></div>'
        ].join(''),
        replace: true,
        scope: {
            id: '@',
            group: '@?',
            showSpinner: '@?',
            onRegisterComplete: '&?'
        },
        controller: ['$scope', '$attrs', 'spinnerService', function ($scope, $attrs, spinnerService) {
            // Register the spinner with the spinner service.
            spinnerService._register($scope);

            // Invoke the onRegisterComplete expression, if any.
            // Expose the spinner service for easy access.
            $scope.onRegisterComplete({ $spinnerService: spinnerService });
        }],
        link: function (scope, elem, attrs) {
            // Check for pre-defined size aliases and set pixel width accordingly.
            /*if (attrs.hasOwnProperty('size')) {
              attrs.size = attrs.size.toLowerCase();
            }
            switch (attrs.size) {
              case 'tiny':
                scope.spinnerSize = '15px';
                break;
              case 'small':
                scope.spinnerSize = '25px';
                break;
              case 'medium':
                scope.spinnerSize = '35px';
                break;
              case 'large':
                scope.spinnerSize = '64px';
                break;
              default:
                scope.spinnerSize = '50px';
                break;
            }*/
        }
    };
}]);











