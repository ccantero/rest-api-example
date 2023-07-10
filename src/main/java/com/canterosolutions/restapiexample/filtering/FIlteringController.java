package com.canterosolutions.restapiexample.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FIlteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue filtering( ) {
		ExampleBean exampleBean = new ExampleBean("value1", "value2", "value3");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(exampleBean);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.
				filterOutAllExcept("field4", "field5");
		FilterProvider filters = new SimpleFilterProvider().
				addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list")
	public List<ExampleBean> filteringList() {
		return Arrays.asList(
				new ExampleBean("value1", "value2", "value3"),
				new ExampleBean("value4", "value5", "value6")
			);
	}
}
