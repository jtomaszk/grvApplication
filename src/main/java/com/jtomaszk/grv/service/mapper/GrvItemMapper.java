package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.GrvItem;
import com.jtomaszk.grv.service.dto.GrvItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity GrvItem and its DTO GrvItemDTO.
 */
@Mapper(componentModel = "spring", uses = {SourceMapper.class, LocationMapper.class, SourceArchiveMapper.class})
public interface GrvItemMapper extends EntityMapper<GrvItemDTO, GrvItem> {

    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "sourceArchive.id", target = "sourceArchiveId")
    GrvItemDTO toDto(GrvItem grvItem);

    @Mapping(source = "sourceId", target = "source")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "sourceArchiveId", target = "sourceArchive")
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "errors", ignore = true)
    GrvItem toEntity(GrvItemDTO grvItemDTO);

    default GrvItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrvItem grvItem = new GrvItem();
        grvItem.setId(id);
        return grvItem;
    }
}
