package com.ilhaha.yueyishou.recycler.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.common.util.IDUtil;
import com.ilhaha.yueyishou.model.entity.recycler.RecyclerInfo;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerApplyForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerQueryForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateForm;
import com.ilhaha.yueyishou.model.form.recycler.RecyclerUpdateStatusForm;
import com.ilhaha.yueyishou.model.vo.recycler.RecyclerBaseInfoVo;
import com.ilhaha.yueyishou.recycler.service.IRecyclerInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recyclerInfo")
@Slf4j
public class RecyclerInfoController {

    @Resource
    private IRecyclerInfoService recyclerInfoService;

    /**
     * 获取回收员基本信息
     *
     * @param recyclerId
     * @return
     */
    @GetMapping("/base/info/{recyclerId}")
    public Result<RecyclerBaseInfoVo> getBaseInfo(@PathVariable("recyclerId") Long recyclerId) {
        return Result.ok(recyclerInfoService.getBaseInfo(recyclerId));
    }

    /**
     * 修改回收员评分
     *
     * @param recyclerUpdateForm
     * @return
     */
    @PostMapping("/update/rate")
    public Result<Boolean> updateBaseInfo(@RequestBody RecyclerUpdateForm recyclerUpdateForm) {
        return Result.ok(recyclerInfoService.updateBaseInfo(recyclerUpdateForm));
    }

    /*-------------------------------------------------以下是后管website的接口-----------------------------------------------------------*/

    /**
     * 回收员分页列表查询
     *
     * @param recyclerQueryForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/list")
    public Result<Page<RecyclerInfo>> queryPageList(@RequestBody RecyclerQueryForm recyclerQueryForm,
                                                    @RequestParam(name = "pageNo", required = false) Integer pageNo,
                                                    @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return Result.ok(recyclerInfoService.queryPageList(recyclerQueryForm, pageNo, pageSize));
    }

    /**
     * 添加
     *
     * @param recyclerApplyForm
     * @return
     */
    @PostMapping(value = "/add")
    public Result<Boolean> add(@RequestBody RecyclerApplyForm recyclerApplyForm) {
        RecyclerInfo recyclerInfo = new RecyclerInfo();
        BeanUtils.copyProperties(recyclerApplyForm, recyclerInfo);
        recyclerInfo.setRecyclerNo(IDUtil.generateRecyclerID());
        return Result.ok(recyclerInfoService.save(recyclerInfo));
    }

    /**
     * 编辑
     *
     * @param recyclerInfo
     * @return
     */
    @PostMapping(value = "/edit")
    public Result<String> edit(@RequestBody RecyclerInfo recyclerInfo) {
        recyclerInfoService.updateById(recyclerInfo);
        return Result.ok("编辑成功!");
    }


    /**
     * 回收员状态切换
     *
     * @param recyclerUpdateStatusForm
     * @return
     */
    @PostMapping("/switch/status")
    public Result<String> switchStatus(@RequestBody RecyclerUpdateStatusForm recyclerUpdateStatusForm) {
        return Result.ok(recyclerInfoService.switchStatus(recyclerUpdateStatusForm));
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<RecyclerInfo> queryById(@RequestParam(name = "id") Long id) {
        RecyclerInfo recyclerInfo = recyclerInfoService.getById(id);
        if (recyclerInfo == null) {
            throw new YueYiShouException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(recyclerInfo);
    }
}
