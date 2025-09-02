package com.ilhaha.yueyishou.tencentcloud.service.impl;

import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.tencentcloud.config.TencentCloudProperties;
import com.ilhaha.yueyishou.tencentcloud.service.CosService;
import com.ilhaha.yueyishou.model.vo.tencentcloud.CosUploadVo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author ilhaha
 * @Create 2024/9/8 15:21
 * @Version 1.0
 */
@Service
public class CosServiceImpl implements CosService {

    @Resource
    private COSClient cosClient;

    @Resource
    private TencentCloudProperties tencentCloudProperties;

    /**
     * 使用腾讯云 COS 上传图片，并设置为公有读
     *
     * @param file 上传的文件
     * @param path 存储路径
     * @return 上传后的文件信息，包括 URL
     */
    @Override
    public CosUploadVo upload(MultipartFile file, String path) {
        // 元数据信息
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.getSize());
        meta.setContentEncoding("UTF-8");
        meta.setContentType(file.getContentType());

        // 生成上传路径：path/uuid.文件后缀
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String uploadPath = path + "/" + UUID.randomUUID().toString().replaceAll("-", "") + fileType;

        PutObjectRequest putObjectRequest;
        try {
            // 创建 PutObjectRequest 对象
            putObjectRequest = new PutObjectRequest(
                    tencentCloudProperties.getBucketPrivate(),
                    uploadPath,
                    file.getInputStream(),
                    meta
            );
        } catch (IOException e) {
            throw new YueYiShouException(ResultCodeEnum.IMAGE_UPLOAD_FAIL);
        }

        // 设置文件为标准存储
        putObjectRequest.setStorageClass(StorageClass.Standard);

        // 设置文件为公有读权限
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        // 上传文件到 COS
        cosClient.putObject(putObjectRequest);

        // 封装返回对象
        CosUploadVo cosUploadVo = new CosUploadVo();
        cosUploadVo.setUrl(getPublicImageUrl(uploadPath)); // 获取文件的公有读 URL
        return cosUploadVo;
    }

    /**
     * 获取公有读文件的永久有效 URL
     *
     * @param path 文件在 COS 中的路径
     * @return 文件的公有读 URL
     */
    private String getPublicImageUrl(String path) {
        return "https://" + tencentCloudProperties.getBucketPrivate() + ".cos."
                + tencentCloudProperties.getRegion() + ".myqcloud.com/" + path;
    }
}
