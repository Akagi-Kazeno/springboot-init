package com.shimakaze.springbootinit.controller;

import cn.hutool.core.util.IdUtil;
import com.shimakaze.springbootinit.common.BaseResponse;
import com.shimakaze.springbootinit.common.ErrorCode;
import com.shimakaze.springbootinit.common.ResultUtils;
import com.shimakaze.springbootinit.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 上传接口
 */
@RestController
@RequestMapping(value = "/upload", produces = "application/json;charset=utf-8")
@Slf4j
public class UploadController {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * 上传头像文件目录
     */
    @Value("${prop.avatar-folder}")
    private String AVATAR_FOLDER;
    /**
     * 上传文件目录
     */
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    /**
     * 用户头像上传
     *
     * @param file 头像文件
     * @return 文件路径
     */
    @PostMapping(value = "/avatar")
    public BaseResponse<Map<String, String>> image(@RequestPart MultipartFile file) {
        if (file == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "选择要上传的图片");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能大于10M");
        }
        //获取文件后缀名
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!"jpg,jpeg,png".toUpperCase().contains(suffix.toUpperCase())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "支持文件类型(jpg,jpeg,png),该文件格式不支持");
        }
        String savePath = AVATAR_FOLDER;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            savePathFile.mkdir();
        }
        //重命名文件
        String fileName = IdUtil.simpleUUID() + "." + suffix;
        try {
            file.transferTo(new File(savePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "保存文件异常");
        }
        Map<String, String> result = new HashMap<>();
        result.put("fileName", "/img/" + fileName);
        return ResultUtils.success(result);
    }

    /**
     * 文件上传
     *
     * @param files   文件
     * @return 文件路径
     */
    @PostMapping("/files")
    public BaseResponse<List<Object>> files(@RequestPart MultipartFile[] files) {
        if (files == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请选择要上传的文件");
        }
        List<Object> list = new ArrayList<>();
        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (file.getSize() > 1024 * 1024 * 500) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能大于500M");
                }
                String realPath = UPLOAD_FOLDER;
                String format = simpleDateFormat.format(new Date());
                File folder = new File(realPath + format);
                if (!folder.isDirectory()) {
                    folder.mkdirs();
                }
                String oldName = file.getOriginalFilename();
                assert oldName != null;
                // 重命名文件
                String newName = IdUtil.simpleUUID() + oldName.substring(oldName.lastIndexOf("."));
                try {
                    file.transferTo(new File(folder, newName));
                    // 返回服务器内文件路径
                    String filePath = UPLOAD_FOLDER + format + "/" + newName;
                    // 返回访问路径
                    // String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file/" + format + "/" + newName;
                    list.add(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return ResultUtils.success(list);
        }
        throw new BusinessException(ErrorCode.OPERATION_ERROR, "上传文件失败");
    }
}
