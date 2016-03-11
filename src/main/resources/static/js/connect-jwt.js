(function () {
    var connect = angular.module('connect', ['angular-jwt', 'angular-storage']);

    connect.factory('ConnectStore', ['store', function (store) {
        return store.getNamespacedStore('connect');
    }]);

    function expired(target) {
        return target.expiration - Date.now() - 5000 < 0;
    }

    connect.config(['$httpProvider', 'jwtInterceptorProvider', function ($httpProvider, jwtInterceptorProvider) {
        jwtInterceptorProvider.tokenGetter = ['$rootScope', 'config', function ($rootScope, config) {
            if (config.url.indexOf(this.base) < 0) {
                // Only send the token for requests to the resource server
                return null;
            }
            if ($rootScope.user && !expired($rootScope.user)) {
                return $rootScope.user.access_token;
            }
            // Refresh the token
            console.log('Refreshing token...');
            getToken();
            return {
                'then': function () {
                }
            };
        }];

        $httpProvider.interceptors.push('jwtInterceptor');
    }]);

    function getToken() {
        window.location.href = this.connect + "/oauth/authorize?client_id=" + this.clientId + "&grant_type=implicit&response_type=token&scope=info " + this.scopes;
    }

    connect.run(['$rootScope', '$http', 'appBase', 'serviceName', 'connectBase', 'scopes', 'ConnectStore', 'clientId',
        function ($rootScope, $http, appBase, serviceName, connectBase, scopes, ConnectStore, clientId) {
            var hash = window.location.hash;
            var tokenIndex = hash.indexOf("access_token=");
            if (tokenIndex >= 0) {
                var auth = {};
                hash.substring(tokenIndex).split("&").map(function (param) {
                    var arg = param.split("=");
                    auth[arg[0]] = decodeURIComponent(arg[1]);
                });
                if ('scope' in auth) {
                    auth.scope = auth.scope.split(" ");
                }
                auth.expiration = Date.now() + (auth.expires_in * 1000);
                ConnectStore.set('user', auth);
                window.location.href = "/";
                return;
            }
            this.base = appBase;
            this.service = serviceName;
            this.connect = connectBase;
            this.scopes = scopes;
            this.clientId = clientId;
            var user = ConnectStore.get('user');
            if (!user) {
                getToken($rootScope, $http, ConnectStore);
                return;
            } else {
                $rootScope.user = user;
                $rootScope.appLoaded = true;
            }
            $rootScope.logout = function () {
                $rootScope.user = null;
                ConnectStore.remove('user');
                window.location.href = connectBase + '/logout';
            };
        }]);

})();
