package com.simplilearn.persistant;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Route")
public class Route {

	public Route(String source, String destination) {
		super();
		this.source = source;
		this.destination = destination;
	}

	@Id
	@GeneratedValue(strategy= GenerationType.TABLE,generator="native")
	@GenericGenerator(name = "native",strategy = "native")	
	@Column(name = "id")
	private int id;
	
	@Column(name = "source")
	private String source;
	
	@Column(name = "destination")
	private String destination;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="rid")
	@OrderColumn(name="type")
	private List<Itinerary> itineraries;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Itinerary> getItineraries() {
		return itineraries;
	}

	public void setItineraries(List<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}
	
	
}
