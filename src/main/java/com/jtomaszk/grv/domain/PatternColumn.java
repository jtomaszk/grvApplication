package com.jtomaszk.grv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.jtomaszk.grv.domain.enumeration.Column;

/**
 * A PatternColumn.
 */
@Entity
@Table(name = "pattern_column")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patterncolumn")
public class PatternColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_column", nullable = false)
    private Column column;

    @NotNull
    @Pattern(regexp = "[\\w ${}]+")
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @ManyToOne(optional = false)
    @NotNull
    private Pattern pattern;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Column getColumn() {
        return column;
    }

    public PatternColumn column(Column column) {
        this.column = column;
        return this;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public PatternColumn value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public PatternColumn pattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
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
        PatternColumn patternColumn = (PatternColumn) o;
        if (patternColumn.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patternColumn.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatternColumn{" +
            "id=" + getId() +
            ", column='" + getColumn() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}