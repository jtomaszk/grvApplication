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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A GrvItem.
 */
@Entity
@Table(name = "grv_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "grvitem")
public class GrvItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Size(max = 15)
    @Column(name = "valid_to_date_string", length = 15)
    private String validToDateString;

    @Column(name = "valid_to_date")
    private Instant validToDate;

    @Size(max = 31)
    @Column(name = "externalid", length = 31)
    private String externalid;

    @Lob
    @Column(name = "info")
    private String info;

    @Column(name = "docnr")
    private String docnr;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @ManyToOne(optional = false)
    @NotNull
    private Source source;

    @ManyToOne(optional = false)
    @NotNull
    private Location location;

    @ManyToOne
    private SourceArchive sourceArchive;

    @OneToOne(mappedBy = "item")
    @JsonIgnore
    private GrvItemPerson person;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ParseError> errors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public GrvItem startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public GrvItem endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getValidToDateString() {
        return validToDateString;
    }

    public GrvItem validToDateString(String validToDateString) {
        this.validToDateString = validToDateString;
        return this;
    }

    public void setValidToDateString(String validToDateString) {
        this.validToDateString = validToDateString;
    }

    public Instant getValidToDate() {
        return validToDate;
    }

    public GrvItem validToDate(Instant validToDate) {
        this.validToDate = validToDate;
        return this;
    }

    public void setValidToDate(Instant validToDate) {
        this.validToDate = validToDate;
    }

    public String getExternalid() {
        return externalid;
    }

    public GrvItem externalid(String externalid) {
        this.externalid = externalid;
        return this;
    }

    public void setExternalid(String externalid) {
        this.externalid = externalid;
    }

    public String getInfo() {
        return info;
    }

    public GrvItem info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDocnr() {
        return docnr;
    }

    public GrvItem docnr(String docnr) {
        this.docnr = docnr;
        return this;
    }

    public void setDocnr(String docnr) {
        this.docnr = docnr;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public GrvItem createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Source getSource() {
        return source;
    }

    public GrvItem source(Source source) {
        this.source = source;
        return this;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Location getLocation() {
        return location;
    }

    public GrvItem location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SourceArchive getSourceArchive() {
        return sourceArchive;
    }

    public GrvItem sourceArchive(SourceArchive sourceArchive) {
        this.sourceArchive = sourceArchive;
        return this;
    }

    public void setSourceArchive(SourceArchive sourceArchive) {
        this.sourceArchive = sourceArchive;
    }

    public GrvItemPerson getPerson() {
        return person;
    }

    public GrvItem person(GrvItemPerson grvItemPerson) {
        this.person = grvItemPerson;
        return this;
    }

    public void setPerson(GrvItemPerson grvItemPerson) {
        this.person = grvItemPerson;
    }

    public Set<ParseError> getErrors() {
        return errors;
    }

    public GrvItem errors(Set<ParseError> parseErrors) {
        this.errors = parseErrors;
        return this;
    }

    public GrvItem addErrors(ParseError parseError) {
        this.errors.add(parseError);
        parseError.setItem(this);
        return this;
    }

    public GrvItem removeErrors(ParseError parseError) {
        this.errors.remove(parseError);
        parseError.setItem(null);
        return this;
    }

    public void setErrors(Set<ParseError> parseErrors) {
        this.errors = parseErrors;
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
        GrvItem grvItem = (GrvItem) o;
        if (grvItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grvItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrvItem{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", validToDateString='" + getValidToDateString() + "'" +
            ", validToDate='" + getValidToDate() + "'" +
            ", externalid='" + getExternalid() + "'" +
            ", info='" + getInfo() + "'" +
            ", docnr='" + getDocnr() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
