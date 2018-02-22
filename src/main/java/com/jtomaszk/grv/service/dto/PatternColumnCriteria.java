package com.jtomaszk.grv.service.dto;

import java.io.Serializable;
import com.jtomaszk.grv.domain.enumeration.Column;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the PatternColumn entity. This class is used in PatternColumnResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /pattern-columns?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PatternColumnCriteria implements Serializable {
    /**
     * Class for filtering Column
     */
    public static class ColumnFilter extends Filter<Column> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private ColumnFilter column;

    private StringFilter value;

    private LongFilter patternId;

    public PatternColumnCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ColumnFilter getColumn() {
        return column;
    }

    public void setColumn(ColumnFilter column) {
        this.column = column;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public LongFilter getPatternId() {
        return patternId;
    }

    public void setPatternId(LongFilter patternId) {
        this.patternId = patternId;
    }

    @Override
    public String toString() {
        return "PatternColumnCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (column != null ? "column=" + column + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (patternId != null ? "patternId=" + patternId + ", " : "") +
            "}";
    }

}
