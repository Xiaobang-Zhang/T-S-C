package org.maple.tsc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.TopicModel;

public interface TopicService {

	/**
	 * Get a list of topic record by user id.
	 * 
	 * @param userId
	 * @return
	 * @throws TSCException
	 */
	List<TopicModel> selectListByUserId(Long userId) throws TSCException;
	
	/**
	 * Get a list of topic record by course id.
	 * 
	 * @param courseId
	 * @return
	 * @throws TSCException
	 */
	List<TopicModel> selectListByCourseId(Long courseId) throws TSCException;
	
	/**
	 * Insert a topic.
	 * 
	 * @param record
	 * @return
	 * @throws TSCException
	 */
	int insert(TopicModel record) throws TSCException;
	
	/**
	 * Delete a record by id.
	 * 
	 * @param id
	 * @return
	 * @throws TSCException
	 */
	int deleteById(Long id) throws TSCException;
	
	/**
	 * Get all topic records with user detail model.
	 * 
	 * @return
	 */
	List<TopicModel> getAll();
	
	TopicModel getTopicById(Long topicId) throws TSCException;
	
	Integer updateTopic(TopicModel topic) throws TSCException;
	
	Integer addTopic(TopicModel topic) throws TSCException;
	
	/**
	 * Get the last {count} topics associated with the user with id {userId}.
	 * 
	 * @param userId
	 * @param count
	 * @return
	 * @throws TSCException 
	 */
	List<TopicModel> getTopicListByUserIdAndCount(Long userId, Integer count) throws TSCException;
	
	/**
	 * Get a list of topics of top #{count}.
	 * 
	 * @param count
	 * @return
	 */
	List<TopicModel> getTopicListTopByCount(@Param("count") Integer count);
}
