package org.maple.tsc.mappers;

import org.junit.Test;
import org.maple.tsc.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentCourseXrefModelMapperTest extends BaseTest {
	
	@Autowired
	StudentCourseXrefModelMapper mapper;
	
	@Test
	public void deleteByStudentIdAndCourseIdTest() {
		int num = mapper.deleteByStudentIdAndCourseId(1L, 1L);
		
		System.out.println(num);
	}

}
