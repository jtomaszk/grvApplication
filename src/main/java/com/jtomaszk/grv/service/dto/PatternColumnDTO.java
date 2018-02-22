package com.jtomaszk.grv.service.dto;


import com.jtomaszk.grv.domain.enumeration.ColumnEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PatternColumn entity.
 */
public class PatternColumnDTO implements Serializable {

    private Long id;

    @NotNull
    private ColumnEnum column;

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

    public ColumnEnum getColumn() {
        return column;
    }

    public void setColumn(ColumnEnum column) {
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

    public void setPatternId(Long inputPatternId) {
        this.patternId = inputPatternId;
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
