package com.allure.service.controller;

import com.allure.service.framework.response.BaseResponse;
import com.allure.service.framework.response.SuccessResponse;
import com.allure.service.persistence.entity.Category;
import com.allure.service.request.CategoryCreateRequest;
import com.allure.service.request.CategoryUpdateRequest;
import com.allure.service.srv.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Category> create(@Valid @RequestBody CategoryCreateRequest request) {
        Category category = categoryService.create(request);
        return new SuccessResponse<>(category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return new SuccessResponse<>();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@PathVariable("id") Long id, @Valid @RequestBody CategoryUpdateRequest request) {
        categoryService.update(id, request);
        return new SuccessResponse<>();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Category> find(@PathVariable("id") Long id) {
        Category category = categoryService.find(id);
        return new SuccessResponse<>(category);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<Category>> list() {
        List<Category> categories = categoryService.list();
        return new SuccessResponse<>(categories);
    }

}
