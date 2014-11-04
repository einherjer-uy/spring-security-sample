package org.einherjer.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;


public class SampleObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public SampleObjectMapper() {
        super();
        this.registerModule(new JodaModule());
        this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}

