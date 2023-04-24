package com.yunzyq.yudbapp.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @ClassName TkMapper
 * @Description TODO
 * @Date 2019/7/16 16:15
 * @Created by sunyiwei
 */
public interface MyBaseMapper<T> extends Mapper<T>,MySqlMapper<T> {
}