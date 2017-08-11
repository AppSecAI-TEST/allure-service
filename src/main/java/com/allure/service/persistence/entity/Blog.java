package com.allure.service.persistence.entity;

import com.allure.service.framework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
@Entity
@Table(name = "blog")
@Getter
@Setter
public class Blog extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "text")
    private String text;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blog")
    private List<Tag> tags;

}
