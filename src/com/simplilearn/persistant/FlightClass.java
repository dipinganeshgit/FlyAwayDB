package com.simplilearn.persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FlightClass")
public class FlightClass {

	
	public FlightClass(String className) {
		super();
		this.className = className;
	}

	@Id
	@GeneratedValue(strategy= GenerationType.TABLE,generator="native")
	@GenericGenerator(name = "native",strategy = "native")	
	@Column(name = "id")
	private int id;
	
	@Column(name = "className")
	private String className;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	

	
}
