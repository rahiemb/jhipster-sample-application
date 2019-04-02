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

import io.github.jhipster.application.domain.enumeration.GroupType;

/**
 * A UserGroup.
 */
@Entity
@Table(name = "user_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private GroupType type;

    @Column(name = "ad")
    private Boolean ad;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_group_roles",
               joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_group_abilities",
               joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "abilities_id", referencedColumnName = "id"))
    private Set<Ability> abilities = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Users> users = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Organization> organizations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("groups")
    private Step step;

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

    public UserGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupType getType() {
        return type;
    }

    public UserGroup type(GroupType type) {
        this.type = type;
        return this;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public Boolean isAd() {
        return ad;
    }

    public UserGroup ad(Boolean ad) {
        this.ad = ad;
        return this;
    }

    public void setAd(Boolean ad) {
        this.ad = ad;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserGroup roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserGroup addRoles(Role role) {
        this.roles.add(role);
        role.getGroups().add(this);
        return this;
    }

    public UserGroup removeRoles(Role role) {
        this.roles.remove(role);
        role.getGroups().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public UserGroup abilities(Set<Ability> abilities) {
        this.abilities = abilities;
        return this;
    }

    public UserGroup addAbilities(Ability ability) {
        this.abilities.add(ability);
        ability.getGroups().add(this);
        return this;
    }

    public UserGroup removeAbilities(Ability ability) {
        this.abilities.remove(ability);
        ability.getGroups().remove(this);
        return this;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public UserGroup users(Set<Users> users) {
        this.users = users;
        return this;
    }

    public UserGroup addUsers(Users users) {
        this.users.add(users);
        users.getGroups().add(this);
        return this;
    }

    public UserGroup removeUsers(Users users) {
        this.users.remove(users);
        users.getGroups().remove(this);
        return this;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public UserGroup organizations(Set<Organization> organizations) {
        this.organizations = organizations;
        return this;
    }

    public UserGroup addOrganizations(Organization organization) {
        this.organizations.add(organization);
        organization.getGroups().add(this);
        return this;
    }

    public UserGroup removeOrganizations(Organization organization) {
        this.organizations.remove(organization);
        organization.getGroups().remove(this);
        return this;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public Step getStep() {
        return step;
    }

    public UserGroup step(Step step) {
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
        UserGroup userGroup = (UserGroup) o;
        if (userGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", ad='" + isAd() + "'" +
            "}";
    }
}
