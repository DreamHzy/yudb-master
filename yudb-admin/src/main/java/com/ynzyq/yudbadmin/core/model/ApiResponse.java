package com.ynzyq.yudbadmin.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 接口返回对象
 *
 * @author Caesar Liu
 * @date 2021/03/26 19:48
 */
@Data
@ApiModel(value = "ReQueryResult对象", description = "公共返回对象ReQueryResult")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Integer code;

    @ApiModelProperty(value = "响应描述", required = true)
    private String message;

    @ApiModelProperty(value = "响应实体类", required = true)
    private T data;

    /**
     * 请求成功
     *
     * @author Caesar Liu
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse success(T data) {
        return ApiResponse.success("请求成功", data);
    }

    /**
     * 请求成功
     *
     * @author Caesar Liu
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse success(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data);
    }

    /**
     * 请求失败
     *
     * @author Caesar Liu
     * @date 2021/03/26 19:48
     */
    public static ApiResponse failed() {
        return ApiResponse.failed("请求失败");
    }

    /**
     * 请求失败
     *
     * @author Caesar Liu
     * @date 2021/03/26 19:48
     */
    public static ApiResponse failed(String message) {
        return ApiResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }


    /**
     * 请求失败
     *
     * @author Caesar Liu
     * @date 2021/03/26 19:48
     */
    public static ApiResponse failed(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }


    /**
     * 相应
     *
     * @author Caesar Liu
     * @date 2021/03/26 19:48
     */
    public static <T> ApiResponse response(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}