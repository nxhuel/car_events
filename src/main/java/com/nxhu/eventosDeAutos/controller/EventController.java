package com.nxhu.eventosDeAutos.controller;

import com.nxhu.eventosDeAutos.model.EventEntity;
import com.nxhu.eventosDeAutos.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class EventController {

    @Autowired
    private IEventService iEventService;

    @GetMapping("/events")
    public ResponseEntity<List<EventEntity>> getEvents() {
        return new ResponseEntity<>(iEventService.getEvents(), HttpStatus.OK);
    }

    @GetMapping("/event/{event_id}")
    public ResponseEntity<EventEntity> getEvent(@PathVariable Long event_id) {
        return new ResponseEntity<>(iEventService.getEvent(event_id), HttpStatus.OK);
    }

    @PostMapping("/event/create")
    public ResponseEntity<EventEntity> createEvent(@RequestBody @Validated EventEntity event) {
        return new ResponseEntity<>(iEventService.createEvent(event), HttpStatus.CREATED);
    }

    @DeleteMapping("/event/delete/{event_id}")
    public ResponseEntity deleteEvent(@PathVariable Long event_id) {
        iEventService.deleteEvent(event_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/event/update/{event_id}")
    public ResponseEntity<EventEntity> updateEvent(@PathVariable Long event_id,
                                                   @RequestBody EventEntity newEvent) {
        iEventService.updateEvent(event_id,
                newEvent.getName(), newEvent.getAddress(), newEvent.getDate(), newEvent.getDescription(), newEvent.getCategory(), newEvent.getPrice(), newEvent.getImage(), newEvent.getCreator());

        EventEntity eventUpdated = iEventService.getEvent(event_id);

        return new ResponseEntity<>(eventUpdated, HttpStatus.OK);
    }
}
