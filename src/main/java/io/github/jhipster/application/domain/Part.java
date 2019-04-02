package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Part.
 */
@Entity
@Table(name = "part")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Part implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ancestor_id")
    private Long ancestorId;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToOne
    @JoinColumn(unique = true)
    private Part children;

    @ManyToOne
    @JsonIgnoreProperties("parts")
    private Version version;

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

    public Part name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAncestorId() {
        return ancestorId;
    }

    public Part ancestorId(Long ancestorId) {
        this.ancestorId = ancestorId;
        return this;
    }

    public void setAncestorId(Long ancestorId) {
        this.ancestorId = ancestorId;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Part enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Part getChildren() {
        return children;
    }

    public Part children(Part part) {
        this.children = part;
        return this;
    }

    public void setChildren(Part part) {
        this.children = part;
    }

    public Version getVersion() {
        return version;
    }

    public Part version(Version version) {
        this.version = version;
        return this;
    }

    public void setVersion(Version version) {
        this.version = version;
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
        Part part = (Part) o;
        if (part.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), part.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Part{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ancestorId=" + getAncestorId() +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
