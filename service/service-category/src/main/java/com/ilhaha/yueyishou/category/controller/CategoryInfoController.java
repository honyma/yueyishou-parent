package com.ilhaha.yueyishou.category.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ilhaha.yueyishou.category.service.ICategoryInfoService;
import com.ilhaha.yueyishou.model.entity.category.CategoryInfo;
import com.ilhaha.yueyishou.model.form.category.CategoryUpdateStatusForm;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.category.CategorySubVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/categoryInfo")
@Slf4j
public class CategoryInfoController {
    @Resource
    private ICategoryInfoService categoryInfoService;

    /**
     * 获取所有已启用的父废品品类
     *
     * @return
     */
    @GetMapping("/parent/list")
    public Result<List<CategoryInfo>> parentList() {
        return Result.ok(categoryInfoService.parentList());
    }

    /**
     * 获取父品类的所有子品类
     *
     * @param parentId
     * @return
     */
    @GetMapping("/sub/list/{parentId}")
    public Result<List<CategorySubVo>> getSubCategories(@PathVariable("parentId") Long parentId) {
        return Result.ok(categoryInfoService.getSubCategories(parentId));
    }

    /**
     * 获取已启用的废品品类树
     *
     * @return
     */
    @GetMapping("/tree")
    public Result<List<CategoryInfo>> getCategoryTree() {
        return Result.ok(categoryInfoService.getCategoryTree());
    }


    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 列表查询
     *
     * @return
     */
    @GetMapping(value = "/list")
    public Result<List<CategoryInfo>> queryPageList() {
        List<CategoryInfo> list = categoryInfoService.queryPageList();
        return Result.ok(list);
    }

    /**
     * 切换废品品类状态
     *
     * @return
     */
    @PostMapping("/switch/status")
    public Result<String> switchStatus(@RequestBody CategoryUpdateStatusForm categoryUpdateStatusForm) {
        return Result.ok(categoryInfoService.switchStatus(categoryUpdateStatusForm));
    }

    /**
     * 添加
     *
     * @param categoryInfo
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "category",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE",
                    condition = "#result.data"),
            @CacheEvict(value = "userCategory",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE",
                    condition = "#result.data")
    })
    @PostMapping(value = "/add")
    public Result<Boolean> add(@RequestBody CategoryInfo categoryInfo) {
        CategoryInfo categoryInfoDB = categoryInfoService.getCategoryByName(categoryInfo.getName());
        if (!ObjectUtils.isEmpty(categoryInfoDB)) {
            return Result.ok(false);
        }
        categoryInfoService.save(categoryInfo);
        return Result.ok(true);
    }

    /**
     * 编辑
     *
     * @param categoryInfo
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "category",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE",
                    condition = "#result.data"),
            @CacheEvict(value = "userCategory",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE",
                    condition = "#result.data")
    })
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<Boolean> edit(@RequestBody CategoryInfo categoryInfo) {
        CategoryInfo categoryInfoDB = categoryInfoService.getCategoryByName(categoryInfo.getName());

        if (!ObjectUtils.isEmpty(categoryInfoDB) &&
                !categoryInfoDB.getId().equals(categoryInfo.getId())) {
            return Result.ok(false);
        }
        categoryInfoService.updateById(categoryInfo);
        return Result.ok(true);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "category",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE",
                    condition = "#result.data"),
            @CacheEvict(value = "userCategory",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE",
                    condition = "#result.data")
    })
    @DeleteMapping(value = "/delete")
    public Result<Boolean> delete(@RequestParam(name = "id") String id) {
        // 判断要删除的品类是否有子品类
        LambdaQueryWrapper<CategoryInfo> categoryInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryInfoLambdaQueryWrapper.in(CategoryInfo::getParentId, List.of(id));
        List<CategoryInfo> categoryInfos = categoryInfoService.list(categoryInfoLambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(categoryInfos) && categoryInfos.size() > 0) {
            return Result.ok(false);
        }
        categoryInfoService.removeById(id);
        return Result.ok(true);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "category",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE",
                    condition = "#result.data"),
            @CacheEvict(value = "userCategory",
                    key = "T(com.ilhaha.yueyishou.model.constant.RedisConstant).CATEGORY_TREE",
                    condition = "#result.data")
    })
    @DeleteMapping(value = "/deleteBatch")
    public Result<Boolean> deleteBatch(@RequestParam(name = "ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        LambdaQueryWrapper<CategoryInfo> categoryInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryInfoLambdaQueryWrapper.in(CategoryInfo::getParentId, idList);
        List<CategoryInfo> categoryInfos = categoryInfoService.list(categoryInfoLambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(categoryInfos) && categoryInfos.size() > 0) {
            return Result.ok(false);
        }
        this.categoryInfoService.removeByIds(idList);
        return Result.ok(true);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<CategoryInfo> queryById(@RequestParam(name = "id") String id) {
        return Result.ok(categoryInfoService.queryById(id));
    }
}
