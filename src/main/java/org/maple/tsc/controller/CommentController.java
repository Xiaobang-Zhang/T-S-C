package org.maple.tsc.controller;

import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CommentModel;
import org.maple.tsc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Transactional(value = "transactionManager", readOnly = false, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@RestController
@RequestMapping(value = "/comment")
public class CommentController extends BaseController{
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(value = "/updateComment", method = RequestMethod.POST)
	public Integer updateComment(@RequestBody CommentModel comment) throws TSCException{
		return commentService.update(comment);
	}
	
	@PostMapping("/addComment")
	public Integer addComment(@RequestBody CommentModel comment) throws TSCException{
		return commentService.insert(comment);
	}
}
