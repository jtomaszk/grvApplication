package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.GrvItemPerson;
import com.jtomaszk.grv.service.dto.GrvItemPersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity GrvItemPerson and its DTO GrvItemPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {GrvItemMapper.class})
public interface GrvItemPersonMapper extends EntityMapper<GrvItemPersonDTO, GrvItemPerson> {

    @Mapping(source = "item.id", target = "itemId")
    GrvItemPersonDTO toDto(GrvItemPerson grvItemPerson);

    @Mapping(source = "itemId", target = "item")
    GrvItemPerson toEntity(GrvItemPersonDTO grvItemPersonDTO);

    default GrvItemPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrvItemPerson grvItemPerson = new GrvItemPerson();
        grvItemPerson.setId(id);
        return grvItemPerson;
    }
}
