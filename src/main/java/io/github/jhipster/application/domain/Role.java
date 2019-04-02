package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "role_abilities",
               joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "abilities_id", referencedColumnName = "id"))
    private Set<Ability> abilities = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Users> users = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<UserGroup> groups = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Organization> organizations = new HashSet<>();

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

    public Role name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public Role abilities(Set<Ability> abilities) {
        this.abilities = abilities;
        return this;
    }

    public Role addAbilities(Ability ability) {
        this.abilities.add(ability);
        ability.getRoles().add(this);
        return this;
    }

    public Role removeAbilities(Ability ability) {
        this.abilities.remove(ability);
        ability.getRoles().remove(this);
        return this;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public Role users(Set<Users> users) {
        this.users = users;
        return this;
    }

    public Role addUsers(Users users) {
        this.users.add(users);
        users.getRoles().add(this);
        return this;
    }

    public Role removeUsers(Users users) {
        this.users.remove(users);
        users.getRoles().remove(this);
        return this;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public Role groups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
        return this;
    }

    public Role addGroups(UserGroup userGroup) {
        this.groups.add(userGroup);
        userGroup.getRoles().add(this);
        return this;
    }

    public Role removeGroups(UserGroup userGroup) {
        this.groups.remove(userGroup);
        userGroup.getRoles().remove(this);
        return this;
    }

    public void setGroups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public Role organizations(Set<Organization> organizations) {
        this.organizations = organizations;
        return this;
    }

    public Role addOrganizations(Organization organization) {
        this.organizations.add(organization);
        organization.getRoles().add(this);
        return this;
    }

    public Role removeOrganizations(Organization organization) {
        this.organizations.remove(organization);
        organization.getRoles().remove(this);
        return this;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
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
        Role role = (Role) o;
        if (role.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
