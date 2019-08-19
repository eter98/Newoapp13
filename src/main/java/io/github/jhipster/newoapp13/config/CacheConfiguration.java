package io.github.jhipster.newoapp13.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.github.jhipster.newoapp13.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.github.jhipster.newoapp13.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.github.jhipster.newoapp13.domain.User.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Authority.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.User.class.getName() + ".authorities");
            createCache(cm, io.github.jhipster.newoapp13.domain.Miembros.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.EntradaMiembros.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Invitados.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.EntradaInvitados.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Sedes.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.EspacioLibre.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.TipoEspacio.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.HostSede.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Reservas.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.EspaciosReserva.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.RegistroCompra.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.TipoRegistroCompra.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Facturacion.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.RegistroFacturacion.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.EquipoEmpresas.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.CuentaAsociada.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Beneficio.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Empresa.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Landing.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.ProductosServicios.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Pais.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Ciudad.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Blog.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.ComentarioBlog.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Feed.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.ComentarioFeed.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Chat.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.ChatsListado.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.ChatsListado.class.getName() + ".chats");
            createCache(cm, io.github.jhipster.newoapp13.domain.Evento.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.CategoriaContenidos.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.Grupos.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.MiembrosGrupo.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.RecursosFisicos.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.UsoRecursoFisico.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.TipoRecurso.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.ConsumoMarket.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.PrepagoConsumo.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.MargenNewoEventos.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.MargenNewoGrupos.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.MargenNewoBlog.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.MargenNewoProductos.class.getName());
            createCache(cm, io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
