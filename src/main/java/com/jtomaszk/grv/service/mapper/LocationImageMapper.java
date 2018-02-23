package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.LocationImage;
import com.jtomaszk.grv.service.dto.LocationImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity LocationImage and its DTO LocationImageDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface LocationImageMapper extends EntityMapper<LocationImageDTO, LocationImage> {

    @Mapping(source = "location.id", target = "locationId")
    LocationImageDTO toDto(LocationImage locationImage);

    @Mapping(source = "locationId", target = "location")
    LocationImage toEntity(LocationImageDTO locationImageDTO);

    default LocationImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocationImage locationImage = new LocationImage();
        locationImage.setId(id);
        return locationImage;
    }
}
