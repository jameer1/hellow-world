'use strict';

/**
 * @ngdoc service
 */
app.factory('TokenExpirationService', ["ngDialog", "$state", "$rootScope", "stylesService", "slideBar", "blockUI", function (ngDialog, $state, $rootScope, stylesService, slideBar, blockUI) {
    const loginStateName = "site.login";
    const mainStateName = "main";
    let tokenExpiredPopup = null;
    const tokenExpiredMessage = 'Session Expired, Please Login again';
    return {
        validateTokenExpiration: function (errorResponse) {
            if (errorResponse.status === 401) {
                $rootScope.$broadcast('tokenExpired');
                const currentState = $state.current.name;
                if (currentState && currentState !== loginStateName && currentState !== mainStateName) {
                    tokenExpiredPopup && tokenExpiredPopup.close();
                    ngDialog.closeAll();
                    blockUI.stop();
                    tokenExpiredPopup = ngDialog.open({
                        template: 'views/alerts/alert-popup.html',
                        className: 'ngdialog-theme-plain ng-dialogueCustom7',
                        showClose: false,
                        closeByDocument: false,
                        controller: ['$scope', function ($scope, reqService) {
                            $scope.header = 'Info';
                            $scope.message = tokenExpiredMessage;
                            $scope.confirm = function () {
                                $scope.closeThisDialog();
                                $state.go("site.login");
                                // lets just live with it for now!! this line makes the footer line always stays at bottom for login page.
                                $(".content-wrapper").height(stylesService.contentHeight);
                                slideBar.resetSlideBar();
                            }
                        }]
                    });
                }

            }
        }
    }

}]);
