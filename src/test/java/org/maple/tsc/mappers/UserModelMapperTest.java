package org.maple.tsc.mappers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.maple.tsc.BaseTest;
import org.maple.tsc.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

public class UserModelMapperTest extends BaseTest{

	@Autowired
	UserModelMapper mapper;
	
	@Test
	public void selectByAccountIdTest() {
		UserModel user = mapper.selectByAccountId(1L);
		
		assertEquals(user != null, true);
	}
	
	@Test
	public void insertTest() {
		UserModel record = new UserModel();
		record.setAccountId(1L);
		record.setName("sdf");
		record.setUserRole(1L);
		
		int result = mapper.insert(record);
		System.out.println(result);
	}
}
