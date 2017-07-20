package com.allure.service.framework.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by yang_shoulai on 7/20/2017.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable{

    @Id
    private Long id;

    @Version
    private int version;

}
