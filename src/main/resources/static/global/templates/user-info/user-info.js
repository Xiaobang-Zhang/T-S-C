angular.module('userInfo',['serverService'])
.directive('userInfo',function(){

    return {
        restrict : 'EA',
        templateUrl : '../../global/templates/user-info/user-info.html',
        scope : {
            mode : '=mode',
            userWithDetail : '=userWithDetail',
            isFormValid : '=isFormValid',	// For share the form status with outside
            userRoleCategory : '='			// For display user role
        },
        link : function(scope, ele, attrs) {
            // init
        	scope.DATE_FORMAT = window.tsc.constants.DATE_FORMAT;
            scope.nowDate = new Date();
            
            // check the mode
            if(scope.mode == window.tsc.constants.USER_INFO_MODE.USER_MODE){
                // The user editable
            	scope.isBaseEditable = false;
            	scope.isDetailEditable = true;
                $('.user-info .user-header img')[0].title = "更换头像";
            }
            else if(scope.mode == window.tsc.constants.USER_INFO_MODE.ADMIN_MODE){
                // The Admin editable
            	scope.isBaseEditable = true;
            	scope.isDetailEditable = true;
                $('.user-info .user-header img')[0].title = "更换头像";
                
            }
            else if(scope.mode ==  window.tsc.constants.USER_INFO_MODE.VISTOR_MODE) {
                // The Vistor editable mode
            	scope.isBaseEditable = false;
            	scope.isDetailEditable = false;
            }
            
            // Watch the form status
            scope.$watch('userDetailForm.$invalid', function(value){
            	if(value == scope.isFormValid)
            		return ;
            	scope.isFormValid = value;
            });
        },
        controller : function($scope, serverService, toastr){

        		
        	// ------------- Block about uploading picture
        	var uploadEle =document.querySelector('#userPicFileInput');
        	uploadEle.onchange = function(){
        		serverService.uploadPicture(uploadEle).success(function(response){
        			$scope.userWithDetail.userDetail.picturePath = response.fileName;
        		}).error(function(response){
        			toastr.error('图片过大或连接服务器失败！', '上传头像失败：');
        		});
	        }
        	
        	$scope.uploadPic = function(){
        		if($scope.isDetailEditable)
        			uploadEle.click();
        	}
        }
    };

});