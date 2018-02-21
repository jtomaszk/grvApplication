package com.jtomaszk.grv.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




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

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter anotherLastName;

    private StringFilter startDateString;

    private InstantFilter startDate;

    private StringFilter endDateString;

    private InstantFilter endDate;

    private StringFilter validToDateString;

    private InstantFilter validToDate;

    private StringFilter coords;

    private StringFilter externalid;

    private StringFilter externalboxid;

    private StringFilter info;

    private StringFilter docnr;

    private LongFilter sourceId;

    public GrvItemCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getAnotherLastName() {
        return anotherLastName;
    }

    public void setAnotherLastName(StringFilter anotherLastName) {
        this.anotherLastName = anotherLastName;
    }

    public StringFilter getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(StringFilter startDateString) {
        this.startDateString = startDateString;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public StringFilter getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(StringFilter endDateString) {
        this.endDateString = endDateString;
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

    public StringFilter getCoords() {
        return coords;
    }

    public void setCoords(StringFilter coords) {
        this.coords = coords;
    }

    public StringFilter getExternalid() {
        return externalid;
    }

    public void setExternalid(StringFilter externalid) {
        this.externalid = externalid;
    }

    public StringFilter getExternalboxid() {
        return externalboxid;
    }

    public void setExternalboxid(StringFilter externalboxid) {
        this.externalboxid = externalboxid;
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

    public LongFilter getSourceId() {
        return sourceId;
    }

    public void setSourceId(LongFilter sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return "GrvItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (anotherLastName != null ? "anotherLastName=" + anotherLastName + ", " : "") +
                (startDateString != null ? "startDateString=" + startDateString + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDateString != null ? "endDateString=" + endDateString + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (validToDateString != null ? "validToDateString=" + validToDateString + ", " : "") +
                (validToDate != null ? "validToDate=" + validToDate + ", " : "") +
                (coords != null ? "coords=" + coords + ", " : "") +
                (externalid != null ? "externalid=" + externalid + ", " : "") +
                (externalboxid != null ? "externalboxid=" + externalboxid + ", " : "") +
                (info != null ? "info=" + info + ", " : "") +
                (docnr != null ? "docnr=" + docnr + ", " : "") +
                (sourceId != null ? "sourceId=" + sourceId + ", " : "") +
            "}";
    }

}