package com.jtomaszk.grv.service.dto;


import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the SourceArchive entity.
 */
public class SourceArchiveDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant createdDate;

    @NotNull
    @Lob
    private String json;

    private Long sourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SourceArchiveDTO sourceArchiveDTO = (SourceArchiveDTO) o;
        if(sourceArchiveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sourceArchiveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SourceArchiveDTO{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", json='" + getJson() + "'" +
            "}";
    }
}
