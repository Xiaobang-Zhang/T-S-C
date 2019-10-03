package org.maple.tsc.mappers;

import org.maple.tsc.models.UserDetailModel;

public interface UserDetailModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_detail
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_detail
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int insert(UserDetailModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_detail
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int insertSelective(UserDetailModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_detail
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    UserDetailModel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_detail
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int updateByPrimaryKeySelective(UserDetailModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_detail
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int updateByPrimaryKey(UserDetailModel record);
    
    // Below is customized methods
    
    UserDetailModel selectByUserId(Long userId);
    
    UserDetailModel selectByAccountId(Long accountId);
}