package com.rest.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.springboot.exception.ResourceNotFoundException;
import com.rest.springboot.model.Event;
import com.rest.springboot.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	private static final String EVENT = "Event";
	private static final String ID = "id";

	@Autowired
	private EventRepository eventRepository;

	@Override
	public List<Event> getAllEvents() {
		return eventRepository.findAllByOrderByIdDesc();
	}

	@Override
	public Event getEventById(Long eventId) {
		return eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException(EVENT, ID, eventId));
	}

	@Override
	public Event createNewEvent(Event event) {
		setEventDisplayDate(event);
		return eventRepository.save(event);
	}

	@Override
	public Event updateEvent(Long eventId, Event eventDetails) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException(EVENT, ID, eventId));

		event.setName(eventDetails.getName());
		event.setVenue(eventDetails.getVenue());
		event.setStartDate(eventDetails.getStartDate());
		event.setEndDate(eventDetails.getEndDate());
		setEventDisplayDate(event);
		event.setImage(eventDetails.getImage());

		return eventRepository.save(event);
	}

	@Override
	public void deleteEvent(Long eventId) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException(EVENT, ID, eventId));
		eventRepository.delete(event);
	}

	private static void setEventDisplayDate(Event event) {
		event.setDispDate(event.getStartDate().getDayOfMonth() + " "
				+ event.getStartDate().getMonth().toString().substring(0, 3));
	}

	@Override
	public List<Event> findByName(String name) {
		if (name == null) {
			return eventRepository.findAll();
		}
		return eventRepository.findByNameContainingIgnoreCase(name);
	}

}
