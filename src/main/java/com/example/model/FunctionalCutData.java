package com.example.model;

import java.util.ArrayList;
import java.util.List;


public class FunctionalCutData {

	public static final Integer expectCount = 3;
	public  Integer receivedCount = 0;
	public List<String> fileList = new ArrayList<String>();
	public List<String> getFileList() {
		return fileList;
	}
	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}
	public static Integer getExpectcount() {
		return expectCount;
	}
	public Integer getReceivedCount() {
		return receivedCount;
	}
	public void setReceivedCount(Integer receivedCount) {
		this.receivedCount = receivedCount;
	}

	
	

}
