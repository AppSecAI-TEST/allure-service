package com.allure.service.framework.repository;

import com.allure.service.framework.entity.BaseEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
public class BaseRepositoryImpl<T extends BaseEntity> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }
}
