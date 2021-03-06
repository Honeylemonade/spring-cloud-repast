package com.example.product.vo;

import com.example.product.entity.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * ProductVO:
 *
 * @Author XvYanpeng
 * @Date 2020/3/7 17:17
 */
@Data
public class ProductVO {

    @JsonProperty("typeId")
    private Integer categoryId;

    @JsonProperty("name")
    private String categoryName;


    @JsonProperty("foods")
    List<ProductInfo> productInfoList;
}
