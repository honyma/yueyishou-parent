package com.ilhaha.yueyishou.recycler.website.controller;

import com.ilhaha.yueyishou.common.anno.WebsiteLoginVerification;
import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.tencentcloud.CosUploadVo;
import com.ilhaha.yueyishou.recycler.website.service.CosService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ilhaha
 * @Create 2024/9/24 10:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/cos")
public class CosController {

    @Resource
    private CosService cosService;

    /**
     * 回收员上传资料文件
     *
     * @param file
     * @param path
     * @return
     */
    @WebsiteLoginVerification
    @PostMapping("/upload")
    public Result<CosUploadVo> upload(@RequestPart("file") MultipartFile file,
                                      @RequestParam("path") String path) {
        return cosService.upload(file, path);
    }
}
