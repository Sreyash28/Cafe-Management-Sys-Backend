package com.cts.cms.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// it's a Data Trasfer Object for registration
	String userName;
	String contactNumber;
	String email;
	String password;
}