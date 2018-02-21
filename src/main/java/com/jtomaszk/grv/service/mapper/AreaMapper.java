package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.*;
import com.jtomaszk.grv.service.dto.AreaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Area and its DTO AreaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AreaMapper extends EntityMapper<AreaDTO, Area> {


    @Mapping(target = "sources", ignore = true)
    Area toEntity(AreaDTO areaDTO);

    default Area fromId(Long id) {
        if (id == null) {
            return null;
        }
        Area area = new Area();
        area.setId(id);
        return area;
    }
}
