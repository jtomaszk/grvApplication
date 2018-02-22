package com.jtomaszk.grv.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the InputPattern entity. This class is used in InputPatternResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /input-patterns?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InputPatternCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private LongFilter sourcesId;

    private LongFilter columnsId;

    public InputPatternCriteria() {
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

    public LongFilter getSourcesId() {
        return sourcesId;
    }

    public void setSourcesId(LongFilter sourcesId) {
        this.sourcesId = sourcesId;
    }

    public LongFilter getColumnsId() {
        return columnsId;
    }

    public void setColumnsId(LongFilter columnsId) {
        this.columnsId = columnsId;
    }

    @Override
    public String toString() {
        return "InputPatternCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (sourcesId != null ? "sourcesId=" + sourcesId + ", " : "") +
                (columnsId != null ? "columnsId=" + columnsId + ", " : "") +
            "}";
    }

}
