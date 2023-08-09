package com.simplilearn.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.simplilearn.persistant.Airline;
import com.simplilearn.persistant.FlightClass;
import com.simplilearn.persistant.Itinerary;
import com.simplilearn.persistant.Route;

public class AirlinesDao {

	public static void main(String[] args) {
	
		
		
		AirlinesDao airlinesDao = new AirlinesDao();
//		airlinesDao.createCompleteSetDatabase();
//		
		Airline airline = airlinesDao.createAirline("Fly Dubai", "Economical Flights");
		Route route = airlinesDao.createRoute("Dubai", "Kannur", airline);
		Itinerary itinerary = airlinesDao.createItinerary("10:00", "14:00", route);
		
		
	}
	
	void createCompleteSetDatabase()
	{
		DatabaseMappingHelper databaseMappingHelper = new DatabaseMappingHelper();

		SessionFactory sf = databaseMappingHelper.getDBCurrentSessionFactory();

		Session session = sf.openSession();
		
		Transaction t = session.beginTransaction();
		
		
		Airline airline = new Airline("Fly Dubai", "Economical Flight");
		
		Route route = new Route("Dubai", "Kannur");
		
		Itinerary itinerary = new Itinerary("10:00", "14.00");
		
		FlightClass flightClass = new FlightClass("Economical");
		
		List<Route> routes = new ArrayList<Route>();
		List<Itinerary> itineraries = new ArrayList<Itinerary>();
		List<FlightClass> flightClasses = new ArrayList<FlightClass>();
		
		routes.add(route);
		itineraries.add(itinerary);
		flightClasses.add(flightClass);
		itinerary.setFlightClassess(flightClasses);
		route.setItineraries(itineraries);
		airline.setRoutes(routes);
		
		session.persist(airline);
		t.commit();
		session.close();
	    sf.close();
		
	}
	
	Airline createAirline(String name, String description)
	{
		
		DatabaseMappingHelper databaseMappingHelper = new DatabaseMappingHelper();

		SessionFactory sf = databaseMappingHelper.getDBCurrentSessionFactory();

		Session session = sf.openSession();
		
		Transaction t = session.beginTransaction();
		
		System.out.println("Use Airline Filter");
		
		Filter airlineFilter = session.enableFilter("airlineFilter");
		airlineFilter.setParameter("nameParam", name);
		org.hibernate.query.Query query1 = session.createQuery("select p from Airline p");
		List<Airline> airlines = query1.getResultList();
		

		
		Airline createdAirline = null;
		if (airlines.isEmpty()) {
			createdAirline = new Airline(name, description);
		}
		else {
			
	        ListIterator<Airline> airlinesIterator
            = airlines.listIterator();
	        while (airlinesIterator.hasNext()) {
	        	createdAirline = airlinesIterator.next();
	        }
		}
		
		session.save(createdAirline);
		t.commit();
		session.close();
	    sf.close();
	    
	    return createdAirline;
	}
	
	Route createRoute(String source, String destination, Airline airline)
	{
		
		DatabaseMappingHelper databaseMappingHelper = new DatabaseMappingHelper();

		SessionFactory sf = databaseMappingHelper.getDBCurrentSessionFactory();

		Session session = sf.openSession();
		
		Transaction t = session.beginTransaction();
		
		System.out.println("Current Route == "+airline.getRoutes().size()+ " "+airline.getRoutes());

		List<Route> routes = airline.getRoutes().stream().filter(p -> (p.getSource().equals(source) & p.getDestination().equals(destination))).collect(Collectors.toList());

		Route createdRoute = null;
		List<Route>currentRoutes = airline.getRoutes();

		if (routes.size() > 0) {
			ListIterator<Route> routesIterator
	            = routes.listIterator();
		        while (routesIterator.hasNext()) {
		        	createdRoute = routesIterator.next();
		        }
		}
		else {
			createdRoute = new Route(source, destination);
			currentRoutes.add(createdRoute);

		}

		System.out.println("Current Route new == "+currentRoutes.size()+ " "+currentRoutes);

		airline.setRoutes(currentRoutes);
		session.update(airline);
		t.commit();
		session.close();
	    sf.close();
	    
	    return createdRoute;
	}
	
	Itinerary createItinerary(String departureTime, String arrivaltime , Route route)
	{
		
		DatabaseMappingHelper databaseMappingHelper = new DatabaseMappingHelper();

		SessionFactory sf = databaseMappingHelper.getDBCurrentSessionFactory();

		Session session = sf.openSession();
		
		Transaction t = session.beginTransaction();
		
		System.out.println("Current Itineries == "+route.getItineraries().size()+ " "+route.getItineraries());

		Itinerary createdItinerary = null;
		List<Itinerary>currentItineraries = route.getItineraries();
		List<Itinerary> itineraries = route.getItineraries().stream().filter(p -> (p.getArrivaltime().equals(arrivaltime) & p.getDepartureTime().equals(departureTime))).collect(Collectors.toList());

		if (itineraries.size() > 0) {
		        ListIterator<Itinerary> itineraryIterator
	            = itineraries.listIterator();
		        while (itineraryIterator.hasNext()) {
		        	createdItinerary = itineraryIterator.next();
		        }
		}
		else {
			createdItinerary = new Itinerary(departureTime, arrivaltime);
			currentItineraries.add(createdItinerary);

		}

		route.setItineraries(currentItineraries);
		session.update(route);
		t.commit();
		session.close();
	    sf.close();
	    
	    return createdItinerary;
	}
	
	
	
	
}
