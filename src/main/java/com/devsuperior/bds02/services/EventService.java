package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional
	public EventDTO update(Long eventId, EventDTO eventDTO) {
		try {
			Event retrievedEvent = eventRepository.getOne(eventId);
			
			City retrievedCity = cityRepository.getOne(eventDTO.getCityId());
			
			retrievedEvent.setName(eventDTO.getName());
			retrievedEvent.setDate(eventDTO.getDate());
			retrievedEvent.setUrl(eventDTO.getUrl());
			retrievedEvent.setCity(retrievedCity);
			
			retrievedEvent = eventRepository.save(retrievedEvent);
			
			return new EventDTO(retrievedEvent);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + eventId);
		}
	}
	
}
