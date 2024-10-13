package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.model.AddressEntity;
import com.nxhu.eventosDeAutos.model.CategoryEntity;
import com.nxhu.eventosDeAutos.model.EventEntity;
import com.nxhu.eventosDeAutos.model.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IEventService {

    public List<EventEntity> getEvents();

    public EventEntity getEvent(Long event_id);

    public EventEntity createEvent(EventEntity event);

    public void deleteEvent(Long event_id);

    public EventEntity updateEvent(Long event_id,
                                   String newName, AddressEntity newAdress, LocalDate newDate, String newDescription, CategoryEntity newCategory, BigDecimal newPrice, String newImage, UserEntity newCreator);
}
