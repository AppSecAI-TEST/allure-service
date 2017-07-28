package com.allure.service.srv;

import com.allure.service.persistence.entity.Category;
import com.allure.service.request.CategoryCreateRequest;
import com.allure.service.request.CategoryUpdateRequest;

import java.util.List;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
public interface CategoryService {

    Category create(CategoryCreateRequest request);

    void delete(Long id);

    Category find(Long id);

    List<Category> list();

    Category update(Long id, CategoryUpdateRequest request);

}
