TechVortex.service('AccountService', function($http) {
    var serviceUrl = '/api/profile';
    
    this.getSessionId = function() {
        return $http.get(serviceUrl + '/session');
    };
    
    this.updateAccount = function(userName, profile) {
        return $http.put(serviceUrl + '/' + userName, profile);
    };
    
    this.updateAvatar = function(userName, file) {
        var formData = new FormData();
        formData.append('file', file);

        return $http.post(serviceUrl + '/' + userName + '/avatar', formData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
    };
});

