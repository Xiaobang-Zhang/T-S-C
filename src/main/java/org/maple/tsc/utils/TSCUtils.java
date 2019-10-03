/**
 * 
 */
/**
 * @author Maple
 *
 */
package org.maple.tsc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.maple.tsc.config.ConfigProperties;
import org.maple.tsc.exception.TSCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TSCUtils {
	
	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(TSCUtils.class);
	
	@Autowired
	ConfigProperties configProperties;
	
	/**
	 * Store the picture in file system and return file name.
	 * 
	 * @param picture
	 * @return file name.
	 * @throws TSCException
	 */
	public String storeUserPicture(MultipartFile picture) throws TSCException{
		final int BUFFER_SIZE = 1024;
		String picturePath = configProperties.getPicturePath();
		String pictureName = "img-" + new Date().getTime() + "." + getFileSuffix(picture.getOriginalFilename());
		
		File imgUri = new File(picturePath, pictureName);
		
		if(!imgUri.getParentFile().exists()){
			imgUri.getParentFile().mkdirs();
		}

		InputStream picInputStream = null;
		OutputStream outStream = null;
		try {
			while(!imgUri.createNewFile()){
				pictureName = "img-" + new Date().getTime() + "." + getFileSuffix(picture.getName());
				imgUri = new File(picturePath, pictureName);
			}
			picInputStream = picture.getInputStream();
			outStream = new FileOutputStream(imgUri);
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int count = 0;
			while((count = picInputStream.read(buffer)) > 0){
				outStream.write(buffer, 0, count);
			}
			
			
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new TSCException(e.getMessage());
		}
		finally {
			IOUtils.closeQuietly(picInputStream);
			IOUtils.closeQuietly(outStream);
		}
		return pictureName;
	}
	
	public InputStream getUserPicture(String pictureName) throws TSCException{
		String picturePath = configProperties.getPicturePath();
		if(null == pictureName){ // Get default picture
			pictureName = configProperties.getDefaultPictureName();
		}
		
		try {
			File imgUri = new File(picturePath, pictureName);
			if(!imgUri.canRead()){
				logger.error("Can't read file:" + imgUri.getAbsolutePath());
				throw new TSCException("Can't read file:" + imgUri.getAbsolutePath());				
			}
			InputStream in = new FileInputStream(imgUri);
			return in;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			throw new TSCException(e.getMessage());		
		}
		
	} 
	
	public String getFileSuffix(String name){
		return name.substring(name.lastIndexOf(".") + 1);
	}
}