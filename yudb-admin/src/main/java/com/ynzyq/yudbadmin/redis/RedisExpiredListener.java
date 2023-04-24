package com.ynzyq.yudbadmin.redis;//package com.hzjb.jfsc.common.redis;
//
//import com.hzjb.jfsc.common.Log;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//
///**
// * 接收key过期通知
// */
//public class RedisExpiredListener implements MessageListener {
//
//    @Override
//    public void onMessage(Message message, byte[] bytes) {
//        String expiredKey = new String(message.getBody());
//        byte[] channel = message.getChannel();
//        Log.info("redis key 过期  key = {} , channel {} ", expiredKey, new String(channel));
//    }
//}
