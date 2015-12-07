(function() {
'use strict';

	angular
		.module('demoApp')
		.service('DemoService', DemoService);

	DemoService.$inject = ['$http'];
	function DemoService($http) {
		
		this.getAPIResponse = getAPIResponse;
		
		function getAPIResponse (url) {
			if(url.indexOf('/organizations/') != -1 || url.indexOf('account') != -1){
				return getAccount(url);
			} else if (url.indexOf('/organizations') != -1) {
				return getOrganization(url);
			} else {
				return getGreeting(url);
			}
		}
		
		function getGreeting(url) {
			return $http.get('http://localhost:8080/greeting');	
		}
		
		function getOrganization(url) {
			return $http.get('http://localhost:8080/organizations');
		}
		
		function getAccount(url) {
			return $http.get(url);
		}
	}
})();