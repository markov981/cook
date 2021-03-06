package com.libertymutual.goforcode.cook.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No set of instructions with the given id was found.") 
public class InstructionNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1654138248009564021L;	
}