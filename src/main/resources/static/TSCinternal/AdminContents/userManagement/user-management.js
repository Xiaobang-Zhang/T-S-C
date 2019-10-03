/**
 * Created by Maple on 2017/4/13.
 */
angular.module(window.tsc.constants.DASHBOARD_APP).component('userManagement', {
    templateUrl : '/TSCinternal/AdminContents/userManagement/user-management.html',
    controller : function($http, NgTableParams, $q, toastr, serverService) {
		var ctrl = this;
        // Init ctrl
        ctrl.DATE_FORMAT = window.tsc.constants.DATE_FORMAT;
        ctrl.editableMode = window.tsc.constants.USER_INFO_MODE.ADMIN_MODE;
        ctrl.isFormValid;
        ctrl.isAccountIdValid = true;
        ctrl.selectedUserIdList = [];
        ctrl.clickedUser = null;
        
        getAllUsers();

		ctrl.tableParams = getTableParams();
		ctrl.getUserRoleCategory = getAllUserRoleCategory;
		
        // The list of user role codetable
		ctrl.userRoleCategoryList = {};

		// Fot ng-table edit
		ctrl.cancel = cancel;
		ctrl.del = delRowInTable;
		ctrl.save = saveRow;

		function getTableParams() {
			var initialParams = {
				count: 10,
				sorting: {createdDt: "desc"}
			};
			var initialSettings = {
				counts: [10, 25, 50, 100],
				paginationMaxBlocks: 13,
				paginationMinBlocks: 2,
				dataset: ctrl.users
			};
			return new NgTableParams(initialParams, initialSettings);
		}

		ctrl.checkAccountId = function(row){
			var defered = $q.defer();
			
			// Compatible the case that accountId fied is empty 
			if(typeof row.accountId == 'undefined'){
				defered.reject(false);
				return defered.promise;
			}

			// Get the original row
            var index = _.findIndex(ctrl.originalData, function(r){
                return r.id === row.id;
            });
            
            if(index >= 0){
            	// check if the original row's accountId is similar with the new row when original row existed   
    			var originAccountId = ctrl.originalData[index].accountId;
    			
    			if(originAccountId == row.accountId){
    				ctrl.isAccountIdValid = true;
    				defered.resolve(true);
    				return defered.promise;
    			}
            }

			// Check the accountId
			$http.get('/user//checkAccountId',{
				params : {
					accountId : row.accountId
				}
			}).success(function(response){
               ctrl.isAccountIdValid = response;
               defered.resolve(response);
            }).error(function(response){
                ctrl.isAccountIdValid = false;
                toastr.error("检查 学（工）号 失败！");
                defered.reject(false);
            });
			
			return defered.promise;
		}
		
// -------------- Add function And Delte function ------------------------
		ctrl.checkSelectStatus = function(event,row) {
			if(event.currentTarget.checked) {
				// add its id to list
				var index = _.indexOf(ctrl.selectedUserIdList, row.id);
				if(index < 0) {
					ctrl.selectedUserIdList.push(row.id);
				}
			}
			else {
				// delete its id from list
				_.remove(ctrl.selectedUserIdList, function(rowId){
					return rowId == row.id;
				});

			}
		};
		
		/**
		 * Delete users in selectedUserIdList
		 */
		ctrl.deleteUser = function() {
			if(ctrl.selectedUserIdList.length > 0){
				// Delete users from server
                var promise = deleteUsersFromServer(ctrl.selectedUserIdList);

                promise.then(function(resolve){
                    // delete user success
                    
                    // remove these users in ng-table
                    _.remove(ctrl.tableParams.settings().dataset, function (item) {
                        return  _.indexOf(ctrl.selectedUserIdList, item.id) >= 0;
                    });
                    
                    // Do not need to change originalData
//                    ctrl.originalData = angular.copy(ctrl.tableParams.settings().dataset);
                    // reload ng-table
        			ctrl.tableParams.reload().then(function (data) {
        				if (data.length === 0 && ctrl.tableParams.total() > 0) {
        					ctrl.tableParams.page(ctrl.tableParams.page() - 1);
        					ctrl.tableParams.reload();
        				}
        			});
                    // initialize the list
        			ctrl.selectedUserIdList = [];
                }, function(reject){
                    toastr.error("删除用户信息失败！", "Server Error:");
                });
			}
		};
		
		/**
		 * Check if checkbox of all rows is checked.
		 */
		ctrl.isSelected = function(row) {
			return _.indexOf(ctrl.selectedUserIdList, row.id) >= 0;
		}
// -------------- Functions About operations on ng-table ---------------------------------
        function saveRow(row, rowForm) {
            // Check the account id
            var promise = ctrl.checkAccountId(row);
            
            promise.then(function(isValid){
            	if(isValid == true) {
                    // Get the original data
                    var originalRow = resetRow(row, rowForm);              
                    // Update the user in server
                    $http.post('/user/updateUser', row).success(function(response){
                        // Update the row in ngtable
                        angular.extend(originalRow, row);
                    }).error(function(response){
                    	angular.extend(row, originalRow);
                        console.log(response);
                        toastr.error('更新用户数据失败');
                    });
            	}
            });
        }

        function cancel(row, rowForm) {
            var originalRow = resetRow(row, rowForm);
            angular.extend(row, originalRow);
        }

		function delRowInTable(row) {
			_.remove(ctrl.tableParams.settings().dataset, function (item) {
				return row === item;
			});
			ctrl.tableParams.reload().then(function (data) {
				if (data.length === 0 && ctrl.tableParams.total() > 0) {
					ctrl.tableParams.page(ctrl.tableParams.page() - 1);
					ctrl.tableParams.reload();
				}
			});
		}

		function resetRow(row, rowForm) {
			row.isEditing = false;
			rowForm.$setPristine();
			ctrl.tableTracker.untrack(row);
            var index = _.findIndex(ctrl.originalData, function(r){
                return r.id === row.id;
            });
			return ctrl.originalData[index];
		}
// ------------------ Model functions  ----------------------------------
		
		ctrl.openAddUserWithDetail = function(){
			var userWithDetail = {};
			userWithDetail.userDetail = {};
			// set default photo path
			userWithDetail.userDetail.picturePath = '/global/img/bio-default.png';
            ctrl.clickedUser = userWithDetail;
			// Open modal to display user detail
            $('#userDetailModal').modal('show');
		};
		
		ctrl.openUserDetail = function(row) {
			// Get user detail 
			$http.get('/user/getUserDetailById', {
				params : {
					userId : row.id
				}
			}).success(function(response){
				row.userDetail = response;
				row.userDetail.birthday == null ? null : row.userDetail.birthday = new Date(row.userDetail.birthday);
				ctrl.clickedUser = {};
				angular.copy(row, ctrl.clickedUser);
				
	            // get password
	    		serverService.getUserPasswordById(row.id).success(function(response){
	    			ctrl.clickedUser.password = response;
	    		}).error(function(response){
	    			toastr.error('获取该用户密码失败！', 'Server Error：');
	    		});
	    		
				// Open modal to display user detail
                $('#userDetailModal').modal();
				
			}).error(function(response){
				toastr.error("获取用户信息失败！", 'Server Error：');
			});

		}
		
		/**
		 * Save the user information and send to server for modal
		 */ 
		ctrl.saveUserWithDetail = function(userWithDetail) {
            // Check the account id
            var promise = ctrl.checkAccountId(userWithDetail);
            promise.then(function(isValid){

            	if(isValid == true) {
                    // Check if the action is adding user or update user
                    if(userWithDetail.id == null){
                        // Add user
                        var promise = addUserToServer(userWithDetail);
                        promise.then(function(resolve){
                            // success
                            // Add the row into ngtable
                        	ctrl.tableParams.settings().dataset.push(resolve);
                            ctrl.originalData.push(angular.copy(resolve));
                            $('#userDetailModal').modal('hide');
                            ctrl.clickedUser = null;
                        }, function(reject){
                           toastr.error("添加用户数据失败！", "Server Error");
                        });
                        return ;
                    }
                    else {
                        // Get the original data
                        var index = _.findIndex(ctrl.originalData, function(r){
                            return r.id === userWithDetail.id;
                        });
                        // update password
                        if(typeof(userWithDetail.password) != 'undefined' || userWithDetail.password != null){
                        	serverService.updateUserPasswordByUserId(userWithDetail.id, userWithDetail.password).error(function(response){
                        		toastr.error('更新用户密码失败！', 'Server Error:');
                        	})
                        }
                        // Update the user in server
                        $http.post('/user/updateUserWithDetail', userWithDetail).success(function(response){
                            $('#userDetailModal').modal('hide');
                            // Update the row in ngtable
                            angular.extend(ctrl.tableParams.settings().dataset[index], userWithDetail)
                            angular.extend(ctrl.originalData[index], userWithDetail);
                            
                        }).error(function(response){
                            toastr.error('更新用户数据失败！', 'Server Error:');
                        });
                    }
            	}
            	else {
            		toastr.error("添加用户数据失败！学（工）号已存在！", "Operation Error");
            	}
            });
            
		};

// ------------------------- Functions Interact with server -------------------------------
		/**
		 * Add user to server
		 * 
		 * @param user
		 * @returns promise that include user with id if success, otherwise null.
		 */
		function addUserToServer(user) {
			var defered = $q.defer();
			
			$http.post('/user/addUser',user).success(function(response){
				// change user birthday to type Date
				if(response.userDetail != null && response.userDetail.birthday != null){
					response.userDetail.birthday = new Date(response.userDetail.birthday);
				}
                defered.resolve(response);
			}).error(function(response){
				defered.reject(null);
			});
            return defered.promise;
		};

		/**
		 * Get all user base information without detail.
		 * 
		 * @returns
		 */
		function getAllUsers() {
			$http.get('/user/getAllUsers').success(function (response) {
				ctrl.users = response;
				ctrl.tableParams.settings({
					dataset: ctrl.users
				});
				ctrl.originalData = angular.copy(ctrl.users);
			}).error(function (response) {
				toastr.error("Get all users fail!");
			});
		}

		function getAllUserRoleCategory() {
			var defered = $q.defer();
			$http.get('/user/getAllUserRoleCategory', {
				cache : true
			}).success(function (response) {
				ctrl.userRoleCategoryList = response;
				var userRoleCategoryList = new Array();
				for (var i = 0; i < response.length; i++) {
					var item = {};
					item.id = response[i].displayValue;
					item.title = response[i].displayValue;
					userRoleCategoryList[i] = item;
				}
				defered.resolve(userRoleCategoryList);
			}).error(function (response) {
				defered.reject(response);
			});
			return defered.promise;
		}

        /**
         * Delete users in rowIdList.
         *
         * @param rowIdList
         * @returns {Promise} with true if success, otherwise false.
         */
		function deleteUsersFromServer(rowIdList){
			var defered = $q.defer();
			
			$http.post('/user/deleteUsersByIdList', rowIdList).success(function(response){
               defered.resolve(true);
            }).error(function(response){
                defered.reject(false);
            });
            return defered.promise;
		}
	}
});