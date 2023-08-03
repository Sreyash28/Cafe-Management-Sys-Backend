package com.cts.cms.exception;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorRespose> handleUserNotFoundException(UserNotFoundException e) {
		ErrorRespose errorRespose = new ErrorRespose(new Date(), e.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errorRespose, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MenuItemNotFoundException.class)
	public ResponseEntity<ErrorRespose> handleMenuItemNotFoundException(MenuItemNotFoundException e) {
		ErrorRespose errorRespose = new ErrorRespose(new Date(), e.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errorRespose, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorRespose> handleOrderNotFoundException(OrderNotFoundException e) {
		ErrorRespose errorRespose = new ErrorRespose(new Date(), e.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errorRespose, HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorRespose> handleException(Exception e) {
//		ErrorRespose errorRespose = new ErrorRespose(new Date(), e.getMessage(), HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(errorRespose, HttpStatus.NOT_FOUND);
//	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ExceptionDetails> handleAuthorizationException(AuthorizationException ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.FORBIDDEN,
				ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.FORBIDDEN);
	}

//	    @ExceptionHandler(Exception.class)
//	    public ResponseEntity<ExceptionDetails> handleException(Exception ex) {
//	        ExceptionDetails exceptionDetail;
//	        HttpStatus status;
//	        
//	        if (ex instanceof AuthorizationException) {
//	            exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.FORBIDDEN, ex.getMessage());
//	            status = HttpStatus.FORBIDDEN;
//	        } else {
//	            exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, ex.getMessage());
//	            status = HttpStatus.UNAUTHORIZED;
//	        }
//
//	        log.error(ex.getMessage());
//	        return new ResponseEntity<>(exceptionDetail, status);
//	    }

	/**
	 * 
	 * @param ex
	 * @return ResponseEntity<ExceptionDetails> This method is to handle any type of
	 *         Exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetails> handleGlobalException(Exception ex) {
		ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,
				ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionDetail, HttpStatus.UNAUTHORIZED);
	}
}
