package com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VenueMapper.class})
public interface EventMapper {
    @Mapping(source = "venue", target = "venue")
    Event toDomain(EventEntity entity);

    @Mapping(source = "venue", target = "venue")
    EventEntity toEntity(Event domain);

    List<Event> toDomainList(List<EventEntity> entityList);
}
