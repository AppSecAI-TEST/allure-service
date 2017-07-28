package com.allure.service.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yang_shoulai on 7/28/2017.
 */
@Setter
@Getter
public class CategoryCreateRequest {

    @NotEmpty
    private String code;

    @NotEmpty
    private String label;
}
