package com.ilhaha.yueyishou.recycler.website.service;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.tencentcloud.CosUploadVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ilhaha
 * @Create 2024/9/24 10:38
 * @Version 1.0
 */
public interface CosService {

    /**
     * 回收员上传资料文件
     *
     * @param file
     * @param path
     * @return
     */
    Result<CosUploadVo> upload(MultipartFile file, String path);
}
