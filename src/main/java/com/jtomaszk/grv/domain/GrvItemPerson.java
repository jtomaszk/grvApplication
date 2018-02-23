package com.jtomaszk.grv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GrvItemPerson.
 */
@Entity
@Table(name = "grv_item_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "grvitemperson")
public class GrvItemPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "another_last_name")
    private String anotherLastName;

    @Size(max = 15)
    @Column(name = "start_date_string", length = 15)
    private String startDateString;

    @Size(max = 15)
    @Column(name = "end_date_string", length = 15)
    private String endDateString;

    @OneToOne
    @JoinColumn(unique = true)
    private GrvItem item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public GrvItemPerson firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GrvItemPerson lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAnotherLastName() {
        return anotherLastName;
    }

    public GrvItemPerson anotherLastName(String anotherLastName) {
        this.anotherLastName = anotherLastName;
        return this;
    }

    public void setAnotherLastName(String anotherLastName) {
        this.anotherLastName = anotherLastName;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public GrvItemPerson startDateString(String startDateString) {
        this.startDateString = startDateString;
        return this;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public GrvItemPerson endDateString(String endDateString) {
        this.endDateString = endDateString;
        return this;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public GrvItem getItem() {
        return item;
    }

    public GrvItemPerson item(GrvItem grvItem) {
        this.item = grvItem;
        return this;
    }

    public void setItem(GrvItem grvItem) {
        this.item = grvItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GrvItemPerson grvItemPerson = (GrvItemPerson) o;
        if (grvItemPerson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grvItemPerson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrvItemPerson{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", anotherLastName='" + getAnotherLastName() + "'" +
            ", startDateString='" + getStartDateString() + "'" +
            ", endDateString='" + getEndDateString() + "'" +
            "}";
    }
}
