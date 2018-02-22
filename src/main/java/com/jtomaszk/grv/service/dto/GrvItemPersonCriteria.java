package com.jtomaszk.grv.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;






/**
 * Criteria class for the GrvItemPerson entity. This class is used in GrvItemPersonResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /grv-item-people?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GrvItemPersonCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter anotherLastName;

    private StringFilter startDateString;

    private StringFilter endDateString;

    private LongFilter itemId;

    public GrvItemPersonCriteria() {
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

    public StringFilter getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(StringFilter endDateString) {
        this.endDateString = endDateString;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "GrvItemPersonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (anotherLastName != null ? "anotherLastName=" + anotherLastName + ", " : "") +
                (startDateString != null ? "startDateString=" + startDateString + ", " : "") +
                (endDateString != null ? "endDateString=" + endDateString + ", " : "") +
                (itemId != null ? "itemId=" + itemId + ", " : "") +
            "}";
    }

}
