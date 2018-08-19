package com.rest.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.springboot.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	public List<Event> findAllByOrderByIdDesc();

	public List<Event> findByNameContainingIgnoreCase(String name);

}
