package com.ilhaha.yueyishou.customer.wechat.service;

import com.ilhaha.yueyishou.model.entity.category.CategoryInfo;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.category.CategorySubVo;

import java.util.List;

/**
 * @Author ilhaha
 * @Create 2024/9/21 16:18
 * @Version 1.0
 */
public interface CategoryService {

    /**
     * 获取所有已启用的父废品品类
     *
     * @return
     */
    List<CategoryInfo> parentList();

    /**
     * 获取父品类的所有子品类
     *
     * @param parentId
     * @return
     */
    Result<List<CategorySubVo>> getSubCategories(Long parentId);

    /**
     * 获取已启用的废品品类树
     *
     * @return
     */
    Result<List<CategoryInfo>> getCategoryTree();
}
