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
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "site_url")
    private String siteUrl;

    @Column(name = "repository")
    private String repository;

    @Lob
    @Column(name = "thumbnail")
    private byte[] thumbnail;

    @Column(name = "thumbnail_content_type")
    private String thumbnailContentType;

    @Column(name = "sorl_core")
    private String sorlCore;

    @Column(name = "approval_date_enabled")
    private Boolean approvalDateEnabled;

    @Column(name = "effective_date_enabled")
    private Boolean effectiveDateEnabled;

    @Column(name = "original_date_enabled")
    private Boolean originalDateEnabled;

    @Column(name = "review_date_enabled")
    private Boolean reviewDateEnabled;

    @Column(name = "revision_date_enabled")
    private Boolean revisionDateEnabled;

    @Column(name = "supersedes_date_enabled")
    private Boolean supersedesDateEnabled;

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Manual> manuals = new HashSet<>();
    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LogEntry> logs = new HashSet<>();
    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Report> reports = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "organization_groups",
               joinColumns = @JoinColumn(name = "organization_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "groups_id", referencedColumnName = "id"))
    private Set<UserGroup> groups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "organization_roles",
               joinColumns = @JoinColumn(name = "organization_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

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

    public Organization name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Organization enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Organization orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public Organization siteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
        return this;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getRepository() {
        return repository;
    }

    public Organization repository(String repository) {
        this.repository = repository;
        return this;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public Organization thumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailContentType() {
        return thumbnailContentType;
    }

    public Organization thumbnailContentType(String thumbnailContentType) {
        this.thumbnailContentType = thumbnailContentType;
        return this;
    }

    public void setThumbnailContentType(String thumbnailContentType) {
        this.thumbnailContentType = thumbnailContentType;
    }

    public String getSorlCore() {
        return sorlCore;
    }

    public Organization sorlCore(String sorlCore) {
        this.sorlCore = sorlCore;
        return this;
    }

    public void setSorlCore(String sorlCore) {
        this.sorlCore = sorlCore;
    }

    public Boolean isApprovalDateEnabled() {
        return approvalDateEnabled;
    }

    public Organization approvalDateEnabled(Boolean approvalDateEnabled) {
        this.approvalDateEnabled = approvalDateEnabled;
        return this;
    }

    public void setApprovalDateEnabled(Boolean approvalDateEnabled) {
        this.approvalDateEnabled = approvalDateEnabled;
    }

    public Boolean isEffectiveDateEnabled() {
        return effectiveDateEnabled;
    }

    public Organization effectiveDateEnabled(Boolean effectiveDateEnabled) {
        this.effectiveDateEnabled = effectiveDateEnabled;
        return this;
    }

    public void setEffectiveDateEnabled(Boolean effectiveDateEnabled) {
        this.effectiveDateEnabled = effectiveDateEnabled;
    }

    public Boolean isOriginalDateEnabled() {
        return originalDateEnabled;
    }

    public Organization originalDateEnabled(Boolean originalDateEnabled) {
        this.originalDateEnabled = originalDateEnabled;
        return this;
    }

    public void setOriginalDateEnabled(Boolean originalDateEnabled) {
        this.originalDateEnabled = originalDateEnabled;
    }

    public Boolean isReviewDateEnabled() {
        return reviewDateEnabled;
    }

    public Organization reviewDateEnabled(Boolean reviewDateEnabled) {
        this.reviewDateEnabled = reviewDateEnabled;
        return this;
    }

    public void setReviewDateEnabled(Boolean reviewDateEnabled) {
        this.reviewDateEnabled = reviewDateEnabled;
    }

    public Boolean isRevisionDateEnabled() {
        return revisionDateEnabled;
    }

    public Organization revisionDateEnabled(Boolean revisionDateEnabled) {
        this.revisionDateEnabled = revisionDateEnabled;
        return this;
    }

    public void setRevisionDateEnabled(Boolean revisionDateEnabled) {
        this.revisionDateEnabled = revisionDateEnabled;
    }

    public Boolean isSupersedesDateEnabled() {
        return supersedesDateEnabled;
    }

    public Organization supersedesDateEnabled(Boolean supersedesDateEnabled) {
        this.supersedesDateEnabled = supersedesDateEnabled;
        return this;
    }

    public void setSupersedesDateEnabled(Boolean supersedesDateEnabled) {
        this.supersedesDateEnabled = supersedesDateEnabled;
    }

    public Set<Manual> getManuals() {
        return manuals;
    }

    public Organization manuals(Set<Manual> manuals) {
        this.manuals = manuals;
        return this;
    }

    public Organization addManuals(Manual manual) {
        this.manuals.add(manual);
        manual.setOrganization(this);
        return this;
    }

    public Organization removeManuals(Manual manual) {
        this.manuals.remove(manual);
        manual.setOrganization(null);
        return this;
    }

    public void setManuals(Set<Manual> manuals) {
        this.manuals = manuals;
    }

    public Set<LogEntry> getLogs() {
        return logs;
    }

    public Organization logs(Set<LogEntry> logEntries) {
        this.logs = logEntries;
        return this;
    }

    public Organization addLog(LogEntry logEntry) {
        this.logs.add(logEntry);
        logEntry.setOrganization(this);
        return this;
    }

    public Organization removeLog(LogEntry logEntry) {
        this.logs.remove(logEntry);
        logEntry.setOrganization(null);
        return this;
    }

    public void setLogs(Set<LogEntry> logEntries) {
        this.logs = logEntries;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public Organization reports(Set<Report> reports) {
        this.reports = reports;
        return this;
    }

    public Organization addReports(Report report) {
        this.reports.add(report);
        report.setOrganization(this);
        return this;
    }

    public Organization removeReports(Report report) {
        this.reports.remove(report);
        report.setOrganization(null);
        return this;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public Organization groups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
        return this;
    }

    public Organization addGroups(UserGroup userGroup) {
        this.groups.add(userGroup);
        userGroup.getOrganizations().add(this);
        return this;
    }

    public Organization removeGroups(UserGroup userGroup) {
        this.groups.remove(userGroup);
        userGroup.getOrganizations().remove(this);
        return this;
    }

    public void setGroups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Organization roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Organization addRoles(Role role) {
        this.roles.add(role);
        role.getOrganizations().add(this);
        return this;
    }

    public Organization removeRoles(Role role) {
        this.roles.remove(role);
        role.getOrganizations().remove(this);
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
        Organization organization = (Organization) o;
        if (organization.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", orderId=" + getOrderId() +
            ", siteUrl='" + getSiteUrl() + "'" +
            ", repository='" + getRepository() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", thumbnailContentType='" + getThumbnailContentType() + "'" +
            ", sorlCore='" + getSorlCore() + "'" +
            ", approvalDateEnabled='" + isApprovalDateEnabled() + "'" +
            ", effectiveDateEnabled='" + isEffectiveDateEnabled() + "'" +
            ", originalDateEnabled='" + isOriginalDateEnabled() + "'" +
            ", reviewDateEnabled='" + isReviewDateEnabled() + "'" +
            ", revisionDateEnabled='" + isRevisionDateEnabled() + "'" +
            ", supersedesDateEnabled='" + isSupersedesDateEnabled() + "'" +
            "}";
    }
}
