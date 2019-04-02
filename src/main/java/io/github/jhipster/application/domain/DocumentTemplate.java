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
 * A DocumentTemplate.
 */
@Entity
@Table(name = "document_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentTemplate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "primary_header")
    private String primaryHeader;

    @Column(name = "primary_footer")
    private String primaryFooter;

    @Column(name = "appendix_header")
    private String appendixHeader;

    @Column(name = "appendix_footer")
    private String appendixFooter;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(mappedBy = "documentTemplate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DocumentTemplatePart> sections = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("templates")
    private Manual manual;

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

    public DocumentTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryHeader() {
        return primaryHeader;
    }

    public DocumentTemplate primaryHeader(String primaryHeader) {
        this.primaryHeader = primaryHeader;
        return this;
    }

    public void setPrimaryHeader(String primaryHeader) {
        this.primaryHeader = primaryHeader;
    }

    public String getPrimaryFooter() {
        return primaryFooter;
    }

    public DocumentTemplate primaryFooter(String primaryFooter) {
        this.primaryFooter = primaryFooter;
        return this;
    }

    public void setPrimaryFooter(String primaryFooter) {
        this.primaryFooter = primaryFooter;
    }

    public String getAppendixHeader() {
        return appendixHeader;
    }

    public DocumentTemplate appendixHeader(String appendixHeader) {
        this.appendixHeader = appendixHeader;
        return this;
    }

    public void setAppendixHeader(String appendixHeader) {
        this.appendixHeader = appendixHeader;
    }

    public String getAppendixFooter() {
        return appendixFooter;
    }

    public DocumentTemplate appendixFooter(String appendixFooter) {
        this.appendixFooter = appendixFooter;
        return this;
    }

    public void setAppendixFooter(String appendixFooter) {
        this.appendixFooter = appendixFooter;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public DocumentTemplate enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<DocumentTemplatePart> getSections() {
        return sections;
    }

    public DocumentTemplate sections(Set<DocumentTemplatePart> documentTemplateParts) {
        this.sections = documentTemplateParts;
        return this;
    }

    public DocumentTemplate addSections(DocumentTemplatePart documentTemplatePart) {
        this.sections.add(documentTemplatePart);
        documentTemplatePart.setDocumentTemplate(this);
        return this;
    }

    public DocumentTemplate removeSections(DocumentTemplatePart documentTemplatePart) {
        this.sections.remove(documentTemplatePart);
        documentTemplatePart.setDocumentTemplate(null);
        return this;
    }

    public void setSections(Set<DocumentTemplatePart> documentTemplateParts) {
        this.sections = documentTemplateParts;
    }

    public Manual getManual() {
        return manual;
    }

    public DocumentTemplate manual(Manual manual) {
        this.manual = manual;
        return this;
    }

    public void setManual(Manual manual) {
        this.manual = manual;
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
        DocumentTemplate documentTemplate = (DocumentTemplate) o;
        if (documentTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", primaryHeader='" + getPrimaryHeader() + "'" +
            ", primaryFooter='" + getPrimaryFooter() + "'" +
            ", appendixHeader='" + getAppendixHeader() + "'" +
            ", appendixFooter='" + getAppendixFooter() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
