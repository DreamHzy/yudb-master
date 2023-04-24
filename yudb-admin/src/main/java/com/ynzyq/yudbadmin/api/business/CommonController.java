package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.CommonConstant;
import com.ynzyq.yudbadmin.dao.business.dto.SmsDto;
import com.ynzyq.yudbadmin.service.business.CommonService;
import com.ynzyq.yudbadmin.util.oss.FileUploadOss;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "(新)公共接口")
@Slf4j
@RequestMapping("/common")
@RestController
public class CommonController extends BaseController {


    @Resource
    CommonService commonService;


    @Value("${imageUrl}")
    private String imageurl;

    /**
     * 短信验证码
     */
    @ApiOperation("短信验证码(测试环境默认123456)")
    @PostMapping("/sms")
    public ApiRes sms(@RequestBody SmsDto dto, HttpServletRequest httpServletRequest) {
        return commonService.sms(dto, httpServletRequest);
    }


    /**
     * 文件上传
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ApiRes uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        try {

            if (file.isEmpty()) {
                return new ApiRes(CommonConstant.FAIL_CODE, "上传图片错误", "");
            }
            String path = FileUploadOss.uploadMultipartFile(file);
            if (StringUtils.isBlank(path)) {
                log.info("文件上传到OSS失败");
                return new ApiRes(CommonConstant.FAIL_CODE, "文件上传到OSS失败", null);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("path", path);
            map.put("imageurl", imageurl);
            map.put("fileName",  file.getOriginalFilename());
            return new ApiRes(CommonConstant.SUCCESS_CODE, "上传成功", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ApiRes(CommonConstant.FAIL_CODE, "请求上传文件异常", null);
    }
}
