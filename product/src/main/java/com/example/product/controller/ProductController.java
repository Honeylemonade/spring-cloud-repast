package com.example.product.controller;

import com.example.product.dto.CartDTO;
import com.example.product.entity.ProductCategory;
import com.example.product.entity.ProductInfo;
import com.example.product.enums.ResultCodeEnum;
import com.example.product.service.CategoryService;
import com.example.product.service.ProductService;
import com.example.product.vo.ProductVO;
import com.example.product.utils.APIResult;
import org.bouncycastle.jcajce.provider.digest.MD2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * ProductController:
 *
 * @Author XvYanpeng
 * @Date 2020/3/7 10:42
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有正在销售的商品
     *
     * @return
     */
    @GetMapping("/list")
    public APIResult<List<ProductVO>> list() {
        //商品列表
        List<ProductInfo> allOnSellList = productService.findAllOnSell();
        Set<Integer> categorySet = new HashSet<Integer>();
        for (ProductInfo p : allOnSellList) {
            categorySet.add(p.getCategoryId());
        }
        //种类列表
        List<ProductCategory> categoryList = categoryService.findByCategoryIdIn(new ArrayList<>(categorySet));

        ArrayList<ProductVO> productVOArrayList = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            ProductVO tempProductVO = new ProductVO();
            productVOArrayList.add(tempProductVO);
            tempProductVO.setCategoryId(categoryList.get(i).getCategoryId());
            tempProductVO.setCategoryName(categoryList.get(i).getCategoryName());
            tempProductVO.setProductInfoList(new ArrayList<>());
            for (ProductInfo p : allOnSellList) {
                if (p.getCategoryId().equals(productVOArrayList.get(i).getCategoryId())) {
                    tempProductVO.getProductInfoList().add(p);
                }
            }

        }
        return new APIResult<List<ProductVO>>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), productVOArrayList);
    }

    /**
     * 根据商品ID列表，获取对应商品信息（提供给Order服务调用）
     *
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<Integer> productIdList) {
        return productService.findByProductIdIn(productIdList);

    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList) {
        productService.decreaseStock(cartDTOList);
    }
}
