package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.LockType;

import io.github.jhipster.application.domain.enumeration.ObjectType;

/**
 * A Lock.
 */
@Entity
@Table(name = "lock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lock implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "object_id")
    private Long objectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "lock_type")
    private LockType lockType;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private ObjectType objectType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectId() {
        return objectId;
    }

    public Lock objectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public LockType getLockType() {
        return lockType;
    }

    public Lock lockType(LockType lockType) {
        this.lockType = lockType;
        return this;
    }

    public void setLockType(LockType lockType) {
        this.lockType = lockType;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public Lock objectType(ObjectType objectType) {
        this.objectType = objectType;
        return this;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
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
        Lock lock = (Lock) o;
        if (lock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lock{" +
            "id=" + getId() +
            ", objectId=" + getObjectId() +
            ", lockType='" + getLockType() + "'" +
            ", objectType='" + getObjectType() + "'" +
            "}";
    }
}
