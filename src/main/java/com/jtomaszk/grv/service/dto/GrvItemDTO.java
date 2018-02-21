package com.jtomaszk.grv.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the GrvItem entity.
 */
public class GrvItemDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String anotherLastName;

    private String startDateString;

    private Instant startDate;

    private String endDateString;

    private Instant endDate;

    private String validToDateString;

    private Instant validToDate;

    private String externalid;

    private String info;

    private String docnr;

    @NotNull
    private Instant createdDate;

    private Long sourceId;

    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAnotherLastName() {
        return anotherLastName;
    }

    public void setAnotherLastName(String anotherLastName) {
        this.anotherLastName = anotherLastName;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getValidToDateString() {
        return validToDateString;
    }

    public void setValidToDateString(String validToDateString) {
        this.validToDateString = validToDateString;
    }

    public Instant getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(Instant validToDate) {
        this.validToDate = validToDate;
    }

    public String getExternalid() {
        return externalid;
    }

    public void setExternalid(String externalid) {
        this.externalid = externalid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDocnr() {
        return docnr;
    }

    public void setDocnr(String docnr) {
        this.docnr = docnr;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrvItemDTO grvItemDTO = (GrvItemDTO) o;
        if(grvItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grvItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrvItemDTO{" +
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
