package com.simplilearn.dao;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.simplilearn.persistant.Airline;
import com.simplilearn.persistant.FlightClass;
import com.simplilearn.persistant.Itinerary;
import com.simplilearn.persistant.Route;

public class DatabaseMappingHelper {

	
	public SessionFactory getDBCurrentSessionFactory() {
		// TODO Auto-generated method stub
		
		
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		SessionFactory sf = meta.getSessionFactoryBuilder().build();

		
		return sf;
	}
	
	public Airline getAirlineForId(int id, String name)
	{
		SessionFactory sessionFactory =
				getDBCurrentSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        Airline airline = session.get(Airline.class, id);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return airline;
	}
	
	public Itinerary getItineraryForId(int id, Route route)
	{
		SessionFactory sessionFactory =
				getDBCurrentSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        Itinerary itinerary = session.get(Itinerary.class, id);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return itinerary;
	}
	
	public Route getRouteForId(int id, Airline airline)
	{
		SessionFactory sessionFactory =
				getDBCurrentSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        Route route = session.get(Route.class, id);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return route;
	}
	
	
	public FlightClass getFlightClassForId(int id)
	{
		SessionFactory sessionFactory =
				getDBCurrentSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        FlightClass flightClass = session.get(FlightClass.class, id);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return flightClass;
	}
	
	void createAirline(Airline airline, List<Route> routes)
	{
		SessionFactory sessionFactory =
				getDBCurrentSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        airline.setRoutes(routes);
        session.persist(airline);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
	
	void createRoute(Route route, List<Itinerary> itineraries)
	{
		SessionFactory sessionFactory =
				getDBCurrentSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        route.setItineraries(itineraries);
        session.persist(route);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
	}
	
	void createItinerary(Itinerary itinerary, List<FlightClass> flightClasses)
	{
		SessionFactory sessionFactory =
				getDBCurrentSessionFactory();
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        itinerary.setFlightClassess(flightClasses);
        session.persist(itinerary);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
	}
	
	
}
