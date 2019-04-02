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
 * A Users.
 */
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "jhi_uid")
    private String uid;

    @Column(name = "hopkins_id")
    private String hopkinsId;

    @Column(name = "defer_to_proxy")
    private Boolean deferToProxy;

    @OneToOne
    @JoinColumn(unique = true)
    private Users proxy;

    @OneToOne
    @JoinColumn(unique = true)
    private Organization organization;

    @OneToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Title> titles = new HashSet<>();
    @OneToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bookmark> bookmarks = new HashSet<>();
    @OneToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "users_groups",
               joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "groups_id", referencedColumnName = "id"))
    private Set<UserGroup> groups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "users_groups",
               joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "groups_id", referencedColumnName = "id"))
    private Set<UserGroup> groups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "users_roles",
               joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "users_abilities",
               joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "abilities_id", referencedColumnName = "id"))
    private Set<Ability> abilities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Users firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Users lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Users email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Users enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUid() {
        return uid;
    }

    public Users uid(String uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHopkinsId() {
        return hopkinsId;
    }

    public Users hopkinsId(String hopkinsId) {
        this.hopkinsId = hopkinsId;
        return this;
    }

    public void setHopkinsId(String hopkinsId) {
        this.hopkinsId = hopkinsId;
    }

    public Boolean isDeferToProxy() {
        return deferToProxy;
    }

    public Users deferToProxy(Boolean deferToProxy) {
        this.deferToProxy = deferToProxy;
        return this;
    }

    public void setDeferToProxy(Boolean deferToProxy) {
        this.deferToProxy = deferToProxy;
    }

    public Users getProxy() {
        return proxy;
    }

    public Users proxy(Users users) {
        this.proxy = users;
        return this;
    }

    public void setProxy(Users users) {
        this.proxy = users;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Users organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<Title> getTitles() {
        return titles;
    }

    public Users titles(Set<Title> titles) {
        this.titles = titles;
        return this;
    }

    public Users addTitles(Title title) {
        this.titles.add(title);
        title.setUsers(this);
        return this;
    }

    public Users removeTitles(Title title) {
        this.titles.remove(title);
        title.setUsers(null);
        return this;
    }

    public void setTitles(Set<Title> titles) {
        this.titles = titles;
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public Users bookmarks(Set<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        return this;
    }

    public Users addBookmarks(Bookmark bookmark) {
        this.bookmarks.add(bookmark);
        bookmark.setUsers(this);
        return this;
    }

    public Users removeBookmarks(Bookmark bookmark) {
        this.bookmarks.remove(bookmark);
        bookmark.setUsers(null);
        return this;
    }

    public void setBookmarks(Set<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Users comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Users addComments(Comment comment) {
        this.comments.add(comment);
        comment.setUsers(this);
        return this;
    }

    public Users removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setUsers(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public Users groups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
        return this;
    }

    public Users addGroups(UserGroup userGroup) {
        this.groups.add(userGroup);
        userGroup.getUsers().add(this);
        return this;
    }

    public Users removeGroups(UserGroup userGroup) {
        this.groups.remove(userGroup);
        userGroup.getUsers().remove(this);
        return this;
    }

    public void setGroups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public Users groups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
        return this;
    }

    public Users addGroups(UserGroup userGroup) {
        this.groups.add(userGroup);
        userGroup.getUsers().add(this);
        return this;
    }

    public Users removeGroups(UserGroup userGroup) {
        this.groups.remove(userGroup);
        userGroup.getUsers().remove(this);
        return this;
    }

    public void setGroups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Users roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Users addRoles(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
        return this;
    }

    public Users removeRoles(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public Users abilities(Set<Ability> abilities) {
        this.abilities = abilities;
        return this;
    }

    public Users addAbilities(Ability ability) {
        this.abilities.add(ability);
        ability.getUsers().add(this);
        return this;
    }

    public Users removeAbilities(Ability ability) {
        this.abilities.remove(ability);
        ability.getUsers().remove(this);
        return this;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
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
        Users users = (Users) o;
        if (users.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), users.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Users{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", uid='" + getUid() + "'" +
            ", hopkinsId='" + getHopkinsId() + "'" +
            ", deferToProxy='" + isDeferToProxy() + "'" +
            "}";
    }
}
