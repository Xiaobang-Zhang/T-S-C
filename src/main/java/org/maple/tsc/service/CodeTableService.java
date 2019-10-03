package org.maple.tsc.service;

import java.util.List;

import org.maple.tsc.exception.TSCException;
import org.maple.tsc.models.CodetableModel;

public interface CodeTableService {
	
	/**
	 * Select a codetable record by id.
	 * 
	 * @param id
	 * @return
	 * @throws TSCException
	 */
	CodetableModel selectById(Long id) throws TSCException;
	
	/**
	 * Select a codetable record by name
	 * 
	 * @param name
	 * @return
	 * @throws TSCException
	 */
	CodetableModel selectByName(String name) throws TSCException;
	
	/**
	 * Select a list of codetable records by category.
	 * 
	 * @param category
	 * @return
	 * @throws TSCException
	 */
	List<CodetableModel> selectListByCategory(String category) throws TSCException;

}
