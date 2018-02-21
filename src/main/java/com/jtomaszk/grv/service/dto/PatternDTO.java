package com.jtomaszk.grv.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pattern entity.
 */
public class PatternDTO implements Serializable {

    private Long id;

    @NotNull
    @Pattern(regexp = "^\\w+(,\\w+)*$")
    private String columns;

    @NotNull
    @Pattern(regexp = "^\\w+(,\\w+)*$")
    private String values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatternDTO patternDTO = (PatternDTO) o;
        if(patternDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patternDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatternDTO{" +
            "id=" + getId() +
            ", columns='" + getColumns() + "'" +
            ", values='" + getValues() + "'" +
            "}";
    }
}
