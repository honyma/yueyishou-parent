package com.ilhaha.yueyishou.model.entity.category;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ilhaha.yueyishou.model.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@TableName("t_category_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CategoryInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String name;
    /**
     * icon
     */
    private String icon;
    /**
     * 上层分类ID，0表示顶级分类
     */
    private Long parentId;
    /**
     * 单价,支持价格区间
     */
    private String unitPrice;
    /**
     * 单位
     */
    private String unitName;
    /**
     * 品类描述
     */
    private String describe;
    /**
     * 是否常用，1:是 0:否
     */
    private Integer common;
    /**
     * 状态，1:正常 0:禁用
     */
    private Integer status;
    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<CategoryInfo> children;
}
