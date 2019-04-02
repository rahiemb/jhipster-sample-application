package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Version.
 */
@Entity
@Table(name = "version")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Version implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "version_major")
    private Integer versionMajor;

    @Column(name = "version_minor")
    private Integer versionMinor;

    @Column(name = "version_draft")
    private Integer versionDraft;

    @Column(name = "published")
    private Boolean published;

    @ManyToOne
    @JsonIgnoreProperties("versions")
    private Document document;

    @OneToOne
    @JoinColumn(unique = true)
    private VersionSummary summary;

    @OneToMany(mappedBy = "version")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Part> parts = new HashSet<>();
    @OneToMany(mappedBy = "version")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Workflow> workflows = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersionMajor() {
        return versionMajor;
    }

    public Version versionMajor(Integer versionMajor) {
        this.versionMajor = versionMajor;
        return this;
    }

    public void setVersionMajor(Integer versionMajor) {
        this.versionMajor = versionMajor;
    }

    public Integer getVersionMinor() {
        return versionMinor;
    }

    public Version versionMinor(Integer versionMinor) {
        this.versionMinor = versionMinor;
        return this;
    }

    public void setVersionMinor(Integer versionMinor) {
        this.versionMinor = versionMinor;
    }

    public Integer getVersionDraft() {
        return versionDraft;
    }

    public Version versionDraft(Integer versionDraft) {
        this.versionDraft = versionDraft;
        return this;
    }

    public void setVersionDraft(Integer versionDraft) {
        this.versionDraft = versionDraft;
    }

    public Boolean isPublished() {
        return published;
    }

    public Version published(Boolean published) {
        this.published = published;
        return this;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Document getDocument() {
        return document;
    }

    public Version document(Document document) {
        this.document = document;
        return this;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public VersionSummary getSummary() {
        return summary;
    }

    public Version summary(VersionSummary versionSummary) {
        this.summary = versionSummary;
        return this;
    }

    public void setSummary(VersionSummary versionSummary) {
        this.summary = versionSummary;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public Version parts(Set<Part> parts) {
        this.parts = parts;
        return this;
    }

    public Version addParts(Part part) {
        this.parts.add(part);
        part.setVersion(this);
        return this;
    }

    public Version removeParts(Part part) {
        this.parts.remove(part);
        part.setVersion(null);
        return this;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public Set<Workflow> getWorkflows() {
        return workflows;
    }

    public Version workflows(Set<Workflow> workflows) {
        this.workflows = workflows;
        return this;
    }

    public Version addWorkflow(Workflow workflow) {
        this.workflows.add(workflow);
        workflow.setVersion(this);
        return this;
    }

    public Version removeWorkflow(Workflow workflow) {
        this.workflows.remove(workflow);
        workflow.setVersion(null);
        return this;
    }

    public void setWorkflows(Set<Workflow> workflows) {
        this.workflows = workflows;
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
        Version version = (Version) o;
        if (version.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), version.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Version{" +
            "id=" + getId() +
            ", versionMajor=" + getVersionMajor() +
            ", versionMinor=" + getVersionMinor() +
            ", versionDraft=" + getVersionDraft() +
            ", published='" + isPublished() + "'" +
            "}";
    }
}
