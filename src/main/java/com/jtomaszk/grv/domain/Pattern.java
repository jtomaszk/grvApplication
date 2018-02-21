package com.jtomaszk.grv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pattern.
 */
@Entity
@Table(name = "pattern")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pattern")
public class Pattern implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^\\w+(,\\w+)*$")
    @Column(name = "jhi_columns", nullable = false)
    private String columns;

    @NotNull
    @Pattern(regexp = "^\\w+(,\\w+)*$")
    @Column(name = "jhi_values", nullable = false)
    private String values;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumns() {
        return columns;
    }

    public Pattern columns(String columns) {
        this.columns = columns;
        return this;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getValues() {
        return values;
    }

    public Pattern values(String values) {
        this.values = values;
        return this;
    }

    public void setValues(String values) {
        this.values = values;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pattern pattern = (Pattern) o;
        if (pattern.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pattern.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pattern{" +
            "id=" + getId() +
            ", columns='" + getColumns() + "'" +
            ", values='" + getValues() + "'" +
            "}";
    }
}
