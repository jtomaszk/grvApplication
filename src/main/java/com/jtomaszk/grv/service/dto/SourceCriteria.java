package com.jtomaszk.grv.service.dto;

import com.jtomaszk.grv.domain.enumeration.SourceStatusEnum;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;




/**
 * Criteria class for the Source entity. This class is used in SourceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /sources?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SourceCriteria implements Serializable {
    /**
     * Class for filtering SourceStatusEnum
     */
    public static class SourceStatusEnumFilter extends Filter<SourceStatusEnum> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private StringFilter url;

    private SourceStatusEnumFilter status;

    private InstantFilter lastRunDate;

    private StringFilter info;

    private LongFilter areaId;

    private LongFilter patternId;

    private LongFilter errorsId;

    private LongFilter archivesId;

    private LongFilter locationsId;

    public SourceCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public SourceStatusEnumFilter getStatus() {
        return status;
    }

    public void setStatus(SourceStatusEnumFilter status) {
        this.status = status;
    }

    public InstantFilter getLastRunDate() {
        return lastRunDate;
    }

    public void setLastRunDate(InstantFilter lastRunDate) {
        this.lastRunDate = lastRunDate;
    }

    public StringFilter getInfo() {
        return info;
    }

    public void setInfo(StringFilter info) {
        this.info = info;
    }

    public LongFilter getAreaId() {
        return areaId;
    }

    public void setAreaId(LongFilter areaId) {
        this.areaId = areaId;
    }

    public LongFilter getPatternId() {
        return patternId;
    }

    public void setPatternId(LongFilter patternId) {
        this.patternId = patternId;
    }

    public LongFilter getErrorsId() {
        return errorsId;
    }

    public void setErrorsId(LongFilter errorsId) {
        this.errorsId = errorsId;
    }

    public LongFilter getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(LongFilter archivesId) {
        this.archivesId = archivesId;
    }

    public LongFilter getLocationsId() {
        return locationsId;
    }

    public void setLocationsId(LongFilter locationsId) {
        this.locationsId = locationsId;
    }

    @Override
    public String toString() {
        return "SourceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (lastRunDate != null ? "lastRunDate=" + lastRunDate + ", " : "") +
                (info != null ? "info=" + info + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (patternId != null ? "patternId=" + patternId + ", " : "") +
                (errorsId != null ? "errorsId=" + errorsId + ", " : "") +
                (archivesId != null ? "archivesId=" + archivesId + ", " : "") +
                (locationsId != null ? "locationsId=" + locationsId + ", " : "") +
            "}";
    }

}
