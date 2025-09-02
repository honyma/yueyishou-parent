package com.ilhaha.yueyishou.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilhaha.yueyishou.common.constant.CategoryConstant;
import com.ilhaha.yueyishou.common.constant.PublicConstant;
import com.ilhaha.yueyishou.model.entity.category.CategoryInfo;
import com.ilhaha.yueyishou.category.mapper.CategoryInfoMapper;
import com.ilhaha.yueyishou.category.service.ICategoryInfoService;
import com.ilhaha.yueyishou.model.form.category.CategoryUpdateStatusForm;
import com.ilhaha.yueyishou.model.vo.category.CategorySubVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryInfoServiceImpl extends ServiceImpl<CategoryInfoMapper, CategoryInfo> implements ICategoryInfoService {

    /**
     * 获取所有已启用的父废品品类
     *
     * @return
     */
    @Override
    public List<CategoryInfo> parentList() {
        LambdaQueryWrapper<CategoryInfo> categoryInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryInfoLambdaQueryWrapper.eq(CategoryInfo::getParentId, CategoryConstant.FIRST_LEVEL_CATEGORY_ID)
                .eq(CategoryInfo::getStatus, PublicConstant.ENABLE_STATUS);
        return this.list(categoryInfoLambdaQueryWrapper);
    }

    /**
     * 获取父品类的所有子品类
     *
     * @param parentId
     * @return
     */
    @Override
    public List<CategorySubVo> getSubCategories(Long parentId) {
        LambdaQueryWrapper<CategoryInfo> categoryInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryInfoLambdaQueryWrapper.eq(CategoryInfo::getParentId, parentId)
                .eq(CategoryInfo::getStatus, PublicConstant.ENABLE_STATUS);
        List<CategoryInfo> list = this.list(categoryInfoLambdaQueryWrapper);
        return list.stream().map(item -> {
            CategorySubVo categorySubVo = new CategorySubVo();
            BeanUtils.copyProperties(item, categorySubVo);
            return categorySubVo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取已启用的废品品类树
     *
     * @return
     */
    @Cacheable(value = "userCategory", key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE")
    @Override
    public List<CategoryInfo> getCategoryTree() {
        // 查处所有的废品品类父级
        LambdaQueryWrapper<CategoryInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryInfo::getParentId, CategoryConstant.FIRST_LEVEL_CATEGORY_ID)
                .eq(CategoryInfo::getStatus, PublicConstant.ENABLE_STATUS);
        List<CategoryInfo> categoryInfoList = this.list(wrapper);
        // 根据父级查询所有的子品类
        return categoryInfoList.stream().peek(item -> {
            LambdaQueryWrapper<CategoryInfo> categoryInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            categoryInfoLambdaQueryWrapper.eq(CategoryInfo::getParentId, item.getId())
                    .eq(CategoryInfo::getStatus, PublicConstant.ENABLE_STATUS);
            List<CategoryInfo> child = this.list(categoryInfoLambdaQueryWrapper);
            item.setChildren(child);
        }).collect(Collectors.toList());
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 根据品类名称查询品类信息
     *
     * @param categoryName
     * @return
     */
    @Override
    public CategoryInfo getCategoryByName(String categoryName) {
        LambdaQueryWrapper<CategoryInfo> categoryInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryInfoLambdaQueryWrapper.eq(CategoryInfo::getName, categoryName);
        return this.getOne(categoryInfoLambdaQueryWrapper);
    }

    /**
     * 修改废品品类状态
     *
     * @param categoryUpdateStatusForm
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "category",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE"),
            @CacheEvict(value = "userCategory",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE")
    })
    @Override
    public String switchStatus(CategoryUpdateStatusForm categoryUpdateStatusForm) {
        LambdaUpdateWrapper<CategoryInfo> categoryInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        categoryInfoLambdaUpdateWrapper.set(CategoryInfo::getStatus, categoryUpdateStatusForm.getStatus())
                .in(CategoryInfo::getId, categoryUpdateStatusForm.getCategoryIds());
        this.update(categoryInfoLambdaUpdateWrapper);
        return "修改成功";
    }

    /**
     * 废品回收品类分层查询
     *
     * @return
     */
    @Cacheable(value = "category", key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE")
    @Override
    public List<CategoryInfo> queryPageList() {
        // 查处所有的废品品类父级
        LambdaQueryWrapper<CategoryInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryInfo::getParentId, CategoryConstant.FIRST_LEVEL_CATEGORY_ID);
        List<CategoryInfo> categoryInfos = this.list(wrapper);

        // 根据父级查询所有的子品类
        return categoryInfos.stream().peek(item -> {
            LambdaQueryWrapper<CategoryInfo> categoryInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            categoryInfoLambdaQueryWrapper.eq(CategoryInfo::getParentId, item.getId());
            List<CategoryInfo> child = this.list(categoryInfoLambdaQueryWrapper);
            item.setChildren(child);
        }).collect(Collectors.toList());
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Override
    public CategoryInfo queryById(String id) {
        return this.getById(id);
    }
}
