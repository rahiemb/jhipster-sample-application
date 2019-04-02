package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Section.
 */
@Entity
@Table(name = "section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "seed")
    private Integer seed;

    @Column(name = "mask")
    private String mask;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "jhi_link")
    private String link;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "retired")
    private Boolean retired;

    @Column(name = "retired_date")
    private LocalDate retiredDate;

    @Column(name = "retired_note")
    private String retiredNote;

    @ManyToOne
    @JsonIgnoreProperties("sections")
    private Manual manual;

    @OneToOne
    @JoinColumn(unique = true)
    private Lock lock;

    @OneToMany(mappedBy = "section")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Document> documents = new HashSet<>();
    @OneToMany(mappedBy = "section")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LogEntry> logs = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Section name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Section code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSeed() {
        return seed;
    }

    public Section seed(Integer seed) {
        this.seed = seed;
        return this;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public String getMask() {
        return mask;
    }

    public Section mask(String mask) {
        this.mask = mask;
        return this;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getDescription() {
        return description;
    }

    public Section description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Section enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getLink() {
        return link;
    }

    public Section link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Section orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean isRetired() {
        return retired;
    }

    public Section retired(Boolean retired) {
        this.retired = retired;
        return this;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public LocalDate getRetiredDate() {
        return retiredDate;
    }

    public Section retiredDate(LocalDate retiredDate) {
        this.retiredDate = retiredDate;
        return this;
    }

    public void setRetiredDate(LocalDate retiredDate) {
        this.retiredDate = retiredDate;
    }

    public String getRetiredNote() {
        return retiredNote;
    }

    public Section retiredNote(String retiredNote) {
        this.retiredNote = retiredNote;
        return this;
    }

    public void setRetiredNote(String retiredNote) {
        this.retiredNote = retiredNote;
    }

    public Manual getManual() {
        return manual;
    }

    public Section manual(Manual manual) {
        this.manual = manual;
        return this;
    }

    public void setManual(Manual manual) {
        this.manual = manual;
    }

    public Lock getLock() {
        return lock;
    }

    public Section lock(Lock lock) {
        this.lock = lock;
        return this;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Section documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Section addDocuments(Document document) {
        this.documents.add(document);
        document.setSection(this);
        return this;
    }

    public Section removeDocuments(Document document) {
        this.documents.remove(document);
        document.setSection(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Set<LogEntry> getLogs() {
        return logs;
    }

    public Section logs(Set<LogEntry> logEntries) {
        this.logs = logEntries;
        return this;
    }

    public Section addLog(LogEntry logEntry) {
        this.logs.add(logEntry);
        logEntry.setSection(this);
        return this;
    }

    public Section removeLog(LogEntry logEntry) {
        this.logs.remove(logEntry);
        logEntry.setSection(null);
        return this;
    }

    public void setLogs(Set<LogEntry> logEntries) {
        this.logs = logEntries;
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
        Section section = (Section) o;
        if (section.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), section.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Section{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", seed=" + getSeed() +
            ", mask='" + getMask() + "'" +
            ", description='" + getDescription() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", link='" + getLink() + "'" +
            ", orderId=" + getOrderId() +
            ", retired='" + isRetired() + "'" +
            ", retiredDate='" + getRetiredDate() + "'" +
            ", retiredNote='" + getRetiredNote() + "'" +
            "}";
    }
}
