package com.sky.controller.root;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@Api(tags = "文件上传接口")
@RequestMapping("/admin/common/")
public class UploadController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public Result<String> uploadFile(MultipartFile file){
        log.info("文件上传,{}",file.getOriginalFilename());
        String url;
        try {
            //noinspection DataFlowIssue
            url = aliOssUtil.upload(file.getBytes(), file.getOriginalFilename());
        } catch (IOException e) {
            log.error("文件上传失败,{}",e.getMessage());
            throw new RuntimeException(e);
        }
        return Result.success(url);
    }
}
