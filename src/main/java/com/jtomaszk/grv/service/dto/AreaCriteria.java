package com.jtomaszk.grv.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;






/**
 * Criteria class for the Area entity. This class is used in AreaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /areas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AreaCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter areaName;

    private LongFilter sourcesId;

    public AreaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAreaName() {
        return areaName;
    }

    public void setAreaName(StringFilter areaName) {
        this.areaName = areaName;
    }

    public LongFilter getSourcesId() {
        return sourcesId;
    }

    public void setSourcesId(LongFilter sourcesId) {
        this.sourcesId = sourcesId;
    }

    @Override
    public String toString() {
        return "AreaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (areaName != null ? "areaName=" + areaName + ", " : "") +
                (sourcesId != null ? "sourcesId=" + sourcesId + ", " : "") +
            "}";
    }

}
