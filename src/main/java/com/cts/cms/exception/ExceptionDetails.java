package com.cts.cms.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionDetails {

	/**
	 * Stores the time when the exception has occur
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeStamp;

	/**
	 * Stores the status of the Exception
	 */
	private HttpStatus status;

	/**
	 * Stores the message of the Exception
	 */
	private String message;
}