package com.jtomaszk.grv.service.dto;


import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the ParseError entity.
 */
public class ParseErrorDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private String msg;

    @NotNull
    private Instant createdDate;

    private Long sourceId;

    private Long itemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
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

        ParseErrorDTO parseErrorDTO = (ParseErrorDTO) o;
        if(parseErrorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parseErrorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParseErrorDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", msg='" + getMsg() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
