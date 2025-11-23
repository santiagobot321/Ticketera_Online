package com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface VenueMapper {
    @Mapping(source = "events", target = "events")
    Venue toDomain(VenueEntity entity);

    @Mapping(source = "events", target = "events")
    VenueEntity toEntity(Venue domain);

    List<Venue> toDomainList(List<VenueEntity> entityList);
}
