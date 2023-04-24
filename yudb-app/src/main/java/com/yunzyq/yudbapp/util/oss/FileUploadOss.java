package com.yunzyq.yudbapp.util.oss;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class FileUploadOss {

    //图片后缀名
    public static final String PICSUFFIXS = ".jpg.jpeg.png.gif.JPG.JPEG.PNG.GIF.avi.asf.wmv.3gp.mp4.flv.apk.txt";

    public static String uploadPic(File picFile) {

        String originalFilename = picFile.getName();
        String picSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        if (FileUploadOss.PICSUFFIXS.indexOf(picSuffix) == -1) {
//            return "";
//        }
        String fileName = UUID.randomUUID().toString() + new Date().getTime() + picSuffix;
        try {

            InputStream input = new FileInputStream(picFile);
            String key = OSSUtil.FILEPATH + fileName;
            OSSUtil.uploadFile(input, key, picFile.length());
            return OSSUtil.OSS_URLPREFIX + "/" + key;
        } catch (Exception e) {
            log.error("上传图片到OSS发生异常 ==> " + e.getMessage(), e);
            return "";
        }
    }

    public static String uploadMultipartFile(MultipartFile picFile) {
        String originalFilename = picFile.getOriginalFilename();
        String picSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        if (FileUploadOss.PICSUFFIXS.indexOf(picSuffix) == -1) {
//            return "";
//        }
        String fileName = new Date().getTime() + picSuffix;
        try (InputStream input = picFile.getInputStream()) {
            String key = OSSUtil.FILEPATH + fileName;

            OSSUtil.uploadFile(input, key, picFile.getSize());
            return OSSUtil.OSS_URLPREFIX + "/" + key;
        } catch (final Exception e) {
            log.error("上传图片到OSS发生异常 ==> " + e.getMessage(), e);
            return "";

        }
    }

    public static String uploadMultipartFileNew(MultipartFile picFile, String filePath) {
        String originalFilename = picFile.getOriginalFilename();
        String picSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        if (FileUploadOss.PICSUFFIXS.indexOf(picSuffix) == -1) {
//            return "";
//        }

        String fileName = new Date().getTime() + picSuffix;

        try (InputStream input = picFile.getInputStream()) {
            String key = filePath + "/" + fileName;
            OSSUtil.uploadFile(input, key, picFile.getSize());
            return OSSUtil.OSS_URLPREFIX + "/" + key;
        } catch (final Exception e) {
            log.error("上传图片到OSS发生异常 ==> " + e.getMessage(), e);
            return "";

        }
    }

}
