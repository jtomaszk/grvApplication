package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.ParseError;
import com.jtomaszk.grv.service.dto.ParseErrorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity ParseError and its DTO ParseErrorDTO.
 */
@Mapper(componentModel = "spring", uses = {SourceMapper.class, GrvItemMapper.class})
public interface ParseErrorMapper extends EntityMapper<ParseErrorDTO, ParseError> {

    @Mapping(source = "source.id", target = "sourceId")
    @Mapping(source = "item.id", target = "itemId")
    ParseErrorDTO toDto(ParseError parseError);

    @Mapping(source = "sourceId", target = "source")
    @Mapping(source = "itemId", target = "item")
    ParseError toEntity(ParseErrorDTO parseErrorDTO);

    default ParseError fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParseError parseError = new ParseError();
        parseError.setId(id);
        return parseError;
    }
}
