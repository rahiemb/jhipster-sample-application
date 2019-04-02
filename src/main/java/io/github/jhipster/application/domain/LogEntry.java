package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.ObjectType;

import io.github.jhipster.application.domain.enumeration.LogLevel;

/**
 * A LogEntry.
 */
@Entity
@Table(name = "log_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LogEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ObjectType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level")
    private LogLevel level;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "details")
    private String details;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "jhi_timestamp")
    private LocalDate timestamp;

    @ManyToOne
    @JsonIgnoreProperties("logs")
    private Document document;

    @ManyToOne
    @JsonIgnoreProperties("logs")
    private Manual manual;

    @ManyToOne
    @JsonIgnoreProperties("logs")
    private Organization organization;

    @ManyToOne
    @JsonIgnoreProperties("logs")
    private Section section;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ObjectType getType() {
        return type;
    }

    public LogEntry type(ObjectType type) {
        this.type = type;
        return this;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public LogLevel getLevel() {
        return level;
    }

    public LogEntry level(LogLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public Long getObjectId() {
        return objectId;
    }

    public LogEntry objectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getDetails() {
        return details;
    }

    public LogEntry details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUser() {
        return user;
    }

    public LogEntry user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public LogEntry timestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public Document getDocument() {
        return document;
    }

    public LogEntry document(Document document) {
        this.document = document;
        return this;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Manual getManual() {
        return manual;
    }

    public LogEntry manual(Manual manual) {
        this.manual = manual;
        return this;
    }

    public void setManual(Manual manual) {
        this.manual = manual;
    }

    public Organization getOrganization() {
        return organization;
    }

    public LogEntry organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Section getSection() {
        return section;
    }

    public LogEntry section(Section section) {
        this.section = section;
        return this;
    }

    public void setSection(Section section) {
        this.section = section;
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
        LogEntry logEntry = (LogEntry) o;
        if (logEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogEntry{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", level='" + getLevel() + "'" +
            ", objectId=" + getObjectId() +
            ", details='" + getDetails() + "'" +
            ", user='" + getUser() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
