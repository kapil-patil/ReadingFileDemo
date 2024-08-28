package com.example.model;

import java.util.HashMap;


public class IntialLoadInfo {

	public static  HashMap<String, FunctionalCutData> ilMetaData = new HashMap<String, FunctionalCutData>();
	
	public IntialLoadInfo() {
		ilMetaData.put("partner_br", new FunctionalCutData());
	}
	
	

}
