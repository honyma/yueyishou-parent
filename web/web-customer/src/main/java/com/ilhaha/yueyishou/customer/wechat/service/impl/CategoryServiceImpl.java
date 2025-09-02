package com.ilhaha.yueyishou.customer.wechat.service.impl;

import com.ilhaha.yueyishou.category.client.CategoryInfoFeignClient;
import com.ilhaha.yueyishou.customer.wechat.service.CategoryService;
import com.ilhaha.yueyishou.model.entity.category.CategoryInfo;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.category.CategorySubVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/21 16:18
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryInfoFeignClient categoryInfoFeignClient;

    /**
     * 获取所有已启用的父废品品类
     *
     * @return
     */
    @Override
    public List<CategoryInfo> parentList() {
        return categoryInfoFeignClient.parentList().getData();
    }

    /**
     * 获取已启用的废品品类树
     *
     * @return
     */
    @Override
    public Result<List<CategoryInfo>> getCategoryTree() {
        return categoryInfoFeignClient.getCategoryTree();
    }

    /**
     * 获取父品类的所有子品类
     *
     * @param parentId
     * @return
     */
    @Override
    public Result<List<CategorySubVo>> getSubCategories(Long parentId) {
        return categoryInfoFeignClient.getSubCategories(parentId);
    }
}
