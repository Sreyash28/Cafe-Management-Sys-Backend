package com.cts.cms.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorRespose {
	private Date date;
	private String errorMsg;
	private HttpStatus httpStatus;

}
