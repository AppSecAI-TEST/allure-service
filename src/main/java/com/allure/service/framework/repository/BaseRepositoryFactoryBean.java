package com.allure.service.framework.repository;

import com.allure.service.framework.entity.BaseEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public class BaseRepositoryFactoryBean<R extends BaseRepository<T>, T extends BaseEntity> extends JpaRepositoryFactoryBean<R, T, Long> {

    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new BaseRepositoryFactory(entityManager);
    }

    private static class BaseRepositoryFactory<T extends BaseEntity> extends JpaRepositoryFactory {

        private final EntityManager em;

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            return new BaseRepositoryImpl<>((Class<T>) information.getDomainType(), em);
        }
    }
}
