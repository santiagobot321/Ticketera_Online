package com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring") // Removed 'uses = {EventMapper.class}'
public interface VenueMapper {
    // Removed '@Mapping(source = "events", target = "events")'
    Venue toDomain(VenueEntity entity);

    // Removed '@Mapping(source = "events", target = "events")'
    VenueEntity toEntity(Venue domain);

    List<Venue> toDomainList(List<VenueEntity> entityList);
}
