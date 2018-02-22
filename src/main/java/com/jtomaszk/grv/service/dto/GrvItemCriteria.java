package com.jtomaszk.grv.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;




/**
 * Criteria class for the GrvItem entity. This class is used in GrvItemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /grv-items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GrvItemCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private StringFilter validToDateString;

    private InstantFilter validToDate;

    private StringFilter externalid;

    private StringFilter info;

    private StringFilter docnr;

    private InstantFilter createdDate;

    private LongFilter sourceId;

    private LongFilter locationId;

    private LongFilter sourceArchiveId;

    private LongFilter personId;

    private LongFilter errorsId;

    public GrvItemCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getValidToDateString() {
        return validToDateString;
    }

    public void setValidToDateString(StringFilter validToDateString) {
        this.validToDateString = validToDateString;
    }

    public InstantFilter getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(InstantFilter validToDate) {
        this.validToDate = validToDate;
    }

    public StringFilter getExternalid() {
        return externalid;
    }

    public void setExternalid(StringFilter externalid) {
        this.externalid = externalid;
    }

    public StringFilter getInfo() {
        return info;
    }

    public void setInfo(StringFilter info) {
        this.info = info;
    }

    public StringFilter getDocnr() {
        return docnr;
    }

    public void setDocnr(StringFilter docnr) {
        this.docnr = docnr;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getSourceId() {
        return sourceId;
    }

    public void setSourceId(LongFilter sourceId) {
        this.sourceId = sourceId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getSourceArchiveId() {
        return sourceArchiveId;
    }

    public void setSourceArchiveId(LongFilter sourceArchiveId) {
        this.sourceArchiveId = sourceArchiveId;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }

    public LongFilter getErrorsId() {
        return errorsId;
    }

    public void setErrorsId(LongFilter errorsId) {
        this.errorsId = errorsId;
    }

    @Override
    public String toString() {
        return "GrvItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (validToDateString != null ? "validToDateString=" + validToDateString + ", " : "") +
                (validToDate != null ? "validToDate=" + validToDate + ", " : "") +
                (externalid != null ? "externalid=" + externalid + ", " : "") +
                (info != null ? "info=" + info + ", " : "") +
                (docnr != null ? "docnr=" + docnr + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (sourceId != null ? "sourceId=" + sourceId + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (sourceArchiveId != null ? "sourceArchiveId=" + sourceArchiveId + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (errorsId != null ? "errorsId=" + errorsId + ", " : "") +
            "}";
    }

}
