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

import io.github.jhipster.application.domain.enumeration.WorkflowStatus;

/**
 * A Workflow.
 */
@Entity
@Table(name = "workflow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workflow implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkflowStatus status;

    @ManyToOne
    @JsonIgnoreProperties("workflows")
    private Version version;

    @OneToMany(mappedBy = "workflow")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Step> steps = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowStatus getStatus() {
        return status;
    }

    public Workflow status(WorkflowStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(WorkflowStatus status) {
        this.status = status;
    }

    public Version getVersion() {
        return version;
    }

    public Workflow version(Version version) {
        this.version = version;
        return this;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Set<Step> getSteps() {
        return steps;
    }

    public Workflow steps(Set<Step> steps) {
        this.steps = steps;
        return this;
    }

    public Workflow addSteps(Step step) {
        this.steps.add(step);
        step.setWorkflow(this);
        return this;
    }

    public Workflow removeSteps(Step step) {
        this.steps.remove(step);
        step.setWorkflow(null);
        return this;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
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
        Workflow workflow = (Workflow) o;
        if (workflow.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workflow.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Workflow{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
