package com.rest.springboot.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.springboot.model.Event;
import com.rest.springboot.model.User;
import com.rest.springboot.service.EventService;

@RestController
@RequestMapping("/api")
public class EventController {

	@Autowired
	private EventService eventService;

	// Get All Events
	@GetMapping("/events")
	public ResponseEntity<List<Event>> getAllEvents() {
		List<Event> events = eventService.getAllEvents();
		if (events == null || events.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	// Get a Single Event
	@GetMapping("/events/{id}")
	public Event getEventById(@PathVariable(value = "id") Long eventId) {
		return eventService.getEventById(eventId);
	}

	// Create a new Event
	@PostMapping("/events")
	public ResponseEntity<Event> createAnEvent(@RequestParam("name") String name, @RequestParam("venue") String venue,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("image") MultipartFile file) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Event event = new Event();
		event.setName(name);
		event.setVenue(venue);
		event.setStartDate(LocalDate.parse(startDate, formatter));
		event.setEndDate(LocalDate.parse(endDate, formatter));
		try {
			event.setImage(file.getBytes());
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(eventService.createNewEvent(event), HttpStatus.ACCEPTED);
	}

	// Update an Event
	@PostMapping("/events/update/{id}")
	public ResponseEntity<Event> updateAnEvent(@PathVariable(value = "id") Long eventId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "venue", required = false) String venue,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "image", required = false) MultipartFile file) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Event event = new Event();
		event.setName(name);
		event.setVenue(venue);
		event.setStartDate(LocalDate.parse(startDate, formatter));
		event.setEndDate(LocalDate.parse(endDate, formatter));
		try {
			if (file != null) {
				event.setImage(file.getBytes());
			}
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(eventService.updateEvent(eventId, event), HttpStatus.ACCEPTED);
	}

	// Delete an Event
	@DeleteMapping("/events/delete/{id}")
	public ResponseEntity<?> deleteAnEvent(@PathVariable(value = "id") Long eventId) {
		eventService.deleteEvent(eventId);
		return ResponseEntity.ok().build();
	}

	// Search an event
	@GetMapping(value = "/events/search")
	public ResponseEntity<List<Event>> search(@RequestParam("q") String query) {
		return new ResponseEntity<>(eventService.findByName(query), HttpStatus.OK);
	}

	// authentication
	@PostMapping(value = "/users/authenticate")
	public ResponseEntity<User> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		User user = null;
		if (username.equalsIgnoreCase("user") && password.equalsIgnoreCase("12345")) {
			user = new User("user");
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			user = new User("admin");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
	}

}
