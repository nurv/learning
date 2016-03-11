(function () {
	var app = angular.module('fenixeduCoffee', ['connect', 'ui.router']);

	app.constant('appBase', 'http://localhost:8090');
	app.constant('serviceName', 'coffee.tecnico.ulisboa.pt');
	app.constant('connectBase', 'http://localhost:8080');
	app.constant('scopes', 'COFFEE');
	app.constant('clientId', 'client');

	app.config(function($stateProvider, $urlRouterProvider) {
	  //
	  // For any unmatched url, redirect to /state1
	  $urlRouterProvider.otherwise("/coffee");
	  //
	  // Now set up the states
	  $stateProvider
	    .state('coffee', {
	      url: "/coffee",
	      templateUrl: "views/coffee.html",
	      controller: 'CoffeeController'
	    });
	});

	app.controller('CoffeeController', ['$scope', '$http', 'appBase', function ($scope, $http, appBase) {
		$scope.name = 'Hello';
		$scope.loadCoffees = function() {
			$http.get(appBase + '/coffee').success(function (data) {
				$scope.coffees = data;
				console.log('Loaded ' + data.length + ' coffees');
			});
		};
		$scope.loadCoffees();
		$scope.addCoffee = function() {
			$http.post(appBase + '/coffee', { image: $scope.newPhoto, name: $scope.newName, price: $scope.newPrice }).success(function (data) {
					$scope.newPrice = ''; $scope.newPhoto = ''; $scope.newName = '';
				$scope.coffees.push(data);
			});
		};
	}]);
})();