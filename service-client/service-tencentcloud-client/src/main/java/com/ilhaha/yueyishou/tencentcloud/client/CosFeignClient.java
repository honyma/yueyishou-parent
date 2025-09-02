package com.ilhaha.yueyishou.tencentcloud.client;

import com.ilhaha.yueyishou.common.result.Result;
import com.ilhaha.yueyishou.model.vo.tencentcloud.CosUploadVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ilhaha
 * @Create 2024/9/8 16:28
 * @Version 1.0
 */
@FeignClient("service-server")
public interface CosFeignClient {

    /**
     * 使用腾讯云cos上传图片
     *
     * @param file
     * @param path
     * @return
     */
    @PostMapping(value = "/cos/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<CosUploadVo> upload(@RequestPart("file") MultipartFile file,
                               @RequestParam("path") String path);

}
