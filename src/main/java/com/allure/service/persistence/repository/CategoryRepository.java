package com.allure.service.persistence.repository;

import com.allure.service.framework.repository.BaseRepository;
import com.allure.service.persistence.entity.Category;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
public interface CategoryRepository extends BaseRepository<Category> {

    Category findByCode(String code);

    Category findByLabel(String label);

    @Query("select o from Category o order by o.createdDate desc")
    List<Category> findAll();

}
