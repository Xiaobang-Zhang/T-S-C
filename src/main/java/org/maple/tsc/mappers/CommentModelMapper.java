package org.maple.tsc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.maple.tsc.models.CommentModel;

public interface CommentModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int insert(CommentModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int insertSelective(CommentModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    CommentModel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int updateByPrimaryKeySelective(CommentModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Wed Apr 05 21:26:36 CST 2017
     */
    int updateByPrimaryKey(CommentModel record);
    
    // Below is customized methods
    
    List<CommentModel> selectListByTopicId(Long topicId);
    
    List<CommentModel> selectListByOwnerId(Long ownerId);
    
    int deleteByOwnerIdAndTopicId(@Param("ownerId") Long ownerId, @Param("topicId") Long topicId);
    
    List<CommentModel> selectListWithOwnerDetailAndSuperCommentByTopicId(Long topicId);
}