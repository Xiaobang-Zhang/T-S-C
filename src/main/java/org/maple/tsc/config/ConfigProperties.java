package org.maple.tsc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tsc", ignoreUnknownFields = false)
public class ConfigProperties {

	private String dbDriverClassName;
	private String dbUrl;
	private String dbUserName;
	private String dbPassword;
	private String dbMapperXml;
	
	private String picturePath;
	private String defaultPictureName;
	
	
	public String getDbDriverClassName() {
		return dbDriverClassName;
	}
	public void setDbDriverClassName(String dbDriverClassName) {
		this.dbDriverClassName = dbDriverClassName;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getDbMapperXml() {
		return dbMapperXml;
	}
	public void setDbMapperXml(String dbMapperXml) {
		this.dbMapperXml = dbMapperXml;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getDefaultPictureName() {
		return defaultPictureName;
	}
	public void setDefaultPictureName(String defaultPictureName) {
		this.defaultPictureName = defaultPictureName;
	}
}
