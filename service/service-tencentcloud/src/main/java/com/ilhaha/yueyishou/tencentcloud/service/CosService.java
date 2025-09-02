package com.ilhaha.yueyishou.tencentcloud.service;

import com.ilhaha.yueyishou.model.vo.tencentcloud.CosUploadVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ilhaha
 * @Create 2024/9/8 15:20
 * @Version 1.0
 */
public interface CosService {

    /**
     * 使用腾讯云cos上传图片
     * @param file
     * @param path
     * @return
     */
    CosUploadVo upload(MultipartFile file, String path);

}
