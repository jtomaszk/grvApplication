package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.*;
import com.jtomaszk.grv.service.dto.SourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Source and its DTO SourceDTO.
 */
@Mapper(componentModel = "spring", uses = {AreaMapper.class, PatternMapper.class})
public interface SourceMapper extends EntityMapper<SourceDTO, Source> {

    @Mapping(source = "area.id", target = "areaId")
    @Mapping(source = "pattern.id", target = "patternId")
    SourceDTO toDto(Source source);

    @Mapping(source = "areaId", target = "area")
    @Mapping(source = "patternId", target = "pattern")
    @Mapping(target = "errors", ignore = true)
    @Mapping(target = "archives", ignore = true)
    @Mapping(target = "locations", ignore = true)
    Source toEntity(SourceDTO sourceDTO);

    default Source fromId(Long id) {
        if (id == null) {
            return null;
        }
        Source source = new Source();
        source.setId(id);
        return source;
    }
}
