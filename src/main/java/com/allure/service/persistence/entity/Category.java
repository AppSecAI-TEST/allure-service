package com.allure.service.persistence.entity;

import com.allure.service.framework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
@Entity
@Table(name = "category")
@Getter
@Setter
public class Category extends BaseEntity {

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "label", unique = true, nullable = false)
    private String label;
}
