package com.canterosolutions.restapiexample.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"field1", "field2"})
@JsonFilter("SomeBeanFilter")
public class ExampleBean {	
	
	private String field1;
	@JsonIgnore
	private String field2;
	private String field3;
	private String field4;
	private String field5;
	private String field6;

	public ExampleBean(String value1, String value2, String value3) {
		this.field1 = value1;
		this.field2 = value2;
		this.field3 = value3;
		this.field4 = value1;
		this.field5 = value2;
		this.field6 = value3;
	}

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}

	public String getField3() {
		return field3;
	}

	public String getField4() {
		return field4;
	}

	public String getField5() {
		return field5;
	}

	public String getField6() {
		return field6;
	}

}
