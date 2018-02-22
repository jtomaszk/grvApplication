package com.jtomaszk.grv.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GrvItemPerson entity.
 */
public class GrvItemPersonDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String anotherLastName;

    private String startDateString;

    private String endDateString;

    private Long itemId;

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

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long grvItemId) {
        this.itemId = grvItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrvItemPersonDTO grvItemPersonDTO = (GrvItemPersonDTO) o;
        if(grvItemPersonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grvItemPersonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrvItemPersonDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", anotherLastName='" + getAnotherLastName() + "'" +
            ", startDateString='" + getStartDateString() + "'" +
            ", endDateString='" + getEndDateString() + "'" +
            "}";
    }
}
