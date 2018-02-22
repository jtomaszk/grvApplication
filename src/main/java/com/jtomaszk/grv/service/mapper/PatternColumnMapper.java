package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.PatternColumn;
import com.jtomaszk.grv.service.dto.PatternColumnDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity PatternColumn and its DTO PatternColumnDTO.
 */
@Mapper(componentModel = "spring", uses = {InputPatternMapper.class})
public interface PatternColumnMapper extends EntityMapper<PatternColumnDTO, PatternColumn> {

    @Mapping(source = "pattern.id", target = "patternId")
    PatternColumnDTO toDto(PatternColumn patternColumn);

    @Mapping(source = "patternId", target = "pattern")
    PatternColumn toEntity(PatternColumnDTO patternColumnDTO);

    default PatternColumn fromId(Long id) {
        if (id == null) {
            return null;
        }
        PatternColumn patternColumn = new PatternColumn();
        patternColumn.setId(id);
        return patternColumn;
    }
}
