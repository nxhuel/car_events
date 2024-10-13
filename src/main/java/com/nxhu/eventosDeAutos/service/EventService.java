package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.model.AddressEntity;
import com.nxhu.eventosDeAutos.model.CategoryEntity;
import com.nxhu.eventosDeAutos.model.EventEntity;
import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.repository.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventService implements IEventService {

    @Autowired
    private IEventRepository iEventRepository;

    @Override
    public List<EventEntity> getEvents() {
        var events = iEventRepository.findAll();
        if (events.isEmpty()) {
            throw new RuntimeException("No se encontraron eventos");
        }
        return events;
    }

    @Override
    public EventEntity getEvent(Long event_id) {
        return iEventRepository.findById(event_id).orElseThrow(() -> new RuntimeException(String.format("No existe el evento con el ID: %d", event_id)));
    }

    @Override
    public EventEntity createEvent(EventEntity event) {
        return iEventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long event_id) {
        iEventRepository.deleteById(event_id);
    }

    @Override
    public EventEntity updateEvent(Long event_id, String newName, AddressEntity newAdress, LocalDate newDate, String newDescription, CategoryEntity newCategory, BigDecimal newPrice, String newImage, UserEntity newCreator) {
        EventEntity eventToUpdate = this.getEvent(event_id);

        if (eventToUpdate == null) {
            throw new RuntimeException(String.format("No existe el evento con el ID: %d", event_id));
        }

        eventToUpdate.setName(newName);
        eventToUpdate.setAddress(newAdress);
        eventToUpdate.setDate(newDate);
        eventToUpdate.setDescription(newDescription);
        if (Boolean.FALSE.equals(newCategory.getIsPrivate())) {
            eventToUpdate.setPrice(null);
            eventToUpdate.setImage(null);
        } else {
            eventToUpdate.setPrice(newPrice);
            eventToUpdate.setImage(newImage);
        }
        eventToUpdate.setCategory(newCategory);
        eventToUpdate.setCreator(newCreator);

        this.createEvent(eventToUpdate);
        return eventToUpdate;
    }
}
