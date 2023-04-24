package com.ynzyq.yudbadmin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 接口返回对象
 *
 * @author Caesar Liu
 * @date 2021/03/26 19:48
 */
@Data
@AllArgsConstructor
@ApiModel(value = "ReQueryResult对象", description = "公共返回对象ReQueryResult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRes<T> {


    @ApiModelProperty(value = "响应标识 200成功 其余都是失败请求", required = true)
    private Integer code;

    @ApiModelProperty(value = "响应描述", required = true)
    private String message;

    @ApiModelProperty(value = "响应实体类", required = true)
    private T data;


    /**
     * 相应
     *
     * @author Caesar Liu
     * @date 2021/03/26 19:48
     */
    public static <T> ApiRes response(int code, String message, T data) {
        return new ApiRes<>(code, message, data);
    }

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> ApiRes successResponse() {
        return new ApiRes<>(CommonConstant.SUCCESS_CODE, "成功", "");
    }

    /**
     * 成功带返回数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiRes successResponseData(T data) {
        return new ApiRes<>(CommonConstant.SUCCESS_CODE, "成功", data);
    }

    /**
     * 失败
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ApiRes failResponse(String msg) {
        return new ApiRes<>(CommonConstant.FAIL_CODE, msg, "");
    }
}