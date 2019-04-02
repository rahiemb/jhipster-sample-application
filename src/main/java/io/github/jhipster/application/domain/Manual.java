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
 * A Manual.
 */
@Entity
@Table(name = "manual")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Manual implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "jhi_link")
    private String link;

    @Column(name = "description")
    private String description;

    @Column(name = "track_changes_enabled")
    private Boolean trackChangesEnabled;

    @Column(name = "retired")
    private Boolean retired;

    @Column(name = "retired_date")
    private LocalDate retiredDate;

    @Column(name = "retired_note")
    private String retiredNote;

    @OneToOne
    @JoinColumn(unique = true)
    private Lock lock;

    @OneToOne
    @JoinColumn(unique = true)
    private DocumentTemplate defaultTemplate;

    @OneToMany(mappedBy = "manual")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Section> sections = new HashSet<>();
    @OneToMany(mappedBy = "manual")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LogEntry> logs = new HashSet<>();
    @OneToMany(mappedBy = "manual")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DocumentTemplate> templates = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "manual_manual_type",
               joinColumns = @JoinColumn(name = "manual_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "manual_type_id", referencedColumnName = "id"))
    private Set<ManualType> manualTypes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("manuals")
    private Organization organization;

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

    public Manual name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Manual code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Manual enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getLink() {
        return link;
    }

    public Manual link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public Manual description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isTrackChangesEnabled() {
        return trackChangesEnabled;
    }

    public Manual trackChangesEnabled(Boolean trackChangesEnabled) {
        this.trackChangesEnabled = trackChangesEnabled;
        return this;
    }

    public void setTrackChangesEnabled(Boolean trackChangesEnabled) {
        this.trackChangesEnabled = trackChangesEnabled;
    }

    public Boolean isRetired() {
        return retired;
    }

    public Manual retired(Boolean retired) {
        this.retired = retired;
        return this;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public LocalDate getRetiredDate() {
        return retiredDate;
    }

    public Manual retiredDate(LocalDate retiredDate) {
        this.retiredDate = retiredDate;
        return this;
    }

    public void setRetiredDate(LocalDate retiredDate) {
        this.retiredDate = retiredDate;
    }

    public String getRetiredNote() {
        return retiredNote;
    }

    public Manual retiredNote(String retiredNote) {
        this.retiredNote = retiredNote;
        return this;
    }

    public void setRetiredNote(String retiredNote) {
        this.retiredNote = retiredNote;
    }

    public Lock getLock() {
        return lock;
    }

    public Manual lock(Lock lock) {
        this.lock = lock;
        return this;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public DocumentTemplate getDefaultTemplate() {
        return defaultTemplate;
    }

    public Manual defaultTemplate(DocumentTemplate documentTemplate) {
        this.defaultTemplate = documentTemplate;
        return this;
    }

    public void setDefaultTemplate(DocumentTemplate documentTemplate) {
        this.defaultTemplate = documentTemplate;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public Manual sections(Set<Section> sections) {
        this.sections = sections;
        return this;
    }

    public Manual addSections(Section section) {
        this.sections.add(section);
        section.setManual(this);
        return this;
    }

    public Manual removeSections(Section section) {
        this.sections.remove(section);
        section.setManual(null);
        return this;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public Set<LogEntry> getLogs() {
        return logs;
    }

    public Manual logs(Set<LogEntry> logEntries) {
        this.logs = logEntries;
        return this;
    }

    public Manual addLog(LogEntry logEntry) {
        this.logs.add(logEntry);
        logEntry.setManual(this);
        return this;
    }

    public Manual removeLog(LogEntry logEntry) {
        this.logs.remove(logEntry);
        logEntry.setManual(null);
        return this;
    }

    public void setLogs(Set<LogEntry> logEntries) {
        this.logs = logEntries;
    }

    public Set<DocumentTemplate> getTemplates() {
        return templates;
    }

    public Manual templates(Set<DocumentTemplate> documentTemplates) {
        this.templates = documentTemplates;
        return this;
    }

    public Manual addTemplates(DocumentTemplate documentTemplate) {
        this.templates.add(documentTemplate);
        documentTemplate.setManual(this);
        return this;
    }

    public Manual removeTemplates(DocumentTemplate documentTemplate) {
        this.templates.remove(documentTemplate);
        documentTemplate.setManual(null);
        return this;
    }

    public void setTemplates(Set<DocumentTemplate> documentTemplates) {
        this.templates = documentTemplates;
    }

    public Set<ManualType> getManualTypes() {
        return manualTypes;
    }

    public Manual manualTypes(Set<ManualType> manualTypes) {
        this.manualTypes = manualTypes;
        return this;
    }

    public Manual addManualType(ManualType manualType) {
        this.manualTypes.add(manualType);
        manualType.getManuals().add(this);
        return this;
    }

    public Manual removeManualType(ManualType manualType) {
        this.manualTypes.remove(manualType);
        manualType.getManuals().remove(this);
        return this;
    }

    public void setManualTypes(Set<ManualType> manualTypes) {
        this.manualTypes = manualTypes;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Manual organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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
        Manual manual = (Manual) o;
        if (manual.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), manual.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Manual{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", link='" + getLink() + "'" +
            ", description='" + getDescription() + "'" +
            ", trackChangesEnabled='" + isTrackChangesEnabled() + "'" +
            ", retired='" + isRetired() + "'" +
            ", retiredDate='" + getRetiredDate() + "'" +
            ", retiredNote='" + getRetiredNote() + "'" +
            "}";
    }
}
