package com.jtomaszk.grv.service.mapper;

import com.jtomaszk.grv.domain.*;
import com.jtomaszk.grv.service.dto.PatternDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pattern and its DTO PatternDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PatternMapper extends EntityMapper<PatternDTO, Pattern> {



    default Pattern fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pattern pattern = new Pattern();
        pattern.setId(id);
        return pattern;
    }
}
