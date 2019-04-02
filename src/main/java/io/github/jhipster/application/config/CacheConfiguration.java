package io.github.jhipster.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Ability.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Ability.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Ability.class.getName() + ".groups", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Ability.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Action.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Attachment.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Bookmark.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Comment.class.getName() + ".replies", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Document.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Document.class.getName() + ".versions", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Document.class.getName() + ".logs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Document.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Document.class.getName() + ".keywords", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Document.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Document.class.getName() + ".participants", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DocumentTemplate.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DocumentTemplate.class.getName() + ".sections", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DocumentTemplatePart.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Email.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.UserGroup.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.UserGroup.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.UserGroup.class.getName() + ".abilities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.UserGroup.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.UserGroup.class.getName() + ".organizations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Keyword.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Lock.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.LogEntry.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Manual.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Manual.class.getName() + ".sections", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Manual.class.getName() + ".logs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Manual.class.getName() + ".templates", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Manual.class.getName() + ".manualTypes", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ManualType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Notification.class.getName() + ".recipients", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Organization.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Organization.class.getName() + ".manuals", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Organization.class.getName() + ".logs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Organization.class.getName() + ".reports", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Organization.class.getName() + ".groups", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Organization.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Part.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Participant.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Recipient.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Report.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Role.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Role.class.getName() + ".abilities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Role.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Role.class.getName() + ".groups", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Role.class.getName() + ".organizations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Section.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Section.class.getName() + ".documents", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Section.class.getName() + ".logs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Step.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Step.class.getName() + ".groups", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Step.class.getName() + ".attachments", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Step.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Step.class.getName() + ".emails", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Step.class.getName() + ".actions", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Step.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Step.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Title.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Users.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Users.class.getName() + ".titles", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Users.class.getName() + ".bookmarks", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Users.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Users.class.getName() + ".groups", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Users.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Users.class.getName() + ".abilities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Version.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Version.class.getName() + ".parts", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Version.class.getName() + ".workflows", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.VersionSummary.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Workflow.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Workflow.class.getName() + ".steps", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
