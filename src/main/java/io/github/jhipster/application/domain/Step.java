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

import io.github.jhipster.application.domain.enumeration.StepType;

/**
 * A Step.
 */
@Entity
@Table(name = "step")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Step implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "manager")
    private String manager;

    @Column(name = "workflow")
    private String workflow;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private StepType type;

    @Column(name = "attachments")
    private String attachments;

    @OneToOne
    @JoinColumn(unique = true)
    private User sender;

    @OneToMany(mappedBy = "step")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserGroup> groups = new HashSet<>();
    @OneToMany(mappedBy = "step")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attachment> attachments = new HashSet<>();
    @OneToMany(mappedBy = "step")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(mappedBy = "step")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Email> emails = new HashSet<>();
    @OneToMany(mappedBy = "step")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Action> actions = new HashSet<>();
    @OneToMany(mappedBy = "step")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "step_users",
               joinColumns = @JoinColumn(name = "step_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<Users> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("steps")
    private Workflow workflow;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManager() {
        return manager;
    }

    public Step manager(String manager) {
        this.manager = manager;
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getWorkflow() {
        return workflow;
    }

    public Step workflow(String workflow) {
        this.workflow = workflow;
        return this;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public StepType getType() {
        return type;
    }

    public Step type(StepType type) {
        this.type = type;
        return this;
    }

    public void setType(StepType type) {
        this.type = type;
    }

    public String getAttachments() {
        return attachments;
    }

    public Step attachments(String attachments) {
        this.attachments = attachments;
        return this;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public User getSender() {
        return sender;
    }

    public Step sender(User user) {
        this.sender = user;
        return this;
    }

    public void setSender(User user) {
        this.sender = user;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public Step groups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
        return this;
    }

    public Step addGroups(UserGroup userGroup) {
        this.groups.add(userGroup);
        userGroup.setStep(this);
        return this;
    }

    public Step removeGroups(UserGroup userGroup) {
        this.groups.remove(userGroup);
        userGroup.setStep(null);
        return this;
    }

    public void setGroups(Set<UserGroup> userGroups) {
        this.groups = userGroups;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public Step attachments(Set<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Step addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setStep(this);
        return this;
    }

    public Step removeAttachments(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setStep(null);
        return this;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Step comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Step addComments(Comment comment) {
        this.comments.add(comment);
        comment.setStep(this);
        return this;
    }

    public Step removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setStep(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public Step emails(Set<Email> emails) {
        this.emails = emails;
        return this;
    }

    public Step addEmails(Email email) {
        this.emails.add(email);
        email.setStep(this);
        return this;
    }

    public Step removeEmails(Email email) {
        this.emails.remove(email);
        email.setStep(null);
        return this;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public Step actions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Step addActions(Action action) {
        this.actions.add(action);
        action.setStep(this);
        return this;
    }

    public Step removeActions(Action action) {
        this.actions.remove(action);
        action.setStep(null);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Step notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Step addNotifications(Notification notification) {
        this.notifications.add(notification);
        notification.setStep(this);
        return this;
    }

    public Step removeNotifications(Notification notification) {
        this.notifications.remove(notification);
        notification.setStep(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public Step users(Set<Users> users) {
        this.users = users;
        return this;
    }

    public Step addUsers(Users users) {
        this.users.add(users);
        users.getSteps().add(this);
        return this;
    }

    public Step removeUsers(Users users) {
        this.users.remove(users);
        users.getSteps().remove(this);
        return this;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public Step workflow(Workflow workflow) {
        this.workflow = workflow;
        return this;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
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
        Step step = (Step) o;
        if (step.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), step.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Step{" +
            "id=" + getId() +
            ", manager='" + getManager() + "'" +
            ", workflow='" + getWorkflow() + "'" +
            ", type='" + getType() + "'" +
            ", attachments='" + getAttachments() + "'" +
            "}";
    }
}
