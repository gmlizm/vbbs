package com.aboo.vbbs.test.test.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Param {
	
	private byte byteData;
	private short shortDate;
	private int intData;
	private long longData;
	private float floatData;
	private double doubleData;
	
	private boolean boolData;
	private char charData;

	private Object objData;
	private String[] arrData;
	private List<String> listData;
	private Map<String,Object> mapData;
}
