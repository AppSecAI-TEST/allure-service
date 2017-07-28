package com.allure.service.persistence.entity;

import com.allure.service.framework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
@Entity
@Table(name = "tag")
@Getter
@Setter
public class Tag extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog", nullable = false)
    private Blog blog;
}
