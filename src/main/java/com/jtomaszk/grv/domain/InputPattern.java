package com.jtomaszk.grv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A InputPattern.
 */
@Entity
@Table(name = "input_pattern")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "inputpattern")
public class InputPattern implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "pattern")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Source> sources = new HashSet<>();

    @OneToMany(mappedBy = "pattern")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatternColumn> columns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public InputPattern title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Source> getSources() {
        return sources;
    }

    public InputPattern sources(Set<Source> sources) {
        this.sources = sources;
        return this;
    }

    public InputPattern addSources(Source source) {
        this.sources.add(source);
        source.setPattern(this);
        return this;
    }

    public InputPattern removeSources(Source source) {
        this.sources.remove(source);
        source.setPattern(null);
        return this;
    }

    public void setSources(Set<Source> sources) {
        this.sources = sources;
    }

    public Set<PatternColumn> getColumns() {
        return columns;
    }

    public InputPattern columns(Set<PatternColumn> patternColumns) {
        this.columns = patternColumns;
        return this;
    }

    public InputPattern addColumns(PatternColumn patternColumn) {
        this.columns.add(patternColumn);
        patternColumn.setPattern(this);
        return this;
    }

    public InputPattern removeColumns(PatternColumn patternColumn) {
        this.columns.remove(patternColumn);
        patternColumn.setPattern(null);
        return this;
    }

    public void setColumns(Set<PatternColumn> patternColumns) {
        this.columns = patternColumns;
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
        InputPattern inputPattern = (InputPattern) o;
        if (inputPattern.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inputPattern.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InputPattern{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
