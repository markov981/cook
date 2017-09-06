package com.libertymutual.goforcode.cook.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Instruction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	// ???????
	private String description;
	
	
//	@JsonIgnore 
	@ManyToOne
	private Recipe recipe;
	
	
	public Instruction() {}
	
	public Instruction(String descr) {
		description = descr;
	}
}