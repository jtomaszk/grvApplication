package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.Location;
import com.jtomaszk.grv.service.dto.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {SourceMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "source.id", target = "sourceId")
    LocationDTO toDto(Location location);

    @Mapping(source = "sourceId", target = "source")
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
