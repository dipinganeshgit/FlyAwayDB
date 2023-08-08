package com.simplilearn.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
		Airline airline = airlinesDao.createAirline("Indigo", "Economical Flights");
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
		
		Criteria query = session.createCriteria( Airline.class );
	    query.add( Restrictions.eq( "name", name ) );
		
		List<Airline> airlines = query.list();
		
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
		
		Criteria query = session.createCriteria( Route.class );
	    query.add( Restrictions.eq( "source", source ) );
	    query.add( Restrictions.eq( "destination", destination ) );

	    Criterion crAux1 = Restrictions.isNull("aid");
	    Criterion crAux2 = (Criterion) query.createCriteria("aid")
	        .add(Restrictions.eq("id", airline.getId()));
	    query.add(Restrictions.or(crAux1, crAux2));

	    
		List<Route> routes = query.list();
		List<Route>currentRoutes = airline.getRoutes();
		Route createdRoute = null;
		if (routes.isEmpty()) {
			createdRoute = new Route(source, destination);
		}
		else {
			
	        ListIterator<Route> routesIterator
            = routes.listIterator();
	        while (routesIterator.hasNext()) {
	        	createdRoute = routesIterator.next();
	        }
		}
		currentRoutes.add(createdRoute);
		airline.setRoutes(currentRoutes);
		session.save(airline);
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
		
		Criteria query = session.createCriteria( Itinerary.class );
	    query.add( Restrictions.eq( "departureTime", departureTime ) );
	    query.add( Restrictions.eq( "arrivaltime", arrivaltime ) );
	    query.add( Restrictions.eq( "rid", route.getId() ) );

		List<Itinerary> itineraries = query.list();
		List<Itinerary>currentItineraries = route.getItineraries();
		Itinerary createdItinerary = null;
		if (itineraries.isEmpty()) {
			createdItinerary = new Itinerary(departureTime, arrivaltime);
		}
		else {
			
	        ListIterator<Itinerary> itineraryIterator
            = itineraries.listIterator();
	        while (itineraryIterator.hasNext()) {
	        	createdItinerary = itineraryIterator.next();
	        }
		}
		currentItineraries.add(createdItinerary);
		route.setItineraries(currentItineraries);
		session.save(route);
		t.commit();
		session.close();
	    sf.close();
	    
	    return createdItinerary;
	}
	
	
	
	
}
