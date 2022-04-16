package com.my.service;

import com.my.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jhp
 * @create 2022-04-15 15:54
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
