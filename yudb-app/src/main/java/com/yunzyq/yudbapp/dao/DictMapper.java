package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.vo.IsShowQrcodeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author wj
 * @Date 2021/11/6
 */
@Mapper
public interface DictMapper {
    /**
     * 查询二维码设置
     *
     * @return
     */
    @Select("SELECT int_val as isShow FROM  system_dict WHERE code = 'qrcode' AND `status` = 1")
    IsShowQrcodeVO getIntVal();
}
