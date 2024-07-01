package com.example.demo.model;

public class JsonModel {
	private String fileName;
	private long fileSize;
	
	public String getFileName() {
		return this.fileName;
	}
	public void setFileName(String name) {
		this.fileName = name;
	}
	public long getFileSize() {
		return this.fileSize;
	}
	public void setFileSize(long size) {
		this.fileSize = size;
	}
	public JsonModel() {}
	public JsonModel(String name, long size) {
		fileName = name;
		fileSize = size;
	}
}
