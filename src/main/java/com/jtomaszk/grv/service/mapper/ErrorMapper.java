package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.*;
import com.jtomaszk.grv.service.dto.ErrorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Error and its DTO ErrorDTO.
 */
@Mapper(componentModel = "spring", uses = {SourceMapper.class, GrvItemMapper.class})
public interface ErrorMapper extends EntityMapper<ErrorDTO, Error> {

    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "item.id", target = "itemId")
    ErrorDTO toDto(Error error);

    @Mapping(source = "sourceId", target = "source")
    @Mapping(source = "itemId", target = "item")
    Error toEntity(ErrorDTO errorDTO);

    default Error fromId(Long id) {
        if (id == null) {
            return null;
        }
        Error error = new Error();
        error.setId(id);
        return error;
    }
}
