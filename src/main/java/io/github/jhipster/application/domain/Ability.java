package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.AbilityAction;

import io.github.jhipster.application.domain.enumeration.AbilityScope;

/**
 * A Ability.
 */
@Entity
@Table(name = "ability")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ability implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "resource_id")
    private Long resourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private AbilityAction action;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_scope")
    private AbilityScope scope;

    @ManyToMany(mappedBy = "abilities")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Users> users = new HashSet<>();

    @ManyToMany(mappedBy = "abilities")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<UserGroup> groups = new HashSet<>();

    @ManyToMany(mappedBy = "abilities")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Ability resourceId(Long resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public AbilityAction getAction() {
        return action;
    }

    public Ability action(AbilityAction action) {
        this.action = action;
        return this;
    }

    public void setAction(AbilityAction action) {
        this.action = action;
    }

    public AbilityScope getScope() {
        return scope;
    }

    public Ability scope(AbilityScope scope) {
        this.scope = scope;
        return this;
    }

    public void setScope(AbilityScope scope) {
        this.scope = scope;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public Ability users(Set<Users> users) {
        this.users = users;
        return this;
    }

    public Ability addUsers(Users users) {
        this.users.add(users);
        users.getAbilities().add(this);
        return this;
    }

    public Ability removeUsers(Users users) {
        this.users.remove(users);
        users.getAbilities().remove(this);
        return this;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public Ability groups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
        return this;
    }

    public Ability addGroups(UserGroup userGroup) {
        this.groups.add(userGroup);
        userGroup.getAbilities().add(this);
        return this;
    }

    public Ability removeGroups(UserGroup userGroup) {
        this.groups.remove(userGroup);
        userGroup.getAbilities().remove(this);
        return this;
    }

    public void setGroups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Ability roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Ability addRoles(Role role) {
        this.roles.add(role);
        role.getAbilities().add(this);
        return this;
    }

    public Ability removeRoles(Role role) {
        this.roles.remove(role);
        role.getAbilities().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        Ability ability = (Ability) o;
        if (ability.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ability{" +
            "id=" + getId() +
            ", resourceId=" + getResourceId() +
            ", action='" + getAction() + "'" +
            ", scope='" + getScope() + "'" +
            "}";
    }
}
