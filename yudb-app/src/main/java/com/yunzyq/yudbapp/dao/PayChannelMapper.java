package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.PayChannel;

import com.yunzyq.yudbapp.dos.PayChannelDO;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

public interface PayChannelMapper extends MyBaseMapper<PayChannel> {

    @Select("SELECT*FROM pay_channel WHERE CHANNEL_STATUS = 1 and PAY_TYPE=3 LIMIT 1")
    PayChannel queryByStatus();

//    @Select("<script>SELECT*FROM pay_channel WHERE CHANNEL_STATUS = 1 and PAY_TYPE=4 AND #{money} <![CDATA[ >= ]]> MIN_MONEY AND #{money} <![CDATA[ < ]]> MAX_MONEY  LIMIT 1</script>")
    @Select("SELECT " +
            "  *  " +
            "FROM " +
            "  pay_channel AS t1 " +
            "  JOIN ( " +
            "  SELECT " +
            "    ROUND( " +
            "      RAND() * ( " +
            "        ( SELECT MAX( id ) FROM pay_channel WHERE CHANNEL_STATUS = 1 AND PAY_TYPE = 4  AND ID != 10  ) - ( SELECT MIN( id ) FROM pay_channel WHERE CHANNEL_STATUS = 1 AND PAY_TYPE = 4 AND ID != 10 )  " +
            "      ) + ( SELECT MIN( id ) FROM pay_channel WHERE CHANNEL_STATUS = 1 AND PAY_TYPE = 4  AND ID != 10  )  " +
            "    ) AS id  " +
            "  ) AS t2  " +
            "WHERE " +
            "  t1.id >= t2.id  " +
            "  AND CHANNEL_STATUS = 1  " +
            "  AND t1.PAY_TYPE = 4  " +
            "ORDER BY " +
            "  t1.id  " +
            "  LIMIT 1")
    PayChannel queryByStatusWx(BigDecimal money);

    /**
     * 查询通道信息
     *
     * @param payType
     * @param money
     * @return
     */
//    @Select("SELECT * FROM pay_channel WHERE CHANNEL_STATUS = 1 and PAY_TYPE = #{payType} LIMIT 1")
    @Select("SELECT " +
            "  *  " +
            "FROM " +
            "  pay_channel AS t1 " +
            "  JOIN ( " +
            "  SELECT " +
            "    ROUND( " +
            "      RAND() * ( " +
            "        ( SELECT MAX( id ) FROM pay_channel WHERE CHANNEL_STATUS = 1 AND PAY_TYPE = #{payType} AND ID != 10  ) - ( SELECT MIN( id ) FROM pay_channel WHERE CHANNEL_STATUS = 1 AND PAY_TYPE = #{payType} AND ID != 10  )  " +
            "      ) + ( SELECT MIN( id ) FROM pay_channel WHERE CHANNEL_STATUS = 1 AND PAY_TYPE = #{payType} AND ID != 10  )  " +
            "    ) AS id  " +
            "  ) AS t2  " +
            "WHERE " +
            "  t1.id >= t2.id  " +
            "  AND CHANNEL_STATUS = 1  " +
            "  AND t1.PAY_TYPE = #{payType}  " +
            "ORDER BY " +
            "  t1.id  " +
            "  LIMIT 1")
//    @Select("<script>" +
//            "SELECT " +
//            " *  " +
//            "FROM " +
//            " pay_channel  " +
//            "WHERE " +
//            " CHANNEL_STATUS = 1  " +
//            " AND PAY_TYPE = #{payType}  " +
//            " <if test='money != null'> AND #{money} <![CDATA[ >= ]]> MIN_MONEY AND #{money} <![CDATA[ < ]]> MAX_MONEY </if> " +
//            " LIMIT 1 " +
//            "</script>")
    PayChannelDO queryChannelByStatus(Integer payType, BigDecimal money);

    @Select("SELECT * FROM pay_channel WHERE CHANNEL_STATUS = 1 AND ID = #{channelId}")
    PayChannel getChannelById(Integer channelId);

    @Select("SELECT * FROM pay_channel WHERE CHANNEL_STATUS = 1 AND ID = #{channelId}")
    PayChannelDO getChannelById2(Integer channelId);
}