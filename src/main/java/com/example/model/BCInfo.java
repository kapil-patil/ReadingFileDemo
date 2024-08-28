package com.example.model;

import lombok.Data;
import lombok.Getter;

public class BCInfo {

	private String bcId;
	private IntialLoadInfo lodInfo;

	public BCInfo() {
		new IntialLoadInfo();
	}

	public String getBcId() {
		return bcId;
	}

	public void setBcId(String bcId) {
		this.bcId = bcId;
	}

	public IntialLoadInfo getLodInfo() {
		return lodInfo;
	}

	public void setLodInfo(IntialLoadInfo lodInfo) {
		this.lodInfo = lodInfo;
	}
	
	

}
