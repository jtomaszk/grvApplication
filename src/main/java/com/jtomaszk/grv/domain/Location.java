package com.jtomaszk.grv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 31)
    @Column(name = "externalid", length = 31)
    private String externalid;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Size(max = 100)
    @Column(name = "coords", length = 100)
    private String coords;

    @ManyToOne(optional = false)
    @NotNull
    private Source source;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GrvItem> items = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocationImage> images = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalid() {
        return externalid;
    }

    public Location externalid(String externalid) {
        this.externalid = externalid;
        return this;
    }

    public void setExternalid(String externalid) {
        this.externalid = externalid;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Location createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCoords() {
        return coords;
    }

    public Location coords(String coords) {
        this.coords = coords;
        return this;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public Source getSource() {
        return source;
    }

    public Location source(Source source) {
        this.source = source;
        return this;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Set<GrvItem> getItems() {
        return items;
    }

    public Location items(Set<GrvItem> grvItems) {
        this.items = grvItems;
        return this;
    }

    public Location addItems(GrvItem grvItem) {
        this.items.add(grvItem);
        grvItem.setLocation(this);
        return this;
    }

    public Location removeItems(GrvItem grvItem) {
        this.items.remove(grvItem);
        grvItem.setLocation(null);
        return this;
    }

    public void setItems(Set<GrvItem> grvItems) {
        this.items = grvItems;
    }

    public Set<LocationImage> getImages() {
        return images;
    }

    public Location images(Set<LocationImage> locationImages) {
        this.images = locationImages;
        return this;
    }

    public Location addImages(LocationImage locationImage) {
        this.images.add(locationImage);
        locationImage.setLocation(this);
        return this;
    }

    public Location removeImages(LocationImage locationImage) {
        this.images.remove(locationImage);
        locationImage.setLocation(null);
        return this;
    }

    public void setImages(Set<LocationImage> locationImages) {
        this.images = locationImages;
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", externalid='" + getExternalid() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", coords='" + getCoords() + "'" +
            "}";
    }
}
