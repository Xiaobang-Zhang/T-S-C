package org.maple.tsc.mappers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.maple.tsc.BaseTest;
import org.maple.tsc.models.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentModelMapperTest extends BaseTest{
	
	@Autowired
	CommentModelMapper mapper;
	
	@Test
	public void selectWithInfoTest() {
		List<CommentModel> result = mapper.selectListWithOwnerDetailAndSuperCommentByTopicId(1L);
		
		assertEquals(result.get(0).getOwnerDetailModel() != null, true);
	}

}
