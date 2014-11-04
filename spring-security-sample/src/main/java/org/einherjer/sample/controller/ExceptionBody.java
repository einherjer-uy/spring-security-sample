package org.einherjer.sample.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Helper model to return to the client of a REST service useful information about server side errors
 *
 */
public class ExceptionBody {

	@JsonProperty("error")
    private String message;
    
    @JsonInclude(Include.NON_NULL)
    private String stackTrace;

    
	public ExceptionBody(String message) {
		super();
		this.message = message;
	}
	public ExceptionBody(String message, String stackTrace) {
		this(message);
		this.stackTrace = stackTrace;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

    
}
