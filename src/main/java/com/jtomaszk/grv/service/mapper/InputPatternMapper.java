package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.InputPattern;
import com.jtomaszk.grv.service.dto.InputPatternDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity InputPattern and its DTO InputPatternDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InputPatternMapper extends EntityMapper<InputPatternDTO, InputPattern> {


    @Mapping(target = "sources", ignore = true)
    @Mapping(target = "columns", ignore = true)
    InputPattern toEntity(InputPatternDTO inputPatternDTO);

    default InputPattern fromId(Long id) {
        if (id == null) {
            return null;
        }
        InputPattern inputPattern = new InputPattern();
        inputPattern.setId(id);
        return inputPattern;
    }
}
