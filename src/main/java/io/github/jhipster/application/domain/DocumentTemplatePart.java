package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DocumentTemplatePart.
 */
@Entity
@Table(name = "document_template_part")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentTemplatePart implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @Column(name = "read_only")
    private Boolean readOnly;

    @ManyToOne
    @JsonIgnoreProperties("sections")
    private DocumentTemplate documentTemplate;

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

    public DocumentTemplatePart name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public DocumentTemplatePart text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isReadOnly() {
        return readOnly;
    }

    public DocumentTemplatePart readOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public DocumentTemplate getDocumentTemplate() {
        return documentTemplate;
    }

    public DocumentTemplatePart documentTemplate(DocumentTemplate documentTemplate) {
        this.documentTemplate = documentTemplate;
        return this;
    }

    public void setDocumentTemplate(DocumentTemplate documentTemplate) {
        this.documentTemplate = documentTemplate;
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
        DocumentTemplatePart documentTemplatePart = (DocumentTemplatePart) o;
        if (documentTemplatePart.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentTemplatePart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentTemplatePart{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", text='" + getText() + "'" +
            ", readOnly='" + isReadOnly() + "'" +
            "}";
    }
}
