package com.jtomaszk.grv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jtomaszk.grv.domain.enumeration.SourceStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Source.
 */
@Entity
@Table(name = "source")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "source")
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SourceStatusEnum status;

    @Column(name = "last_run_date")
    private Instant lastRunDate;

    @Column(name = "info")
    private String info;

    @ManyToOne(optional = false)
    @NotNull
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    private InputPattern pattern;

    @OneToMany(mappedBy = "source")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Error> errors = new HashSet<>();

    @OneToMany(mappedBy = "source")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SourceArchive> archives = new HashSet<>();

    @OneToMany(mappedBy = "source")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Location> locations = new HashSet<>();

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

    public Source title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public Source url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SourceStatusEnum getStatus() {
        return status;
    }

    public Source status(SourceStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(SourceStatusEnum status) {
        this.status = status;
    }

    public Instant getLastRunDate() {
        return lastRunDate;
    }

    public Source lastRunDate(Instant lastRunDate) {
        this.lastRunDate = lastRunDate;
        return this;
    }

    public void setLastRunDate(Instant lastRunDate) {
        this.lastRunDate = lastRunDate;
    }

    public String getInfo() {
        return info;
    }

    public Source info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Area getArea() {
        return area;
    }

    public Source area(Area area) {
        this.area = area;
        return this;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public InputPattern getPattern() {
        return pattern;
    }

    public Source pattern(InputPattern inputPattern) {
        this.pattern = inputPattern;
        return this;
    }

    public void setPattern(InputPattern inputPattern) {
        this.pattern = inputPattern;
    }

    public Set<Error> getErrors() {
        return errors;
    }

    public Source errors(Set<Error> errors) {
        this.errors = errors;
        return this;
    }

    public Source addErrors(Error error) {
        this.errors.add(error);
        error.setSource(this);
        return this;
    }

    public Source removeErrors(Error error) {
        this.errors.remove(error);
        error.setSource(null);
        return this;
    }

    public void setErrors(Set<Error> errors) {
        this.errors = errors;
    }

    public Set<SourceArchive> getArchives() {
        return archives;
    }

    public Source archives(Set<SourceArchive> sourceArchives) {
        this.archives = sourceArchives;
        return this;
    }

    public Source addArchives(SourceArchive sourceArchive) {
        this.archives.add(sourceArchive);
        sourceArchive.setSource(this);
        return this;
    }

    public Source removeArchives(SourceArchive sourceArchive) {
        this.archives.remove(sourceArchive);
        sourceArchive.setSource(null);
        return this;
    }

    public void setArchives(Set<SourceArchive> sourceArchives) {
        this.archives = sourceArchives;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Source locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Source addLocations(Location location) {
        this.locations.add(location);
        location.setSource(this);
        return this;
    }

    public Source removeLocations(Location location) {
        this.locations.remove(location);
        location.setSource(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
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
        Source source = (Source) o;
        if (source.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), source.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Source{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", url='" + getUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastRunDate='" + getLastRunDate() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
