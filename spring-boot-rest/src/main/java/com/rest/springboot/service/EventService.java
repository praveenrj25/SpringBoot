package com.rest.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rest.springboot.model.Event;

@Service
public interface EventService {

	List<Event> getAllEvents();

	Event getEventById(Long eventId);

	Event createNewEvent(Event event);

	Event updateEvent(Long eventId, Event eventDetails);

	void deleteEvent(Long eventId);

	List<Event> findByName(String name);

}
