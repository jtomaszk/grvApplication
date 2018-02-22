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

    private Instant startDate;

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

    private Long sourceArchiveId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
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

    public Long getSourceArchiveId() {
        return sourceArchiveId;
    }

    public void setSourceArchiveId(Long sourceArchiveId) {
        this.sourceArchiveId = sourceArchiveId;
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
