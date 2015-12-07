<!DOCTYPE html>
<html ng-app="demoApp" lang="english">
<head>
<meta charset=UTF-8>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous"> -->
<link rel="stylesheet" type="text/css" href="frameworks/bootstrap/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/greeting.css">

<title>Demo App</title>
</head>
<body ng-app="demoApp" ng-controller="DemoController as demo">
<!-- <inav-header></inav-header> -->
	<div class='container-fluid'>
	    <div class="header">
	        <img src="images/logo.png" class="img-responsive" alt="Amex Logo">
	        <h3>Welcome To HATEOAS Demo</h3>
	        <div class="divider"></div>
	     </div>
		<div class='row'>
			<div class='col-md-4 userView'>
			  <h3>Links</h3>
				<div ng-repeat="res in demo.transformedResponse">
				    <h3 ng-repeat="(res1,res2) in res" id="headSpace">
				       <label ng-bind="res1" ng-if="!$last" for="dataItems" id="dataKey"></label>
				       <label ng-if="!$last" id="dataKey">:</label>
				       <label ng-bind="res2" ng-if="!$last" id="dataValue"></label>
				    </h3>
 				    
				    <button type="button" class="btn btn-primary" ng-repeat="(linkType, link) in res._links" ng-click='demo.getData(link.href)'>
						{{linkType}}</button>
				   <hr ng-if="!$last">
				 </div>
			 </div>
			<div class='col-md-6 response'>
				<h3>Response</h3>
				<textarea disabled="disabled" cols="60" rows="28">{{demo.currentResponse | json  }}</textarea>
			</div>
		</div>
		   <div class="footer">
		 </div>
      </div>
      <!--  <inav-footer></inav-footer>  -->
<script type="text/javascript" src="frameworks/angular/angular.min.js"></script>	
<script type="text/javascript" src="js/app.js"></script>
<script type="text/javascript" src="js/directives/common.directive.js"></script>
<script type="text/javascript" src="js/controllers/demoController.js"></script>
<script type="text/javascript" src="js/Services/demoService.js"></script>
</body>
</html>
