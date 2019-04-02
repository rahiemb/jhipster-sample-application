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

import io.github.jhipster.application.domain.enumeration.TimeInterval;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value")
    private Integer value;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_interval")
    private TimeInterval interval;

    @Column(name = "before_date")
    private LocalDate beforeDate;

    @Column(name = "after_date")
    private LocalDate afterDate;

    @Column(name = "send_date")
    private LocalDate sendDate;

    @Column(name = "sender")
    private String sender;

    @Column(name = "message")
    private String message;

    @OneToOne
    @JoinColumn(unique = true)
    private Users sender;

    @OneToMany(mappedBy = "notification")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Recipient> recipients = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("notifications")
    private Step step;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public Notification value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TimeInterval getInterval() {
        return interval;
    }

    public Notification interval(TimeInterval interval) {
        this.interval = interval;
        return this;
    }

    public void setInterval(TimeInterval interval) {
        this.interval = interval;
    }

    public LocalDate getBeforeDate() {
        return beforeDate;
    }

    public Notification beforeDate(LocalDate beforeDate) {
        this.beforeDate = beforeDate;
        return this;
    }

    public void setBeforeDate(LocalDate beforeDate) {
        this.beforeDate = beforeDate;
    }

    public LocalDate getAfterDate() {
        return afterDate;
    }

    public Notification afterDate(LocalDate afterDate) {
        this.afterDate = afterDate;
        return this;
    }

    public void setAfterDate(LocalDate afterDate) {
        this.afterDate = afterDate;
    }

    public LocalDate getSendDate() {
        return sendDate;
    }

    public Notification sendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    public String getSender() {
        return sender;
    }

    public Notification sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public Notification message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getSender() {
        return sender;
    }

    public Notification sender(Users users) {
        this.sender = users;
        return this;
    }

    public void setSender(Users users) {
        this.sender = users;
    }

    public Set<Recipient> getRecipients() {
        return recipients;
    }

    public Notification recipients(Set<Recipient> recipients) {
        this.recipients = recipients;
        return this;
    }

    public Notification addRecipients(Recipient recipient) {
        this.recipients.add(recipient);
        recipient.setNotification(this);
        return this;
    }

    public Notification removeRecipients(Recipient recipient) {
        this.recipients.remove(recipient);
        recipient.setNotification(null);
        return this;
    }

    public void setRecipients(Set<Recipient> recipients) {
        this.recipients = recipients;
    }

    public Step getStep() {
        return step;
    }

    public Notification step(Step step) {
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
        Notification notification = (Notification) o;
        if (notification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", interval='" + getInterval() + "'" +
            ", beforeDate='" + getBeforeDate() + "'" +
            ", afterDate='" + getAfterDate() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", sender='" + getSender() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
