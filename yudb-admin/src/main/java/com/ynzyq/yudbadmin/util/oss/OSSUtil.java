package com.ynzyq.yudbadmin.util.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OSSUtil {
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI5tFzNNJFJEcPNxBtoW1d";
    private static String accessKeySecret = "cAdIq3NeWVG9CmrDGELyiSvyDD4fAR";
    private static String bucketName = "yn-ydb";
    private static OSSClient ossClient;
    /**
     * oss中文件地址的前缀
     */
    public static String OSS_URLPREFIX = "";
    public static String FILEPATH = "file/";

    static {
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传资源到oss指定路径下
     *
     * @param input 输入流对象
     * @param key   该资源key
     * @param size  该文件的大小
     */
    public static void uploadFile(InputStream input, String key, long size) {
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(size);
        // 上传Object.
        ossClient.putObject(bucketName, key, input, meta);
    }

    /**
     * 通过资源key删除该资源
     *
     * @param key
     */
    public static void deleteFile(String key) {
        ossClient.deleteObject(bucketName, key);
    }

    /**
     * 根据oss 路径前缀获得该路径下的所有资源的key的List集合
     *
     * @param prefix
     * @return
     */
    public static List<String> listFile(String prefix) {
        List<String> list = new ArrayList<String>();
        ObjectListing olist = ossClient.listObjects(bucketName, prefix);
        // 遍历所有Object
        for (OSSObjectSummary objectSummary : olist.getObjectSummaries()) {
            list.add(objectSummary.getKey());
        }
        return list;
    }

    /**
     * 从oss中下载文件
     *
     * @param filePath     oss key
     * @param downloadPath 下载文件存放路径
     * @throws IOException
     */
    public static void download(String filePath, String downloadPath) throws IOException {
        // 获取Object，返回结果为OSSObject对象
        OSSObject object = ossClient.getObject(bucketName, filePath);
        // 获取Object的输入流
        InputStream input = null;
        FileOutputStream out = null;
        try {
            input = object.getObjectContent();
            File file = new File(downloadPath);
            if (file.exists()) {
                file.delete();
            }
            out = new FileOutputStream(file);
            int i = 1;
            while (i >= 0) {
                i = input.read();
                out.write(i);
            }
        } catch (IOException e) {
            throw new IOException();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (input != null) input.close();
        }
    }

    /**
     * 从oss中下载文件
     *
     * @param filePath oss key
     * @throws IOException
     */
    public static void download(String filePath, HttpServletResponse response) throws IOException {
        // 获取Object，返回结果为OSSObject对象
        System.out.println("filePath：" + filePath);
        OSSObject object = ossClient.getObject(bucketName, filePath);

        BufferedInputStream in = new BufferedInputStream(object.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        byte[] car = new byte[1024];
        int L = 0;
        while ((L = in.read(car)) != -1) {
            out.write(car, 0, L);

        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String contentType(String FilenameExtension) {
        if (FilenameExtension.equals("BMP") || FilenameExtension.equals("bmp"))
            return "image/bmp";
        if (FilenameExtension.equals("GIF") || FilenameExtension.equals("gif"))
            return "image/gif";
        if (FilenameExtension.equals("JPEG") || FilenameExtension.equals("jpeg") ||
                FilenameExtension.equals("JPG") || FilenameExtension.equals("jpg") ||
                FilenameExtension.equals("PNG") || FilenameExtension.equals("png"))
            return "image/jpeg";
        if (FilenameExtension.equals("HTML") || FilenameExtension.equals("html"))
            return "text/html";
        if (FilenameExtension.equals("TXT") || FilenameExtension.equals("txt"))
            return "text/plain";
        if (FilenameExtension.equals("VSD") || FilenameExtension.equals("vsd"))
            return "application/vnd.visio";
        if (FilenameExtension.equals("PPTX") || FilenameExtension.equals("pptx") ||
                FilenameExtension.equals("PPT") || FilenameExtension.equals("ppt"))
            return "application/vnd.ms-powerpoint";
        if (FilenameExtension.equals("DOCX") || FilenameExtension.equals("docx") ||
                FilenameExtension.equals("DOC") || FilenameExtension.equals("doc"))
            return "application/msword";
        if (FilenameExtension.equals("XML") || FilenameExtension.equals("xml"))
            return "text/xml";
        return "text/html";
    }


}
