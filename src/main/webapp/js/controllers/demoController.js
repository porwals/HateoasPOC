(function() {
'use strict';

	angular
		.module('demoApp')
		.controller('DemoController', DemoController);

	DemoController.$inject = ['DemoService'];
	function DemoController(DemoService) {
		var vm = this;
		
		vm.currentResponse = {};
		vm.transformedResponse = [];
		vm.getData = getData;
		
		init();
		
		function init() {
			getData("http://localhost:8080/greeting");
		}
		
		function getData(url) {
			console.log(url);
			DemoService.getAPIResponse(url).then(function(res) {
				console.log(res.data);
				vm.transformedResponse = angular.isArray(res.data) ? res.data :  [res.data];
				vm.currentResponse = res.data;
			});
		}
	}
})();