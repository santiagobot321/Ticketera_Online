package com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.EventEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toDomain(EventEntity entity);
    EventEntity toEntity(Event domain);
    List<Event> toDomainList(List<EventEntity> entityList);
}
