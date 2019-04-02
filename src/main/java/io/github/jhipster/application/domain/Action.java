package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "step")
    private String step;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "notified")
    private Boolean notified;

    @Column(name = "origination_timestamp")
    private ZonedDateTime originationTimestamp;

    @Column(name = "response_timestamp")
    private ZonedDateTime responseTimestamp;

    @OneToOne
    @JoinColumn(unique = true)
    private User recipient;

    @OneToOne
    @JoinColumn(unique = true)
    private Version version;

    @ManyToOne
    @JsonIgnoreProperties("actions")
    private Step step;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStep() {
        return step;
    }

    public Action step(String step) {
        this.step = step;
        return this;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getRecipient() {
        return recipient;
    }

    public Action recipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Boolean isNotified() {
        return notified;
    }

    public Action notified(Boolean notified) {
        this.notified = notified;
        return this;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }

    public ZonedDateTime getOriginationTimestamp() {
        return originationTimestamp;
    }

    public Action originationTimestamp(ZonedDateTime originationTimestamp) {
        this.originationTimestamp = originationTimestamp;
        return this;
    }

    public void setOriginationTimestamp(ZonedDateTime originationTimestamp) {
        this.originationTimestamp = originationTimestamp;
    }

    public ZonedDateTime getResponseTimestamp() {
        return responseTimestamp;
    }

    public Action responseTimestamp(ZonedDateTime responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
        return this;
    }

    public void setResponseTimestamp(ZonedDateTime responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public User getRecipient() {
        return recipient;
    }

    public Action recipient(User user) {
        this.recipient = user;
        return this;
    }

    public void setRecipient(User user) {
        this.recipient = user;
    }

    public Version getVersion() {
        return version;
    }

    public Action version(Version version) {
        this.version = version;
        return this;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Step getStep() {
        return step;
    }

    public Action step(Step step) {
        this.step = step;
        return this;
    }

    public void setStep(Step step) {
        this.step = step;
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
        Action action = (Action) o;
        if (action.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", step='" + getStep() + "'" +
            ", recipient='" + getRecipient() + "'" +
            ", notified='" + isNotified() + "'" +
            ", originationTimestamp='" + getOriginationTimestamp() + "'" +
            ", responseTimestamp='" + getResponseTimestamp() + "'" +
            "}";
    }
}
