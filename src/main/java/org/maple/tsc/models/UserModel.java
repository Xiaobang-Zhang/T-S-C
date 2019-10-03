package org.maple.tsc.models;

import java.util.Date;

public class UserModel extends BaseModel {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.account_id
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    private Long accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.name
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_role
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    private Long userRole;
    
    private String userRoleDisplayValue;
    
    private UserDetailModel userDetail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.created_dt
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    private Date createdDt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.last_left_dt
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    private Date lastLeftDt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.is_active
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    private Boolean isActive;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.account_id
     *
     * @return the value of user.account_id
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.account_id
     *
     * @param accountId the value for user.account_id
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.name
     *
     * @return the value of user.name
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.name
     *
     * @param name the value for user.name
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_role
     *
     * @return the value of user.user_role
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public Long getUserRole() {
        return userRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_role
     *
     * @param userRole the value for user.user_role
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public void setUserRole(Long userRole) {
        this.userRole = userRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.created_dt
     *
     * @return the value of user.created_dt
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.created_dt
     *
     * @param createdDt the value for user.created_dt
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.last_left_dt
     *
     * @return the value of user.last_left_dt
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public Date getLastLeftDt() {
        return lastLeftDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.last_left_dt
     *
     * @param lastLeftDt the value for user.last_left_dt
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public void setLastLeftDt(Date lastLeftDt) {
        this.lastLeftDt = lastLeftDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.is_active
     *
     * @return the value of user.is_active
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.is_active
     *
     * @param isActive the value for user.is_active
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

	public String getUserRoleDisplayValue() {
		return userRoleDisplayValue;
	}

	public void setUserRoleDisplayValue(String userRoleDisplayValue) {
		this.userRoleDisplayValue = userRoleDisplayValue;
	}

	public UserDetailModel getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetailModel userDetail) {
		this.userDetail = userDetail;
	}
}