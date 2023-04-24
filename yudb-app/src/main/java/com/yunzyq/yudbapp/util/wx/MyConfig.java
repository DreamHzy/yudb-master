package com.yunzyq.yudbapp.util.wx;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MyConfig extends WXPayConfig {

    private byte[] certData;

    public MyConfig(String certPath) throws Exception {

        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return "wx86531cf5d7365328";
    }

    public String getMchID() {
        return "1618149975";
    }

    public String getKey() {
        return "usce06572090ce41918935479aafbdAA";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }


    // 这个方法需要这样实现, 否则无法正常初始化WXPay
    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {

            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;

    }

}