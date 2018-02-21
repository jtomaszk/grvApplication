package com.jtomaszk.grv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "another_last_name")
    private String anotherLastName;

    @Column(name = "start_date_string")
    private String startDateString;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date_string")
    private String endDateString;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "valid_to_date_string")
    private String validToDateString;

    @Column(name = "valid_to_date")
    private Instant validToDate;

    @Column(name = "externalid")
    private String externalid;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public GrvItem firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GrvItem lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAnotherLastName() {
        return anotherLastName;
    }

    public GrvItem anotherLastName(String anotherLastName) {
        this.anotherLastName = anotherLastName;
        return this;
    }

    public void setAnotherLastName(String anotherLastName) {
        this.anotherLastName = anotherLastName;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public GrvItem startDateString(String startDateString) {
        this.startDateString = startDateString;
        return this;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
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

    public String getEndDateString() {
        return endDateString;
    }

    public GrvItem endDateString(String endDateString) {
        this.endDateString = endDateString;
        return this;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
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
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", anotherLastName='" + getAnotherLastName() + "'" +
            ", startDateString='" + getStartDateString() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDateString='" + getEndDateString() + "'" +
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
