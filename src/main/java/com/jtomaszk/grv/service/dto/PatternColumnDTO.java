package com.jtomaszk.grv.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.jtomaszk.grv.domain.enumeration.Column;

/**
 * A DTO for the PatternColumn entity.
 */
public class PatternColumnDTO implements Serializable {

    private Long id;

    @NotNull
    private Column column;

    @NotNull
    @Pattern(regexp = "[\\w ${}]+")
    private String value;

    private Long patternId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getPatternId() {
        return patternId;
    }

    public void setPatternId(Long patternId) {
        this.patternId = patternId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatternColumnDTO patternColumnDTO = (PatternColumnDTO) o;
        if(patternColumnDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patternColumnDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatternColumnDTO{" +
            "id=" + getId() +
            ", column='" + getColumn() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
