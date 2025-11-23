package com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    Venue toDomain(VenueEntity entity);
    VenueEntity toEntity(Venue domain);
    List<Venue> toDomainList(List<VenueEntity> entityList);
}
