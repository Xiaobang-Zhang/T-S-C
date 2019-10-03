/**
 * 
 */
/**
 * @author Maple
 *
 */
package org.maple.tsc.mappers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.maple.tsc.BaseTest;
import org.maple.tsc.models.CodetableModel;
import org.springframework.beans.factory.annotation.Autowired;


public class CodetableModelMapperTest extends BaseTest{
	
	@Autowired
	CodetableModelMapper mapper;
	
	@Test
	public void selectByNameTest(){
		
		CodetableModel result = mapper.selectByName("TEACHER");
		
		assertEquals(result != null, true);
	}
}