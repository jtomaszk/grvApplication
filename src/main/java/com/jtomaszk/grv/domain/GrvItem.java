package com.jtomaszk.grv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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

    @Column(name = "coords")
    private String coords;

    @Column(name = "externalid")
    private String externalid;

    @Column(name = "externalboxid")
    private String externalboxid;

    @Column(name = "info")
    private String info;

    @Column(name = "docnr")
    private String docnr;

    @ManyToOne
    private Source source;

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

    public String getCoords() {
        return coords;
    }

    public GrvItem coords(String coords) {
        this.coords = coords;
        return this;
    }

    public void setCoords(String coords) {
        this.coords = coords;
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

    public String getExternalboxid() {
        return externalboxid;
    }

    public GrvItem externalboxid(String externalboxid) {
        this.externalboxid = externalboxid;
        return this;
    }

    public void setExternalboxid(String externalboxid) {
        this.externalboxid = externalboxid;
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
            ", coords='" + getCoords() + "'" +
            ", externalid='" + getExternalid() + "'" +
            ", externalboxid='" + getExternalboxid() + "'" +
            ", info='" + getInfo() + "'" +
            ", docnr='" + getDocnr() + "'" +
            "}";
    }
}
