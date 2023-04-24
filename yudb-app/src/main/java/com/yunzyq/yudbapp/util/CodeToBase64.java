package com.yunzyq.yudbapp.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class CodeToBase64 {

    /**
     * 生成二维码转base64
     */
    public static String b64QRCode(String code) {
        int width = 800;
        int height = 800;
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 容错级别 这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码图片
        // base64
        String encodeToString = "";
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            encodeToString = encoder.encodeToString(outputStream.toByteArray());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/jpeg;base64," + encodeToString;
    }

    /**
     * 生成一维码转base64
     */
    public static String barCode(String code) {
        int width = 560;
        int height = 75;
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 容错级别 这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        String encodeToString = "";
        try {
            // 生成条形码图片
            BitMatrix bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.CODE_128, width, height, hints);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            encodeToString = encoder.encodeToString(outputStream.toByteArray());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/jpeg;base64," + encodeToString;
    }
}
