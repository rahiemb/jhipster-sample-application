package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.CommentContext;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "context")
    private CommentContext context;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "jhi_timestamp")
    private ZonedDateTime timestamp;

    @Column(name = "reply_to")
    private String replyTo;

    @Column(name = "item_id")
    private String itemId;

    @OneToOne
    @JoinColumn(unique = true)
    private Comment replyTo;

    @OneToOne
    @JoinColumn(unique = true)
    private Users user;

    @ManyToOne
    @JsonIgnoreProperties("replies")
    private Comment comment;

    @OneToMany(mappedBy = "comment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> replies = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Document document;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Step step;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Users users;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Comment text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentContext getContext() {
        return context;
    }

    public Comment context(CommentContext context) {
        this.context = context;
        return this;
    }

    public void setContext(CommentContext context) {
        this.context = context;
    }

    public String getUser() {
        return user;
    }

    public Comment user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Comment timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public Comment replyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getItemId() {
        return itemId;
    }

    public Comment itemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Comment getReplyTo() {
        return replyTo;
    }

    public Comment replyTo(Comment comment) {
        this.replyTo = comment;
        return this;
    }

    public void setReplyTo(Comment comment) {
        this.replyTo = comment;
    }

    public Users getUser() {
        return user;
    }

    public Comment user(Users users) {
        this.user = users;
        return this;
    }

    public void setUser(Users users) {
        this.user = users;
    }

    public Comment getComment() {
        return comment;
    }

    public Comment comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Set<Comment> getReplies() {
        return replies;
    }

    public Comment replies(Set<Comment> comments) {
        this.replies = comments;
        return this;
    }

    public Comment addReplies(Comment comment) {
        this.replies.add(comment);
        comment.setComment(this);
        return this;
    }

    public Comment removeReplies(Comment comment) {
        this.replies.remove(comment);
        comment.setComment(null);
        return this;
    }

    public void setReplies(Set<Comment> comments) {
        this.replies = comments;
    }

    public Document getDocument() {
        return document;
    }

    public Comment document(Document document) {
        this.document = document;
        return this;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Step getStep() {
        return step;
    }

    public Comment step(Step step) {
        this.step = step;
        return this;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Users getUsers() {
        return users;
    }

    public Comment users(Users users) {
        this.users = users;
        return this;
    }

    public void setUsers(Users users) {
        this.users = users;
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
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", context='" + getContext() + "'" +
            ", user='" + getUser() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", replyTo='" + getReplyTo() + "'" +
            ", itemId='" + getItemId() + "'" +
            "}";
    }
}
