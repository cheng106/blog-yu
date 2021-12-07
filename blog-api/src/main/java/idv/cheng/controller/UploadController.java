package idv.cheng.controller;

import idv.cheng.aop.LogApi;
import idv.cheng.enums.ErrorCode;
import idv.cheng.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @author cheng
 * @since 2021/12/7 17:37
 **/
@Slf4j
@RestController
@RequestMapping("upload")
public class UploadController {

    @Value("${file.path}")
    private String filepath;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + "." +
                StringUtils.substringAfterLast(originalFilename, ".");
        try (BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(new File(filepath, filename)))) {

            outputStream.write(file.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            log.error("#ERR:", e);
            return Result.fail(ErrorCode.UPLOAD_ERROR);
        }

        return Result.success(filename);
    }
}
