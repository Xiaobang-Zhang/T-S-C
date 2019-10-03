package org.maple.tsc.controller;

import java.util.List;

import org.maple.tsc.constants.CommonConstants;
import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CodetableModel;
import org.maple.tsc.models.TopicModel;
import org.maple.tsc.service.CodeTableService;
import org.maple.tsc.service.CommentService;
import org.maple.tsc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@RestController
@RequestMapping("/topic")
public class TopicController extends BaseController{

	@Autowired
	TopicService topicService;
	
	@Autowired
	CodeTableService CodeTableService;
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(value = "/getAllTopics", method = RequestMethod.GET)
	public List<TopicModel> getAllTopics(){
		return topicService.getAll();
	}
	
	@RequestMapping(value = "/getTopicDetailById", method = RequestMethod.GET)
	public TopicModel getAllTopics(@RequestParam("topicId") Long topicId) throws TSCException{
		return topicService.getTopicById(topicId);
	}
	
	@RequestMapping(value = "/updateTopic", method = RequestMethod.POST)
	public Integer updateTopic(@RequestBody TopicModel topic) throws TSCException{
		return topicService.updateTopic(topic);
	}
	
	@RequestMapping(value = "/getTopicListByCourseId", method = RequestMethod.GET)
	public List<TopicModel> getTopicListByCourseId(@RequestParam("courseId") Long courseId) throws TSCException{
		return topicService.selectListByCourseId(courseId);
	}
	
	@PostMapping("/addTopic")
	public Integer addTopic(@RequestBody TopicModel topic) throws TSCException{
		return topicService.addTopic(topic);
	}
	
	@GetMapping("/getAllTopicCategorys")
	public List<CodetableModel> getAllTopicCategorys() throws TSCException{
		return CodeTableService.selectListByCategory(CommonConstants.CODETABLE_CATEGORY_TOPIC_CATEGORY);
	}
	
	@GetMapping("/getTopicListByUserId")
	public List<TopicModel> getTopicListByUserId(@RequestParam("userId") Long userId) throws TSCException{
		return topicService.selectListByUserId(userId);
	}
	
	@GetMapping("/getTopicListOfCourseTrendByUserId")
	public List<TopicModel> getTopicListOfCourseTrendByUserId(@RequestParam("userId") Long userId) throws TSCException{
		int count = 10;
		return topicService.getTopicListByUserIdAndCount(userId, count);
	}	
	
	@GetMapping("/getTopicListTop")
	public List<TopicModel> getTopicListTop(@RequestParam("count") Integer count) throws TSCException{
		return topicService.getTopicListTopByCount(count);
	}
}
 