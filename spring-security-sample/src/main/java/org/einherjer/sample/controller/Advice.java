package org.einherjer.sample.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class Advice {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Advice.class);
	
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionBody handleIllegalArgumentException(IllegalArgumentException e) {
        return new ExceptionBody(e.getMessage()/*, ExceptionUtils.getStackTrace(e)*/);
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionBody handleSecurityException(SecurityException e) {
        return new ExceptionBody(e.getMessage()/*, ExceptionUtils.getStackTrace(e)*/);
    }
    
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionBody handleUnsupportedOperationException(UnsupportedOperationException e) {
        return new ExceptionBody(e.getMessage()/*, ExceptionUtils.getStackTrace(e)*/);
    }
 
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ExceptionBody handleAccessDeniedException(AccessDeniedException e) {
        return new ExceptionBody(e.getMessage()/*, ExceptionUtils.getStackTrace(e)*/);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionBody handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ExceptionBody(e.getMessage()/*, ExceptionUtils.getStackTrace(e)*/);
    }
    
    //hack to be able to debug bad request exceptions that doesn't show up in the log
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionBody catchAllHandle(Throwable e) {
        log.error("unexpected error handled by " + this.getClass(), e);
        return new ExceptionBody(e.getMessage()/*, ExceptionUtils.getStackTrace(e)*/);
    }
    
}
