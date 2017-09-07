package com.libertymutual.goforcode.cook.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No recipe with the given title was found.") 
public class TitleNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;	
}