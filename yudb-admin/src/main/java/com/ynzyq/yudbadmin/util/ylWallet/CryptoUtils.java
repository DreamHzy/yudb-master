package com.ynzyq.yudbadmin.util.ylWallet;


import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;

public class CryptoUtils {


    /**
     * 生成签名
     *
     * @param algorithm 签名算法
     * @param content   原文
     * @param priKey    私钥
     * @param charset   字符编码
     * @return 签名
     */
    public static String sign(String algorithm, String content, PrivateKey priKey, String charset) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(priKey);
            signature.update(content.getBytes(charset));
            return Hex.toHexString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证签名
     *
     * @param algorithm 签名算法
     * @param content   原文
     * @param sign      签名
     * @param pubKey    公钥
     * @param charset   字符编码
     * @return 是否验签通过
     */
    public static boolean verifySign(String algorithm, String content, String sign, PublicKey pubKey, String charset) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(charset));
            return signature.verify(Hex.decode(sign));
        } catch (Exception e) {
            //验签失败
            throw new RuntimeException(e);
        }
    }

    public static PublicKey initPublicKeyFromHexString(String hexString) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Hex.decode(hexString));
        try {
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey initPublicKeyFromBase64String(String base64String) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64String));
        try {
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey initPublicKeyFromFile(File file) {
        try (FileInputStream bais = new FileInputStream(file)) {
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
            X509Certificate Cert = (X509Certificate) certificatefactory.generateCertificate(bais);
            PublicKey pk = Cert.getPublicKey();
            BASE64Encoder bse = new BASE64Encoder();
            return pk;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey initPrivateKeyFromHexString(String hexString) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Hex.decode(hexString));
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey initPrivateKeyFromBase64String(String base64String) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64String));
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey initPrivateKeyFromFile(File file, String pwd) {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream fin = new FileInputStream(file);
            ks.load(fin, pwd.toCharArray());
            return (PrivateKey) ks.getKey(ks.aliases().nextElement(), pwd.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
