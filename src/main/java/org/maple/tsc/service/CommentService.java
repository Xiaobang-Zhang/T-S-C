package org.maple.tsc.service;

import java.util.List;

import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CommentModel;

public interface CommentService {

	CommentModel selectById(Long id) throws TSCException;
	
	List<CommentModel> selectListByTopicId(Long topicId) throws TSCException;
	
	List<CommentModel> selectListByOwnerId(Long ownerId) throws TSCException;
	
	int insert(CommentModel record) throws TSCException;
	
	int deleteById(Long id) throws TSCException;
	
	int deleteByOwnerIdAndTopicId(Long ownerId, Long topicId) throws TSCException;
	
	List<CommentModel> getListWithOwnerDetailAndSuperCommentByTopicId(Long topicId) throws TSCException;
	
	Integer update(CommentModel record) throws TSCException;
}
