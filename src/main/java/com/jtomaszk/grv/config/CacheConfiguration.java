package com.jtomaszk.grv.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.jtomaszk.grv.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Area.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Area.class.getName() + ".sources", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Source.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Source.class.getName() + ".archives", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Source.class.getName() + ".locations", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.SourceArchive.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.GrvItem.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Error.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Source.class.getName() + ".errors", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.GrvItem.class.getName() + ".errors", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.InputPattern.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.InputPattern.class.getName() + ".sources", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.InputPattern.class.getName() + ".columns", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.PatternColumn.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.GrvItemPerson.class.getName(), jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Location.class.getName() + ".items", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.Location.class.getName() + ".images", jcacheConfiguration);
            cm.createCache(com.jtomaszk.grv.domain.LocationImage.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
