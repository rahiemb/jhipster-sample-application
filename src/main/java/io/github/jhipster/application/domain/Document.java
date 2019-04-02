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

import io.github.jhipster.application.domain.enumeration.ExpirationType;

import io.github.jhipster.application.domain.enumeration.TimeInterval;

import io.github.jhipster.application.domain.enumeration.ExpirationBase;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "seed")
    private Integer seed;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "jhi_link")
    private String link;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "supersedes_date")
    private LocalDate supersedesDate;

    @Column(name = "original_date")
    private LocalDate originalDate;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Column(name = "revision_date")
    private LocalDate revisionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "expiration_type")
    private ExpirationType expirationType;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "expiration_period")
    private Integer expirationPeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "expiration_interval")
    private TimeInterval expirationInterval;

    @Enumerated(EnumType.STRING)
    @Column(name = "expiration_base")
    private ExpirationBase expirationBase;

    @Column(name = "table_of_contents")
    private Boolean tableOfContents;

    @Column(name = "retired")
    private Boolean retired;

    @Column(name = "retired_date")
    private LocalDate retiredDate;

    @Column(name = "retired_note")
    private String retiredNote;

    @OneToOne
    @JoinColumn(unique = true)
    private Lock lock;

    @OneToOne
    @JoinColumn(unique = true)
    private Workflow workflow;

    @OneToMany(mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Version> versions = new HashSet<>();
    @OneToMany(mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LogEntry> logs = new HashSet<>();
    @OneToMany(mappedBy = "document")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "document_keywords",
               joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "keywords_id", referencedColumnName = "id"))
    private Set<Keyword> keywords = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "document_categories",
               joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "document_participants",
               joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "participants_id", referencedColumnName = "id"))
    private Set<Participant> participants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("documents")
    private Section section;

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

    public Document name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Document code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSeed() {
        return seed;
    }

    public Document seed(Integer seed) {
        this.seed = seed;
        return this;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public String getDescription() {
        return description;
    }

    public Document description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Document enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getLink() {
        return link;
    }

    public Document link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Document orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public Document effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public Document approvalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
        return this;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocalDate getSupersedesDate() {
        return supersedesDate;
    }

    public Document supersedesDate(LocalDate supersedesDate) {
        this.supersedesDate = supersedesDate;
        return this;
    }

    public void setSupersedesDate(LocalDate supersedesDate) {
        this.supersedesDate = supersedesDate;
    }

    public LocalDate getOriginalDate() {
        return originalDate;
    }

    public Document originalDate(LocalDate originalDate) {
        this.originalDate = originalDate;
        return this;
    }

    public void setOriginalDate(LocalDate originalDate) {
        this.originalDate = originalDate;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public Document reviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
        return this;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public LocalDate getRevisionDate() {
        return revisionDate;
    }

    public Document revisionDate(LocalDate revisionDate) {
        this.revisionDate = revisionDate;
        return this;
    }

    public void setRevisionDate(LocalDate revisionDate) {
        this.revisionDate = revisionDate;
    }

    public ExpirationType getExpirationType() {
        return expirationType;
    }

    public Document expirationType(ExpirationType expirationType) {
        this.expirationType = expirationType;
        return this;
    }

    public void setExpirationType(ExpirationType expirationType) {
        this.expirationType = expirationType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Document expirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getExpirationPeriod() {
        return expirationPeriod;
    }

    public Document expirationPeriod(Integer expirationPeriod) {
        this.expirationPeriod = expirationPeriod;
        return this;
    }

    public void setExpirationPeriod(Integer expirationPeriod) {
        this.expirationPeriod = expirationPeriod;
    }

    public TimeInterval getExpirationInterval() {
        return expirationInterval;
    }

    public Document expirationInterval(TimeInterval expirationInterval) {
        this.expirationInterval = expirationInterval;
        return this;
    }

    public void setExpirationInterval(TimeInterval expirationInterval) {
        this.expirationInterval = expirationInterval;
    }

    public ExpirationBase getExpirationBase() {
        return expirationBase;
    }

    public Document expirationBase(ExpirationBase expirationBase) {
        this.expirationBase = expirationBase;
        return this;
    }

    public void setExpirationBase(ExpirationBase expirationBase) {
        this.expirationBase = expirationBase;
    }

    public Boolean isTableOfContents() {
        return tableOfContents;
    }

    public Document tableOfContents(Boolean tableOfContents) {
        this.tableOfContents = tableOfContents;
        return this;
    }

    public void setTableOfContents(Boolean tableOfContents) {
        this.tableOfContents = tableOfContents;
    }

    public Boolean isRetired() {
        return retired;
    }

    public Document retired(Boolean retired) {
        this.retired = retired;
        return this;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public LocalDate getRetiredDate() {
        return retiredDate;
    }

    public Document retiredDate(LocalDate retiredDate) {
        this.retiredDate = retiredDate;
        return this;
    }

    public void setRetiredDate(LocalDate retiredDate) {
        this.retiredDate = retiredDate;
    }

    public String getRetiredNote() {
        return retiredNote;
    }

    public Document retiredNote(String retiredNote) {
        this.retiredNote = retiredNote;
        return this;
    }

    public void setRetiredNote(String retiredNote) {
        this.retiredNote = retiredNote;
    }

    public Lock getLock() {
        return lock;
    }

    public Document lock(Lock lock) {
        this.lock = lock;
        return this;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public Document workflow(Workflow workflow) {
        this.workflow = workflow;
        return this;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Set<Version> getVersions() {
        return versions;
    }

    public Document versions(Set<Version> versions) {
        this.versions = versions;
        return this;
    }

    public Document addVersions(Version version) {
        this.versions.add(version);
        version.setDocument(this);
        return this;
    }

    public Document removeVersions(Version version) {
        this.versions.remove(version);
        version.setDocument(null);
        return this;
    }

    public void setVersions(Set<Version> versions) {
        this.versions = versions;
    }

    public Set<LogEntry> getLogs() {
        return logs;
    }

    public Document logs(Set<LogEntry> logEntries) {
        this.logs = logEntries;
        return this;
    }

    public Document addLog(LogEntry logEntry) {
        this.logs.add(logEntry);
        logEntry.setDocument(this);
        return this;
    }

    public Document removeLog(LogEntry logEntry) {
        this.logs.remove(logEntry);
        logEntry.setDocument(null);
        return this;
    }

    public void setLogs(Set<LogEntry> logEntries) {
        this.logs = logEntries;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Document comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Document addComments(Comment comment) {
        this.comments.add(comment);
        comment.setDocument(this);
        return this;
    }

    public Document removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setDocument(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public Document keywords(Set<Keyword> keywords) {
        this.keywords = keywords;
        return this;
    }

    public Document addKeywords(Keyword keyword) {
        this.keywords.add(keyword);
        keyword.getDocuments().add(this);
        return this;
    }

    public Document removeKeywords(Keyword keyword) {
        this.keywords.remove(keyword);
        keyword.getDocuments().remove(this);
        return this;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Document categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Document addCategories(Category category) {
        this.categories.add(category);
        category.getDocuments().add(this);
        return this;
    }

    public Document removeCategories(Category category) {
        this.categories.remove(category);
        category.getDocuments().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public Document participants(Set<Participant> participants) {
        this.participants = participants;
        return this;
    }

    public Document addParticipants(Participant participant) {
        this.participants.add(participant);
        participant.getDocuments().add(this);
        return this;
    }

    public Document removeParticipants(Participant participant) {
        this.participants.remove(participant);
        participant.getDocuments().remove(this);
        return this;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public Section getSection() {
        return section;
    }

    public Document section(Section section) {
        this.section = section;
        return this;
    }

    public void setSection(Section section) {
        this.section = section;
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
        Document document = (Document) o;
        if (document.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", seed=" + getSeed() +
            ", description='" + getDescription() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", link='" + getLink() + "'" +
            ", orderId=" + getOrderId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", approvalDate='" + getApprovalDate() + "'" +
            ", supersedesDate='" + getSupersedesDate() + "'" +
            ", originalDate='" + getOriginalDate() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            ", revisionDate='" + getRevisionDate() + "'" +
            ", expirationType='" + getExpirationType() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", expirationPeriod=" + getExpirationPeriod() +
            ", expirationInterval='" + getExpirationInterval() + "'" +
            ", expirationBase='" + getExpirationBase() + "'" +
            ", tableOfContents='" + isTableOfContents() + "'" +
            ", retired='" + isRetired() + "'" +
            ", retiredDate='" + getRetiredDate() + "'" +
            ", retiredNote='" + getRetiredNote() + "'" +
            "}";
    }
}
