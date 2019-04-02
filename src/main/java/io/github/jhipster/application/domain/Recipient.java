package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Recipient.
 */
@Entity
@Table(name = "recipient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recipient implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "notification")
    private String notification;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "jhi_timestamp")
    private LocalDate timestamp;

    @ManyToOne
    @JsonIgnoreProperties("recipients")
    private Notification notification;

    @OneToOne
    @JoinColumn(unique = true)
    private Users user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public Recipient notification(String notification) {
        this.notification = notification;
        return this;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getUser() {
        return user;
    }

    public Recipient user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean isSent() {
        return sent;
    }

    public Recipient sent(Boolean sent) {
        this.sent = sent;
        return this;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public Recipient timestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public Notification getNotification() {
        return notification;
    }

    public Recipient notification(Notification notification) {
        this.notification = notification;
        return this;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Users getUser() {
        return user;
    }

    public Recipient user(Users users) {
        this.user = users;
        return this;
    }

    public void setUser(Users users) {
        this.user = users;
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
        Recipient recipient = (Recipient) o;
        if (recipient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recipient{" +
            "id=" + getId() +
            ", notification='" + getNotification() + "'" +
            ", user='" + getUser() + "'" +
            ", sent='" + isSent() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
