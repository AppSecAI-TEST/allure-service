package com.allure.service.srv.impl;

import com.allure.service.framework.constants.MessageCode;
import com.allure.service.framework.exceprion.ApiException;
import com.allure.service.persistence.entity.Category;
import com.allure.service.persistence.repository.BlogRepository;
import com.allure.service.persistence.repository.CategoryRepository;
import com.allure.service.request.CategoryCreateRequest;
import com.allure.service.request.CategoryUpdateRequest;
import com.allure.service.srv.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final BlogRepository blogRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, BlogRepository blogRepository) {
        this.categoryRepository = categoryRepository;
        this.blogRepository = blogRepository;
    }

    @Transactional
    @Override
    public Category create(CategoryCreateRequest request) {
        Category category = categoryRepository.findByCode(request.getCode());
        if (category != null) throw new ApiException(MessageCode.Category.CODE_EXIST, request.getCode());
        category = categoryRepository.findByLabel(request.getLabel());
        if (category != null) throw new ApiException(MessageCode.Category.LABEL_EXIST, request.getLabel());
        category = new Category();
        category.setCode(request.getCode());
        category.setLabel(request.getLabel());
        categoryRepository.save(category);
        return category;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        int count = blogRepository.countByCategoryId(id);
        if (count > 0) {
            throw new ApiException(MessageCode.Category.IN_USE, id);
        }
        categoryRepository.delete(id);
    }

    @Override
    public Category find(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public List<Category> list() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public Category update(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findOne(id);
        if (category == null) throw new ApiException(MessageCode.Category.NOT_FOUND, id);
        Category existed = categoryRepository.findByCode(request.getCode());
        if (!existed.getId().equals(category.getId()))
            throw new ApiException(MessageCode.Category.CODE_EXIST, request.getCode());
        existed = categoryRepository.findByLabel(request.getLabel());
        if (!existed.getId().equals(category.getId()))
            throw new ApiException(MessageCode.Category.LABEL_EXIST, request.getLabel());
        category.setCode(request.getCode());
        category.setLabel(request.getLabel());
        categoryRepository.save(category);
        return category;
    }
}
