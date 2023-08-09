package com.simplilearn.persistant;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Itinerary")
public class Itinerary {

	public Itinerary(String departureTime, String arrivaltime) {
		super();
		this.departureTime = departureTime;
		this.arrivaltime = arrivaltime;
	}
	
	public Itinerary(){}


	@Id
	@GeneratedValue(strategy= GenerationType.TABLE,generator="native")
	@GenericGenerator(name = "native",strategy = "native")	
	@Column(name = "id")
	private int id;
	
	@Column(name = "departureTime")
	private String departureTime;
	
	@Column(name = "arrivaltime")
	private String arrivaltime;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="iid")
	@OrderColumn(name="type")
	private List<FlightClass> flightClassess;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivaltime() {
		return arrivaltime;
	}

	public void setArrivaltime(String arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

	public List<FlightClass> getFlightClassess() {
		return flightClassess;
	}

	public void setFlightClassess(List<FlightClass> flightClassess) {
		this.flightClassess = flightClassess;
	}
	
	
}
