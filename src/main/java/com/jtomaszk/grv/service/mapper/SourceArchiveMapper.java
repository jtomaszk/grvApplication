package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.*;
import com.jtomaszk.grv.service.dto.SourceArchiveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SourceArchive and its DTO SourceArchiveDTO.
 */
@Mapper(componentModel = "spring", uses = {SourceMapper.class})
public interface SourceArchiveMapper extends EntityMapper<SourceArchiveDTO, SourceArchive> {

    @Mapping(source = "source.id", target = "sourceId")
    SourceArchiveDTO toDto(SourceArchive sourceArchive);

    @Mapping(source = "sourceId", target = "source")
    SourceArchive toEntity(SourceArchiveDTO sourceArchiveDTO);

    default SourceArchive fromId(Long id) {
        if (id == null) {
            return null;
        }
        SourceArchive sourceArchive = new SourceArchive();
        sourceArchive.setId(id);
        return sourceArchive;
    }
}
