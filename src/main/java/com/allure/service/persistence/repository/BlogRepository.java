package com.allure.service.persistence.repository;

import com.allure.service.framework.repository.BaseRepository;
import com.allure.service.persistence.entity.Blog;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
public interface BlogRepository extends BaseRepository<Blog> {

    int countByCategoryId(Long id);
}
